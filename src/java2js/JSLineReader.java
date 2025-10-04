package edu.stanford.cs.java2js;

public class JSLineReader {
   private String[] lines;
   private int index;
   private int nLines;

   public JSLineReader(String text) {
      this.lines = JSPlatform.splitLines(text);
      this.nLines = this.lines.length;
      if (this.nLines > 0 && this.lines[this.nLines - 1].length() == 0) {
         --this.nLines;
      }

      this.index = 0;
   }

   public JSLineReader(String[] lines) {
      this.lines = lines;
      this.nLines = lines.length;
      this.index = 0;
   }

   public String nextLine() {
      return this.index >= this.nLines ? null : this.lines[this.index++];
   }

   public void saveLine() {
      if (this.index > 0) {
         --this.index;
      }

   }
}
