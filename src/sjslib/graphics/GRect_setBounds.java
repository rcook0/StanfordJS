package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRectangle;
import edu.stanford.cs.svm.SVM;

class GRect_setBounds extends GRectMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("GRect.setBounds", "O");
         GRectangle r = (GRectangle)svm.pop().getValue();
         this.getGRect(svm, receiver).setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
      } else {
         svm.checkSignature("GRect.setBounds", "DDDD");
         double height = svm.popDouble();
         double width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         this.getGRect(svm, receiver).setBounds(x, y, width, height);
      }

   }
}
