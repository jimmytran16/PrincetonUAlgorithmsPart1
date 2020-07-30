// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.awt.Color;

public class CollisionSystem
{
    private static final double HZ = 0.5;
    private MinPQ<Event> pq;
    private double t;
    private Particle[] particles;
    
    public CollisionSystem(final Particle[] particles) {
        this.t = 0.0;
        this.particles = particles.clone();
    }
    
    private void predict(final Particle a, final double limit) {
        if (a == null) {
            return;
        }
        for (int i = 0; i < this.particles.length; ++i) {
            final double dt = a.timeToHit(this.particles[i]);
            if (this.t + dt <= limit) {
                this.pq.insert(new Event(this.t + dt, a, this.particles[i]));
            }
        }
        final double dtX = a.timeToHitVerticalWall();
        final double dtY = a.timeToHitHorizontalWall();
        if (this.t + dtX <= limit) {
            this.pq.insert(new Event(this.t + dtX, a, null));
        }
        if (this.t + dtY <= limit) {
            this.pq.insert(new Event(this.t + dtY, null, a));
        }
    }
    
    private void redraw(final double limit) {
        StdDraw.clear();
        for (int i = 0; i < this.particles.length; ++i) {
            this.particles[i].draw();
        }
        StdDraw.show();
        StdDraw.pause(20);
        if (this.t < limit) {
            this.pq.insert(new Event(this.t + 2.0, null, null));
        }
    }
    
    public void simulate(final double limit) {
        this.pq = new MinPQ<Event>();
        for (int i = 0; i < this.particles.length; ++i) {
            this.predict(this.particles[i], limit);
        }
        this.pq.insert(new Event(0.0, null, null));
        while (!this.pq.isEmpty()) {
            final Event e = this.pq.delMin();
            if (!e.isValid()) {
                continue;
            }
            final Particle a = e.a;
            final Particle b = e.b;
            for (int j = 0; j < this.particles.length; ++j) {
                this.particles[j].move(e.time - this.t);
            }
            this.t = e.time;
            if (a != null && b != null) {
                a.bounceOff(b);
            }
            else if (a != null && b == null) {
                a.bounceOffVerticalWall();
            }
            else if (a == null && b != null) {
                b.bounceOffHorizontalWall();
            }
            else if (a == null && b == null) {
                this.redraw(limit);
            }
            this.predict(a, limit);
            this.predict(b, limit);
        }
    }
    
    public static void main(final String[] args) {
        StdDraw.setCanvasSize(600, 600);
        StdDraw.enableDoubleBuffering();
        Particle[] particles;
        if (args.length == 1) {
            final int n = Integer.parseInt(args[0]);
            particles = new Particle[n];
            for (int i = 0; i < n; ++i) {
                particles[i] = new Particle();
            }
        }
        else {
            final int n = StdIn.readInt();
            particles = new Particle[n];
            for (int i = 0; i < n; ++i) {
                final double rx = StdIn.readDouble();
                final double ry = StdIn.readDouble();
                final double vx = StdIn.readDouble();
                final double vy = StdIn.readDouble();
                final double radius = StdIn.readDouble();
                final double mass = StdIn.readDouble();
                final int r = StdIn.readInt();
                final int g = StdIn.readInt();
                final int b = StdIn.readInt();
                final Color color = new Color(r, g, b);
                particles[i] = new Particle(rx, ry, vx, vy, radius, mass, color);
            }
        }
        final CollisionSystem system = new CollisionSystem(particles);
        system.simulate(10000.0);
    }
    
    private static class Event implements Comparable<Event>
    {
        private final double time;
        private final Particle a;
        private final Particle b;
        private final int countA;
        private final int countB;
        
        public Event(final double t, final Particle a, final Particle b) {
            this.time = t;
            this.a = a;
            this.b = b;
            if (a != null) {
                this.countA = a.count();
            }
            else {
                this.countA = -1;
            }
            if (b != null) {
                this.countB = b.count();
            }
            else {
                this.countB = -1;
            }
        }
        
        @Override
        public int compareTo(final Event that) {
            return Double.compare(this.time, that.time);
        }
        
        public boolean isValid() {
            return (this.a == null || this.a.count() == this.countA) && (this.b == null || this.b.count() == this.countB);
        }
    }
}
