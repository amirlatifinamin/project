package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.awt.*;
import java.io.FileOutputStream;

public class StartPage extends Pane {
    public static double width = LayoutCreator.boardWidth;
    public static double height = LayoutCreator.boardHeight;
    public static double fieldX = 0.53 * width;
    public static double labelX = 0.35 * width;
    public static double buttonHeight = 70;
    public static double buttonWidth = 200;
    private static double FontSize = 20;
    private static double arc = 20;
    public Rectangle background;
    public Rectangle startButton ;
    public Text buttonLabel;
    public TextField gameDurationField ;
    public Text gameDurationLabel ;
    public TextField numOfWinsField ;
    public Text numOfWinsLabel ;
    public TextField playerTimeField ;
    public Text playerTimeLabel ;
    private int roundDuration; //min
    private int turnDuration; //sec
    private int numOfWins;

    public StartPage (){
        background = rectangleInit(0, 0, width, height, "#240D00");
        getChildren().addAll(background);
        startButton = rectangleInit((width - buttonWidth)/2, 2 * buttonHeight, buttonWidth, buttonHeight, "#bb0000");
        buttonLabel = textInit(width/2 - buttonWidth*9/32 , 2.4 * buttonHeight, "Start Game");
        getChildren().addAll(startButton, buttonLabel);
        gameDurationField = textFieldInit(fieldX, 4.2 * buttonHeight);
        gameDurationLabel = textInit(labelX, 4.35 * buttonHeight, "Round Duration: ");
        getChildren().addAll(gameDurationField, gameDurationLabel);
        numOfWinsField = textFieldInit(fieldX, 5.4 * buttonHeight);
        numOfWinsLabel = textInit(labelX, 5.55 * buttonHeight, "Wins Number: ");
        getChildren().addAll(numOfWinsField, numOfWinsLabel);
        playerTimeField = textFieldInit(fieldX, 6.6 * buttonHeight);
        playerTimeLabel = textInit(labelX, 6.75 * buttonHeight, "Turn Duration: ");
        getChildren().addAll(playerTimeField, playerTimeLabel);
        roundDuration = 10;
        turnDuration = 30;
        numOfWins = 3;
    }

    public void startGame (){
        String temp;
        temp = gameDurationField.getText();
        if (temp != "")
            System.out.println(temp);
            try {
                roundDuration = Integer.valueOf(temp);
            } catch (NumberFormatException e){

            }
        temp = playerTimeField.getText();
        if (temp != "")
            try {
                turnDuration = Integer.valueOf(temp);
            } catch (NumberFormatException e){

            }
        temp = numOfWinsField.getText();
        if (temp != "")
            try {
                numOfWins = Integer.valueOf(temp);
            } catch (NumberFormatException e){

            }
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
        temp.relocate(x , y);
        temp.setFont(Font.font(FontSize));
        temp.setFill(Color.valueOf("#FFFFFF"));
        return temp;
    }

    private TextField textFieldInit (double x, double y){
        TextField temp = new TextField();
        temp.relocate(x, y);
        temp.setFont(Font.font(FontSize));
        temp.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue <? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    temp.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        return temp;
    }

    public int getRoundDuration() {
        return roundDuration;
    }
}
