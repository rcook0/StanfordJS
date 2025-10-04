package edu.stanford.cs.psgraphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Composite;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.PixelGrabber;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.AttributedCharacterIterator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class PSGraphics extends Graphics2D {
   private Color bg;
   private Color fg;
   private Font font;
   private Component comp;
   private ImageObserver observer;
   private PrintWriter wr;
   private Rectangle clip;
   private Stack<GraphicsState> stack;
   private HashMap<Image, String> images;
   private boolean topLevel;

   public PSGraphics(String filename, Component comp) {
      try {
         this.wr = new PrintWriter(new BufferedWriter(new FileWriter(filename)));
         this.writeHeader();
         this.comp = comp;
         this.stack = new Stack();
         this.images = new HashMap();
         this.observer = new DummyObserver();
         Dimension size = comp.getSize();
         this.setClip(0, 0, size.width, size.height);
         this.setBackground(comp.getBackground());
         this.setColor(comp.getBackground());
         this.wr.println("clippath fill");
         this.setColor(comp.getForeground());
         this.topLevel = true;
      } catch (IOException var4) {
         throw new RuntimeException(var4);
      }
   }

   private PSGraphics() {
   }

   public Graphics create() {
      this.wr.printf("gsave%n");
      PSGraphics g = new PSGraphics();
      g.bg = this.bg;
      g.fg = this.fg;
      g.comp = this.comp;
      g.font = this.font;
      g.wr = this.wr;
      g.clip = this.clip;
      g.stack = this.stack;
      g.images = this.images;
      g.topLevel = false;
      GraphicsState gs = new GraphicsState();
      gs.fg = this.fg;
      gs.font = this.font;
      gs.clip = this.clip;
      this.stack.push(gs);
      return g;
   }

   public void translate(int x, int y) {
      this.wr.printf("%d %d translate%n", x, y);
   }

   public Color getColor() {
      return this.fg;
   }

   public void setColor(Color c) {
      this.wr.printf("16#%X sethexcolor%n", c.getRGB());
      this.fg = c;
   }

   public void setPaintMode() {
      throw new RuntimeException("setPaintMode: Not yet implemented");
   }

   public void setXORMode(Color c1) {
      throw new RuntimeException("setXORMode: Not yet implemented");
   }

   public Font getFont() {
      return this.font;
   }

   public void setFont(Font font) {
      this.font = font;
      this.wr.printf("/%s findfont %d scalefont setfont%n", this.getPSName(font), font.getSize());
   }

   private String getPSName(Font font) {
      String family = font.getPSName();
      String base = null;
      if (family.equals("Serif")) {
         base = "TimesNewRomanPS";
      } else if (family.equals("SansSerif")) {
         base = "HelveticaNeuePS";
      } else if (family.equals("Monospaced")) {
         base = "CourierNewPS";
      }

      if (base != null) {
         String style = "";
         if (font.isBold()) {
            style = style + "Bold";
         }

         if (font.isItalic()) {
            style = style + "Italic";
         }

         if (style.length() > 0) {
            base = base + "-";
         }

         family = base + style + "MT";
      }

      return family;
   }

   public FontMetrics getFontMetrics(Font font) {
      String family = font.getFamily();
      if (family.equals("Serif")) {
         font = new Font("Times New Roman", font.getStyle(), font.getSize());
      } else if (family.equals("SansSerif")) {
         font = new Font("Helvetica Neue", font.getStyle(), font.getSize());
      } else if (family.equals("Monospaced")) {
         font = new Font("Courier New", font.getStyle(), font.getSize());
      }

      return this.comp.getFontMetrics(font);
   }

   public Rectangle getClipBounds() {
      return this.clip;
   }

   public void clipRect(int x, int y, int width, int height) {
      this.clip = new Rectangle(x, y, width, height);
      this.wr.printf("%d %d %d %d rectclip%n", x, y, width, height);
   }

   public void setClip(int x, int y, int width, int height) {
      if (this.clip == null) {
         this.clip = new Rectangle(x, y, width, height);
      } else {
         this.clip = this.clip.intersection(new Rectangle(x, y, width, height));
      }

      this.wr.printf("%d %d %d %d rectclip%n", x, y, width, height);
   }

   public Shape getClip() {
      return this.clip;
   }

   public void setClip(Shape clip) {
      throw new RuntimeException("setClip: Not yet implemented");
   }

   public void copyArea(int x, int y, int width, int height, int dx, int dy) {
      throw new RuntimeException("copyArea: Not yet implemented");
   }

   public void drawLine(int x1, int y1, int x2, int y2) {
      this.wr.printf("%d %d %d %d drawLineTo%n", x1, y1, x2, y2);
   }

   public void drawRect(int x, int y, int width, int height) {
      this.wr.printf("%d %d %d %d boxPath stroke%n", x, y, width, height);
   }

   public void fillRect(int x, int y, int width, int height) {
      if (this.fg.getRGB() != -1118482) {
         this.wr.printf("%d %d %d %d boxPath fill%n", x, y, width, height);
      }
   }

   public void clearRect(int x, int y, int width, int height) {
      throw new RuntimeException("clearRect: Not yet implemented");
   }

   public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
      throw new RuntimeException("drawRoundRect: Not yet implemented");
   }

   public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
      throw new RuntimeException("fillRoundRect: Not yet implemented");
   }

   public void drawOval(int x, int y, int width, int height) {
      this.psOval(x, y, width, height, "stroke");
   }

   public void fillOval(int x, int y, int width, int height) {
      this.psOval(x, y, width, height, "fill");
   }

   private void psOval(int x, int y, int width, int height, String op) {
      this.wr.printf("gsave%n");
      this.wr.printf("  %d %d 2 div add %d %d 2 div add translate%n", x, width, y, height);
      this.wr.printf("  1 %d %d div scale%n", height, width);
      this.wr.printf("  newpath 0 0 %d 2 div 0 360 arc closepath%n");
      this.wr.printf("  1 %d %d div scale%n", width, height);
      this.wr.printf("  %s%n", op);
      this.wr.printf("grestore%n");
   }

   public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
      throw new RuntimeException("drawArc: Not yet implemented");
   }

   public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
      throw new RuntimeException("fillArc: Not yet implemented");
   }

   public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
      throw new RuntimeException("drawPolyline: Not yet implemented");
   }

   public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
      this.psPolygon(xPoints, yPoints, nPoints, "stroke");
   }

   public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
      this.psPolygon(xPoints, yPoints, nPoints, "fill");
   }

   private void psPolygon(int[] xp, int[] yp, int nPoints, String op) {
      this.wr.printf("newpath%n");

      for(int i = 0; i < nPoints; ++i) {
         this.wr.printf("%d %d %s%n", xp[i], yp[i], i == 0 ? "moveto" : "lineto");
      }

      this.wr.printf("closepath %s%n", op);
   }

   public void drawString(String str, int x, int y) {
      this.wr.printf("gsave%n");
      this.wr.printf("  %d %d translate%n", x, y);
      this.wr.printf("  1 -1 scale%n");
      this.wr.printf("  0 0 (%s) lshow%n", str);
      this.wr.printf("grestore%n");
   }

   public void drawString(AttributedCharacterIterator iterator, int x, int y) {
      throw new RuntimeException("drawString: Not yet implemented");
   }

   public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
      this.psImage(img, x, y, 1.0D, 1.0D);
      return true;
   }

   public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
      this.psImage(img, x, y, (double)width / (double)img.getWidth(observer), (double)height / (double)img.getHeight(observer));
      return true;
   }

   private void psImage(Image img, int x, int y, double sx, double sy) {
      String name = (String)this.images.get(img);
      if (name == null) {
         name = "IMAGE-" + this.images.size();
         this.images.put(img, name);
      }

      this.wr.printf("/iy 0 def%n");
      this.wr.printf("%d %d 8 [%g 0 0 %g 0 0]%n", img.getWidth(this.observer), img.getHeight(this.observer), 1.0D / sx, 1.0D / sy);
      this.wr.printf("{ %s iy get /iy iy 1 add def }%n", name);
      this.wr.printf("false 3 colorimage%n");
   }

   public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
      throw new RuntimeException("drawImage: Not yet implemented");
   }

   public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
      throw new RuntimeException("drawImage: Not yet implemented");
   }

   public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
      throw new RuntimeException("drawImage: Not yet implemented");
   }

   public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
      throw new RuntimeException("drawImage: Not yet implemented");
   }

   public void dispose() {
      if (this.topLevel) {
         this.writeTrailer();
         this.wr.close();
         Toolkit.getDefaultToolkit().beep();
      } else if (!this.stack.isEmpty() && this.getFont() != null) {
         this.wr.printf("grestore%n");
         GraphicsState gs = (GraphicsState)this.stack.pop();
         this.fg = gs.fg;
         this.font = gs.font;
         this.clip = gs.clip;
      }

   }

   public void draw(Shape s) {
      throw new RuntimeException("draw: Not yet implemented");
   }

   public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
      throw new RuntimeException("drawImage: Not yet implemented");
   }

   public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
      throw new RuntimeException("drawImage: Not yet implemented");
   }

   public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
      throw new RuntimeException("drawRenderedImage: Not yet implemented");
   }

   public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
      throw new RuntimeException("drawRenderableImage: Not yet implemented");
   }

   public void drawString(String str, float x, float y) {
      throw new RuntimeException("drawString: Not yet implemented");
   }

   public void drawString(AttributedCharacterIterator iterator, float x, float y) {
      throw new RuntimeException("drawString: Not yet implemented");
   }

   public void drawGlyphVector(GlyphVector g, float x, float y) {
      throw new RuntimeException("drawGlyphVector: Not yet implemented");
   }

   public void fill(Shape s) {
      throw new RuntimeException("fill: Not yet implemented");
   }

   public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
      throw new RuntimeException("hit: Not yet implemented");
   }

   public GraphicsConfiguration getDeviceConfiguration() {
      throw new RuntimeException("getDeviceConfiguration: Not yet implemented");
   }

   public void setComposite(Composite comp) {
      throw new RuntimeException("setComposite: Not yet implemented");
   }

   public void setPaint(Paint paint) {
      throw new RuntimeException("setPaint: Not yet implemented");
   }

   public void setStroke(Stroke s) {
      throw new RuntimeException("setStroke: Not yet implemented");
   }

   public void setRenderingHint(Key hintKey, Object hintValue) {
   }

   public Object getRenderingHint(Key hintKey) {
      throw new RuntimeException("getRenderingHint: Not yet implemented");
   }

   public void setRenderingHints(Map<?, ?> hints) {
      throw new RuntimeException("setRenderingHints: Not yet implemented");
   }

   public void addRenderingHints(Map<?, ?> hints) {
      throw new RuntimeException("addRenderingHints: Not yet implemented");
   }

   public RenderingHints getRenderingHints() {
      throw new RuntimeException("getRenderingHints: Not yet implemented");
   }

   public void translate(double tx, double ty) {
      throw new RuntimeException("translate: Not yet implemented");
   }

   public void rotate(double theta) {
      this.wr.printf("%g rotate%n", theta * 180.0D / 3.141592653589793D);
   }

   public void rotate(double theta, double x, double y) {
      throw new RuntimeException("rotate: Not yet implemented");
   }

   public void scale(double sx, double sy) {
      this.wr.printf("%g %g scale%n", sx, sy);
   }

   public void shear(double shx, double shy) {
      throw new RuntimeException("shear: Not yet implemented");
   }

   public void transform(AffineTransform Tx) {
      throw new RuntimeException("transform: Not yet implemented");
   }

   public void setTransform(AffineTransform Tx) {
      throw new RuntimeException("setTransform: Not yet implemented");
   }

   public AffineTransform getTransform() {
      throw new RuntimeException("getTransform: Not yet implemented");
   }

   public Paint getPaint() {
      throw new RuntimeException("getPaint: Not yet implemented");
   }

   public Composite getComposite() {
      throw new RuntimeException("getComposite: Not yet implemented");
   }

   public void setBackground(Color color) {
      this.bg = color;
   }

   public Color getBackground() {
      return this.bg;
   }

   public Stroke getStroke() {
      throw new RuntimeException("getStroke: Not yet implemented");
   }

   public void clip(Shape s) {
      throw new RuntimeException("clip: Not yet implemented");
   }

   public FontRenderContext getFontRenderContext() {
      throw new RuntimeException("getFontRenderContext: Not yet implemented");
   }

   private void writeHeader() {
      this.wr.println("#include <defs.ps>");
      this.wr.println("/drawScreenImage {");
      this.wr.println("1 1001 translate");
      this.wr.println("1 -1 scale");
      this.wr.println();
   }

   private void writeTrailer() {
      this.wr.println("} def");
      this.wr.println();
      this.dumpImages();
      this.wr.println("drawScreenImage");
   }

   private int[][] getPixelArray(Image image) {
      int width = image.getWidth(this.observer);
      int height = image.getHeight(this.observer);
      int[] pixels = new int[width * height];
      int[][] array = new int[height][width];
      PixelGrabber pg = new PixelGrabber(image.getSource(), 0, 0, width, height, pixels, 0, width);

      try {
         pg.grabPixels();
      } catch (InterruptedException var8) {
         throw new RuntimeException("Transfer interrupted");
      }

      if ((pg.getStatus() & 128) != 0) {
         throw new RuntimeException("Transfer aborted");
      } else {
         for(int i = 0; i < height; ++i) {
            System.arraycopy(pixels, i * width, array[i], 0, width);
         }

         return array;
      }
   }

   private void dumpImages() {
      Iterator var2 = this.images.keySet().iterator();

      while(var2.hasNext()) {
         Image img = (Image)var2.next();
         String name = (String)this.images.get(img);
         int[][] pixels = this.getPixelArray(img);
         int width = pixels[0].length;
         int height = pixels.length;
         int bg = this.comp.getBackground().getRGB();
         this.wr.printf("/%s [%n", name);

         for(int i = 0; i < height; ++i) {
            int ns = 0;

            for(int j = 0; j < width; ++j) {
               int pixel = pixels[i][j];
               if (pixel >= 0) {
                  pixel = bg;
               }

               if (ns == 0) {
                  this.wr.printf("  <");
               } else if (ns % 8 == 0) {
                  this.wr.printf("%n   ");
               }

               this.wr.printf("%06X", pixel & 16777215);
               ++ns;
            }

            this.wr.printf(">%n");
         }

         this.wr.printf("] def%n");
      }

   }
}
