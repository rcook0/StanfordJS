package edu.stanford.cs.java2js;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.JComponent;

public class JSFrame extends JSPanel {
   public static final Color BORDER_COLOR = new Color(6710886);
   private JSTitleBar tb;
   private JComponent contents;

   public JSFrame(JComponent contents, String title) {
      this.contents = contents;
      this.setLayout(new BorderLayout());
      this.add(contents, "Center");
      contents.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
      this.tb = new JSTitleBar(title);
      this.add(this.tb, "North");
      contents.addFocusListener(this.tb);
      this.tb.addMouseListener(new JSTitleBarListener(contents));
   }

   public void setContents(JComponent contents) {
      this.remove(this.contents);
      this.add(contents, "Center");
      this.contents = contents;
   }

   public void setTitle(String title) {
      this.tb.setTitle(title);
   }

   public String getTitle() {
      return this.tb.getTitle();
   }
}
