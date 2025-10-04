package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.svm.SVM;

class GCompound_remove extends GCompoundMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GCompound.remove", "O");
      GObject gobj = (GObject)svm.pop().getValue();
      this.getGCompound(svm, receiver).remove(gobj);
      svm.push(Value.UNDEFINED);
   }
}
