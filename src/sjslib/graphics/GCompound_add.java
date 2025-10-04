package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GObject;
import edu.stanford.cs.svm.SVM;

class GCompound_add extends GCompoundMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 3) {
         svm.checkSignature("GCompound.add", "ODD");
         double y = svm.popDouble();
         double x = svm.popDouble();
         GObject gobj = (GObject)svm.pop().getValue();
         this.getGCompound(svm, receiver).add(gobj, x, y);
      } else {
         svm.checkSignature("GCompound.add", "O");
         GObject gobj = (GObject)svm.pop().getValue();
         this.getGCompound(svm, receiver).add(gobj);
      }

      svm.push(Value.UNDEFINED);
   }
}
