package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.exp.Value;
import edu.stanford.cs.svm.SVM;
import edu.stanford.cs.svm.SVMPackage;

public class Package_core extends SVMPackage {
   private String[] DEPENDENCIES = new String[]{"java.lang", "edu.stanford.cs.java2js", "edu.stanford.cs.jsconsole"};
   private String[] WRAPPER = new String[]{"", "/* Wrapper to support the core JavaScript functionality */", "", "var JSProgram = edu_stanford_cs_java2js.JSProgram;", "var JSConsole = edu_stanford_cs_jsconsole.JSConsole;", "var Character = java_lang.Character;", "var Double = java_lang.Double;", "var Integer = java_lang.Integer;", "var System = java_lang.System;", "", "/* Polyfill missing methods */", "", "Math.sign = Math.sign || function(x) {", "   if (isNaN(x)) return x;", "   return (x === 0) ? 0 : (x < 0) ? -1 : 1;", "};", "", "Math.toDegrees = Math.toDegrees || function(x) {", "   return 180 * x / Math.PI;", "};", "", "Math.toRadians = Math.toRadians || function(x) {", "   return Math.PI * x / 180;", "};", "", "String.prototype.contains = String.prototype.contains ||", "function(str) {", "   return this.indexOf(str) !== -1;", "};", "", "String.prototype.equalsIgnoreCase = String.prototype.equalsIgnoreCase ||", "function(str) {", "   return this.toLowerCase() === str.toLowerCase();", "};", "", "String.prototype.compareTo = String.prototype.compareTo ||", "function(str) {", "   var s1 = \"\" + this;", "   return (s1 === str) ? 0 : (s1 < str) ? -1 : 1;", "};", "", "String.prototype.compareToIgnoreCase = String.prototype.compareToIgnoreCase ||", "function(str) {", "   var s1 = this.toLowerCase();", "   var s2 = str.toLowerCase();", "   return (s1 === s2) ? 0 : (s1 < s2) ? -1 : 1;", "};", "", "String.prototype.startsWith = String.prototype.startsWith ||", "function(prefix) {", "   return this.substring(0, Math.min(this.length, prefix.length)) === prefix;", "};", "", "String.prototype.endsWith = String.prototype.endsWith ||", "function(suffix) {", "   return this.substring(Math.max(0, this.length - suffix.length)) === suffix;", "};", "", "var pgm = new JSProgram();", "var jsconsole = console;", "console = new JSConsole();", "pgm.add(console, \"console\");"};

   public void defineClasses(SVM svm) {
      this.defineClass(svm, "Array", new SJSArrayClass());
      this.defineClass(svm, "Character", new SJSCharacterClass());
      this.defineClass(svm, "Console", new SJSConsoleClass());
      this.defineClass(svm, "Math", new SJSMathClass());
      this.defineClass(svm, "Number", new SJSNumberClass());
      this.defineClass(svm, "Object", new SJSObjectClass());
      this.defineClass(svm, "Program", new SJSProgramClass());
      this.defineClass(svm, "String", new SJSStringClass());
      this.defineClass(svm, "System", new SJSSystemClass());
      this.defineClass(svm, "Timer", new SJSTimerClass());
   }

   public void defineGlobals(SVM svm) {
      this.defineMethod(svm, "getNumber", "Console.getFloat");
      this.defineMethod(svm, "getInt", "Console.getInt");
      this.defineMethod(svm, "getLine", "Console.getLine");
      this.defineMethod(svm, "getNumber", "Console.getNumber");
      this.defineMethod(svm, "print", "Console.print");
      this.defineMethod(svm, "println", "Console.println");
      this.defineMethod(svm, "isFinite", "Number.isFinite");
      this.defineMethod(svm, "isNaN", "Number.isNaN");
      this.defineMethod(svm, "parseFloat", "Number.parseFloat");
      this.defineMethod(svm, "parseInt", "Number.parseInt");
      this.defineMethod(svm, "alert", "Program.alert");
      this.defineMethod(svm, "clearTimeout", "Timer.clearTimeout");
      this.defineMethod(svm, "setTimeout", "Timer.setTimeout");
      this.defineMethod(svm, "setInterval", "Timer.setInterval");
      svm.setGlobal("true", Value.TRUE);
      svm.setGlobal("false", Value.FALSE);
      svm.setGlobal("null", Value.NULL);
      svm.setGlobal("undefined", Value.UNDEFINED);
      svm.setGlobal("console", Value.createObject(svm.getConsole(), "Console"));
   }

   public String[] getDependencies() {
      return this.DEPENDENCIES;
   }

   public String[] getWrapper() {
      return this.WRAPPER;
   }
}
