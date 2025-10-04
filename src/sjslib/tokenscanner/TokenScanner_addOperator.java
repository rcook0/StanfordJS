package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_addOperator extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.addOperator", "S");
      String op = svm.popString();
      this.getTokenScanner(svm, receiver).addOperator(op);
      svm.push(Value.UNDEFINED);
   }
}
