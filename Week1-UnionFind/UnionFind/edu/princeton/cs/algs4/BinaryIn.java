// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.NoSuchElementException;
import java.io.FileInputStream;
import java.io.File;
import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedInputStream;

public final class BinaryIn
{
    private static final int EOF = -1;
    private BufferedInputStream in;
    private int buffer;
    private int n;
    
    public BinaryIn() {
        this.in = new BufferedInputStream(System.in);
        this.fillBuffer();
    }
    
    public BinaryIn(final InputStream is) {
        this.in = new BufferedInputStream(is);
        this.fillBuffer();
    }
    
    public BinaryIn(final Socket socket) {
        try {
            final InputStream is = socket.getInputStream();
            this.in = new BufferedInputStream(is);
            this.fillBuffer();
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + socket);
        }
    }
    
    public BinaryIn(final URL url) {
        try {
            final URLConnection site = url.openConnection();
            final InputStream is = site.getInputStream();
            this.in = new BufferedInputStream(is);
            this.fillBuffer();
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + url);
        }
    }
    
    public BinaryIn(final String name) {
        try {
            final File file = new File(name);
            if (file.exists()) {
                final FileInputStream fis = new FileInputStream(file);
                this.in = new BufferedInputStream(fis);
                this.fillBuffer();
                return;
            }
            URL url = this.getClass().getResource(name);
            if (url == null) {
                url = new URL(name);
            }
            final URLConnection site = url.openConnection();
            final InputStream is = site.getInputStream();
            this.in = new BufferedInputStream(is);
            this.fillBuffer();
        }
        catch (IOException ioe) {
            System.err.println("Could not open " + name);
        }
    }
    
    private void fillBuffer() {
        try {
            this.buffer = this.in.read();
            this.n = 8;
        }
        catch (IOException e) {
            System.err.println("EOF");
            this.buffer = -1;
            this.n = -1;
        }
    }
    
    public boolean exists() {
        return this.in != null;
    }
    
    public boolean isEmpty() {
        return this.buffer == -1;
    }
    
    public boolean readBoolean() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        --this.n;
        final boolean bit = (this.buffer >> this.n & 0x1) == 0x1;
        if (this.n == 0) {
            this.fillBuffer();
        }
        return bit;
    }
    
    public char readChar() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        if (this.n == 8) {
            final int x = this.buffer;
            this.fillBuffer();
            return (char)(x & 0xFF);
        }
        int x = this.buffer;
        x <<= 8 - this.n;
        final int oldN = this.n;
        this.fillBuffer();
        if (this.isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        this.n = oldN;
        x |= this.buffer >>> this.n;
        return (char)(x & 0xFF);
    }
    
    public char readChar(final int r) {
        if (r < 1 || r > 16) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }
        if (r == 8) {
            return this.readChar();
        }
        char x = '\0';
        for (int i = 0; i < r; ++i) {
            x <<= 1;
            final boolean bit = this.readBoolean();
            if (bit) {
                x |= '\u0001';
            }
        }
        return x;
    }
    
    public String readString() {
        if (this.isEmpty()) {
            throw new NoSuchElementException("Reading from empty input stream");
        }
        final StringBuilder sb = new StringBuilder();
        while (!this.isEmpty()) {
            final char c = this.readChar();
            sb.append(c);
        }
        return sb.toString();
    }
    
    public short readShort() {
        short x = 0;
        for (int i = 0; i < 2; ++i) {
            final char c = this.readChar();
            x <<= 8;
            x |= (short)c;
        }
        return x;
    }
    
    public int readInt() {
        int x = 0;
        for (int i = 0; i < 4; ++i) {
            final char c = this.readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }
    
    public int readInt(final int r) {
        if (r < 1 || r > 32) {
            throw new IllegalArgumentException("Illegal value of r = " + r);
        }
        if (r == 32) {
            return this.readInt();
        }
        int x = 0;
        for (int i = 0; i < r; ++i) {
            x <<= 1;
            final boolean bit = this.readBoolean();
            if (bit) {
                x |= 0x1;
            }
        }
        return x;
    }
    
    public long readLong() {
        long x = 0L;
        for (int i = 0; i < 8; ++i) {
            final char c = this.readChar();
            x <<= 8;
            x |= c;
        }
        return x;
    }
    
    public double readDouble() {
        return Double.longBitsToDouble(this.readLong());
    }
    
    public float readFloat() {
        return Float.intBitsToFloat(this.readInt());
    }
    
    public byte readByte() {
        final char c = this.readChar();
        return (byte)(c & '\u00ff');
    }
    
    public static void main(final String[] args) {
        final BinaryIn in = new BinaryIn(args[0]);
        final BinaryOut out = new BinaryOut(args[1]);
        while (!in.isEmpty()) {
            final char c = in.readChar();
            out.write(c);
        }
        out.flush();
    }
}
