package edu.stanford.cs.jseditor;

public class OffsetRange {
   private int start;
   private int end;

   public OffsetRange(int start, int end) {
      this.start = start;
      this.end = end;
   }

   public boolean isEmpty() {
      return this.start == this.end;
   }

   public int getStart() {
      return this.start;
   }

   public int getEnd() {
      return this.end;
   }
}
