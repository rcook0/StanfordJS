package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSStringClass extends SVMClass {
   public SJSStringClass() {
      this.defineMethod("charAt", new String_charAt());
      this.defineMethod("charCodeAt", new String_charCodeAt());
      this.defineMethod("compareTo", new String_compareTo());
      this.defineMethod("compareToIgnoreCase", new String_compareToIgnoreCase());
      this.defineMethod("concat", new String_concat());
      this.defineMethod("contains", new String_contains());
      this.defineMethod("endsWith", new String_endsWith());
      this.defineMethod("equals", new String_equals());
      this.defineMethod("equalsIgnoreCase", new String_equalsIgnoreCase());
      this.defineMethod("fromCharCode", new String_fromCharCode());
      this.defineMethod("indexOf", new String_indexOf());
      this.defineMethod("isEmpty", new String_isEmpty());
      this.defineMethod("lastIndexOf", new String_lastIndexOf());
      this.defineMethod("length", new String_length());
      this.defineMethod("size", new String_size());
      this.defineMethod("startsWith", new String_startsWith());
      this.defineMethod("substring", new String_substring());
      this.defineMethod("toLowerCase", new String_toLowerCase());
      this.defineMethod("toUpperCase", new String_toUpperCase());
      this.defineMethod("trim", new String_trim());
   }
}
