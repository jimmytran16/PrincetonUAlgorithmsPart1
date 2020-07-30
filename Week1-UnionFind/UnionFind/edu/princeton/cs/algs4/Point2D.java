// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Arrays;
import java.util.Comparator;

public final class Point2D implements Comparable<Point2D>
{
    public static final Comparator<Point2D> X_ORDER;
    public static final Comparator<Point2D> Y_ORDER;
    public static final Comparator<Point2D> R_ORDER;
    private final double x;
    private final double y;
    
    public Point2D(final double x, final double y) {
        if (Double.isInfinite(x) || Double.isInfinite(y)) {
            throw new IllegalArgumentException("Coordinates must be finite");
        }
        if (Double.isNaN(x) || Double.isNaN(y)) {
            throw new IllegalArgumentException("Coordinates cannot be NaN");
        }
        if (x == 0.0) {
            this.x = 0.0;
        }
        else {
            this.x = x;
        }
        if (y == 0.0) {
            this.y = 0.0;
        }
        else {
            this.y = y;
        }
    }
    
    public double x() {
        return this.x;
    }
    
    public double y() {
        return this.y;
    }
    
    public double r() {
        return Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    public double theta() {
        return Math.atan2(this.y, this.x);
    }
    
    private double angleTo(final Point2D that) {
        final double dx = that.x - this.x;
        final double dy = that.y - this.y;
        return Math.atan2(dy, dx);
    }
    
    public static int ccw(final Point2D a, final Point2D b, final Point2D c) {
        final double area2 = (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
        if (area2 < 0.0) {
            return -1;
        }
        if (area2 > 0.0) {
            return 1;
        }
        return 0;
    }
    
    public static double area2(final Point2D a, final Point2D b, final Point2D c) {
        return (b.x - a.x) * (c.y - a.y) - (b.y - a.y) * (c.x - a.x);
    }
    
    public double distanceTo(final Point2D that) {
        final double dx = this.x - that.x;
        final double dy = this.y - that.y;
        return Math.sqrt(dx * dx + dy * dy);
    }
    
    public double distanceSquaredTo(final Point2D that) {
        final double dx = this.x - that.x;
        final double dy = this.y - that.y;
        return dx * dx + dy * dy;
    }
    
    @Override
    public int compareTo(final Point2D that) {
        if (this.y < that.y) {
            return -1;
        }
        if (this.y > that.y) {
            return 1;
        }
        if (this.x < that.x) {
            return -1;
        }
        if (this.x > that.x) {
            return 1;
        }
        return 0;
    }
    
    public Comparator<Point2D> polarOrder() {
        return new PolarOrder();
    }
    
    public Comparator<Point2D> atan2Order() {
        return new Atan2Order();
    }
    
    public Comparator<Point2D> distanceToOrder() {
        return new DistanceToOrder();
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        final Point2D that = (Point2D)other;
        return this.x == that.x && this.y == that.y;
    }
    
    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ")";
    }
    
    @Override
    public int hashCode() {
        final int hashX = Double.valueOf(this.x).hashCode();
        final int hashY = Double.valueOf(this.y).hashCode();
        return 31 * hashX + hashY;
    }
    
    public void draw() {
        StdDraw.point(this.x, this.y);
    }
    
    public void drawTo(final Point2D that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    
    public static void main(final String[] args) {
        final int x0 = Integer.parseInt(args[0]);
        final int y0 = Integer.parseInt(args[1]);
        final int n = Integer.parseInt(args[2]);
        StdDraw.setCanvasSize(800, 800);
        StdDraw.setXscale(0.0, 100.0);
        StdDraw.setYscale(0.0, 100.0);
        StdDraw.setPenRadius(0.005);
        StdDraw.enableDoubleBuffering();
        final Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; ++i) {
            final int x2 = StdRandom.uniform(100);
            final int y2 = StdRandom.uniform(100);
            (points[i] = new Point2D(x2, y2)).draw();
        }
        final Point2D p = new Point2D(x0, y0);
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius(0.02);
        p.draw();
        StdDraw.setPenRadius();
        StdDraw.setPenColor(StdDraw.BLUE);
        Arrays.sort(points, p.polarOrder());
        for (int j = 0; j < n; ++j) {
            p.drawTo(points[j]);
            StdDraw.show();
            StdDraw.pause(100);
        }
    }
    
    static {
        X_ORDER = new XOrder();
        Y_ORDER = new YOrder();
        R_ORDER = new ROrder();
    }
    
    private static class XOrder implements Comparator<Point2D>
    {
        @Override
        public int compare(final Point2D p, final Point2D q) {
            if (p.x < q.x) {
                return -1;
            }
            if (p.x > q.x) {
                return 1;
            }
            return 0;
        }
    }
    
    private static class YOrder implements Comparator<Point2D>
    {
        @Override
        public int compare(final Point2D p, final Point2D q) {
            if (p.y < q.y) {
                return -1;
            }
            if (p.y > q.y) {
                return 1;
            }
            return 0;
        }
    }
    
    private static class ROrder implements Comparator<Point2D>
    {
        @Override
        public int compare(final Point2D p, final Point2D q) {
            final double delta = p.x * p.x + p.y * p.y - (q.x * q.x + q.y * q.y);
            if (delta < 0.0) {
                return -1;
            }
            if (delta > 0.0) {
                return 1;
            }
            return 0;
        }
    }
    
    private class Atan2Order implements Comparator<Point2D>
    {
        @Override
        public int compare(final Point2D q1, final Point2D q2) {
            final double angle1 = Point2D.this.angleTo(q1);
            final double angle2 = Point2D.this.angleTo(q2);
            if (angle1 < angle2) {
                return -1;
            }
            if (angle1 > angle2) {
                return 1;
            }
            return 0;
        }
    }
    
    private class PolarOrder implements Comparator<Point2D>
    {
        @Override
        public int compare(final Point2D q1, final Point2D q2) {
            final double dx1 = q1.x - Point2D.this.x;
            final double dy1 = q1.y - Point2D.this.y;
            final double dx2 = q2.x - Point2D.this.x;
            final double dy2 = q2.y - Point2D.this.y;
            if (dy1 >= 0.0 && dy2 < 0.0) {
                return -1;
            }
            if (dy2 >= 0.0 && dy1 < 0.0) {
                return 1;
            }
            if (dy1 != 0.0 || dy2 != 0.0) {
                return -Point2D.ccw(Point2D.this, q1, q2);
            }
            if (dx1 >= 0.0 && dx2 < 0.0) {
                return -1;
            }
            if (dx2 >= 0.0 && dx1 < 0.0) {
                return 1;
            }
            return 0;
        }
    }
    
    private class DistanceToOrder implements Comparator<Point2D>
    {
        @Override
        public int compare(final Point2D p, final Point2D q) {
            final double dist1 = Point2D.this.distanceSquaredTo(p);
            final double dist2 = Point2D.this.distanceSquaredTo(q);
            if (dist1 < dist2) {
                return -1;
            }
            if (dist1 > dist2) {
                return 1;
            }
            return 0;
        }
    }
}
