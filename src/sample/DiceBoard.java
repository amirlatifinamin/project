package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Random;

public class DiceBoard{
    public static final double height = 220;
    public static final double width = ScoreBoard.scoreBoardWidth;
    private static double fontSize = 20;
    public final double X = 215;
    public final double Y = 300;
    public final double arc = 30;
    public Dice dice1 = new Dice(30,10);
    public Dice dice2 = new Dice(100,10);
    public Dice dice3 = new Dice(30, 90);
    public Dice dice4 = new Dice(100, 90);
    public DiceController diceController = new DiceController(45, 160, dice1, dice2);
    public Pane layout = new Pane();
    public int numOfMovements;
    public boolean doubledDice;
    public boolean newDiceRoll;
    private Random randGen = new Random();
    private ScoreBoard scoreBoard;
    private PieceType currentUser;
    private Rectangle turnTile = new Rectangle();
    private Text turnTitle = new Text();
    private boolean firstRoll;
    private boolean canRollDice;

//    private int numOfRolls = 10;

    public Pane layoutCreator (double x, double y, ScoreBoard s){
        scoreBoard = s;
        currentUser = PieceType.red;
        layout.relocate(x, y);
        Rectangle rect1 = new Rectangle();
        Rectangle rect2 = new Rectangle();
        rect1.setHeight(Y);
        rect1.setWidth(X);
        rect1.setFill(Color.valueOf("#21242E"));
        rect1.relocate(0, 0);
        layout.getChildren().addAll(rect1);
        rect2.setHeight(height);
        rect2.setWidth(width);
        rect2.smoothProperty().setValue(true);
        rect2.setFill(Color.valueOf("#A36525"));
        rect2.relocate(0, 0);
        rect2.setArcWidth(arc);
        rect2.setArcHeight(arc);
        layout.getChildren().addAll(rect2);
        Rectangle dice3Shadow = new Rectangle();
        Rectangle dice4Shadow = new Rectangle();
        dice3Shadow.relocate(30, 90);
        dice4Shadow.relocate(100, 90);
        dice3Shadow.setFill(Color.valueOf("#5d2f00"));
        dice4Shadow.setFill(Color.valueOf("#5d2f00"));
        dice3Shadow.setArcHeight(20);
        dice3Shadow.setArcWidth(20);
        dice4Shadow.setArcWidth(20);
        dice4Shadow.setArcHeight(20);
        dice3Shadow.setHeight(60);
        dice3Shadow.setWidth(60);
        dice4Shadow.setWidth(60);
        dice4Shadow.setHeight(60);
        diceController.setOnMouseClicked(event -> {
            if (firstRoll){
                dice1.rollDice();
                dice2.rollDice();
                if (dice1.getDiceValue() > dice2.getDiceValue()){
                    currentUser = PieceType.red;
                    dice1.newDice();
                    dice2.newDice();
                    turnTile.setFill(Color.valueOf("#770000"));
                    firstRoll = false;
                    canRollDice = true;
                } else if (dice1.getDiceValue() < dice2.getDiceValue()){
                    currentUser = PieceType.white;
                    dice1.newDice();
                    dice2.newDice();
                    turnTile.setFill(Color.valueOf("#F2CE7C"));
                    firstRoll = false;
                    canRollDice = true;
                }
            } else if (canRollDice) {
                canRollDice = false;
                diceController.lockkey();
                dice1.newDice();
                dice2.newDice();
                dice3.newDice();
                dice4.newDice();
                try {
                    layout.getChildren().remove(dice3);
                    layout.getChildren().remove(dice4);
                } catch (Error e) {

                }
                dice1.rollDice();
                dice2.rollDice();
                numOfMovements = 2;
                doubledDice = false;
                newDiceRoll = true;
                if (dice1.getDiceValue() == dice2.getDiceValue()) {
                    dice3.setDiceSide(dice1.getDiceValue());
                    dice4.setDiceSide(dice1.getDiceValue());
                    layout.getChildren().addAll(dice3, dice4);
                    numOfMovements = 4;
                    doubledDice = true;
                }
                scoreBoard.updateScores(currentUser);
            }
        });
        layout.getChildren().addAll(dice1, dice2, diceController, dice4Shadow, dice3Shadow);
        layout.setPrefSize(400,350);
        numOfMovements = 0;
        doubledDice = false;
        turnTitle.relocate(200,20);
        turnTitle.setText("Turn");
        turnTitle.setFont(Font.font(fontSize));
        turnTitle.setFill(Color.valueOf("#000000"));
        layout.getChildren().addAll(turnTitle);
        turnTile.relocate(170, 60);
        turnTile.setHeight(150);
        turnTile.setWidth(110);
        turnTile.setArcHeight(20);
        turnTile.setArcWidth(20);
        turnTile.setFill(Color.valueOf("#000000"));
        layout.getChildren().addAll(turnTile);
        firstRoll = true;
        dice1.firstRollDice(PieceType.red);
        dice2.firstRollDice(PieceType.white);
        canRollDice = false;
        // Movement test key
//        Rectangle testMove = new Rectangle();
//        testMove.setWidth(100);
//        testMove.setHeight(50);
//        testMove.setArcHeight(arc);
//        testMove.setArcWidth(arc);
//        testMove.setFill(Color.valueOf("#fc1300"));
//        testMove.relocate(45, 220);
//        testMove.setOnMouseClicked(event -> {
//            int val = randGen.nextInt(6) + 1;
//            this.makeMovement(val);
//        });
//        layout.getChildren().addAll(testMove);
        return layout;
    }

    public boolean canMove (int val){
        if (numOfMovements == 4)  {
            if (dice1.getDiceValue() + dice2.getDiceValue() + dice3.getDiceValue() + dice4.getDiceValue() == val){
                numOfMovements = 0;
                changeTurn();
                dice1.useDice();
                dice2.useDice();
                dice3.useDice();
                dice4.useDice();
                return true;
            } else if (dice2.getDiceValue() + dice3.getDiceValue() + dice4.getDiceValue()  == val) {
                numOfMovements = 1;
                changeTurn();
                dice2.useDice();
                dice3.useDice();
                dice4.useDice();
                return true;
            } else if (dice3.getDiceValue() + dice4.getDiceValue() == val) {
                numOfMovements = 2;
                changeTurn();
                dice3.useDice();
                dice4.useDice();
                return true;
            } else if (dice4.getDiceValue() == val){
                numOfMovements = 3;
                changeTurn();
                dice4.useDice();
                return true;
            }
        } else if (numOfMovements == 3){
            if (dice1.getDiceValue() + dice2.getDiceValue() + dice3.getDiceValue() == val){
                numOfMovements = 0;
                changeTurn();
                dice1.useDice();
                dice2.useDice();
                dice3.useDice();
                return true;
            } else if (dice2.getDiceValue() + dice3.getDiceValue() == val) {
                numOfMovements = 1;
                changeTurn();
                dice2.useDice();
                dice3.useDice();
                return true;
            } else if (dice3.getDiceValue() == val) {
                numOfMovements = 2;
                changeTurn();
                dice3.useDice();
                return true;
            }
        }else if (numOfMovements > 0){
            if (numOfMovements == 2 && dice1.getDiceValue() + dice2.getDiceValue() == val){
                numOfMovements = 0;
                changeTurn();
                dice1.useDice();
                dice2.useDice();
                return true;
            } else if (dice1.getDiceValue() == val && !dice1.diceUsed){
                numOfMovements -= 1;
                if (numOfMovements == 0){
                    changeTurn();
                }
                dice1.useDice();
//                System.out.println(numOfMovements);
                return true;
            } else if (dice2.getDiceValue() == val && !dice2.diceUsed) {
                numOfMovements -= 1;
                if (numOfMovements == 0){
                    changeTurn();
                }
                dice2.useDice();
//                System.out.println(numOfMovements);
                return true;
            } else if (dice3.getDiceValue() == val && !dice3.diceUsed) {
                numOfMovements -= 1;
                if (numOfMovements == 0){
                    changeTurn();
                }
                dice3.useDice();
//                System.out.println(numOfMovements);
                return true;
            } else if (dice4.getDiceValue() == val && !dice4.diceUsed) {
                numOfMovements -= 1;
                if (numOfMovements == 0){
                    changeTurn();
                }
                dice4.useDice();
//                System.out.println(numOfMovements);
                return true;
            }
        }
        return false;
    }

    public int getSumValueOfDices (){
        if (newDiceRoll) {
            newDiceRoll = false;
            if (doubledDice)
                return dice1.getDiceValue() + dice2.getDiceValue() + dice3.getDiceValue() + dice4.getDiceValue();
            else
                return dice1.getDiceValue() + dice2.getDiceValue();
        } else
            return 0;
    }

    public PieceType getCurrentUser() {
        return currentUser;
    }

    public void changeTurn (){
        if (currentUser == PieceType.red) {
            currentUser = PieceType.white;
            turnTile.setFill(Color.valueOf("#F2CE7C"));
        } else {
            currentUser = PieceType.red;
            turnTile.setFill(Color.valueOf("#770000"));
        }
        canRollDice = true;
        diceController.unlockKey();
    }
}
