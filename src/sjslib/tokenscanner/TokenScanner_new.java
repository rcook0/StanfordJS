package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.tokenscanner.TokenScanner;

class TokenScanner_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 0) {
         svm.push(Value.createObject(new TokenScanner(), "TokenScanner"));
      } else {
         svm.checkSignature("TokenScanner.new", "S");
         String str = svm.popString();
         svm.push(Value.createObject(new TokenScanner(str), "TokenScanner"));
      }

   }
}
