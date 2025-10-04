package edu.stanford.cs.jsconsole;

import java.awt.event.ActionListener;

public interface NBConsole {
   void clear();

   boolean isSwingComponent();

   void print(Object var1);

   void println();

   void println(Object var1);

   void log(Object var1);

   void showErrorMessage(String var1);

   void requestInput(String var1);

   void forceInput(String var1);

   void addActionListener(ActionListener var1);

   void startConsoleLog();

   String endConsoleLog();
}
