package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Controller controller = new Controller();
        primaryStage.setTitle("Hello World");
        LayoutCreator layoutCreator = new LayoutCreator(controller);
        Scene scene = new Scene(layoutCreator.layout());
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
