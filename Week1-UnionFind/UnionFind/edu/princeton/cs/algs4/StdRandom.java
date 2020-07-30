// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Random;

public final class StdRandom
{
    private static Random random;
    private static long seed;
    
    private StdRandom() {
    }
    
    public static void setSeed(final long s) {
        StdRandom.seed = s;
        StdRandom.random = new Random(StdRandom.seed);
    }
    
    public static long getSeed() {
        return StdRandom.seed;
    }
    
    public static double uniform() {
        return StdRandom.random.nextDouble();
    }
    
    public static int uniform(final int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }
        return StdRandom.random.nextInt(n);
    }
    
    public static long uniform(final long n) {
        if (n <= 0L) {
            throw new IllegalArgumentException("argument must be positive: " + n);
        }
        long r = StdRandom.random.nextLong();
        final long m = n - 1L;
        if ((n & m) == 0x0L) {
            return r & m;
        }
        for (long u = r >>> 1; u + m - (r = u % n) < 0L; u = StdRandom.random.nextLong() >>> 1) {}
        return r;
    }
    
    @Deprecated
    public static double random() {
        return uniform();
    }
    
    public static int uniform(final int a, final int b) {
        if (b <= a || b - (long)a >= 2147483647L) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform(b - a);
    }
    
    public static double uniform(final double a, final double b) {
        if (a >= b) {
            throw new IllegalArgumentException("invalid range: [" + a + ", " + b + ")");
        }
        return a + uniform() * (b - a);
    }
    
    public static boolean bernoulli(final double p) {
        if (p < 0.0 || p > 1.0) {
            throw new IllegalArgumentException("probability p must be between 0.0 and 1.0: " + p);
        }
        return uniform() < p;
    }
    
    public static boolean bernoulli() {
        return bernoulli(0.5);
    }
    
    public static double gaussian() {
        double r;
        double x;
        do {
            x = uniform(-1.0, 1.0);
            final double y = uniform(-1.0, 1.0);
            r = x * x + y * y;
        } while (r >= 1.0 || r == 0.0);
        return x * Math.sqrt(-2.0 * Math.log(r) / r);
    }
    
    public static double gaussian(final double mu, final double sigma) {
        return mu + sigma * gaussian();
    }
    
    public static int geometric(final double p) {
        if (p < 0.0) {
            throw new IllegalArgumentException("probability p must be greater than 0: " + p);
        }
        if (p > 1.0) {
            throw new IllegalArgumentException("probability p must not be larger than 1: " + p);
        }
        return (int)Math.ceil(Math.log(uniform()) / Math.log(1.0 - p));
    }
    
    public static int poisson(final double lambda) {
        if (lambda <= 0.0) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        }
        if (Double.isInfinite(lambda)) {
            throw new IllegalArgumentException("lambda must not be infinite: " + lambda);
        }
        int k = 0;
        double p = 1.0;
        final double expLambda = Math.exp(-lambda);
        do {
            ++k;
            p *= uniform();
        } while (p >= expLambda);
        return k - 1;
    }
    
    public static double pareto() {
        return pareto(1.0);
    }
    
    public static double pareto(final double alpha) {
        if (alpha <= 0.0) {
            throw new IllegalArgumentException("alpha must be positive: " + alpha);
        }
        return Math.pow(1.0 - uniform(), -1.0 / alpha) - 1.0;
    }
    
    public static double cauchy() {
        return Math.tan(3.141592653589793 * (uniform() - 0.5));
    }
    
    public static int discrete(final double[] probabilities) {
        if (probabilities == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        final double EPSILON = 1.0E-14;
        double sum = 0.0;
        for (int i = 0; i < probabilities.length; ++i) {
            if (probabilities[i] < 0.0) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + probabilities[i]);
            }
            sum += probabilities[i];
        }
        if (sum > 1.0 + EPSILON || sum < 1.0 - EPSILON) {
            throw new IllegalArgumentException("sum of array entries does not approximately equal 1.0: " + sum);
        }
        int j = 0;
    Block_6:
        while (true) {
            final double r = uniform();
            sum = 0.0;
            for (j = 0; j < probabilities.length; ++j) {
                sum += probabilities[j];
                if (sum > r) {
                    break Block_6;
                }
            }
        }
        return j;
    }
    
    public static int discrete(final int[] frequencies) {
        if (frequencies == null) {
            throw new IllegalArgumentException("argument array is null");
        }
        long sum = 0L;
        for (int i = 0; i < frequencies.length; ++i) {
            if (frequencies[i] < 0) {
                throw new IllegalArgumentException("array entry " + i + " must be nonnegative: " + frequencies[i]);
            }
            sum += frequencies[i];
        }
        if (sum == 0L) {
            throw new IllegalArgumentException("at least one array entry must be positive");
        }
        if (sum >= 2147483647L) {
            throw new IllegalArgumentException("sum of frequencies overflows an int");
        }
        final double r = uniform((int)sum);
        sum = 0L;
        for (int j = 0; j < frequencies.length; ++j) {
            sum += frequencies[j];
            if (sum > r) {
                return j;
            }
        }
        assert false;
        return -1;
    }
    
    public static double exp(final double lambda) {
        if (lambda <= 0.0) {
            throw new IllegalArgumentException("lambda must be positive: " + lambda);
        }
        return -Math.log(1.0 - uniform()) / lambda;
    }
    
    public static void shuffle(final Object[] a) {
        validateNotNull(a);
        for (int n = a.length, i = 0; i < n; ++i) {
            final int r = i + uniform(n - i);
            final Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void shuffle(final double[] a) {
        validateNotNull(a);
        for (int n = a.length, i = 0; i < n; ++i) {
            final int r = i + uniform(n - i);
            final double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void shuffle(final int[] a) {
        validateNotNull(a);
        for (int n = a.length, i = 0; i < n; ++i) {
            final int r = i + uniform(n - i);
            final int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void shuffle(final char[] a) {
        validateNotNull(a);
        for (int n = a.length, i = 0; i < n; ++i) {
            final int r = i + uniform(n - i);
            final char temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void shuffle(final Object[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        for (int i = lo; i < hi; ++i) {
            final int r = i + uniform(hi - i);
            final Object temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void shuffle(final double[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        for (int i = lo; i < hi; ++i) {
            final int r = i + uniform(hi - i);
            final double temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static void shuffle(final int[] a, final int lo, final int hi) {
        validateNotNull(a);
        validateSubarrayIndices(lo, hi, a.length);
        for (int i = lo; i < hi; ++i) {
            final int r = i + uniform(hi - i);
            final int temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }
    
    public static int[] permutation(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        final int[] perm = new int[n];
        for (int i = 0; i < n; ++i) {
            perm[i] = i;
        }
        shuffle(perm);
        return perm;
    }
    
    public static int[] permutation(final int n, final int k) {
        if (n < 0) {
            throw new IllegalArgumentException("argument is negative");
        }
        if (k < 0 || k > n) {
            throw new IllegalArgumentException("k must be between 0 and n");
        }
        final int[] perm = new int[k];
        for (int i = 0; i < k; ++i) {
            final int r = uniform(i + 1);
            perm[i] = perm[r];
            perm[r] = i;
        }
        for (int i = k; i < n; ++i) {
            final int r = uniform(i + 1);
            if (r < k) {
                perm[r] = i;
            }
        }
        return perm;
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
        final int n = Integer.parseInt(args[0]);
        if (args.length == 2) {
            setSeed(Long.parseLong(args[1]));
        }
        final double[] probabilities = { 0.5, 0.3, 0.1, 0.1 };
        final int[] frequencies = { 5, 3, 1, 1 };
        final String[] a = "A B C D E F G".split(" ");
        StdOut.println("seed = " + getSeed());
        for (int i = 0; i < n; ++i) {
            StdOut.printf("%2d ", uniform(100));
            StdOut.printf("%8.5f ", uniform(10.0, 99.0));
            StdOut.printf("%5b ", bernoulli(0.5));
            StdOut.printf("%7.5f ", gaussian(9.0, 0.2));
            StdOut.printf("%1d ", discrete(probabilities));
            StdOut.printf("%1d ", discrete(frequencies));
            StdOut.printf("%11d ", uniform(100000000000L));
            shuffle(a);
            for (final String s : a) {
                StdOut.print(s);
            }
            StdOut.println();
        }
    }
    
    static {
        StdRandom.seed = System.currentTimeMillis();
        StdRandom.random = new Random(StdRandom.seed);
    }
}
