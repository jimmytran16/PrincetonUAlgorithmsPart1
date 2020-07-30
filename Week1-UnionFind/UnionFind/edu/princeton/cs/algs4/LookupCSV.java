// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class LookupCSV
{
    private LookupCSV() {
    }
    
    public static void main(final String[] args) {
        final int keyField = Integer.parseInt(args[1]);
        final int valField = Integer.parseInt(args[2]);
        final ST<String, String> st = new ST<String, String>();
        final In in = new In(args[0]);
        while (in.hasNextLine()) {
            final String line = in.readLine();
            final String[] tokens = line.split(",");
            final String key = tokens[keyField];
            final String val = tokens[valField];
            st.put(key, val);
        }
        while (!StdIn.isEmpty()) {
            final String s = StdIn.readString();
            if (st.contains(s)) {
                StdOut.println(st.get(s));
            }
            else {
                StdOut.println("Not found");
            }
        }
    }
}
