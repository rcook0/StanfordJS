package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Expression;
import java.util.ArrayList;
import java.util.Iterator;

public class SVMModule {
   private ArrayList<String> imports;
   private ArrayList<Expression> functions;
   private ArrayList<Expression> globals;
   private String pathname;
   private String source;

   public SVMModule(String pathname) {
      this.pathname = pathname;
      this.imports = new ArrayList();
      this.functions = new ArrayList();
      this.globals = new ArrayList();
   }

   public String getFilePath() {
      return this.pathname;
   }

   public void addImport(String pathname) {
      this.imports.add(pathname);
   }

   public String getMainFunction() {
      if (this.hasFunction("main")) {
         return "main";
      } else {
         Iterator var2 = this.functions.iterator();

         Expression fn;
         String name;
         do {
            if (!var2.hasNext()) {
               return null;
            }

            fn = (Expression)var2.next();
            name = fn.getArgs()[0].getName();
         } while(name.indexOf("#") != -1 || fn.getArgs()[1].getArgs().length != 0);

         return name;
      }
   }

   public boolean hasImport(String name) {
      return this.imports.contains(name);
   }

   public boolean hasFunction(String name) {
      Iterator var3 = this.functions.iterator();

      while(var3.hasNext()) {
         Expression fn = (Expression)var3.next();
         if (fn.getArgs()[0].getName().equals(name)) {
            return true;
         }
      }

      return false;
   }

   public ArrayList<String> getImports() {
      return this.imports;
   }

   public void addFunction(Expression fn) {
      this.functions.add(fn);
   }

   public ArrayList<Expression> getFunctions() {
      return this.functions;
   }

   public void addGlobal(Expression global) {
      this.globals.add(global);
   }

   public ArrayList<Expression> getGlobals() {
      return this.globals;
   }

   public void setSourceText(String source) {
      this.source = source;
   }

   public String getSourceText() {
      return this.source;
   }
}
