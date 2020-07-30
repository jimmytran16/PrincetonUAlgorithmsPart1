// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class FenwickTree
{
    int[] array;
    
    public FenwickTree(final int size) {
        this.array = new int[size + 1];
    }
    
    public int rsq(int ind) {
        assert ind > 0;
        int sum = 0;
        while (ind > 0) {
            sum += this.array[ind];
            ind -= (ind & -ind);
        }
        return sum;
    }
    
    public int rsq(final int a, final int b) {
        assert b >= a && a > 0 && b > 0;
        return this.rsq(b) - this.rsq(a - 1);
    }
    
    public void update(int ind, final int value) {
        assert ind > 0;
        while (ind < this.array.length) {
            final int[] array = this.array;
            final int n = ind;
            array[n] += value;
            ind += (ind & -ind);
        }
    }
    
    public int size() {
        return this.array.length - 1;
    }
    
    public static void main(final String[] args) {
        FenwickTree ft = null;
        final String cmd = "cmp";
        while (true) {
            final String[] line = StdIn.readLine().split(" ");
            if (line[0].equals("exit")) {
                break;
            }
            int arg1 = 0;
            int arg2 = 0;
            if (line.length > 1) {
                arg1 = Integer.parseInt(line[1]);
            }
            if (line.length > 2) {
                arg2 = Integer.parseInt(line[2]);
            }
            if (!line[0].equals("set") && !line[0].equals("init") && ft == null) {
                StdOut.println("FenwickTree not initialized");
            }
            else if (line[0].equals("init")) {
                ft = new FenwickTree(arg1);
                for (int i = 1; i <= ft.size(); ++i) {
                    StdOut.print(ft.rsq(i, i) + " ");
                }
                StdOut.println();
            }
            else if (line[0].equals("set")) {
                ft = new FenwickTree(line.length - 1);
                for (int i = 1; i <= line.length - 1; ++i) {
                    ft.update(i, Integer.parseInt(line[i]));
                }
            }
            else if (line[0].equals("up")) {
                ft.update(arg1, arg2);
                for (int i = 1; i <= ft.size(); ++i) {
                    StdOut.print(ft.rsq(i, i) + " ");
                }
                StdOut.println();
            }
            else if (line[0].equals("rsq")) {
                StdOut.printf("Sum from %d to %d = %d%n", arg1, arg2, ft.rsq(arg1, arg2));
            }
            else {
                StdOut.println("Invalid command");
            }
        }
    }
}
