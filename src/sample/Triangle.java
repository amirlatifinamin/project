package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static sample.LayoutCrator.triangleBase;

public class Triangle extends Pane {
    private Polygon triangle = new Polygon();

    public Triangle(double x, double y, TriangleType type) {
        relocate(x, y);
        if(type.direction == 1){
            triangle.getPoints().addAll(0.0,0.0, triangleBase, 0.0, triangleBase/2 , 5*triangleBase);
        }
        else{
            triangle.getPoints().addAll(0.0,5*triangleBase, triangleBase, 5*triangleBase, triangleBase/2 , 0.0);
        }
        triangle.setFill((type == TriangleType.downRed|| type == TriangleType.upRed)?
                Color.valueOf("#760D0D") : Color.valueOf("#707070"));
        getChildren().addAll(triangle);

    }
}
