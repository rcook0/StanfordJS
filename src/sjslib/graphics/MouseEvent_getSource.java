package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class MouseEvent_getSource extends MouseEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("MouseEvent.getSource", "");
      Object source = this.getMouseEvent(svm, receiver).getSource();
      Value v = new Value(79, source);
      String className = source.getClass().getName();
      className = className.substring(className.lastIndexOf(".") + 1);
      v.setClassName(className);
      svm.push(v);
   }
}
