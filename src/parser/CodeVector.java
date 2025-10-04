package edu.stanford.cs.parser;

import edu.stanford.cs.utf8.UTF8;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class CodeVector {
   private ArrayList<Integer> code = new ArrayList();
   private HashMap<String, Integer> labels = new HashMap();
   private HashMap<String, IntList> labelRefs = new HashMap();
   private HashMap<Integer, IntList> intRefs = new HashMap();
   private HashMap<String, IntList> stringRefs = new HashMap();
   private String source = null;
   private int labelCount = 0;

   public void addWord(int word) {
      this.code.add(word);
   }

   public void addInstruction(int op, int addr) {
      this.addWord(op << 24 | addr);
   }

   public int getCurrentAddress() {
      return this.code.size();
   }

   public String newLabel() {
      return "$" + this.labelCount++;
   }

   public void defineLabel(String label) {
      this.defineSymbol(label, this.getCurrentAddress());
   }

   public void defineSymbol(String label, int value) {
      this.labels.put(label, value);
   }

   public boolean isDefined(String label) {
      return this.labels.containsKey(label);
   }

   public int getLabel(String label) {
      if (this.labels.containsKey(label)) {
         return (Integer)this.labels.get(label);
      } else {
         throw new RuntimeException("Undefined symbol " + label);
      }
   }

   public int labelRef(String label) {
      if (this.labels.containsKey(label)) {
         return (Integer)this.labels.get(label);
      } else {
         IntList refs = (IntList)this.labelRefs.get(label);
         if (refs == null) {
            refs = new IntList();
            this.labelRefs.put(label, refs);
         }

         refs.add(this.code.size());
         return 0;
      }
   }

   public int stringRef(String str) {
      IntList refs = (IntList)this.stringRefs.get(str);
      if (refs == null) {
         refs = new IntList();
         this.stringRefs.put(str, refs);
      }

      refs.add(this.code.size());
      return 0;
   }

   public int intRef(int n) {
      IntList refs = (IntList)this.intRefs.get(n);
      if (refs == null) {
         refs = new IntList();
         this.intRefs.put(n, refs);
      }

      refs.add(this.code.size());
      return 0;
   }

   public int[] getCode() {
      this.resolveReferences();
      int n = this.code.size();
      int[] array = new int[n];

      for(int i = 0; i < n; ++i) {
         array[i] = (Integer)this.code.get(i);
      }

      return array;
   }

   public void setSource(String source) {
      this.source = source;
   }

   public String getSource() {
      return this.source;
   }

   private void resolveReferences() {
      Iterator var2 = this.labelRefs.keySet().iterator();

      String str;
      int addr;
      Iterator var6;
      while(var2.hasNext()) {
         str = (String)var2.next();
         if (!this.labels.containsKey(str)) {
            throw new RuntimeException("Unresolved reference: " + str);
         }

         IntList refs = (IntList)this.labelRefs.get(str);
         int value = (Integer)this.labels.get(str);
         var6 = refs.iterator();

         while(var6.hasNext()) {
            addr = (Integer)var6.next();
            this.code.set(addr, (Integer)this.code.get(addr) + value);
         }
      }

      var2 = this.stringRefs.keySet().iterator();

      int start;
      IntList refs;
      while(var2.hasNext()) {
         str = (String)var2.next();
         start = this.code.size();
         refs = (IntList)this.stringRefs.get(str);
         int[] words = UTF8.encode(str);

         int addr;
         for(addr = 0; addr < words.length; ++addr) {
            this.code.add(words[addr]);
         }

         Iterator var7 = refs.iterator();

         while(var7.hasNext()) {
            addr = (Integer)var7.next();
            this.code.set(addr, (Integer)this.code.get(addr) + start);
         }
      }

      var2 = this.intRefs.keySet().iterator();

      while(var2.hasNext()) {
         int n = (Integer)var2.next();
         start = this.code.size();
         refs = (IntList)this.intRefs.get(n);
         this.code.add(n);
         var6 = refs.iterator();

         while(var6.hasNext()) {
            addr = (Integer)var6.next();
            this.code.set(addr, (Integer)this.code.get(addr) + start);
         }
      }

   }
}
