package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_assign extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.assign", "***");
      Value value = svm.pop();
      Value ix = svm.pop();
      Value obj = receiver == null ? svm.pop() : receiver;
      if (obj.getType() == 79) {
         String cname = obj.getClassName();
         if (cname.equals("Array")) {
            SVMArray array = (SVMArray)obj.getValue();
            array.set(ix.getIntegerValue(), value);
         } else if (!cname.equals("Object") && !cname.equals("Map")) {
            String name = ix.getStringValue();
            obj.setProperty(name, value);
         } else {
            SVMObject map = (SVMObject)obj.getValue();
            map.put(ix.getStringValue(), value);
         }
      } else {
         throw new RuntimeException("Illegal selection");
      }
   }
}
