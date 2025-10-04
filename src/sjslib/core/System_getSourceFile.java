package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.sjs.SJSVM;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;

class System_getSourceFile extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("System.getSourceFile", "");
      svm.pushString(((SJSVM)svm).getSourceFile());
   }
}
