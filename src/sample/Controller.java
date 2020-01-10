package sample;

import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.io.*;

public class Controller {
    int sel=0;
    public Button button;
    public Button encode;
    public Button decode;
    public Text select;
    public TextField reveal;
    public TextField read;
    public PasswordField key;
    public void sel1() {
        decode.setStyle(null);
        System.out.println("Encode");
        encode.setStyle("-fx-background-color: silver");
        sel = 0;
    }
    public void sel2() {
        encode.setStyle(null);
        System.out.println("Decode");
        sel = 1;
        decode.setStyle("-fx-background-color: silver");
    }
    public void submit() throws IOException {
        System.out.println("Submit");
        String text = read.getText().replace(' ', 'λ');
        String line;
        OutputStream stdin;
        InputStream stdout;

        // execute and grab stdin/stdout
        Process process = Runtime.getRuntime().exec("enigma.exe");
        stdin = process.getOutputStream ();
        stdout = process.getInputStream ();

        // "write" the parms into stdin
        line = sel + " " + text + " " + key.getText();
        stdin.write(line.getBytes());

        stdin.close();

        // clean up if any output in stdout
        BufferedReader readOut =
                new BufferedReader (new InputStreamReader (stdout));
        while ((line = readOut.readLine ()) != null) {
            reveal.setVisible(true);
            line = line.replace('λ', ' ');
            reveal.setText(line);
        }
        readOut.close();

        System.out.println("Success...");
    }
}
