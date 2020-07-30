// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class GaussJordanElimination
{
    private static final double EPSILON = 1.0E-8;
    private final int n;
    private double[][] a;
    
    public GaussJordanElimination(final double[][] A, final double[] b) {
        this.n = b.length;
        this.a = new double[this.n][this.n + this.n + 1];
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                this.a[i][j] = A[i][j];
            }
        }
        for (int i = 0; i < this.n; ++i) {
            this.a[i][this.n + i] = 1.0;
        }
        for (int i = 0; i < this.n; ++i) {
            this.a[i][this.n + this.n] = b[i];
        }
        this.solve();
        assert this.certifySolution(A, b);
    }
    
    private void solve() {
        for (int p = 0; p < this.n; ++p) {
            int max = p;
            for (int i = p + 1; i < this.n; ++i) {
                if (Math.abs(this.a[i][p]) > Math.abs(this.a[max][p])) {
                    max = i;
                }
            }
            this.swap(p, max);
            if (Math.abs(this.a[p][p]) > 1.0E-8) {
                this.pivot(p, p);
            }
        }
    }
    
    private void swap(final int row1, final int row2) {
        final double[] temp = this.a[row1];
        this.a[row1] = this.a[row2];
        this.a[row2] = temp;
    }
    
    private void pivot(final int p, final int q) {
        for (int i = 0; i < this.n; ++i) {
            final double alpha = this.a[i][q] / this.a[p][q];
            for (int j = 0; j <= this.n + this.n; ++j) {
                if (i != p && j != q) {
                    final double[] array = this.a[i];
                    final int n = j;
                    array[n] -= alpha * this.a[p][j];
                }
            }
        }
        for (int i = 0; i < this.n; ++i) {
            if (i != p) {
                this.a[i][q] = 0.0;
            }
        }
        for (int k = 0; k <= this.n + this.n; ++k) {
            if (k != q) {
                final double[] array2 = this.a[p];
                final int n2 = k;
                array2[n2] /= this.a[p][q];
            }
        }
        this.a[p][q] = 1.0;
    }
    
    public double[] primal() {
        final double[] x = new double[this.n];
        for (int i = 0; i < this.n; ++i) {
            if (Math.abs(this.a[i][i]) > 1.0E-8) {
                x[i] = this.a[i][this.n + this.n] / this.a[i][i];
            }
            else if (Math.abs(this.a[i][this.n + this.n]) > 1.0E-8) {
                return null;
            }
        }
        return x;
    }
    
    public double[] dual() {
        final double[] y = new double[this.n];
        for (int i = 0; i < this.n; ++i) {
            if (Math.abs(this.a[i][i]) <= 1.0E-8 && Math.abs(this.a[i][this.n + this.n]) > 1.0E-8) {
                for (int j = 0; j < this.n; ++j) {
                    y[j] = this.a[i][this.n + j];
                }
                return y;
            }
        }
        return null;
    }
    
    public boolean isFeasible() {
        return this.primal() != null;
    }
    
    private void show() {
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                StdOut.printf("%8.3f ", this.a[i][j]);
            }
            StdOut.printf("| ", new Object[0]);
            for (int j = this.n; j < this.n + this.n; ++j) {
                StdOut.printf("%8.3f ", this.a[i][j]);
            }
            StdOut.printf("| %8.3f\n", this.a[i][this.n + this.n]);
        }
        StdOut.println();
    }
    
    private boolean certifySolution(final double[][] A, final double[] b) {
        if (this.isFeasible()) {
            final double[] x = this.primal();
            for (int i = 0; i < this.n; ++i) {
                double sum = 0.0;
                for (int j = 0; j < this.n; ++j) {
                    sum += A[i][j] * x[j];
                }
                if (Math.abs(sum - b[i]) > 1.0E-8) {
                    StdOut.println("not feasible");
                    StdOut.printf("b[%d] = %8.3f, sum = %8.3f\n", i, b[i], sum);
                    return false;
                }
            }
            return true;
        }
        final double[] y = this.dual();
        for (int k = 0; k < this.n; ++k) {
            double sum = 0.0;
            for (int l = 0; l < this.n; ++l) {
                sum += A[l][k] * y[l];
            }
            if (Math.abs(sum) > 1.0E-8) {
                StdOut.println("invalid certificate of infeasibility");
                StdOut.printf("sum = %8.3f\n", sum);
                return false;
            }
        }
        double sum2 = 0.0;
        for (int m = 0; m < this.n; ++m) {
            sum2 += y[m] * b[m];
        }
        if (Math.abs(sum2) < 1.0E-8) {
            StdOut.println("invalid certificate of infeasibility");
            StdOut.printf("yb  = %8.3f\n", sum2);
            return false;
        }
        return true;
    }
    
    private static void test(final String name, final double[][] A, final double[] b) {
        StdOut.println("----------------------------------------------------");
        StdOut.println(name);
        StdOut.println("----------------------------------------------------");
        final GaussJordanElimination gaussian = new GaussJordanElimination(A, b);
        if (gaussian.isFeasible()) {
            StdOut.println("Solution to Ax = b");
            final double[] x = gaussian.primal();
            for (int i = 0; i < x.length; ++i) {
                StdOut.printf("%10.6f\n", x[i]);
            }
        }
        else {
            StdOut.println("Certificate of infeasibility");
            final double[] y = gaussian.dual();
            for (int j = 0; j < y.length; ++j) {
                StdOut.printf("%10.6f\n", y[j]);
            }
        }
        StdOut.println();
        StdOut.println();
    }
    
    private static void test1() {
        final double[][] A = { { 0.0, 1.0, 1.0 }, { 2.0, 4.0, -2.0 }, { 0.0, 3.0, 15.0 } };
        final double[] b = { 4.0, 2.0, 36.0 };
        test("test 1", A, b);
    }
    
    private static void test2() {
        final double[][] A = { { 1.0, -3.0, 1.0 }, { 2.0, -8.0, 8.0 }, { -6.0, 3.0, -15.0 } };
        final double[] b = { 4.0, -2.0, 9.0 };
        test("test 2", A, b);
    }
    
    private static void test3() {
        final double[][] A = { { 2.0, -3.0, -1.0, 2.0, 3.0 }, { 4.0, -4.0, -1.0, 4.0, 11.0 }, { 2.0, -5.0, -2.0, 2.0, -1.0 }, { 0.0, 2.0, 1.0, 0.0, 4.0 }, { -4.0, 6.0, 0.0, 0.0, 7.0 } };
        final double[] b = { 4.0, 4.0, 9.0, -6.0, 5.0 };
        test("test 3", A, b);
    }
    
    private static void test4() {
        final double[][] A = { { 2.0, -3.0, -1.0, 2.0, 3.0 }, { 4.0, -4.0, -1.0, 4.0, 11.0 }, { 2.0, -5.0, -2.0, 2.0, -1.0 }, { 0.0, 2.0, 1.0, 0.0, 4.0 }, { -4.0, 6.0, 0.0, 0.0, 7.0 } };
        final double[] b = { 4.0, 4.0, 9.0, -5.0, 5.0 };
        test("test 4", A, b);
    }
    
    private static void test5() {
        final double[][] A = { { 2.0, -1.0, 1.0 }, { 3.0, 2.0, -4.0 }, { -6.0, 3.0, -3.0 } };
        final double[] b = { 1.0, 4.0, 2.0 };
        test("test 5", A, b);
    }
    
    private static void test6() {
        final double[][] A = { { 1.0, -1.0, 2.0 }, { 4.0, 4.0, -2.0 }, { -2.0, 2.0, -4.0 } };
        final double[] b = { -3.0, 1.0, 6.0 };
        test("test 6 (infinitely many solutions)", A, b);
    }
    
    public static void main(final String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        final int n = Integer.parseInt(args[0]);
        double[][] A = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                A[i][j] = StdRandom.uniform(1000);
            }
        }
        double[] b = new double[n];
        for (int k = 0; k < n; ++k) {
            b[k] = StdRandom.uniform(1000);
        }
        test("random " + n + "-by-" + n + " (likely full rank)", A, b);
        A = new double[n][n];
        for (int k = 0; k < n - 1; ++k) {
            for (int l = 0; l < n; ++l) {
                A[k][l] = StdRandom.uniform(1000);
            }
        }
        for (int k = 0; k < n - 1; ++k) {
            final double alpha = StdRandom.uniform(11) - 5.0;
            for (int m = 0; m < n; ++m) {
                final double[] array = A[n - 1];
                final int n2 = m;
                array[n2] += alpha * A[k][m];
            }
        }
        b = new double[n];
        for (int k = 0; k < n; ++k) {
            b[k] = StdRandom.uniform(1000);
        }
        test("random " + n + "-by-" + n + " (likely infeasible)", A, b);
    }
}
