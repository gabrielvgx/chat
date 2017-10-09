package chatLogin;

import Domain.ExcecaoPersistencia;
import Domain.Sala;
import Domain.Usuario;
import Service.PersisteSala;
import Service.PersisteUsuario;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.imageio.ImageIO;

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
    private String login;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeUsuario.setFocusTraversable(false);
    }

    private void gerarNotificacao(String titulo, String subTitulo) {
        BufferedImage trayImage = null;
        File a = new File("icon.png");
        String path = "";
        path = a.getAbsolutePath().replace('\\', '/');

        String[] aux = path.split("/");
        path = "";
        for (int i = 0; i < aux.length - 1; i++) {
            path += aux[i] + "/";
        }
        try {
            trayImage = ImageIO.read(new FileInputStream(new File(path + "icones/icon.png")));
            TrayIcon tray = new TrayIcon(trayImage);
            SystemTray sysTray = SystemTray.getSystemTray();
            tray.setImageAutoSize(true);
            sysTray.add(tray);
            tray.displayMessage(titulo, subTitulo, TrayIcon.MessageType.INFO);
        } catch (AWTException | FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @FXML
    protected void fazerLogin(ActionEvent e) {
        try {
            if (validaLogin()) {
                
                login = ((TextArea) painelPrincipal.getChildren().get(2)).getText();
                painelPrincipal.getChildren().clear();

                AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
                painelPrincipal.getChildren().add(novaTela);
                AnchorPane teste = (AnchorPane) novaTela.getChildren().get(0);

                Label criarSala = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(3)));
                Label sair = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(2)));
                sair.setOnMouseClicked(((event -> {
                    gerarNotificacao("Obrigado por usar nosso Software", "Volte Sempre, " + login + "!");
                    System.exit(0);
                })));
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
                                try {
                                    PersisteUsuario usr = new PersisteUsuario();
                                    PersisteSala sala = new PersisteSala();
                                    sala.cadastrar(new Sala(nomeSala));
                                    Usuario aux = new Usuario(login, null, true, sala.getSala(nomeSala).getIdSala());
                                    usr.cadastrar(aux);
                                    painelPrincipal.getChildren().clear();
                                    painelPrincipal.getChildren().add(novaTela);
                                    Text titulo = (Text) (((AnchorPane) (((AnchorPane) novaTela.getChildren().get(0)).getChildren().get(1))).getChildren().get(1));
                                    titulo.setText(nomeSala);
                                    if (nomeSala.length() > 10) {
                                        titulo.setStyle("-fx-font-size: 16");
                                    }
                                } catch (ExcecaoPersistencia ex) {
                                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                        })));

                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    }

                }));

                SplitPane panel = (SplitPane) (((AnchorPane) teste.getChildren().get(0)).getChildren().get(0));
                AnchorPane painelSalas = (AnchorPane) (((ScrollPane) ((AnchorPane) ((TitledPane) (((AnchorPane) ((SplitPane) (((AnchorPane) ((SplitPane) (((AnchorPane) panel.getItems().get(0))
                        .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getContent()).getChildren().get(0))).getContent();

                double x = painelSalas.getLayoutX() + 20;
                double y = painelSalas.getLayoutY() + 10;
                System.out.println("y = " + painelSalas.getPrefHeight());
                System.out.println(new PersisteUsuario().listarUsuario());
                PersisteUsuario usr = new PersisteUsuario();
                ArrayList<Usuario> usuariosOnline = usr.listarUsuario();
                System.out.println("size_" + usuariosOnline.size());
                for (int i = 0; i < usuariosOnline.size(); i++) {
                    RadioButton r = new RadioButton(usuariosOnline.get(i).getNomeUsuario());
                    r.setLayoutX(x);
                    r.setLayoutY(y);
                    painelSalas.getChildren().add(r);
                    y += 20;
                    painelSalas.setPrefHeight(y);
                }

            } else {
                erroLogar.setText("Essa id ja esta em uso!");
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    private boolean validaLogin() {
        try {
            PersisteUsuario bdUsuario = new PersisteUsuario();
            Usuario usuario = null;
            usuario = bdUsuario.getUserLogin(nomeUsuario.getText(), null);
            if (usuario == null) {
                return true;
            }
            return false;
        } catch (ExcecaoPersistencia ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

}
