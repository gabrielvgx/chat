/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatLogin;

import Domain.ExcessoesPercistencia;
import Domain.Usuario;
import Service.PercisteUsuario;
import java.io.IOException;
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
import javafx.scene.text.Text;

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
                AnchorPane teste = (AnchorPane) novaTela.getChildren().get(0);

                Label criarSala = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(3)));
                criarSala.setOnMouseClicked(((event) -> {
                    try {
                        painelPrincipal.getChildren().clear();
                        AnchorPane novaSala = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLNovaSala.fxml"));
                        painelPrincipal.getChildren().add(novaSala);
                        AnchorPane a = (AnchorPane) painelPrincipal.getChildren().get(0);
                        Button adicionar = (Button) a.getChildren().get(0);
                        System.out.println("OK_"+a.getChildren());
                        adicionar.setOnMouseClicked(((MouseEvent -> {
                            String nomeSala = ((TextArea) novaSala.getChildren().get(2)).getText();
                            if (nomeSala.length() > 15) {
                                Label notificar = ((Label) a.getChildren().get(3));
                                notificar.setText("Máximo de 15 caracteres");
                            } else {
                                painelPrincipal.getChildren().clear();
                                painelPrincipal.getChildren().add(novaTela);
                                Text titulo = (Text) (((AnchorPane) (((AnchorPane) novaTela.getChildren().get(0)).getChildren().get(1))).getChildren().get(1));
                                titulo.setText(nomeSala);
                                if (nomeSala.length() > 10) {
                                    titulo.setStyle("-fx-font-size: 16");
                                }
                            }
                        })));

                        System.out.println(a.getChildren());
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }));

                SplitPane panel = (SplitPane) (((AnchorPane) teste.getChildren().get(0)).getChildren().get(0));
                /*
                System.out.println("oii__"+((TitledPane)(((AnchorPane)((SplitPane)(((AnchorPane)((SplitPane)(((AnchorPane)panel.getItems().get(0))
                        .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(0)).getChildren().get(0))).get);*/
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
