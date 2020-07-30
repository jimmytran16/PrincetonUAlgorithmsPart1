// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.io.FileOutputStream;
import java.net.Socket;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

public class Out
{
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private PrintWriter out;
    
    public Out(final OutputStream os) {
        try {
            final OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            this.out = new PrintWriter(osw, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Out() {
        this(System.out);
    }
    
    public Out(final Socket socket) {
        try {
            final OutputStream os = socket.getOutputStream();
            final OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            this.out = new PrintWriter(osw, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public Out(final String filename) {
        try {
            final OutputStream os = new FileOutputStream(filename);
            final OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
            this.out = new PrintWriter(osw, true);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void close() {
        this.out.close();
    }
    
    public void println() {
        this.out.println();
    }
    
    public void println(final Object x) {
        this.out.println(x);
    }
    
    public void println(final boolean x) {
        this.out.println(x);
    }
    
    public void println(final char x) {
        this.out.println(x);
    }
    
    public void println(final double x) {
        this.out.println(x);
    }
    
    public void println(final float x) {
        this.out.println(x);
    }
    
    public void println(final int x) {
        this.out.println(x);
    }
    
    public void println(final long x) {
        this.out.println(x);
    }
    
    public void println(final byte x) {
        this.out.println(x);
    }
    
    public void print() {
        this.out.flush();
    }
    
    public void print(final Object x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final boolean x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final char x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final double x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final float x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final int x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final long x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void print(final byte x) {
        this.out.print(x);
        this.out.flush();
    }
    
    public void printf(final String format, final Object... args) {
        this.out.printf(Out.LOCALE, format, args);
        this.out.flush();
    }
    
    public void printf(final Locale locale, final String format, final Object... args) {
        this.out.printf(locale, format, args);
        this.out.flush();
    }
    
    public static void main(final String[] args) {
        Out out = new Out();
        out.println("Test 1");
        out.close();
        out = new Out("test.txt");
        out.println("Test 2");
        out.close();
    }
    
    static {
        LOCALE = Locale.US;
    }
}
