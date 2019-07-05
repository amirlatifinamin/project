package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DiceBoard{
    public final double X = 215;
    public final double Y = 300;
    public final double arc = 30;
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
        rect2.setHeight(160);
        rect2.setWidth(190);
        rect2.smoothProperty().setValue(true);
        rect2.setFill(Color.valueOf("#A36525"));
        rect2.relocate(0, 0);
        rect2.setArcWidth(arc);
        rect2.setArcHeight(arc);
        layout.getChildren().addAll(rect2);
        Dice dice1 = new Dice(30,20);
        Dice dice2 = new Dice(100,20) ;
        DiceController diceController = new DiceController(45, 90, dice1, dice2);
        diceController.setOnMouseClicked(event -> {
            dice1.rollDice();
            dice2.rollDice();
        });
        layout.getChildren().addAll(dice1, dice2, diceController);
        layout.setPrefSize(400,350);
        return layout;
    }

}
