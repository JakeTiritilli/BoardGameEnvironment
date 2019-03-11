package memory.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class Controller {


    @FXML
    private ImageView iv1;
    @FXML
    private Button button1;

    @FXML
    private ImageView iv2;
    @FXML
    private Button button2;

    @FXML
    private ImageView iv3;
    @FXML
    private Button button3;

    @FXML
    private ImageView iv4;
    @FXML
    private Button button4;

    public void cardClicked1(){
        iv1.setVisible(true);
        button1.setVisible(false);
        }

    public void cardClicked2(){
        iv2.setVisible(true);
        button2.setVisible(false);
    }

    public void cardClicked3(){
        iv3.setVisible(true);
        button3.setVisible(false);
    }

    public void cardClicked4(){
        iv4.setVisible(true);
        button4.setVisible(false);
    }



}
