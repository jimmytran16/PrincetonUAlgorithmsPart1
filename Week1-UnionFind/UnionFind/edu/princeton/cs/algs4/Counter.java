// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Counter implements Comparable<Counter>
{
    private final String name;
    private int count;
    
    public Counter(final String id) {
        this.count = 0;
        this.name = id;
    }
    
    public void increment() {
        ++this.count;
    }
    
    public int tally() {
        return this.count;
    }
    
    @Override
    public String toString() {
        return this.count + " " + this.name;
    }
    
    @Override
    public int compareTo(final Counter that) {
        if (this.count < that.count) {
            return -1;
        }
        if (this.count > that.count) {
            return 1;
        }
        return 0;
    }
    
    public static void main(final String[] args) {
        final int n = Integer.parseInt(args[0]);
        final int trials = Integer.parseInt(args[1]);
        final Counter[] hits = new Counter[n];
        for (int i = 0; i < n; ++i) {
            hits[i] = new Counter("counter" + i);
        }
        for (int t = 0; t < trials; ++t) {
            hits[StdRandom.uniform(n)].increment();
        }
        for (int i = 0; i < n; ++i) {
            StdOut.println(hits[i]);
        }
    }
}
