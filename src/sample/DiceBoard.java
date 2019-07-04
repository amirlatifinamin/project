package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DiceBoard{
    public static double X = 425;
    public static double Y = 500;
//    private int numOfRolls = 10;

    public Pane layoutCreator (double x, double y){
        Pane layout = new Pane();
        layout.relocate(x, y);
        Rectangle rect1 = new Rectangle();
        Rectangle rect2 = new Rectangle();
        rect1.setHeight(Y);
        rect1.setWidth(X);
        rect1.setFill(Color.valueOf("#21242E"));
        rect1.relocate(0, 0);
        layout.getChildren().addAll(rect1);
        rect2.setHeight(170);
        rect2.setWidth(370);
        rect2.smoothProperty().setValue(true);
        rect2.setFill(Color.valueOf("#A36525"));
        rect2.relocate(25, 75);
        layout.getChildren().addAll(rect2);
        Dice dice1 = new Dice(50,100);
        Dice dice2 = new Dice(250,100) ;
        DiceController diceController = new DiceController(170, 250, dice1, dice2);
        diceController.setOnMouseClicked(event -> {
            dice1.rollDice();
            System.out.println("rollDice1 Done");
            dice2.rollDice();
            System.out.println("rollDice2 Done");
        });
        layout.getChildren().addAll(dice1, dice2, diceController);
        layout.setPrefSize(400,350);
        return layout;
    }

}
