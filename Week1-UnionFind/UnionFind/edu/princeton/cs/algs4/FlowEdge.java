// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class FlowEdge
{
    private static final double FLOATING_POINT_EPSILON = 1.0E-10;
    private final int v;
    private final int w;
    private final double capacity;
    private double flow;
    
    public FlowEdge(final int v, final int w, final double capacity) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a non-negative integer");
        }
        if (w < 0) {
            throw new IllegalArgumentException("vertex index must be a non-negative integer");
        }
        if (capacity < 0.0) {
            throw new IllegalArgumentException("Edge capacity must be non-negative");
        }
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = 0.0;
    }
    
    public FlowEdge(final int v, final int w, final double capacity, final double flow) {
        if (v < 0) {
            throw new IllegalArgumentException("vertex index must be a non-negative integer");
        }
        if (w < 0) {
            throw new IllegalArgumentException("vertex index must be a non-negative integer");
        }
        if (capacity < 0.0) {
            throw new IllegalArgumentException("edge capacity must be non-negative");
        }
        if (flow > capacity) {
            throw new IllegalArgumentException("flow exceeds capacity");
        }
        if (flow < 0.0) {
            throw new IllegalArgumentException("flow must be non-negative");
        }
        this.v = v;
        this.w = w;
        this.capacity = capacity;
        this.flow = flow;
    }
    
    public FlowEdge(final FlowEdge e) {
        this.v = e.v;
        this.w = e.w;
        this.capacity = e.capacity;
        this.flow = e.flow;
    }
    
    public int from() {
        return this.v;
    }
    
    public int to() {
        return this.w;
    }
    
    public double capacity() {
        return this.capacity;
    }
    
    public double flow() {
        return this.flow;
    }
    
    public int other(final int vertex) {
        if (vertex == this.v) {
            return this.w;
        }
        if (vertex == this.w) {
            return this.v;
        }
        throw new IllegalArgumentException("invalid endpoint");
    }
    
    public double residualCapacityTo(final int vertex) {
        if (vertex == this.v) {
            return this.flow;
        }
        if (vertex == this.w) {
            return this.capacity - this.flow;
        }
        throw new IllegalArgumentException("invalid endpoint");
    }
    
    public void addResidualFlowTo(final int vertex, final double delta) {
        if (delta < 0.0) {
            throw new IllegalArgumentException("Delta must be nonnegative");
        }
        if (vertex == this.v) {
            this.flow -= delta;
        }
        else {
            if (vertex != this.w) {
                throw new IllegalArgumentException("invalid endpoint");
            }
            this.flow += delta;
        }
        if (Math.abs(this.flow) <= 1.0E-10) {
            this.flow = 0.0;
        }
        if (Math.abs(this.flow - this.capacity) <= 1.0E-10) {
            this.flow = this.capacity;
        }
        if (this.flow < 0.0) {
            throw new IllegalArgumentException("Flow is negative");
        }
        if (this.flow > this.capacity) {
            throw new IllegalArgumentException("Flow exceeds capacity");
        }
    }
    
    @Override
    public String toString() {
        return this.v + "->" + this.w + " " + this.flow + "/" + this.capacity;
    }
    
    public static void main(final String[] args) {
        final FlowEdge e = new FlowEdge(12, 23, 4.56);
        StdOut.println(e);
    }
}
