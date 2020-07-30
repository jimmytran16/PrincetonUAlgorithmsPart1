// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class DegreesOfSeparation
{
    private DegreesOfSeparation() {
    }
    
    public static void main(final String[] args) {
        final String filename = args[0];
        final String delimiter = args[1];
        final String source = args[2];
        final SymbolGraph sg = new SymbolGraph(filename, delimiter);
        final Graph G = sg.graph();
        if (!sg.contains(source)) {
            StdOut.println(source + " not in database.");
            return;
        }
        final int s = sg.indexOf(source);
        final BreadthFirstPaths bfs = new BreadthFirstPaths(G, s);
        while (!StdIn.isEmpty()) {
            final String sink = StdIn.readLine();
            if (sg.contains(sink)) {
                final int t = sg.indexOf(sink);
                if (bfs.hasPathTo(t)) {
                    for (final int v : bfs.pathTo(t)) {
                        StdOut.println("   " + sg.nameOf(v));
                    }
                }
                else {
                    StdOut.println("Not connected");
                }
            }
            else {
                StdOut.println("   Not in database.");
            }
        }
    }
}
