package AnimationStudio.Studio;

import AnimationStudio.Animation.Animation;
import AnimationStudio.Animation.AnimationOutline;
import AnimationStudio.Animation.AnimationPoints;

import java.awt.*;

public class StudioData extends Component {

    private AnimationPoints currentData;

    private AnimationOutline outline;
    private double[] timeChanges;
    private Animation wholeAnimation;

    public StudioData(AnimationPoints currentData, int numStates) {
        this.currentData = currentData;
        AnimationPoints[] animationPoints = new AnimationPoints[numStates];
        outline = new AnimationOutline(animationPoints);
        timeChanges = new double[numStates];
        wholeAnimation = new Animation(currentData, outline, timeChanges);
    }

    public Animation getWholeAnimation() {
        return wholeAnimation;
    }

    public AnimationOutline getOutline() {
        return outline;
    }

    public int getNumStates() {
        return timeChanges.length;
    }

    public double[] getTimeChanges() {
        return timeChanges;
    }

    public void setWholeAnimation(Animation animation) {
        this.wholeAnimation = animation;
        this.outline = animation.getOutline();
        this.timeChanges = animation.getTimeChanges();
    }

}
