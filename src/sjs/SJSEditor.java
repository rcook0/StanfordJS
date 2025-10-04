package edu.stanford.cs.sjs;

import edu.stanford.cs.svmtools.SVMEditor;
import java.awt.Dimension;

public class SJSEditor extends SVMEditor {
   public SJSEditor() {
      this.addWordCharacters("_");
      this.setFont(SJSC.EDITOR_FONT);
   }

   public Dimension getPreferredSize() {
      return new Dimension(600, 556);
   }
}
