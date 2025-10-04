package edu.stanford.cs.svmtools;

import edu.stanford.cs.programeditor.CommentHighlightMode;
import edu.stanford.cs.programeditor.ProgramEditor;
import edu.stanford.cs.tokenscanner.TokenScanner;
import java.awt.Font;
import java.util.TreeMap;
import java.util.TreeSet;

public class SVMEditor extends ProgramEditor {
   public static final int[] FONT_SIZES = new int[]{7, 8, 9, 10, 11, 12, 14, 16, 18, 20, 24, 28, 32, 36, 40, 44, 48};
   private TreeMap<Integer, Integer> offsetToLine;
   private TreeSet<Integer> breakpointLines;
   private TokenScanner scanner = new TokenScanner();

   public SVMEditor() {
      this.scanner.ignoreWhitespace();
      this.scanner.ignoreComments();
      this.scanner.scanNumbers();
      this.setLineWrap(false);
      this.setEditorMode(new CommentHighlightMode());
      this.offsetToLine = new TreeMap();
      this.breakpointLines = new TreeSet();
   }

   public int getSourceLineIndex(int offset) {
      Integer k = (Integer)this.offsetToLine.get(offset);
      return k == null ? -1 : k;
   }

   public void clearOffsetTable() {
      this.offsetToLine.clear();
      this.breakpointLines.clear();
   }

   public void addBreakpointLines(int[] code) {
      int n = code.length;

      for(int i = 0; i < n; ++i) {
         int ins = code[i];
         if (ins >> 24 == 3) {
            int offset = ins & 16777215;
            int line = this.getLineNumber(offset);
            this.offsetToLine.put(offset, line);
            this.breakpointLines.add(line);
         }
      }

   }

   public void addWordCharacters(String chars) {
      this.scanner.addWordCharacters(chars);
   }

   public static Font smallerFont(Font font) {
      int nSizes = FONT_SIZES.length;
      int oldSize = font.getSize();
      int newSize = FONT_SIZES[0];

      for(int i = nSizes - 1; i >= 0; --i) {
         if (FONT_SIZES[i] < oldSize) {
            newSize = FONT_SIZES[i];
            break;
         }
      }

      return font.deriveFont(font.getStyle(), (float)newSize);
   }

   public static Font largerFont(Font font) {
      int nSizes = FONT_SIZES.length;
      int oldSize = font.getSize();
      int newSize = FONT_SIZES[nSizes - 1];

      for(int i = 0; i < nSizes; ++i) {
         if (FONT_SIZES[i] > oldSize) {
            newSize = FONT_SIZES[i];
            break;
         }
      }

      return font.deriveFont(font.getStyle(), (float)newSize);
   }

   public boolean isBreakpointLegal(int k) {
      return this.breakpointLines.contains(k);
   }
}
