package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Compound;
import edu.stanford.cs.exp.Constant;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.Operator;
import edu.stanford.cs.parser.Parser;
import edu.stanford.cs.parser.Statement;
import edu.stanford.cs.parser.SyntaxError;
import edu.stanford.cs.svm.SVMModule;
import edu.stanford.cs.tokenscanner.TokenScanner;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import java.util.TreeSet;

public class SJSParser extends Parser {
   private ArrayList<String> functions;
   private ArrayList<String> formals;
   private ArrayList<String> locals;
   private Operator blockOperator = new SJSBlockOperator();
   private Operator functionKeyword = new SJSFunctionKeyword();
   private Operator statementOperator = new SJSStatementOperator();
   private Stack<StatementContext> statementStack;
   private SVMModule module;
   private TreeSet<String> lhsids = new TreeSet();
   private int anonymousFunctionCount;

   public SJSParser() {
      this.defineOperators();
      this.defineConstants();
      this.defineStatementForms();
      this.anonymousFunctionCount = 0;
      this.module = null;
   }

   public TokenScanner createTokenScanner() {
      TokenScanner scanner = new TokenScanner();
      scanner.ignoreWhitespace();
      scanner.ignoreComments();
      scanner.scanStrings();
      scanner.scanNumbers();
      scanner.addWordCharacters("_");
      this.addOperatorTokens(scanner);
      return scanner;
   }

   public void addOperatorTokens(TokenScanner scanner) {
      scanner.addOperator("++");
      scanner.addOperator("--");
      scanner.addOperator("==");
      scanner.addOperator("!=");
      scanner.addOperator("<=");
      scanner.addOperator(">=");
      scanner.addOperator("&&");
      scanner.addOperator("||");
      scanner.addOperator("+=");
      scanner.addOperator("-=");
      scanner.addOperator("*=");
      scanner.addOperator("/=");
      scanner.addOperator("%=");
      scanner.addOperator("===");
      scanner.addOperator("!==");
      scanner.addOperator("<<");
      scanner.addOperator(">>");
      scanner.addOperator(">>>");
   }

   public void defineOperators() {
      int LEFT = 0;
      int RIGHT = 1;
      this.defineOperator("(", new SJSParenOperator(), 0, 130, RIGHT);
      this.defineOperator("{", new SJSBraceOperator(), 0, 130, RIGHT);
      this.defineOperator("[", new SJSBracketOperator(), 0, 110, RIGHT);
      this.defineOperator(".", new SJSDotOperator(), 0, 110, LEFT);
      this.defineOperator("+", new SJSPlusOperator(), 100, 80, LEFT);
      this.defineOperator("-", new SJSMinusOperator(), 100, 80, LEFT);
      this.defineOperator("++", new SJSIncrementOperator(), 100, 100, RIGHT);
      this.defineOperator("--", new SJSDecrementOperator(), 100, 100, RIGHT);
      this.definePrefixOperator("!", new SJSNOTOperator(), 100);
      this.definePrefixOperator("~", new SJSBitwiseNOTOperator(), 100);
      this.defineInfixOperator("*", new SJSStarOperator(), 90, LEFT);
      this.defineInfixOperator("/", new SJSSlashOperator(), 90, LEFT);
      this.defineInfixOperator("%", new SJSPercentOperator(), 90, LEFT);
      this.defineInfixOperator("<<", new SJSLSHLeftOperator(), 70, LEFT);
      this.defineInfixOperator(">>", new SJSASHRightOperator(), 70, LEFT);
      this.defineInfixOperator(">>>", new SJSLSHRightOperator(), 70, LEFT);
      this.defineInfixOperator("<", new SJSLessThanOperator(), 60, LEFT);
      this.defineInfixOperator("<=", new SJSLessEqualOperator(), 60, LEFT);
      this.defineInfixOperator(">", new SJSGreaterThanOperator(), 60, LEFT);
      this.defineInfixOperator(">=", new SJSGreaterEqualOperator(), 60, LEFT);
      this.defineInfixOperator("===", new SJSEqualOperator(), 50, LEFT);
      this.defineInfixOperator("!==", new SJSNotEqualOperator(), 50, LEFT);
      this.defineInfixOperator("&", new SJSBitwiseANDOperator(), 45, LEFT);
      this.defineInfixOperator("^", new SJSBitwiseXOROperator(), 40, LEFT);
      this.defineInfixOperator("|", new SJSBitwiseOROperator(), 35, LEFT);
      this.defineInfixOperator("&&", new SJSANDOperator(), 30, LEFT);
      this.defineInfixOperator("||", new SJSOROperator(), 20, LEFT);
      this.defineInfixOperator("?", new SJSQuestionMarkColonOperator(), 15, RIGHT);
      this.defineInfixOperator("=", new SJSAssignmentOperator(), 10, RIGHT);
      this.defineInfixOperator("+=", new SJSPlusEqualOperator(), 10, RIGHT);
      this.defineInfixOperator("-=", new SJSMinusEqualOperator(), 10, RIGHT);
      this.defineInfixOperator("*=", new SJSStarEqualOperator(), 10, RIGHT);
      this.defineInfixOperator("/=", new SJSSlashEqualOperator(), 10, RIGHT);
      this.defineInfixOperator("%=", new SJSPercentEqualOperator(), 10, RIGHT);
      this.definePrefixOperator("+x", new SJSUnaryPlusOperator(), 100);
      this.definePrefixOperator("-x", new SJSUnaryMinusOperator(), 100);
      this.definePrefixOperator("x++", new SJSSuffixIncrementOperator(), 100);
      this.definePrefixOperator("x--", new SJSSuffixDecrementOperator(), 100);
      this.definePrefixOperator("[...]", new SJSListOperator(), 100);
      this.definePrefixOperator("var", new SJSVarKeyword(), 100);
      this.definePrefixOperator("const", new SJSConstKeyword(), 100);
      this.definePrefixOperator("new", new SJSNewKeyword(), 120);
      this.definePrefixOperator("function", new SJSFunctionKeyword(), 100);
      this.definePrefixOperator("this", new SJSThisKeyword(), 100);
   }

   public SVMModule readModule(String pathname) {
      this.module = new SVMModule(pathname);

      while(this.hasMoreTokens()) {
         this.readEntry();
      }

      return this.module;
   }

   public void readEntry() {
      String token = this.nextToken();
      if (token.equals("function")) {
         Expression fn = this.readFunction(true);
         String name = fn.getArgs()[0].getName();
         if (name.indexOf("#") != -1) {
            throw new SyntaxError("Top-level functions must have names");
         }
      } else if (!token.equals("var") && !token.equals("const")) {
         if (!token.equals("import")) {
            throw new SyntaxError("Illegal top-level definition");
         }

         this.readImport();
      } else {
         this.saveToken(token);
         this.readGlobal();
      }

   }

   public Expression readFunction(boolean topLevel) {
      ArrayList<String> oldFunctions = this.functions;
      ArrayList<String> oldFormals = this.formals;
      ArrayList<String> oldLocals = this.locals;
      this.functions = new ArrayList();
      this.formals = new ArrayList();
      this.locals = new ArrayList();
      String name = this.nextToken();
      if (name.equals("(")) {
         name = "fn#" + this.anonymousFunctionCount++;
      } else {
         if (this.getTokenType(name) != 1) {
            throw new SyntaxError("Illegal function name");
         }

         if (!topLevel) {
            name = name + "#" + this.anonymousFunctionCount++;
         }

         this.verifyToken("(");
      }

      String token = this.nextToken();

      while(!token.equals(")")) {
         this.formals.add(token);
         token = this.nextToken();
         if (!token.equals(")")) {
            if (!token.equals(",")) {
               throw new SyntaxError("Illegal parameter syntax");
            }

            token = this.nextToken();
         }
      }

      Expression body = this.readCompoundStatement();
      Expression top = new Constant(Value.createBoolean(topLevel));
      Expression fn = this.createCompound6(this.functionKeyword, this.createIdentifier(name), this.createList("functions", this.functions), this.createList("formals", this.formals), this.createList("locals", this.locals), body, top);
      this.functions = oldFunctions;
      this.formals = oldFormals;
      this.locals = oldLocals;
      this.module.addFunction(fn);
      return fn;
   }

   public void readGlobal() {
      Expression exp = this.readE(0);
      this.verifyToken(";");
      this.module.addGlobal(exp);
   }

   public void readImport() {
      String token = this.nextToken();
      if (this.getTokenType(token) != 3) {
         throw new SyntaxError("Missing library name in import");
      } else {
         this.verifyToken(";");
         this.module.addImport(this.getStringValue(token));
      }
   }

   public void declareLocal(String name) {
      if (this.formals != null && this.formals.contains(name)) {
         throw new SyntaxError(this.markCode(name) + " is both a local and a parameter");
      } else {
         if (this.locals != null) {
            this.locals.add(name);
         }

      }
   }

   public void setLocals(ArrayList<String> locals) {
      this.locals = locals;
   }

   public ArrayList<String> getLocals() {
      return this.locals;
   }

   public void setFormals(ArrayList<String> formals) {
      this.formals = formals;
   }

   public ArrayList<String> getFormals() {
      return this.formals;
   }

   public void setFunctions(ArrayList<String> functions) {
      this.functions = functions;
   }

   public ArrayList<String> getFunctions() {
      return this.functions;
   }

   public void defineStatementForms() {
      this.defineStatementForm("if", new SJSIfStatement());
      this.defineStatementForm("switch", new SJSSwitchStatement());
      this.defineStatementForm("while", new SJSWhileStatement());
      this.defineStatementForm("for", new SJSForStatement());
      this.defineStatementForm("return", new SJSReturnStatement());
      this.defineStatementForm("break", new SJSBreakStatement());
      this.defineStatementForm("continue", new SJSContinueStatement());
      this.defineStatementForm("case", new SJSCaseStatement());
      this.defineStatementForm("default", new SJSDefaultStatement());
      this.defineStatementForm("try", new SJSTryStatement());
      this.defineStatementForm("throw", new SJSThrowStatement());
   }

   public void defineConstants() {
      this.definePrefixOperator("false", new SJSFalseOperator(), 100);
      this.definePrefixOperator("true", new SJSTrueOperator(), 100);
      this.definePrefixOperator("null", new SJSNullOperator(), 100);
      this.definePrefixOperator("undefined", new SJSUndefinedOperator(), 100);
      this.definePrefixOperator("NaN", new SJSNaNOperator(), 100);
      this.definePrefixOperator("Infinity", new SJSInfinityOperator(), 100);
   }

   public void compile(Expression exp, CodeVector cv) {
      int type = exp.getType();
      switch(type) {
      case 1:
         this.compileConstant(exp.getValue(), cv);
         break;
      case 2:
         this.compileIdentifier(exp.getName(), cv);
         break;
      case 3:
         this.compileCompound((Compound)exp, cv);
      }

   }

   public void compileConstant(Value value, CodeVector cv) {
      int type = value.getType();
      switch(type) {
      case 66:
         String fn = "Core." + value.getValue().toString().toUpperCase();
         cv.addInstruction(97, cv.stringRef(fn));
         break;
      case 67:
         int ch = (Integer)value.getValue();
         cv.addInstruction(16, ch);
         break;
      case 68:
         cv.addInstruction(17, cv.stringRef("" + value.getValue()));
         break;
      case 73:
         int n = (Integer)value.getValue();
         if ((n & 16777215) == n) {
            cv.addInstruction(16, n);
         } else {
            cv.addInstruction(17, cv.stringRef("" + n));
         }
         break;
      case 83:
         String str = (String)value.getValue();
         cv.addInstruction(18, cv.stringRef(str));
         break;
      case 86:
         cv.addInstruction(97, cv.stringRef("Core.UNDEFINED"));
         break;
      default:
         throw new SyntaxError("Illegal value: " + value);
      }

   }

   public void compileIdentifier(String name, CodeVector cv) {
      cv.addInstruction(108, cv.stringRef(name));
   }

   public void compileCompound(Compound exp, CodeVector cv) {
      Expression fn = exp.getFunction();
      int type = fn.getType();
      if (type == 4) {
         ((Operator)fn).compile(this, exp.getArgs(), cv);
      } else {
         if (type == 3) {
            Expression op = fn.getFunction();
            if (op.getType() == 4 && op.getName().equals(".")) {
               Expression lhs = fn.getArgs()[0];
               Expression rhs = fn.getArgs()[1];
               if (lhs.getType() == 2) {
                  this.lhsids.add(lhs.getName());
               }

               this.compileArgs(exp.getArgs(), cv);
               this.compile(lhs, cv);
               cv.addInstruction(21, 0);
               cv.addInstruction(97, cv.stringRef("Core.setReceiver"));
               cv.addInstruction(18, cv.stringRef(rhs.getName()));
               cv.addInstruction(97, cv.stringRef("Core.select"));
               cv.addInstruction(98, 0);
               cv.addInstruction(106, exp.getArgs().length);
               return;
            }
         }

         this.compileArgs(exp.getArgs(), cv);
         this.compile(fn, cv);
         cv.addInstruction(97, cv.stringRef("Core.NULL"));
         cv.addInstruction(97, cv.stringRef("Core.setReceiver"));
         cv.addInstruction(98, 0);
         cv.addInstruction(106, exp.getArgs().length);
      }

   }

   public void compileLHS(Expression exp, CodeVector cv) {
      int type = exp.getType();
      if (type == 3) {
         Expression op = exp.getFunction();
         Expression lhs = exp.getArgs()[0];
         Expression rhs = exp.getArgs()[1];
         if (op.getType() == 4) {
            String name = op.getName();
            if (name.equals(".") && rhs.getType() == 2) {
               this.compile(lhs, cv);
               cv.addInstruction(18, cv.stringRef(rhs.getName()));
               return;
            }

            if (name.equals("[")) {
               this.compile(lhs, cv);
               this.compile(rhs, cv);
               return;
            }
         }
      } else if (type == 2) {
         return;
      }

      throw new SyntaxError("Illegal assignment");
   }

   public void compileSetLHS(Expression exp, CodeVector cv) {
      int type = exp.getType();
      if (type == 3) {
         cv.addInstruction(97, cv.stringRef("Core.set"));
      } else if (type == 2) {
         cv.addInstruction(109, cv.stringRef(exp.getName()));
      }

   }

   public void compileArgs(Expression[] args, CodeVector cv) {
      int n = args.length;

      for(int i = 0; i < n; ++i) {
         this.compile(args[i], cv);
      }

   }

   private Expression createList(String key, ArrayList<String> list) {
      int n = list.size();
      Expression[] args = new Expression[n];

      for(int i = 0; i < n; ++i) {
         args[i] = this.createIdentifier((String)list.get(i));
      }

      return this.createCompound(this.createIdentifier(key), args);
   }

   public Expression readStatement() {
      String token = this.nextToken();
      if (token == null) {
         throw new SyntaxError("Unexpected end of line");
      } else {
         this.saveToken(token);
         if (token.equals("{")) {
            return this.readCompoundStatement();
         } else {
            Constant pos = new Constant(Value.createInteger(this.getPosition()));
            Operator op = this.getOperator(token);
            if (op != null && op.isStatement()) {
               this.nextToken();
               return this.createCompound2(this.statementOperator, pos, op.prefixAction(this));
            } else {
               Expression exp = this.readE(0);
               this.verifyToken(";");
               return this.createCompound2(this.statementOperator, pos, exp);
            }
         }
      }
   }

   public Expression readCompoundStatement() {
      this.verifyToken("{");
      ArrayList<Expression> statements = new ArrayList();

      for(String token = this.nextToken(); !token.equals("}"); token = this.nextToken()) {
         Expression exp;
         if (token.equals("function")) {
            exp = this.readFunction(false);
            String name = exp.getArgs()[0].getName();
            this.declareLocal(name);
            statements.add(this.createCompound2(this.getOperator("="), this.createIdentifier(name), exp));
         } else if (token.equals("function")) {
            exp = this.functionKeyword.prefixAction(this);
            this.functions.add(exp.getArgs()[0].getName());
         } else {
            this.saveToken(token);
            statements.add(this.readStatement());
         }
      }

      int n = statements.size();
      Expression[] array = new Expression[n];

      for(int i = 0; i < n; ++i) {
         array[i] = (Expression)statements.get(i);
      }

      return this.createCompound(this.blockOperator, array);
   }

   public void defineStatementForm(String name, Statement op) {
      this.defineOperator(name, op, 0, 0, 0);
   }

   public boolean usesConsole() {
      return this.lhsids.contains("console") || this.lhsids.contains("Console") || this.lhsids.contains("UnitTest");
   }

   public Expression parseConstant(String token) {
      int type = this.getTokenType(token);
      if (type == 2) {
         if (token.indexOf(".") >= 0) {
            return new Constant(Value.createDouble(Double.parseDouble(token)));
         } else {
            int n;
            if (!token.startsWith("0x") && !token.startsWith("0X")) {
               if (token.startsWith("0")) {
                  n = Integer.parseInt(token, 8);
                  return new Constant(Value.createInteger(n));
               } else {
                  return new Constant(Value.createInteger(Integer.parseInt(token)));
               }
            } else {
               n = Integer.parseInt(token.substring(2), 16);
               return new Constant(Value.createInteger(n));
            }
         }
      } else if (type == 3) {
         String s = this.getStringValue(token);
         return new Constant(Value.createString(s));
      } else {
         throw new SyntaxError("Illegal constant: " + this.markCode(token));
      }
   }

   public void compileFunctions(ArrayList<Expression> functions, CodeVector cv) {
      this.statementStack = new Stack();
      Iterator var4 = functions.iterator();

      while(var4.hasNext()) {
         Expression fn = (Expression)var4.next();
         Expression[] args = fn.getArgs();
         this.pushStatementContext((String)null, (String)null);
         String name = args[0].getName();
         Expression[] innerFunctions = args[1].getArgs();
         Expression[] formals = args[2].getArgs();
         Expression[] locals = args[3].getArgs();
         Expression body = args[4];
         cv.defineLabel(name);
         int n = formals.length;
         cv.addInstruction(105, n);

         int i;
         for(i = n - 1; i >= 0; --i) {
            cv.addInstruction(103, cv.stringRef(formals[i].getName()));
         }

         n = locals.length;

         for(i = 0; i < n; ++i) {
            cv.addInstruction(104, cv.stringRef(locals[i].getName()));
         }

         n = innerFunctions.length;

         for(i = 0; i < n; ++i) {
            String id = innerFunctions[i].getName();
            String fnName = id.substring(0, id.indexOf("#"));
            cv.addInstruction(104, cv.stringRef(fnName));
            cv.addInstruction(18, cv.stringRef(id));
            cv.addInstruction(97, cv.stringRef("Global.get"));
            cv.addInstruction(109, cv.stringRef(fnName));
         }

         this.compile(body, cv);
         cv.addInstruction(97, cv.stringRef("Core.UNDEFINED"));
         cv.addInstruction(99, 0);
         this.popStatementContext();
      }

   }

   public void pushStatementContext(String breakLabel, String continueLabel) {
      StatementContext sc = new StatementContext();
      sc.breakLabel = breakLabel;
      sc.continueLabel = continueLabel;
      sc.nextLabel = null;
      this.statementStack.push(sc);
   }

   public void popStatementContext() {
      this.statementStack.pop();
   }

   public int getStatementDepth() {
      return this.statementStack.size();
   }

   public void setNextLabel(String str) {
      ((StatementContext)this.statementStack.peek()).nextLabel = str;
   }

   public String getNextLabel() {
      return ((StatementContext)this.statementStack.peek()).nextLabel;
   }

   public String getBreakLabel() {
      return ((StatementContext)this.statementStack.peek()).breakLabel;
   }

   public String getContinueLabel() {
      return ((StatementContext)this.statementStack.peek()).continueLabel;
   }
}
