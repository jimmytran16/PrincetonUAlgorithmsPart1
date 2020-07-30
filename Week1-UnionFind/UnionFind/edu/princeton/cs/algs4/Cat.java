// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Cat
{
    private Cat() {
    }
    
    public static void main(final String[] args) {
        final Out out = new Out(args[args.length - 1]);
        for (int i = 0; i < args.length - 1; ++i) {
            final In in = new In(args[i]);
            final String s = in.readAll();
            out.println(s);
            in.close();
        }
        out.close();
    }
}
