package edu.stanford.cs.graphics;

class ReverseColorMap {
   private static final int HCAPACITY = 2053;
   private ReverseColorMap.ColorRecord[] hTable = new ReverseColorMap.ColorRecord[2053];

   int getPaletteIndex(int rgb) {
      ReverseColorMap.ColorRecord rec;
      for(int itable = rgb % this.hTable.length; (rec = this.hTable[itable]) != null && rec.rgb != rgb; itable %= this.hTable.length) {
         ++itable;
      }

      return rec != null ? rec.ipalette : -1;
   }

   void put(int rgb, int ipalette) {
      int itable;
      for(itable = rgb % this.hTable.length; this.hTable[itable] != null; itable %= this.hTable.length) {
         ++itable;
      }

      this.hTable[itable] = new ReverseColorMap.ColorRecord(rgb, ipalette);
   }

   private static class ColorRecord {
      int rgb;
      int ipalette;

      ColorRecord(int rgb, int ipalette) {
         this.rgb = rgb;
         this.ipalette = ipalette;
      }
   }
}
