package AnimationStudio.Studio;

import AnimationStudio.Animation.AnimationPoints;
import AnimationStudio.Animation.MyPoint;

import javax.swing.*;

public class ButtonOverlay2 {

    private AnimationPoints animationPoints;
    private PointLinkedButton[] buttons;
    private MyPoint offset;

    public ButtonOverlay2(AnimationPoints animationPoints, MyPoint offset) {
        this.animationPoints = animationPoints;
        this.offset = offset;
        buttons = new PointLinkedButton[animationPoints.getPoints().length];
        MyPoint[] myPoints = animationPoints.getPoints();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new PointLinkedButton(myPoints[i], offset);
        }
    }

    public void addTo(JFrame frame) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].addTo(frame);
//            System.out.println("Pos: (" + buttons[i].getX() + ", " + buttons[i].getY() + ")\n");
        }
    }

    public void removeFrom(JFrame frame) {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].removeFrom(frame);
        }
    }

    public void setAnimationPoints(AnimationPoints animationPoints) {
        this.animationPoints = animationPoints;
        MyPoint[] points = animationPoints.getPoints();
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setLocation(points[i]);
        }
    }

}
