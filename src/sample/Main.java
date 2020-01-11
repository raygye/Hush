package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        root.getStylesheets().add("sample/styles.css");
        primaryStage.setTitle("Hush");
        primaryStage.setScene(new Scene(root, 500, 800));
        primaryStage.show();
    }

    public static void main(String[] args) throws IOException {
        Process process = Runtime.getRuntime().exec("g++ src/sample/enigma.cpp -o enigma");
        InputStream errStream = process.getErrorStream();
        Scanner scanner = new Scanner(errStream);
        String err = "";
        while (scanner.hasNextLine()) {
            err += scanner.nextLine();
        }
        System.out.println(err);

        launch(args);
    }
}
