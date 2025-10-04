package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class TokenScanner_STRING extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.STRING", "");
      svm.pushInteger(3);
   }
}
