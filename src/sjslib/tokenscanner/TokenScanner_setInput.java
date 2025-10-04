package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_setInput extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.setInput", "S");
      String str = svm.popString();
      this.getTokenScanner(svm, receiver).setInput(str);
      svm.push(Value.UNDEFINED);
   }
}
