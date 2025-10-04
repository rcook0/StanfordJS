package edu.stanford.cs.controller;

class ControllerStepper implements Runnable {
   private Controller controller;
   private Steppable target;

   public ControllerStepper(Controller controller, Steppable target) {
      this.controller = controller;
      this.target = target;
   }

   public void run() {
      try {
         int oldState = this.controller.getControllerState();
         int nCycles = this.controller.getCycleCount();
         this.target.step();

         while(this.controller.shouldKeepRunning()) {
            --nCycles;
            if (nCycles <= 0) {
               break;
            }

            this.target.step();
         }

         this.controller.stepComplete(oldState);
      } catch (Exception var3) {
         String msg = var3 instanceof RuntimeException ? var3.getMessage() : var3.toString();
         this.controller.signalError(msg);
      }

   }
}
