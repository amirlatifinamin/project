package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class DiceController extends Pane {
//    private int numOfRolls = 10;
    private Text keyText = new Text();
    private Rectangle key = new Rectangle();
    private double XSIZE = 100;
    private double YSIZE = 50;
    private double XTEXT = 20;
    private double YTEXT = 15;
    private double FontSize = 15;
    private final double arc = 25;

    public DiceController (double x, double y, Dice dice1, Dice dice2){
        relocate(x, y);
        key.relocate(0,0);
        key.setWidth(XSIZE);
        key.setHeight(YSIZE);
        key.setArcHeight(arc);
        key.setArcWidth(arc);
        key.setFill(Color.valueOf("#1155DC"));
        getChildren().addAll(key);
        keyText.setText("Roll Dice");
        keyText.relocate(XTEXT, YTEXT);
        keyText.setFont(Font.font(FontSize));
        keyText.setFill(Color.valueOf("#FFFFFF"));
        getChildren().addAll(keyText);
        setOnMouseClicked(event -> {
            dice1.rollDice();
            dice2.rollDice();
            //}
        });
    }

    public void unlockKey (){
        key.setFill(Color.valueOf("#1155DC"));
    }

    public void lockkey (){
        key.setFill(Color.valueOf("#606060"));
    }
}
