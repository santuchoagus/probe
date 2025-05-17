package com._64;
import ar.www.modules.Point;

public class App {
    public static void main(String[] args) {
        Point p1 = new Point();
        Point p2 = new Point(1,2);

        System.out.printf("Comparing points P1 %s and P2 %s\n", p1, p2);
    }
}
