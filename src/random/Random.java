package edu.stanford.cs.random;

import java.awt.Color;

public class Random {
   private Random() {
      throw new RuntimeException("Illegal call to Random constructor");
   }

   public static int nextInt(int n) {
      return RandomGenerator.getInstance().nextInt(n);
   }

   public static int nextInt(int low, int high) {
      return RandomGenerator.getInstance().nextInt(low, high);
   }

   public static double nextDouble() {
      return RandomGenerator.getInstance().nextDouble();
   }

   public static double nextDouble(double low, double high) {
      return RandomGenerator.getInstance().nextDouble(low, high);
   }

   public static boolean nextBoolean(double p) {
      return RandomGenerator.getInstance().nextBoolean(p);
   }

   public static boolean nextBoolean() {
      return RandomGenerator.getInstance().nextBoolean();
   }

   public static Color nextColor() {
      return RandomGenerator.getInstance().nextColor();
   }

   public static void setSeed(int seed) {
      RandomGenerator.getInstance().setSeed(seed);
   }
}
