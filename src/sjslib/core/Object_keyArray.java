package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.svm.SVMObject;
import java.util.Iterator;

class Object_keyArray extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      SVMArray array = new SVMArray();
      SVMObject obj = (SVMObject)svm.pop().getValue();
      Iterator var6 = obj.keySet().iterator();

      while(var6.hasNext()) {
         String key = (String)var6.next();
         array.add(Value.createString(key));
      }

      svm.push(Value.createObject(array, "Array"));
   }
}
