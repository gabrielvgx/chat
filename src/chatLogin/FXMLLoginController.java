/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatLogin;

import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import Service.PercisteUsuario;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
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

                        
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }));

                SplitPane panel = (SplitPane) (((AnchorPane) teste.getChildren().get(0)).getChildren().get(0));
                AnchorPane painelSalas = (AnchorPane)(((ScrollPane)((AnchorPane)((TitledPane)(((AnchorPane)((SplitPane)(((AnchorPane)((SplitPane)(((AnchorPane)panel.getItems().get(0))
                        .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getContent()).getChildren().get(0))).getContent();
                
                double x = painelSalas.getLayoutX()+20;
                double y = painelSalas.getLayoutY()+10;
                System.out.println("y = "+painelSalas.getPrefHeight());
                System.out.println(new PercisteUsuario().listarUsuario());
                PercisteUsuario usr = new PercisteUsuario();
                ArrayList<Usuario> usuariosOnline = usr.listarUsuario();System.out.println("size_"+usuariosOnline.size());
                for(int i=0; i < usuariosOnline.size(); i++){
                    RadioButton r = new RadioButton(usuariosOnline.get(i).getNomeusuario());
                    r.setLayoutX(x);
                    r.setLayoutY(y);
                    painelSalas.getChildren().add(r);
                    y+=20;
                    painelSalas.setPrefHeight(y);
                }
                
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
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
        } catch (ExcecaoPersistencia ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

}
