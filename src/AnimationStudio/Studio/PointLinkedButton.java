package AnimationStudio.Studio;

import AnimationStudio.Animation.MyPoint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class PointLinkedButton extends Component {

    private static int ButtonRadius = 10;
    private JRadioButton button;
    private MyPoint offset;
    private MyPoint location;
    private MouseListener mouseListener;

    private boolean pressed = false;
    private int cX, cY;
    private int oMX, oMY;
    private int dMX, dMY;

    public PointLinkedButton(MyPoint locationIn, MyPoint offset) {
        this.offset = offset;
        this.location = locationIn;
//        button = new JRadioButton();
        button = new JRadioButton() {
            @Override
            public void paint(Graphics graphics) {
                button.setBounds(location.x + offset.x - ButtonRadius, location.y + offset.y - ButtonRadius, 2 * ButtonRadius, 2 * ButtonRadius);
                super.paint(graphics);
            }
        };
        mouseListener = new PLBMouseListener();
        button.addMouseListener(mouseListener);
        button.setBounds(location.x + offset.x - ButtonRadius, location.y + offset.y - ButtonRadius, 2 * ButtonRadius, 2 * ButtonRadius);
        setBounds(0, 0, 1, 1);
    }

    @Override
    public void paint(Graphics graphics) {
        if (!pressed) {
            return;
        }

        Point mousePos = MouseInfo.getPointerInfo().getLocation();
        dMX = mousePos.x - oMX;
        dMY = mousePos.y - oMY;

        int nX = cX + dMX;
        int nY = cY + dMY;

        int pX = nX + offset.x;
        int pY = nY + offset.y;

        location.x = nX;
        location.y = nY;
        button.setBounds(pX - ButtonRadius, pY - ButtonRadius, 2 * ButtonRadius, 2 * ButtonRadius);
    }

    public void addTo(JFrame frame) {
        frame.add(button);
        frame.add(this);
    }

    public void removeFrom(Frame frame) {
        frame.remove(button);
        frame.remove(this);
    }

    public void setLocation(MyPoint location) {
        this.location = location;
//        button.setBounds(location.x + offset.x - ButtonRadius, location.y + offset.y - ButtonRadius, 2 * ButtonRadius, 2 * ButtonRadius);
    }

    private class PLBMouseListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // DO Nothing
        }

        @Override
        public void mousePressed(MouseEvent e) {
            if (!pressed) {
                pressed = true;
                Point mousePos = MouseInfo.getPointerInfo().getLocation();
                oMX = mousePos.x;
                oMY = mousePos.y;
                cX = location.x;
                cY = location.y;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            pressed = false;
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            // DO Nothing
        }

        @Override
        public void mouseExited(MouseEvent e) {
            // DO Nothing
        }
    }

}
