// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class FarthestPair
{
    private Point2D best1;
    private Point2D best2;
    private double bestDistanceSquared;
    
    public FarthestPair(final Point2D[] points) {
        this.bestDistanceSquared = Double.NEGATIVE_INFINITY;
        if (points == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("array element " + i + " is null");
            }
        }
        final GrahamScan graham = new GrahamScan(points);
        if (points.length <= 1) {
            return;
        }
        int m = 0;
        for (final Point2D p : graham.hull()) {
            ++m;
        }
        final Point2D[] hull = new Point2D[m + 1];
        m = 1;
        for (final Point2D p2 : graham.hull()) {
            hull[m++] = p2;
        }
        if (--m == 1) {
            return;
        }
        if (m == 2) {
            this.best1 = hull[1];
            this.best2 = hull[2];
            this.bestDistanceSquared = this.best1.distanceSquaredTo(this.best2);
            return;
        }
        int k;
        for (k = 2; Point2D.area2(hull[m], hull[1], hull[k + 1]) > Point2D.area2(hull[m], hull[1], hull[k]); ++k) {}
        for (int j = k, l = 1; l <= k && j <= m; ++l) {
            if (hull[l].distanceSquaredTo(hull[j]) > this.bestDistanceSquared) {
                this.best1 = hull[l];
                this.best2 = hull[j];
                this.bestDistanceSquared = hull[l].distanceSquaredTo(hull[j]);
            }
            while (j < m && Point2D.area2(hull[l], hull[l + 1], hull[j + 1]) > Point2D.area2(hull[l], hull[l + 1], hull[j])) {
                ++j;
                final double distanceSquared = hull[l].distanceSquaredTo(hull[j]);
                if (distanceSquared > this.bestDistanceSquared) {
                    this.best1 = hull[l];
                    this.best2 = hull[j];
                    this.bestDistanceSquared = hull[l].distanceSquaredTo(hull[j]);
                }
            }
        }
    }
    
    public Point2D either() {
        return this.best1;
    }
    
    public Point2D other() {
        return this.best2;
    }
    
    public double distance() {
        return Math.sqrt(this.bestDistanceSquared);
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; ++i) {
            final int x = StdIn.readInt();
            final int y = StdIn.readInt();
            points[i] = new Point2D(x, y);
        }
        final FarthestPair farthest = new FarthestPair(points);
        StdOut.println(farthest.distance() + " from " + farthest.either() + " to " + farthest.other());
    }
}
