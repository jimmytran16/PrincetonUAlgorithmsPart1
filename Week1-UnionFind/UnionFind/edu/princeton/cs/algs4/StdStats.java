// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public final class StdStats
{
    private StdStats() {
    }
    
    public static double max(final double[] a) {
        validateNotNull(a);
        double max = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < a.length; ++i) {
            if (Double.isNaN(a[i])) {
                return Double.NaN;
            }
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }
    
    public static double max(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        double max = Double.NEGATIVE_INFINITY;
        for (int i = lo; i < hi; ++i) {
            if (Double.isNaN(a[i])) {
                return Double.NaN;
            }
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }
    
    public static int max(final int[] a) {
        validateNotNull(a);
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < a.length; ++i) {
            if (a[i] > max) {
                max = a[i];
            }
        }
        return max;
    }
    
    public static double min(final double[] a) {
        validateNotNull(a);
        double min = Double.POSITIVE_INFINITY;
        for (int i = 0; i < a.length; ++i) {
            if (Double.isNaN(a[i])) {
                return Double.NaN;
            }
            if (a[i] < min) {
                min = a[i];
            }
        }
        return min;
    }
    
    public static double min(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        double min = Double.POSITIVE_INFINITY;
        for (int i = lo; i < hi; ++i) {
            if (Double.isNaN(a[i])) {
                return Double.NaN;
            }
            if (a[i] < min) {
                min = a[i];
            }
        }
        return min;
    }
    
    public static int min(final int[] a) {
        validateNotNull(a);
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < a.length; ++i) {
            if (a[i] < min) {
                min = a[i];
            }
        }
        return min;
    }
    
    public static double mean(final double[] a) {
        validateNotNull(a);
        if (a.length == 0) {
            return Double.NaN;
        }
        final double sum = sum(a);
        return sum / a.length;
    }
    
    public static double mean(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        final int length = hi - lo;
        if (length == 0) {
            return Double.NaN;
        }
        final double sum = sum(a, lo, hi);
        return sum / length;
    }
    
    public static double mean(final int[] a) {
        validateNotNull(a);
        if (a.length == 0) {
            return Double.NaN;
        }
        final int sum = sum(a);
        return 1.0 * sum / a.length;
    }
    
    public static double var(final double[] a) {
        validateNotNull(a);
        if (a.length == 0) {
            return Double.NaN;
        }
        final double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; ++i) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }
    
    public static double var(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        final int length = hi - lo;
        if (length == 0) {
            return Double.NaN;
        }
        final double avg = mean(a, lo, hi);
        double sum = 0.0;
        for (int i = lo; i < hi; ++i) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (length - 1);
    }
    
    public static double var(final int[] a) {
        validateNotNull(a);
        if (a.length == 0) {
            return Double.NaN;
        }
        final double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; ++i) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / (a.length - 1);
    }
    
    public static double varp(final double[] a) {
        validateNotNull(a);
        if (a.length == 0) {
            return Double.NaN;
        }
        final double avg = mean(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; ++i) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / a.length;
    }
    
    public static double varp(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        final int length = hi - lo;
        if (length == 0) {
            return Double.NaN;
        }
        final double avg = mean(a, lo, hi);
        double sum = 0.0;
        for (int i = lo; i < hi; ++i) {
            sum += (a[i] - avg) * (a[i] - avg);
        }
        return sum / length;
    }
    
    public static double stddev(final double[] a) {
        validateNotNull(a);
        return Math.sqrt(var(a));
    }
    
    public static double stddev(final int[] a) {
        validateNotNull(a);
        return Math.sqrt(var(a));
    }
    
    public static double stddev(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        return Math.sqrt(var(a, lo, hi));
    }
    
    public static double stddevp(final double[] a) {
        validateNotNull(a);
        return Math.sqrt(varp(a));
    }
    
    public static double stddevp(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        return Math.sqrt(varp(a, lo, hi));
    }
    
    private static double sum(final double[] a) {
        validateNotNull(a);
        double sum = 0.0;
        for (int i = 0; i < a.length; ++i) {
            sum += a[i];
        }
        return sum;
    }
    
    private static double sum(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        double sum = 0.0;
        for (int i = lo; i < hi; ++i) {
            sum += a[i];
        }
        return sum;
    }
    
    private static int sum(final int[] a) {
        validateNotNull(a);
        int sum = 0;
        for (int i = 0; i < a.length; ++i) {
            sum += a[i];
        }
        return sum;
    }
    
    public static void plotPoints(final double[] a) {
        validateNotNull(a);
        final int n = a.length;
        StdDraw.setXscale(-1.0, n);
        StdDraw.setPenRadius(1.0 / (3.0 * n));
        for (int i = 0; i < n; ++i) {
            StdDraw.point(i, a[i]);
        }
    }
    
    public static void plotLines(final double[] a) {
        validateNotNull(a);
        final int n = a.length;
        StdDraw.setXscale(-1.0, n);
        StdDraw.setPenRadius();
        for (int i = 1; i < n; ++i) {
            StdDraw.line(i - 1, a[i - 1], i, a[i]);
        }
    }
    
    public static void plotBars(final double[] a) {
        validateNotNull(a);
        final int n = a.length;
        StdDraw.setXscale(-1.0, n);
        for (int i = 0; i < n; ++i) {
            StdDraw.filledRectangle(i, a[i] / 2.0, 0.25, a[i] / 2.0);
        }
    }
    
    private static void validateNotNull(final Object x) {
        if (x == null) {
            throw new IllegalArgumentException("argument is null");
        }
    }
    
    private static void validateSubarrayIndices(final int lo, final int hi, final int length) {
        if (lo < 0 || hi > length || lo > hi) {
            throw new IllegalArgumentException("subarray indices out of bounds: [" + lo + ", " + hi + ")");
        }
    }
    
    public static void main(final String[] args) {
        final double[] a = StdArrayIO.readDouble1D();
        StdOut.printf("       min %10.3f\n", min(a));
        StdOut.printf("      mean %10.3f\n", mean(a));
        StdOut.printf("       max %10.3f\n", max(a));
        StdOut.printf("    stddev %10.3f\n", stddev(a));
        StdOut.printf("       var %10.3f\n", var(a));
        StdOut.printf("   stddevp %10.3f\n", stddevp(a));
        StdOut.printf("      varp %10.3f\n", varp(a));
    }
}
