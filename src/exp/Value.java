package edu.stanford.cs.exp;

import java.util.TreeMap;

public class Value {
   public static final int ASSIGNABLE = 65;
   public static final int BOOLEAN = 66;
   public static final int CHARACTER = 67;
   public static final int DOUBLE = 68;
   public static final int FUNCTION = 70;
   public static final int INTEGER = 73;
   public static final int LONG = 76;
   public static final int OBJECT = 79;
   public static final int REF = 82;
   public static final int STRING = 83;
   public static final int VOID = 86;
   public static final Value TRUE = new Value(66, true);
   public static final Value FALSE = new Value(66, false);
   public static final Value NULL = new Value(82, "null");
   public static final Value UNDEFINED = new Value(86, "undefined");
   private int type;
   private Object value;
   private String className;
   private TreeMap<String, Value> properties;

   public Value(int type, Object value) {
      this.type = type;
      this.value = value;
      this.properties = null;
      switch(type) {
      case 66:
         this.className = "Boolean";
         break;
      case 67:
         this.className = "Character";
         break;
      case 68:
         this.className = "Double";
         break;
      case 73:
         this.className = "Integer";
         break;
      case 76:
         this.className = "Long";
         break;
      case 79:
         this.className = "Object";
         break;
      case 83:
         this.className = "String";
      }

   }

   public int getType() {
      return this.type;
   }

   public String getClassName() {
      return this.className;
   }

   public void setClassName(String name) {
      this.className = name;
   }

   public Object getValue() {
      return this.value;
   }

   public void setProperty(String name, Value value) {
      if (this.properties == null) {
         this.properties = new TreeMap();
      }

      this.properties.put(name, value);
   }

   public Value getProperty(String name) {
      return this.properties == null ? null : (Value)this.properties.get(name);
   }

   public String toString() {
      if (this.value == null) {
         return "null";
      } else {
         return this.isIntegral() ? "" + this.getIntegerValue() : this.value.toString();
      }
   }

   public boolean isIntegral() {
      switch(this.type) {
      case 68:
         double d = (Double)this.value;
         if ((double)((int)d) == d) {
            return true;
         }

         return false;
      case 73:
         return true;
      default:
         return false;
      }
   }

   public boolean isNumeric() {
      switch(this.type) {
      case 68:
      case 73:
         return true;
      default:
         return false;
      }
   }

   public boolean isLValue() {
      return false;
   }

   public int getIntegerValue() {
      switch(this.type) {
      case 68:
         double d = (Double)this.value;
         if ((double)((int)d) == d) {
            return (int)d;
         }

         throw new RuntimeException("Illegal integer");
      case 73:
         return (Integer)this.value;
      default:
         throw new RuntimeException("Illegal integer");
      }
   }

   public double getDoubleValue() {
      switch(this.type) {
      case 68:
         return (Double)this.value;
      case 73:
         return (double)(Integer)this.value;
      default:
         throw new RuntimeException("Illegal double");
      }
   }

   public String getStringValue() {
      return this.toString();
   }

   public boolean getBooleanValue() {
      if (this.type != 66) {
         throw new RuntimeException("Illegal boolean");
      } else {
         return (Boolean)this.value;
      }
   }

   public static Value createInteger(int n) {
      return new Value(73, n);
   }

   public static Value createDouble(double d) {
      return new Value(68, d);
   }

   public static Value createBoolean(boolean b) {
      return new Value(66, b);
   }

   public static Value createCharacter(char ch) {
      return new Value(67, ch);
   }

   public static Value createString(String s) {
      return new Value(83, s);
   }

   public static Value createObject(Object obj, String className) {
      Value value = new Value(79, obj);
      value.setClassName(className);
      return value;
   }
}
