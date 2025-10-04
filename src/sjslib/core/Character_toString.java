package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Character_toString extends CharacterMethod {
   public void execute(SVM svm, Value receiver) {
      if (receiver != null) {
         throw new RuntimeException("Character methods calls must be static");
      } else {
         svm.checkSignature("Character.toString", "*");
         svm.pushString("" + (char)this.popChar(svm));
      }
   }
}
