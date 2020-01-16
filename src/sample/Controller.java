package sample;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;

public class Controller {
    int sel=0;
    public Button button;
    public Button encode;
    public Button decode;
    public Button fileSelect;
    public Text select;
    public TextField reveal;
    public TextField read;
    public PasswordField key;
    public Stage primaryStage;
    public File file = null;
    FileChooser fileChooser = new FileChooser();

    public void sel1() {
        decode.setStyle(null);
        fileSelect.setStyle(null);
        System.out.println("Encode");
        encode.setStyle("-fx-background-color: silver");
        sel = 0;
    }
    public void sel2() {
        encode.setStyle(null);
        fileSelect.setStyle(null);
        System.out.println("Decode");
        sel = 1;
        decode.setStyle("-fx-background-color: silver");
    }
    public void file() {
        read.setText("");
        if (sel == 0) {
            fileChooser.setTitle("Open File To Be Encoded");
        }
        else {
            fileChooser.setTitle("Open File To Be Decoded");
        }
        file = fileChooser.showOpenDialog(primaryStage);
    }
    public void submit() throws IOException {
        reveal.setVisible(false);
        System.out.println("Submit");
        String text = read.getText().replace(' ', 'λ');
        String line;
        OutputStream stdin;
        InputStream stdout;

        //stdin/out
        Process process = Runtime.getRuntime().exec("./enigma.out");
        stdin = process.getOutputStream ();
        stdout = process.getInputStream ();
        if (!read.getText().equals("")) {
            // writing
            line = sel + "\n" + key.getText() + "\n" + text;
            stdin.write(line.getBytes());
            stdin.write("\nsysclose29110391039484horse".getBytes());
        }
        else {
            //writing
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            byte[] bytes = Files.readAllBytes(file.toPath());
            outputStream.write((sel+ "\n").getBytes());
            outputStream.write((key.getText()+ "\n").getBytes());
            outputStream.write(bytes);
            outputStream.write("\nsysclose29110391039484horse".getBytes());
            stdin.write(outputStream.toByteArray());
        }

        stdin.close();

        // clean up if any output in stdout
        BufferedReader readOut =
                new BufferedReader (new InputStreamReader (stdout));
        if (!read.getText().equals("")) {
            while ((line = readOut.readLine()) != null) {
                reveal.setVisible(true);
                line = line.replace('λ', ' ');
                reveal.setText(line);
            }
        }
        else {
            PrintWriter writer = new PrintWriter(file.getName(), "UTF-8");
            while ((line = readOut.readLine()) != null) {
                line = line.replace('λ', ' ');
                writer.println(line+"\n");
            }
            writer.close();
        }
        readOut.close();
        System.out.println("Success");
        file = null;
    }
}
