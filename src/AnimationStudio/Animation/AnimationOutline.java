package AnimationStudio.Animation;

import java.util.ArrayList;

public class AnimationOutline {

    private AnimationPoints[] animationStates;

    public AnimationOutline(AnimationPoints... animationStates) {
        this.animationStates = animationStates;
    }

    public int getNumStates() {
        return animationStates.length;
    }

    public AnimationPoints[] getAnimationStates() {
        return animationStates;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < animationStates.length; i++) {
            builder.append('A').append(animationStates[i]).append("P\n");
        }

        return builder.toString();
    }

    public static AnimationOutline fromString(String string) {
        ArrayList<AnimationPoints> animPoints = new ArrayList<>();

        int idx1 = string.indexOf("A");
        int idx2 = string.indexOf("P");

        while (idx2 > 0) {
            animPoints.add(AnimationPoints.fromString(string.substring(idx1 + 1, idx2)));
            string = string.substring(idx2 + 1);
            idx1 = string.indexOf("A");
            idx2 = string.indexOf("P");
        }

        AnimationPoints[] animPointsArray = new AnimationPoints[animPoints.size()];
        for (int i = 0; i < animPointsArray.length; i++) {
            animPointsArray[i] = animPoints.get(i);
        }

        return new AnimationOutline(animPointsArray);
    }

}
