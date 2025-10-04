package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class Character_toLowerCase extends CharacterMethod {
   public void execute(SVM svm, Value receiver) {
      if (receiver != null) {
         throw new RuntimeException("Character methods calls must be static");
      } else {
         svm.checkSignature("Character.toLowerCase", "*");
         Value v = svm.pop();
         if (v.isIntegral()) {
            svm.pushInteger(Character.toLowerCase(v.getIntegerValue()));
         } else if (v.getType() == 83) {
            String str = v.getStringValue();
            if (str.length() != 1) {
               throw new RuntimeException("Value must be a single character");
            }

            svm.pushString(str.toLowerCase());
         }

      }
   }
}
