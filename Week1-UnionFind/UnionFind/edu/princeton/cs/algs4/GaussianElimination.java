// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class GaussianElimination
{
    private static final double EPSILON = 1.0E-8;
    private final int m;
    private final int n;
    private double[][] a;
    
    public GaussianElimination(final double[][] A, final double[] b) {
        this.m = A.length;
        this.n = A[0].length;
        if (b.length != this.m) {
            throw new IllegalArgumentException("Dimensions disagree");
        }
        this.a = new double[this.m][this.n + 1];
        for (int i = 0; i < this.m; ++i) {
            for (int j = 0; j < this.n; ++j) {
                this.a[i][j] = A[i][j];
            }
        }
        for (int i = 0; i < this.m; ++i) {
            this.a[i][this.n] = b[i];
        }
        this.forwardElimination();
        assert this.certifySolution(A, b);
    }
    
    private void forwardElimination() {
        for (int p = 0; p < Math.min(this.m, this.n); ++p) {
            int max = p;
            for (int i = p + 1; i < this.m; ++i) {
                if (Math.abs(this.a[i][p]) > Math.abs(this.a[max][p])) {
                    max = i;
                }
            }
            this.swap(p, max);
            if (Math.abs(this.a[p][p]) > 1.0E-8) {
                this.pivot(p);
            }
        }
    }
    
    private void swap(final int row1, final int row2) {
        final double[] temp = this.a[row1];
        this.a[row1] = this.a[row2];
        this.a[row2] = temp;
    }
    
    private void pivot(final int p) {
        for (int i = p + 1; i < this.m; ++i) {
            final double alpha = this.a[i][p] / this.a[p][p];
            for (int j = p; j <= this.n; ++j) {
                final double[] array = this.a[i];
                final int n = j;
                array[n] -= alpha * this.a[p][j];
            }
        }
    }
    
    public double[] primal() {
        final double[] x = new double[this.n];
        for (int i = Math.min(this.n - 1, this.m - 1); i >= 0; --i) {
            double sum = 0.0;
            for (int j = i + 1; j < this.n; ++j) {
                sum += this.a[i][j] * x[j];
            }
            if (Math.abs(this.a[i][i]) > 1.0E-8) {
                x[i] = (this.a[i][this.n] - sum) / this.a[i][i];
            }
            else if (Math.abs(this.a[i][this.n] - sum) > 1.0E-8) {
                return null;
            }
        }
        for (int i = this.n; i < this.m; ++i) {
            double sum = 0.0;
            for (int j = 0; j < this.n; ++j) {
                sum += this.a[i][j] * x[j];
            }
            if (Math.abs(this.a[i][this.n] - sum) > 1.0E-8) {
                return null;
            }
        }
        return x;
    }
    
    public boolean isFeasible() {
        return this.primal() != null;
    }
    
    private boolean certifySolution(final double[][] A, final double[] b) {
        if (!this.isFeasible()) {
            return true;
        }
        final double[] x = this.primal();
        for (int i = 0; i < this.m; ++i) {
            double sum = 0.0;
            for (int j = 0; j < this.n; ++j) {
                sum += A[i][j] * x[j];
            }
            if (Math.abs(sum - b[i]) > 1.0E-8) {
                StdOut.println("not feasible");
                StdOut.println("b[" + i + "] = " + b[i] + ", sum = " + sum);
                return false;
            }
        }
        return true;
    }
    
    private static void test(final String name, final double[][] A, final double[] b) {
        StdOut.println("----------------------------------------------------");
        StdOut.println(name);
        StdOut.println("----------------------------------------------------");
        final GaussianElimination gaussian = new GaussianElimination(A, b);
        final double[] x = gaussian.primal();
        if (gaussian.isFeasible()) {
            for (int i = 0; i < x.length; ++i) {
                StdOut.printf("%.6f\n", x[i]);
            }
        }
        else {
            StdOut.println("System is infeasible");
        }
        StdOut.println();
        StdOut.println();
    }
    
    private static void test1() {
        final double[][] A = { { 0.0, 1.0, 1.0 }, { 2.0, 4.0, -2.0 }, { 0.0, 3.0, 15.0 } };
        final double[] b = { 4.0, 2.0, 36.0 };
        test("test 1 (3-by-3 system, nonsingular)", A, b);
    }
    
    private static void test2() {
        final double[][] A = { { 1.0, -3.0, 1.0 }, { 2.0, -8.0, 8.0 }, { -6.0, 3.0, -15.0 } };
        final double[] b = { 4.0, -2.0, 9.0 };
        test("test 2 (3-by-3 system, nonsingular)", A, b);
    }
    
    private static void test3() {
        final double[][] A = { { 2.0, -3.0, -1.0, 2.0, 3.0 }, { 4.0, -4.0, -1.0, 4.0, 11.0 }, { 2.0, -5.0, -2.0, 2.0, -1.0 }, { 0.0, 2.0, 1.0, 0.0, 4.0 }, { -4.0, 6.0, 0.0, 0.0, 7.0 } };
        final double[] b = { 4.0, 4.0, 9.0, -6.0, 5.0 };
        test("test 3 (5-by-5 system, no solutions)", A, b);
    }
    
    private static void test4() {
        final double[][] A = { { 2.0, -3.0, -1.0, 2.0, 3.0 }, { 4.0, -4.0, -1.0, 4.0, 11.0 }, { 2.0, -5.0, -2.0, 2.0, -1.0 }, { 0.0, 2.0, 1.0, 0.0, 4.0 }, { -4.0, 6.0, 0.0, 0.0, 7.0 } };
        final double[] b = { 4.0, 4.0, 9.0, -5.0, 5.0 };
        test("test 4 (5-by-5 system, infinitely many solutions)", A, b);
    }
    
    private static void test5() {
        final double[][] A = { { 2.0, -1.0, 1.0 }, { 3.0, 2.0, -4.0 }, { -6.0, 3.0, -3.0 } };
        final double[] b = { 1.0, 4.0, 2.0 };
        test("test 5 (3-by-3 system, no solutions)", A, b);
    }
    
    private static void test6() {
        final double[][] A = { { 1.0, -1.0, 2.0 }, { 4.0, 4.0, -2.0 }, { -2.0, 2.0, -4.0 } };
        final double[] b = { -3.0, 1.0, 6.0 };
        test("test 6 (3-by-3 system, infinitely many solutions)", A, b);
    }
    
    private static void test7() {
        final double[][] A = { { 0.0, 1.0, 1.0 }, { 2.0, 4.0, -2.0 }, { 0.0, 3.0, 15.0 }, { 2.0, 8.0, 14.0 } };
        final double[] b = { 4.0, 2.0, 36.0, 42.0 };
        test("test 7 (4-by-3 system, full rank)", A, b);
    }
    
    private static void test8() {
        final double[][] A = { { 0.0, 1.0, 1.0 }, { 2.0, 4.0, -2.0 }, { 0.0, 3.0, 15.0 }, { 2.0, 8.0, 14.0 } };
        final double[] b = { 4.0, 2.0, 36.0, 40.0 };
        test("test 8 (4-by-3 system, no solution)", A, b);
    }
    
    private static void test9() {
        final double[][] A = { { 1.0, -3.0, 1.0, 1.0 }, { 2.0, -8.0, 8.0, 2.0 }, { -6.0, 3.0, -15.0, 3.0 } };
        final double[] b = { 4.0, -2.0, 9.0 };
        test("test 9 (3-by-4 system, full rank)", A, b);
    }
    
    public static void main(final String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        test6();
        test7();
        test8();
        test9();
        final int n = Integer.parseInt(args[0]);
        final double[][] A = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                A[i][j] = StdRandom.uniform(1000);
            }
        }
        final double[] b = new double[n];
        for (int k = 0; k < n; ++k) {
            b[k] = StdRandom.uniform(1000);
        }
        test(n + "-by-" + n + " (probably nonsingular)", A, b);
    }
}
