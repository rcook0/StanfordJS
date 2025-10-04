package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_english extends SVMPackage {
   private String[] DEPENDENCIES = new String[]{"edu.stanford.cs.english"};
   private String[] WRAPPER = new String[]{"", "/* Wrapper to read in the english package */", "", "var EnglishLexicon = edu_stanford_cs_english.EnglishLexicon;"};

   public void defineClasses(SVM svm) {
      this.defineClass(svm, "EnglishLexicon", new SJSEnglishLexiconClass());
   }

   public String[] getDependencies() {
      return this.DEPENDENCIES;
   }

   public String[] getWrapper() {
      return this.WRAPPER;
   }
}
