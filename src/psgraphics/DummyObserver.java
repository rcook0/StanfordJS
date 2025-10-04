package edu.stanford.cs.psgraphics;

import java.awt.Image;
import java.awt.image.ImageObserver;

class DummyObserver implements ImageObserver {
   public boolean imageUpdate(Image img, int infoflags, int x, int y, int width, int height) {
      System.out.println("imageUpdate");
      return false;
   }
}
