package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static sample.LayoutCreator.triangleBase;

public class ControlPanel extends Pane {
    public static final double width = 100;
    public static final double height = 12*triangleBase;
    public static final double arc = 20;
//    public Button pauseButton = new Button((width - Button.width)/2, height / 5, "#1155DC", "Pause", true);
//    public Button restartButton = new Button((width - Button.width)/2, height * 2 / 5, "#1155DC", "Restart", true);
//    public Button resumeButton = new Button((width - Button.width)/2, height * 3 / 5, "#1155DC", "Resume", false);

    public Button pauseButton = new Button((width - Button.width)/2, 4*triangleBase, "#1155DC", "Pause", true);
    public Button restartButton = new Button((width - Button.width)/2, 5.5*triangleBase, "#1155DC", "Restart", true);
    public Button resumeButton = new Button((width - Button.width)/2, 7*triangleBase, "#1155DC", "Resume", false);
    public ControlPanel (double x, double y){
        relocate(x, y);
        getChildren().addAll(pauseButton, restartButton, resumeButton);
    }

    private Rectangle rectangleInit (double x, double y, String color) {
        Rectangle temp = new Rectangle();
        temp.relocate(x, y);
        temp.setWidth(width);
        temp.setHeight(height);
        temp.setArcWidth(arc);
        temp.setArcHeight(arc);
        temp.setFill(Color.valueOf(color));
        return temp;
    }

}
