package AnimationStudio.Studio;

import AnimationStudio.Animation.Animation;
import AnimationStudio.Animation.AnimationOutline;
import AnimationStudio.Animation.AnimationPoints;
import AnimationStudio.Animation.MyPoint;
import AnimationStudio.KeyBoardListener;
import AnimationStudio.Renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.Scanner;
import java.util.TimerTask;

public class StudioGUI extends JFrame {

    private StudioData studioData;

    private Button[] selectStates;
    private TextField timeInput;
    private ButtonOverlay2[] buttonOverlays;

    private Button playButton;
    private Button printButton;
    private Button writeButton;
    private TextField writeFileName;
    private Button readButton;
    private TextField readFileName;

    private final int BlinkDuration = 500;
    private java.util.Timer blinkTimer;
    private final Color successColor = Color.GREEN;
    private final Color failureColor = Color.RED;

    private int selectedIdx;
    private Renderer renderer;
    private AnimationPoints animated;
    private boolean animRunning = false;

    private MyPoint zeroPoint;
    private PointLinkedButton zeroButton;

    private String pathString = "C:\\Users\\eddie\\Downloads\\Programming\\C++ Programs\\AnimationBasics\\Animations\\";

    public StudioGUI(StudioData studioData, Renderer renderer) {
        this.studioData = studioData;
        this.renderer = renderer;
        animated = renderer.getAnimationPoints();
        selectStates = new Button[studioData.getNumStates()];
        timeInput = new TextField();
        buttonOverlays = new ButtonOverlay2[studioData.getNumStates()];
        blinkTimer = new java.util.Timer();

        zeroPoint = new MyPoint(400, 400);
        zeroButton = new PointLinkedButton(zeroPoint, new MyPoint(0, 0));
//        zeroButton.addMouseListener((MouseClickedListener) e -> {
//
//        });
        zeroButton.addTo(this);

        setLayout(null);

        Label state = new Label("States: ");
        state.setBounds(10, 10, 40, 20);
        add(state);

        Label timeLabel = new Label("Time: ");
        timeLabel.setBounds(10, 85, 30, 20);
        add(timeLabel);

        AnimationPoints[] animStates = studioData.getOutline().getAnimationStates();
        for (int i = 0; i < studioData.getNumStates(); i++) {
            selectStates[i] = new Button("" + i);
            selectStates[i].setBackground(Color.WHITE);
            selectStates[i].addMouseListener(new SGUIButtonMouseListener());
            selectStates[i].setBounds(60 * i + 10, 30, 50, 50);
            add(selectStates[i]);
            buttonOverlays[i] = new ButtonOverlay2(animStates[i], zeroPoint);
        }
        timeInput.setBounds(45, 85, 40, 20);
        add(timeInput);

        playButton = new Button("Play");
        playButton.setBounds(900, 10, 50, 40);
        playButton.addMouseListener((MouseClickedListener) e -> {
            startAnim();
        });
        add(playButton);
        printButton = new Button("Print");
        printButton.setBounds(900, 60, 50, 40);
        printButton.addMouseListener((MouseClickedListener) e -> {
            System.out.println(studioData.getOutline().toString());
        });
        add(printButton);

        Label readFileNameLabel = new Label("File Name: ");
//        readFileNameLabel.setBounds(870, 160, 60, 20);
        readFileNameLabel.setBounds(960, 120, 60, 20);
        add(readFileNameLabel);

        writeFileName = new TextField("File Name");
//        writeFileName.setBounds(930, 160, 100, 20);
        writeFileName.setBounds(1020, 120, 100, 20);
        add(writeFileName);

        writeButton = new Button("Write");
        writeButton.setBounds(900, 110, 50, 40);
        writeButton.addMouseListener((MouseClickedListener) e -> {
            boolean success = true;
            try {
                FileWriter fileWriter = new FileWriter(pathString + writeFileName.getText() + ".txt");
                fileWriter.write(studioData.getWholeAnimation().toString());
                fileWriter.close();
            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("Failed to Write");
                success = false;
            }
            setBlink(writeButton, success ? successColor : failureColor, playButton.getBackground());
        });
        add(writeButton);

        Label writeFileNameLabel = new Label("File Name: ");
        writeFileNameLabel.setBounds(960, 170, 60, 20);
        add(writeFileNameLabel);

        readFileName = new TextField("File Name");
        readFileName.setBounds(1020, 170, 100, 20);
        add(readFileName);

        readButton = new Button("Read");
        readButton.setBounds(900, 160, 50, 40);
        readButton.addMouseListener((MouseClickedListener) e -> {
            boolean success = true;
            try {
                FileReader fileReader = new FileReader(pathString + readFileName.getText() + ".txt");
                Scanner reader = new Scanner(fileReader);
                String contents = "";
                while (reader.hasNextLine()) {
                    contents += reader.nextLine();
                    if (reader.hasNextLine()) {
                        contents += "\n";
                    }
                }

                studioData.setWholeAnimation(Animation.fromString(contents, studioData.getWholeAnimation().getCurrentData()));
                AnimationPoints[] outlinesPoints = studioData.getWholeAnimation().getOutline().getAnimationStates();
                for (int i = 0; i < outlinesPoints.length && i < buttonOverlays.length; i++) {
                    buttonOverlays[i].setAnimationPoints(outlinesPoints[i]);
                }

                /*
                Animation animation = studioData.getWholeAnimation();
                AnimationOutline outline = animation.getOutline();
                for (int i = 0; i < animation.getTimeChanges().length; i++) {
                    animation.getTimeChanges()[i] *= .5;
//                    outline.getAnimationStates()[i].scaleAll(.5);
                }
                //*/

            } catch (Exception exception) {
                exception.printStackTrace();
                System.out.println("Failed to Read");
                success = false;
            }
            setBlink(readButton, success ? successColor : failureColor, playButton.getBackground());
        });
        add(readButton);

        add(renderer);
        selectButton(0);

        setBounds(75, 25, 1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setVisible(true);
    }

    public void selectButton(int idx) {
        selectStates[selectedIdx].setBackground(Color.WHITE);
        buttonOverlays[selectedIdx].removeFrom(this);

        selectedIdx = idx;

        selectStates[selectedIdx].setBackground(Color.GRAY);
        timeInput.setText("" + studioData.getTimeChanges()[selectedIdx]);
        buttonOverlays[selectedIdx].addTo(this);
        renderer.setAnimationPoints(studioData.getOutline().getAnimationStates()[selectedIdx]);
        animRunning = false;
    }

    public int findButtonIdx(Object source) {
        for (int i = 0; i < selectStates.length; i++) {
            if (source == selectStates[i]) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public void paint(Graphics graphics) {
        if (animRunning) {
            studioData.getWholeAnimation().update();
        } else if (KeyBoardListener.get(KeyEvent.VK_SPACE)) {
            startAnim();
        }
        super.paint(graphics);
    }

    public void startAnim() {
        selectStates[selectedIdx].setBackground(Color.WHITE);
        buttonOverlays[selectedIdx].removeFrom(this);
        renderer.setAnimationPoints(animated);
        studioData.getWholeAnimation().start();
        animRunning = true;
    }

    public MyPoint getZeroPoint() {
        return zeroPoint;
    }

    public void setBlink(Component component, Color blinkTo, Color normal) {
        component.setBackground(blinkTo);
        blinkTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                component.setBackground(normal);
            }
        }, BlinkDuration);
    }

    private class SGUIButtonMouseListener implements MouseClickedListener {
        @Override
        public void mouseClicked(MouseEvent e) {
            selectButton(findButtonIdx(e.getSource()));
        }
    }

}
