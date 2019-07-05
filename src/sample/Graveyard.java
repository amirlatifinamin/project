package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

import static sample.LayoutCreator.triangleBase;

public class Graveyard extends Pane {
    private Rectangle graveyard = new Rectangle();
    private ArrayList<Piece> pieces = new ArrayList<>();
    private int numberOfPieces;
    private double xBase, yBase;
    private int sumOfRedKilled = 0;
    private int sumOfWhiteKilled = 0;
    private ScoreBoard scoreboard;

    public Graveyard(ScoreBoard s) {
        scoreboard = s;
        numberOfPieces = 0;
        xBase = 7 * triangleBase;
        yBase = 5.5 * triangleBase;
        graveyard.setHeight(12 * triangleBase);
        graveyard.setWidth(0.5 * triangleBase);
        graveyard.setFill(Color.valueOf("#1E0000"));
        relocate(7.25 * triangleBase, 0);
        getChildren().addAll(graveyard);
    }

    public void addPiece(Piece piece) {
        pieces.add(piece);
        if(piece.getPieceType()==PieceType.red){
            sumOfRedKilled++;
            scoreboard.updateScores(PieceType.red);
        }
        else {
            sumOfWhiteKilled++;
            scoreboard.updateScores(PieceType.white);
        }
        numberOfPieces++;
        rearrangePieces();
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
        numberOfPieces--;
        rearrangePieces();
    }

    public void rearrangePieces() {
        switch (numberOfPieces){
            case 1:{
                pieces.get(0).move(xBase, yBase);
                break;
            }
            case 2:{
                pieces.get(0).move(xBase, yBase+0.5*triangleBase);
                pieces.get(1).move(xBase, yBase-0.5*triangleBase);
                break;
            }
            case 3:{
                pieces.get(0).move(xBase, yBase+triangleBase);
                pieces.get(1).move(xBase, yBase);
                pieces.get(2).move(xBase, yBase-triangleBase);
                break;
            }
            case 4:{
                pieces.get(0).move(xBase, yBase+1.5*triangleBase);
                pieces.get(1).move(xBase, yBase+0.5*triangleBase);
                pieces.get(2).move(xBase, yBase-0.5*triangleBase);
                pieces.get(3).move(xBase, yBase-1.5*triangleBase);
            }
        }

    }

    public int getSumOfRedKilled() {
        return sumOfRedKilled;
    }

    public int getSumOfWhiteKilled() {
        return sumOfWhiteKilled;
    }
}
