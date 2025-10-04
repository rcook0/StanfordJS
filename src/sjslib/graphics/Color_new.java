package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.Color;

class Color_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      Value v = null;
      int nArgs = svm.getArgumentCount();
      int type;
      int rgb;
      if (nArgs == 1) {
         v = svm.pop();
         type = v.getType();
         int rgb = false;
         if (type == 73) {
            rgb = v.getIntegerValue();
         } else {
            if (type != 83) {
               throw new RuntimeException("Illegal color specification");
            }

            rgb = Integer.parseInt(v.getStringValue(), 16);
         }

         v = Value.createObject(new Color(rgb), "Color");
      } else {
         int gg;
         if (nArgs == 4) {
            svm.checkSignature("Color.new", "IIII");
            type = svm.popInteger();
            rgb = svm.popInteger();
            gg = svm.popInteger();
            int rr = svm.popInteger();
            v = Value.createObject(new Color(rr, gg, rgb, type), "Color");
         } else {
            svm.checkSignature("Color.new", "III");
            type = svm.popInteger();
            rgb = svm.popInteger();
            gg = svm.popInteger();
            v = Value.createObject(new Color(gg, rgb, type), "Color");
         }
      }

      svm.push(v);
   }
}
