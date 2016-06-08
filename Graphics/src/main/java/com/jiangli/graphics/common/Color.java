package com.jiangli.graphics.common;

/**
 * @author Jiangli
 *
 *         CreatedTime  2016/6/1 0001 15:41
 */
public class Color {
    static int offSet = 20;
    int r = 0;
    int g = 0;
    int b = 0;

    public Color(int r, int g, int b) {
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public Color() {
    }

    public static int getOffSet() {
        return offSet;
    }

    public int getR() {
        return r;
    }

    public int getG() {
        return g;
    }

    public int getB() {
        return b;
    }

    private boolean withinOffset(int x) {
        return Math.abs(x) <= offSet;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if ( obj instanceof Color) {
            Color c = (Color) obj;
            return withinOffset(this.r-c.r) && withinOffset(this.g-c.g) && withinOffset(this.b-c.b);
        }
        return false;
    }

    @Override
    public String toString() {
        return "Color{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}
