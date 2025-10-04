package edu.stanford.cs.svm;

import edu.stanford.cs.exp.Value;
import java.util.ArrayList;

public class SVMEventClosure {
   private Value fn;
   private ArrayList<Value> args;

   public SVMEventClosure(Value fn) {
      this.fn = fn;
      this.args = new ArrayList();
   }

   public void add(Value arg) {
      this.args.add(arg);
   }

   public int getArgumentCount() {
      return this.args.size();
   }

   public void pushEventData(SVM svm) {
      for(int i = this.args.size() - 1; i >= 0; --i) {
         svm.push((Value)this.args.get(i));
      }

      svm.push(this.fn);
   }
}
