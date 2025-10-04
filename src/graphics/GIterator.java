package edu.stanford.cs.graphics;

import java.util.Iterator;

class GIterator implements Iterator<GObject> {
   private GContainer cont;
   private int dir;
   private int index;
   private int nElements;

   public GIterator(GContainer container, int direction) {
      switch(direction) {
      case 0:
      case 1:
         this.dir = direction;
         this.cont = container;
         this.index = 0;
         this.nElements = container.getElementCount();
         return;
      default:
         throw new RuntimeException("Illegal direction for iterator");
      }
   }

   public boolean hasNext() {
      return this.index < this.nElements;
   }

   public GObject next() {
      return this.dir == 1 ? this.cont.getElement(this.nElements - this.index++ - 1) : this.cont.getElement(this.index++);
   }

   public GObject nextElement() {
      return this.next();
   }

   public void remove() {
      if (this.dir == 1) {
         this.cont.remove(this.cont.getElement(this.nElements - --this.index - 1));
      } else {
         this.cont.remove(this.cont.getElement(--this.index));
      }

      --this.nElements;
   }
}
