package edu.stanford.cs.sjslib.english;

import edu.stanford.cs.svm.SVMClass;

public class SJSEnglishLexiconClass extends SVMClass {
   public SJSEnglishLexiconClass() {
      this.defineMethod("new", new EnglishLexicon_new());
      this.defineMethod("contains", new EnglishLexicon_contains());
      this.defineMethod("containsPrefix", new EnglishLexicon_containsPrefix());
      this.defineMethod("size", new EnglishLexicon_size());
      this.defineMethod("get", new EnglishLexicon_get());
   }
}
