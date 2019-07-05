package sample;

import com.sun.xml.internal.ws.api.ha.StickyFeature;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Stockpile extends Pane {
    private final int numOfPieces = 12;
    public final double pileWidth = 60;
    public final double pileHeight = 250;
    public final double pieceHeight = 50;
    public final double pieceWidth = 14;
    public final double boarderWidth = 3;
    public final double pileX = boarderWidth;
    public final double pileY = boarderWidth;
    public final double arc = 15;
    private PieceType type;
    private int numOfPiecesInPile;
    private int currentPieceX;
    private int currentPieceY;
    private String peiceColor = new String();
    private Rectangle pile = new Rectangle();
    private Rectangle pileBoarder = new Rectangle();
    private Rectangle[] pieces = new Rectangle[numOfPieces];

    public Stockpile(double x, double y, PieceType t){
        this.type = t;
        relocate(x, y);
        if (type.equals(PieceType.red)){
            this.peiceColor = "#770000";
        } else {
            this.peiceColor = "#F2CE7C";
        }
        pile = rectangleInit(pileX, pileY, pileWidth, pileHeight, "#6797A9");
        pileBoarder = rectangleInit(0, 0, pileWidth + 2 * boarderWidth, pileHeight + 2 * boarderWidth, "#000000");
        getChildren().addAll(pileBoarder);
        getChildren().addAll(pile);
        for (int pieceNum = 0; pieceNum < numOfPieces; pieceNum += 1){
 //           pieces[pieceNum] =
        }
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

}
