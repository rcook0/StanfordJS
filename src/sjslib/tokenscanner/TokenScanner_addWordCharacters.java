package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class TokenScanner_addWordCharacters extends TokenScannerMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("TokenScanner.addWordCharacters", "S");
      String str = svm.popString();
      this.getTokenScanner(svm, receiver).addWordCharacters(str);
      svm.push(Value.UNDEFINED);
   }
}
