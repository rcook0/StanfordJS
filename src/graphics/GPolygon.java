package edu.stanford.cs.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Path2D;
import java.awt.geom.Path2D.Double;

public class GPolygon extends GObject implements GFillable {
   private VertexList vertices;
   private boolean isFilled;
   private Color fillColor;

   public GPolygon() {
      this.vertices = new VertexList();
      this.clear();
   }

   public GPolygon(double x, double y) {
      this();
      this.setLocation(x, y);
   }

   public GPolygon(GPoint[] points) {
      this();
      this.vertices.add(points);
   }

   public void addVertex(double x, double y) {
      this.vertices.addVertex(x, y);
   }

   public void addEdge(double dx, double dy) {
      this.vertices.addEdge(dx, dy);
   }

   public final void addPolarEdge(double r, double theta) {
      this.vertices.addEdge(r * GMath.cosDegrees(theta), -r * GMath.sinDegrees(theta));
   }

   public void addArc(double arcWidth, double arcHeight, double start, double sweep) {
      this.vertices.addArc(arcWidth, arcHeight, start, sweep);
   }

   public GPoint getCurrentPoint() {
      return this.vertices.getCurrentPoint();
   }

   public void setFilled(boolean fill) {
      this.isFilled = fill;
      this.repaint();
   }

   public boolean isFilled() {
      return this.isFilled;
   }

   public void setFillColor(Color color) {
      this.fillColor = color;
      this.repaint();
   }

   public Color getFillColor() {
      return this.fillColor == null ? this.getColor() : this.fillColor;
   }

   protected GRectangle localBounds(GTransform ctm) {
      int nPoints = this.vertices.size();
      if (nPoints == 0) {
         return new GRectangle();
      } else {
         GRectangle bb = new GRectangle(ctm.transform(this.vertices.get(0)));

         for(int i = 1; i < nPoints; ++i) {
            bb.add(ctm.transform(this.vertices.get(i)));
         }

         return bb;
      }
   }

   protected boolean localContains(double x, double y) {
      int nPoints = this.vertices.size();
      boolean isContained = false;

      for(int i = 0; i < nPoints; ++i) {
         GPoint v1 = this.vertices.get(i);
         GPoint v2 = this.vertices.get((i + 1) % nPoints);
         if (v1.getY() < y && v2.getY() >= y || v2.getY() < y && v1.getY() >= y) {
            double t = (y - v1.getY()) / (v2.getY() - v1.getY());
            double xp = v1.getX() + t * (v2.getX() - v1.getX());
            if (xp < x) {
               isContained = !isContained;
            }
         }
      }

      return isContained;
   }

   protected void paint2d(Graphics2D g) {
      int nPoints = this.vertices.size();
      Path2D path = new Double(0);
      path.moveTo(this.vertices.get(0).getX(), this.vertices.get(0).getY());

      for(int i = 0; i < nPoints; ++i) {
         path.lineTo(this.vertices.get(i).getX(), this.vertices.get(i).getY());
      }

      path.lineTo(this.vertices.get(0).getX(), this.vertices.get(0).getY());
      if (this.isFilled()) {
         g.setColor(this.getFillColor());
         g.fill(path);
         g.setColor(this.getColor());
      }

      g.draw(path);
   }

   public Object clone() {
      try {
         GPolygon clone = (GPolygon)super.clone();
         clone.vertices = new VertexList(clone.vertices);
         return clone;
      } catch (Exception var2) {
         throw new RuntimeException("Impossible exception");
      }
   }

   protected void clear() {
      this.vertices.clear();
   }
}
