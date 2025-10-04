package edu.stanford.cs.graphics;

public interface GContainer {
   int BACK_TO_FRONT = 0;
   int FRONT_TO_BACK = 1;

   void add(GObject var1);

   void add(GObject var1, double var2, double var4);

   void add(GObject var1, GPoint var2);

   void remove(GObject var1);

   void removeAll();

   int getElementCount();

   GObject getElement(int var1);

   GObject getElementAt(double var1, double var3);

   GObject getElementAt(GPoint var1);
}
