package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GImage;
import edu.stanford.cs.java2js.JSImage;
import edu.stanford.cs.sjs.SJS;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMMethod;

class GImage_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      double x = 0.0D;
      double y = 0.0D;
      if (svm.getArgumentCount() == 3) {
         svm.checkSignature("GImage.new", "SDD");
         y = svm.popDouble();
         x = svm.popDouble();
      } else {
         svm.checkSignature("GImage.new", "*");
      }

      Value v = svm.pop();
      if (v.getType() == 83) {
         String url = v.getStringValue();
         String cwd = ".";
         SJS app = ((SJSVM)svm).getApplication();
         if (app != null) {
            cwd = app.getCurrentDirectory();
         }

         JSImage jsi = new SJSImage(cwd, url);
         svm.push(Value.createObject(new GImage(jsi, x, y), "GImage"));
         if (!JSImage.isComplete(jsi)) {
            JSImage.addActionListener(jsi, new GImageListener(svm));
            svm.setState(6);
         }
      } else {
         if (!(v.getValue() instanceof SVMArray)) {
            throw new RuntimeException("Illegal type in GImage constructor");
         }

         SVMArray array = (SVMArray)v.getValue();
         int height = array.size();
         int width = ((SVMArray)((Value)array.get(0)).getValue()).size();
         int[][] pixels = JSImage.createPixelArray(width, height);

         for(int i = 0; i < height; ++i) {
            SVMArray row = (SVMArray)((Value)array.get(i)).getValue();

            for(int j = 0; j < width; ++j) {
               pixels[i][j] = ((Value)row.get(j)).getIntegerValue();
            }
         }

         GImage gi = new GImage(pixels);
         svm.push(Value.createObject(gi, "GImage"));
         if (!JSImage.isComplete(gi.getImage())) {
            JSImage.addActionListener(gi.getImage(), new GImageListener(svm));
            svm.setState(6);
         }
      }

   }
}
