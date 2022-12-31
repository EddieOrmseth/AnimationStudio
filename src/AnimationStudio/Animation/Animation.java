package AnimationStudio.Animation;

import java.util.ArrayList;
import java.util.Arrays;

public class Animation {

    private MyPoint[] startPoints;
    private AnimationPoints currentData;
    private AnimationOutline outline;
    private double[] timeChanges;

    private int currentFrame = -1;
    private MyPoint[] deltas;

    private double startTime;
    private double timePassed;
    private double timeNeeded;

    public Animation(AnimationPoints currentData, AnimationOutline outline, double[] timesChanges) {
        this.currentData = currentData;
        this.outline = outline;
        this.timeChanges = timesChanges;
        startPoints = new MyPoint[currentData.getNumPoints()];
        for (int i = 0; i < startPoints.length; i++) {
            startPoints[i] = new MyPoint(0, 0);
        }
        deltas = new MyPoint[currentData.getNumPoints()];
    }

    public void start() {
        transitionToFrame(0);
        startTime = System.currentTimeMillis();
    }

    public void update() {
        double now = System.currentTimeMillis();
        timePassed = now - startTime;

        boolean transitionToNext = timePassed > timeNeeded;
        double newDelta = transitionToNext ? timeNeeded : timePassed;

        movePoints(newDelta);

        if (transitionToNext) {
            double leftOver = timePassed - timeNeeded;
            transitionToFrame(currentFrame + 1);
            startTime -= leftOver;
//            update();
        }
    }

    public void movePoints(double deltaTime) {
        double fractionDone = deltaTime / timeNeeded;

        MyPoint[] currentPoints = currentData.getPoints();

        for (int i = 0; i < currentData.getNumPoints(); i++) {
            MyPoint startPoint = startPoints[i];
            MyPoint delta = deltas[i];
            MyPoint currentPoint = currentPoints[i];

            currentPoint.x = (int) (startPoint.x + delta.x * fractionDone);
            currentPoint.y = (int) (startPoint.y + delta.y * fractionDone);

            currentPoints[i] = currentPoint;
        }

    }

    public void transitionToFrame(int frame) {
        if (frame >= outline.getNumStates()) {
            reset();
            return;
        }

        currentFrame = frame;

        AnimationPoints targetState = outline.getAnimationStates()[currentFrame];
        MyPoint[] targetPoints = targetState.getPoints();

        MyPoint[] currentPoints = currentData.getPoints();

        for (int i = 0; i < startPoints.length; i++) {
            startPoints[i].x = currentPoints[i].x;
            startPoints[i].y = currentPoints[i].y;
        }

        for (int i = 0; i < currentData.getNumPoints(); i++) {
            deltas[i] = new MyPoint(targetPoints[i].x - currentPoints[i].x, targetPoints[i].y - currentPoints[i].y);
        }

        startTime = System.currentTimeMillis();
        timePassed = 0;
        timeNeeded = timeChanges[currentFrame] * 1000;
    }

    public void reset() {
        transitionToFrame(0);
    }

    public AnimationOutline getOutline() {
        return outline;
    }

    public double[] getTimeChanges() {
        return timeChanges;
    }

    public AnimationPoints getCurrentData() {
        return currentData;
    }

    @Override
    public String toString() {
        return outline.toString() + "\n\n" + Arrays.toString(timeChanges);
    }

    public static Animation fromString(String string, AnimationPoints currentData) {
        int idx = string.indexOf("\n\n");
        AnimationOutline outline = AnimationOutline.fromString(string.substring(0, idx + 1));
        String timeString = string.substring(idx + 4, string.length() - 1);

        ArrayList<Double> timeChanges = new ArrayList<>();
        int idx1 = 0;
        int idx2 = timeString.indexOf(",");
        while (idx2 > 0) {
            String str = timeString.substring(idx1, idx2);
            timeChanges.add(Double.parseDouble(str));

            timeString = timeString.substring(idx2 + 1);
            idx2 = timeString.indexOf(",");
        }
        timeChanges.add(Double.parseDouble(timeString.substring(idx1)));

        double[] timeChangesArray = new double[timeChanges.size()];
        for (int i = 0; i < timeChangesArray.length; i++) {
            timeChangesArray[i] = timeChanges.get(i);
        }

        return new Animation(currentData, outline, timeChangesArray);
    }

}
