package edu.stanford.cs.java2js;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class JSProgram implements Runnable {
   public static final int DEFAULT_DELAY = 100;
   private ArrayList<JFrame> windows;
   private JFrame frame = null;
   private JPanel controls;
   private String currentDirectory;
   private String title;
   private String uid = null;

   public JSProgram() {
      this.setCurrentDirectory(System.getProperty("user.dir"));
      this.windows = new ArrayList();
      JSFile.setCGIServer("https://web.stanford.edu/class/cs54n/cgi-bin");
   }

   public void setUID(String uid) {
      this.uid = uid;
   }

   public String getUID() {
      return this.uid;
   }

   public void setCurrentDirectory(String dir) {
      this.currentDirectory = dir;
   }

   public String getCurrentDirectory() {
      return this.currentDirectory;
   }

   public JFrame createProgramFrame() {
      if (this.frame == null) {
         this.frame = new JFrame();
         this.frame.setLayout(new JSProgramLayout());
         this.controls = new JPanel();
         this.controls.setOpaque(false);
         this.controls.setBackground((Color)null);
      }

      return this.frame;
   }

   public JFrame getFrame() {
      return this.frame;
   }

   public void setLayout(LayoutManager layout) {
      this.createProgramFrame().setLayout(layout);
   }

   public LayoutManager getLayout() {
      return this.frame == null ? null : this.frame.getLayout();
   }

   public void setPreferredSize(Dimension size) {
      if (this.frame == null && !this.windows.isEmpty()) {
         ((JFrame)this.windows.get(0)).setPreferredSize(size);
      } else {
         this.createProgramFrame().setPreferredSize(size);
      }

   }

   public void setMinimumSize(Dimension size) {
      if (this.frame == null && !this.windows.isEmpty()) {
         ((JFrame)this.windows.get(0)).setMinimumSize(size);
      } else {
         this.createProgramFrame().setMinimumSize(size);
      }

   }

   public Dimension getMinimumSize() {
      return this.frame == null && !this.windows.isEmpty() ? ((JFrame)this.windows.get(0)).getMinimumSize() : this.createProgramFrame().getMinimumSize();
   }

   public void setMaximumSize(Dimension size) {
      if (this.frame == null && !this.windows.isEmpty()) {
         ((JFrame)this.windows.get(0)).setMaximumSize(size);
      } else {
         this.createProgramFrame().setMaximumSize(size);
      }

   }

   public Dimension getMaximumSize() {
      return this.frame == null && !this.windows.isEmpty() ? ((JFrame)this.windows.get(0)).getMaximumSize() : this.createProgramFrame().getMaximumSize();
   }

   public void setMenuBar(JMenuBar mbar) {
      this.createProgramFrame().setJMenuBar(mbar);
   }

   public JMenuBar getMenuBar() {
      return this.frame == null ? null : this.frame.getJMenuBar();
   }

   public void setBackground(Color color) {
      Component contentPane = this.createProgramFrame().getContentPane();
      if (contentPane != null) {
         contentPane.setBackground(color);
      }

   }

   public void add(Component c, String id) {
      if (this.frame == null) {
         String cap = id.substring(0, 1).toUpperCase() + id.substring(1);
         JFrame window = new JFrame(cap);
         window.setLayout(new BorderLayout());
         window.add(c, "Center");
         this.windows.add(window);
         window.pack();
         window.setVisible(true);
      } else {
         this.frame.add(c, id);
      }

   }

   public void setTitle(String title) {
      this.title = title;
      if (this.frame == null) {
         if (!this.windows.isEmpty()) {
            ((JFrame)this.windows.get(0)).setTitle(title);
         }
      } else {
         this.frame.setTitle(title);
      }

   }

   public String getTitle() {
      return this.title;
   }

   public void pack() {
      if (this.frame == null) {
         Iterator var2 = this.windows.iterator();

         while(var2.hasNext()) {
            JFrame window = (JFrame)var2.next();
            window.pack();
         }
      } else {
         this.frame.pack();
      }

   }

   public void setVisible(boolean flag) {
      if (this.frame == null) {
         Iterator var3 = this.windows.iterator();

         while(var3.hasNext()) {
            JFrame window = (JFrame)var3.next();
            window.setVisible(flag);
         }
      } else {
         this.frame.setVisible(flag);
      }

   }

   public void addControl(JComponent control) {
      this.createProgramFrame();
      if (this.controls.getComponentCount() == 0) {
         this.frame.add(this.controls, "controls");
      }

      this.controls.add(control);
   }

   public static boolean isJavaScript() {
      return false;
   }

   public static void alert(Object value) {
      if (GraphicsEnvironment.isHeadless()) {
         System.err.println("Alert: " + value);
      } else {
         JOptionPane.showMessageDialog((Component)null, value);
      }

   }

   public static void exit(int status) {
      System.exit(status);
   }

   public void start() {
      this.startAfter(100);
   }

   public void startAfter(int milliseconds) {
      Timer timer = new Timer(milliseconds, new JSStartupListener(this));
      timer.setRepeats(false);
      timer.start();
   }

   public void startAfterLogin() {
      this.setUID(System.getProperty("user.name"));
      this.start();
   }

   public void startAfterSetup(String dirname) {
      this.setUID(System.getProperty("user.name"));
      this.start();
   }

   public void run() {
   }
}
