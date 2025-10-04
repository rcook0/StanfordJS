package edu.stanford.cs.controller;

import edu.stanford.cs.java2js.JSErrorHandler;
import java.awt.Adjustable;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Controller {
   public static final int INITIAL = 0;
   public static final int RUNNING = 1;
   public static final int STEPPING = 2;
   public static final int CALLING = 3;
   public static final int STOPPED = 4;
   public static final int FINISHED = 5;
   public static final int WAITING = 6;
   public static final int ERROR = 7;
   private static final int MAX_CYCLE_COUNT = 10000;
   private static final int SLOW_DELAY = 500;
   private static final int FAST_DELAY = 5;
   private ActionListener timerListener;
   private Adjustable speedControl = null;
   private ArrayList<ChangeListener> listeners = new ArrayList();
   private HashMap<String, Control> controlTable = new HashMap();
   private JSErrorHandler errorHandler = null;
   private SpeedListener speedListener;
   private Steppable target = null;
   private String errorMessage;
   private Timer timer = null;
   private boolean deferExecution = true;
   private int speed;
   private int controllerState = 0;
   private int callDepth;

   public void setTarget(Steppable obj) {
      this.target = obj;
      this.timerListener = this.createTimerListener();
      this.timer = new Timer(0, this.timerListener);
      this.timer.setRepeats(false);
      this.speedListener = new SpeedListener(this);
      this.setSpeed(50);
   }

   public Steppable getTarget() {
      return this.target;
   }

   public void addControl(Control control) {
      this.controlTable.put(control.getName(), control);
      control.setController(this);
      this.addChangeListener(control);
      if (control.getName().equals("Speed")) {
         this.speedControl = (Adjustable)control;
         this.speedControl.addAdjustmentListener(this.speedListener);
         this.setSpeed(this.speedControl.getValue());
      }

   }

   public Control getControl(String name) {
      return (Control)this.controlTable.get(name);
   }

   public void setControllerState(int state) {
      this.controllerState = state;
      this.fireChangeListeners();
   }

   public int getControllerState() {
      return this.controllerState;
   }

   public void setDeferredExecution(boolean flag) {
      this.deferExecution = flag;
   }

   public boolean isDeferredExecution() {
      return this.deferExecution;
   }

   public static String getStateName(int state) {
      switch(state) {
      case 0:
         return "INITIAL";
      case 1:
         return "RUNNING";
      case 2:
         return "STEPPING";
      case 3:
         return "CALLING";
      case 4:
         return "STOPPED";
      case 5:
         return "FINISHED";
      case 6:
         return "WAITING";
      case 7:
         return "ERROR";
      default:
         return "???";
      }
   }

   public boolean isRunning() {
      switch(this.controllerState) {
      case 1:
      case 2:
      case 3:
         return true;
      default:
         return false;
      }
   }

   public void startAction() {
      this.start(1);
   }

   public void stopAction() {
      if (this.isRunning()) {
         this.stop(4);
      }

   }

   public void stepAction() {
      this.start(2);
   }

   public void callAction() {
      if (this.target.isCallable()) {
         this.callDepth = this.target.getStackDepth();
         this.start(3);
      } else {
         this.start(2);
      }

   }

   public static int speedToTimerDelay(int speed) {
      return speed > 90 ? 0 : (int)(500.0D + (double)(-495 * speed) / 90.0D);
   }

   public static int speedToCycleCount(int speed) {
      return speed < 90 ? 1 : Math.min(10000, 1 << speed - 90);
   }

   public void setSpeed(int speed) {
      if (this.speedControl == null) {
         this.setSpeedCallback(speed);
      } else {
         this.speedControl.setValue(speed);
      }

   }

   public int getTimerDelay() {
      return speedToTimerDelay(this.speed);
   }

   public int getCycleCount() {
      return speedToCycleCount(this.speed);
   }

   protected ActionListener createTimerListener() {
      return new TimerListener(this);
   }

   protected void setSpeedCallback(int speed) {
      this.speed = speed;
      if (this.timer != null) {
         this.timer.setInitialDelay(speedToTimerDelay(speed));
      }

   }

   public int getSpeed() {
      return this.speed;
   }

   public void addChangeListener(ChangeListener listener) {
      this.listeners.add(listener);
   }

   public void removeChangeListener(ChangeListener listener) {
      int index = this.listeners.indexOf(listener);
      if (index >= 0) {
         this.listeners.remove(index);
      }

   }

   public void update() {
      this.fireChangeListeners();
   }

   public void signalError(String msg) {
      this.setErrorMessage(msg);
      this.setControllerState(7);
      if (this.errorHandler != null) {
         this.errorHandler.error(msg);
      }

   }

   public void setErrorHandler(JSErrorHandler handler) {
      this.errorHandler = handler;
   }

   public JSErrorHandler getErrorHandler() {
      return this.errorHandler;
   }

   public String getErrorMessage() {
      return this.errorMessage;
   }

   public void setErrorMessage(String msg) {
      this.errorMessage = msg;
   }

   public void executeTimeStep() {
      try {
         this.stepTarget();
      } catch (Exception var3) {
         String msg = var3 instanceof RuntimeException ? var3.getMessage() : var3.toString();
         if (msg == null) {
            msg = var3.toString();
         }

         this.signalError(msg);
      }

   }

   public void stop(int state) {
      this.setControllerState(state);
   }

   public void start(int state) {
      this.setControllerState(state);
      this.timer.restart();
   }

   public int getCallDepth() {
      return this.callDepth;
   }

   private void fireChangeListeners() {
      ChangeEvent e = new ChangeEvent(this);
      Iterator var3 = this.listeners.iterator();

      while(var3.hasNext()) {
         ChangeListener listener = (ChangeListener)var3.next();
         listener.stateChanged(e);
      }

   }

   private void stepTarget() {
      if (this.deferExecution) {
         SwingUtilities.invokeLater(new ControllerStepper(this, this.target));
      } else {
         int oldState = this.controllerState;
         this.target.step();
         this.stepComplete(oldState);
      }

   }

   public boolean shouldKeepRunning() {
      switch(this.controllerState) {
      case 1:
         return true;
      case 2:
      default:
         return false;
      case 3:
         return this.target.getStackDepth() > this.callDepth;
      }
   }

   public void stepComplete(int oldState) {
      switch(oldState) {
      case 1:
         if (this.controllerState == 1) {
            this.timer.restart();
         }
         break;
      case 2:
         this.stopAction();
         break;
      case 3:
         if (this.target.getStackDepth() <= this.callDepth) {
            this.stopAction();
         } else if (this.controllerState == 3) {
            this.timer.restart();
         }
      }

   }
}
