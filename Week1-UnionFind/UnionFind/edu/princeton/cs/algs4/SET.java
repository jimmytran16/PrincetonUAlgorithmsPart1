// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SET<Key extends Comparable<Key>> implements Iterable<Key>
{
    private TreeSet<Key> set;
    
    public SET() {
        this.set = new TreeSet<Key>();
    }
    
    public SET(final SET<Key> x) {
        this.set = new TreeSet<Key>(x.set);
    }
    
    public void add(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called add() with a null key");
        }
        this.set.add(key);
    }
    
    public boolean contains(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called contains() with a null key");
        }
        return this.set.contains(key);
    }
    
    public void delete(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called delete() with a null key");
        }
        this.set.remove(key);
    }
    
    public void remove(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called remove() with a null key");
        }
        this.set.remove(key);
    }
    
    public int size() {
        return this.set.size();
    }
    
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public Iterator<Key> iterator() {
        return this.set.iterator();
    }
    
    public Key max() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called max() with empty set");
        }
        return this.set.last();
    }
    
    public Key min() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("called min() with empty set");
        }
        return this.set.first();
    }
    
    public Key ceiling(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called ceiling() with a null key");
        }
        final Key k = this.set.ceiling(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are less than " + key);
        }
        return k;
    }
    
    public Key floor(final Key key) {
        if (key == null) {
            throw new IllegalArgumentException("called floor() with a null key");
        }
        final Key k = this.set.floor(key);
        if (k == null) {
            throw new NoSuchElementException("all keys are greater than " + key);
        }
        return k;
    }
    
    public SET<Key> union(final SET<Key> that) {
        if (that == null) {
            throw new IllegalArgumentException("called union() with a null argument");
        }
        final SET<Key> c = new SET<Key>();
        for (final Key x : this) {
            c.add(x);
        }
        for (final Key x : that) {
            c.add(x);
        }
        return c;
    }
    
    public SET<Key> intersects(final SET<Key> that) {
        if (that == null) {
            throw new IllegalArgumentException("called intersects() with a null argument");
        }
        final SET<Key> c = new SET<Key>();
        if (this.size() < that.size()) {
            for (final Key x : this) {
                if (that.contains(x)) {
                    c.add(x);
                }
            }
        }
        else {
            for (final Key x : that) {
                if (this.contains(x)) {
                    c.add(x);
                }
            }
        }
        return c;
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
        final SET that = (SET)other;
        return this.set.equals(that.set);
    }
    
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException("hashCode() is not supported because sets are mutable");
    }
    
    @Override
    public String toString() {
        final String s = this.set.toString();
        return "{ " + s.substring(1, s.length() - 1) + " }";
    }
    
    public static void main(final String[] args) {
        final SET<String> set = new SET<String>();
        StdOut.println("set = " + set);
        set.add("www.cs.princeton.edu");
        set.add("www.cs.princeton.edu");
        set.add("www.princeton.edu");
        set.add("www.math.princeton.edu");
        set.add("www.yale.edu");
        set.add("www.amazon.com");
        set.add("www.simpsons.com");
        set.add("www.stanford.edu");
        set.add("www.google.com");
        set.add("www.ibm.com");
        set.add("www.apple.com");
        set.add("www.slashdot.com");
        set.add("www.whitehouse.gov");
        set.add("www.espn.com");
        set.add("www.snopes.com");
        set.add("www.movies.com");
        set.add("www.cnn.com");
        set.add("www.iitb.ac.in");
        StdOut.println(set.contains("www.cs.princeton.edu"));
        StdOut.println(!set.contains("www.harvardsucks.com"));
        StdOut.println(set.contains("www.simpsons.com"));
        StdOut.println();
        StdOut.println("ceiling(www.simpsonr.com) = " + set.ceiling("www.simpsonr.com"));
        StdOut.println("ceiling(www.simpsons.com) = " + set.ceiling("www.simpsons.com"));
        StdOut.println("ceiling(www.simpsont.com) = " + set.ceiling("www.simpsont.com"));
        StdOut.println("floor(www.simpsonr.com)   = " + set.floor("www.simpsonr.com"));
        StdOut.println("floor(www.simpsons.com)   = " + set.floor("www.simpsons.com"));
        StdOut.println("floor(www.simpsont.com)   = " + set.floor("www.simpsont.com"));
        StdOut.println();
        StdOut.println("set = " + set);
        StdOut.println();
        for (final String s : set) {
            StdOut.println(s);
        }
        StdOut.println();
        final SET<String> set2 = new SET<String>(set);
        StdOut.println(set.equals(set2));
    }
}
