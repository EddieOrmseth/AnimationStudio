package AnimationStudio.Studio;

import AnimationStudio.Animation.AnimationPoints;
import AnimationStudio.Animation.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonOverlay extends Component {

    private final int ButtonRadius = 10;

    private Component component;

    private AnimationPoints data;
    private JRadioButton[] buttons;

    private MyPoint zeroPoint;

    private JRadioButton selectedButton;
    private MyPoint selectedPoint;

    public ButtonOverlay(AnimationPoints data, Component component, MyPoint zeroPoint) {
        this.data = data;
        this.component = component;
        this.zeroPoint = zeroPoint;
        buttons = new JRadioButton[data.getPoints().length];
        for (int i = 0; i < data.getPoints().length; i++) {
            buttons[i] = new JRadioButton();
            MyPoint point = data.getPoints()[i];
            buttons[i].setBounds(point.x - ButtonRadius, point.y - ButtonRadius, 2 * ButtonRadius, 2 * ButtonRadius);
            addMouseListener(buttons[i], i);
        }
        setBounds(0, 0, 1, 1);
    }

    public void addTo(JFrame frame) {
        for (int i = 0; i < buttons.length; i++) {
            frame.add(buttons[i]);
        }
        frame.add(this);
    }

    public void removeFrom(JFrame frame) {
        for (int i = 0; i < buttons.length; i++) {
            frame.remove(buttons[i]);
        }
        frame.remove(this);
    }

    int cX, cY;
    int oMX, oMY;
    @Override
    public void paint(Graphics graphics) {
        if (selectedButton == null || selectedPoint == null) {
            return;
        }

        Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
//
        int nX = cX + (mouseLoc.x - oMX);
        int nY = cY + (mouseLoc.y - oMY);

        selectedPoint.x = nX + ButtonRadius;
        selectedPoint.y = nY + ButtonRadius;
        selectedButton.setBounds(nX, nY, 2 * ButtonRadius, 2 * ButtonRadius);
    }

    public void setAnimationPoints(AnimationPoints points) {
        this.data = points;
        for (int i = 0; i < data.getPoints().length; i++) {
            MyPoint point = data.getPoints()[i];
            buttons[i].setBounds(point.x - ButtonRadius, point.y - ButtonRadius, 2 * ButtonRadius, 2 * ButtonRadius);
        }
    }

    private void addMouseListener(JRadioButton button, int pointIdx) {
        button.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Do Nothing
//                System.out.println("1");
            }

            @Override
            public void mousePressed(MouseEvent e) {
//                System.out.println("2");
                selectedButton = button;
                selectedPoint = data.getPoints()[pointIdx];
                cX = button.getX();
                cY = button.getY();
                Point mouseLoc = MouseInfo.getPointerInfo().getLocation();
                oMX = mouseLoc.x;
                oMY = mouseLoc.y;
            }

            @Override
            public void mouseReleased(MouseEvent e) {
//                System.out.println("3");
                selectedButton = null;
                selectedPoint = null;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // Do Nothing
//                System.out.println("4");
            }

            @Override
            public void mouseExited(MouseEvent e) {
                // Do Nothing
//                System.out.println("5");
            }
        });
    }

}
