package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class EnglishLexicon_size extends EnglishLexiconMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("EnglishLexicon.contains", "");
      svm.pushInteger(this.getLexicon(svm, receiver).size());
   }
}
