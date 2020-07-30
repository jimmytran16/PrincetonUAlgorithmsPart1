// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class FFT
{
    private static final Complex ZERO;
    
    private FFT() {
    }
    
    public static Complex[] fft(final Complex[] x) {
        final int n = x.length;
        if (n == 1) {
            return new Complex[] { x[0] };
        }
        if (n % 2 != 0) {
            throw new IllegalArgumentException("n is not a power of 2");
        }
        final Complex[] even = new Complex[n / 2];
        for (int k = 0; k < n / 2; ++k) {
            even[k] = x[2 * k];
        }
        final Complex[] q = fft(even);
        final Complex[] odd = even;
        for (int i = 0; i < n / 2; ++i) {
            odd[i] = x[2 * i + 1];
        }
        final Complex[] r = fft(odd);
        final Complex[] y = new Complex[n];
        for (int j = 0; j < n / 2; ++j) {
            final double kth = -2 * j * 3.141592653589793 / n;
            final Complex wk = new Complex(Math.cos(kth), Math.sin(kth));
            y[j] = q[j].plus(wk.times(r[j]));
            y[j + n / 2] = q[j].minus(wk.times(r[j]));
        }
        return y;
    }
    
    public static Complex[] ifft(final Complex[] x) {
        final int n = x.length;
        Complex[] y = new Complex[n];
        for (int i = 0; i < n; ++i) {
            y[i] = x[i].conjugate();
        }
        y = fft(y);
        for (int i = 0; i < n; ++i) {
            y[i] = y[i].conjugate();
        }
        for (int i = 0; i < n; ++i) {
            y[i] = y[i].scale(1.0 / n);
        }
        return y;
    }
    
    public static Complex[] cconvolve(final Complex[] x, final Complex[] y) {
        if (x.length != y.length) {
            throw new IllegalArgumentException("Dimensions don't agree");
        }
        final int n = x.length;
        final Complex[] a = fft(x);
        final Complex[] b = fft(y);
        final Complex[] c = new Complex[n];
        for (int i = 0; i < n; ++i) {
            c[i] = a[i].times(b[i]);
        }
        return ifft(c);
    }
    
    public static Complex[] convolve(final Complex[] x, final Complex[] y) {
        final Complex[] a = new Complex[2 * x.length];
        for (int i = 0; i < x.length; ++i) {
            a[i] = x[i];
        }
        for (int i = x.length; i < 2 * x.length; ++i) {
            a[i] = FFT.ZERO;
        }
        final Complex[] b = new Complex[2 * y.length];
        for (int j = 0; j < y.length; ++j) {
            b[j] = y[j];
        }
        for (int j = y.length; j < 2 * y.length; ++j) {
            b[j] = FFT.ZERO;
        }
        return cconvolve(a, b);
    }
    
    private static void show(final Complex[] x, final String title) {
        StdOut.println(title);
        StdOut.println("-------------------");
        for (int i = 0; i < x.length; ++i) {
            StdOut.println(x[i]);
        }
        StdOut.println();
    }
    
    public static void main(final String[] args) {
        final int n = Integer.parseInt(args[0]);
        final Complex[] x = new Complex[n];
        for (int i = 0; i < n; ++i) {
            x[i] = new Complex(i, 0.0);
            x[i] = new Complex(StdRandom.uniform(-1.0, 1.0), 0.0);
        }
        show(x, "x");
        final Complex[] y = fft(x);
        show(y, "y = fft(x)");
        final Complex[] z = ifft(y);
        show(z, "z = ifft(y)");
        final Complex[] c = cconvolve(x, x);
        show(c, "c = cconvolve(x, x)");
        final Complex[] d = convolve(x, x);
        show(d, "d = convolve(x, x)");
    }
    
    static {
        ZERO = new Complex(0.0, 0.0);
    }
}
