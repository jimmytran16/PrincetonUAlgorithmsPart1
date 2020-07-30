// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DirectedEulerianCycle
{
    private Stack<Integer> cycle;
    
    public DirectedEulerianCycle(final Digraph G) {
        this.cycle = null;
        if (G.E() == 0) {
            return;
        }
        for (int v = 0; v < G.V(); ++v) {
            if (G.outdegree(v) != G.indegree(v)) {
                return;
            }
        }
        final Iterator<Integer>[] adj = (Iterator<Integer>[])new Iterator[G.V()];
        for (int v2 = 0; v2 < G.V(); ++v2) {
            adj[v2] = G.adj(v2).iterator();
        }
        final int s = nonIsolatedVertex(G);
        final Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        this.cycle = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v3;
            for (v3 = stack.pop(); adj[v3].hasNext(); v3 = adj[v3].next()) {
                stack.push(v3);
            }
            this.cycle.push(v3);
        }
        if (this.cycle.size() != G.E() + 1) {
            this.cycle = null;
        }
        assert this.certifySolution(G);
    }
    
    public Iterable<Integer> cycle() {
        return this.cycle;
    }
    
    public boolean hasEulerianCycle() {
        return this.cycle != null;
    }
    
    private static int nonIsolatedVertex(final Digraph G) {
        for (int v = 0; v < G.V(); ++v) {
            if (G.outdegree(v) > 0) {
                return v;
            }
        }
        return -1;
    }
    
    private static boolean satisfiesNecessaryAndSufficientConditions(final Digraph G) {
        if (G.E() == 0) {
            return false;
        }
        for (int v = 0; v < G.V(); ++v) {
            if (G.outdegree(v) != G.indegree(v)) {
                return false;
            }
        }
        final Graph H = new Graph(G.V());
        for (int v2 = 0; v2 < G.V(); ++v2) {
            for (final int w : G.adj(v2)) {
                H.addEdge(v2, w);
            }
        }
        final int s = nonIsolatedVertex(G);
        final BreadthFirstPaths bfs = new BreadthFirstPaths(H, s);
        for (int v3 = 0; v3 < G.V(); ++v3) {
            if (H.degree(v3) > 0 && !bfs.hasPathTo(v3)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean certifySolution(final Digraph G) {
        return this.hasEulerianCycle() != (this.cycle() == null) && this.hasEulerianCycle() == satisfiesNecessaryAndSufficientConditions(G) && (this.cycle == null || this.cycle.size() == G.E() + 1);
    }
    
    private static void unitTest(final Digraph G, final String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);
        final DirectedEulerianCycle euler = new DirectedEulerianCycle(G);
        StdOut.print("Eulerian cycle: ");
        if (euler.hasEulerianCycle()) {
            for (final int v : euler.cycle()) {
                StdOut.print(v + " ");
            }
            StdOut.println();
        }
        else {
            StdOut.println("none");
        }
        StdOut.println();
    }
    
    public static void main(final String[] args) {
        final int V = Integer.parseInt(args[0]);
        final int E = Integer.parseInt(args[1]);
        final Digraph G1 = DigraphGenerator.eulerianCycle(V, E);
        unitTest(G1, "Eulerian cycle");
        final Digraph G2 = DigraphGenerator.eulerianPath(V, E);
        unitTest(G2, "Eulerian path");
        final Digraph G3 = new Digraph(V);
        unitTest(G3, "empty digraph");
        final Digraph G4 = new Digraph(V);
        final int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");
        final Digraph H1 = DigraphGenerator.eulerianCycle(V / 2, E / 2);
        final Digraph H2 = DigraphGenerator.eulerianCycle(V - V / 2, E - E / 2);
        final int[] perm = new int[V];
        for (int i = 0; i < V; ++i) {
            perm[i] = i;
        }
        StdRandom.shuffle(perm);
        final Digraph G5 = new Digraph(V);
        for (int v5 = 0; v5 < H1.V(); ++v5) {
            for (final int w : H1.adj(v5)) {
                G5.addEdge(perm[v5], perm[w]);
            }
        }
        for (int v5 = 0; v5 < H2.V(); ++v5) {
            for (final int w : H2.adj(v5)) {
                G5.addEdge(perm[V / 2 + v5], perm[V / 2 + w]);
            }
        }
        unitTest(G5, "Union of two disjoint cycles");
        final Digraph G6 = DigraphGenerator.simple(V, E);
        unitTest(G6, "simple digraph");
        final Digraph G7 = new Digraph(new In("eulerianD.txt"));
        unitTest(G7, "4-vertex Eulerian digraph");
    }
}
