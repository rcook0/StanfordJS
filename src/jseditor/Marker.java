package edu.stanford.cs.jseditor;

import javax.swing.text.Position;

public class Marker implements Comparable<Marker> {
   private Position pos;

   protected Marker(Position pos) {
      this.pos = pos;
   }

   public int compareTo(Marker marker) {
      return this.pos.getOffset() - marker.pos.getOffset();
   }

   public int getOffset() {
      return this.pos.getOffset();
   }
}
