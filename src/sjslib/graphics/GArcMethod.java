package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GArc;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GArcMethod extends SVMMethod {
   public GArc getGArc(SVM svm, Value receiver) {
      return receiver == null ? (GArc)svm.pop().getValue() : (GArc)receiver.getValue();
   }
}
