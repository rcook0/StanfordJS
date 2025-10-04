package edu.stanford.cs.sjslib.util;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.random.RandomGenerator;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class Random_nextBoolean extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 0) {
         svm.checkSignature("Random.nextBoolean", "");
         svm.pushBoolean(RandomGenerator.getInstance().nextBoolean());
      } else {
         svm.checkSignature("Random.nextDouble", "D");
         double p = svm.popDouble();
         svm.pushBoolean(RandomGenerator.getInstance().nextBoolean(p));
      }

   }
}
