package AnimationStudio;

import AnimationStudio.Animation.AnimationOutline;
import AnimationStudio.Renderers.StickPerson;

import java.io.FileReader;
import java.util.Scanner;

public class StickPersonSwap {

    public static void main(String... args) {

        String contents = "";

        contents = "A(0, -200) (0, -150) (0, -80) (-19, -106) (7, -97) (13, -103) (37, -118) (-6, -33) (-34, -32) (23, -38) (32, 0) P\n" +
                "A(0, -200) (0, -150) (0, -80) (-3, -103) (26, -98) (0, -104) (21, -111) (3, -36) (-19, -18) (4, -36) (6, 0) P\n" +
                "A(0, -200) (0, -150) (0, -80) (13, -103) (37, -118) (-19, -106) (7, -97) (18, -36) (-5, -13) (-4, -33) (-26, -6) P";

        if (contents.equals("")) {
            try {
                FileReader fileReader = new FileReader("Running" + ".txt");
                Scanner reader = new Scanner(fileReader);
                while (reader.hasNextLine()) {
                    contents += reader.nextLine();
                    if (reader.hasNextLine()) {
                        contents += "\n";
                    }
                }
            } catch (Exception exception) {
                System.out.println("Failed to Read");
                exception.printStackTrace();
            }
        }

        //AnimationPoints points = AnimationPoints.fromString(contents);
        AnimationOutline outline = AnimationOutline.fromString(contents);
        for (int i = 0; i < outline.getNumStates(); i++) {
            outline.getAnimationStates()[i] = StickPerson.switchLeftAndRight(outline.getAnimationStates()[i]);
        }

        System.out.println(outline);

    }

}
