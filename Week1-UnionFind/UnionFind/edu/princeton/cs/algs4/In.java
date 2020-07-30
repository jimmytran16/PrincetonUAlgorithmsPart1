// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.io.FileInputStream;
import java.io.File;
import java.net.URLConnection;
import java.net.URL;
import java.io.IOException;
import java.net.Socket;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Locale;

public final class In
{
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern EMPTY_PATTERN;
    private static final Pattern EVERYTHING_PATTERN;
    private Scanner scanner;
    
    public In() {
        (this.scanner = new Scanner(new BufferedInputStream(System.in), "UTF-8")).useLocale(In.LOCALE);
    }
    
    public In(final Socket socket) {
        if (socket == null) {
            throw new IllegalArgumentException("socket argument is null");
        }
        try {
            final InputStream is = socket.getInputStream();
            (this.scanner = new Scanner(new BufferedInputStream(is), "UTF-8")).useLocale(In.LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + socket, ioe);
        }
    }
    
    public In(final URL url) {
        if (url == null) {
            throw new IllegalArgumentException("url argument is null");
        }
        try {
            final URLConnection site = url.openConnection();
            final InputStream is = site.getInputStream();
            (this.scanner = new Scanner(new BufferedInputStream(is), "UTF-8")).useLocale(In.LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + url, ioe);
        }
    }
    
    public In(final File file) {
        if (file == null) {
            throw new IllegalArgumentException("file argument is null");
        }
        try {
            final FileInputStream fis = new FileInputStream(file);
            (this.scanner = new Scanner(new BufferedInputStream(fis), "UTF-8")).useLocale(In.LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + file, ioe);
        }
    }
    
    public In(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("argument is null");
        }
        try {
            final File file = new File(name);
            if (file.exists()) {
                final FileInputStream fis = new FileInputStream(file);
                (this.scanner = new Scanner(new BufferedInputStream(fis), "UTF-8")).useLocale(In.LOCALE);
                return;
            }
            URL url = this.getClass().getResource(name);
            if (url == null) {
                url = this.getClass().getClassLoader().getResource(name);
            }
            if (url == null) {
                url = new URL(name);
            }
            final URLConnection site = url.openConnection();
            final InputStream is = site.getInputStream();
            (this.scanner = new Scanner(new BufferedInputStream(is), "UTF-8")).useLocale(In.LOCALE);
        }
        catch (IOException ioe) {
            throw new IllegalArgumentException("Could not open " + name, ioe);
        }
    }
    
    public In(final Scanner scanner) {
        if (scanner == null) {
            throw new IllegalArgumentException("scanner argument is null");
        }
        this.scanner = scanner;
    }
    
    public boolean exists() {
        return this.scanner != null;
    }
    
    public boolean isEmpty() {
        return !this.scanner.hasNext();
    }
    
    public boolean hasNextLine() {
        return this.scanner.hasNextLine();
    }
    
    public boolean hasNextChar() {
        this.scanner.useDelimiter(In.EMPTY_PATTERN);
        final boolean result = this.scanner.hasNext();
        this.scanner.useDelimiter(In.WHITESPACE_PATTERN);
        return result;
    }
    
    public String readLine() {
        String line;
        try {
            line = this.scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }
    
    public char readChar() {
        this.scanner.useDelimiter(In.EMPTY_PATTERN);
        try {
            final String ch = this.scanner.next();
            assert ch.length() == 1 : "Internal (Std)In.readChar() error! Please contact the authors.";
            this.scanner.useDelimiter(In.WHITESPACE_PATTERN);
            return ch.charAt(0);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'char' value from the input stream, but no more tokens are available");
        }
    }
    
    public String readAll() {
        if (!this.scanner.hasNextLine()) {
            return "";
        }
        final String result = this.scanner.useDelimiter(In.EVERYTHING_PATTERN).next();
        this.scanner.useDelimiter(In.WHITESPACE_PATTERN);
        return result;
    }
    
    public String readString() {
        try {
            return this.scanner.next();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'String' value from the input stream, but no more tokens are available");
        }
    }
    
    public int readInt() {
        try {
            return this.scanner.nextInt();
        }
        catch (InputMismatchException e) {
            final String token = this.scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read an 'int' value from the input stream, but no more tokens are available");
        }
    }
    
    public double readDouble() {
        try {
            return this.scanner.nextDouble();
        }
        catch (InputMismatchException e) {
            final String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read a 'double' value from the input stream, but no more tokens are available");
        }
    }
    
    public float readFloat() {
        try {
            return this.scanner.nextFloat();
        }
        catch (InputMismatchException e) {
            final String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read a 'float' value from the input stream, but no more tokens are available");
        }
    }
    
    public long readLong() {
        try {
            return this.scanner.nextLong();
        }
        catch (InputMismatchException e) {
            final String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read a 'long' value from the input stream, but no more tokens are available");
        }
    }
    
    public short readShort() {
        try {
            return this.scanner.nextShort();
        }
        catch (InputMismatchException e) {
            final String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read a 'short' value from the input stream, but no more tokens are available");
        }
    }
    
    public byte readByte() {
        try {
            return this.scanner.nextByte();
        }
        catch (InputMismatchException e) {
            final String token = this.scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read a 'byte' value from the input stream, but no more tokens are available");
        }
    }
    
    public boolean readBoolean() {
        try {
            final String token = this.readString();
            if ("true".equalsIgnoreCase(token)) {
                return true;
            }
            if ("false".equalsIgnoreCase(token)) {
                return false;
            }
            if ("1".equals(token)) {
                return true;
            }
            if ("0".equals(token)) {
                return false;
            }
            throw new InputMismatchException("attempts to read a 'boolean' value from the input stream, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from the input stream, but no more tokens are available");
        }
    }
    
    public String[] readAllStrings() {
        final String[] tokens = In.WHITESPACE_PATTERN.split(this.readAll());
        if (tokens.length == 0 || tokens[0].length() > 0) {
            return tokens;
        }
        final String[] decapitokens = new String[tokens.length - 1];
        for (int i = 0; i < tokens.length - 1; ++i) {
            decapitokens[i] = tokens[i + 1];
        }
        return decapitokens;
    }
    
    public String[] readAllLines() {
        final ArrayList<String> lines = new ArrayList<String>();
        while (this.hasNextLine()) {
            lines.add(this.readLine());
        }
        return lines.toArray(new String[lines.size()]);
    }
    
    public int[] readAllInts() {
        final String[] fields = this.readAllStrings();
        final int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            vals[i] = Integer.parseInt(fields[i]);
        }
        return vals;
    }
    
    public long[] readAllLongs() {
        final String[] fields = this.readAllStrings();
        final long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            vals[i] = Long.parseLong(fields[i]);
        }
        return vals;
    }
    
    public double[] readAllDoubles() {
        final String[] fields = this.readAllStrings();
        final double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            vals[i] = Double.parseDouble(fields[i]);
        }
        return vals;
    }
    
    public void close() {
        this.scanner.close();
    }
    
    @Deprecated
    public static int[] readInts(final String filename) {
        return new In(filename).readAllInts();
    }
    
    @Deprecated
    public static double[] readDoubles(final String filename) {
        return new In(filename).readAllDoubles();
    }
    
    @Deprecated
    public static String[] readStrings(final String filename) {
        return new In(filename).readAllStrings();
    }
    
    @Deprecated
    public static int[] readInts() {
        return new In().readAllInts();
    }
    
    @Deprecated
    public static double[] readDoubles() {
        return new In().readAllDoubles();
    }
    
    @Deprecated
    public static String[] readStrings() {
        return new In().readAllStrings();
    }
    
    public static void main(final String[] args) {
        final String urlName = "https://introcs.cs.princeton.edu/java/stdlib/InTest.txt";
        System.out.println("readAll() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In(urlName);
            System.out.println(in.readAll());
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readLine() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In(urlName);
            while (!in.isEmpty()) {
                final String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readString() from URL " + urlName);
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In(urlName);
            while (!in.isEmpty()) {
                final String s = in.readString();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readLine() from current directory");
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In("./InTest.txt");
            while (!in.isEmpty()) {
                final String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readLine() from relative path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In("../stdlib/InTest.txt");
            while (!in.isEmpty()) {
                final String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readChar() from file");
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In("InTest.txt");
            while (!in.isEmpty()) {
                final char c = in.readChar();
                System.out.print(c);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println();
        System.out.println("readLine() from absolute OS X / Linux path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In("/n/fs/introcs/www/java/stdlib/InTest.txt");
            while (!in.isEmpty()) {
                final String s = in.readLine();
                System.out.println(s);
            }
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
        System.out.println("readLine() from absolute Windows path");
        System.out.println("---------------------------------------------------------------------------");
        try {
            final In in = new In("G:\\www\\introcs\\stdlib\\InTest.txt");
            while (!in.isEmpty()) {
                final String s = in.readLine();
                System.out.println(s);
            }
            System.out.println();
        }
        catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        System.out.println();
    }
    
    static {
        LOCALE = Locale.US;
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        EMPTY_PATTERN = Pattern.compile("");
        EVERYTHING_PATTERN = Pattern.compile("\\A");
    }
}
