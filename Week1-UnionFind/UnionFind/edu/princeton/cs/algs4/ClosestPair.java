// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Comparator;
import java.util.Arrays;

public class ClosestPair
{
    private Point2D best1;
    private Point2D best2;
    private double bestDistance;
    
    public ClosestPair(final Point2D[] points) {
        this.bestDistance = Double.POSITIVE_INFINITY;
        if (points == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        for (int i = 0; i < points.length; ++i) {
            if (points[i] == null) {
                throw new IllegalArgumentException("array element " + i + " is null");
            }
        }
        final int n = points.length;
        if (n <= 1) {
            return;
        }
        final Point2D[] pointsByX = new Point2D[n];
        for (int j = 0; j < n; ++j) {
            pointsByX[j] = points[j];
        }
        Arrays.sort(pointsByX, Point2D.X_ORDER);
        for (int j = 0; j < n - 1; ++j) {
            if (pointsByX[j].equals(pointsByX[j + 1])) {
                this.bestDistance = 0.0;
                this.best1 = pointsByX[j];
                this.best2 = pointsByX[j + 1];
                return;
            }
        }
        final Point2D[] pointsByY = new Point2D[n];
        for (int k = 0; k < n; ++k) {
            pointsByY[k] = pointsByX[k];
        }
        final Point2D[] aux = new Point2D[n];
        this.closest(pointsByX, pointsByY, aux, 0, n - 1);
    }
    
    private double closest(final Point2D[] pointsByX, final Point2D[] pointsByY, final Point2D[] aux, final int lo, final int hi) {
        if (hi <= lo) {
            return Double.POSITIVE_INFINITY;
        }
        final int mid = lo + (hi - lo) / 2;
        final Point2D median = pointsByX[mid];
        final double delta1 = this.closest(pointsByX, pointsByY, aux, lo, mid);
        final double delta2 = this.closest(pointsByX, pointsByY, aux, mid + 1, hi);
        double delta3 = Math.min(delta1, delta2);
        merge(pointsByY, aux, lo, mid, hi);
        int m = 0;
        for (int i = lo; i <= hi; ++i) {
            if (Math.abs(pointsByY[i].x() - median.x()) < delta3) {
                aux[m++] = pointsByY[i];
            }
        }
        for (int i = 0; i < m; ++i) {
            for (int j = i + 1; j < m && aux[j].y() - aux[i].y() < delta3; ++j) {
                final double distance = aux[i].distanceTo(aux[j]);
                if (distance < delta3) {
                    delta3 = distance;
                    if (distance < this.bestDistance) {
                        this.bestDistance = delta3;
                        this.best1 = aux[i];
                        this.best2 = aux[j];
                    }
                }
            }
        }
        return delta3;
    }
    
    public Point2D either() {
        return this.best1;
    }
    
    public Point2D other() {
        return this.best2;
    }
    
    public double distance() {
        return this.bestDistance;
    }
    
    private static boolean less(final Comparable v, final Comparable w) {
        return v.compareTo(w) < 0;
    }
    
    private static void merge(final Comparable[] a, final Comparable[] aux, final int lo, final int mid, final int hi) {
        for (int k = lo; k <= hi; ++k) {
            aux[k] = a[k];
        }
        int i = lo;
        int j = mid + 1;
        for (int l = lo; l <= hi; ++l) {
            if (i > mid) {
                a[l] = aux[j++];
            }
            else if (j > hi) {
                a[l] = aux[i++];
            }
            else if (less(aux[j], aux[i])) {
                a[l] = aux[j++];
            }
            else {
                a[l] = aux[i++];
            }
        }
    }
    
    public static void main(final String[] args) {
        final int n = StdIn.readInt();
        final Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; ++i) {
            final double x = StdIn.readDouble();
            final double y = StdIn.readDouble();
            points[i] = new Point2D(x, y);
        }
        final ClosestPair closest = new ClosestPair(points);
        StdOut.println(closest.distance() + " from " + closest.either() + " to " + closest.other());
    }
}
