package edu.stanford.cs.sjs;

import edu.stanford.cs.java2js.JSPlatform;
import edu.stanford.cs.jsconsole.JSConsole;
import edu.stanford.cs.jscontrols.CompileControl;
import edu.stanford.cs.jscontrols.JSControl;
import edu.stanford.cs.jscontrols.LoadControl;
import edu.stanford.cs.jscontrols.NewControl;
import edu.stanford.cs.jscontrols.ResetControl;
import edu.stanford.cs.jscontrols.RunControl;
import edu.stanford.cs.jscontrols.SaveControl;
import edu.stanford.cs.jscontrols.StepControl;
import edu.stanford.cs.jscontrols.StepOverControl;
import edu.stanford.cs.jsmenu.JSMenuItem;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svmtools.SVMEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

public class SJSControlStrip implements ActionListener {
   private ActionEvent lastEvent;
   private ArrayList<JSControl> controls;
   private SJS app;
   private JSConsole console;
   private SVM svm;
   private CompileControl compileControl;
   private LoadControl loadControl;
   private NewControl newControl;
   private ResetControl resetControl;
   private RunControl runControl;
   private SaveControl saveControl;
   private StepControl stepControl;
   private StepOverControl stepOverControl;

   public SJSControlStrip(SJS app) {
      this.app = app;
      this.svm = app.getSVM();
      this.console = app.getConsole();
      this.lastEvent = null;
      this.initControlStrip();
   }

   public SJS getApplication() {
      return this.app;
   }

   public void update() {
      Iterator var2 = this.controls.iterator();

      while(var2.hasNext()) {
         JSControl control = (JSControl)var2.next();
         control.update();
         control.repaint();
      }

   }

   public void actionPerformed(ActionEvent e) {
      if (!this.isDuplicateEvent(e)) {
         String cmd = e.getActionCommand();
         if (cmd.startsWith("@")) {
            this.app.selectEditor(this.app.getEditor(cmd.substring(1)));
         } else if (cmd.equals("Reset")) {
            ((SJSVM)this.svm).clearAllTimers();
            ((SJSVM)this.svm).closeAllGWindows();
            this.console.println();
            this.svm.stopAction();
            this.svm.setState(5);
            this.app.getActiveEditor().dismissErrorDialog();
         } else if (cmd.equals("Compile")) {
            this.app.parseProgram();
         } else {
            String main;
            if (cmd.equals("Run")) {
               if (this.svm.getState() == 4) {
                  this.svm.startAction();
               } else {
                  main = this.app.getMainFunction();
                  this.console.forceInput(main + "();");
               }
            } else if (cmd.equals("Stop")) {
               this.svm.stopAction();
            } else if (cmd.equals("Step")) {
               if (this.svm.getState() == 4) {
                  this.svm.stepAction();
               } else {
                  main = this.app.getMainFunction();
                  this.console.forceInput("debug " + main + "();");
               }
            } else if (cmd.equals("StepOver")) {
               if (this.svm.getState() == 4) {
                  this.svm.callAction();
               } else {
                  main = this.app.getMainFunction();
                  this.console.forceInput("debug " + main + "();");
               }
            } else if (cmd.equals("Load")) {
               this.svm.stopAction();
               this.app.showLoadDialog();
            } else if (cmd.equals("Close")) {
               this.svm.stopAction();
               this.app.closeCurrentFile();
            } else if (cmd.equals("Save")) {
               this.svm.stopAction();
               this.app.showSaveDialog();
            } else if (cmd.equals("Export")) {
               this.svm.stopAction();
               this.app.showExportDialog();
            } else if (cmd.equals("New")) {
               this.svm.stopAction();
               this.app.newFileCommand();
            } else {
               SJSEditor editor;
               if (cmd.equals("Find/Replace")) {
                  editor = this.app.getActiveEditor();
                  editor.showFindAndReplaceDialog();
               } else if (cmd.equals("LargerFont")) {
                  editor = this.app.getActiveEditor();
                  editor.setFont(SVMEditor.largerFont(editor.getFont()));
                  this.console.setFont(SVMEditor.largerFont(this.console.getFont()));
               } else if (cmd.equals("SmallerFont")) {
                  editor = this.app.getActiveEditor();
                  editor.setFont(SVMEditor.smallerFont(editor.getFont()));
                  this.console.setFont(SVMEditor.smallerFont(this.console.getFont()));
               } else if (cmd.equals("ClearConsole")) {
                  this.console.clear();
               }
            }
         }

      }
   }

   protected void initControlStrip() {
      this.controls = new ArrayList();
      this.loadControl = new LoadControl();
      this.loadControl.addActionListener(this);
      this.app.addControl(this.loadControl);
      this.controls.add(this.loadControl);
      this.saveControl = new SaveControl();
      this.saveControl.addActionListener(this);
      this.saveControl.setUpdater(this.app.createSaveUpdater());
      this.app.addControl(this.saveControl);
      this.controls.add(this.saveControl);
      this.newControl = new NewControl();
      this.newControl.addActionListener(this);
      this.app.addControl(this.newControl);
      this.controls.add(this.newControl);
      this.resetControl = new ResetControl();
      this.resetControl.addActionListener(this);
      this.app.addControl(this.resetControl);
      this.controls.add(this.resetControl);
      this.runControl = new RunControl();
      this.runControl.addActionListener(this);
      this.runControl.setUpdater(new RunControlUpdater(this.app));
      this.app.addControl(this.runControl);
      this.controls.add(this.runControl);
      this.stepControl = new StepControl();
      this.stepControl.addActionListener(this);
      this.stepControl.setUpdater(this.app.createStepUpdater());
      this.app.addControl(this.stepControl);
      this.controls.add(this.stepControl);
      this.stepOverControl = new StepOverControl();
      this.stepOverControl.addActionListener(this);
      this.stepOverControl.setUpdater(this.app.createStepUpdater());
      this.app.addControl(this.stepOverControl);
      this.controls.add(this.stepOverControl);
      this.compileControl = new CompileControl();
      this.compileControl.addActionListener(this);
      this.compileControl.setUpdater(this.app.createCompileUpdater());
      this.app.addControl(this.compileControl);
      this.controls.add(this.compileControl);
   }

   public boolean isDuplicateEvent(ActionEvent e) {
      if (!JSPlatform.isJavaScript() && JSPlatform.isMacOSX()) {
         if (this.lastEvent != null && e.getSource() instanceof JSMenuItem && e.getWhen() - this.lastEvent.getWhen() < 100L) {
            this.lastEvent = null;
            return true;
         } else {
            this.lastEvent = e;
            return false;
         }
      } else {
         return false;
      }
   }
}
