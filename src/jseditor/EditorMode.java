package edu.stanford.cs.jseditor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EditorMode {
   private String modeName = null;
   private TreeMap<String, ArrayList<EditorRule>> ruleTable = new TreeMap();
   private TreeMap<String, EditorStyle> styleTable = new TreeMap();

   public void setModeName(String name) {
      this.modeName = name;
   }

   public String getModeName() {
      return this.modeName;
   }

   public void setStyleColor(String style, Color color) {
      EditorStyle es = (EditorStyle)this.styleTable.get(style);
      if (es == null) {
         es = new EditorStyle();
         this.styleTable.put(style, es);
      }

      es.color = color;
   }

   public void addRule(String state, String regex, String style) {
      this.addRule(state, regex, style, (String)null);
   }

   public void addRule(String state, String regex, String style, String next) {
      ArrayList<EditorRule> rules = (ArrayList)this.ruleTable.get(state);
      if (rules == null) {
         rules = new ArrayList();
         this.ruleTable.put(state, rules);
      }

      EditorRule rule = new EditorRule();
      rule.style = style;
      rule.pattern = Pattern.compile(regex, 8);
      rule.next = next;
      rules.add(rule);
   }

   public void runTokenizer(JSEditor editor) {
      String text = editor.getText();
      editor.setTextColor(0, text.length(), Color.BLACK);
      Matcher matcher = Pattern.compile("").matcher(editor.getText());
      String state = "start";
      if (this.ruleTable.get(state) != null) {
         int end = matcher.regionEnd();

         while(matcher.regionStart() < end) {
            ArrayList<EditorRule> rules = (ArrayList)this.ruleTable.get(state);
            boolean found = false;

            for(int i = 0; i < rules.size() && !found; ++i) {
               EditorRule rule = (EditorRule)rules.get(i);
               matcher.usePattern(rule.pattern);
               if (matcher.lookingAt()) {
                  found = true;
                  int tokenStart = matcher.start();
                  int tokenEnd = matcher.end();
                  EditorStyle es = null;
                  if (rule.style != null) {
                     es = (EditorStyle)this.styleTable.get(rule.style);
                  }

                  if (es != null) {
                     editor.setTextColor(tokenStart, tokenEnd, es.color);
                  }

                  matcher.region(tokenEnd, end);
                  if (rule.next != null) {
                     state = rule.next;
                  }
               }
            }

            if (!found) {
               matcher.region(matcher.regionStart() + 1, end);
            }
         }

      }
   }

   public String toString() {
      String str = "EditorMode";
      if (this.modeName != null) {
         str = str + ":" + this.modeName;
      }

      return str;
   }
}
