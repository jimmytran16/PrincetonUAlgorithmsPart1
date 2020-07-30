// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DirectedCycle
{
    private boolean[] marked;
    private int[] edgeTo;
    private boolean[] onStack;
    private Stack<Integer> cycle;
    
    public DirectedCycle(final Digraph G) {
        this.marked = new boolean[G.V()];
        this.onStack = new boolean[G.V()];
        this.edgeTo = new int[G.V()];
        for (int v = 0; v < G.V(); ++v) {
            if (!this.marked[v] && this.cycle == null) {
                this.dfs(G, v);
            }
        }
    }
    
    private void dfs(final Digraph G, final int v) {
        this.onStack[v] = true;
        this.marked[v] = true;
        for (final int w : G.adj(v)) {
            if (this.cycle != null) {
                return;
            }
            if (!this.marked[w]) {
                this.edgeTo[w] = v;
                this.dfs(G, w);
            }
            else {
                if (!this.onStack[w]) {
                    continue;
                }
                this.cycle = new Stack<Integer>();
                for (int x = v; x != w; x = this.edgeTo[x]) {
                    this.cycle.push(x);
                }
                this.cycle.push(w);
                this.cycle.push(v);
                assert this.check();
                continue;
            }
        }
        this.onStack[v] = false;
    }
    
    public boolean hasCycle() {
        return this.cycle != null;
    }
    
    public Iterable<Integer> cycle() {
        return this.cycle;
    }
    
    private boolean check() {
        if (this.hasCycle()) {
            int first = -1;
            int last = -1;
            for (final int v : this.cycle()) {
                if (first == -1) {
                    first = v;
                }
                last = v;
            }
            if (first != last) {
                System.err.printf("cycle begins with %d and ends with %d\n", first, last);
                return false;
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final In in = new In(args[0]);
        final Digraph G = new Digraph(in);
        final DirectedCycle finder = new DirectedCycle(G);
        if (finder.hasCycle()) {
            StdOut.print("Directed cycle: ");
            for (final int v : finder.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("No directed cycle");
        }
        StdOut.println();
    }
}
