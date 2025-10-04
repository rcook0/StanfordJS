package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Number_parseFloat extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Number.parseFloat", "S");

      try {
         svm.pushDouble(Double.parseDouble(svm.popString()));
      } catch (NumberFormatException var4) {
         svm.pushDouble(Double.NaN);
      }

   }
}
