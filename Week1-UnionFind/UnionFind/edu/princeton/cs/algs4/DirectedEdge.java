// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class DirectedEdge
{
    private final int v;
    private final int w;
    private final double weight;
    
    public DirectedEdge(final int v, final int w, final double weight) {
        if (v < 0) {
            throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        }
        if (w < 0) {
            throw new IllegalArgumentException("Vertex names must be nonnegative integers");
        }
        if (Double.isNaN(weight)) {
            throw new IllegalArgumentException("Weight is NaN");
        }
        this.v = v;
        this.w = w;
        this.weight = weight;
    }
    
    public int from() {
        return this.v;
    }
    
    public int to() {
        return this.w;
    }
    
    public double weight() {
        return this.weight;
    }
    
    @Override
    public String toString() {
        return this.v + "->" + this.w + " " + String.format("%5.2f", this.weight);
    }
    
    public static void main(final String[] args) {
        final DirectedEdge e = new DirectedEdge(12, 34, 5.67);
        StdOut.println(e);
    }
}
