// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class TwoPersonZeroSumGame
{
    private static final double EPSILON = 1.0E-8;
    private final int m;
    private final int n;
    private LinearProgramming lp;
    private double constant;
    
    public TwoPersonZeroSumGame(final double[][] payoff) {
        this.m = payoff.length;
        this.n = payoff[0].length;
        final double[] c = new double[this.n];
        final double[] b = new double[this.m];
        final double[][] A = new double[this.m][this.n];
        for (int i = 0; i < this.m; ++i) {
            b[i] = 1.0;
        }
        for (int j = 0; j < this.n; ++j) {
            c[j] = 1.0;
        }
        this.constant = Double.POSITIVE_INFINITY;
        for (int i = 0; i < this.m; ++i) {
            for (int k = 0; k < this.n; ++k) {
                if (payoff[i][k] < this.constant) {
                    this.constant = payoff[i][k];
                }
            }
        }
        if (this.constant <= 0.0) {
            this.constant = -this.constant + 1.0;
        }
        else {
            this.constant = 0.0;
        }
        for (int i = 0; i < this.m; ++i) {
            for (int k = 0; k < this.n; ++k) {
                A[i][k] = payoff[i][k] + this.constant;
            }
        }
        this.lp = new LinearProgramming(A, b, c);
        assert this.certifySolution(payoff);
    }
    
    public double value() {
        return 1.0 / this.scale() - this.constant;
    }
    
    private double scale() {
        final double[] x = this.lp.primal();
        double sum = 0.0;
        for (int j = 0; j < this.n; ++j) {
            sum += x[j];
        }
        return sum;
    }
    
    public double[] row() {
        final double scale = this.scale();
        final double[] x = this.lp.primal();
        for (int j = 0; j < this.n; ++j) {
            final double[] array = x;
            final int n = j;
            array[n] /= scale;
        }
        return x;
    }
    
    public double[] column() {
        final double scale = this.scale();
        final double[] y = this.lp.dual();
        for (int i = 0; i < this.m; ++i) {
            final double[] array = y;
            final int n = i;
            array[n] /= scale;
        }
        return y;
    }
    
    private boolean isPrimalFeasible() {
        final double[] x = this.row();
        double sum = 0.0;
        for (int j = 0; j < this.n; ++j) {
            if (x[j] < 0.0) {
                StdOut.println("row vector not a probability distribution");
                StdOut.printf("    x[%d] = %f\n", j, x[j]);
                return false;
            }
            sum += x[j];
        }
        if (Math.abs(sum - 1.0) > 1.0E-8) {
            StdOut.println("row vector x[] is not a probability distribution");
            StdOut.println("    sum = " + sum);
            return false;
        }
        return true;
    }
    
    private boolean isDualFeasible() {
        final double[] y = this.column();
        double sum = 0.0;
        for (int i = 0; i < this.m; ++i) {
            if (y[i] < 0.0) {
                StdOut.println("column vector y[] is not a probability distribution");
                StdOut.printf("    y[%d] = %f\n", i, y[i]);
                return false;
            }
            sum += y[i];
        }
        if (Math.abs(sum - 1.0) > 1.0E-8) {
            StdOut.println("column vector not a probability distribution");
            StdOut.println("    sum = " + sum);
            return false;
        }
        return true;
    }
    
    private boolean isNashEquilibrium(final double[][] payoff) {
        final double[] x = this.row();
        final double[] y = this.column();
        final double value = this.value();
        double opt1 = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < this.m; ++i) {
            double sum = 0.0;
            for (int j = 0; j < this.n; ++j) {
                sum += payoff[i][j] * x[j];
            }
            if (sum > opt1) {
                opt1 = sum;
            }
        }
        if (Math.abs(opt1 - value) > 1.0E-8) {
            StdOut.println("Optimal value = " + value);
            StdOut.println("Optimal best response for column player = " + opt1);
            return false;
        }
        double opt2 = Double.POSITIVE_INFINITY;
        for (int k = 0; k < this.n; ++k) {
            double sum2 = 0.0;
            for (int l = 0; l < this.m; ++l) {
                sum2 += payoff[l][k] * y[l];
            }
            if (sum2 < opt2) {
                opt2 = sum2;
            }
        }
        if (Math.abs(opt2 - value) > 1.0E-8) {
            StdOut.println("Optimal value = " + value);
            StdOut.println("Optimal best response for row player = " + opt2);
            return false;
        }
        return true;
    }
    
    private boolean certifySolution(final double[][] payoff) {
        return this.isPrimalFeasible() && this.isDualFeasible() && this.isNashEquilibrium(payoff);
    }
    
    private static void test(final String description, final double[][] payoff) {
        StdOut.println();
        StdOut.println(description);
        StdOut.println("------------------------------------");
        final int m = payoff.length;
        final int n = payoff[0].length;
        final TwoPersonZeroSumGame zerosum = new TwoPersonZeroSumGame(payoff);
        final double[] x = zerosum.row();
        final double[] y = zerosum.column();
        StdOut.print("x[] = [");
        for (int j = 0; j < n - 1; ++j) {
            StdOut.printf("%8.4f, ", x[j]);
        }
        StdOut.printf("%8.4f]\n", x[n - 1]);
        StdOut.print("y[] = [");
        for (int i = 0; i < m - 1; ++i) {
            StdOut.printf("%8.4f, ", y[i]);
        }
        StdOut.printf("%8.4f]\n", y[m - 1]);
        StdOut.println("value =  " + zerosum.value());
    }
    
    private static void test1() {
        final double[][] payoff = { { 30.0, -10.0, 20.0 }, { 10.0, 20.0, -20.0 } };
        test("wikipedia", payoff);
    }
    
    private static void test2() {
        final double[][] payoff = { { 0.0, 2.0, -3.0, 0.0 }, { -2.0, 0.0, 0.0, 3.0 }, { 3.0, 0.0, 0.0, -4.0 }, { 0.0, -3.0, 4.0, 0.0 } };
        test("Chvatal, p. 230", payoff);
    }
    
    private static void test3() {
        final double[][] payoff = { { 0.0, 2.0, -3.0, 0.0 }, { -2.0, 0.0, 0.0, 3.0 }, { 3.0, 0.0, 0.0, -4.0 }, { 0.0, -3.0, 4.0, 0.0 }, { 0.0, 0.0, -3.0, 3.0 }, { -2.0, 2.0, 0.0, 0.0 }, { 3.0, -3.0, 0.0, 0.0 }, { 0.0, 0.0, 4.0, -4.0 } };
        test("Chvatal, p. 234", payoff);
    }
    
    private static void test4() {
        final double[][] payoff = { { 0.0, 2.0, -1.0, -1.0 }, { 0.0, 1.0, -2.0, -1.0 }, { -1.0, -1.0, 1.0, 1.0 }, { -1.0, 0.0, 0.0, 1.0 }, { 1.0, -2.0, 0.0, -3.0 }, { 1.0, -1.0, -1.0, -3.0 }, { 0.0, -3.0, 2.0, -1.0 }, { 0.0, -2.0, 1.0, -1.0 } };
        test("Chvatal p. 236", payoff);
    }
    
    private static void test5() {
        final double[][] payoff = { { 0.0, -1.0, 1.0 }, { 1.0, 0.0, -1.0 }, { -1.0, 1.0, 0.0 } };
        test("rock, paper, scisssors", payoff);
    }
    
    public static void main(final String[] args) {
        test1();
        test2();
        test3();
        test4();
        test5();
        final int m = Integer.parseInt(args[0]);
        final int n = Integer.parseInt(args[1]);
        final double[][] payoff = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                payoff[i][j] = StdRandom.uniform(-0.5, 0.5);
            }
        }
        test("random " + m + "-by-" + n, payoff);
    }
}
