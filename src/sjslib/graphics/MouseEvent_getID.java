package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class MouseEvent_getID extends MouseEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("MouseEvent.getID", "");
      int id = this.getMouseEvent(svm, receiver).getID();
      switch(id) {
      case 500:
         svm.pushInteger(301);
         break;
      case 501:
         svm.pushInteger(304);
         break;
      case 502:
         svm.pushInteger(305);
         break;
      case 503:
         svm.pushInteger(303);
         break;
      case 504:
      case 505:
      default:
         throw new RuntimeException("Illegal event type");
      case 506:
         svm.pushInteger(302);
      }

   }
}
