// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class HexDump
{
    private HexDump() {
    }
    
    public static void main(final String[] args) {
        int bytesPerLine = 16;
        if (args.length == 1) {
            bytesPerLine = Integer.parseInt(args[0]);
        }
        int i = 0;
        while (!BinaryStdIn.isEmpty()) {
            if (bytesPerLine == 0) {
                BinaryStdIn.readChar();
            }
            else {
                if (i == 0) {
                    StdOut.printf("", new Object[0]);
                }
                else if (i % bytesPerLine == 0) {
                    StdOut.printf("\n", i);
                }
                else {
                    StdOut.print(" ");
                }
                final char c = BinaryStdIn.readChar();
                StdOut.printf("%02x", c & '\u00ff');
            }
            ++i;
        }
        if (bytesPerLine != 0) {
            StdOut.println();
        }
        StdOut.println(i * 8 + " bits");
    }
}
