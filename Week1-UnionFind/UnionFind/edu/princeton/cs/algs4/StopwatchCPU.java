// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class StopwatchCPU
{
    private static final double NANOSECONDS_PER_SECOND = 1.0E9;
    private final ThreadMXBean threadTimer;
    private final long start;
    
    public StopwatchCPU() {
        this.threadTimer = ManagementFactory.getThreadMXBean();
        this.start = this.threadTimer.getCurrentThreadCpuTime();
    }
    
    public double elapsedTime() {
        final long now = this.threadTimer.getCurrentThreadCpuTime();
        return (now - this.start) / 1.0E9;
    }
    
    public static void main(final String[] args) {
        final int n = Integer.parseInt(args[0]);
        final StopwatchCPU timer1 = new StopwatchCPU();
        double sum1 = 0.0;
        for (int i = 1; i <= n; ++i) {
            sum1 += Math.sqrt(i);
        }
        final double time1 = timer1.elapsedTime();
        StdOut.printf("%e (%.2f seconds)\n", sum1, time1);
        final StopwatchCPU timer2 = new StopwatchCPU();
        double sum2 = 0.0;
        for (int j = 1; j <= n; ++j) {
            sum2 += Math.pow(j, 0.5);
        }
        final double time2 = timer2.elapsedTime();
        StdOut.printf("%e (%.2f seconds)\n", sum2, time2);
    }
}
