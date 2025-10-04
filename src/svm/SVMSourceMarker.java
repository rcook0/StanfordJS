package edu.stanford.cs.svm;

public class SVMSourceMarker {
   private String line;
   private int index;

   public SVMSourceMarker(String line, int index) {
      this.line = line;
      this.index = index;
   }

   public String getSourceLine() {
      return this.line;
   }

   public int getStartingIndex() {
      return this.index;
   }
}
