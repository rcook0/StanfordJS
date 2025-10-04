package edu.stanford.cs.sjs;

public class SJSSuffixIncrementOperator extends SJSSuffixIncDecOperator {
   public int getInstructionCode() {
      return 32;
   }

   public int getSign() {
      return 1;
   }
}
