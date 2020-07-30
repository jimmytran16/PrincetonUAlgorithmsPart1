// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class DeDup
{
    private DeDup() {
    }
    
    public static void main(final String[] args) {
        final SET<String> set = new SET<String>();
        while (!StdIn.isEmpty()) {
            final String key = StdIn.readString();
            if (!set.contains(key)) {
                set.add(key);
                StdOut.println(key);
            }
        }
    }
}
