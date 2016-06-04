package com.jiangli.graphics.common;

public class Rect {
    private int x;
    private int y;
    private int width;
    private int length;

    public Rect() {

    }

    public Rect(int x, int y, int width, int length) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.length = length;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getLength() {
        return length;
    }

    @Override
    public String toString() {
        return "Rect{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", length=" + length +
                '}';
    }

    public void setLength(int length) {


        this.length = length;
    }
}
