package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_tokenscanner extends SVMPackage {
   private String[] DEPENDENCIES = new String[]{"edu.stanford.cs.tokenscanner"};
   private String[] WRAPPER = new String[]{"", "/* Wrapper to read in the tokenscanner package */", "", "var TokenScanner = edu_stanford_cs_tokenscanner.TokenScanner;"};

   public void defineClasses(SVM svm) {
      this.defineClass(svm, "TokenScanner", new SJSTokenScannerClass());
   }

   public String[] getDependencies() {
      return this.DEPENDENCIES;
   }

   public String[] getWrapper() {
      return this.WRAPPER;
   }
}
