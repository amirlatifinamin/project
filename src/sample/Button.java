package sample;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class Button extends Pane {
    public Rectangle button;
    public Text text;
    public static final double fontSize = 20;
    public static final double width = 200;
    public static final double height = 75;
    public static final double arc = 20;
    private boolean isActive;

    public Button (double x, double y, String color, String val, boolean active){
        relocate(x, y);
        button = rectangleInit(0, 0, color);
        getChildren().addAll(button);
        text = textInit(width/4, height/3, val);
        getChildren().addAll(text);
        if (active)
            activeButton();
        else
            deactiveButton();
    }

    private Rectangle rectangleInit (double x, double y, String color) {
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
        temp.setFont(Font.font(fontSize));
        temp.setFill(Color.valueOf("#FFFFFF"));
        return temp;
    }

    public void deactiveButton (){
        button.setFill(Color.valueOf("#606060"));
        isActive = false;
    }

    public void activeButton (){
        button.setFill(Color.valueOf("#1155DC"));
        isActive = true;
    }

    public boolean isActive() {
        return isActive;
    }
}
