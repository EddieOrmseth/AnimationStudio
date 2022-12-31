package AnimationStudio.Renderers;

import AnimationStudio.Animation.AnimationPoints;
import AnimationStudio.Animation.MyPoint;

import java.awt.*;

public class StickPerson extends Renderer {

    public enum SPPoint {
        BodyTop,
        BodyShoulder,
        BodyBottom,
        LeftElbow,
        LeftHand,
        RightElbow,
        RightHand,
        LeftKnee,
        LeftFoot,
        RightKnee,
        RightFoot
    }

    public static int SPPointToInt(SPPoint point) {
        switch (point) {
            case BodyTop:
                return 0;
            case BodyShoulder:
                return 1;
            case BodyBottom:
                return 2;
            case LeftElbow:
                return 3;
            case LeftHand:
                return 4;
            case RightElbow:
                return 5;
            case RightHand:
                return 6;
            case LeftKnee:
                return 7;
            case LeftFoot:
                return 8;
            case RightKnee:
                return 9;
            case RightFoot:
                return 10;
            default:
                return -1;
        }
    }

    public static final AnimationPoints stickPersonOutline;
    public static final int numPoints = 11;

    static {
        stickPersonOutline = new AnimationPoints(numPoints);
        MyPoint[] points = stickPersonOutline.getPoints();

        points[0] = new MyPoint(0, 200);
        points[1] = new MyPoint(0, 150);
        points[2] = new MyPoint(0, 80);
        points[3] = new MyPoint(-30, 110);
        points[4] = new MyPoint(-50, 130);
        points[5] = new MyPoint(30, 110);
        points[6] = new MyPoint(50, 130);
        points[7] = new MyPoint(-20, 40);
        points[8] = new MyPoint(-40, 0);
        points[9] = new MyPoint(20, 40);
        points[10] = new MyPoint(40, 0);

        for (int i = 0; i < points.length; i++) {
            points[i].y *= -1;
        }

    }

    private Color color;
    private int bodyWidth;
    private int headRadius;

    public StickPerson(Color color, int bodyWidth, int headRadius) {
        super(numPoints);
        this.color = color;
        this.bodyWidth = bodyWidth;
        this.headRadius = headRadius;
        setBounds(0, 0, 10000, 10000);
    }

    @Override
    public void paint(Graphics graphics) {
        MyPoint[] points = data.getPoints();
        graphics.setColor(color);
        Graphics2D graphics2D = (Graphics2D) graphics;
        graphics2D.setStroke(new BasicStroke(5));

        MyPoint headPoint = points[SPPointToInt(SPPoint.BodyTop)];
        graphics.fillOval(headPoint.x + offset.x - headRadius, headPoint.y + offset.y - headRadius, 2 * headRadius, 2 * headRadius);

        drawLine(graphics, points[SPPointToInt(SPPoint.BodyTop)], points[SPPointToInt(SPPoint.BodyShoulder)]);
        drawLine(graphics, points[SPPointToInt(SPPoint.BodyShoulder)], points[SPPointToInt(SPPoint.BodyBottom)]);

        drawLine(graphics, points[SPPointToInt(SPPoint.BodyShoulder)], points[SPPointToInt(SPPoint.LeftElbow)]);
        drawLine(graphics, points[SPPointToInt(SPPoint.LeftElbow)], points[SPPointToInt(SPPoint.LeftHand)]);

        drawLine(graphics, points[SPPointToInt(SPPoint.BodyShoulder)], points[SPPointToInt(SPPoint.RightElbow)]);
        drawLine(graphics, points[SPPointToInt(SPPoint.RightElbow)], points[SPPointToInt(SPPoint.RightHand)]);

        drawLine(graphics, points[SPPointToInt(SPPoint.BodyBottom)], points[SPPointToInt(SPPoint.LeftKnee)]);
        drawLine(graphics, points[SPPointToInt(SPPoint.LeftKnee)], points[SPPointToInt(SPPoint.LeftFoot)]);

        drawLine(graphics, points[SPPointToInt(SPPoint.BodyBottom)], points[SPPointToInt(SPPoint.RightKnee)]);
        drawLine(graphics, points[SPPointToInt(SPPoint.RightKnee)], points[SPPointToInt(SPPoint.RightFoot)]);
    }

    private void drawLine(Graphics graphics, MyPoint p1, MyPoint p2) {
        graphics.drawLine(p1.x + offset.x, p1.y + offset.y, p2.x + offset.x, p2.y + offset.y);
    }

    public static AnimationPoints switchLeftAndRight(AnimationPoints oldPoints) {
        MyPoint[] oldPointArr = oldPoints.getPoints();
        AnimationPoints newPoints = new AnimationPoints(oldPoints.getPoints().length);
        MyPoint[] newPointArr = newPoints.getPoints();

        for (int i = 0; i < oldPointArr.length; i++) {
            newPointArr[i] = oldPointArr[i];
        }

        swap(newPointArr, 3, 5);
        swap(newPointArr, 4, 6);
        swap(newPointArr, 7, 9);
        swap(newPointArr, 8, 10);

        return newPoints;
    }

    public static void swap(MyPoint[] points, int index1, int index2) {
        MyPoint tmp = points[index1];
        points[index1] = points[index2];
        points[index2] = tmp;
    }

}
