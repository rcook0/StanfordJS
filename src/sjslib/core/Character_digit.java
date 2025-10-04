package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Character_digit extends CharacterMethod {
   public void execute(SVM svm, Value receiver) {
      if (receiver != null) {
         throw new RuntimeException("Character methods calls must be static");
      } else {
         svm.checkSignature("Character.digit", "*I");
         int radix = svm.popInteger();
         svm.pushInteger(Character.digit(this.popChar(svm), radix));
      }
   }
}
