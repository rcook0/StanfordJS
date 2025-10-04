package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRectangle;
import edu.stanford.cs.svm.SVM;

class GRoundRect_setBounds extends GRoundRectMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("GRoundRect.setBounds", "O");
         GRectangle r = (GRectangle)svm.pop().getValue();
         this.getGRoundRect(svm, receiver).setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
      } else {
         svm.checkSignature("GRoundRect.setBounds", "DDDD");
         double height = svm.popDouble();
         double width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         this.getGRoundRect(svm, receiver).setBounds(x, y, width, height);
      }

      svm.push(Value.UNDEFINED);
   }
}
