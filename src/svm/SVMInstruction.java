package edu.stanford.cs.svm;

import edu.stanford.cs.parser.CodeVector;
import edu.stanford.cs.tokenscanner.TokenScanner;
import java.util.TreeMap;

public abstract class SVMInstruction {
   private static TreeMap<String, SVMInstruction> instructionTable = null;
   private static TreeMap<Integer, SVMInstruction> codeTable = null;
   private String name;
   private int code;

   public SVMInstruction(String name, int code) {
      this.name = name;
      this.code = code;
   }

   public String getName() {
      return this.name;
   }

   public int getCode() {
      return this.code;
   }

   public void execute(SVM svm, int addr) {
      throw new RuntimeException("Not yet implemented");
   }

   public String unparse(SVM svm, int addr) {
      return this.name;
   }

   public void assemble(CodeVector cv, TokenScanner scanner) {
      cv.addWord(this.code << 24);
   }

   public static SVMInstruction lookup(String name) {
      if (instructionTable == null) {
         initializeInstructionTable();
      }

      return (SVMInstruction)instructionTable.get(name);
   }

   public static SVMInstruction get(int code) {
      if (instructionTable == null) {
         initializeInstructionTable();
      }

      return (SVMInstruction)codeTable.get(code);
   }

   private static void initializeInstructionTable() {
      instructionTable = new TreeMap();
      codeTable = new TreeMap();
      define(new END_Ins());
      define(new VERSION_Ins());
      define(new PSTACK_Ins());
      define(new STMT_Ins());
      define(new HALT_Ins());
      define(new PUSHINT_Ins());
      define(new PUSHNUM_Ins());
      define(new PUSHSTR_Ins());
      define(new PUSHFN_Ins());
      define(new FLUSH_Ins());
      define(new DUP_Ins());
      define(new ROLL_Ins());
      define(new ADD_Ins());
      define(new SUB_Ins());
      define(new MUL_Ins());
      define(new DIV_Ins());
      define(new IDIV_Ins());
      define(new REM_Ins());
      define(new NEG_Ins());
      define(new EQ_Ins());
      define(new NE_Ins());
      define(new LT_Ins());
      define(new LE_Ins());
      define(new GT_Ins());
      define(new GE_Ins());
      define(new JUMP_Ins());
      define(new JUMPT_Ins());
      define(new JUMPF_Ins());
      define(new DISPATCH_Ins());
      define(new TRY_Ins());
      define(new ENDTRY_Ins());
      define(new THROW_Ins());
      define(new NOT_Ins());
      define(new AND_Ins());
      define(new OR_Ins());
      define(new XOR_Ins());
      define(new LSH_Ins());
      define(new ASH_Ins());
      define(new CALL_Ins());
      define(new CALLM_Ins());
      define(new CALLFN_Ins());
      define(new RETURN_Ins());
      define(new LOCALS_Ins());
      define(new PUSHLOC_Ins());
      define(new POPLOC_Ins());
      define(new ARG_Ins());
      define(new VAR_Ins());
      define(new PARAMS_Ins());
      define(new NARGS_Ins());
      define(new VARGS_Ins());
      define(new PUSHVAR_Ins());
      define(new POPVAR_Ins());
   }

   private static void define(SVMInstruction ins) {
      instructionTable.put(ins.getName(), ins);
      codeTable.put(ins.getCode(), ins);
   }
}
