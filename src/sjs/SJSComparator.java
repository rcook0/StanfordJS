package edu.stanford.cs.sjs;

import edu.stanford.cs.exp.Value;
import java.util.Comparator;

public class SJSComparator implements Comparator<Value> {
   public int compare(Value v1, Value v2) {
      int t1 = v1.getType();
      int t2 = v2.getType();
      if (t1 != 73 && t1 != 68 || t2 != 73 && t2 != 68) {
         return v1.toString().compareTo(v2.toString());
      } else {
         double d1 = v1.getDoubleValue();
         double d2 = v2.getDoubleValue();
         return d1 == d2 ? 0 : (d1 < d2 ? -1 : 1);
      }
   }
}
