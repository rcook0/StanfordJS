package edu.stanford.cs.sjslib.unittest;

import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_unittest extends SVMPackage {
   private String[] DEPENDENCIES = new String[]{"edu.stanford.cs.unittest"};
   private String[] WRAPPER = new String[]{"", "/* Wrapper to read in the unittest package */", "", "var UnitTest = edu_stanford_cs_unittest.UnitTest;", "", "UnitTest.setConsole(console);"};

   public void defineClasses(SVM svm) {
      this.defineClass(svm, "UnitTest", new SJSUnitTestClass());
   }

   public String[] getDependencies() {
      return this.DEPENDENCIES;
   }

   public String[] getWrapper() {
      return this.WRAPPER;
   }
}
