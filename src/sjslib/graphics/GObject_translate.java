package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class GObject_translate extends GObjectMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GObject.translate", "DD");
      double ty = svm.popDouble();
      double tx = svm.popDouble();
      this.getGObject(svm, receiver).translate(tx, ty);
      svm.push(Value.UNDEFINED);
   }
}
