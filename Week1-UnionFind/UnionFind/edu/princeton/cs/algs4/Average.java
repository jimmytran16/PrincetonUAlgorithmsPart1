// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Average
{
    private Average() {
    }
    
    public static void main(final String[] args) {
        int count = 0;
        double sum = 0.0;
        while (!StdIn.isEmpty()) {
            final double value = StdIn.readDouble();
            sum += value;
            ++count;
        }
        final double average = sum / count;
        StdOut.println("Average is " + average);
    }
}
