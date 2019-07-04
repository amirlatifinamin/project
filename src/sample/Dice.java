package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class Dice extends Pane {
    private long waitTime = 50; //Second
    public DiceSide[] diceSides = new DiceSide[6];
    private Random randGen = new Random();
    public int currentSide;
    public Rectangle rectangle = new Rectangle();
    public static double diceSize = 120;

    public Dice (double x, double y) {
        rectangle.relocate(0,0);
        rectangle.setHeight(diceSize);
        rectangle.setWidth(diceSize);
        rectangle.setFill(Color.valueOf("#C3C3C3"));
        getChildren().addAll(rectangle);
        relocate(x, y);
        for (int sideNum = 0; sideNum < 6; sideNum += 1) {
            diceSides[sideNum] = new DiceSide(sideNum + 1, 0, 0);
        }
        currentSide = 0;
        getChildren().addAll(diceSides[currentSide]);
    }

    public void rollDice () {
        int randomSide;
        getChildren().remove(diceSides[currentSide]);
        randomSide = randGen.nextInt(6);
        currentSide = randomSide;
        getChildren().addAll(diceSides[randomSide]);
        try{
            Thread.sleep(waitTime);
            System.out.println("*");
        }catch(InterruptedException ex){
            System.out.println("#");
        }
        System.out.println("Current Side: " + currentSide);
    }

    public int getDiceValue (){
        return currentSide;
    }



}