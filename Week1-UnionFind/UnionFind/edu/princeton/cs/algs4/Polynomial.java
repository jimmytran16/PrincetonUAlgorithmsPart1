// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Polynomial
{
    private int[] coef;
    private int degree;
    
    public Polynomial(final int a, final int b) {
        if (b < 0) {
            throw new IllegalArgumentException("exponent cannot be negative: " + b);
        }
        (this.coef = new int[b + 1])[b] = a;
        this.reduce();
    }
    
    private void reduce() {
        this.degree = -1;
        for (int i = this.coef.length - 1; i >= 0; --i) {
            if (this.coef[i] != 0) {
                this.degree = i;
                return;
            }
        }
    }
    
    public int degree() {
        return this.degree;
    }
    
    public Polynomial plus(final Polynomial that) {
        final Polynomial poly = new Polynomial(0, Math.max(this.degree, that.degree));
        for (int i = 0; i <= this.degree; ++i) {
            final int[] coef = poly.coef;
            final int n = i;
            coef[n] += this.coef[i];
        }
        for (int i = 0; i <= that.degree; ++i) {
            final int[] coef2 = poly.coef;
            final int n2 = i;
            coef2[n2] += that.coef[i];
        }
        poly.reduce();
        return poly;
    }
    
    public Polynomial minus(final Polynomial that) {
        final Polynomial poly = new Polynomial(0, Math.max(this.degree, that.degree));
        for (int i = 0; i <= this.degree; ++i) {
            final int[] coef = poly.coef;
            final int n = i;
            coef[n] += this.coef[i];
        }
        for (int i = 0; i <= that.degree; ++i) {
            final int[] coef2 = poly.coef;
            final int n2 = i;
            coef2[n2] -= that.coef[i];
        }
        poly.reduce();
        return poly;
    }
    
    public Polynomial times(final Polynomial that) {
        final Polynomial poly = new Polynomial(0, this.degree + that.degree);
        for (int i = 0; i <= this.degree; ++i) {
            for (int j = 0; j <= that.degree; ++j) {
                final int[] coef = poly.coef;
                final int n = i + j;
                coef[n] += this.coef[i] * that.coef[j];
            }
        }
        poly.reduce();
        return poly;
    }
    
    public Polynomial compose(final Polynomial that) {
        Polynomial poly = new Polynomial(0, 0);
        for (int i = this.degree; i >= 0; --i) {
            final Polynomial term = new Polynomial(this.coef[i], 0);
            poly = term.plus(that.times(poly));
        }
        return poly;
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
        final Polynomial that = (Polynomial)other;
        if (this.degree != that.degree) {
            return false;
        }
        for (int i = this.degree; i >= 0; --i) {
            if (this.coef[i] != that.coef[i]) {
                return false;
            }
        }
        return true;
    }
    
    public Polynomial differentiate() {
        if (this.degree == 0) {
            return new Polynomial(0, 0);
        }
        final Polynomial poly = new Polynomial(0, this.degree - 1);
        poly.degree = this.degree - 1;
        for (int i = 0; i < this.degree; ++i) {
            poly.coef[i] = (i + 1) * this.coef[i + 1];
        }
        return poly;
    }
    
    public int evaluate(final int x) {
        int p = 0;
        for (int i = this.degree; i >= 0; --i) {
            p = this.coef[i] + x * p;
        }
        return p;
    }
    
    public int compareTo(final Polynomial that) {
        if (this.degree < that.degree) {
            return -1;
        }
        if (this.degree > that.degree) {
            return 1;
        }
        for (int i = this.degree; i >= 0; --i) {
            if (this.coef[i] < that.coef[i]) {
                return -1;
            }
            if (this.coef[i] > that.coef[i]) {
                return 1;
            }
        }
        return 0;
    }
    
    @Override
    public String toString() {
        if (this.degree == -1) {
            return "0";
        }
        if (this.degree == 0) {
            return "" + this.coef[0];
        }
        if (this.degree == 1) {
            return this.coef[1] + "x + " + this.coef[0];
        }
        String s = this.coef[this.degree] + "x^" + this.degree;
        for (int i = this.degree - 1; i >= 0; --i) {
            if (this.coef[i] != 0) {
                if (this.coef[i] > 0) {
                    s = s + " + " + this.coef[i];
                }
                else if (this.coef[i] < 0) {
                    s = s + " - " + -this.coef[i];
                }
                if (i == 1) {
                    s += "x";
                }
                else if (i > 1) {
                    s = s + "x^" + i;
                }
            }
        }
        return s;
    }
    
    public static void main(final String[] args) {
        final Polynomial zero = new Polynomial(0, 0);
        final Polynomial p1 = new Polynomial(4, 3);
        final Polynomial p2 = new Polynomial(3, 2);
        final Polynomial p3 = new Polynomial(1, 0);
        final Polynomial p4 = new Polynomial(2, 1);
        final Polynomial p5 = p1.plus(p2).plus(p3).plus(p4);
        final Polynomial q1 = new Polynomial(3, 2);
        final Polynomial q2 = new Polynomial(5, 0);
        final Polynomial q3 = q1.plus(q2);
        final Polynomial r = p5.plus(q3);
        final Polynomial s = p5.times(q3);
        final Polynomial t = p5.compose(q3);
        final Polynomial u = p5.minus(p5);
        StdOut.println("zero(x)     = " + zero);
        StdOut.println("p(x)        = " + p5);
        StdOut.println("q(x)        = " + q3);
        StdOut.println("p(x) + q(x) = " + r);
        StdOut.println("p(x) * q(x) = " + s);
        StdOut.println("p(q(x))     = " + t);
        StdOut.println("p(x) - p(x) = " + u);
        StdOut.println("0 - p(x)    = " + zero.minus(p5));
        StdOut.println("p(3)        = " + p5.evaluate(3));
        StdOut.println("p'(x)       = " + p5.differentiate());
        StdOut.println("p''(x)      = " + p5.differentiate().differentiate());
    }
}
