package edu.stanford.cs.sjs;

public class SJSDecrementOperator extends SJSPrefixIncDecOperator {
   public int getInstructionCode() {
      return 33;
   }

   public int getSign() {
      return -1;
   }
}
