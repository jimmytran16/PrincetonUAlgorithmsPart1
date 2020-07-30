// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Complex
{
    private final double re;
    private final double im;
    
    public Complex(final double real, final double imag) {
        this.re = real;
        this.im = imag;
    }
    
    @Override
    public String toString() {
        if (this.im == 0.0) {
            return this.re + "";
        }
        if (this.re == 0.0) {
            return this.im + "i";
        }
        if (this.im < 0.0) {
            return this.re + " - " + -this.im + "i";
        }
        return this.re + " + " + this.im + "i";
    }
    
    public double abs() {
        return Math.hypot(this.re, this.im);
    }
    
    public double phase() {
        return Math.atan2(this.im, this.re);
    }
    
    public Complex plus(final Complex that) {
        final double real = this.re + that.re;
        final double imag = this.im + that.im;
        return new Complex(real, imag);
    }
    
    public Complex minus(final Complex that) {
        final double real = this.re - that.re;
        final double imag = this.im - that.im;
        return new Complex(real, imag);
    }
    
    public Complex times(final Complex that) {
        final double real = this.re * that.re - this.im * that.im;
        final double imag = this.re * that.im + this.im * that.re;
        return new Complex(real, imag);
    }
    
    public Complex scale(final double alpha) {
        return new Complex(alpha * this.re, alpha * this.im);
    }
    
    @Deprecated
    public Complex times(final double alpha) {
        return new Complex(alpha * this.re, alpha * this.im);
    }
    
    public Complex conjugate() {
        return new Complex(this.re, -this.im);
    }
    
    public Complex reciprocal() {
        final double scale = this.re * this.re + this.im * this.im;
        return new Complex(this.re / scale, -this.im / scale);
    }
    
    public double re() {
        return this.re;
    }
    
    public double im() {
        return this.im;
    }
    
    public Complex divides(final Complex that) {
        return this.times(that.reciprocal());
    }
    
    public Complex exp() {
        return new Complex(Math.exp(this.re) * Math.cos(this.im), Math.exp(this.re) * Math.sin(this.im));
    }
    
    public Complex sin() {
        return new Complex(Math.sin(this.re) * Math.cosh(this.im), Math.cos(this.re) * Math.sinh(this.im));
    }
    
    public Complex cos() {
        return new Complex(Math.cos(this.re) * Math.cosh(this.im), -Math.sin(this.re) * Math.sinh(this.im));
    }
    
    public Complex tan() {
        return this.sin().divides(this.cos());
    }
    
    public static void main(final String[] args) {
        final Complex a = new Complex(5.0, 6.0);
        final Complex b = new Complex(-3.0, 4.0);
        StdOut.println("a            = " + a);
        StdOut.println("b            = " + b);
        StdOut.println("Re(a)        = " + a.re());
        StdOut.println("Im(a)        = " + a.im());
        StdOut.println("b + a        = " + b.plus(a));
        StdOut.println("a - b        = " + a.minus(b));
        StdOut.println("a * b        = " + a.times(b));
        StdOut.println("b * a        = " + b.times(a));
        StdOut.println("a / b        = " + a.divides(b));
        StdOut.println("(a / b) * b  = " + a.divides(b).times(b));
        StdOut.println("conj(a)      = " + a.conjugate());
        StdOut.println("|a|          = " + a.abs());
        StdOut.println("tan(a)       = " + a.tan());
    }
}
