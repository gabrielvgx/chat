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
import javafx.scene.control.ToggleGroup;
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
    private String nomeSala;

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
                Cliente1 cliente = new Cliente1();
                Cliente1.iniciarChat();
                // cliente.inicarLeitor();

                login = ((TextArea) painelPrincipal.getChildren().get(2)).getText();
                painelPrincipal.getChildren().clear();
                AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

                painelPrincipal.autosize();
                painelPrincipal.setTopAnchor(novaTela, 0.0);
                painelPrincipal.setBottomAnchor(novaTela, 0.0);
                painelPrincipal.setLeftAnchor(novaTela, 0.0);
                painelPrincipal.setRightAnchor(novaTela, 0.0);
                painelPrincipal.getChildren().add(novaTela);

                AnchorPane teste = (AnchorPane) novaTela.getChildren().get(0);

                Button botaoEnviar = (Button) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) novaTela.getChildren().get(0))
                        .getChildren().get(0)).getChildren().get(0)).getItems().get(1)).getChildren().get(0)).getItems().get(1)).getChildren().get(0);
                botaoEnviar.setOnAction(event -> {
                    PersisteUsuario usr = new PersisteUsuario();
                    try {
                        if (usr.getUserLogin(login, null) == null) {
                            gerarNotificacao("Ops!", "Crie uma sala ou entre em uma existente para enviar uma mensagem!");
                        } else {
                            cliente.iniciarEscritor((TextArea) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) novaTela.getChildren().get(0))
                                    .getChildren().get(0)).getChildren().get(0)).getItems().get(1)).getChildren().get(0)).getItems().get(1)).getChildren().get(1));
                        }
                    } catch (IOException ex) {
                        System.out.println(ex.getMessage());
                    } catch (ExcecaoPersistencia ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
                Label criarSala = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(3)));
                Label sair = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(2)));
                sair.setOnMouseClicked(((event -> {
                    try {
                        gerarNotificacao("Obrigado por usar nosso Software", "Volte Sempre, " + login + "!");
                        PersisteUsuario usr = new PersisteUsuario();
                        if (usr.getUserLogin(login, null) != null) {
                            PersisteSala sala = new PersisteSala();
                            Usuario aux = usr.getUserLogin(login, null);
                            usr.excluir(login);
                            if (usr.listarUsuarioSala(aux.getIdSala()).isEmpty()) {
                                sala.excluir(sala.getSala(aux.getIdSala()).getNomeSala());
                            }
                        }
                        System.exit(0);
                    } catch (ExcecaoPersistencia ex) {
                        Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                })));

                criarSala.setOnMouseClicked(((event) -> {
                    try {
                        painelPrincipal.getChildren().clear();
                        AnchorPane novaSala = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLNovaSala.fxml"));
                        painelPrincipal.getChildren().add(novaSala);
                        AnchorPane a = (AnchorPane) painelPrincipal.getChildren().get(0);
                        Button adicionar = (Button) a.getChildren().get(0);

                        adicionar.setOnMouseClicked(((MouseEvent -> {
                            nomeSala = ((TextArea) novaSala.getChildren().get(2)).getText();
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

                SplitPane painel = (SplitPane) (((AnchorPane) teste.getChildren().get(0)).getChildren().get(0));
                AnchorPane painelSalas = (AnchorPane) (((ScrollPane) ((AnchorPane) ((TitledPane) (((AnchorPane) ((SplitPane) (((AnchorPane) ((SplitPane) (((AnchorPane) painel.getItems().get(0))
                        .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getContent()).getChildren().get(0))).getContent();
                System.out.println("split_");
                AnchorPane painelParticipantes = (AnchorPane) ((ScrollPane) ((TitledPane) (((AnchorPane) ((SplitPane) (((AnchorPane) ((SplitPane) (((AnchorPane) painel.getItems().get(0))
                        .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(1)).getChildren().get(0))).getContent()).getContent();

                double x1 = painelParticipantes.getLayoutX() + 20;
                double y1 = painelParticipantes.getLayoutY() + 10;
                PersisteSala sala = new PersisteSala();
                PersisteUsuario usuario = new PersisteUsuario();
                if (nomeSala != null) {
                    ArrayList<Usuario> participantes = usuario.listarUsuarioSala(sala.getSala(nomeSala).getIdSala());
                    for (int i = 0; i < participantes.size(); i++) {
                        RadioButton r = new RadioButton(participantes.get(i).getNomeUsuario());
                        r.setLayoutX(x1);
                        r.setLayoutY(y1);
                        painelParticipantes.getChildren().add(r);
                        y1 += 20;
                        painelParticipantes.setPrefHeight(y1);
                    }
                }
                double x = painelSalas.getLayoutX() + 20;
                double y = painelSalas.getLayoutY() + 10;
                ArrayList<Sala> salasDisponiveis = sala.listarSala();
                ToggleGroup grupo = new ToggleGroup();

                for (int i = 0; i < salasDisponiveis.size(); i++) {
                    if (usuario.listarUsuarioSala(salasDisponiveis.get(i).getIdSala()).isEmpty()) {
                        sala.excluir(salasDisponiveis.get(i).getNomeSala());
                    } else {
                        RadioButton r = new RadioButton(salasDisponiveis.get(i).getNomeSala());
                        r.setToggleGroup(grupo);
                        r.setLayoutX(x);
                        r.setLayoutY(y);
                        r.setOnAction(((event -> {

                        })));

                        painelSalas.getChildren().add(r);
                        y += 20;
                        painelSalas.setPrefHeight(y);
                    }
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
