package chatLogin;

import Domain.ExcecaoPersistencia;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author M
 */
public class FXMLLoginController implements Initializable, java.io.Serializable {

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
    private Cliente1 cliente;
    private Socket socketCliente;
    private ObjectInputStream leitor;
    private ObjectOutputStream escritor;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        nomeUsuario.setFocusTraversable(false);
        cliente = new Cliente1();

    }

    private class AtualizarUsuarios extends Thread {

        private AnchorPane painelParticipantes = null;

        public AtualizarUsuarios() {
            painelParticipantes = ((AnchorPane) ((ScrollPane) ((TitledPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) (((AnchorPane) painelPrincipal.getChildren().get(0)).getChildren().get(0)))
                    .getChildren().get(0)).getChildren().get(0)).getItems().get(0))
                    .getChildren().get(0)).getItems().get(0)).getChildren().get(0)).getItems().get(1))
                    .getChildren().get(0)).getContent()).getContent());
            System.out.println(painelPrincipal.getChildren().get(0).getId());
            this.setName("AtualizadorDeClientes");
            start();
        }

        @Override
        public void run() {
            while (true) {
                try {
                   // System.out.println("Socket: "+socketCliente);
                int o = leitor.readInt();
                    Platform.runLater(() -> {
                        if (o != painelParticipantes.getChildren().size()) {
                            System.out.println(painelParticipantes);
                            System.out.println(painelParticipantes.getId());
                            System.out.println("oi");
                            painelParticipantes.getChildren().add(new RadioButton(""));
                            
                        }
                    }
                    );
                    System.out.println("Clientes_"+o);
                } catch (IOException ex) {
                    Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    protected void fazerLogin(ActionEvent e) throws IOException, ExcecaoPersistencia {

        socketCliente = cliente.iniciarChat();
        login = ((TextArea) painelPrincipal.getChildren().get(2)).getText();
        if (cliente.inicarLeitor(login, painelPrincipal) != -1) {
            cliente.iniciarEscritor(login, painelPrincipal);
        }
        System.out.println(">>>>");
        escritor = new ObjectOutputStream(socketCliente.getOutputStream());
        escritor.flush();
        InputStream i = socketCliente.getInputStream();
        System.out.println(">>><<<<<");
        leitor = new ObjectInputStream(i);
        
        System.out.println(",.<><><>");
        new AtualizarUsuarios();
    }

}
