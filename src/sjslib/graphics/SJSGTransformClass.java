package edu.stanford.cs.sjslib.graphics;

import edu.stanford.cs.svm.SVMClass;

public class SJSGTransformClass extends SVMClass {
   public SJSGTransformClass() {
      this.defineMethod("new", new GTransform_new());
      this.defineMethod("concatenate", new GTransform_concatenate());
      this.defineMethod("createInverse", new GTransform_createInverse());
      this.defineMethod("getDeterminant", new GTransform_getDeterminant());
      this.defineMethod("getScaleX", new GTransform_getScaleX());
      this.defineMethod("getScaleY", new GTransform_getScaleY());
      this.defineMethod("getShearX", new GTransform_getShearX());
      this.defineMethod("getShearY", new GTransform_getShearY());
      this.defineMethod("getTranslateX", new GTransform_getTranslateX());
      this.defineMethod("getTranslateY", new GTransform_getTranslateY());
      this.defineMethod("inverseTransform", new GTransform_inverseTransform());
      this.defineMethod("rotate", new GTransform_rotate());
      this.defineMethod("scale", new GTransform_scale());
      this.defineMethod("setTransform", new GTransform_setTransform());
      this.defineMethod("shear", new GTransform_shear());
      this.defineMethod("transform", new GTransform_transform());
      this.defineMethod("translate", new GTransform_translate());
   }
}
