package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScoreBoard extends Pane {
    public final double arc = 15;
    private static final double FontSize = 10;
    private static final double fieldDistance = 20;
    private static final double fieldWidth = 70;
    private static final double fieldHeight = 30;
    public static final double scoreBoardWidth = 3 * fieldWidth + 4 * fieldDistance;
    public static final double scoreBoardHeight = 6 * fieldHeight + 7 * fieldDistance;
    private Stockpile redPile;
    private Stockpile whitePile;
    private DiceBoard diceBoard;
    private UserStatistics redStats;
    private Graveyard graveyard;
    private UserStatistics whiteStats;
    private Rectangle background;
    private Rectangle textBackground;
    private Text user;
    private Text numOfKilledTXT;
    private Text numOfPiecesInPileTXT;
    private Text winsTXT;
    private Text failuresTXT;
    private Text diceSumTXT;
    private Text redTitle;
    private Text redNumOfKilledTXT;
    private Text redNumOfPiecesInPileTXT;
    private Text redWinsTXT;
    private Text redFailuresTXT;
    private Text redDiceSumTXT;
    private Text whiteTitle;
    private Text whiteNumOfKilledTXT;
    private Text whiteNumOfPiecesInPileTXT;
    private Text whiteWinsTXT;
    private Text whiteFailuresTXT;
    private Text whiteDiceSumTXT;
    private double xLoc;
    private double yLoc;

    public ScoreBoard(double x, double y){
        relocate(x, y);
        xLoc = x;
        yLoc = y;
        double currentX , currentY;
        background = rectangleInit(0,0, scoreBoardWidth, scoreBoardHeight, "#A36525");
        getChildren().addAll(background);
        for(int numOfField = 0; numOfField < 18; numOfField += 1) {
            currentX = fieldDistance + (numOfField % 3) * (fieldWidth + fieldDistance);
            currentY = fieldDistance + (numOfField / 3) * (fieldHeight + fieldDistance);
            textBackground = rectangleInit(currentX, currentY, fieldWidth, fieldHeight, "#5d2f00");
            getChildren().addAll(textBackground);
        }
        createTexts();
        getChildren().addAll(user);
        getChildren().addAll(redTitle);
        getChildren().addAll(whiteTitle);
        getChildren().addAll(winsTXT);
        getChildren().addAll(redWinsTXT);
        getChildren().addAll(whiteWinsTXT);
        getChildren().addAll(failuresTXT);
        getChildren().addAll(redFailuresTXT);
        getChildren().addAll(whiteFailuresTXT);
        getChildren().addAll(numOfKilledTXT);
        getChildren().addAll(redNumOfKilledTXT);
        getChildren().addAll(whiteNumOfKilledTXT);
        getChildren().addAll(numOfPiecesInPileTXT);
        getChildren().addAll(redNumOfPiecesInPileTXT);
        getChildren().addAll(whiteNumOfPiecesInPileTXT);
        getChildren().addAll(diceSumTXT);
        getChildren().addAll(redDiceSumTXT);
        getChildren().addAll(whiteDiceSumTXT);
    }

    private void createTexts (){
        double currentX , currentY;
        for(int numOfField = 0; numOfField < 18; numOfField += 1){
            currentX = fieldDistance + (numOfField % 3) * (fieldWidth + fieldDistance);
            currentY = fieldDistance + (numOfField / 3) * (fieldHeight + fieldDistance);
            switch(numOfField){
                case 0:
                    user = textInit(currentX, currentY , "User: ");
//                    getChildren().addAll(user);
                    break;
                case 1:
                    redTitle = textInit(currentX, currentY, "Red");
//                    getChildren().addAll(redTitle);
                    break;
                case 2:
                    whiteTitle = textInit(currentX, currentY, "White");
//                    getChildren().addAll(whiteTitle);
                    break;
                case 3:
                    winsTXT = textInit(currentX, currentY, "Wins: ");
//                    getChildren().addAll(winsTXT);
                    break;
                case 4:
                    redWinsTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(redWinsTXT);
                    break;
                case 5:
                    whiteWinsTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(whiteWinsTXT);
                    break;
                case 6:
                    failuresTXT = textInit(currentX, currentY, "Looses: ");
//                    getChildren().addAll(failuresTXT);
                    break;
                case 7:
                    redFailuresTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(redFailuresTXT);
                    break;
                case 8:
                    whiteFailuresTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(whiteFailuresTXT);
                    break;
                case 9:
                    numOfKilledTXT = textInit(currentX, currentY, "Killed: ");
//                    getChildren().addAll(numOfKilledTXT);
                    break;
                case 10:
                    redNumOfKilledTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(redNumOfKilledTXT);
                    break;
                case 11:
                    whiteNumOfKilledTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(whiteNumOfKilledTXT);
                    break;
                case 12:
                    numOfPiecesInPileTXT = textInit(currentX, currentY, "Finished: ");
//                    getChildren().addAll(numOfPiecesInPileTXT);
                    break;
                case 13:
                    redNumOfPiecesInPileTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(redNumOfPiecesInPileTXT);
                    break;
                case 14:
                    whiteNumOfPiecesInPileTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(whiteNumOfPiecesInPileTXT);
                    break;
                case 15:
                    diceSumTXT = textInit(currentX, currentY, "Dice Sum:");
//                    getChildren().addAll(diceSumTXT);
                    break;
                case 16:
                    redDiceSumTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(redDiceSumTXT);
                    break;
                case 17:
                    whiteDiceSumTXT = textInit(currentX, currentY, "0");
//                    getChildren().addAll(whiteDiceSumTXT);
                    break;
            }

        }

    }

    public void init (Stockpile rp, Stockpile wp, DiceBoard d, UserStatistics r, UserStatistics w, Graveyard g){
        graveyard = g;
        redPile = rp;
        whitePile = wp;
        diceBoard = d;
        redStats = r;
        whiteStats = w;
    }

    public void updateScores(PieceType t){
        if (t == PieceType.red)
            redStats.updateStats(diceBoard.getSumValueOfDices(), redPile.getNumOfPiecesInPile(), graveyard.getSumOfRedKilled());
        else
            whiteStats.updateStats(diceBoard.getSumValueOfDices(), whitePile.getNumOfPiecesInPile(), graveyard.getSumOfWhiteKilled());
        textRefresh();
    }

    public void newGaeme(PieceType winner){
        if (winner == PieceType.red){
            redStats.newRound(true);
            whiteStats.newRound(false);
        } else {
            redStats.newRound(false);
            whiteStats.newRound(true);
        }
        textRefresh();
    }

    private Rectangle rectangleInit (double x, double y, double width, double height, String color) {
        Rectangle temp = new Rectangle();
        temp.relocate(x, y);
        temp.setWidth(width);
        temp.setHeight(height);
        temp.setArcWidth(arc);
        temp.setArcHeight(arc);
        temp.setFill(Color.valueOf(color));
        return temp;
    }

    private Text textInit (double x, double y, String val){
        Text temp = new Text();
        temp.setText(val);
        temp.relocate(x + fieldDistance/2, y + fieldDistance / 2);
        temp.setFont(Font.font(FontSize));
        temp.setFill(Color.valueOf("#FFFFFF"));
        return temp;
    }

    private void textRefresh(){
        redDiceSumTXT.setText(Integer.toString(redStats.getSumOfDicesValue()));
        redNumOfPiecesInPileTXT.setText(Integer.toString(redStats.getNumOfPiecesInPile()));
        redFailuresTXT.setText(Integer.toString(redStats.getNumOfFailures()));
        redWinsTXT.setText(Integer.toString(redStats.getNumOfWins()));
        redNumOfKilledTXT.setText(Integer.toString(redStats.getNumOfKilledPieces()));
        whiteDiceSumTXT.setText(Integer.toString(whiteStats.getSumOfDicesValue()));
        whiteNumOfPiecesInPileTXT.setText(Integer.toString(whiteStats.getNumOfPiecesInPile()));
        whiteFailuresTXT.setText(Integer.toString(whiteStats.getNumOfFailures()));
        whiteWinsTXT.setText(Integer.toString(whiteStats.getNumOfWins()));
        whiteNumOfKilledTXT.setText(Integer.toString(whiteStats.getNumOfKilledPieces()));
    }

    public void reset(){
        redNumOfKilledTXT.setText("0");
        redWinsTXT.setText("0");
        redFailuresTXT.setText("0");
        redNumOfPiecesInPileTXT.setText("0");
        redDiceSumTXT.setText("0");
        whiteNumOfKilledTXT.setText("0");
        whiteWinsTXT.setText("0");
        whiteFailuresTXT.setText("0");
        whiteNumOfPiecesInPileTXT.setText("0");
        whiteDiceSumTXT.setText("0");
    }

}
