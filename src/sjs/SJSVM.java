package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.graphics.GWindow;
import edu.stanford.cs.java2js.JSFrame;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMFunctionClosure;
import edu.stanford.cs.svm.SVMObject;
import edu.stanford.cs.svm.SVMStackFrame;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.Timer;

public class SJSVM extends SVM {
   private ArrayList<SJSGWindow> windows;
   private SJS app;
   private JFrame frame;
   private JSFrame embeddedFrame;
   private SJSGWindow embeddedGWindow;
   private HashMap<Integer, Timer> timers;

   public SJSVM(SJS app) {
      this.app = app;
      this.setTarget(this);
      this.setStepMode(1);
      this.frame = null;
      this.embeddedFrame = null;
      this.embeddedGWindow = null;
      this.timers = new HashMap();
      this.windows = new ArrayList();
      this.setSpeed(100);
   }

   public SJS getApplication() {
      return this.app;
   }

   public void setFrame(JFrame frame) {
      this.frame = frame;
   }

   public JFrame getFrame() {
      return this.frame;
   }

   public String getSourceFile() {
      int word = this.get(0);
      return (word >> 24 & 255) != 18 ? null : this.getString(word & 16777215);
   }

   public int addTimer(Timer timer) {
      int id = this.getFirstFreeId();
      this.timers.put(id, timer);
      return id;
   }

   public void clearTimer(int id) {
      Timer timer = (Timer)this.timers.get(id);
      if (timer == null) {
         throw new RuntimeException("Illegal timer id");
      } else {
         timer.stop();
         this.timers.remove(id);
      }
   }

   public void removeTimer(Timer timer) {
      Iterator var3 = this.timers.keySet().iterator();

      while(var3.hasNext()) {
         int id = (Integer)var3.next();
         if (this.timers.get(id) == timer) {
            this.timers.remove(id);
            return;
         }
      }

   }

   public boolean timersActive() {
      return !this.timers.isEmpty();
   }

   public void clearAllTimers() {
      Iterator var2 = this.timers.keySet().iterator();

      while(var2.hasNext()) {
         int id = (Integer)var2.next();
         Timer timer = (Timer)this.timers.get(id);
         timer.stop();
      }

      this.timers.clear();
   }

   public SJSGWindow createGWindow(double width, double height) {
      SJSGWindow window = new SJSGWindow(this);
      if (this.app != null && width <= 500.0D && height <= 300.0D) {
         this.embeddedGWindow = window;
         window.createJSFrame(width, height);
         this.embeddedFrame = window.getJSFrame();
         this.frame.add(this.embeddedFrame, "gwindow");
         this.frame.validate();
      } else {
         window.createFrame(width, height);
      }

      this.windows.add(window);
      return window;
   }

   public void closeGWindow(SJSGWindow window) {
      this.closeOneGWindow(window);
      this.windows.remove(window);
      if (this.getState() == 5 && this.app != null) {
         this.app.signalFinished();
      }

   }

   public void closeAllGWindows() {
      Iterator var2 = this.windows.iterator();

      while(var2.hasNext()) {
         SJSGWindow window = (SJSGWindow)var2.next();
         this.closeOneGWindow(window);
      }

      this.windows.clear();
      this.embeddedGWindow = null;
   }

   public boolean hasOpenFrames() {
      Iterator var2 = this.windows.iterator();

      while(var2.hasNext()) {
         SJSGWindow window = (SJSGWindow)var2.next();
         if (window.getFrame() != null) {
            return true;
         }
      }

      return false;
   }

   public boolean isFinished() {
      if (this.getState() == 7) {
         return true;
      } else if (this.getState() != 5) {
         return false;
      } else if (!this.timersActive() && !this.hasOpenFrames()) {
         if (this.embeddedGWindow == null) {
            return true;
         } else {
            return !this.embeddedGWindow.hasActiveListeners();
         }
      } else {
         return false;
      }
   }

   protected void stepHook() {
      if (this.app != null) {
         this.app.statementHook(this.getStatementOffset());
      }

   }

   public String stringify(Value obj) {
      if (obj.getType() == 79) {
         String cname = obj.getClassName();
         Value v = null;
         if (!cname.equals("Object") && !cname.equals("Map")) {
            v = obj.getProperty("toString");
         } else {
            SVMObject map = (SVMObject)obj.getValue();
            v = (Value)map.get("toString");
         }

         if (v != null && v.getClassName().equals("FunctionClosure")) {
            SVMFunctionClosure fc = (SVMFunctionClosure)v.getValue();
            int oldState = this.getState();
            int oldPC = this.getPC();
            this.pushFrame();
            SVMStackFrame cf = this.getCurrentFrame();
            cf.setThis(obj);
            cf.setReturnAddress(-1);
            cf.setFrameLink(fc.getFrame());
            cf.setArgumentCount(0);
            int[] code = fc.getCode();
            if (code != null) {
               this.setCode(code);
            }

            this.setPC(fc.getAddress());
            this.run();
            this.setState(oldState);
            this.setPC(oldPC);
            return this.popString();
         }
      }

      return obj.toString();
   }

   private void closeOneGWindow(SJSGWindow window) {
      GWindow gw = window.getFrame();
      if (gw == null) {
         this.frame.remove(window.getJSFrame());
         this.frame.validate();
      } else {
         gw.setVisible(false);
         this.embeddedGWindow = null;
      }

   }

   private int getFirstFreeId() {
      int id;
      for(id = 1; this.timers.containsKey(id); ++id) {
      }

      return id;
   }
}
