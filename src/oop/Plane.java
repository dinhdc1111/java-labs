package oop;

/** Mặt phẳng z = a.x + b.y + c (a, b kế thừa từ Line) */
public class Plane extends Line {
    private double c;

    public Plane() {
    }

    public Plane(double a, double b, double c) {
        super(a, b);
        this.c = c;
    }

    public double getC() { return c; }
    public void setC(double c) { this.c = c; }

    /** Dạng Ax + By + Cz + D = 0 với A=a, B=b, C=-1, D=c → d(O) = |c| / sqrt(a² + b² + 1) */
    @Override
    public double distanceFromOrigin() {
        double a = getA(), b = getB();
        return Math.abs(c) / Math.sqrt(a * a + b * b + 1);
    }

    public boolean contains(double x, double y, double z) {
        return Math.abs(getA() * x + getB() * y + c - z) < EPS;
    }

    /** Điểm A(x, y) được hiểu là A(x, y, 0) */
    @Override
    public boolean contains(double x, double y) {
        return contains(x, y, 0);
    }

    @Override
    public String toString() {
        return "z = " + getA() + "x + " + getB() + "y + " + c;
    }
}
