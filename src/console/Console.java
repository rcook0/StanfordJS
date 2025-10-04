package edu.stanford.cs.console;

public interface Console {
   void print(Object var1);

   void println();

   void println(Object var1);

   void printf(String var1, Object... var2);

   void format(String var1, Object... var2);

   String nextLine();

   String nextLine(String var1);

   int nextInt();

   int nextInt(String var1);

   double nextDouble();

   double nextDouble(String var1);
}
