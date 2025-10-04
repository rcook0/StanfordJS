package edu.stanford.cs.sjs;

public class SJSSuffixDecrementOperator extends SJSSuffixIncDecOperator {
   public int getInstructionCode() {
      return 33;
   }

   public int getSign() {
      return -1;
   }
}
