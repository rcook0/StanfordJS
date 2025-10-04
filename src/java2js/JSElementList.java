package edu.stanford.cs.java2js;

import java.util.ArrayList;
import java.util.Iterator;

public class JSElementList<T> extends ArrayList<T> {
   public JSElementList(Iterable<T> collection) {
      Iterator var3 = collection.iterator();

      while(var3.hasNext()) {
         T value = (Object)var3.next();
         this.add(value);
      }

   }
}
