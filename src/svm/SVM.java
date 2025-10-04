package edu.stanford.cs.svm;

import edu.stanford.cs.controller.Controller;
import edu.stanford.cs.controller.Steppable;
import edu.stanford.cs.exp.EvalContext;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.LValue;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.jsconsole.NBConsole;
import edu.stanford.cs.utf8.UTF8;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Stack;

public class SVM extends Controller implements EvalContext, Steppable {
   public static final int ERROR_PC = -999;
   public static final int BY_INSTRUCTION = 0;
   public static final int BY_STATEMENT = 1;
   public static final int INITIAL = 0;
   public static final int RUNNING = 1;
   public static final int STEPPING = 2;
   public static final int CALLING = 3;
   public static final int STOPPED = 4;
   public static final int FINISHED = 5;
   public static final int WAITING = 6;
   public static final int ERROR = 7;
   private ArrayDeque<SVMEventClosure> eventQueue = new ArrayDeque();
   private HashMap<String, Value> globals = new HashMap();
   private NBConsole console = null;
   private Stack<ExceptionFrame> exceptionStack = new Stack();
   private Stack<SVMStackFrame> frameStack = new Stack();
   private Stack<Value> valueStack = new Stack();
   private String source = null;
   private SVMStackFrame cf = new SVMStackFrame();
   private SVMProgram program = null;
   private boolean traceFlag = false;
   private boolean traceErrors = false;
   private int pc = 0;
   private int lastInstruction;
   private int statementOffset;
   private int stepMode = 0;
   private int[] code = null;

   public SVM() {
      this.setState(0);
      this.setErrorHandler(new SVMErrorHandler(this));
      SVMClass.defineClass(this, "Core", new SVMCoreClass());
      SVMClass.defineClass(this, "Global", new SVMGlobalClass());
   }

   public void run() {
      if (this.code == null) {
         this.setState(0);
      } else {
         this.setState(1);

         while(this.getState() == 1) {
            try {
               this.executeInstruction();
            } catch (Exception var3) {
               String msg = var3 instanceof RuntimeException ? var3.getMessage() : var3.toString();
               if (this.traceErrors) {
                  var3.printStackTrace();
               }

               this.signalError(msg);
            }
         }
      }

   }

   public void step() {
      if (this.code == null) {
         this.setState(0);
      } else {
         if (this.stepMode == 0) {
            this.executeInstruction();
         } else {
            this.executeInstruction();

            while(this.pc >= 0 && this.pc < this.code.length) {
               int state = this.getState();
               if (state == 7 || state == 5) {
                  break;
               }

               this.executeInstruction();
               if ((this.lastInstruction >> 24 & 255) == 3) {
                  break;
               }
            }
         }

         this.stepHook();
      }

   }

   public boolean isCallable() {
      return true;
   }

   public int getStackDepth() {
      return this.frameStack.size();
   }

   public void setStepMode(int mode) {
      this.stepMode = mode;
   }

   public int getStepMode() {
      return this.stepMode;
   }

   public void executeInstruction() {
      if (this.code == null) {
         this.signalError("Null program");
      } else if (this.pc == -999) {
         this.signalError("Illegal return without a function call");
      } else if (this.pc >= 0 && this.pc < this.code.length) {
         this.lastInstruction = this.code[this.pc];
         int op = this.lastInstruction >> 24 & 255;
         int addr = this.lastInstruction & 16777215;
         SVMInstruction ins = SVMInstruction.get(op);
         if (this.traceFlag) {
            System.out.println("(" + this.pc + ") " + ins.unparse(this, addr));
         }

         ++this.pc;
         ins.execute(this, addr);
      } else {
         this.setState(5);
      }

   }

   public int getLastInstruction() {
      return this.lastInstruction;
   }

   public void setState(int state) {
      this.setControllerState(state);
      if (state == 6 || state == 5) {
         this.processEvents();
      }

   }

   public int getState() {
      return this.getControllerState();
   }

   public void setTraceFlag(boolean flag) {
      this.traceFlag = flag;
   }

   public boolean getTraceFlag() {
      return this.traceFlag;
   }

   public void setTraceErrors(boolean flag) {
      this.traceErrors = flag;
   }

   public boolean getTraceErrors() {
      return this.traceFlag;
   }

   public void setConsole(NBConsole console) {
      this.console = console;
   }

   public NBConsole getConsole() {
      return this.console;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getSource() {
      return this.source;
   }

   public SVMSourceMarker getSourceMarker(int index) {
      if (this.source != null && index >= 0) {
         int start = this.source.lastIndexOf("\n", index) + 1;
         int finish = this.source.indexOf("\n", start);
         if (finish == -1) {
            finish = this.source.length();
         }

         return new SVMSourceMarker(this.source.substring(start, finish), start);
      } else {
         return null;
      }
   }

   public void setProgram(SVMProgram program) {
      this.program = program;
   }

   public SVMProgram getProgram() {
      return this.program;
   }

   public void setCode(int[] code) {
      this.code = code;
      if (this.cf != null) {
         this.cf.setCode(code);
      }

   }

   public int[] getCode() {
      return this.code;
   }

   public int get(int addr) {
      return this.code[addr];
   }

   public String getString(int addr) {
      return UTF8.decode(this.code, addr);
   }

   public void setPC(int addr) {
      this.pc = addr;
   }

   public int getPC() {
      return this.pc;
   }

   public void push(Value v) {
      this.valueStack.push(v);
   }

   public void pushInteger(int n) {
      this.valueStack.push(Value.createInteger(n));
   }

   public void pushDouble(double d) {
      this.valueStack.push(Value.createDouble(d));
   }

   public void pushBoolean(boolean b) {
      this.valueStack.push(Value.createBoolean(b));
   }

   public void pushString(String str) {
      this.valueStack.push(Value.createString(str));
   }

   public Value pop() {
      return (Value)this.valueStack.pop();
   }

   public int popInteger() {
      return ((Value)this.valueStack.pop()).getIntegerValue();
   }

   public double popDouble() {
      return ((Value)this.valueStack.pop()).getDoubleValue();
   }

   public boolean popBoolean() {
      return ((Value)this.valueStack.pop()).getBooleanValue();
   }

   public String popString() {
      return ((Value)this.valueStack.pop()).getStringValue();
   }

   public void roll(int n) {
      Value[] values = new Value[n];

      int i;
      for(i = 0; i < n; ++i) {
         values[i] = this.pop();
      }

      this.push(values[0]);

      for(i = n - 1; i > 0; --i) {
         this.push(values[i]);
      }

   }

   public int getValueStackDepth() {
      return this.valueStack.size();
   }

   public Value peekBack(int k) {
      return (Value)this.valueStack.get(this.valueStack.size() - k - 1);
   }

   public void setStackBase(int offset) {
      this.cf.setStackBase(this.valueStack.size() - offset);
   }

   public void reset() {
      this.valueStack.clear();
      this.frameStack.clear();
      this.exceptionStack.clear();
      this.setState(0);
      this.setPC(0);
   }

   public String stringify(Value obj) {
      return obj.toString();
   }

   public void pstack() {
      if (this.valueStack.isEmpty()) {
         this.println("");
      } else {
         Value v = (Value)this.valueStack.pop();
         this.print(this.stringify(v) + " ");
         this.pstack();
         this.valueStack.push(v);
      }

   }

   public void list() {
      this.listCode(this.code);
   }

   public void listCode(int[] code) {
      int n = code.length;

      for(int i = 0; i < n; ++i) {
         this.print("(" + i + ") ");
         int ir = code[i];
         int op = ir >> 24 & 255;
         int addr = ir & 16777215;
         SVMInstruction ins = SVMInstruction.get(op);
         if (ins == null) {
            this.println("" + ir);
         } else {
            this.println(ins.unparse(this, addr));
         }

         if (ir == 0) {
            break;
         }
      }

   }

   public SVMStackFrame getCurrentFrame() {
      return this.cf;
   }

   public void pushFrame() {
      this.frameStack.push(this.cf);
      this.cf = new SVMStackFrame();
      this.cf.setCode(this.code);
   }

   public void popFrame() {
      this.cf = this.frameStack.isEmpty() ? null : (SVMStackFrame)this.frameStack.pop();
      if (this.cf != null) {
         this.code = this.cf.getCode();
      }

   }

   public void setStatementOffset(int offset) {
      this.statementOffset = offset;
   }

   public int getStatementOffset() {
      return this.statementOffset;
   }

   public void restoreStackBase() {
      if (this.cf != null) {
         int base = this.cf.getStackBase();

         while(this.valueStack.size() > base) {
            this.valueStack.pop();
         }

      }
   }

   public void pushExceptionFrame(int addr) {
      this.exceptionStack.push(new ExceptionFrame(addr, this.frameStack.size()));
   }

   public void popExceptionFrame() {
      this.exceptionStack.pop();
   }

   public void throwException(RuntimeException ex, Value v) {
      if (this.exceptionStack.isEmpty()) {
         throw ex;
      } else {
         ExceptionFrame ef = (ExceptionFrame)this.exceptionStack.pop();
         int depth = ef.getStackDepth();

         while(this.frameStack.size() > depth) {
            this.popFrame();
         }

         this.restoreStackBase();
         this.valueStack.push(v);
         this.setPC(ef.getDispatchAddress());
      }
   }

   public void print(String s) {
      if (this.console == null) {
         System.out.print(s);
      } else {
         this.console.print(s);
      }

   }

   public void println(String s) {
      if (this.console == null) {
         System.out.println(s);
      } else {
         this.console.println(s);
      }

   }

   public Value getValue(String name) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public void setValue(String name, Value value) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public boolean isDefined(String name) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public LValue getLValue(Expression exp) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public Value evalConstant(Expression exp) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public Value evalIdentifier(Expression exp) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public Value evalCompound(Expression exp) {
      throw new RuntimeException("System error: Illegal evaluation");
   }

   public int getArgumentCount() {
      return this.getCurrentFrame().getArgumentCount();
   }

   public int getNARGSCount() {
      if (this.pc >= 0 && this.pc < this.code.length) {
         int ins = this.get(this.pc);
         return (ins >> 24 & 255) != 106 ? -1 : ins & 16777215;
      } else {
         return -1;
      }
   }

   public void checkSignature(String name, String sig) {
      int nArgs = this.getArgumentCount();
      if (nArgs != -1) {
         int len = sig.length();
         if (len != nArgs) {
            throw new RuntimeException("Wrong number of arguments to " + name);
         } else {
            for(int i = 0; i < len; ++i) {
               if (!this.checkArgType(this.peekBack(len - i - 1), sig.charAt(i))) {
                  throw new RuntimeException("Type mismatch in call to " + name);
               }
            }

         }
      }
   }

   public void checkArgumentCount(int nArgs, int nParams) {
      int i;
      for(i = nParams; i < nArgs; ++i) {
         this.pop();
      }

      for(i = nArgs; i < nParams; ++i) {
         this.push(Value.UNDEFINED);
      }

   }

   public void setGlobal(String name, Value value) {
      this.globals.put(name, value);
   }

   public Value getGlobal(String name) {
      Value v = (Value)this.globals.get(name);
      return v == null ? Value.UNDEFINED : v;
   }

   public boolean isGlobal(String name) {
      return this.globals.containsKey(name);
   }

   public int getGlobalInteger(String name, int defValue) {
      return this.isGlobal(name) ? ((Value)this.globals.get(name)).getIntegerValue() : defValue;
   }

   public boolean getGlobalBoolean(String name, boolean defValue) {
      return this.isGlobal(name) ? ((Value)this.globals.get(name)).getBooleanValue() : defValue;
   }

   public double getGlobalDouble(String name, double defValue) {
      return this.isGlobal(name) ? ((Value)this.globals.get(name)).getDoubleValue() : defValue;
   }

   public String getGlobalString(String name, String defValue) {
      return this.isGlobal(name) ? ((Value)this.globals.get(name)).getStringValue() : defValue;
   }

   private boolean checkArgType(Value v, char type) {
      switch(type) {
      case '*':
         return true;
      case 'B':
         if (v.getType() == 66) {
            return true;
         }

         return false;
      case 'D':
         return v.isNumeric();
      case 'I':
         return v.isIntegral();
      case 'O':
         if (v.getType() == 79) {
            return true;
         }

         return false;
      case 'S':
         if (v.getType() == 83) {
            return true;
         }

         return false;
      default:
         throw new RuntimeException("Illegal type code: " + type);
      }
   }

   public void call(int n) {
      Value fn = this.pop();
      String type = fn.getClassName();
      Value receiver;
      if (type.equals("FunctionClosure")) {
         SVMFunctionClosure fc = (SVMFunctionClosure)fn.getValue();
         receiver = this.getCurrentFrame().getReceiver();
         this.pushFrame();
         SVMStackFrame cf = this.getCurrentFrame();
         cf.setThis(receiver);
         cf.setReturnAddress(this.getPC());
         cf.setFrameLink(fc.getFrame());
         cf.setArgumentCount(n);
         int[] code = fc.getCode();
         if (code != null) {
            this.setCode(code);
         }

         this.setPC(fc.getAddress());
      } else if (type.equals("MethodClosure")) {
         SVMMethodClosure mc = (SVMMethodClosure)fn.getValue();
         receiver = mc.getReceiver();
         SVMClass c = SVMClass.forName(mc.getClassName());
         SVMMethod m = c.getMethod(mc.getMethodName());
         this.getCurrentFrame().setArgumentCount(n);
         m.execute(this, receiver);
      } else if (type.equals("SVMMethod")) {
         SVMMethod m = (SVMMethod)fn.getValue();
         this.getCurrentFrame().setArgumentCount(n);
         m.execute(this, (Value)null);
      } else {
         if (!type.equals("Class")) {
            throw new RuntimeException("Illegal function call");
         }

         SVMClass c = SVMClass.forName((String)fn.getValue());
         SVMMethod m = c.getMethod("new");
         this.getCurrentFrame().setArgumentCount(n);
         m.execute(this, (Value)null);
      }

   }

   public void postEvent(SVMEventClosure closure) {
      this.eventQueue.add(closure);
      this.processEvents();
   }

   private void processEvents() {
      int state = this.getState();
      if (state == 6 || state == 5) {
         while(!this.eventQueue.isEmpty()) {
            SVMEventClosure closure = (SVMEventClosure)this.eventQueue.poll();
            closure.pushEventData(this);
            this.call(closure.getArgumentCount());
            this.run();
         }
      }

   }

   protected void stepHook() {
   }
}
