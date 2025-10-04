package edu.stanford.cs.sjs;

public class SJSIncrementOperator extends SJSPrefixIncDecOperator {
   public int getInstructionCode() {
      return 32;
   }

   public int getSign() {
      return 1;
   }
}
