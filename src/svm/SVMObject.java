package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import java.util.Iterator;
import java.util.TreeMap;

public class SVMObject extends TreeMap<String, Value> {
   private SVM svm;

   public SVMObject(SVM svm) {
      this.svm = svm;
   }

   public String toString() {
      if (this.containsKey("toString")) {
         this.svm.push((Value)this.get("toString"));
         this.svm.call(0);
         return this.svm.popString();
      } else {
         String str = "";

         String key;
         for(Iterator var3 = this.keySet().iterator(); var3.hasNext(); str = str + key + ":" + ((Value)this.get(key)).toString()) {
            key = (String)var3.next();
            if (str.length() > 0) {
               str = str + ", ";
            }
         }

         return "{" + str + "}";
      }
   }
}
