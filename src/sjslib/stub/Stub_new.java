package edu.stanford.cs.sjslib.stub;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;
import edu.stanford.cs.svm.SVMFunctionClosure;
import edu.stanford.cs.svm.SVMMethod;
import edu.stanford.cs.svm.SVMStackFrame;

class Stub_new extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      int entry = 0;
      if (svm.getArgumentCount() == 2) {
         svm.checkSignature("Stub.new", "*I");
         entry = svm.popInteger();
      } else {
         svm.checkSignature("GWindow.new", "*");
      }

      SVMArray array = (SVMArray)svm.pop().getValue();
      int n = array.size();
      int[] code = new int[n];

      for(int i = 0; i < n; ++i) {
         code[i] = ((Value)array.get(i)).getIntegerValue();
      }

      SVMFunctionClosure fc = new SVMFunctionClosure(code, entry, (SVMStackFrame)null);
      svm.push(Value.createObject(fc, "FunctionClosure"));
   }
}
