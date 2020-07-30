// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class LinearProgramming
{
    private static final double EPSILON = 1.0E-10;
    private double[][] a;
    private int m;
    private int n;
    private int[] basis;
    
    public LinearProgramming(final double[][] A, final double[] b, final double[] c) {
        this.m = b.length;
        this.n = c.length;
        for (int i = 0; i < this.m; ++i) {
            if (b[i] < 0.0) {
                throw new IllegalArgumentException("RHS must be nonnegative");
            }
        }
        this.a = new double[this.m + 1][this.n + this.m + 1];
        for (int i = 0; i < this.m; ++i) {
            for (int j = 0; j < this.n; ++j) {
                this.a[i][j] = A[i][j];
            }
        }
        for (int i = 0; i < this.m; ++i) {
            this.a[i][this.n + i] = 1.0;
        }
        for (int k = 0; k < this.n; ++k) {
            this.a[this.m][k] = c[k];
        }
        for (int i = 0; i < this.m; ++i) {
            this.a[i][this.m + this.n] = b[i];
        }
        this.basis = new int[this.m];
        for (int i = 0; i < this.m; ++i) {
            this.basis[i] = this.n + i;
        }
        this.solve();
        assert this.check(A, b, c);
    }
    
    private void solve() {
        while (true) {
            final int q = this.bland();
            if (q == -1) {
                return;
            }
            final int p = this.minRatioRule(q);
            if (p == -1) {
                throw new ArithmeticException("Linear program is unbounded");
            }
            this.pivot(p, q);
            this.basis[p] = q;
        }
    }
    
    private int bland() {
        for (int j = 0; j < this.m + this.n; ++j) {
            if (this.a[this.m][j] > 0.0) {
                return j;
            }
        }
        return -1;
    }
    
    private int dantzig() {
        int q = 0;
        for (int j = 1; j < this.m + this.n; ++j) {
            if (this.a[this.m][j] > this.a[this.m][q]) {
                q = j;
            }
        }
        if (this.a[this.m][q] <= 0.0) {
            return -1;
        }
        return q;
    }
    
    private int minRatioRule(final int q) {
        int p = -1;
        for (int i = 0; i < this.m; ++i) {
            if (this.a[i][q] > 1.0E-10) {
                if (p == -1) {
                    p = i;
                }
                else if (this.a[i][this.m + this.n] / this.a[i][q] < this.a[p][this.m + this.n] / this.a[p][q]) {
                    p = i;
                }
            }
        }
        return p;
    }
    
    private void pivot(final int p, final int q) {
        for (int i = 0; i <= this.m; ++i) {
            for (int j = 0; j <= this.m + this.n; ++j) {
                if (i != p && j != q) {
                    final double[] array = this.a[i];
                    final int n = j;
                    array[n] -= this.a[p][j] * this.a[i][q] / this.a[p][q];
                }
            }
        }
        for (int i = 0; i <= this.m; ++i) {
            if (i != p) {
                this.a[i][q] = 0.0;
            }
        }
        for (int k = 0; k <= this.m + this.n; ++k) {
            if (k != q) {
                final double[] array2 = this.a[p];
                final int n2 = k;
                array2[n2] /= this.a[p][q];
            }
        }
        this.a[p][q] = 1.0;
    }
    
    public double value() {
        return -this.a[this.m][this.m + this.n];
    }
    
    public double[] primal() {
        final double[] x = new double[this.n];
        for (int i = 0; i < this.m; ++i) {
            if (this.basis[i] < this.n) {
                x[this.basis[i]] = this.a[i][this.m + this.n];
            }
        }
        return x;
    }
    
    public double[] dual() {
        final double[] y = new double[this.m];
        for (int i = 0; i < this.m; ++i) {
            y[i] = -this.a[this.m][this.n + i];
        }
        return y;
    }
    
    private boolean isPrimalFeasible(final double[][] A, final double[] b) {
        final double[] x = this.primal();
        for (int j = 0; j < x.length; ++j) {
            if (x[j] < 0.0) {
                StdOut.println("x[" + j + "] = " + x[j] + " is negative");
                return false;
            }
        }
        for (int i = 0; i < this.m; ++i) {
            double sum = 0.0;
            for (int k = 0; k < this.n; ++k) {
                sum += A[i][k] * x[k];
            }
            if (sum > b[i] + 1.0E-10) {
                StdOut.println("not primal feasible");
                StdOut.println("b[" + i + "] = " + b[i] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }
    
    private boolean isDualFeasible(final double[][] A, final double[] c) {
        final double[] y = this.dual();
        for (int i = 0; i < y.length; ++i) {
            if (y[i] < 0.0) {
                StdOut.println("y[" + i + "] = " + y[i] + " is negative");
                return false;
            }
        }
        for (int j = 0; j < this.n; ++j) {
            double sum = 0.0;
            for (int k = 0; k < this.m; ++k) {
                sum += A[k][j] * y[k];
            }
            if (sum < c[j] - 1.0E-10) {
                StdOut.println("not dual feasible");
                StdOut.println("c[" + j + "] = " + c[j] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }
    
    private boolean isOptimal(final double[] b, final double[] c) {
        final double[] x = this.primal();
        final double[] y = this.dual();
        final double value = this.value();
        double value2 = 0.0;
        for (int j = 0; j < x.length; ++j) {
            value2 += c[j] * x[j];
        }
        double value3 = 0.0;
        for (int i = 0; i < y.length; ++i) {
            value3 += y[i] * b[i];
        }
        if (Math.abs(value - value2) > 1.0E-10 || Math.abs(value - value3) > 1.0E-10) {
            StdOut.println("value = " + value + ", cx = " + value2 + ", yb = " + value3);
            return false;
        }
        return true;
    }
    
    private boolean check(final double[][] A, final double[] b, final double[] c) {
        return this.isPrimalFeasible(A, b) && this.isDualFeasible(A, c) && this.isOptimal(b, c);
    }
    
    private void show() {
        StdOut.println("m = " + this.m);
        StdOut.println("n = " + this.n);
        for (int i = 0; i <= this.m; ++i) {
            for (int j = 0; j <= this.m + this.n; ++j) {
                StdOut.printf("%7.2f ", this.a[i][j]);
            }
            StdOut.println();
        }
        StdOut.println("value = " + this.value());
        for (int i = 0; i < this.m; ++i) {
            if (this.basis[i] < this.n) {
                StdOut.println("x_" + this.basis[i] + " = " + this.a[i][this.m + this.n]);
            }
        }
        StdOut.println();
    }
    
    private static void test(final double[][] A, final double[] b, final double[] c) {
        LinearProgramming lp;
        try {
            lp = new LinearProgramming(A, b, c);
        }
        catch (ArithmeticException e) {
            System.out.println(e);
            return;
        }
        StdOut.println("value = " + lp.value());
        final double[] x = lp.primal();
        for (int i = 0; i < x.length; ++i) {
            StdOut.println("x[" + i + "] = " + x[i]);
        }
        final double[] y = lp.dual();
        for (int j = 0; j < y.length; ++j) {
            StdOut.println("y[" + j + "] = " + y[j]);
        }
    }
    
    private static void test1() {
        final double[][] A = { { -1.0, 1.0, 0.0 }, { 1.0, 4.0, 0.0 }, { 2.0, 1.0, 0.0 }, { 3.0, -4.0, 0.0 }, { 0.0, 0.0, 1.0 } };
        final double[] c = { 1.0, 1.0, 1.0 };
        final double[] b = { 5.0, 45.0, 27.0, 24.0, 4.0 };
        test(A, b, c);
    }
    
    private static void test2() {
        final double[] c = { 13.0, 23.0 };
        final double[] b = { 480.0, 160.0, 1190.0 };
        final double[][] A = { { 5.0, 15.0 }, { 4.0, 4.0 }, { 35.0, 20.0 } };
        test(A, b, c);
    }
    
    private static void test3() {
        final double[] c = { 2.0, 3.0, -1.0, -12.0 };
        final double[] b = { 3.0, 2.0 };
        final double[][] A = { { -2.0, -9.0, 1.0, 9.0 }, { 1.0, 1.0, -1.0, -2.0 } };
        test(A, b, c);
    }
    
    private static void test4() {
        final double[] c = { 10.0, -57.0, -9.0, -24.0 };
        final double[] b = { 0.0, 0.0, 1.0 };
        final double[][] A = { { 0.5, -5.5, -2.5, 9.0 }, { 0.5, -1.5, -0.5, 1.0 }, { 1.0, 0.0, 0.0, 0.0 } };
        test(A, b, c);
    }
    
    public static void main(final String[] args) {
        StdOut.println("----- test 1 --------------------");
        test1();
        StdOut.println();
        StdOut.println("----- test 2 --------------------");
        test2();
        StdOut.println();
        StdOut.println("----- test 3 --------------------");
        test3();
        StdOut.println();
        StdOut.println("----- test 4 --------------------");
        test4();
        StdOut.println();
        StdOut.println("----- test random ---------------");
        final int m = Integer.parseInt(args[0]);
        final int n = Integer.parseInt(args[1]);
        final double[] c = new double[n];
        final double[] b = new double[m];
        final double[][] A = new double[m][n];
        for (int j = 0; j < n; ++j) {
            c[j] = StdRandom.uniform(1000);
        }
        for (int i = 0; i < m; ++i) {
            b[i] = StdRandom.uniform(1000);
        }
        for (int i = 0; i < m; ++i) {
            for (int k = 0; k < n; ++k) {
                A[i][k] = StdRandom.uniform(100);
            }
        }
        final LinearProgramming lp = new LinearProgramming(A, b, c);
        test(A, b, c);
    }
}
