package oop;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        selfCheck();

        List<Line> shapes = List.of(
                new Line(1, 0),
                new Line(2, 3),
                new Line(0, 5),
                new Line(-1, 4),
                new Plane(1, 1, -4),
                new Plane(0, 0, 2),
                new Plane(2, -1, 0));

        System.out.println("Danh sách:");
        shapes.forEach(s -> System.out.println("  " + s));

        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập xa: ");
        double xa = sc.nextDouble();
        System.out.print("Nhập ya: ");
        double ya = sc.nextDouble();

        long count = shapes.stream().filter(s -> s.contains(xa, ya)).count();
        double total = shapes.stream().mapToDouble(Line::distanceFromOrigin).sum();

        System.out.printf("Số đường thẳng qua A(%s, %s) / mặt phẳng qua A(%s, %s, 0): %d%n", xa, ya, xa, ya, count);
        System.out.printf("Tổng khoảng cách từ O đến các đường thẳng, mặt phẳng: %.4f%n", total);
    }

    private static void selfCheck() {
        assert new Line(1, 0).contains(2, 2);
        assert !new Line(1, 0).contains(2, 3);
        assert Math.abs(new Line(0, 5).distanceFromOrigin() - 5) < 1e-9;
        assert new Plane(1, 1, -4).contains(2, 2);
        assert Math.abs(new Plane(0, 0, 2).distanceFromOrigin() - 2) < 1e-9;
    }
}
