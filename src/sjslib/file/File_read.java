package edu.stanford.cs.sjslib.file;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMMethod;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

class File_read extends SVMMethod {
   public void execute(SVM svm, Value receiver) {
      svm.checkSignature("File.read", "S");
      String filename = svm.popString();

      try {
         File file = new File(SJSFileClass.expandPathname(svm, filename));
         BufferedReader rd = new BufferedReader(new FileReader(file));
         StringBuilder sb = new StringBuilder();

         while(true) {
            String line = rd.readLine();
            if (line == null) {
               rd.close();
               svm.pushString(sb.toString());
               break;
            }

            sb.append(line);
            sb.append("\n");
         }
      } catch (IOException var8) {
         svm.push(Value.UNDEFINED);
      }

   }
}
