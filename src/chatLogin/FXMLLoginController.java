package chatLogin;

import Domain.ExcecaoPersistencia;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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
    protected void fazerLogin(ActionEvent e) throws IOException, ExcecaoPersistencia {

        Cliente1 cliente = new Cliente1();
        login = ((TextArea) painelPrincipal.getChildren().get(2)).getText();
        System.out.println("Login: "+login);
        cliente.iniciarChat(login);
        if (cliente.inicarLeitor(login, painelPrincipal) != -1) {
            cliente.iniciarEscritor(login, painelPrincipal);
        }

    }

}
