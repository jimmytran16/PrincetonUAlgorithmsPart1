// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.net.Socket;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.BufferedOutputStream;

public final class BinaryOut
{
    private BufferedOutputStream out;
    private int buffer;
    private int n;
    
    public BinaryOut() {
        this.out = new BufferedOutputStream(System.out);
    }
    
    public BinaryOut(final OutputStream os) {
        this.out = new BufferedOutputStream(os);
    }
    
    public BinaryOut(final String filename) {
        try {
            final OutputStream os = new FileOutputStream(filename);
            this.out = new BufferedOutputStream(os);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public BinaryOut(final Socket socket) {
        try {
            final OutputStream os = socket.getOutputStream();
            this.out = new BufferedOutputStream(os);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void writeBit(final boolean x) {
        this.buffer <<= 1;
        if (x) {
            this.buffer |= 0x1;
        }
        ++this.n;
        if (this.n == 8) {
            this.clearBuffer();
        }
    }
    
    private void writeByte(final int x) {
        assert x >= 0 && x < 256;
        if (this.n == 0) {
            try {
                this.out.write(x);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        for (int i = 0; i < 8; ++i) {
            final boolean bit = (x >>> 8 - i - 1 & 0x1) == 0x1;
            this.writeBit(bit);
        }
    }
    
    private void clearBuffer() {
        if (this.n == 0) {
            return;
        }
        if (this.n > 0) {
            this.buffer <<= 8 - this.n;
        }
        try {
            this.out.write(this.buffer);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.n = 0;
        this.buffer = 0;
    }
    
    public void flush() {
        this.clearBuffer();
        try {
            this.out.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        this.flush();
        try {
            this.out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void write(final boolean x) {
        this.writeBit(x);
    }
    
    public void write(final byte x) {
        this.writeByte(x & 0xFF);
    }
    
    public void write(final int x) {
        this.writeByte(x >>> 24 & 0xFF);
        this.writeByte(x >>> 16 & 0xFF);
        this.writeByte(x >>> 8 & 0xFF);
        this.writeByte(x >>> 0 & 0xFF);
    }
    
    public void write(final int x, final int r) {
        if (r == 32) {
            this.write(x);
            return;
        }
        if (r < 1 || r > 32) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x >= 1 << r) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; ++i) {
            final boolean bit = (x >>> r - i - 1 & 0x1) == 0x1;
            this.writeBit(bit);
        }
    }
    
    public void write(final double x) {
        this.write(Double.doubleToRawLongBits(x));
    }
    
    public void write(final long x) {
        this.writeByte((int)(x >>> 56 & 0xFFL));
        this.writeByte((int)(x >>> 48 & 0xFFL));
        this.writeByte((int)(x >>> 40 & 0xFFL));
        this.writeByte((int)(x >>> 32 & 0xFFL));
        this.writeByte((int)(x >>> 24 & 0xFFL));
        this.writeByte((int)(x >>> 16 & 0xFFL));
        this.writeByte((int)(x >>> 8 & 0xFFL));
        this.writeByte((int)(x >>> 0 & 0xFFL));
    }
    
    public void write(final float x) {
        this.write(Float.floatToRawIntBits(x));
    }
    
    public void write(final short x) {
        this.writeByte(x >>> 8 & 0xFF);
        this.writeByte(x >>> 0 & 0xFF);
    }
    
    public void write(final char x) {
        if (x < '\0' || x >= '\u0100') {
            throw new IllegalArgumentException("Illegal 8-bit char = " + x);
        }
        this.writeByte(x);
    }
    
    public void write(final char x, final int r) {
        if (r == 8) {
            this.write(x);
            return;
        }
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value for r = " + r);
        }
        if (x >= 1 << r) {
            throw new IllegalArgumentException("Illegal " + r + "-bit char = " + x);
        }
        for (int i = 0; i < r; ++i) {
            final boolean bit = (x >>> r - i - 1 & 0x1) == 0x1;
            this.writeBit(bit);
        }
    }
    
    public void write(final String s) {
        for (int i = 0; i < s.length(); ++i) {
            this.write(s.charAt(i));
        }
    }
    
    public void write(final String s, final int r) {
        for (int i = 0; i < s.length(); ++i) {
            this.write(s.charAt(i), r);
        }
    }
    
    public static void main(final String[] args) {
        final String filename = args[0];
        final BinaryOut out = new BinaryOut(filename);
        final BinaryIn in = new BinaryIn();
        while (!in.isEmpty()) {
            final char c = in.readChar();
            out.write(c);
        }
        out.flush();
    }
}
