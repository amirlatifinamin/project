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
    private double x, yBase;
    private TriangleType triangleType;
    private Controller controller;
    private int numberOfPieces;
    private PieceType typeOfPieces;
    private Graveyard graveyard;
    private DiceBoard diceBoard;
    private Stockpile redStockPile;
    private Stockpile whiteStockPile;
    private Group piecesGroup;
    private String initialColor;


    public Triangle(double x, double y, TriangleType type, Controller controller, int number, Graveyard graveyard,
                    DiceBoard diceBoard, Stockpile redStockPile, Stockpile whiteStockPile, Group pieces) {
        this.piecesGroup = pieces;
        this.whiteStockPile = whiteStockPile;
        this.redStockPile = redStockPile;
        this.graveyard = graveyard;
        this.diceBoard = diceBoard;
        this.typeOfPieces = null;
        this.number = number;
        this.controller = controller;
        this.x = x;
        this.yBase = type.direction == 1 ? 0 : 11 * triangleBase;
        this.triangleType = type;
        this.numberOfPieces = 0;
        relocate(x, y);
        if (type.direction == 1) {
            triangle.getPoints().addAll(0.0, 0.0, triangleBase, 0.0, triangleBase / 2, 5 * triangleBase);
        } else {
            triangle.getPoints().addAll(0.0, 5 * triangleBase, triangleBase, 5 * triangleBase, triangleBase / 2, 0.0);
        }
        this.initialColor = (type == TriangleType.downRed || type == TriangleType.upRed) ?
                "#410B00" : "#A45B2B";
        triangle.setFill(Color.valueOf(this.initialColor));
        getChildren().addAll(triangle);

    }

    public Group initializePieces(int initialNumOfPieces, Group pieces) {
        Group piecesGroup = new Group();
        double firstY = triangleType.direction == 1 ? 0 : 11 * triangleBase;
        if (this.number == 0 || this.number == 11 || this.number == 16 || this.number == 18) {
            typeOfPieces = PieceType.white;
        }
        if (this.number == 5 || this.number == 7 || this.number == 12 || this.number == 23){
            typeOfPieces = PieceType.red;
        }
        for (int index = 0; index < initialNumOfPieces; index++) {
            Piece piece = controller.makeHandlePiece(x, firstY + index * triangleType.direction * triangleBase,
                    typeOfPieces, number, pieces);
            this.pieces.add(piece);
            pieces.getChildren().addAll(piece);
            numberOfPieces++;
        }
        return piecesGroup;
    }

    public double findCoordinationOfNewPiece() {
        double yPosition;
        if (numberOfPieces < 5) {
            yPosition = numberOfPieces * triangleBase * this.triangleType.direction + this.yBase;
        } else {
            yPosition = 4 * triangleBase * this.triangleType.direction + this.yBase;
        }
        return yPosition;
    }

    public void addPiece(Piece piece){
        pieces.add(piece);
        numberOfPieces++;
        rearrangePieces();
        if (numberOfPieces==1){
            this.typeOfPieces = piece.getPieceType();
        }
    }

    public void removePiece(Piece piece){
        pieces.remove(piece);
        numberOfPieces--;
        rearrangePieces();
        if (numberOfPieces==0){
            this.typeOfPieces = null;
        }
    }

    public void killPiece(){
        pieces.remove(pieces.get(0));
        numberOfPieces--;
    }

    public void rearrangePieces(){
        if(numberOfPieces > 5){
            for(int index = 0; index < numberOfPieces; index++){
                pieces.get(index).move(x, yBase + (4.0/(numberOfPieces -1))*triangleBase*index*triangleType.direction);
            }
        }
        else {
            for(int index = 0; index < numberOfPieces; index++){
                pieces.get(index).move(x, yBase + (triangleBase*index*triangleType.direction));
            }
        }

    }

    public void changeColor(PieceType pieceType){
        if(pieceType == typeOfPieces || typeOfPieces==null || numberOfPieces==1){
            triangle.setFill(Color.valueOf("#81FF86"));
        }
    }

    public void changeFirstPieceColor(){
        pieces.get(pieces.size()-1).changeColor();
    }

    public void resetFirstPieceColor(){
        pieces.get(pieces.size()-1).resetColor();
        if(numberOfPieces > 1){
            pieces.get(pieces.size()-2).resetColor();
        }
    }

    public void resetColor(){
        triangle.setFill(Color.valueOf(this.initialColor));
    }

    public PieceType getTypeOfPieces() {
        return typeOfPieces;
    }

    public int getNumberOfPieces() {
        return numberOfPieces;
    }

    public ArrayList<Piece> getPieces() {
        return pieces;
    }

    public int getNumber() {
        return number;
    }
}
