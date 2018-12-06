package ug.protocols.equation;

public class IntPair {
    private final int x;
    private final int y;
    IntPair(int x, int y) {this.x=x;this.y=y;}

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}
