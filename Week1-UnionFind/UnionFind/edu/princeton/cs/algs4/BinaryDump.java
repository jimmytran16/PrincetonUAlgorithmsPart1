// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class BinaryDump
{
    private BinaryDump() {
    }
    
    public static void main(final String[] args) {
        int bitsPerLine = 16;
        if (args.length == 1) {
            bitsPerLine = Integer.parseInt(args[0]);
        }
        int count = 0;
        while (!BinaryStdIn.isEmpty()) {
            if (bitsPerLine == 0) {
                BinaryStdIn.readBoolean();
            }
            else {
                if (count != 0 && count % bitsPerLine == 0) {
                    StdOut.println();
                }
                if (BinaryStdIn.readBoolean()) {
                    StdOut.print(1);
                }
                else {
                    StdOut.print(0);
                }
            }
            ++count;
        }
        if (bitsPerLine != 0) {
            StdOut.println();
        }
        StdOut.println(count + " bits");
    }
}
