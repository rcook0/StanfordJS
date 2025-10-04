package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.exp.Value;
import edu.stanford.cs.file.FileLib;
import edu.stanford.cs.java2js.JSEvent;
import edu.stanford.cs.java2js.JSPackage;
import edu.stanford.cs.java2js.JSPlatform;
import edu.stanford.cs.jsconsole.JavaConsole;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.parser.SyntaxError;
import edu.stanford.cs.shellargs.ShellArgs;
import edu.stanford.cs.sjslib.core.Package_core;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMModule;
import edu.stanford.cs.svm.SVMPackage;
import edu.stanford.cs.svm.SVMProgram;
import edu.stanford.cs.svm.SVMSourceMarker;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.TreeSet;

public class SJSInterpreter extends SVMProgram {
   public static final String PROGRAM_PATH = ".:programs";
   private static final String[] OPTIONS = new String[]{"-c", "-e", "-help", "-i", "-j", "-l", "-script <file>", "-t", "-x"};
   private static final String[] REQUIREJS_HEADER = new String[]{"requirejs.config( { baseUrl: \"js\" });"};
   private static final String[] REQUIREJS_TRAILER = new String[]{"          });"};
   private static final String INDENT = "             ";
   private JavaConsole console;
   private SJSInterpreterMonitor monitor;
   private SJSParser parser;
   private SJSReadEvalPrintLoop repl;
   private SJSVM svm = (SJSVM)this.getSVM();
   private static TreeSet<String> librariesLoaded = new TreeSet();
   private static boolean traceFlag = false;

   public SJSInterpreter() {
      this.setCurrentDirectory(System.getProperty("user.dir"));
   }

   public boolean isInterpreter() {
      return true;
   }

   public void start(String[] args) {
      ShellArgs options = new ShellArgs(args, OPTIONS);
      if (options.isOptionSpecified("-help")) {
         options.showUsage("sjs <options> <files>", OPTIONS);
      } else {
         this.runSJS(options);
      }

   }

   public SJSParser getParser() {
      return this.parser;
   }

   public static void setTraceFlag(boolean flag) {
      traceFlag = flag;
   }

   public static boolean getTraceFlag() {
      return traceFlag;
   }

   public static void showError(RuntimeException ex, SVM svm, int index) {
      String msg = ex.getMessage();
      if (msg == null || msg.isEmpty()) {
         msg = ex.toString();
      }

      SVMSourceMarker marker = svm.getSourceMarker(index);
      if (marker != null) {
         if (msg.endsWith("expecting ;")) {
            msg = "Missing ;";
            marker = svm.getSourceMarker(marker.getStartingIndex() - 2);
            index = marker.getSourceLine().length() + marker.getStartingIndex() + 1;
         }

         System.err.println(marker.getSourceLine());
         if (ex instanceof SyntaxError) {
            String caret = "^";
            int n = index - marker.getStartingIndex() - 1;

            for(int i = 0; i < n; ++i) {
               caret = " " + caret;
            }

            System.err.println(caret);
            msg = "Syntax error: " + msg;
         } else {
            msg = "Runtime error: " + msg;
         }
      }

      System.err.println(msg);
   }

   protected SVM createSVM() {
      return new SJSVM((SJS)null);
   }

   private void runSJS(ShellArgs options) {
      SJSVM svm = (SJSVM)this.getSVM();
      this.console = new JavaConsole();
      this.monitor = new SJSInterpreterMonitor(this);
      this.console.addActionListener(this.monitor);
      svm.setConsole(this.console);
      (new Package_core()).init(svm);
      this.parser = new SJSParser();
      SJSCompiler compiler = new SJSCompiler(svm, this.parser, (SJSCommandMonitor)null);
      SVMModule module = new SVMModule("[empty]");
      CodeVector cv = new CodeVector();
      String title = "Untitled";
      String sjsSource = "";
      boolean merge = options.isOptionSpecified("-c") || options.isOptionSpecified("-j") || options.isOptionSpecified("-l") || options.isOptionSpecified("-x");
      String[] files = options.getFiles();
      TreeSet<String> sourceLibraries = new TreeSet();

      for(int i = 0; i < files.length; ++i) {
         String filename = FileLib.defaultExtension(files[i], ".sjs");
         BufferedReader rd = FileLib.openOnPath(".:programs", filename);
         if (rd == null) {
            filename = FileLib.defaultExtension(files[i], ".js");
            rd = FileLib.openOnPath(".:programs", filename);
         }

         if (rd == null) {
            System.err.println("No file named " + files[i]);
         } else {
            if (title.equals("Untitled")) {
               title = FileLib.getRoot(FileLib.getTail(filename));
            }

            if (merge) {
               sjsSource = appendFileToString(rd, sjsSource);
            } else {
               sjsSource = appendFileToString(rd, "");
               this.scanForSourceLibraries(sjsSource, this.parser, sourceLibraries);
               cv = new CodeVector();
               svm.setSource(sjsSource);
               this.parser.setInput(svm.getSource());
               module = this.parser.readModule(filename);
               module.setSourceText(svm.getSource());
               compiler.compileModule(module, cv, (String)null);
               Iterator var15 = module.getImports().iterator();

               while(var15.hasNext()) {
                  String name = (String)var15.next();
                  if (!isSourceLibrary(name) && !librariesLoaded.contains(name)) {
                     JSPackage.load((String)("edu.stanford.cs.sjslib." + name), svm);
                     librariesLoaded.add(name);
                  }
               }

               svm.setCode(cv.getCode());
               svm.setPC(0);
               svm.run();
            }

            try {
               rd.close();
            } catch (IOException var18) {
            }
         }
      }

      try {
         if (merge) {
            if (sjsSource.length() != 0) {
               sjsSource = this.addSourceLibraries(sjsSource, sourceLibraries);
               svm.setSource(sjsSource);
               this.parser.setInput(sjsSource);
               module = this.parser.readModule("[merged]");
               compiler.compileModule(module, cv, module.getMainFunction());
            }
         } else {
            this.compileSourceLibraries(compiler, sourceLibraries);
         }
      } catch (RuntimeException var17) {
         showError(var17, svm, this.parser.getPosition());
      }

      if (merge) {
         if (options.isOptionSpecified("-x")) {
            exportAsJS(title, module);
         } else {
            cv.addInstruction(0, 0);
            int[] code = cv.getCode();
            if (options.isOptionSpecified("-c")) {
               this.exportAsSVM(title, code, module);
            } else if (options.isOptionSpecified("-j")) {
               System.out.println("   private static int[] PROGRAM = {");
               int n = code.length;

               for(int j = 0; j < n; ++j) {
                  System.out.print("      " + code[j]);
                  System.out.println(j < n - 1 ? "," : "");
               }

               System.out.println("   };");
            } else if (options.isOptionSpecified("-l")) {
               svm.setCode(code);
               svm.list();
            }
         }
      } else {
         try {
            svm.setTraceFlag(options.isOptionSpecified("-t"));
            svm.setTraceErrors(options.isOptionSpecified("-e"));
            if (module.hasImport("graphics")) {
               JSEvent.setHeadlessTimer(false);
            }

            svm.setGlobal("TITLE", Value.createString(title));
            String main = module.getMainFunction();
            if (options.isOptionSpecified("-i")) {
               main = null;
            }

            if (options.isOptionSpecified("-script")) {
               main = null;
            }

            if (main != null) {
               cv = new CodeVector();
               cv.addInstruction(108, cv.stringRef(main));
               cv.addInstruction(98, 0);
               cv.addInstruction(0, 0);
               svm.setCode(cv.getCode());
               svm.setGlobal("$MainProgram", Value.createString(main));
               svm.setPC(0);
               svm.run();
               this.monitor.waitForCompletion();
            }

            if (svm.getState() != 7) {
               this.repl = new SJSReadEvalPrintLoop(svm, this.parser, this.monitor);
               if (options.isOptionSpecified("-script")) {
                  this.processScript(options.getOption("-script"));
               }

               if (options.isOptionSpecified("-i")) {
                  this.monitor.setInteractive(true);
                  this.console.requestInput("> ");
                  this.monitor.waitForExit();
               }
            }
         } catch (RuntimeException var16) {
            showError(var16, svm, svm.getStatementOffset());
         }
      }

   }

   public void processScript(String filename) {
      try {
         BufferedReader rd = new BufferedReader(new FileReader(filename));

         while(true) {
            String line = rd.readLine();
            if (line == null) {
               rd.close();
               break;
            }

            this.console.println("> " + line);
            if (!line.equals("")) {
               this.repl.processConsoleLine(line);
               this.monitor.waitForCompletion();
            }
         }
      } catch (IOException var4) {
         System.err.println("Error: " + var4.getMessage());
      }

   }

   public void processConsoleLine(String line) {
      if (line.equals("quit")) {
         System.exit(0);
      }

      if (!line.equals("")) {
         this.repl.processConsoleLine(line);
      }

   }

   public void signalFinished() {
      if (this.svm.isFinished()) {
         this.console.requestInput("> ");
      }

   }

   private void scanForSourceLibraries(String source, SJSParser parser, TreeSet<String> sourceLibraries) {
      boolean changed = true;

      while(changed) {
         changed = false;
         String[] lines = JSPlatform.splitLines(source);
         String[] var9 = lines;
         int var8 = lines.length;

         for(int var7 = 0; var7 < var8; ++var7) {
            String line = var9[var7];
            if (line.startsWith("import ")) {
               parser.setInput(line);
               parser.verifyToken("import");
               String token = parser.nextToken();
               if (parser.getTokenType(token) != 3) {
                  throw new SyntaxError("Illegal import statement");
               }

               parser.verifyToken(";");
               String filename = parser.getStringValue(token);
               if (isSourceLibrary(filename) && !sourceLibraries.contains(filename)) {
                  sourceLibraries.add(filename);
                  changed = true;
               }
            }
         }
      }

   }

   private String addSourceLibraries(String sjsSource, TreeSet<String> sourceLibraries) {
      Iterator var4 = sourceLibraries.iterator();

      while(var4.hasNext()) {
         String filename = (String)var4.next();
         BufferedReader rd = FileLib.openOnPath(".:programs", filename);
         if (rd == null) {
            System.err.println("No file named " + filename);
         } else {
            sjsSource = appendFileToString(rd, sjsSource);
         }

         try {
            rd.close();
         } catch (IOException var7) {
         }
      }

      return sjsSource;
   }

   private void compileSourceLibraries(SJSCompiler compiler, TreeSet<String> sourceLibraries) {
      SVM svm = compiler.getSVM();
      SJSParser parser = compiler.getParser();
      Iterator var6 = sourceLibraries.iterator();

      while(var6.hasNext()) {
         String filename = (String)var6.next();
         BufferedReader rd = FileLib.openOnPath(".:programs", filename);
         if (rd == null) {
            System.err.println("No file named " + filename);
         } else {
            String sjsSource = appendFileToString(rd, "");
            CodeVector cv = new CodeVector();
            svm.setSource(sjsSource);
            parser.setInput(svm.getSource());
            SVMModule module = parser.readModule(filename);
            compiler.compileModule(module, cv, (String)null);
            svm.setCode(cv.getCode());
            svm.setPC(0);
            svm.run();
         }

         try {
            rd.close();
         } catch (IOException var11) {
         }
      }

   }

   private static boolean isSourceLibrary(String filename) {
      return filename.endsWith(".js") || filename.endsWith(".sjs");
   }

   private static String appendFileToString(BufferedReader rd, String str) {
      try {
         while(true) {
            String line = rd.readLine();
            if (line == null) {
               rd.close();
               return str;
            }

            str = str + line + "\n";
         }
      } catch (IOException var3) {
         throw new SyntaxError(var3.toString());
      }
   }

   public static void exportAsJS(String name, SVMModule module) {
      FileLib.createDirectory(name);
      createJSFile(name, module.getSourceText(), module);
      createJSDirectory(name, module.getClass().getClassLoader());
      createHTMLIndex(name, module);
   }

   private static void createJSFile(String name, String source, SVMModule module) {
      PrintWriter wr = FileLib.openWriter(name + "/" + FileLib.getTail(name) + ".js");
      String[] var7;
      int var6 = (var7 = REQUIREJS_HEADER).length;

      for(int var5 = 0; var5 < var6; ++var5) {
         String line = var7[var5];
         wr.println(line);
      }

      TreeSet<String> libraries = new TreeSet();
      addImports(libraries, "edu.stanford.cs.sjslib.core");
      Iterator var11 = module.getImports().iterator();

      while(var11.hasNext()) {
         String str = (String)var11.next();
         if (!isSourceLibrary(name)) {
            addImports(libraries, "edu.stanford.cs.sjslib." + str);
         }
      }

      boolean first = true;
      wr.print("requirejs([ ");

      String main;
      Iterator var13;
      for(var13 = libraries.iterator(); var13.hasNext(); wr.print('"' + main.replace(".", "/") + '"')) {
         main = (String)var13.next();
         if (first) {
            first = false;
         } else {
            wr.println(",");
            wr.print("            ");
         }
      }

      wr.println(" ],");
      wr.println();
      first = true;
      wr.print("function(");

      for(var13 = libraries.iterator(); var13.hasNext(); wr.print(main.replace(".", "_"))) {
         main = (String)var13.next();
         if (first) {
            first = false;
         } else {
            wr.println(",");
            wr.print("         ");
         }
      }

      wr.println(") {");
      dumpJSWrapper(wr, "edu.stanford.cs.sjslib.core");
      var13 = module.getImports().iterator();

      while(var13.hasNext()) {
         main = (String)var13.next();
         if (!isSourceLibrary(name)) {
            dumpJSWrapper(wr, "edu.stanford.cs.sjslib." + main);
         }
      }

      wr.println();
      copyJSSource(wr, JSPlatform.splitLines(source));
      main = module.getMainFunction();
      if (main != null) {
         wr.println();
         wr.println(main + "();");
      }

      wr.println();
      wr.println("});");
      wr.close();
   }

   private static void copyJSSource(PrintWriter wr, String[] lines) {
      int nLines = lines.length;

      for(int i = 0; i < nLines; ++i) {
         String line = toStandardJavaScript(lines[i]);
         if (line != null) {
            wr.println(line);
         }
      }

   }

   private static String toStandardJavaScript(String line) {
      String trimmed = line.trim();
      if (trimmed.startsWith("import ")) {
         return null;
      } else if (trimmed.startsWith("const ")) {
         int start = line.indexOf("const ");
         return line.substring(0, start) + "var " + line.substring(start + 6);
      } else {
         return line;
      }
   }

   private static void addImports(TreeSet<String> libraries, String name) {
      SVMPackage pkg = (SVMPackage)JSPackage.load((String)name, (Object)null);
      String[] imports = pkg.getDependencies();
      int n = imports.length;

      for(int i = 0; i < n; ++i) {
         libraries.add(imports[i]);
      }

   }

   private static void dumpJSWrapper(PrintWriter wr, String name) {
      SVMPackage pkg = (SVMPackage)JSPackage.load((String)name, (Object)null);
      String[] importCode = pkg.getWrapper();
      int nLines = importCode.length;

      for(int i = 0; i < nLines; ++i) {
         wr.println(importCode[i]);
      }

   }

   public void exportAsSVM(String name, int[] code, SVMModule module) {
      FileLib.createDirectory(name);
      this.createSVMFile(name, code, module);
      createJSDirectory(name, module.getClass().getClassLoader());
      createHTMLIndex(name, module);
   }

   private void createSVMFile(String name, int[] code, SVMModule module) {
      PrintWriter wr = FileLib.openWriter(name + "/" + name + ".js");
      String[] var8;
      int var7 = (var8 = REQUIREJS_HEADER).length;

      String line;
      int var6;
      for(var6 = 0; var6 < var7; ++var6) {
         line = var8[var6];
         wr.println(line);
      }

      wr.println("requirejs([ \"edu/stanford/cs/svm\",");
      wr.print("            \"edu/stanford/cs/sjslib/core\"");
      Iterator var10 = module.getImports().iterator();

      while(var10.hasNext()) {
         line = (String)var10.next();
         if (!isSourceLibrary(name)) {
            wr.println(",");
            wr.print("            \"edu/stanford/cs/sjslib/" + line + "\"");
         }
      }

      wr.println(" ],");
      wr.println();
      wr.println("function(edu_stanford_cs_svm,");
      wr.print("         edu_stanford_cs_sjslib_core");
      var10 = module.getImports().iterator();

      while(var10.hasNext()) {
         line = (String)var10.next();
         if (!isSourceLibrary(name)) {
            wr.println(", ");
            wr.print("         edu_stanford_cs_sjslib_" + line);
         }
      }

      wr.println(") {");
      wr.println("             var pgm = new edu_stanford_cs_svm.SVMConsoleProgram();");
      wr.println("             var svm = pgm.getSVM();");
      wr.println("             new edu_stanford_cs_sjslib_core.Package_core().init(svm);");
      var10 = module.getImports().iterator();

      while(var10.hasNext()) {
         line = (String)var10.next();
         if (!isSourceLibrary(name)) {
            String suffix = line.substring(line.lastIndexOf(".") + 1);
            String className = "edu_stanford_cs_sjslib_" + line + ".Package_" + suffix;
            wr.println("             new " + className + "().init(svm);");
         }
      }

      wr.println("             pgm.setCode([");

      for(int i = 0; i < code.length; ++i) {
         String sep = i < code.length - 1 ? "," : "";
         wr.println("                " + code[i] + sep);
      }

      wr.println("             ]);");
      wr.println("             pgm.getSVM().run();");
      var7 = (var8 = REQUIREJS_TRAILER).length;

      for(var6 = 0; var6 < var7; ++var6) {
         line = var8[var6];
         wr.println(line);
      }

      wr.close();
   }

   private static void createJSDirectory(String path, ClassLoader cl) {
      try {
         String home = System.getProperty("user.home");
         if ((new File(home + "/lib/js")).exists()) {
            String cmd = "ln -s " + home + "/lib/js " + path;
            Process p = Runtime.getRuntime().exec(cmd);
            p.waitFor();
         }
      } catch (Exception var5) {
         System.out.println(var5);
      }

   }

   private static void createHTMLIndex(String name, SVMModule module) {
      int width = 500;
      int height = 300;
      PrintWriter wr = FileLib.openWriter(name + "/index.html");
      wr.println("<!DOCTYPE html>");
      wr.println("<html core=\"en\">");
      wr.println("<head>");
      wr.println("<title>" + name + "</title>");
      wr.println("<style type=\"text/css\" media=\"screen\">");
      if (module.hasImport("graphics")) {
         width = getGlobalInteger("GWINDOW_WIDTH", width, module);
         height = getGlobalInteger("GWINDOW_HEIGHT", height, module);
         wr.println("  #canvas {");
         wr.println("    overflow: hide;");
         wr.println("    border: solid 1px black;");
         wr.println("    background-color: white;");
         wr.println("    width: " + width + "px;");
         wr.println("    height: " + height + "px;");
         wr.println("  }");
      } else if (usesConsole(module)) {
         width = getGlobalInteger("CONSOLE_WIDTH", width, module);
         height = getGlobalInteger("CONSOLE_HEIGHT", height, module);
      }

      if (usesConsole(module)) {
         if (module.hasImport("graphics")) {
            height = 200;
         }

         wr.println("  #console {");
         wr.println("    overflow: hide;");
         wr.println("    border: solid 1px black;");
         wr.println("    background-color: white;");
         wr.println("    width: " + width + "px;");
         wr.println("    height: " + height + "px;");
         wr.println("  }");
      }

      wr.println("</style>");
      wr.println("</head>");
      wr.println("<body>");
      if (module.hasImport("graphics")) {
         wr.println("<div id=\"canvas\"></div>");
         if (usesConsole(module)) {
            wr.println("<p>");
         }
      }

      if (usesConsole(module)) {
         wr.println("<div id=\"console\"></div>");
      }

      wr.println("<script data-main=\"" + FileLib.getTail(name) + ".js\"" + " src=\"js/requirejs/require.js\"></script>");
      wr.println("</body>");
      wr.println("</html>");
      wr.close();
   }

   private static boolean usesConsole(SVMModule module) {
      return false;
   }

   private static int getGlobalInteger(String name, int defValue, SVMModule module) {
      Iterator var4 = module.getGlobals().iterator();

      while(var4.hasNext()) {
         Expression declaration = (Expression)var4.next();
         Expression exp = declaration.getArgs()[0];
         if (exp.getArgs()[0].getName().equals(name)) {
            Expression rhs = exp.getArgs()[1];
            if (rhs.getType() == 1) {
               Value value = rhs.getValue();
               if (value.isIntegral()) {
                  return value.getIntegerValue();
               }
            }

            return defValue;
         }
      }

      return defValue;
   }
}
