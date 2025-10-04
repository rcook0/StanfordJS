package edu.stanford.cs.unittest;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class UnitTest {
   private static String[] METHODS = new String[]{"showErrorMessage", "println"};
   private static double RADIUS = 1.0E-15D;
   private static int errorCount = 0;
   private static Method showErrorMessage = null;

   public static void assertTrue(String msg, boolean exp) {
      if (!exp) {
         ++errorCount;
         if (showErrorMessage == null) {
            System.err.println(msg);
         } else {
            try {
               Object[] args = new Object[]{msg};
               showErrorMessage.invoke(args);
            } catch (InvocationTargetException var3) {
               throw new RuntimeException(var3.toString());
            } catch (IllegalAccessException var4) {
               throw new RuntimeException(var4.toString());
            }
         }
      }

   }

   public static void assertTrue(boolean exp) {
      assertTrue("Failure: " + exp + " != true", exp);
   }

   public static void assertFalse(String msg, boolean exp) {
      assertTrue(msg, !exp);
   }

   public static void assertFalse(boolean exp) {
      assertFalse("Failure: " + exp + " != false", exp);
   }

   public static void assertEquals(String msg, Object exp1, Object exp2) {
      if (exp1 != null && exp2 != null) {
         if (exp1 instanceof Double && exp2 instanceof Number || exp1 instanceof Number && exp2 instanceof Double) {
            double d1 = ((Number)exp1).doubleValue();
            double d2 = ((Number)exp2).doubleValue();
            double r = Math.max(Math.abs(d1), Math.abs(d2)) * RADIUS;
            assertTrue(msg, Math.abs(d1 - d2) <= r);
         } else {
            assertTrue(msg, exp1 == null ? exp2 == null : exp1.equals(exp2));
         }
      } else {
         assertTrue(msg, exp1 == exp2);
      }

   }

   public static void assertEquals(Object exp1, Object exp2) {
      assertEquals("Failure: " + exp1 + " !== " + exp2, exp1, exp2);
   }

   public static void assertNotEquals(String msg, Object exp1, Object exp2) {
      if (exp1 != null && exp2 != null) {
         if (exp1 instanceof Double && exp2 instanceof Number || exp1 instanceof Number && exp2 instanceof Double) {
            double d1 = ((Number)exp1).doubleValue();
            double d2 = ((Number)exp2).doubleValue();
            double r = Math.max(Math.abs(d1), Math.abs(d2)) * RADIUS;
            assertFalse(msg, Math.abs(d1 - d2) <= r);
         } else {
            assertFalse(msg, exp1 == null ? exp2 == null : exp1.equals(exp2));
         }
      } else {
         assertFalse(msg, exp1 == exp2);
      }

   }

   public static void assertNotEquals(Object exp1, Object exp2) {
      assertNotEquals("Failure: " + exp1 + " !== " + exp2, exp1, exp2);
   }

   public static void assertNull(String msg, Object exp) {
      assertEquals(msg, exp, (Object)null);
   }

   public static void assertNull(Object exp) {
      assertNull("Failure: " + exp + " != null", exp);
   }

   public static void assertNotNull(String msg, Object exp) {
      assertNotEquals(msg, exp, (Object)null);
   }

   public static void assertNotNull(Object exp) {
      assertNotNull("Failure: " + exp + " == null", exp);
   }

   public static void assertSame(String msg, Object exp1, Object exp2) {
      assertTrue(msg, exp1 == exp2);
   }

   public static void assertSame(Object exp1, Object exp2) {
      assertSame("Failure: " + exp1 + " != " + exp2, exp1, exp2);
   }

   public static void assertNotSame(String msg, Object exp1, Object exp2) {
      assertTrue(msg, exp1 != exp2);
   }

   public static void assertNotSame(Object exp1, Object exp2) {
      assertNotSame("Failure: " + exp1 + " == " + exp2, exp1, exp2);
   }

   public static void resetErrorCount() {
      errorCount = 0;
   }

   public static int getErrorCount() {
      return errorCount;
   }

   public static void setConsole(Object console) {
      if (console == null) {
         showErrorMessage = null;
      } else {
         String[] var2;
         if ((var2 = METHODS).length != 0) {
            String m = var2[0];

            try {
               Class[] types = new Class[]{String.class};
               showErrorMessage = console.getClass().getMethod(m, types);
            } catch (NoSuchMethodException var4) {
               throw new RuntimeException("No showErrorMessage method");
            }
         }
      }
   }
}
