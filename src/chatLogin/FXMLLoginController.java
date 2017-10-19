package chatLogin;

import Domain.ExcecaoPersistencia;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    private static int nClientesNaSala;
    private String login;
    private Cliente1 cliente;
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

            start();
        }

        @Override
        public void run() {
            while (true) {
                System.out.println(leitor);
                System.out.println("painelParticipantes: " + painelParticipantes.getChildren().size());
                //nClientesNaSala = leitor.readInt();
                System.out.println("nClientes: " + nClientesNaSala);
                Platform.runLater(() -> {
                    if (nClientesNaSala != painelParticipantes.getChildren().size()) {
                        System.out.println(painelParticipantes);
                        System.out.println(painelParticipantes.getId());
                        System.out.println("oi");
//                            painelParticipantes.getChildren().add(new RadioButton(""));

                    }
                    System.out.println("Thread startada, o: " + nClientesNaSala);
                }
                );
                System.out.println("Clientes_" + nClientesNaSala);
            }
        }
    }

    @FXML
    protected void fazerLogin(ActionEvent e) throws IOException, ExcecaoPersistencia {

        leitor = cliente.iniciarChat();
        login = ((TextArea) painelPrincipal.getChildren().get(2)).getText();
        if (cliente.inicarLeitor(login, painelPrincipal) != -1) {
            cliente.iniciarEscritor(login, painelPrincipal);
            System.out.println("ola: "+this);
      //     nClientesNaSala = leitor.readInt();
            System.out.println("ola2");
            new AtualizarUsuarios();
        }

    }

}
