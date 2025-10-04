package edu.stanford.cs.jseditor;

import java.util.HashMap;
import java.util.Iterator;
import javax.swing.text.TabSet;
import javax.swing.text.TabStop;

public class FixedTabSet extends TabSet {
   private HashMap<Integer, TabStop> tabStops = new HashMap();
   private int tabWidth;

   public FixedTabSet(int width) {
      super(new TabStop[0]);
      this.tabWidth = width;
   }

   public boolean equals(Object obj) {
      return obj instanceof FixedTabSet && this.tabWidth == ((FixedTabSet)obj).tabWidth;
   }

   public TabStop getTab(int index) {
      return this.makeTab(this.tabWidth * index);
   }

   public TabStop getTabAfter(float location) {
      return this.makeTab(this.tabWidth * ((int)Math.floor((double)(location / (float)this.tabWidth)) + 1));
   }

   public int getTabIndex(TabStop tab) {
      Iterator var3 = this.tabStops.keySet().iterator();

      while(var3.hasNext()) {
         int index = (Integer)var3.next();
         if (this.tabStops.get(index) == tab) {
            return index;
         }
      }

      return -1;
   }

   public int getTabIndexAfter(float location) {
      return this.getTabIndex(this.getTabAfter(location));
   }

   public int hashCode() {
      return this.tabWidth;
   }

   public String toString() {
      return "FixedTabSet(" + this.tabWidth + ")";
   }

   private TabStop makeTab(int location) {
      TabStop tab = (TabStop)this.tabStops.get(location);
      if (tab == null) {
         tab = new TabStop((float)location);
         this.tabStops.put(location, tab);
      }

      return tab;
   }
}
