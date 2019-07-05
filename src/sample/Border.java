package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static sample.LayoutCreator.triangleBase;

public class Border extends Pane {
    private Rectangle leftBorder = new Rectangle();
    private Rectangle rightBorder = new Rectangle();

    public Border(){
        leftBorder.setHeight(12*triangleBase);
        leftBorder.setWidth(0.5*triangleBase);
        rightBorder.setHeight(12*triangleBase);
        rightBorder.setWidth(0.5*triangleBase);
        leftBorder.setFill(Color.valueOf("#1E0000"));
        rightBorder.setFill(Color.valueOf("#1E0000"));
        leftBorder.setX(0);
        leftBorder.setY(0);
        rightBorder.setX(14*triangleBase);
        rightBorder.setY(0);

        relocate(0.25*triangleBase,0);
        getChildren().addAll(rightBorder, leftBorder);
    }
}
