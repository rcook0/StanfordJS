package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Character_isLetter extends CharacterMethod {
   public void execute(SVM svm, Value receiver) {
      if (receiver != null) {
         throw new RuntimeException("Character methods calls must be static");
      } else {
         svm.checkSignature("Character.isLetter", "*");
         svm.pushBoolean(Character.isLetter(this.popChar(svm)));
      }
   }
}
