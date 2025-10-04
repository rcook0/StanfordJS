package edu.stanford.cs.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class TimerListener implements ActionListener {
   private Controller controller;

   public TimerListener(Controller controller) {
      this.controller = controller;
   }

   public void actionPerformed(ActionEvent e) {
      this.controller.executeTimeStep();
   }
}
