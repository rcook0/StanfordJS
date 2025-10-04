package edu.stanford.cs.programeditor;

import edu.stanford.cs.java2js.JSErrorDialog;
import edu.stanford.cs.java2js.JSImage;
import edu.stanford.cs.jseditor.EditorMode;
import edu.stanford.cs.jseditor.HighlighterKey;
import edu.stanford.cs.jseditor.JSEditor;
import edu.stanford.cs.jseditor.Marker;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.TreeSet;
import javax.swing.SwingUtilities;

public class ProgramEditor extends JSEditor {
   private static final String BREAKPOINT_IMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABEAAAARCAYAAAA7bUf6AAAAPklEQVR42mM48/8/A6UYqyBQ+D8uTJQh+AzAZRDJBmAziCwD0A0i2wBkg0YNGfSGUCWdUC3FUi3vUC0Xk4MBwEuSveqvlaYAAAAASUVORK5CYII=";
   private static final String HAND_IMAGE = "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAPCAYAAAD6Ud/mAAABPElEQVR42uWUoZKDQAyGeSLeYZ+AF1iNrsXikLi6U6gqFApThSgGg6mowSAQGATi778X5ijtwnF3MzXHTAa6k+RL/mTrXADnHeb8T1DoGU/AVTRHvjeNfqsga4A7vQmqWnw95w8518l8No7A0APNjb5nQJk4fwa+gph0YKDHb0XHtGCSz0xAoIF6kMRVIv5RBrQsomkEcq2BIpf4x86soH6UisoRiyfR4pNd5XfkrkjH8yBayrcJKjpWyaTdILIk/vpcdi+DtSOCwqeqj4VAa76jw19ASjp4lK6fOjNnXgDEwVxURmDIGEdvw6xbV3K4vllnJjqmBE8z6jjogxKf3EhKvzTe15UV5E5y6WC+N/EJuJHYlkspfQ49Ur8EaWofPm2NsixAXtFO053ZK91Pzdwp07nyvl+Et/7X3QHwFTTGgRQjRQAAAABJRU5ErkJggg==";
   public static final int BREAKPOINT_DX = -9;
   public static final int BREAKPOINT_DY = -11;
   public static final int ERROR_DX = 2;
   public static final int ERROR_DY = 4;
   public static final int HAND_DX = -13;
   public static final int HAND_DY = -6;
   private HighlighterKey lastErrorKey;
   private JSErrorDialog errorDialog;
   private JSImage breakpointImage;
   private JSImage handImage;
   private PEAnnotationPane annotationPane = new PEAnnotationPane(this);
   private TreeSet<Marker> breakpoints;
   private boolean needsParsing;
   private int currentLine;

   public ProgramEditor() {
      this.add(this.annotationPane, "West");
      this.addChangeListener(this.annotationPane);
      this.addAdjustmentListener(this.annotationPane);
      this.breakpoints = new TreeSet();
      this.errorDialog = null;
      this.lastErrorKey = null;
      this.needsParsing = false;
      this.currentLine = 0;
      this.breakpointImage = new JSImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABEAAAARCAYAAAA7bUf6AAAAPklEQVR42mM48/8/A6UYqyBQ+D8uTJQh+AzAZRDJBmAziCwD0A0i2wBkg0YNGfSGUCWdUC3FUi3vUC0Xk4MBwEuSveqvlaYAAAAASUVORK5CYII=");
      this.handImage = new JSImage("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABoAAAAPCAYAAAD6Ud/mAAABPElEQVR42uWUoZKDQAyGeSLeYZ+AF1iNrsXikLi6U6gqFApThSgGg6mowSAQGATi778X5ijtwnF3MzXHTAa6k+RL/mTrXADnHeb8T1DoGU/AVTRHvjeNfqsga4A7vQmqWnw95w8518l8No7A0APNjb5nQJk4fwa+gph0YKDHb0XHtGCSz0xAoIF6kMRVIv5RBrQsomkEcq2BIpf4x86soH6UisoRiyfR4pNd5XfkrkjH8yBayrcJKjpWyaTdILIk/vpcdi+DtSOCwqeqj4VAa76jw19ASjp4lK6fOjNnXgDEwVxURmDIGEdvw6xbV3K4vllnJjqmBE8z6jjogxKf3EhKvzTe15UV5E5y6WC+N/EJuJHYlkspfQ49Ur8EaWofPm2NsixAXtFO053ZK91Pzdwp07nyvl+Et/7X3QHwFTTGgRQjRQAAAABJRU5ErkJggg==");
   }

   public void setCurrentLine(int k) {
      this.currentLine = k;
      this.ensureLineVisible(k);
      this.repaint();
   }

   public int getCurrentLine() {
      return this.currentLine;
   }

   public void showErrorDialog(String msg, int k) {
      if (this.errorDialog == null) {
         this.errorDialog = this.createErrorDialog();
      }

      if (this.lastErrorKey != null) {
         this.removeBackgroundHighlight(this.lastErrorKey);
         this.lastErrorKey = null;
      }

      if (k != 0) {
         this.lastErrorKey = this.addBackgroundHighlight(k, this.errorDialog.getBackground());
         this.ensureLineVisible(k);
      }

      this.repaint();
      this.errorDialog.setErrorMessage(msg);
      Dimension psize = this.errorDialog.getPreferredSize();
      this.errorDialog.setSize(psize.width, psize.height);
      Point pt = new Point((this.getWidth() - this.errorDialog.getWidth()) / 2, (this.getHeight() - this.errorDialog.getHeight()) / 2);
      this.errorDialog.setLocation(this.errorDialog.getWindowCoordinates(pt));
      this.errorDialog.setVisible(true);
   }

   public void clearErrorHighlight() {
      if (this.lastErrorKey != null) {
         this.removeBackgroundHighlight(this.lastErrorKey);
      }

   }

   public void dismissErrorDialog() {
      if (this.errorDialog != null) {
         this.errorDialog.setVisible(false);
      }

      this.requestFocus();
   }

   public boolean isErrorDialogShowing() {
      return this.errorDialog != null && this.errorDialog.isVisible();
   }

   public void updateHighlights() {
      EditorMode mode = this.getEditorMode();
      if (mode != null) {
         boolean needsSaving = this.isSaveNeeded();
         this.setUndoableFlag(false);
         mode.runTokenizer(this);
         this.setSaveNeeded(needsSaving);
         this.setUndoableFlag(true);
      }

   }

   public void setBreakpoint(int k) {
      this.breakpoints.add(this.createMarker(this.getLineStart(k)));
      this.annotationPane.repaint();
   }

   public void removeBreakpoint(int k) {
      this.breakpoints.remove(this.createMarker(this.getLineStart(k)));
      this.annotationPane.repaint();
   }

   public void removeAllBreakpoints() {
      this.breakpoints.clear();
      this.annotationPane.repaint();
   }

   public boolean isBreakpoint(int k) {
      return this.breakpoints.contains(this.createMarker(this.getLineStart(k)));
   }

   public boolean isBreakpointLegal(int k) {
      return true;
   }

   protected ProgramErrorDialog createErrorDialog() {
      return new ProgramErrorDialog(this);
   }

   protected void drawAnnotations(Graphics g, int k, Rectangle r) {
      if (this.isBreakpoint(k)) {
         this.drawBreakpoint(g, r);
      }

      if (this.getCurrentLine() == k) {
         this.drawCurrentLineMarker(g, r);
      }

   }

   public void setParseNeeded(boolean flag) {
      this.needsParsing = flag;
   }

   public boolean isParseNeeded() {
      return this.needsParsing;
   }

   public void tickle() {
      SwingUtilities.invokeLater(new EditorTickler(this));
   }

   protected void drawCurrentLineMarker(Graphics g, Rectangle r) {
      int x = r.x + r.width / 2 + -13;
      int y = r.y + r.height / 2 + -6;
      g.drawImage(this.handImage, x, y, this);
   }

   protected void drawBreakpoint(Graphics g, Rectangle r) {
      int x = r.x + r.width / 2 + -9;
      int y = r.y + r.height / 2 + -11;
      g.drawImage(this.breakpointImage, x, y, this);
   }

   protected TreeSet<Marker> getBreakpoints() {
      return this.breakpoints;
   }
}
