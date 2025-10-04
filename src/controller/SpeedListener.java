package edu.stanford.cs.controller;

import java.awt.Adjustable;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

class SpeedListener implements AdjustmentListener {
   private Controller controller;

   public SpeedListener(Controller controller) {
      this.controller = controller;
   }

   public void adjustmentValueChanged(AdjustmentEvent e) {
      this.controller.setSpeedCallback(((Adjustable)e.getSource()).getValue());
   }
}
