package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class EnglishLexicon_contains extends EnglishLexiconMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("EnglishLexicon.contains", "S");
      String str = svm.popString().toLowerCase();
      svm.pushBoolean(this.getLexicon(svm, receiver).contains(str));
   }
}
