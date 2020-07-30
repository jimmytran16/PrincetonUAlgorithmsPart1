// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;
import java.io.File;

public class FileIndex
{
    private FileIndex() {
    }
    
    public static void main(final String[] args) {
        final ST<String, SET<File>> st = new ST<String, SET<File>>();
        StdOut.println("Indexing files");
        for (final String filename : args) {
            StdOut.println("  " + filename);
            final File file = new File(filename);
            final In in = new In(file);
            while (!in.isEmpty()) {
                final String word = in.readString();
                if (!st.contains(word)) {
                    st.put(word, new SET<File>());
                }
                final SET<File> set = st.get(word);
                set.add(file);
            }
        }
        while (!StdIn.isEmpty()) {
            final String query = StdIn.readString();
            if (st.contains(query)) {
                final SET<File> set2 = st.get(query);
                for (final File file2 : set2) {
                    StdOut.println("  " + file2.getName());
                }
            }
        }
    }
}
