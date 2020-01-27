package main;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.Objects;

public class Main extends Application {

    public static Scene scene;

    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("resources/app.fxml")));
            scene = new Scene(root);
            scene.getStylesheets().add("resources/app.css");
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.setTitle("Technical Analysis Project");
            stage.show();
        } catch (Exception e) {
            System.out.print(e);
        }
    }
}