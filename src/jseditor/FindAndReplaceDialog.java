package edu.stanford.cs.jseditor;

import edu.stanford.cs.java2js.JSDialog;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FindAndReplaceDialog extends JSDialog {
   private static final int TOP_MARGIN = 4;
   private static final int BOTTOM_MARGIN = 1;
   private static final int LEFT_MARGIN = 4;
   private static final int RIGHT_MARGIN = 4;
   private FindAndReplaceListener listener;
   private JPanel buttons;
   private JTextField findField = new JTextField();
   private JTextField replaceField = new JTextField();

   public FindAndReplaceDialog(JSEditor editor) {
      super(editor, false);
      this.listener = new FindAndReplaceListener(editor, this);
      this.addMouseListener(this.listener);
      this.addMouseMotionListener(this.listener);
      this.initLayout();
      this.pack();
   }

   public String getFindField() {
      return this.findField.getText();
   }

   public String getReplaceField() {
      return this.replaceField.getText();
   }

   protected void initLayout() {
      this.getRootPane().setBorder(BorderFactory.createEmptyBorder(4, 4, 1, 4));
      this.setLayout(new BorderLayout());
      JPanel top = new JPanel();
      top.setLayout(new BorderLayout());
      JPanel labels = new JPanel();
      labels.setLayout(new GridLayout(2, 1));
      JPanel fields = new JPanel();
      fields.setLayout(new BorderLayout());
      this.buttons = new JPanel();
      this.buttons.setLayout(new FlowLayout());
      this.addButton("Find Next");
      this.addButton("Replace");
      this.addButton("Replace All");
      this.addButton("Cancel");
      this.findField.setActionCommand("Find Next");
      this.findField.addActionListener(this.listener);
      this.replaceField.setActionCommand("Find Next");
      this.replaceField.addActionListener(this.listener);
      JLabel label = new JLabel(" Find what: ");
      label.setHorizontalAlignment(4);
      label.setVerticalAlignment(0);
      labels.add(label);
      label = new JLabel(" Replace with: ");
      label.setHorizontalAlignment(4);
      label.setVerticalAlignment(0);
      labels.add(label);
      fields.add(this.findField, "North");
      fields.add(this.replaceField, "South");
      top.add(labels, "West");
      top.add(fields, "Center");
      this.add(top, "Center");
      this.add(this.buttons, "South");
   }

   protected JButton addButton(String label) {
      JButton button = new JButton(label);
      button.addActionListener(this.listener);
      this.buttons.add(button);
      return button;
   }
}
