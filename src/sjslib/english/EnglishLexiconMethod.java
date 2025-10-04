package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.english.EnglishLexicon;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class EnglishLexiconMethod extends SVMMethod {
   public EnglishLexicon getLexicon(SVM svm, Value receiver) {
      return receiver == null ? (EnglishLexicon)svm.pop().getValue() : (EnglishLexicon)receiver.getValue();
   }
}
