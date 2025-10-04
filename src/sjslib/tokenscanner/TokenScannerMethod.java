package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.tokenscanner.TokenScanner;

abstract class TokenScannerMethod extends SVMMethod {
   public TokenScanner getTokenScanner(SVM svm, Value receiver) {
      return receiver == null ? (TokenScanner)svm.pop().getValue() : (TokenScanner)receiver.getValue();
   }
}
