package AnimationStudio.Animation;

import java.awt.*;

public class MyPoint {

    public int x;
    public int y;

    public MyPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(MyPoint point) {
        x = point.x;
        y = point.y;
    }

    public void scale(double scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void add(int x, int y) {
        this.x += x;
        this.y += y;
    }

    public MyPoint clone() {
        return new MyPoint(x, y);
    }

    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    public static MyPoint fromString(String string) {
        int commaIdx = string.indexOf(',');
        String x = string.substring(1, commaIdx);
        String y = string.substring(commaIdx + 2, string.length() - 1);

        return new MyPoint(Integer.parseInt(x), Integer.parseInt(y));
    }

}
