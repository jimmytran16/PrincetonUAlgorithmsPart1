// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class SparseVector
{
    private int d;
    private ST<Integer, Double> st;
    
    public SparseVector(final int d) {
        this.d = d;
        this.st = new ST<Integer, Double>();
    }
    
    public void put(final int i, final double value) {
        if (i < 0 || i >= this.d) {
            throw new IllegalArgumentException("Illegal index");
        }
        if (value == 0.0) {
            this.st.delete(i);
        }
        else {
            this.st.put(i, value);
        }
    }
    
    public double get(final int i) {
        if (i < 0 || i >= this.d) {
            throw new IllegalArgumentException("Illegal index");
        }
        if (this.st.contains(i)) {
            return this.st.get(i);
        }
        return 0.0;
    }
    
    public int nnz() {
        return this.st.size();
    }
    
    @Deprecated
    public int size() {
        return this.d;
    }
    
    public int dimension() {
        return this.d;
    }
    
    public double dot(final SparseVector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Vector lengths disagree");
        }
        double sum = 0.0;
        if (this.st.size() <= that.st.size()) {
            for (final int i : this.st.keys()) {
                if (that.st.contains(i)) {
                    sum += this.get(i) * that.get(i);
                }
            }
        }
        else {
            for (final int i : that.st.keys()) {
                if (this.st.contains(i)) {
                    sum += this.get(i) * that.get(i);
                }
            }
        }
        return sum;
    }
    
    public double dot(final double[] that) {
        double sum = 0.0;
        for (final int i : this.st.keys()) {
            sum += that[i] * this.get(i);
        }
        return sum;
    }
    
    public double magnitude() {
        return Math.sqrt(this.dot(this));
    }
    
    @Deprecated
    public double norm() {
        return Math.sqrt(this.dot(this));
    }
    
    public SparseVector scale(final double alpha) {
        final SparseVector c = new SparseVector(this.d);
        for (final int i : this.st.keys()) {
            c.put(i, alpha * this.get(i));
        }
        return c;
    }
    
    public SparseVector plus(final SparseVector that) {
        if (this.d != that.d) {
            throw new IllegalArgumentException("Vector lengths disagree");
        }
        final SparseVector c = new SparseVector(this.d);
        for (final int i : this.st.keys()) {
            c.put(i, this.get(i));
        }
        for (final int i : that.st.keys()) {
            c.put(i, that.get(i) + c.get(i));
        }
        return c;
    }
    
    @Override
    public String toString() {
        final StringBuilder s = new StringBuilder();
        for (final int i : this.st.keys()) {
            s.append("(" + i + ", " + this.st.get(i) + ") ");
        }
        return s.toString();
    }
    
    public static void main(final String[] args) {
        final SparseVector a = new SparseVector(10);
        final SparseVector b = new SparseVector(10);
        a.put(3, 0.5);
        a.put(9, 0.75);
        a.put(6, 0.11);
        a.put(6, 0.0);
        b.put(3, 0.6);
        b.put(4, 0.9);
        StdOut.println("a = " + a);
        StdOut.println("b = " + b);
        StdOut.println("a dot b = " + a.dot(b));
        StdOut.println("a + b   = " + a.plus(b));
    }
}
