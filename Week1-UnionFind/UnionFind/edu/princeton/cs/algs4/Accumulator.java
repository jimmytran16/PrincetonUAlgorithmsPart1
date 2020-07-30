// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Accumulator
{
    private int n;
    private double sum;
    private double mu;
    
    public Accumulator() {
        this.n = 0;
        this.sum = 0.0;
        this.mu = 0.0;
    }
    
    public void addDataValue(final double x) {
        ++this.n;
        final double delta = x - this.mu;
        this.mu += delta / this.n;
        this.sum += (this.n - 1) / (double)this.n * delta * delta;
    }
    
    public double mean() {
        return this.mu;
    }
    
    public double var() {
        if (this.n <= 1) {
            return Double.NaN;
        }
        return this.sum / (this.n - 1);
    }
    
    public double stddev() {
        return Math.sqrt(this.var());
    }
    
    public int count() {
        return this.n;
    }
    
    @Override
    public String toString() {
        return "n = " + this.n + ", mean = " + this.mean() + ", stddev = " + this.stddev();
    }
    
    public static void main(final String[] args) {
        final Accumulator stats = new Accumulator();
        while (!StdIn.isEmpty()) {
            final double x = StdIn.readDouble();
            stats.addDataValue(x);
        }
        StdOut.printf("n      = %d\n", stats.count());
        StdOut.printf("mean   = %.5f\n", stats.mean());
        StdOut.printf("stddev = %.5f\n", stats.stddev());
        StdOut.printf("var    = %.5f\n", stats.var());
        StdOut.println(stats);
    }
}
