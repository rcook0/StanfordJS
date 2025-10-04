package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Character_isLetterOrDigit extends CharacterMethod {
   public void execute(SVM svm, Value receiver) {
      if (receiver != null) {
         throw new RuntimeException("Character methods calls must be static");
      } else {
         svm.checkSignature("Character.isLetterOrDigit", "*");
         svm.pushBoolean(Character.isLetterOrDigit(this.popChar(svm)));
      }
   }
}
