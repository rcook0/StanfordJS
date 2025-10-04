package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSCharacterClass extends SVMClass {
   public SJSCharacterClass() {
      this.defineMethod("digit", new Character_digit());
      this.defineMethod("forDigit", new Character_forDigit());
      this.defineMethod("getNumericValue", new Character_getNumericValue());
      this.defineMethod("isDigit", new Character_isDigit());
      this.defineMethod("isLetter", new Character_isLetter());
      this.defineMethod("isLetterOrDigit", new Character_isLetterOrDigit());
      this.defineMethod("isLowerCase", new Character_isLowerCase());
      this.defineMethod("isUpperCase", new Character_isUpperCase());
      this.defineMethod("isWhitespace", new Character_isWhitespace());
      this.defineMethod("toUpperCase", new Character_toUpperCase());
      this.defineMethod("toLowerCase", new Character_toLowerCase());
      this.defineMethod("toString", new Character_toString());
   }
}
