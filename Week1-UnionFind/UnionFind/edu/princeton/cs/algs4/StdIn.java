//
// Decompiled by Procyon v0.5.36
//

package edu.princeton.cs.algs4;

import java.io.InputStream;
import java.io.BufferedInputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Locale;

public final class StdIn
{
    private static final String CHARSET_NAME = "UTF-8";
    private static final Locale LOCALE;
    private static final Pattern WHITESPACE_PATTERN;
    private static final Pattern EMPTY_PATTERN;
    private static final Pattern EVERYTHING_PATTERN;
    private static Scanner scanner;

    private StdIn() {
    }

    public static boolean isEmpty() {
        return !StdIn.scanner.hasNext();
    }

    public static boolean hasNextLine() {
        return StdIn.scanner.hasNextLine();
    }

    public static boolean hasNextChar() {
        StdIn.scanner.useDelimiter(StdIn.EMPTY_PATTERN);
        final boolean result = StdIn.scanner.hasNext();
        StdIn.scanner.useDelimiter(StdIn.WHITESPACE_PATTERN);
        return result;
    }

    public static String readLine() {
        String line;
        try {
            line = StdIn.scanner.nextLine();
        }
        catch (NoSuchElementException e) {
            line = null;
        }
        return line;
    }

    public static char readChar() {
        try {
            StdIn.scanner.useDelimiter(StdIn.EMPTY_PATTERN);
            final String ch = StdIn.scanner.next();
            assert ch.length() == 1 : "Internal (Std)In.readChar() error! Please contact the authors.";
            StdIn.scanner.useDelimiter(StdIn.WHITESPACE_PATTERN);
            return ch.charAt(0);
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'char' value from standard input, but no more tokens are available");
        }
    }

    public static String readAll() {
        if (!StdIn.scanner.hasNextLine()) {
            return "";
        }
        final String result = StdIn.scanner.useDelimiter(StdIn.EVERYTHING_PATTERN).next();
        StdIn.scanner.useDelimiter(StdIn.WHITESPACE_PATTERN);
        return result;
    }

    public static String readString() {
        try {
            return StdIn.scanner.next();
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'String' value from standard input, but no more tokens are available");
        }
    }

    public static int readInt() {
        try {
            return StdIn.scanner.nextInt();
        }
        catch (InputMismatchException e) {
            final String token = StdIn.scanner.next();
            throw new InputMismatchException("attempts to read an 'int' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attemps to read an 'int' value from standard input, but no more tokens are available");
        }
    }

    public static double readDouble() {
        try {
            return StdIn.scanner.nextDouble();
        }
        catch (InputMismatchException e) {
            final String token = StdIn.scanner.next();
            throw new InputMismatchException("attempts to read a 'double' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attempts to read a 'double' value from standard input, but no more tokens are available");
        }
    }

    public static float readFloat() {
        try {
            return StdIn.scanner.nextFloat();
        }
        catch (InputMismatchException e) {
            final String token = StdIn.scanner.next();
            throw new InputMismatchException("attempts to read a 'float' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attempts to read a 'float' value from standard input, but there no more tokens are available");
        }
    }

    public static long readLong() {
        try {
            return StdIn.scanner.nextLong();
        }
        catch (InputMismatchException e) {
            final String token = StdIn.scanner.next();
            throw new InputMismatchException("attempts to read a 'long' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attempts to read a 'long' value from standard input, but no more tokens are available");
        }
    }

    public static short readShort() {
        try {
            return StdIn.scanner.nextShort();
        }
        catch (InputMismatchException e) {
            final String token = StdIn.scanner.next();
            throw new InputMismatchException("attempts to read a 'short' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attempts to read a 'short' value from standard input, but no more tokens are available");
        }
    }

    public static byte readByte() {
        try {
            return StdIn.scanner.nextByte();
        }
        catch (InputMismatchException e) {
            final String token = StdIn.scanner.next();
            throw new InputMismatchException("attempts to read a 'byte' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e2) {
            throw new NoSuchElementException("attempts to read a 'byte' value from standard input, but no more tokens are available");
        }
    }

    public static boolean readBoolean() {
        try {
            final String token = readString();
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
            throw new InputMismatchException("attempts to read a 'boolean' value from standard input, but the next token is \"" + token + "\"");
        }
        catch (NoSuchElementException e) {
            throw new NoSuchElementException("attempts to read a 'boolean' value from standard input, but no more tokens are available");
        }
    }

    public static String[] readAllStrings() {
        final String[] tokens = StdIn.WHITESPACE_PATTERN.split(readAll());
        if (tokens.length == 0 || tokens[0].length() > 0) {
            return tokens;
        }
        final String[] decapitokens = new String[tokens.length - 1];
        for (int i = 0; i < tokens.length - 1; ++i) {
            decapitokens[i] = tokens[i + 1];
        }
        return decapitokens;
    }

    public static String[] readAllLines() {
        final ArrayList<String> lines = new ArrayList<String>();
        while (hasNextLine()) {
            lines.add(readLine());
        }
        return lines.toArray(new String[lines.size()]);
    }

    public static int[] readAllInts() {
        final String[] fields = readAllStrings();
        final int[] vals = new int[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            vals[i] = Integer.parseInt(fields[i]);
        }
        return vals;
    }

    public static long[] readAllLongs() {
        final String[] fields = readAllStrings();
        final long[] vals = new long[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            vals[i] = Long.parseLong(fields[i]);
        }
        return vals;
    }

    public static double[] readAllDoubles() {
        final String[] fields = readAllStrings();
        final double[] vals = new double[fields.length];
        for (int i = 0; i < fields.length; ++i) {
            vals[i] = Double.parseDouble(fields[i]);
        }
        return vals;
    }

    private static void resync() {
        setScanner(new Scanner(new BufferedInputStream(System.in), "UTF-8"));
    }

    private static void setScanner(final Scanner scanner) {
        (StdIn.scanner = scanner).useLocale(StdIn.LOCALE);
    }

    @Deprecated
    public static int[] readInts() {
        return readAllInts();
    }

    @Deprecated
    public static double[] readDoubles() {
        return readAllDoubles();
    }

    @Deprecated
    public static String[] readStrings() {
        return readAllStrings();
    }

    public static void main(final String[] args) {
        StdOut.print("Type a string: ");
        final String s = readString();
        StdOut.println("Your string was: " + s);
        StdOut.println();
        StdOut.print("Type an int: ");
        final int a = readInt();
        StdOut.println("Your int was: " + a);
        StdOut.println();
        StdOut.print("Type a boolean: ");
        final boolean b = readBoolean();
        StdOut.println("Your boolean was: " + b);
        StdOut.println();
        StdOut.print("Type a double: ");
        final double c = readDouble();
        StdOut.println("Your double was: " + c);
        StdOut.println();
    }

    static {
        LOCALE = Locale.US;
        WHITESPACE_PATTERN = Pattern.compile("\\p{javaWhitespace}+");
        EMPTY_PATTERN = Pattern.compile("");
        EVERYTHING_PATTERN = Pattern.compile("\\A");
        resync();
    }
}
