package edu.stanford.cs.shellargs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.TreeMap;

public class ShellArgs {
   private TreeMap<String, String> options = new TreeMap();
   private ArrayList<String> files = new ArrayList();

   public ShellArgs(String[] args, String[] optionSpec) {
      int nArgs = args.length;
      String key = null;
      String value = "true";

      for(int i = 0; i < nArgs; ++i) {
         String arg = args[i];
         if (arg.startsWith("-") && arg.length() > 1) {
            int index = this.findOptionSpec(arg, optionSpec);
            String spec = optionSpec[index];
            int space = spec.indexOf(" ");
            if (space == -1) {
               key = spec;
            } else {
               key = spec.substring(0, space);
               ++i;
               if (i == nArgs) {
                  throw new RuntimeException("Missing value after " + arg);
               }

               String str;
               for(str = args[i]; i + 1 < nArgs && args[i + 1].equals("+"); str = str + args[i]) {
                  i += 2;
                  if (i == nArgs) {
                     throw new RuntimeException("Missing value after +");
                  }
               }

               value = str;
            }

            this.options.put(key, value);
         } else {
            this.files.add(arg);
         }
      }

   }

   public String[] getFiles() {
      return (String[])this.files.toArray(new String[this.files.size()]);
   }

   public boolean isOptionSpecified(String key) {
      return this.options.containsKey(key);
   }

   public String getOption(String key) {
      return (String)this.options.get(key);
   }

   public String getOption(String key, String def) {
      String str = (String)this.options.get(key);
      return str == null ? def : str;
   }

   public int getIntOption(String key) {
      return this.getIntOption(key, 0);
   }

   public int getIntOption(String key, int def) {
      String str = (String)this.options.get(key);
      return str == null ? def : Integer.parseInt(str);
   }

   public double getDoubleOption(String key) {
      return this.getDoubleOption(key, 0.0D);
   }

   public double getDoubleOption(String key, double def) {
      String str = (String)this.options.get(key);
      return str == null ? def : Double.parseDouble(str);
   }

   public boolean getBooleanOption(String key) {
      return this.getBooleanOption(key, false);
   }

   public boolean getBooleanOption(String key, boolean def) {
      String str = (String)this.options.get(key);
      if (str != null && !str.isEmpty()) {
         if ("false".startsWith(str.toLowerCase())) {
            return false;
         } else if ("true".startsWith(str.toLowerCase())) {
            return true;
         } else {
            throw new RuntimeException("Illegal boolean value");
         }
      } else {
         return def;
      }
   }

   public double getUnitsOption(String key) {
      return this.getUnitsOption(key, 0.0D);
   }

   public double getUnitsOption(String key, double def) {
      String str = (String)this.options.get(key);
      if (str != null && !str.isEmpty()) {
         double units = 1.0D;
         if (str.endsWith("pt")) {
            str = str.substring(0, str.length() - 2);
         } else if (str.endsWith("px")) {
            str = str.substring(0, str.length() - 2);
         } else if (str.endsWith("i")) {
            str = str.substring(0, str.length() - 1);
            units = 72.0D;
         } else if (str.endsWith("in")) {
            str = str.substring(0, str.length() - 2);
            units = 72.0D;
         } else if (str.endsWith("cm")) {
            str = str.substring(0, str.length() - 2);
            units = 28.346456692913385D;
         }

         return Double.parseDouble(str) * units;
      } else {
         return def;
      }
   }

   public Color getColorOption(String key) {
      return this.getColorOption(key, (Color)null);
   }

   public Color getColorOption(String key, Color def) {
      String str = (String)this.options.get(key);
      if (str != null && !str.isEmpty()) {
         if (str.equalsIgnoreCase("black")) {
            return Color.BLACK;
         } else if (str.equalsIgnoreCase("blue")) {
            return Color.BLUE;
         } else if (str.equalsIgnoreCase("cyan")) {
            return Color.CYAN;
         } else if (str.equalsIgnoreCase("darkGray")) {
            return Color.DARK_GRAY;
         } else if (str.equalsIgnoreCase("DARK_GRAY")) {
            return Color.DARK_GRAY;
         } else if (str.equalsIgnoreCase("gray")) {
            return Color.GRAY;
         } else if (str.equalsIgnoreCase("green")) {
            return Color.GREEN;
         } else if (str.equalsIgnoreCase("lightGray")) {
            return Color.LIGHT_GRAY;
         } else if (str.equalsIgnoreCase("LIGHT_GRAY")) {
            return Color.LIGHT_GRAY;
         } else if (str.equalsIgnoreCase("magenta")) {
            return Color.MAGENTA;
         } else if (str.equalsIgnoreCase("orange")) {
            return Color.ORANGE;
         } else if (str.equalsIgnoreCase("pink")) {
            return Color.PINK;
         } else if (str.equalsIgnoreCase("red")) {
            return Color.RED;
         } else if (str.equalsIgnoreCase("white")) {
            return Color.WHITE;
         } else if (str.equalsIgnoreCase("yellow")) {
            return Color.YELLOW;
         } else {
            if (str.startsWith("0x")) {
               str = str.substring(2);
            } else if (str.startsWith("#")) {
               str = str.substring(1);
            }

            if (!this.isHexString(str)) {
               throw new RuntimeException("Illegal color option");
            } else {
               int nDigits = str.length();
               int aa = 255;
               if (nDigits == 8) {
                  aa = Integer.parseInt(str.substring(0, 2), 16);
                  str = str.substring(2);
               } else if (nDigits != 6) {
                  throw new RuntimeException("Color options must have 6 or 8 hexadecimal digits");
               }

               return new Color(Integer.parseInt(str, 16) | aa << 24, true);
            }
         }
      } else {
         return def;
      }
   }

   public void showUsage(String usage, String[] optionSpec) {
      System.out.println("Usage: " + usage);
      System.out.println("  where <options> can be any of the following:");
      String[] var6 = optionSpec;
      int var5 = optionSpec.length;

      for(int var4 = 0; var4 < var5; ++var4) {
         String option = var6[var4];
         System.out.println("   " + option);
      }

   }

   private boolean isHexString(String str) {
      int n = str.length();

      for(int i = 0; i < n; ++i) {
         if ("0123456789ABCDEFabcdef".indexOf(str.substring(i, i + 1)) == -1) {
            return false;
         }
      }

      return true;
   }

   private int findOptionSpec(String arg, String[] optionSpec) {
      int index = -1;
      int nSpec = optionSpec.length;

      for(int i = 0; i < nSpec; ++i) {
         if (optionSpec[i].startsWith(arg)) {
            if (index != -1) {
               throw new RuntimeException("Ambiguous option: " + arg);
            }

            index = i;
         }
      }

      if (index == -1) {
         throw new RuntimeException("Unrecognized option: " + arg);
      } else {
         return index;
      }
   }
}
