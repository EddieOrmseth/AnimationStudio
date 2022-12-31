package AnimationStudio;

import AnimationStudio.Animation.Animation;
import AnimationStudio.Animation.AnimationOutline;
import AnimationStudio.Animation.AnimationPoints;
import AnimationStudio.Animation.MyPoint;
import AnimationStudio.Renderers.StickPerson;
import AnimationStudio.Studio.StudioData;
import AnimationStudio.Studio.StudioGUI;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String... args) {

//        ShowOutlineAnimation();

        int numStates = 8;

        StickPerson person = new StickPerson(Color.BLACK, 5, 28);
        AnimationPoints currentData = person.getAnimationPoints();
        StudioData data = new StudioData(currentData, numStates);

        AnimationOutline outline = data.getOutline();
        AnimationPoints[] points = new AnimationPoints[numStates];
        for (int i = 0; i < points.length; i++) {
            outline.getAnimationStates()[i] = new AnimationPoints(StickPerson.stickPersonOutline);
            data.getTimeChanges()[i] = 1;
        }

        StudioGUI gui = new StudioGUI(data, person);
        person.setOffset(gui.getZeroPoint());

        while (true) {
            gui.repaint();
        }
    }

    public static void ShowOutlineAnimation() {
        JFrame frame = new JFrame();
        frame.setBounds(75, 25, 1200, 700);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        StickPerson person = new StickPerson(Color.BLACK, 5, 28);
        person.setVisible(true);

        AnimationPoints points = new AnimationPoints(StickPerson.numPoints);
        MyPoint[] myPoints = points.getPoints();
        MyPoint[] personOutline = StickPerson.stickPersonOutline.getPoints();
        for (int i = 0; i < points.getPoints().length; i++) {
            myPoints[i] = personOutline[i];
            myPoints[i].x += 400;
            myPoints[i].y += 400;
        }

        AnimationOutline outline = new AnimationOutline(points);
        Animation animation = new Animation(person.getAnimationPoints(), outline, new double[]{5.0});

        frame.add(person);
        animation.start();

        animation.update();

        frame.setVisible(true);

        while (true) {
            animation.update();
            frame.repaint();
        }
    }

}
