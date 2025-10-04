package edu.stanford.cs.graphics;

public class GTransform {
   private static final double ZERO_RADIUS = 1.0E-10D;
   private double a;
   private double b;
   private double c;
   private double d;
   private double tx;
   private double ty;

   public GTransform() {
      this.setTransform(1.0D, 0.0D, 0.0D, 1.0D, 0.0D, 0.0D);
   }

   public GTransform(GTransform t) {
      this.setTransform(t.a, t.b, t.c, t.d, t.tx, t.ty);
   }

   public GTransform(double a, double b, double c, double d) {
      this.setTransform(a, b, c, d, 0.0D, 0.0D);
   }

   public GTransform(double a, double b, double c, double d, double tx, double ty) {
      this.setTransform(a, b, c, d, tx, ty);
   }

   public void concatenate(GTransform t) {
      this.setTransform(this.a * t.a + this.c * t.b, this.b * t.a + this.d * t.b, this.a * t.c + this.c * t.d, this.b * t.c + this.d * t.d, this.a * t.tx + this.c * t.ty + this.tx, this.b * t.tx + this.d * t.ty + this.ty);
   }

   public GTransform createInverse() {
      double det = this.getDeterminant();
      if (det == 0.0D) {
         throw new RuntimeException("Noninvertible transform");
      } else {
         return new GTransform(this.d / det, -this.b / det, -this.c / det, this.a / det, (this.c * this.ty - this.tx * this.d) / det, (this.b * this.tx - this.a * this.ty) / det);
      }
   }

   public double getDeterminant() {
      return this.a * this.d - this.b * this.c;
   }

   public double getScaleX() {
      return this.a;
   }

   public double getScaleY() {
      return this.d;
   }

   public double getShearX() {
      return this.c;
   }

   public double getShearY() {
      return this.b;
   }

   public double getTranslateX() {
      return this.tx;
   }

   public double getTranslateY() {
      return this.ty;
   }

   public GPoint inverseTransform(GPoint pt) {
      return this.createInverse().transform(pt);
   }

   public GPoint inverseTransform(double x, double y) {
      return this.createInverse().transform(x, y);
   }

   public void rotate(double theta) {
      double cosTheta = GMath.cosDegrees(-theta);
      double sinTheta = GMath.sinDegrees(-theta);
      if (Math.abs(cosTheta) < 1.0E-10D) {
         cosTheta = 0.0D;
      }

      if (Math.abs(sinTheta) < 1.0E-10D) {
         sinTheta = 0.0D;
      }

      this.setTransform(this.a * cosTheta + this.c * sinTheta, this.b * cosTheta + this.d * sinTheta, this.c * cosTheta - this.a * sinTheta, this.d * cosTheta - this.b * sinTheta, this.tx, this.ty);
   }

   public void scale(double sx, double sy) {
      this.a *= sx;
      this.b *= sx;
      this.c *= sy;
      this.d *= sy;
   }

   public void setTransform(double a, double b, double c, double d, double tx, double ty) {
      this.a = a;
      this.b = b;
      this.c = c;
      this.d = d;
      this.tx = tx;
      this.ty = ty;
   }

   public void shear(double sx, double sy) {
      this.setTransform(this.a + sy * this.c, this.c + sx * this.a, this.b + sy * this.d, this.d + sx * this.b, this.tx, this.ty);
   }

   public GPoint transform(GPoint pt) {
      return this.transform(pt.getX(), pt.getY());
   }

   public GPoint transform(double x, double y) {
      return new GPoint(this.a * x + this.c * y + this.tx, this.b * x + this.d * y + this.ty);
   }

   public void translate(double tx, double ty) {
      this.tx += tx * this.a + ty * this.c;
      this.ty += tx * this.b + ty * this.d;
   }

   public String toString() {
      return "[" + this.a + ", " + this.b + ", " + this.c + ", " + this.d + ", " + this.tx + ", " + this.ty + "]";
   }
}
