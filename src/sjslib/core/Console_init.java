package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.java2js.JSPlatform;
import edu.stanford.cs.java2js.JSProgram;
import edu.stanford.cs.jsconsole.JSConsole;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMConsoleListener;
import edu.stanford.cs.svm.SVMMethod;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;

class Console_init extends SVMMethod {
   public void execute(SVM svm, Value closure) {
      if (!JSPlatform.isJavaScript()) {
         boolean needsConsole = false;
         int width = 500;
         if (svm.isGlobal("CONSOLE_WIDTH")) {
            width = svm.getGlobal("CONSOLE_WIDTH").getIntegerValue();
            needsConsole = true;
         } else if (svm.isGlobal("GWINDOW_WIDTH")) {
            width = svm.getGlobal("GWINDOW_WIDTH").getIntegerValue();
         }

         int height = 200;
         if (svm.isGlobal("CONSOLE_HEIGHT")) {
            height = svm.getGlobal("CONSOLE_HEIGHT").getIntegerValue();
            needsConsole = true;
         }

         if (needsConsole) {
            JSConsole console = new JSConsole();
            console.addActionListener(new SVMConsoleListener(svm));
            svm.setConsole(console);
            svm.setGlobal("console", Value.createObject(console, "Console"));
            console.setPreferredSize(new Dimension(width, height));
            console.setFont(Font.decode("Courier New-Bold-14"));
            JSProgram pgm = svm.getProgram();
            if (pgm == null) {
               JFrame frame = null;
               if (svm.isGlobal("canvas")) {
                  Component comp = (Component)svm.getGlobal("canvas").getValue();
                  frame = (JFrame)comp.getParent();
               } else {
                  frame = new JFrame();
                  frame.setTitle(svm.getGlobalString("TITLE", "Console"));
                  frame.setLayout(new BorderLayout());
               }

               frame.add(console, "Center");
               frame.pack();
               frame.setVisible(true);
            } else {
               pgm.add(console, "console");
            }
         }

      }
   }
}
