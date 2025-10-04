package edu.stanford.cs.random;

import java.awt.Color;

public class RandomGenerator {
   private static final int MULTIPLIER = 31421;
   private static final int INCREMENT = 6927;
   private static RandomGenerator standardInstance = new RandomGenerator();
   private int s1;
   private int s2;

   public RandomGenerator() {
      this.setSeed((int)(System.currentTimeMillis() & 2147483647L));
   }

   public int nextInt(int n) {
      return (int)((double)n * this.nextDouble());
   }

   public int nextInt(int low, int high) {
      return low + (int)((double)(high - low + 1) * this.nextDouble());
   }

   public double nextDouble() {
      return (double)this.next() / 1.073741824E9D;
   }

   public double nextDouble(double low, double high) {
      return low + (high - low) * this.nextDouble();
   }

   public boolean nextBoolean(double p) {
      return this.nextDouble() < p;
   }

   public boolean nextBoolean() {
      return this.nextBoolean(0.5D);
   }

   public Color nextColor() {
      return new Color(this.nextInt(256), this.nextInt(256), this.nextInt(256));
   }

   public void setSeed(int seed) {
      this.s1 = seed & '\uffff';
      this.s2 = (seed ^ seed >> 8) & '\uffff';
   }

   public static RandomGenerator getInstance() {
      if (standardInstance == null) {
         standardInstance = new RandomGenerator();
      }

      return standardInstance;
   }

   private int next() {
      this.s1 = this.s1 * 31421 + 6927 & '\uffff';
      this.s2 = this.s2 * 31421 + 6927 & '\uffff';
      return (this.s2 & 16383) << 16 | this.s1;
   }
}
