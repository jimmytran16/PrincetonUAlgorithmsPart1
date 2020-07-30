// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class LookupIndex
{
    private LookupIndex() {
    }
    
    public static void main(final String[] args) {
        final String filename = args[0];
        final String separator = args[1];
        final In in = new In(filename);
        final ST<String, Queue<String>> st = new ST<String, Queue<String>>();
        final ST<String, Queue<String>> ts = new ST<String, Queue<String>>();
        while (in.hasNextLine()) {
            final String line = in.readLine();
            final String[] fields = line.split(separator);
            final String key = fields[0];
            for (int i = 1; i < fields.length; ++i) {
                final String val = fields[i];
                if (!st.contains(key)) {
                    st.put(key, new Queue<String>());
                }
                if (!ts.contains(val)) {
                    ts.put(val, new Queue<String>());
                }
                st.get(key).enqueue(val);
                ts.get(val).enqueue(key);
            }
        }
        StdOut.println("Done indexing");
        while (!StdIn.isEmpty()) {
            final String query = StdIn.readLine();
            if (st.contains(query)) {
                for (final String vals : st.get(query)) {
                    StdOut.println("  " + vals);
                }
            }
            if (ts.contains(query)) {
                for (final String keys : ts.get(query)) {
                    StdOut.println("  " + keys);
                }
            }
        }
    }
}
