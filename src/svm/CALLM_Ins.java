package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.SyntaxError;
import edu.stanford.cs.tokenscanner.TokenScanner;

class CALLM_Ins extends SVMNameInstruction {
   public CALLM_Ins() {
      super("CALLM", 97);
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      String token = scanner.nextToken();
      if (scanner.getTokenType(token) != 1) {
         throw new SyntaxError("CALLM requires a class and method name");
      } else {
         scanner.verifyToken(".");
         token = token + "." + scanner.nextToken();
         cv.addWord(1627389952 | cv.stringRef(token));
      }
   }

   public void execute(SVM svm, int addr) {
      SVMStackFrame cf = svm.getCurrentFrame();
      cf.setArgumentCount(svm.getNARGSCount());
      String name = svm.getString(addr);
      int dot = name.lastIndexOf(".");
      String cname = dot == -1 ? this.receiverClass(svm) : name.substring(0, dot);
      String mname = name.substring(dot + 1);
      SVMClass c = SVMClass.forName(cname);
      SVMMethod m = c.getMethod(mname);
      m.execute(svm, (Value)null);
   }

   public String unparse(SVM svm, int addr) {
      return "CALLM " + svm.getString(addr);
   }

   private String receiverClass(SVM svm) {
      int nArgs = svm.getArgumentCount();
      return svm.peekBack(nArgs).getClassName();
   }
}
