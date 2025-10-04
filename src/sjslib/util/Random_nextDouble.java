package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.random.RandomGenerator;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Random_nextDouble extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 0) {
         svm.checkSignature("Random.nextDouble", "");
         svm.pushDouble(RandomGenerator.getInstance().nextDouble());
      } else {
         svm.checkSignature("Random.nextDouble", "DD");
         double high = svm.popDouble();
         double low = svm.popDouble();
         svm.pushDouble(RandomGenerator.getInstance().nextDouble(low, high));
      }

   }
}
