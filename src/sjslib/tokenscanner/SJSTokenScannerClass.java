package edu.stanford.cs.sjslib.tokenscanner;

import edu.stanford.cs.svm.SVMClass;

public class SJSTokenScannerClass extends SVMClass {
   public SJSTokenScannerClass() {
      this.defineMethod("new", new TokenScanner_new());
      this.defineMethod("setInput", new TokenScanner_setInput());
      this.defineMethod("hasMoreTokens", new TokenScanner_hasMoreTokens());
      this.defineMethod("nextToken", new TokenScanner_nextToken());
      this.defineMethod("saveToken", new TokenScanner_saveToken());
      this.defineMethod("getPosition", new TokenScanner_getPosition());
      this.defineMethod("ignoreWhitespace", new TokenScanner_ignoreWhitespace());
      this.defineMethod("ignoreComments", new TokenScanner_ignoreComments());
      this.defineMethod("scanNumbers", new TokenScanner_scanNumbers());
      this.defineMethod("scanStrings", new TokenScanner_scanStrings());
      this.defineMethod("addWordCharacters", new TokenScanner_addWordCharacters());
      this.defineMethod("isWordCharacter", new TokenScanner_isWordCharacter());
      this.defineMethod("addOperator", new TokenScanner_addOperator());
      this.defineMethod("verifyToken", new TokenScanner_verifyToken());
      this.defineMethod("getStringValue", new TokenScanner_getStringValue());
      this.defineMethod("getTokenType", new TokenScanner_getTokenType());
      this.defineMethod("EOF", new TokenScanner_EOF());
      this.defineMethod("SEPARATOR", new TokenScanner_SEPARATOR());
      this.defineMethod("WORD", new TokenScanner_WORD());
      this.defineMethod("NUMBER", new TokenScanner_NUMBER());
      this.defineMethod("STRING", new TokenScanner_STRING());
      this.defineMethod("OPERATOR", new TokenScanner_OPERATOR());
   }
}
