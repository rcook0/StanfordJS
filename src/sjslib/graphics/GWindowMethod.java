package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSGWindow;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

abstract class GWindowMethod extends SVMMethod {
   public SJSGWindow getGWindow(SVM svm, Value receiver) {
      if (receiver == null) {
         throw new RuntimeException("No window specified");
      } else {
         return (SJSGWindow)receiver.getValue();
      }
   }
}
