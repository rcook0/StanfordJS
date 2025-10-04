package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.svm.SVMObject;

class Object_create extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      SVMObject obj = new SVMObject(svm);
      int nArgs = svm.getArgumentCount();

      for(int i = 0; i < nArgs; i += 2) {
         Value v = svm.pop();
         String k = svm.popString();
         obj.put(k, v);
      }

      svm.push(Value.createObject(obj, "Object"));
   }
}
