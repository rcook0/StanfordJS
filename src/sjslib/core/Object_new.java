package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.svm.SVMObject;

class Object_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Object.new", "");
      svm.push(Value.createObject(new SVMObject(svm), "Object"));
   }
}
