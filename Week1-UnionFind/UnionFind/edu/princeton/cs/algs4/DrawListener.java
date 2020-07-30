// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public interface DrawListener
{
    void mousePressed(final double p0, final double p1);
    
    void mouseDragged(final double p0, final double p1);
    
    void mouseReleased(final double p0, final double p1);
    
    void mouseClicked(final double p0, final double p1);
    
    void keyTyped(final char p0);
    
    void keyPressed(final int p0);
    
    void keyReleased(final int p0);
}
