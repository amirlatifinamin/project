package sample;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import static sample.LayoutCreator.triangleBase;

public class ControlPanel extends Pane {
    public static final double width = 100;
    public static final double height = 12*triangleBase;
    public static final double arc = 20;
//    public Button pauseButton = new Button((width - Button.width)/2, height / 5, "#1155DC", "Pause", true);
//    public Button restartButton = new Button((width - Button.width)/2, height * 2 / 5, "#1155DC", "Restart", true);
//    public Button resumeButton = new Button((width - Button.width)/2, height * 3 / 5, "#1155DC", "Resume", false);

    public static Button pauseButton = new Button((width - Button.width)/2, 4*triangleBase, "#1155DC", "Pause", true);
    public static Button restartButton = new Button((width - Button.width)/2, 5.5*triangleBase, "#1155DC", "Restart", true);
    public static Button resumeButton = new Button((width - Button.width)/2, 7*triangleBase, "#1155DC", "Resume", false);
    public static Button timer = new Button((width - Button.width)/2, 8.5*triangleBase, "#1155DC", "00:00", false);
    public static boolean finished;
    public ControlPanel (double x, double y){
        finished = false;
        relocate(x, y);
        getChildren().addAll(pauseButton, restartButton, resumeButton, timer);
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

    public static void updateTimer(int counter){
        int sec = counter % 60;
        int min = counter / 60;
        String sec_t;
        String min_t;
        String time;
        if (sec < 10)
            sec_t = "0" + sec;
        else
            sec_t = Integer.toString(sec);
        if (min < 10)
            min_t = "0" + min;
        else
            min_t = Integer.toString(min);
        time = min_t + ":" + sec_t;
        timer.text.setText(time);
//        System.out.println(time);
    }

    public static void gameFinished(){
        finished = true;
        ControlPanel.timer.text.setText("Extend");
        ControlPanel.timer.activeButton();
        ControlPanel.resumeButton.deactiveButton();
        ControlPanel.pauseButton.deactiveButton();

    }


}
