package oop;

/** Đường thẳng y = a.x + b */
public class Line {
    protected static final double EPS = 1e-9;

    private double a;
    private double b;

    public Line() {
    }

    public Line(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() { return a; }
    public void setA(double a) { this.a = a; }
    public double getB() { return b; }
    public void setB(double b) { this.b = b; }

    /** Đường ax - y + b = 0 → d(O) = |b| / sqrt(a² + 1) */
    public double distanceFromOrigin() {
        return Math.abs(b) / Math.sqrt(a * a + 1);
    }

    public boolean contains(double x, double y) {
        return Math.abs(a * x + b - y) < EPS;
    }

    @Override
    public String toString() {
        return "y = " + a + "x + " + b;
    }
}
