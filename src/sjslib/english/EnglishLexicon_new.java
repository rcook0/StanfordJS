package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.english.EnglishLexicon;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class EnglishLexicon_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 0) {
         svm.push(Value.createObject(new EnglishLexicon(), "EnglishLexicon"));
      } else {
         svm.checkSignature("EnglishLexicon.contains", "I");
         int len = svm.popInteger();
         svm.push(Value.createObject(new EnglishLexicon(len), "EnglishLexicon"));
      }

   }
}
