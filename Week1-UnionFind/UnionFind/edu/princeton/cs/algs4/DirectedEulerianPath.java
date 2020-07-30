// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DirectedEulerianPath
{
    private Stack<Integer> path;
    
    public DirectedEulerianPath(final Digraph G) {
        this.path = null;
        int deficit = 0;
        int s = nonIsolatedVertex(G);
        for (int v = 0; v < G.V(); ++v) {
            if (G.outdegree(v) > G.indegree(v)) {
                deficit += G.outdegree(v) - G.indegree(v);
                s = v;
            }
        }
        if (deficit > 1) {
            return;
        }
        if (s == -1) {
            s = 0;
        }
        final Iterator<Integer>[] adj = (Iterator<Integer>[])new Iterator[G.V()];
        for (int v2 = 0; v2 < G.V(); ++v2) {
            adj[v2] = G.adj(v2).iterator();
        }
        final Stack<Integer> stack = new Stack<Integer>();
        stack.push(s);
        this.path = new Stack<Integer>();
        while (!stack.isEmpty()) {
            int v3;
            for (v3 = stack.pop(); adj[v3].hasNext(); v3 = adj[v3].next()) {
                stack.push(v3);
            }
            this.path.push(v3);
        }
        if (this.path.size() != G.E() + 1) {
            this.path = null;
        }
        assert this.check(G);
    }
    
    public Iterable<Integer> path() {
        return this.path;
    }
    
    public boolean hasEulerianPath() {
        return this.path != null;
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
            return true;
        }
        int deficit = 0;
        for (int v = 0; v < G.V(); ++v) {
            if (G.outdegree(v) > G.indegree(v)) {
                deficit += G.outdegree(v) - G.indegree(v);
            }
        }
        if (deficit > 1) {
            return false;
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
    
    private boolean check(final Digraph G) {
        return this.hasEulerianPath() != (this.path() == null) && this.hasEulerianPath() == satisfiesNecessaryAndSufficientConditions(G) && (this.path == null || this.path.size() == G.E() + 1);
    }
    
    private static void unitTest(final Digraph G, final String description) {
        StdOut.println(description);
        StdOut.println("-------------------------------------");
        StdOut.print(G);
        final DirectedEulerianPath euler = new DirectedEulerianPath(G);
        StdOut.print("Eulerian path:  ");
        if (euler.hasEulerianPath()) {
            for (final int v : euler.path()) {
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
        final Digraph G3 = new Digraph(G2);
        G3.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G3, "one random edge added to Eulerian path");
        final Digraph G4 = new Digraph(V);
        final int v4 = StdRandom.uniform(V);
        G4.addEdge(v4, v4);
        unitTest(G4, "single self loop");
        final Digraph G5 = new Digraph(V);
        G5.addEdge(StdRandom.uniform(V), StdRandom.uniform(V));
        unitTest(G5, "single edge");
        final Digraph G6 = new Digraph(V);
        unitTest(G6, "empty digraph");
        final Digraph G7 = DigraphGenerator.simple(V, E);
        unitTest(G7, "simple digraph");
        final Digraph G8 = new Digraph(new In("eulerianD.txt"));
        unitTest(G8, "4-vertex Eulerian digraph");
    }
}
