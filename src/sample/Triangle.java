package sample;

import javafx.scene.Group;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import java.util.ArrayList;

import static sample.LayoutCreator.triangleBase;

public class Triangle extends Pane {
    private Polygon triangle = new Polygon();
    private int number;
    private ArrayList<Piece> pieces = new ArrayList<>();
    private double x,y;
    private Group piecesGroup = new Group();
    private TriangleType triangleType;


    public Triangle(double x, double y, TriangleType type, int number, int initialNumOfPieces) {
        this.number = number;
        this.x = x;
        this.triangleType = type;
        relocate(x, y);
        if(type.direction == 1){
            triangle.getPoints().addAll(0.0,0.0, triangleBase, 0.0, triangleBase/2 , 5*triangleBase);
        }
        else{
            triangle.getPoints().addAll(0.0,5*triangleBase, triangleBase, 5*triangleBase, triangleBase/2 , 0.0);
        }
        triangle.setFill((type == TriangleType.downRed|| type == TriangleType.upRed)?
                Color.valueOf("#410B00") : Color.valueOf("#707070"));
        getChildren().addAll(triangle);

    }

    public Group initializePieces(int initialNumOfPieces){
        Group pieces = new Group();
        double firstY = triangleType.direction == 1? 0:11*triangleBase;
        PieceType pieceType;
        if(this.number == 0 || this.number == 11 || this.number == 16 || this.number == 18 ){
            pieceType = PieceType.white;
        }
        else {
            pieceType = PieceType.red;
        }
        for(int index = 0; index<initialNumOfPieces; index++){
            Piece piece = new Piece(pieceType);
            pieces.getChildren().addAll(piece);
            piece.move(x, firstY + index*triangleType.direction*triangleBase);
            System.out.println(5*triangleBase - index*triangleType.direction*triangleBase);
        }
        return pieces;
    }

}
