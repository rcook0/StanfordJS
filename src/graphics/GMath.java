package edu.stanford.cs.graphics;

public class GMath {
   protected GMath() {
   }

   public static int round(double x) {
      return (int)Math.round(x);
   }

   public static double sinDegrees(double angle) {
      return Math.sin(toRadians(angle));
   }

   public static double cosDegrees(double angle) {
      return Math.cos(toRadians(angle));
   }

   public static double tanDegrees(double angle) {
      return sinDegrees(angle) / cosDegrees(angle);
   }

   public static double toDegrees(double radians) {
      return radians * 180.0D / 3.141592653589793D;
   }

   public static double toRadians(double degrees) {
      return degrees * 3.141592653589793D / 180.0D;
   }

   public static double distance(double x, double y) {
      return Math.sqrt(x * x + y * y);
   }

   public static double distance(double x0, double y0, double x1, double y1) {
      return distance(x1 - x0, y1 - y0);
   }

   public static double angle(double x, double y) {
      return x == 0.0D && y == 0.0D ? 0.0D : toDegrees(Math.atan2(-y, x));
   }

   public static double angle(double x0, double y0, double x1, double y1) {
      return angle(x1 - x0, y1 - y0);
   }
}
