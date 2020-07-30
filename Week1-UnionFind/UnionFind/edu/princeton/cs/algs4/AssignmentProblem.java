// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class AssignmentProblem
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-14;
    private static final int UNMATCHED = -1;
    private int n;
    private double[][] weight;
    private double minWeight;
    private double[] px;
    private double[] py;
    private int[] xy;
    private int[] yx;
    
    public AssignmentProblem(final double[][] weight) {
        if (weight == null) {
            throw new IllegalArgumentException("constructor argument is null");
        }
        this.n = weight.length;
        this.weight = new double[this.n][this.n];
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                if (Double.isNaN(weight[i][j])) {
                    throw new IllegalArgumentException("weight " + i + "-" + j + " is NaN");
                }
                if (weight[i][j] < this.minWeight) {
                    this.minWeight = weight[i][j];
                }
                this.weight[i][j] = weight[i][j];
            }
        }
        this.px = new double[this.n];
        this.py = new double[this.n];
        this.xy = new int[this.n];
        this.yx = new int[this.n];
        for (int i = 0; i < this.n; ++i) {
            this.xy[i] = -1;
        }
        for (int k = 0; k < this.n; ++k) {
            this.yx[k] = -1;
        }
        for (int l = 0; l < this.n; ++l) {
            assert this.isDualFeasible();
            assert this.isComplementarySlack();
            this.augment();
        }
        assert this.certifySolution();
    }
    
    private void augment() {
        final EdgeWeightedDigraph G = new EdgeWeightedDigraph(2 * this.n + 2);
        final int s = 2 * this.n;
        final int t = 2 * this.n + 1;
        for (int i = 0; i < this.n; ++i) {
            if (this.xy[i] == -1) {
                G.addEdge(new DirectedEdge(s, i, 0.0));
            }
        }
        for (int j = 0; j < this.n; ++j) {
            if (this.yx[j] == -1) {
                G.addEdge(new DirectedEdge(this.n + j, t, this.py[j]));
            }
        }
        for (int i = 0; i < this.n; ++i) {
            for (int k = 0; k < this.n; ++k) {
                if (this.xy[i] == k) {
                    G.addEdge(new DirectedEdge(this.n + k, i, 0.0));
                }
                else {
                    G.addEdge(new DirectedEdge(i, this.n + k, this.reducedCost(i, k)));
                }
            }
        }
        final DijkstraSP spt = new DijkstraSP(G, s);
        for (final DirectedEdge e : spt.pathTo(t)) {
            final int l = e.from();
            final int m = e.to() - this.n;
            if (l < this.n) {
                this.xy[l] = m;
                this.yx[m] = l;
            }
        }
        for (int i2 = 0; i2 < this.n; ++i2) {
            final double[] px = this.px;
            final int n = i2;
            px[n] += spt.distTo(i2);
        }
        for (int k = 0; k < this.n; ++k) {
            final double[] py = this.py;
            final int n2 = k;
            py[n2] += spt.distTo(this.n + k);
        }
    }
    
    private double reducedCost(final int i, final int j) {
        final double reducedCost = this.weight[i][j] - this.minWeight + this.px[i] - this.py[j];
        final double magnitude = Math.abs(this.weight[i][j]) + Math.abs(this.px[i]) + Math.abs(this.py[j]);
        if (Math.abs(reducedCost) <= 1.0E-14 * magnitude) {
            return 0.0;
        }
        assert reducedCost >= 0.0;
        return reducedCost;
    }
    
    public double dualRow(final int i) {
        this.validate(i);
        return this.px[i];
    }
    
    public double dualCol(final int j) {
        this.validate(j);
        return this.py[j];
    }
    
    public int sol(final int i) {
        this.validate(i);
        return this.xy[i];
    }
    
    public double weight() {
        double total = 0.0;
        for (int i = 0; i < this.n; ++i) {
            if (this.xy[i] != -1) {
                total += this.weight[i][this.xy[i]];
            }
        }
        return total;
    }
    
    private void validate(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException("index is not between 0 and " + (this.n - 1) + ": " + i);
        }
    }
    
    private boolean isDualFeasible() {
        for (int i = 0; i < this.n; ++i) {
            for (int j = 0; j < this.n; ++j) {
                if (this.reducedCost(i, j) < 0.0) {
                    StdOut.println("Dual variables are not feasible");
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isComplementarySlack() {
        for (int i = 0; i < this.n; ++i) {
            if (this.xy[i] != -1 && this.reducedCost(i, this.xy[i]) != 0.0) {
                StdOut.println("Primal and dual variables are not complementary slack");
                return false;
            }
        }
        return true;
    }
    
    private boolean isPerfectMatching() {
        final boolean[] perm = new boolean[this.n];
        for (int i = 0; i < this.n; ++i) {
            if (perm[this.xy[i]]) {
                StdOut.println("Not a perfect matching");
                return false;
            }
            perm[this.xy[i]] = true;
        }
        for (int j = 0; j < this.n; ++j) {
            if (this.xy[this.yx[j]] != j) {
                StdOut.println("xy[] and yx[] are not inverses");
                return false;
            }
        }
        for (int i = 0; i < this.n; ++i) {
            if (this.yx[this.xy[i]] != i) {
                StdOut.println("xy[] and yx[] are not inverses");
                return false;
            }
        }
        return true;
    }
    
    private boolean certifySolution() {
        return this.isPerfectMatching() && this.isDualFeasible() && this.isComplementarySlack();
    }
    
    public static void main(final String[] args) {
        final int n = Integer.parseInt(args[0]);
        final double[][] weight = new double[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                weight[i][j] = StdRandom.uniform(900) + 100;
            }
        }
        final AssignmentProblem assignment = new AssignmentProblem(weight);
        StdOut.printf("weight = %.0f\n", assignment.weight());
        StdOut.println();
        if (n >= 20) {
            return;
        }
        for (int k = 0; k < n; ++k) {
            for (int l = 0; l < n; ++l) {
                if (l == assignment.sol(k)) {
                    StdOut.printf("*%.0f ", weight[k][l]);
                }
                else {
                    StdOut.printf(" %.0f ", weight[k][l]);
                }
            }
            StdOut.println();
        }
    }
}
