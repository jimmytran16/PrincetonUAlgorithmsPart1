// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class Date implements Comparable<Date>
{
    private static final int[] DAYS;
    private final int month;
    private final int day;
    private final int year;
    
    public Date(final int month, final int day, final int year) {
        if (!isValid(month, day, year)) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.month = month;
        this.day = day;
        this.year = year;
    }
    
    public Date(final String date) {
        final String[] fields = date.split("/");
        if (fields.length != 3) {
            throw new IllegalArgumentException("Invalid date");
        }
        this.month = Integer.parseInt(fields[0]);
        this.day = Integer.parseInt(fields[1]);
        this.year = Integer.parseInt(fields[2]);
        if (!isValid(this.month, this.day, this.year)) {
            throw new IllegalArgumentException("Invalid date");
        }
    }
    
    public int month() {
        return this.month;
    }
    
    public int day() {
        return this.day;
    }
    
    public int year() {
        return this.year;
    }
    
    private static boolean isValid(final int m, final int d, final int y) {
        return m >= 1 && m <= 12 && d >= 1 && d <= Date.DAYS[m] && (m != 2 || d != 29 || isLeapYear(y));
    }
    
    private static boolean isLeapYear(final int y) {
        return y % 400 == 0 || (y % 100 != 0 && y % 4 == 0);
    }
    
    public Date next() {
        if (isValid(this.month, this.day + 1, this.year)) {
            return new Date(this.month, this.day + 1, this.year);
        }
        if (isValid(this.month + 1, 1, this.year)) {
            return new Date(this.month + 1, 1, this.year);
        }
        return new Date(1, 1, this.year + 1);
    }
    
    public boolean isAfter(final Date that) {
        return this.compareTo(that) > 0;
    }
    
    public boolean isBefore(final Date that) {
        return this.compareTo(that) < 0;
    }
    
    @Override
    public int compareTo(final Date that) {
        if (this.year < that.year) {
            return -1;
        }
        if (this.year > that.year) {
            return 1;
        }
        if (this.month < that.month) {
            return -1;
        }
        if (this.month > that.month) {
            return 1;
        }
        if (this.day < that.day) {
            return -1;
        }
        if (this.day > that.day) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public String toString() {
        return this.month + "/" + this.day + "/" + this.year;
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
        final Date that = (Date)other;
        return this.month == that.month && this.day == that.day && this.year == that.year;
    }
    
    @Override
    public int hashCode() {
        return this.day + 31 * this.month + 372 * this.year;
    }
    
    public static void main(final String[] args) {
        Date today = new Date(2, 25, 2004);
        StdOut.println(today);
        for (int i = 0; i < 10; ++i) {
            today = today.next();
            StdOut.println(today);
        }
        StdOut.println(today.isAfter(today.next()));
        StdOut.println(today.isAfter(today));
        StdOut.println(today.next().isAfter(today));
        Date birthday = new Date(10, 16, 1971);
        StdOut.println(birthday);
        for (int j = 0; j < 10; ++j) {
            birthday = birthday.next();
            StdOut.println(birthday);
        }
    }
    
    static {
        DAYS = new int[] { 0, 31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
    }
}
