package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class CharacterMethod extends SVMMethod {
   public int popChar(SVM svm) {
      Value v = svm.pop();
      int type = v.getType();
      if (type == 73) {
         return v.getIntegerValue();
      } else if (type == 83) {
         String str = v.getStringValue();
         if (str.length() != 1) {
            throw new RuntimeException("Argument must be a single character");
         } else {
            return str.charAt(0);
         }
      } else {
         throw new RuntimeException("Illegal type in Character class method");
      }
   }
}
