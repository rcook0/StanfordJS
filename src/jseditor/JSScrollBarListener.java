package edu.stanford.cs.jseditor;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;

class JSScrollBarListener implements AdjustmentListener {
   private JSEditor editor;
   private JScrollBar scrollBar;

   public JSScrollBarListener(JSEditor editor, JScrollBar scrollBar) {
      this.editor = editor;
      this.scrollBar = scrollBar;
   }

   public void adjustmentValueChanged(AdjustmentEvent e) {
      e = new AdjustmentEvent(this.scrollBar, e.getID(), e.getAdjustmentType(), e.getValue());
      this.editor.fireAdjustmentListeners(e);
   }
}
