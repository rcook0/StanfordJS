package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GPoint;
import edu.stanford.cs.graphics.GTransform;
import edu.stanford.cs.svm.SVM;

class GTransform_transform extends GTransformMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("GTransform.transform", "O");
         GPoint pt = (GPoint)svm.pop().getValue();
         GTransform t = this.getGTransform(svm, receiver);
         svm.push(Value.createObject(t.transform(pt), "GPoint"));
      } else {
         svm.checkSignature("GTransform.transform", "DD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         GTransform t = this.getGTransform(svm, receiver);
         svm.push(Value.createObject(t.transform(x, y), "GPoint"));
      }

   }
}
