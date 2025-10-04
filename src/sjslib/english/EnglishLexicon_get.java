package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class EnglishLexicon_get extends EnglishLexiconMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("EnglishLexicon.contains", "I");
      int k = svm.popInteger();
      svm.pushString(this.getLexicon(svm, receiver).get(k));
   }
}
