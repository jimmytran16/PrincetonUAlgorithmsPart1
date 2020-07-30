// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class SuffixArrayX
{
    private static final int CUTOFF = 5;
    private final char[] text;
    private final int[] index;
    private final int n;
    
    public SuffixArrayX(String text) {
        this.n = text.length();
        text += '\0';
        this.text = text.toCharArray();
        this.index = new int[this.n];
        for (int i = 0; i < this.n; ++i) {
            this.index[i] = i;
        }
        this.sort(0, this.n - 1, 0);
    }
    
    private void sort(final int lo, final int hi, final int d) {
        if (hi <= lo + 5) {
            this.insertion(lo, hi, d);
            return;
        }
        int lt = lo;
        int gt = hi;
        final char v = this.text[this.index[lo] + d];
        int i = lo + 1;
        while (i <= gt) {
            final char t = this.text[this.index[i] + d];
            if (t < v) {
                this.exch(lt++, i++);
            }
            else if (t > v) {
                this.exch(i, gt--);
            }
            else {
                ++i;
            }
        }
        this.sort(lo, lt - 1, d);
        if (v > '\0') {
            this.sort(lt, gt, d + 1);
        }
        this.sort(gt + 1, hi, d);
    }
    
    private void insertion(final int lo, final int hi, final int d) {
        for (int i = lo; i <= hi; ++i) {
            for (int j = i; j > lo && this.less(this.index[j], this.index[j - 1], d); --j) {
                this.exch(j, j - 1);
            }
        }
    }
    
    private boolean less(int i, int j, final int d) {
        if (i == j) {
            return false;
        }
        for (i += d, j += d; i < this.n && j < this.n; ++i, ++j) {
            if (this.text[i] < this.text[j]) {
                return true;
            }
            if (this.text[i] > this.text[j]) {
                return false;
            }
        }
        return i > j;
    }
    
    private void exch(final int i, final int j) {
        final int swap = this.index[i];
        this.index[i] = this.index[j];
        this.index[j] = swap;
    }
    
    public int length() {
        return this.n;
    }
    
    public int index(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        return this.index[i];
    }
    
    public int lcp(final int i) {
        if (i < 1 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        return this.lcp(this.index[i], this.index[i - 1]);
    }
    
    private int lcp(int i, int j) {
        int length;
        for (length = 0; i < this.n && j < this.n; ++i, ++j, ++length) {
            if (this.text[i] != this.text[j]) {
                return length;
            }
        }
        return length;
    }
    
    public String select(final int i) {
        if (i < 0 || i >= this.n) {
            throw new IllegalArgumentException();
        }
        return new String(this.text, this.index[i], this.n - this.index[i]);
    }
    
    public int rank(final String query) {
        int lo = 0;
        int hi = this.n - 1;
        while (lo <= hi) {
            final int mid = lo + (hi - lo) / 2;
            final int cmp = this.compare(query, this.index[mid]);
            if (cmp < 0) {
                hi = mid - 1;
            }
            else {
                if (cmp <= 0) {
                    return mid;
                }
                lo = mid + 1;
            }
        }
        return lo;
    }
    
    private int compare(final String query, int i) {
        int m;
        int j;
        for (m = query.length(), j = 0; i < this.n && j < m; ++i, ++j) {
            if (query.charAt(j) != this.text[i]) {
                return query.charAt(j) - this.text[i];
            }
        }
        if (i < this.n) {
            return -1;
        }
        if (j < m) {
            return 1;
        }
        return 0;
    }
    
    public static void main(final String[] args) {
        final String s = StdIn.readAll().replaceAll("\n", " ").trim();
        final SuffixArrayX suffix1 = new SuffixArrayX(s);
        final SuffixArray suffix2 = new SuffixArray(s);
        boolean check = true;
        for (int i = 0; check && i < s.length(); ++i) {
            if (suffix1.index(i) != suffix2.index(i)) {
                StdOut.println("suffix1(" + i + ") = " + suffix1.index(i));
                StdOut.println("suffix2(" + i + ") = " + suffix2.index(i));
                final String ith = "\"" + s.substring(suffix1.index(i), Math.min(suffix1.index(i) + 50, s.length())) + "\"";
                final String jth = "\"" + s.substring(suffix2.index(i), Math.min(suffix2.index(i) + 50, s.length())) + "\"";
                StdOut.println(ith);
                StdOut.println(jth);
                check = false;
            }
        }
        StdOut.println("  i ind lcp rnk  select");
        StdOut.println("---------------------------");
        for (int i = 0; i < s.length(); ++i) {
            final int index = suffix2.index(i);
            final String ith2 = "\"" + s.substring(index, Math.min(index + 50, s.length())) + "\"";
            final int rank = suffix2.rank(s.substring(index));
            assert s.substring(index).equals(suffix2.select(i));
            if (i == 0) {
                StdOut.printf("%3d %3d %3s %3d  %s\n", i, index, "-", rank, ith2);
            }
            else {
                final int lcp = suffix2.lcp(i);
                StdOut.printf("%3d %3d %3d %3d  %s\n", i, index, lcp, rank, ith2);
            }
        }
    }
}
