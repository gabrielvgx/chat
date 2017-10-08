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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    @FXML
    ListView contatosOnline;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeUsuario.setFocusTraversable(false);
    }

    @FXML
    protected void fazerLogin(ActionEvent e) throws Exception {
        if (validaLogin()) {
            painelPrincipal.getChildren().clear();
            System.out.println("OKSDOKFSODFK");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
            AnchorPane novaTela = (AnchorPane) loader.load();

            ObservableList items = FXCollections.observableArrayList("A", "B", "C", "D");
            contatosOnline.setItems(items);
            painelPrincipal.getChildren().add(novaTela);

        }
    }

    private boolean validaLogin() {
        try {
        PercisteUsuario bdUsuario = new PercisteUsuario();
        Usuario usuario = null;
            usuario = bdUsuario.getUserLogin(nomeUsuario.getText(), null);
        
        
            if ( usuario == null) {
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
