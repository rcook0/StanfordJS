package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConstant;

class TokenScanner_EOF extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.EOF", "");
      svm.pushInteger(-1);
   }
}
