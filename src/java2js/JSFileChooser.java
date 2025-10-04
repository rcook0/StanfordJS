package edu.stanford.cs.java2js;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;

public class JSFileChooser {
   private ArrayList<ActionListener> loadListeners;
   private ArrayList<ActionListener> saveListeners;
   private Component target;
   private JFileChooser chooser;

   public JSFileChooser(Component target) {
      this.target = target;
      this.chooser = new JFileChooser();
      this.chooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
      this.loadListeners = new ArrayList();
      this.saveListeners = new ArrayList();
   }

   public void setCurrentDirectory(File dir) {
      this.chooser.setCurrentDirectory(dir);
   }

   public void setCurrentDirectory(String path) {
      this.setCurrentDirectory(new File(path));
   }

   public void showOpenDialog() {
      this.showLoadDialog();
   }

   public void showLoadDialog() {
      int result = this.chooser.showOpenDialog(this.target);
      this.fireLoadListeners(this.createActionEvent(result));
   }

   public void showSaveDialog() {
      int result = this.chooser.showSaveDialog(this.target);
      this.fireSaveListeners(this.createActionEvent(result));
   }

   public void setPath(String pathname) {
      this.chooser.setSelectedFile(new File(pathname));
   }

   public String getPath() {
      File selectedFile = this.chooser.getSelectedFile();
      return selectedFile == null ? null : selectedFile.getAbsolutePath();
   }

   public void addLoadListener(ActionListener listener) {
      this.loadListeners.add(listener);
   }

   public void removeLoadListener(ActionListener listener) {
      this.loadListeners.remove(listener);
   }

   public void fireLoadListeners(ActionEvent e) {
      JSEvent.dispatchList(this.loadListeners, e);
   }

   public void addSaveListener(ActionListener listener) {
      this.saveListeners.add(listener);
   }

   public void removeSaveListener(ActionListener listener) {
      this.saveListeners.remove(listener);
   }

   public void fireSaveListeners(ActionEvent e) {
      JSEvent.dispatchList(this.saveListeners, e);
   }

   public void setDialogTitle(String str) {
      this.chooser.setDialogTitle(str);
   }

   public String getDialogTitle() {
      return this.chooser.getDialogTitle();
   }

   private ActionEvent createActionEvent(int code) {
      switch(code) {
      case 0:
         return new ActionEvent(this, 1001, "ApproveSelection");
      case 1:
         return new ActionEvent(this, 1001, "CancelSelection");
      default:
         return new JSErrorEvent(this, "ErrorEvent");
      }
   }
}
