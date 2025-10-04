package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class Array_join extends ArrayMethod {
   public void execute(SVM svm, Value receiver) {
      int nArgs = svm.getArgumentCount();
      String separator = ",";
      if (nArgs == 1) {
         svm.checkSignature("Array.indexOf", "S");
         separator = svm.popString();
      }

      String result = "";
      SVMArray array = this.getArray(svm, receiver);

      for(int i = 0; i < array.size(); ++i) {
         if (i > 0) {
            result = result + separator;
         }

         result = result + array.get(i);
      }

      svm.pushString(result);
   }
}
