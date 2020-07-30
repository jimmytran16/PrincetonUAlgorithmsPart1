// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class SymbolGraph
{
    private ST<String, Integer> st;
    private String[] keys;
    private Graph graph;
    
    public SymbolGraph(final String filename, final String delimiter) {
        this.st = new ST<String, Integer>();
        In in = new In(filename);
        while (!in.isEmpty()) {
            final String[] a = in.readLine().split(delimiter);
            for (int i = 0; i < a.length; ++i) {
                if (!this.st.contains(a[i])) {
                    this.st.put(a[i], this.st.size());
                }
            }
        }
        this.keys = new String[this.st.size()];
        for (final String name : this.st.keys()) {
            this.keys[this.st.get(name)] = name;
        }
        this.graph = new Graph(this.st.size());
        in = new In(filename);
        while (in.hasNextLine()) {
            final String[] a = in.readLine().split(delimiter);
            final int v = this.st.get(a[0]);
            for (int j = 1; j < a.length; ++j) {
                final int w = this.st.get(a[j]);
                this.graph.addEdge(v, w);
            }
        }
    }
    
    public boolean contains(final String s) {
        return this.st.contains(s);
    }
    
    @Deprecated
    public int index(final String s) {
        return this.st.get(s);
    }
    
    public int indexOf(final String s) {
        return this.st.get(s);
    }
    
    @Deprecated
    public String name(final int v) {
        this.validateVertex(v);
        return this.keys[v];
    }
    
    public String nameOf(final int v) {
        this.validateVertex(v);
        return this.keys[v];
    }
    
    @Deprecated
    public Graph G() {
        return this.graph;
    }
    
    public Graph graph() {
        return this.graph;
    }
    
    private void validateVertex(final int v) {
        final int V = this.graph.V();
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final String filename = args[0];
        final String delimiter = args[1];
        final SymbolGraph sg = new SymbolGraph(filename, delimiter);
        final Graph graph = sg.graph();
        while (StdIn.hasNextLine()) {
            final String source = StdIn.readLine();
            if (sg.contains(source)) {
                final int s = sg.index(source);
                for (final int v : graph.adj(s)) {
                    StdOut.println("   " + sg.name(v));
                }
            }
            else {
                StdOut.println("input not contain '" + source + "'");
            }
        }
    }
}
