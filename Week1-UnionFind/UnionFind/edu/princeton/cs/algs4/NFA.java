// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

import java.util.Iterator;

public class NFA
{
    private Digraph graph;
    private String regexp;
    private final int m;
    
    public NFA(final String regexp) {
        this.regexp = regexp;
        this.m = regexp.length();
        final Stack<Integer> ops = new Stack<Integer>();
        this.graph = new Digraph(this.m + 1);
        for (int i = 0; i < this.m; ++i) {
            int lp = i;
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '|') {
                ops.push(i);
            }
            else if (regexp.charAt(i) == ')') {
                final int or = ops.pop();
                if (regexp.charAt(or) == '|') {
                    lp = ops.pop();
                    this.graph.addEdge(lp, or + 1);
                    this.graph.addEdge(or, i);
                }
                else if (regexp.charAt(or) == '(') {
                    lp = or;
                }
                else {
                    assert false;
                }
            }
            if (i < this.m - 1 && regexp.charAt(i + 1) == '*') {
                this.graph.addEdge(lp, i + 1);
                this.graph.addEdge(i + 1, lp);
            }
            if (regexp.charAt(i) == '(' || regexp.charAt(i) == '*' || regexp.charAt(i) == ')') {
                this.graph.addEdge(i, i + 1);
            }
        }
        if (ops.size() != 0) {
            throw new IllegalArgumentException("Invalid regular expression");
        }
    }
    
    public boolean recognizes(final String txt) {
        DirectedDFS dfs = new DirectedDFS(this.graph, 0);
        Bag<Integer> pc = new Bag<Integer>();
        for (int v = 0; v < this.graph.V(); ++v) {
            if (dfs.marked(v)) {
                pc.add(v);
            }
        }
        for (int i = 0; i < txt.length(); ++i) {
            if (txt.charAt(i) == '*' || txt.charAt(i) == '|' || txt.charAt(i) == '(' || txt.charAt(i) == ')') {
                throw new IllegalArgumentException("text contains the metacharacter '" + txt.charAt(i) + "'");
            }
            final Bag<Integer> match = new Bag<Integer>();
            for (final int v2 : pc) {
                if (v2 == this.m) {
                    continue;
                }
                if (this.regexp.charAt(v2) != txt.charAt(i) && this.regexp.charAt(v2) != '.') {
                    continue;
                }
                match.add(v2 + 1);
            }
            dfs = new DirectedDFS(this.graph, match);
            pc = new Bag<Integer>();
            for (int v3 = 0; v3 < this.graph.V(); ++v3) {
                if (dfs.marked(v3)) {
                    pc.add(v3);
                }
            }
            if (pc.size() == 0) {
                return false;
            }
        }
        for (final int v4 : pc) {
            if (v4 == this.m) {
                return true;
            }
        }
        return false;
    }
    
    public static void main(final String[] args) {
        final String regexp = "(" + args[0] + ")";
        final String txt = args[1];
        final NFA nfa = new NFA(regexp);
        StdOut.println(nfa.recognizes(txt));
    }
}
