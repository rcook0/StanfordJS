package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GRectangle;
import edu.stanford.cs.svm.SVM;

class GOval_setBounds extends GOvalMethod {
   public void execute(SVM svm, Value receiver) {
      if (svm.getArgumentCount() == 1) {
         svm.checkSignature("GOval.setBounds", "O");
         GRectangle r = (GRectangle)svm.pop().getValue();
         this.getGOval(svm, receiver).setBounds(r.getX(), r.getY(), r.getWidth(), r.getHeight());
      } else {
         svm.checkSignature("GOval.setBounds", "DDDD");
         double height = svm.popDouble();
         double width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         this.getGOval(svm, receiver).setBounds(x, y, width, height);
      }

   }
}
