package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_verifyToken extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.verifyToken", "S");
      String expected = svm.popString();

      try {
         this.getTokenScanner(svm, receiver).verifyToken(expected);
      } catch (RuntimeException var5) {
         throw new RuntimeException(var5.getMessage());
      }

      svm.push(Value.UNDEFINED);
   }
}
