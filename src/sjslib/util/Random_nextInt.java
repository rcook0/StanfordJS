package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.random.RandomGenerator;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Random_nextInt extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      int n;
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("Random.nextInt", "I");
         n = svm.popInteger();
         svm.pushInteger(RandomGenerator.getInstance().nextInt(n));
      } else {
         svm.checkSignature("Random.nextInt", "II");
         n = svm.popInteger();
         int low = svm.popInteger();
         svm.pushInteger(RandomGenerator.getInstance().nextInt(low, n));
      }

   }
}
