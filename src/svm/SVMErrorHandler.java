package edu.stanford.cs.svm;

import edu.stanford.cs.java2js.JSErrorHandler;

class SVMErrorHandler implements JSErrorHandler {
   private SVM svm;

   public SVMErrorHandler(SVM svm) {
      this.svm = svm;
   }

   public void error(String msg) {
      this.svm.getConsole().showErrorMessage(msg);
   }
}
