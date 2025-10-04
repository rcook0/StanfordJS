package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class TokenScanner_WORD extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.WORD", "");
      svm.pushInteger(1);
   }
}
