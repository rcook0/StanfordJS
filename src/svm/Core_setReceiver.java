package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_setReceiver extends SVMConstant {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.setReceiver", "*");
      receiver = svm.pop();
      svm.getCurrentFrame().setReceiver(receiver);
   }
}
