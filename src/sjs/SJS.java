package edu.stanford.cs.sjs;

import edu.stanford.cs.controller.Updater;
import edu.stanford.cs.exp.Expression;
import edu.stanford.cs.java2js.JSEvent;
import edu.stanford.cs.java2js.JSFile;
import edu.stanford.cs.java2js.JSFileChooser;
import edu.stanford.cs.java2js.JSFrame;
import edu.stanford.cs.java2js.JSProgram;
import edu.stanford.cs.jsconsole.JSConsole;
import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.sjslib.core.Package_core;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMModule;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class SJS extends JSProgram {
   private JSConsole console;
   private JSFileChooser chooser;
   private SJSCompiler compiler;
   private SJSControlStrip controlStrip;
   private SJSEditor activeEditor;
   private SJSEditorListener editorListener;
   private SJSMenuBar menuBar;
   private SJSParser parser;
   private SJSReadEvalPrintLoop repl;
   private SJSVM svm;
   private SVMModule module;
   private String mainFunction;
   private TreeMap<String, SJSEditor> editors;

   public SJS() {
      this.createProgramFrame();
      this.getFrame().setPreferredSize(this.getPreferredSizeForScreen());
      this.setTitle("SJS 1.1.2 (30-May-17)");
      this.setLayout(new SJSLayout());
      this.mainFunction = null;
      this.editors = new TreeMap();
      this.parser = new SJSParser();
      this.editorListener = new SJSEditorListener(this);
      this.activeEditor = new SJSEditor();
      this.activeEditor.addChangeListener(this.editorListener);
      this.editors.put("Untitled", this.activeEditor);
      this.svm = new SJSVM(this);
      SJSStateChangeListener listener = new SJSStateChangeListener(this);
      this.svm.addChangeListener(listener);
      this.svm.setFrame(this.getFrame());
      this.svm.setErrorHandler(new SJSErrorHandler(this));
      this.svm.setTarget(this.svm);
      this.svm.setSpeed(100);
      this.console = new JSConsole();
      this.console.setFont(SJSC.CONSOLE_FONT);
      this.console.addActionListener(new SJSConsoleListener(this));
      this.svm.setConsole(this.console);
      (new Package_core()).init(this.svm);
      this.chooser = new JSFileChooser(this.getFrame());
      this.chooser.addLoadListener(new SJSLoadListener(this, this.chooser));
      this.chooser.addSaveListener(new SJSSaveListener(this, this.chooser));
      JSFrame consoleFrame = this.createFrame(this.console, "Console");
      JSFrame editorFrame = this.createFrame(this.activeEditor, "Untitled");
      this.activeEditor.setFrame(editorFrame);
      SJSApplicationMonitor monitor = new SJSApplicationMonitor(this);
      this.compiler = new SJSCompiler(this.svm, this.parser, monitor);
      this.repl = new SJSReadEvalPrintLoop(this.svm, this.parser, monitor);
      this.controlStrip = new SJSControlStrip(this);
      this.menuBar = new SJSMenuBar(this);
      this.setMenuBar(this.menuBar);
      this.add(consoleFrame, "console");
      this.add(editorFrame, "editor");
      this.setBackground(SJSC.APPLICATION_BACKGROUND);
      this.updateControls();
      this.pack();
      this.setVisible(true);
   }

   public void run() {
      this.updateChooser();
      this.getCommand();
   }

   public void getCommand() {
      this.console.requestInput("> ");
   }

   public void signalFinished() {
      if (this.svm.isFinished()) {
         this.getCommand();
      }

   }

   public void statementHook(int offset) {
      String pathname = this.svm.getSourceFile();
      if (pathname != null) {
         SJSEditor editor = this.getEditor(pathname);
         int line = editor.getSourceLineIndex(offset);
         if (line > 0 && editor.isBreakpoint(line)) {
            this.svm.setState(4);
            this.selectEditor(editor);
         }
      }

   }

   public void showLoadDialog() {
      this.chooser.setDialogTitle("Load");
      this.chooser.showLoadDialog();
   }

   public void showSaveDialog() {
      this.chooser.setDialogTitle("Save");
      String pathname = this.activeEditor.getPathname();
      if (pathname != null) {
         JSFile file = new JSFile(pathname);
         file.write(this.activeEditor.getText(), (ActionListener)null);
      } else {
         this.chooser.showSaveDialog();
      }

   }

   public void showExportDialog() {
      this.chooser.setDialogTitle("Export");
      String path = this.chooser.getPath();
      if (path != null) {
         this.chooser.setPath(JSFile.getRoot(path));
      }

      this.chooser.showSaveDialog();
   }

   public void newFileCommand() {
      this.activeEditor.setPathname((String)null);
      this.activeEditor.setText("");
      this.activeEditor.clearOffsetTable();
      this.activeEditor.repaint();
   }

   public SJSEditor getActiveEditor() {
      return this.activeEditor;
   }

   public SJSEditor getEditor(String pathname) {
      return (SJSEditor)this.editors.get(pathname);
   }

   public ArrayList<SJSEditor> getEditors() {
      ArrayList<SJSEditor> list = new ArrayList();
      Iterator var3 = this.editors.keySet().iterator();

      while(var3.hasNext()) {
         String key = (String)var3.next();
         list.add((SJSEditor)this.editors.get(key));
      }

      return list;
   }

   public void selectEditor(SJSEditor editor) {
      if (editor != null && editor != this.activeEditor) {
         this.activeEditor = editor;
         String pathname = editor.getPathname();
         JSFrame frame = editor.getFrame();
         frame.setContents(editor);
         if (frame != null) {
            frame.setTitle(JSFile.getTail(pathname));
         }

         String cwd = JSFile.getHead(pathname);
         if (!cwd.equals("")) {
            this.setCurrentDirectory(cwd);
         }

         frame.validate();
         editor.repaint();
      }

      this.updateControls();
   }

   public void openEditor(String pathname, String text) {
      SJSEditor editor = this.activeEditor;
      if (this.editors.get(pathname) == null) {
         editor = new SJSEditor();
         editor.addChangeListener(this.editorListener);
         editor.setPathname(pathname);
         editor.setText(text);
         editor.setCursorPosition(0);
         editor.setFrame(this.activeEditor.getFrame());
         this.editors.put(pathname, editor);
      }

      this.selectEditor(editor);
   }

   public void closeCurrentFile() {
      String pathname = this.activeEditor.getPathname();
      if (pathname == null) {
         pathname = "Untitled";
      }

      this.editors.remove(pathname);
      this.selectEditor((SJSEditor)this.getEditors().get(0));
   }

   public void updateChooser() {
      if (JSProgram.isJavaScript()) {
         String path = "cgi:" + this.getUID() + "/SJS";
         this.chooser.setCurrentDirectory(path);
      } else {
         this.chooser.setCurrentDirectory("examples");
      }

   }

   public void updateControls() {
      this.controlStrip.update();
      this.menuBar.update();
   }

   public void processConsoleLine(String line) {
      this.repl.processConsoleLine(line);
   }

   public void showError(String msg, int line) {
      String pathname = this.svm.getSourceFile();
      if (pathname == null) {
         line = 0;
      }

      this.selectEditor(this.getEditor(pathname));
      this.activeEditor.showErrorDialog(msg, line);
   }

   public void showErrorAtOffset(String msg, int offset) {
      String pathname = this.svm.getSourceFile();
      int line = 0;
      if (pathname != null) {
         SJSEditor editor = this.getEditor(pathname);
         this.selectEditor(editor);
         line = editor.getLineNumber(offset);
      }

      this.activeEditor.showErrorDialog(msg, line);
   }

   public SVM getSVM() {
      return this.svm;
   }

   public SVMModule getModule() {
      return this.module;
   }

   public JSConsole getConsole() {
      return this.console;
   }

   public SJSParser getParser() {
      return this.parser;
   }

   public SJSControlStrip getControlStrip() {
      return this.controlStrip;
   }

   public void setTraceFlag(boolean flag) {
      this.svm.setTraceFlag(flag);
   }

   public boolean getTraceFlag() {
      return this.svm.getTraceFlag();
   }

   public String getMainFunction() {
      return this.svm.isGlobal("main") ? "main" : this.mainFunction;
   }

   public void setMainFunction(String fn) {
      this.mainFunction = fn;
   }

   public void parseProgram() {
      try {
         String pathname = this.activeEditor.getPathname();
         if (pathname == null) {
            pathname = "Undefined";
         }

         String text = this.activeEditor.getText();
         this.svm.setSource(text);
         this.parser.setInput(text);
         this.module = this.parser.readModule(pathname);
         this.module.setSourceText(text);
         CodeVector cv = this.compiler.executeModule(this.module);
         this.activeEditor.addBreakpointLines(cv.getCode());
         boolean first = true;
         Iterator var6 = this.module.getFunctions().iterator();

         while(var6.hasNext()) {
            Expression fn = (Expression)var6.next();
            String name = fn.getArgs()[0].getName();
            if (first && name.indexOf("#") == -1) {
               this.setMainFunction(name);
               first = false;
            }
         }

         var6 = this.module.getImports().iterator();

         while(var6.hasNext()) {
            String name = (String)var6.next();
            if (name.indexOf(".") != -1) {
               this.compileSourceLibrary(JSFile.getHead(pathname), name);
            }
         }

         this.activeEditor.setParseNeeded(false);
      } catch (RuntimeException var8) {
         int line = this.activeEditor.getLineNumber(this.parser.getPosition());
         this.activeEditor.showErrorDialog(var8.getMessage(), line);
      }

      this.updateControls();
   }

   public void compileSourceLibrary(String base, String filename) {
      String pathname = base + "/" + filename;
      String text = "";
      SJSEditor editor = this.getEditor(pathname);
      String name;
      if (editor == null) {
         try {
            BufferedReader rd = new BufferedReader(new FileReader(pathname));

            while(true) {
               name = rd.readLine();
               if (name == null) {
                  rd.close();
                  editor = new SJSEditor();
                  editor.addChangeListener(this.editorListener);
                  editor.setPathname(pathname);
                  editor.setText(text);
                  editor.setCursorPosition(0);
                  editor.setFrame(this.activeEditor.getFrame());
                  editor.setSaveNeeded(false);
                  if (!pathname.endsWith("MagicStub.js")) {
                     this.editors.put(pathname, editor);
                  }
                  break;
               }

               text = text + name + "\n";
            }
         } catch (IOException var9) {
            throw new RuntimeException("No source library named " + filename);
         }
      } else {
         text = editor.getText();
      }

      this.svm.setSource(text);
      this.parser.setInput(text);
      SVMModule module = this.parser.readModule(pathname);
      this.compiler.executeModule(module);
      editor.setParseNeeded(false);
      Iterator var8 = module.getImports().iterator();

      while(var8.hasNext()) {
         name = (String)var8.next();
         if (name.indexOf(".") != -1) {
            this.compileSourceLibrary(JSFile.getHead(pathname), name);
         }
      }

   }

   protected Updater createSaveUpdater() {
      return new SaveUpdater(this);
   }

   protected boolean isSaveEnabled() {
      return this.activeEditor.getPathname() == null || this.activeEditor.isSaveNeeded();
   }

   protected boolean isCloseEnabled() {
      return this.activeEditor.getPathname() != null && !this.activeEditor.isSaveNeeded();
   }

   protected Updater createRunUpdater() {
      return new RunUpdater(this);
   }

   protected boolean isRunEnabled() {
      return !this.svm.isRunning() && this.getMainFunction() != null && !this.activeEditor.isParseNeeded();
   }

   protected Updater createCompileUpdater() {
      return new CompileUpdater(this);
   }

   protected boolean isCompileEnabled() {
      Iterator var2 = this.getEditors().iterator();

      while(var2.hasNext()) {
         SJSEditor e = (SJSEditor)var2.next();
         if (e.isParseNeeded()) {
            return true;
         }
      }

      return false;
   }

   protected Updater createStepUpdater() {
      return new StepUpdater(this);
   }

   protected boolean isStepEnabled() {
      return !this.svm.isRunning() && this.getMainFunction() != null && !this.activeEditor.isParseNeeded();
   }

   private Dimension getPreferredSizeForScreen() {
      Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
      int width = Math.min(1200, (int)size.getWidth() - 20);
      int height = Math.min(800, (int)size.getHeight() - 60);
      return new Dimension(width, height);
   }

   private JSFrame createFrame(JComponent component, String title) {
      return new JSFrame(component, title);
   }

   public static void main(String[] args) {
      JSEvent.setHeadlessTimer(true);
      boolean hasArgs = false;
      boolean trace = false;
      boolean traceErrors = false;

      for(int i = 0; i < args.length; ++i) {
         String arg = args[i];
         if (arg.startsWith("code=")) {
            break;
         }

         if (arg.startsWith("-")) {
            if (arg.equals("-t")) {
               trace = true;
            } else if (arg.equals("-e")) {
               traceErrors = true;
            } else {
               hasArgs = true;
            }
         } else {
            hasArgs = true;
         }
      }

      if (hasArgs) {
         (new SJSInterpreter()).start(args);
      } else {
         JSEvent.setHeadlessTimer(false);
         SwingUtilities.invokeLater(new SJSGUI(trace, traceErrors));
      }

   }
}
