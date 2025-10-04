package edu.stanford.cs.graphics;

import java.util.ArrayList;

class VertexList {
   private ArrayList<GPoint> vertices;
   private double cx;
   private double cy;

   public VertexList() {
      this.vertices = new ArrayList();
      this.cx = 0.0D;
      this.cy = 0.0D;
   }

   public VertexList(VertexList oldList) {
      this();

      for(int i = 0; i < oldList.vertices.size(); ++i) {
         this.vertices.add((GPoint)oldList.vertices.get(i));
      }

   }

   public synchronized void addVertex(double x, double y) {
      this.cx = x;
      this.cy = y;
      this.vertices.add(new GPoint(this.cx, this.cy));
   }

   public synchronized void addEdge(double dx, double dy) {
      this.cx += dx;
      this.cy += dy;
      this.vertices.add(new GPoint(this.cx, this.cy));
   }

   public void addArc(double arcWidth, double arcHeight, double start, double sweep) {
      double aspectRatio = arcHeight / arcWidth;
      double rx = arcWidth / 2.0D;
      double ry = arcHeight / 2.0D;
      double x0 = this.cx - rx * GMath.cosDegrees(start);
      double y0 = this.cy + ry * GMath.sinDegrees(start);
      if (sweep > 359.99D) {
         sweep = 360.0D;
      }

      if (sweep < -359.99D) {
         sweep = -360.0D;
      }

      double dt = Math.atan2(1.0D, Math.max(arcWidth, arcHeight));
      int nSteps = (int)(GMath.toRadians(Math.abs(sweep)) / dt);
      dt = GMath.toRadians(sweep) / (double)nSteps;
      double theta = GMath.toRadians(start);

      for(int i = 0; i < nSteps; ++i) {
         theta += dt;
         double px = x0 + rx * Math.cos(theta);
         double py = y0 - rx * Math.sin(theta) * aspectRatio;
         this.addVertex(px, py);
      }

   }

   public synchronized void add(GPoint[] array) {
      for(int i = 0; i < array.length; ++i) {
         this.vertices.add(new GPoint(array[i].getX(), array[i].getY()));
      }

   }

   public synchronized void remove(GPoint vertex) {
      this.vertices.remove(vertex);
   }

   public synchronized void clear() {
      this.vertices.clear();
   }

   public int size() {
      return this.vertices.size();
   }

   GPoint get(int index) {
      return (GPoint)this.vertices.get(index);
   }

   public GPoint getCurrentPoint() {
      return this.vertices.size() == 0 ? null : new GPoint(this.cx, this.cy);
   }
}
