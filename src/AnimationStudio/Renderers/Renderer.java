package AnimationStudio.Renderers;

import AnimationStudio.Animation.AnimationPoints;
import AnimationStudio.Animation.MyPoint;

import java.awt.*;

public abstract class Renderer extends Component {

    protected AnimationPoints data;
    protected MyPoint offset;

    public Renderer(int numPoints) {
        data = new AnimationPoints(numPoints);
        setVisible(true);
    }

    public Renderer(int numPoints, MyPoint offset) {
        data = new AnimationPoints(numPoints);
        setVisible(true);
        this.offset = offset;
    }

    public void setOffset(MyPoint offset) {
        this.offset = offset;
    }

    public AnimationPoints getAnimationPoints() {
        return data;
    }

    public void setAnimationPoints(AnimationPoints data) {
        this.data = data;
    }

}
