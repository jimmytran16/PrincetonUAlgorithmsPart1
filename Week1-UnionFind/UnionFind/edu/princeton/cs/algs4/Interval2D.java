// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Interval2D
{
    private final Interval1D x;
    private final Interval1D y;
    
    public Interval2D(final Interval1D x, final Interval1D y) {
        this.x = x;
        this.y = y;
    }
    
    public boolean intersects(final Interval2D that) {
        return this.x.intersects(that.x) && this.y.intersects(that.y);
    }
    
    public boolean contains(final Point2D p) {
        return this.x.contains(p.x()) && this.y.contains(p.y());
    }
    
    public double area() {
        return this.x.length() * this.y.length();
    }
    
    @Override
    public String toString() {
        return this.x + " x " + this.y;
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
        final Interval2D that = (Interval2D)other;
        return this.x.equals(that.x) && this.y.equals(that.y);
    }
    
    @Override
    public int hashCode() {
        final int hash1 = this.x.hashCode();
        final int hash2 = this.y.hashCode();
        return 31 * hash1 + hash2;
    }
    
    public void draw() {
        final double xc = (this.x.min() + this.x.max()) / 2.0;
        final double yc = (this.y.min() + this.y.max()) / 2.0;
        StdDraw.rectangle(xc, yc, this.x.length() / 2.0, this.y.length() / 2.0);
    }
    
    public static void main(final String[] args) {
        final double xmin = Double.parseDouble(args[0]);
        final double xmax = Double.parseDouble(args[1]);
        final double ymin = Double.parseDouble(args[2]);
        final double ymax = Double.parseDouble(args[3]);
        final int trials = Integer.parseInt(args[4]);
        final Interval1D xInterval = new Interval1D(xmin, xmax);
        final Interval1D yInterval = new Interval1D(ymin, ymax);
        final Interval2D box = new Interval2D(xInterval, yInterval);
        box.draw();
        final Counter counter = new Counter("hits");
        for (int t = 0; t < trials; ++t) {
            final double x = StdRandom.uniform(0.0, 1.0);
            final double y = StdRandom.uniform(0.0, 1.0);
            final Point2D point = new Point2D(x, y);
            if (box.contains(point)) {
                counter.increment();
            }
            else {
                point.draw();
            }
        }
        StdOut.println(counter);
        StdOut.printf("box area = %.2f\n", box.area());
    }
}
