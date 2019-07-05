package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class DiceSide extends Pane {
    public static double diceSize = 60;
    public Circle[] points = new Circle[6];

    public DiceSide (int value, double x, double y){
        relocate(x, y);
        switch (value){
            case 1:
                points[0] = setPoint(0.5, 0.5 );
                getChildren().addAll(points[0]);
                break;
            case 2:
                points[0] = setPoint(0.333,0.333);
                getChildren().addAll(points[0]);
                points[1] = setPoint(0.666,0.666);
                getChildren().addAll(points[1]);
                break;
            case 3:
                points[0] = setPoint(0.25,0.25);
                getChildren().addAll(points[0]);
                points[1] = setPoint(0.5,0.5);
                getChildren().addAll(points[1]);
                points[2] = setPoint(0.75,0.75);
                getChildren().addAll(points[2]);
                break;
            case 4:
                points[0] = setPoint(0.333,0.333);
                getChildren().addAll(points[0]);
                points[1] = setPoint(0.333,0.666);
                getChildren().addAll(points[1]);
                points[2] = setPoint(0.666,0.333);
                getChildren().addAll(points[2]);
                points[3] = setPoint(0.666,0.666);
                getChildren().addAll(points[3]);
                break;
            case 5:
                points[0] = setPoint(0.25,0.25);
                getChildren().addAll(points[0]);
                points[1] = setPoint(0.5,0.5);
                getChildren().addAll(points[1]);
                points[2] = setPoint(0.75,0.75);
                getChildren().addAll(points[2]);
                points[3] = setPoint(0.25,0.75);
                getChildren().addAll(points[3]);
                points[4] = setPoint(0.75,0.25);
                getChildren().addAll(points[4]);
                break;
            case 6:
                points[0] = setPoint(0.333,0.25);
                getChildren().addAll(points[0]);
                points[1] = setPoint(0.333,0.5);
                getChildren().addAll(points[1]);
                points[2] = setPoint(0.333,0.75);
                getChildren().addAll(points[2]);
                points[3] = setPoint(0.666,0.25);
                getChildren().addAll(points[3]);
                points[4] = setPoint(0.666,0.5);
                getChildren().addAll(points[4]);
                points[5] = setPoint(0.666,0.75);
                getChildren().addAll(points[5]);
        }

    }
    public Circle setPoint(double x, double y){
        Circle point = new Circle();
        point.relocate(diceSize*x, diceSize*y);
        point.setRadius(diceSize/10);
        point.setFill(Color.valueOf("#000000"));
        return point;
    }

}
