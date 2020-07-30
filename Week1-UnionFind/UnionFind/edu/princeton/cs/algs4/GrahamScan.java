// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.util.Comparator;
import java.util.Arrays;

public class GrahamScan
{
    private Stack<Point2D> hull;
    
    public GrahamScan(final Point2D[] points) {
        this.hull = new Stack<Point2D>();
        if (points == null) {
            throw new IllegalArgumentException("argument is null");
        }
        if (points.length == 0) {
            throw new IllegalArgumentException("array is of length 0");
        }
        final int n = points.length;
        final Point2D[] a = new Point2D[n];
        for (int i = 0; i < n; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("points[" + i + "] is null");
            }
            a[i] = points[i];
        }
        Arrays.sort(a);
        Arrays.sort(a, 1, n, a[0].polarOrder());
        this.hull.push(a[0]);
        int k1;
        for (k1 = 1; k1 < n && a[0].equals(a[k1]); ++k1) {}
        if (k1 == n) {
            return;
        }
        int k2;
        for (k2 = k1 + 1; k2 < n && Point2D.ccw(a[0], a[k1], a[k2]) == 0; ++k2) {}
        this.hull.push(a[k2 - 1]);
        for (int j = k2; j < n; ++j) {
            Point2D top;
            for (top = this.hull.pop(); Point2D.ccw(this.hull.peek(), top, a[j]) <= 0; top = this.hull.pop()) {}
            this.hull.push(top);
            this.hull.push(a[j]);
        }
        assert this.isConvex();
    }
    
    public Iterable<Point2D> hull() {
        final Stack<Point2D> s = new Stack<Point2D>();
        for (final Point2D p : this.hull) {
            s.push(p);
        }
        return s;
    }
    
    private boolean isConvex() {
        final int n = this.hull.size();
        if (n <= 2) {
            return true;
        }
        final Point2D[] points = new Point2D[n];
        int k = 0;
        for (final Point2D p : this.hull()) {
            points[k++] = p;
        }
        for (int i = 0; i < n; ++i) {
            if (Point2D.ccw(points[i], points[(i + 1) % n], points[(i + 2) % n]) <= 0) {
                return false;
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; ++i) {
            final int x = StdIn.readInt();
            final int y = StdIn.readInt();
            points[i] = new Point2D(x, y);
        }
        final GrahamScan graham = new GrahamScan(points);
        for (final Point2D p : graham.hull()) {
            StdOut.println(p);
        }
    }
}
