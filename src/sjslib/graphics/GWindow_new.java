package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSProgram;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class GWindow_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      double width = 500.0D;
      double height = 300.0D;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("GWindow.new", "DD");
         height = svm.popDouble();
         width = svm.popDouble();
      } else {
         svm.checkSignature("GWindow.new", "");
      }

      SJSGWindow gw = ((SJSVM)svm).createGWindow(width, height);
      if (svm.isGlobal("$MainProgram")) {
         gw.setTitle(svm.getGlobal("$MainProgram").getStringValue());
      }

      if (JSProgram.isJavaScript()) {
         svm.getProgram().add(gw, "canvas");
      } else {
         gw.setVisible(true);
      }

      svm.push(Value.createObject(gw, "GWindow"));
   }
}
