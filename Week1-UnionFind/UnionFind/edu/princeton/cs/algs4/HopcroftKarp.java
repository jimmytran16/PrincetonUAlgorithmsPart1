// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class HopcroftKarp
{
    private static final int UNMATCHED = -1;
    private final int V;
    private BipartiteX bipartition;
    private int cardinality;
    private int[] mate;
    private boolean[] inMinVertexCover;
    private boolean[] marked;
    private int[] distTo;
    
    public HopcroftKarp(final Graph G) {
        this.bipartition = new BipartiteX(G);
        if (!this.bipartition.isBipartite()) {
            throw new IllegalArgumentException("graph is not bipartite");
        }
        this.V = G.V();
        this.mate = new int[this.V];
        for (int v = 0; v < this.V; ++v) {
            this.mate[v] = -1;
        }
        while (this.hasAugmentingPath(G)) {
            final Iterator<Integer>[] adj = (Iterator<Integer>[])new Iterator[G.V()];
            for (int v2 = 0; v2 < G.V(); ++v2) {
                adj[v2] = G.adj(v2).iterator();
            }
            for (int s = 0; s < this.V; ++s) {
                if (!this.isMatched(s)) {
                    if (this.bipartition.color(s)) {
                        final Stack<Integer> path = new Stack<Integer>();
                        path.push(s);
                        while (!path.isEmpty()) {
                            final int v3 = path.peek();
                            if (!adj[v3].hasNext()) {
                                path.pop();
                            }
                            else {
                                final int w = adj[v3].next();
                                if (!this.isLevelGraphEdge(v3, w)) {
                                    continue;
                                }
                                path.push(w);
                                if (this.isMatched(w)) {
                                    continue;
                                }
                                while (!path.isEmpty()) {
                                    final int x = path.pop();
                                    final int y = path.pop();
                                    this.mate[x] = y;
                                    this.mate[y] = x;
                                }
                                ++this.cardinality;
                            }
                        }
                    }
                }
            }
        }
        this.inMinVertexCover = new boolean[this.V];
        for (int v = 0; v < this.V; ++v) {
            if (this.bipartition.color(v) && !this.marked[v]) {
                this.inMinVertexCover[v] = true;
            }
            if (!this.bipartition.color(v) && this.marked[v]) {
                this.inMinVertexCover[v] = true;
            }
        }
        assert this.certifySolution(G);
    }
    
    private static String toString(final Iterable<Integer> path) {
        final StringBuilder sb = new StringBuilder();
        for (final int v : path) {
            sb.append(v + "-");
        }
        String s = sb.toString();
        s = s.substring(0, s.lastIndexOf(45));
        return s;
    }
    
    private boolean isLevelGraphEdge(final int v, final int w) {
        return this.distTo[w] == this.distTo[v] + 1 && this.isResidualGraphEdge(v, w);
    }
    
    private boolean isResidualGraphEdge(final int v, final int w) {
        return (this.mate[v] != w && this.bipartition.color(v)) || (this.mate[v] == w && !this.bipartition.color(v));
    }
    
    private boolean hasAugmentingPath(final Graph G) {
        this.marked = new boolean[this.V];
        this.distTo = new int[this.V];
        for (int v = 0; v < this.V; ++v) {
            this.distTo[v] = Integer.MAX_VALUE;
        }
        final Queue<Integer> queue = new Queue<Integer>();
        for (int v2 = 0; v2 < this.V; ++v2) {
            if (this.bipartition.color(v2) && !this.isMatched(v2)) {
                queue.enqueue(v2);
                this.marked[v2] = true;
                this.distTo[v2] = 0;
            }
        }
        boolean hasAugmentingPath = false;
        while (!queue.isEmpty()) {
            final int v3 = queue.dequeue();
            for (final int w : G.adj(v3)) {
                if (this.isResidualGraphEdge(v3, w) && !this.marked[w]) {
                    this.distTo[w] = this.distTo[v3] + 1;
                    this.marked[w] = true;
                    if (!this.isMatched(w)) {
                        hasAugmentingPath = true;
                    }
                    if (hasAugmentingPath) {
                        continue;
                    }
                    queue.enqueue(w);
                }
            }
        }
        return hasAugmentingPath;
    }
    
    public int mate(final int v) {
        this.validate(v);
        return this.mate[v];
    }
    
    public boolean isMatched(final int v) {
        this.validate(v);
        return this.mate[v] != -1;
    }
    
    public int size() {
        return this.cardinality;
    }
    
    public boolean isPerfect() {
        return this.cardinality * 2 == this.V;
    }
    
    public boolean inMinVertexCover(final int v) {
        this.validate(v);
        return this.inMinVertexCover[v];
    }
    
    private void validate(final int v) {
        if (v < 0 || v >= this.V) {
            throw new IllegalArgumentException("vertex " + v + " is not between 0 and " + (this.V - 1));
        }
    }
    
    private boolean certifySolution(final Graph G) {
        for (int v = 0; v < this.V; ++v) {
            if (this.mate(v) != -1) {
                if (this.mate(this.mate(v)) != v) {
                    return false;
                }
            }
        }
        int matchedVertices = 0;
        for (int v2 = 0; v2 < this.V; ++v2) {
            if (this.mate(v2) != -1) {
                ++matchedVertices;
            }
        }
        if (2 * this.size() != matchedVertices) {
            return false;
        }
        int sizeOfMinVertexCover = 0;
        for (int v3 = 0; v3 < this.V; ++v3) {
            if (this.inMinVertexCover(v3)) {
                ++sizeOfMinVertexCover;
            }
        }
        if (this.size() != sizeOfMinVertexCover) {
            return false;
        }
        final boolean[] isMatched = new boolean[this.V];
        for (int v4 = 0; v4 < this.V; ++v4) {
            final int w = this.mate[v4];
            if (w != -1) {
                if (v4 == w) {
                    return false;
                }
                if (v4 < w) {
                    if (isMatched[v4] || isMatched[w]) {
                        return false;
                    }
                    isMatched[w] = (isMatched[v4] = true);
                }
            }
        }
        for (int v4 = 0; v4 < this.V; ++v4) {
            if (this.mate(v4) != -1) {
                boolean isEdge = false;
                for (final int w2 : G.adj(v4)) {
                    if (this.mate(v4) == w2) {
                        isEdge = true;
                    }
                }
                if (!isEdge) {
                    return false;
                }
            }
        }
        for (int v4 = 0; v4 < this.V; ++v4) {
            for (final int w3 : G.adj(v4)) {
                if (!this.inMinVertexCover(v4) && !this.inMinVertexCover(w3)) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static void main(final String[] args) {
        final int V1 = Integer.parseInt(args[0]);
        final int V2 = Integer.parseInt(args[1]);
        final int E = Integer.parseInt(args[2]);
        final Graph G = GraphGenerator.bipartite(V1, V2, E);
        if (G.V() < 1000) {
            StdOut.println(G);
        }
        final HopcroftKarp matching = new HopcroftKarp(G);
        StdOut.printf("Number of edges in max matching        = %d\n", matching.size());
        StdOut.printf("Number of vertices in min vertex cover = %d\n", matching.size());
        StdOut.printf("Graph has a perfect matching           = %b\n", matching.isPerfect());
        StdOut.println();
        if (G.V() >= 1000) {
            return;
        }
        StdOut.print("Max matching: ");
        for (int v = 0; v < G.V(); ++v) {
            final int w = matching.mate(v);
            if (matching.isMatched(v) && v < w) {
                StdOut.print(v + "-" + w + " ");
            }
        }
        StdOut.println();
        StdOut.print("Min vertex cover: ");
        for (int v = 0; v < G.V(); ++v) {
            if (matching.inMinVertexCover(v)) {
                StdOut.print(v + " ");
            }
        }
        StdOut.println();
    }
}
