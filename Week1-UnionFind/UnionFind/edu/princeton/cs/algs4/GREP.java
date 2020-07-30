// 
// Decompiled by Procyon v0.5.36
// 

package edu.princeton.cs.algs4;

public class GREP
{
    private GREP() {
    }
    
    public static void main(final String[] args) {
        final String regexp = "(.*" + args[0] + ".*)";
        final NFA nfa = new NFA(regexp);
        while (StdIn.hasNextLine()) {
            final String line = StdIn.readLine();
            if (nfa.recognizes(line)) {
                StdOut.println(line);
            }
        }
    }
}
