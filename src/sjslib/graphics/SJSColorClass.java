package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSColorClass extends SVMClass {
   public SJSColorClass() {
      this.defineMethod("new", new Color_new());
      this.defineMethod("getRed", new Color_getRed());
      this.defineMethod("getGreen", new Color_getGreen());
      this.defineMethod("getBlue", new Color_getBlue());
      this.defineMethod("getAlpha", new Color_getAlpha());
      this.defineMethod("BLACK", new Color_BLACK());
      this.defineMethod("BLUE", new Color_BLUE());
      this.defineMethod("CYAN", new Color_CYAN());
      this.defineMethod("DARK_GRAY", new Color_DARK_GRAY());
      this.defineMethod("GRAY", new Color_GRAY());
      this.defineMethod("GREEN", new Color_GREEN());
      this.defineMethod("LIGHT_GRAY", new Color_LIGHT_GRAY());
      this.defineMethod("MAGENTA", new Color_MAGENTA());
      this.defineMethod("ORANGE", new Color_ORANGE());
      this.defineMethod("PINK", new Color_PINK());
      this.defineMethod("RED", new Color_RED());
      this.defineMethod("WHITE", new Color_WHITE());
      this.defineMethod("YELLOW", new Color_YELLOW());
   }
}
