package edu.stanford.cs.svm;

import edu.stanford.cs.parser.Parser;

class SVMImportDirective extends SVMDirective {
   public void execute(Parser parser, SVMModule module) {
      String pkg = parser.nextToken();
      String token = parser.nextToken();
      if (!token.equals("\n") && !token.equals(";")) {
         throw new RuntimeException("Illegal import declaration");
      } else {
         module.addImport(pkg);
      }
   }
}
