// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Multiway
{
    private Multiway() {
    }
    
    private static void merge(final In[] streams) {
        final int n = streams.length;
        final IndexMinPQ<String> pq = new IndexMinPQ<String>(n);
        for (int i = 0; i < n; ++i) {
            if (!streams[i].isEmpty()) {
                pq.insert(i, streams[i].readString());
            }
        }
        while (!pq.isEmpty()) {
            StdOut.print(pq.minKey() + " ");
            final int i = pq.delMin();
            if (!streams[i].isEmpty()) {
                pq.insert(i, streams[i].readString());
            }
        }
        StdOut.println();
    }
    
    public static void main(final String[] args) {
        final int n = args.length;
        final In[] streams = new In[n];
        for (int i = 0; i < n; ++i) {
            streams[i] = new In(args[i]);
        }
        merge(streams);
    }
}
