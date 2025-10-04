package edu.stanford.cs.svm;

import edu.stanford.cs.parser.Parser;
import java.util.TreeMap;

public abstract class SVMDirective {
   private static TreeMap<String, SVMDirective> directives;

   public abstract void execute(Parser var1, SVMModule var2);

   public static void init() {
      directives = new TreeMap();
      directives.put("IMPORT", new SVMImportDirective());
   }

   public static SVMDirective lookup(String name) {
      return (SVMDirective)directives.get(name);
   }
}
