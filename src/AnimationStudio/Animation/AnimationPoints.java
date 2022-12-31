package AnimationStudio.Animation;

import java.util.ArrayList;

public class AnimationPoints {

    private MyPoint[] points;

    public AnimationPoints(int numPoints) {
        points = new MyPoint[numPoints];
        for (int i = 0; i < points.length; i++) {
            points[i] = new MyPoint(0, 0);
        }
    }

    public AnimationPoints(AnimationPoints toCopy) {
        this.points = new MyPoint[toCopy.getNumPoints()];
        for (int i = 0; i < points.length; i++) {
            points[i] = toCopy.getPoints()[i].clone();
        }
    }

    private AnimationPoints(MyPoint[] points) {
        this.points = points;
    }

    int getNumPoints() {
        return points.length;
    }

    public MyPoint[] getPoints() {
        return points;
    }

    public void addAll(int x, int y) {
        for (int i = 0; i < getNumPoints(); i++) {
            points[i].add(x, y);
        }
    }

    public void scaleAll(double scalar) {
        for (int i = 0; i < getNumPoints(); i++) {
            points[i].scale(scalar);
        }
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < points.length; i++) {
            string.append(points[i].toString()).append(" ");
        }

        return string.toString();
    }

    public static AnimationPoints fromString(String string) {
        ArrayList<MyPoint> points = new ArrayList<>();

        int idx1 = 0;
        int idx2 = string.indexOf(") ") + 1;
        while (idx2 > 0) {
            points.add(MyPoint.fromString(string.substring(idx1, idx2)));
            string = string.substring(idx2 + 1);
            idx2 = string.indexOf(") ") + 1;
        }

        MyPoint[] arrayPoints = new MyPoint[points.size()];
        for (int i = 0; i < points.size(); i++) {
            arrayPoints[i] = points.get(i);
        }

        return new AnimationPoints(arrayPoints);
    }

}
