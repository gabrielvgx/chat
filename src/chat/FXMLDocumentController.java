/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author M
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private Label label;
    @FXML
    private AnchorPane painelSala;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @FXML
    protected void eventoCriarSala(ActionEvent e) {
        double x = 0;
        double y = 0;
        for (int i = 0; i < painelSala.getChildren().size() - 1; i++) {
            if (painelSala.getChildren().get(i) instanceof RadioButton) {
                if (((RadioButton) (painelSala.getChildren().get(i))).getText().equals("ALP")) {
                    x = ((RadioButton) (painelSala.getChildren().get(i))).getLayoutX();
                    y = ((RadioButton) (painelSala.getChildren().get(i))).getLayoutY();
                }
            }
        }
        for (int i = 0; i < 7; i++) {
            RadioButton r1 = new RadioButton("OL");
            r1.setTranslateX(x);
            y += r1.getLayoutY() + 20;
            x += r1.getLayoutX();
            r1.setTranslateY(y);
            painelSala.getChildren().add(r1);
            System.out.println(x);
            System.out.println(y);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
