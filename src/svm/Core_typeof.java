package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;

class Core_typeof extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("Core.typeof", "*");
      Value obj = svm.pop();
      switch(obj.getType()) {
      case 66:
         svm.pushString("boolean");
         break;
      case 68:
         svm.pushString("number");
         break;
      case 70:
         svm.pushString("function");
         break;
      case 73:
         svm.pushString("number");
         break;
      case 76:
         svm.pushString("number");
         break;
      case 83:
         svm.pushString("string");
         break;
      case 86:
         svm.pushString("undefined");
         break;
      default:
         svm.pushString("object");
      }

   }
}
