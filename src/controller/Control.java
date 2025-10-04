package edu.stanford.cs.controller;

import java.awt.event.ActionListener;
import javax.swing.event.ChangeListener;

public interface Control extends ChangeListener {
   void setName(String var1);

   String getName();

   void setEnabled(boolean var1);

   boolean isEnabled();

   void setController(Controller var1);

   Controller getController();

   void execute();

   void addActionListener(ActionListener var1);

   void removeActionListener(ActionListener var1);
}
