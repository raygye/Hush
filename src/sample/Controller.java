package sample;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class Controller {
    int sel=-1;
    public Button button;
    public Button encode;
    public Button decode;
    public Text select;
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
    public void submit() {

    }
}
