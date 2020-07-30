// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Comparator;
import java.util.Arrays;

public class Transaction implements Comparable<Transaction>
{
    private final String who;
    private final Date when;
    private final double amount;
    
    public Transaction(final String who, final Date when, final double amount) {
        if (Double.isNaN(amount) || Double.isInfinite(amount)) {
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
        }
        this.who = who;
        this.when = when;
        this.amount = amount;
    }
    
    public Transaction(final String transaction) {
        final String[] a = transaction.split("\\s+");
        this.who = a[0];
        this.when = new Date(a[1]);
        this.amount = Double.parseDouble(a[2]);
        if (Double.isNaN(this.amount) || Double.isInfinite(this.amount)) {
            throw new IllegalArgumentException("Amount cannot be NaN or infinite");
        }
    }
    
    public String who() {
        return this.who;
    }
    
    public Date when() {
        return this.when;
    }
    
    public double amount() {
        return this.amount;
    }
    
    @Override
    public String toString() {
        return String.format("%-10s %10s %8.2f", this.who, this.when, this.amount);
    }
    
    @Override
    public int compareTo(final Transaction that) {
        return Double.compare(this.amount, that.amount);
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        final Transaction that = (Transaction)other;
        return this.amount == that.amount && this.who.equals(that.who) && this.when.equals(that.when);
    }
    
    @Override
    public int hashCode() {
        int hash = 1;
        hash = 31 * hash + this.who.hashCode();
        hash = 31 * hash + this.when.hashCode();
        hash = 31 * hash + Double.valueOf(this.amount).hashCode();
        return hash;
    }
    
    public static void main(final String[] args) {
        final Transaction[] a = { new Transaction("Turing   6/17/1990  644.08"), new Transaction("Tarjan   3/26/2002 4121.85"), new Transaction("Knuth    6/14/1999  288.34"), new Transaction("Dijkstra 8/22/2007 2678.40") };
        StdOut.println("Unsorted");
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i]);
        }
        StdOut.println();
        StdOut.println("Sort by date");
        Arrays.sort(a, new WhenOrder());
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i]);
        }
        StdOut.println();
        StdOut.println("Sort by customer");
        Arrays.sort(a, new WhoOrder());
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i]);
        }
        StdOut.println();
        StdOut.println("Sort by amount");
        Arrays.sort(a, new HowMuchOrder());
        for (int i = 0; i < a.length; ++i) {
            StdOut.println(a[i]);
        }
        StdOut.println();
    }
    
    public static class WhoOrder implements Comparator<Transaction>
    {
        @Override
        public int compare(final Transaction v, final Transaction w) {
            return v.who.compareTo(w.who);
        }
    }
    
    public static class WhenOrder implements Comparator<Transaction>
    {
        @Override
        public int compare(final Transaction v, final Transaction w) {
            return v.when.compareTo(w.when);
        }
    }
    
    public static class HowMuchOrder implements Comparator<Transaction>
    {
        @Override
        public int compare(final Transaction v, final Transaction w) {
            return Double.compare(v.amount, w.amount);
        }
    }
}
