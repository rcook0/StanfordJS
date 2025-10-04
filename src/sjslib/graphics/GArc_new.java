package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GArc;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GArc_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      double sweep;
      double start;
      double height;
      double width;
      if (svm.getArgumentCount() == 4) {
         svm.checkSignature("GArc.new", "DDDD");
         sweep = svm.popDouble();
         start = svm.popDouble();
         height = svm.popDouble();
         width = svm.popDouble();
         GArc arc = new GArc(0.0D, 0.0D, width, height, start, sweep);
         svm.push(Value.createObject(arc, "GArc"));
      } else {
         svm.checkSignature("GArc.new", "DDDDDD");
         sweep = svm.popDouble();
         start = svm.popDouble();
         height = svm.popDouble();
         width = svm.popDouble();
         double y = svm.popDouble();
         double x = svm.popDouble();
         GArc arc = new GArc(x, y, width, height, start, sweep);
         svm.push(Value.createObject(arc, "GArc"));
      }

   }
}
