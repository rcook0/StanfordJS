package edu.stanford.cs.sjslib.core;

import edu.stanford.cs.svm.SVMClass;

public class SJSMathClass extends SVMClass {
   public SJSMathClass() {
      this.defineMethod("PI", new Math_PI());
      this.defineMethod("E", new Math_E());
      this.defineMethod("abs", new Math_abs());
      this.defineMethod("atan", new Math_atan());
      this.defineMethod("atan2", new Math_atan2());
      this.defineMethod("ceil", new Math_ceil());
      this.defineMethod("cos", new Math_cos());
      this.defineMethod("exp", new Math_exp());
      this.defineMethod("floor", new Math_floor());
      this.defineMethod("hypot", new Math_hypot());
      this.defineMethod("log", new Math_log());
      this.defineMethod("log10", new Math_log10());
      this.defineMethod("max", new Math_max());
      this.defineMethod("min", new Math_min());
      this.defineMethod("pow", new Math_pow());
      this.defineMethod("random", new Math_random());
      this.defineMethod("round", new Math_round());
      this.defineMethod("sign", new Math_sign());
      this.defineMethod("sin", new Math_sin());
      this.defineMethod("sqrt", new Math_sqrt());
      this.defineMethod("tan", new Math_tan());
      this.defineMethod("toDegrees", new Math_toDegrees());
      this.defineMethod("toRadians", new Math_toRadians());
   }
}
