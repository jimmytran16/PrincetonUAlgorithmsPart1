// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class StdArrayIO
{
    private StdArrayIO() {
    }
    
    public static double[] readDouble1D() {
        final int n = StdIn.readInt();
        final double[] a = new double[n];
        for (int i = 0; i < n; ++i) {
            a[i] = StdIn.readDouble();
        }
        return a;
    }
    
    public static void print(final double[] a) {
        final int n = a.length;
        StdOut.println(n);
        for (int i = 0; i < n; ++i) {
            StdOut.printf("%9.5f ", a[i]);
        }
        StdOut.println();
    }
    
    public static double[][] readDouble2D() {
        final int m = StdIn.readInt();
        final int n = StdIn.readInt();
        final double[][] a = new double[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = StdIn.readDouble();
            }
        }
        return a;
    }
    
    public static void print(final double[][] a) {
        final int m = a.length;
        final int n = a[0].length;
        StdOut.println(m + " " + n);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                StdOut.printf("%9.5f ", a[i][j]);
            }
            StdOut.println();
        }
    }
    
    public static int[] readInt1D() {
        final int n = StdIn.readInt();
        final int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = StdIn.readInt();
        }
        return a;
    }
    
    public static void print(final int[] a) {
        final int n = a.length;
        StdOut.println(n);
        for (int i = 0; i < n; ++i) {
            StdOut.printf("%9d ", a[i]);
        }
        StdOut.println();
    }
    
    public static int[][] readInt2D() {
        final int m = StdIn.readInt();
        final int n = StdIn.readInt();
        final int[][] a = new int[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = StdIn.readInt();
            }
        }
        return a;
    }
    
    public static void print(final int[][] a) {
        final int m = a.length;
        final int n = a[0].length;
        StdOut.println(m + " " + n);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                StdOut.printf("%9d ", a[i][j]);
            }
            StdOut.println();
        }
    }
    
    public static boolean[] readBoolean1D() {
        final int n = StdIn.readInt();
        final boolean[] a = new boolean[n];
        for (int i = 0; i < n; ++i) {
            a[i] = StdIn.readBoolean();
        }
        return a;
    }
    
    public static void print(final boolean[] a) {
        final int n = a.length;
        StdOut.println(n);
        for (int i = 0; i < n; ++i) {
            if (a[i]) {
                StdOut.print("1 ");
            }
            else {
                StdOut.print("0 ");
            }
        }
        StdOut.println();
    }
    
    public static boolean[][] readBoolean2D() {
        final int m = StdIn.readInt();
        final int n = StdIn.readInt();
        final boolean[][] a = new boolean[m][n];
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                a[i][j] = StdIn.readBoolean();
            }
        }
        return a;
    }
    
    public static void print(final boolean[][] a) {
        final int m = a.length;
        final int n = a[0].length;
        StdOut.println(m + " " + n);
        for (int i = 0; i < m; ++i) {
            for (int j = 0; j < n; ++j) {
                if (a[i][j]) {
                    StdOut.print("1 ");
                }
                else {
                    StdOut.print("0 ");
                }
            }
            StdOut.println();
        }
    }
    
    public static void main(final String[] args) {
        final double[] a = readDouble1D();
        print(a);
        StdOut.println();
        final double[][] b = readDouble2D();
        print(b);
        StdOut.println();
        final boolean[][] d = readBoolean2D();
        print(d);
        StdOut.println();
    }
}
