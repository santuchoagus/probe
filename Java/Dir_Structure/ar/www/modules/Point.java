package ar.www.modules;

public class Point {
    public final double x;
    public final double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {
        this(1, 0);
    }

    public final double size() {
        return Math.hypot(this.x, this.y);
    }

    public String toString() {
        return String.format("Point(%s, %s)", this.x, this.y);
    }
}
