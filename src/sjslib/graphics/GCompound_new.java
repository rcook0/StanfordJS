package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GCompound;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GCompound_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GCompound.new", "");
      svm.push(Value.createObject(new GCompound(), "GCompound"));
   }
}
