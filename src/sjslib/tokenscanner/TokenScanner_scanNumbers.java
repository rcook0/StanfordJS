package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_scanNumbers extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.scanNumbers", "");
      this.getTokenScanner(svm, receiver).scanNumbers();
      svm.push(Value.UNDEFINED);
   }
}
