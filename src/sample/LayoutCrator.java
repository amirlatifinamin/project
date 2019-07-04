package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class LayoutCrator {
    public static int row = 2;
    public static int column = 12;
    public static double triangleBase = 50;
    private double boardWidth = 15 * triangleBase;
    private double boardHeight = 12 * triangleBase;
    private Group triangles = new Group();


    public Pane layout() {
        Pane board = new Pane();
        board.setPrefSize(boardWidth,boardHeight);
        board.getChildren().addAll(triangles);
        board.setStyle("-fx-background-color: #21242E");

        for (int index = 1; index <= column; index++) {
            Triangle upTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, 0.0,
                    index % 2 == 0 ? TriangleType.upRed : TriangleType.upWhite);
            Triangle downTriangle = new Triangle((index <= column / 2 ? index : index + 1) * triangleBase, boardHeight/2 + triangleBase,
                    index % 2 == 0 ? TriangleType.downWhite : TriangleType.downRed);
            triangles.getChildren().addAll(upTriangle, downTriangle);
        }
        return board;
    }

}
