package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSPackage;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMFunctionClosure;
import edu.stanford.cs.svm.SVMModule;
import edu.stanford.cs.svm.SVMStackFrame;
import java.util.Iterator;

public class SJSCompiler {
   private SVM svm;
   private SJSParser parser;
   private SJSCommandMonitor monitor;

   public SJSCompiler(SVM svm, SJSParser parser, SJSCommandMonitor monitor) {
      this.svm = svm;
      this.parser = parser;
      this.monitor = monitor;
   }

   public SVM getSVM() {
      return this.svm;
   }

   public SJSParser getParser() {
      return this.parser;
   }

   public SJSCommandMonitor getCommandMonitor() {
      return this.monitor;
   }

   public CodeVector executeModule(SVMModule module) {
      CodeVector cv = new CodeVector();
      cv.addInstruction(18, cv.stringRef(module.getFilePath()));
      cv.addInstruction(20, 1);
      Iterator var4 = module.getImports().iterator();

      while(var4.hasNext()) {
         String name = (String)var4.next();
         if (name.indexOf(".") == -1) {
            String pkg = "edu.stanford.cs.sjslib." + name;
            JSPackage.load((String)pkg, this.svm);
         }
      }

      this.parser.compileFunctions(module.getFunctions(), cv);
      int[] code = cv.getCode();
      Iterator var13 = module.getFunctions().iterator();

      while(var13.hasNext()) {
         Expression fn = (Expression)var13.next();
         Expression[] args = fn.getArgs();
         String name = args[0].getName();
         int addr = cv.getLabel(name);
         SVMFunctionClosure closure = new SVMFunctionClosure(code, addr, (SVMStackFrame)null);
         this.svm.setGlobal(name, Value.createObject(closure, "FunctionClosure"));
      }

      CodeVector ncv = new CodeVector();
      Iterator var15 = module.getGlobals().iterator();

      while(var15.hasNext()) {
         Expression exp = (Expression)var15.next();
         Expression[] args = exp.getArgs()[0].getArgs();
         ncv.addInstruction(18, ncv.stringRef(args[0].getName()));
         this.parser.compile(args[1], ncv);
         ncv.addInstruction(97, ncv.stringRef("Global.set"));
      }

      ncv.addInstruction(0, 0);
      code = ncv.getCode();
      this.svm.reset();
      this.svm.setCode(code);
      this.svm.pushExceptionFrame(-999);
      this.svm.setState(1);

      while(code[this.svm.getPC()] != 0) {
         this.svm.executeInstruction();
      }

      this.svm.reset();
      return cv;
   }

   public void compileModule(SVMModule module, CodeVector cv, String main) {
      cv.addInstruction(18, cv.stringRef(module.getFilePath()));
      cv.addInstruction(20, 1);
      String init = cv.newLabel();
      cv.addInstruction(96, cv.labelRef(init));
      if (main != null) {
         cv.addInstruction(108, cv.stringRef(main));
         cv.addInstruction(98, 0);
      }

      cv.addInstruction(4, 0);
      this.parser.compileFunctions(module.getFunctions(), cv);
      cv.defineLabel(init);
      Iterator var6 = module.getFunctions().iterator();

      Expression exp;
      while(var6.hasNext()) {
         exp = (Expression)var6.next();
         String name = exp.getArgs()[0].getName();
         if (exp.getArgs()[5].getValue().getBooleanValue()) {
            cv.addInstruction(18, cv.stringRef(name));
            cv.addInstruction(19, cv.getLabel(name));
            cv.addInstruction(97, cv.stringRef("Global.set"));
         }
      }

      var6 = module.getGlobals().iterator();

      while(var6.hasNext()) {
         exp = (Expression)var6.next();
         Expression[] args = exp.getArgs()[0].getArgs();
         Expression lhs = args[0];
         Expression rhs = args[1];
         cv.addInstruction(18, cv.stringRef(lhs.getName()));
         this.parser.compile(rhs, cv);
         cv.addInstruction(97, cv.stringRef("Global.set"));
      }

      cv.addInstruction(99, 0);
   }
}
