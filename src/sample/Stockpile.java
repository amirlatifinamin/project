package sample;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Stockpile extends Pane {
    public static final int numOfPieces = 15;
    public static final double pieceHeight = 13;
    public static final double pieceWidth = 50;
    public static final double boarderWidth = 3;
    public static final double pileWidth = 56;
    public static final double pileHeight = numOfPieces * (pieceHeight + boarderWidth) + boarderWidth;
    public final double pileX = boarderWidth;
    public final double pileY = boarderWidth;
    public final double arc = 15;
    private PieceType type;
    private int numOfPiecesInPile;
    private double currentPieceX;
    private double currentPieceY;
    private String pieceColor = new String();
    private Rectangle pile;
    private Rectangle pileBoarder;
    private Rectangle[] pieces = new Rectangle[numOfPieces];
    private ScoreBoard scoreBoard;
    // Test
    private Rectangle key;

    public Stockpile(double x, double y, PieceType t, ScoreBoard s){
        scoreBoard = s;
        this.type = t;
        relocate(x, y);
        if (type.equals(PieceType.red)){
            this.pieceColor = "#770000";
        } else {
            this.pieceColor = "#F2CE7C";
        }
        pile = rectangleInit(pileX, pileY, pileWidth, pileHeight, "#1b0069");
        pileBoarder = rectangleInit(0, 0, pileWidth + 2 * boarderWidth, pileHeight + 2 * boarderWidth, "#000000");
        getChildren().addAll(pileBoarder);
        getChildren().addAll(pile);
        currentPieceY  = 2 * boarderWidth;
        currentPieceX = 2 * boarderWidth;
        for (int pieceNum = 0; pieceNum < numOfPieces; pieceNum += 1){
            pieces[pieceNum] = rectangleInit(currentPieceX, currentPieceY, pieceWidth, pieceHeight, pieceColor);
            currentPieceY += pieceHeight + boarderWidth;
        }
        numOfPiecesInPile = 0;
        key = rectangleInit(100, 0, 100, 50, "#1155DC");
        key.setOnMouseClicked(event -> {
            this.addPieceToPile();
        });
        //getChildren().addAll(key);
    }

    private Rectangle rectangleInit (double x, double y, double width, double height, String color){
        Rectangle temp = new Rectangle();
        temp.relocate(x, y);
        temp.setWidth(width);
        temp.setHeight(height);
        temp.setArcWidth(arc);
        temp.setArcHeight(arc);
        temp.setFill(Color.valueOf(color));
        return temp;
    }

    public int getNumOfPiecesInPile(){
        return numOfPiecesInPile;
    }

    public void addPieceToPile(){
        getChildren().addAll(pieces[numOfPiecesInPile]);
        numOfPiecesInPile += 1;
        scoreBoard.updateScores(type);
    }

}
