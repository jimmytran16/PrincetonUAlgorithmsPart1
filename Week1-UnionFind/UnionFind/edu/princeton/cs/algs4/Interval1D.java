// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Arrays;
import java.util.Comparator;

public class Interval1D
{
    public static final Comparator<Interval1D> MIN_ENDPOINT_ORDER;
    public static final Comparator<Interval1D> MAX_ENDPOINT_ORDER;
    public static final Comparator<Interval1D> LENGTH_ORDER;
    private final double min;
    private final double max;
    
    public Interval1D(double min, double max) {
        if (Double.isInfinite(min) || Double.isInfinite(max)) {
            throw new IllegalArgumentException("Endpoints must be finite");
        }
        if (Double.isNaN(min) || Double.isNaN(max)) {
            throw new IllegalArgumentException("Endpoints cannot be NaN");
        }
        if (min == 0.0) {
            min = 0.0;
        }
        if (max == 0.0) {
            max = 0.0;
        }
        if (min <= max) {
            this.min = min;
            this.max = max;
            return;
        }
        throw new IllegalArgumentException("Illegal interval");
    }
    
    @Deprecated
    public double left() {
        return this.min;
    }
    
    @Deprecated
    public double right() {
        return this.max;
    }
    
    public double min() {
        return this.min;
    }
    
    public double max() {
        return this.max;
    }
    
    public boolean intersects(final Interval1D that) {
        return this.max >= that.min && that.max >= this.min;
    }
    
    public boolean contains(final double x) {
        return this.min <= x && x <= this.max;
    }
    
    public double length() {
        return this.max - this.min;
    }
    
    @Override
    public String toString() {
        return "[" + this.min + ", " + this.max + "]";
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
        final Interval1D that = (Interval1D)other;
        return this.min == that.min && this.max == that.max;
    }
    
    @Override
    public int hashCode() {
        final int hash1 = Double.valueOf(this.min).hashCode();
        final int hash2 = Double.valueOf(this.max).hashCode();
        return 31 * hash1 + hash2;
    }
    
    public static void main(final String[] args) {
        final Interval1D[] intervals = { new Interval1D(15.0, 33.0), new Interval1D(45.0, 60.0), new Interval1D(20.0, 70.0), new Interval1D(46.0, 55.0) };
        StdOut.println("Unsorted");
        for (int i = 0; i < intervals.length; ++i) {
            StdOut.println(intervals[i]);
        }
        StdOut.println();
        StdOut.println("Sort by min endpoint");
        Arrays.sort(intervals, Interval1D.MIN_ENDPOINT_ORDER);
        for (int i = 0; i < intervals.length; ++i) {
            StdOut.println(intervals[i]);
        }
        StdOut.println();
        StdOut.println("Sort by max endpoint");
        Arrays.sort(intervals, Interval1D.MAX_ENDPOINT_ORDER);
        for (int i = 0; i < intervals.length; ++i) {
            StdOut.println(intervals[i]);
        }
        StdOut.println();
        StdOut.println("Sort by length");
        Arrays.sort(intervals, Interval1D.LENGTH_ORDER);
        for (int i = 0; i < intervals.length; ++i) {
            StdOut.println(intervals[i]);
        }
        StdOut.println();
    }
    
    static {
        MIN_ENDPOINT_ORDER = new MinEndpointComparator();
        MAX_ENDPOINT_ORDER = new MaxEndpointComparator();
        LENGTH_ORDER = new LengthComparator();
    }
    
    private static class MinEndpointComparator implements Comparator<Interval1D>
    {
        @Override
        public int compare(final Interval1D a, final Interval1D b) {
            if (a.min < b.min) {
                return -1;
            }
            if (a.min > b.min) {
                return 1;
            }
            if (a.max < b.max) {
                return -1;
            }
            if (a.max > b.max) {
                return 1;
            }
            return 0;
        }
    }
    
    private static class MaxEndpointComparator implements Comparator<Interval1D>
    {
        @Override
        public int compare(final Interval1D a, final Interval1D b) {
            if (a.max < b.max) {
                return -1;
            }
            if (a.max > b.max) {
                return 1;
            }
            if (a.min < b.min) {
                return -1;
            }
            if (a.min > b.min) {
                return 1;
            }
            return 0;
        }
    }
    
    private static class LengthComparator implements Comparator<Interval1D>
    {
        @Override
        public int compare(final Interval1D a, final Interval1D b) {
            final double alen = a.length();
            final double blen = b.length();
            if (alen < blen) {
                return -1;
            }
            if (alen > blen) {
                return 1;
            }
            return 0;
        }
    }
}
