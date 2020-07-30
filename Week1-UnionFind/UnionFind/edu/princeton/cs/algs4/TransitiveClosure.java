// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class TransitiveClosure
{
    private DirectedDFS[] tc;
    
    public TransitiveClosure(final Digraph G) {
        this.tc = new DirectedDFS[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            this.tc[v] = new DirectedDFS(G, v);
        }
    }
    
    public boolean reachable(final int v, final int w) {
        this.validateVertex(v);
        this.validateVertex(w);
        return this.tc[v].marked(w);
    }
    
    private void validateVertex(final int v) {
        final int V = this.tc.length;
        if (v < 0 || v >= V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (V - 1));
        }
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Digraph G = new Digraph(in);
        final TransitiveClosure tc = new TransitiveClosure(G);
        StdOut.print("     ");
        for (int v = 0; v < G.V(); ++v) {
            StdOut.printf("%3d", v);
        }
        StdOut.println();
        StdOut.println("--------------------------------------------");
        for (int v = 0; v < G.V(); ++v) {
            StdOut.printf("%3d: ", v);
            for (int w = 0; w < G.V(); ++w) {
                if (tc.reachable(v, w)) {
                    StdOut.printf("  T", new Object[0]);
                }
                else {
                    StdOut.printf("   ", new Object[0]);
                }
            }
            StdOut.println();
        }
    }
}
