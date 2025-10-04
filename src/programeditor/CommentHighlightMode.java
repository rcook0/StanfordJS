package edu.stanford.cs.programeditor;

import edu.stanford.cs.jseditor.EditorMode;
import java.awt.Color;

public class CommentHighlightMode extends EditorMode {
   public static final Color COMMENT_COLOR;

   static {
      COMMENT_COLOR = Color.BLUE;
   }

   public CommentHighlightMode() {
      this.setModeName("CommentHighlightMode");
      this.setStyleColor("comment", COMMENT_COLOR);
      this.addRule("start", "//.*$", "comment");
      this.addRule("start", "/[*](?:[^*]/|[^/])*[*]/", "comment");
      this.addRule("start", "/[*](?:[^*]/|[^/])*$", "comment", "comment");
      this.addRule("comment", "(?:[^*]/|[^/])*[*]/", "comment", "start");
      this.addRule("comment", "(?:[^*]/|[^/])*$", "comment");
   }
}
