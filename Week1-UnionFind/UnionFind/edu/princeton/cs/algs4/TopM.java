// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class TopM
{
    private TopM() {
    }
    
    public static void main(final String[] args) {
        final int m = Integer.parseInt(args[0]);
        final MinPQ<Transaction> pq = new MinPQ<Transaction>(m + 1);
        while (StdIn.hasNextLine()) {
            final String line = StdIn.readLine();
            final Transaction transaction = new Transaction(line);
            pq.insert(transaction);
            if (pq.size() > m) {
                pq.delMin();
            }
        }
        final Stack<Transaction> stack = new Stack<Transaction>();
        for (final Transaction transaction2 : pq) {
            stack.push(transaction2);
        }
        for (final Transaction transaction2 : stack) {
            StdOut.println(transaction2);
        }
    }
}
