/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatLogin;

import Domain.ExcessoesPercistencia;
import Domain.Usuario;
import Service.PercisteUsuario;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author M
 */
public class FXMLLoginController implements Initializable {

    @FXML
    TextArea nomeUsuario;
    @FXML
    Button entrar;
    @FXML
    AnchorPane painelPrincipal;
    @FXML
    Label erroLogar;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeUsuario.setFocusTraversable(false);
    }

    @FXML
    protected void fazerLogin(ActionEvent e) {
        try {
            if (validaLogin()) {
                painelPrincipal.getChildren().clear();
                
                AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                painelPrincipal.getChildren().add(novaTela);
                AnchorPane teste = (AnchorPane)novaTela.getChildren().get(0);
                System.out.println("teste"+teste.getChildren().get(0));
                System.out.println("teste"+teste.getChildren().get(1));
                SplitPane panel = (SplitPane)(((AnchorPane)teste.getChildren().get(0)).getChildren().get(0));
                System.out.println("oii__"+((SplitPane)(((AnchorPane)panel.getItems().get(0)).getChildren().get(0))));
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            System.out.println("fudeu");
        }
    }

    private boolean validaLogin() {
        try {
            PercisteUsuario bdUsuario = new PercisteUsuario();
            Usuario usuario = null;
            usuario = bdUsuario.getUserLogin(nomeUsuario.getText(), null);

            if (usuario == null) {
                bdUsuario.cadastrar(new Usuario(nomeUsuario.getText(), null));

                return true;
            }
            erroLogar.setText("Essa id ja esta em uso!");

            return false;
        } catch (ExcessoesPercistencia ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

}
