// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Edge implements Comparable<Edge>
{
    private final int v;
    private final int w;
    private final double weight;
    
    public Edge(final int v, final int w, final double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (w < 0) {
            throw new IllegalArgumentException("vertex index must be a nonnegative integer");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    
    public double weight() {
        return this.weight;
    }
    
    public int either() {
        return this.v;
    }
    
    public int other(final int vertex) {
        if (vertex == this.v) {
            return this.w;
        }
        if (vertex == this.w) {
            return this.v;
        }
        throw new IllegalArgumentException("Illegal endpoint");
    }
    
    @Override
    public int compareTo(final Edge that) {
        return Double.compare(this.weight, that.weight);
    }
    
    @Override
    public String toString() {
        return String.format("%d-%d %.5f", this.v, this.w, this.weight);
    }
    
    public static void main(final String[] args) {
        final Edge e = new Edge(12, 34, 5.67);
        StdOut.println(e);
    }
}
