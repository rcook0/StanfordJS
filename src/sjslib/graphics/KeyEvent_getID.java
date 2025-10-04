package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;

class KeyEvent_getID extends KeyEventMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("KeyEvent.getID", "");
      int id = this.getKeyEvent(svm, receiver).getID();
      switch(id) {
      case 400:
         svm.pushInteger(201);
         break;
      case 401:
         svm.pushInteger(202);
         break;
      case 402:
         svm.pushInteger(203);
         break;
      default:
         throw new RuntimeException("Illegal event type");
      }

   }
}
