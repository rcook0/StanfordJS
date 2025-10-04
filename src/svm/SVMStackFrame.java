package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import java.util.HashMap;

public class SVMStackFrame {
   private HashMap<String, Value> symbolTable = null;
   private SVMStackFrame link = null;
   private String sourceFile;
   private Value thisRef;
   private Value receiver;
   private Value[] locals;
   private int[] code = null;
   private int argCount = 0;
   private int frameSize = 0;
   private int returnAddress = -1;
   private int stackBase;

   public SVMStackFrame() {
      this.thisRef = Value.NULL;
      this.receiver = Value.NULL;
   }

   public void setFrameSize(int n) {
      this.frameSize = n;
      this.locals = new Value[n];
   }

   public int getFrameSize() {
      return this.frameSize;
   }

   public void setArgumentCount(int n) {
      this.argCount = n;
   }

   public int getArgumentCount() {
      return this.argCount;
   }

   public void setStackBase(int base) {
      this.stackBase = base;
   }

   public int getStackBase() {
      return this.stackBase;
   }

   public void setCode(int[] code) {
      this.code = code;
   }

   public int[] getCode() {
      return this.code;
   }

   public void setReturnAddress(int addr) {
      this.returnAddress = addr;
   }

   public int getReturnAddress() {
      return this.returnAddress;
   }

   public void setSourceFile(String filename) {
      this.sourceFile = filename;
   }

   public String getSourceFile() {
      return this.sourceFile;
   }

   public void setLocal(int k, Value value) {
      this.locals[k] = value;
   }

   public Value getLocal(int k) {
      return this.locals[k];
   }

   public void declareVar(String name) {
      if (this.symbolTable == null) {
         this.symbolTable = new HashMap();
      }

      this.symbolTable.put(name, Value.UNDEFINED);
   }

   public boolean isDeclared(String name) {
      return this.symbolTable == null ? false : this.symbolTable.containsKey(name);
   }

   public void setVar(String name, Value value) {
      if (this.symbolTable == null) {
         this.symbolTable = new HashMap();
      }

      this.symbolTable.put(name, value);
   }

   public Value getVar(String name) {
      if (this.symbolTable == null) {
         return Value.UNDEFINED;
      } else {
         return !this.symbolTable.containsKey(name) ? Value.UNDEFINED : (Value)this.symbolTable.get(name);
      }
   }

   public void setFrameLink(SVMStackFrame frame) {
      this.link = frame;
   }

   public SVMStackFrame getFrameLink() {
      return this.link;
   }

   public void setReceiver(Value value) {
      this.receiver = value;
   }

   public Value getReceiver() {
      return this.receiver;
   }

   public void setThis(Value value) {
      this.thisRef = value;
   }

   public Value getThis() {
      return this.thisRef;
   }
}
