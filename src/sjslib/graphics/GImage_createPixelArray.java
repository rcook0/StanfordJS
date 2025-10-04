package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMArray;

class GImage_createPixelArray extends GImageMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("GImage.createPixelArray", "II");
      int height = svm.popInteger();
      int width = svm.popInteger();
      SVMArray array = new SVMArray();

      for(int i = 0; i < height; ++i) {
         SVMArray row = new SVMArray();

         for(int j = 0; j < width; ++j) {
            row.add(Value.createInteger(0));
         }

         array.add(Value.createObject(row, "Array"));
      }

      svm.push(Value.createObject(array, "Array"));
   }
}
