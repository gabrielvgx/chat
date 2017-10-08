package core;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.TextArea;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Cliente {

    private JList liUsarios = new JList();
    private ObjectOutputStream escritor;
    private ObjectInputStream leitor;

    public Cliente() {
    }

    /**
     * Preenche a lista de usuarios
     *
     * @param usuarios
     */
    private void preencherListaUsuarios(String[] usuarios) {
        DefaultListModel modelo = new DefaultListModel();
        liUsarios.setModel(modelo);
        for (String usuario : usuarios) {
            modelo.addElement(usuario);
        }
    }

    private void iniciarEscritor() throws IOException {//ActionOnClicked do botao enviar mensagem
        escritor = new ObjectOutputStream(Cliente.iniciarChat().getOutputStream());

        TextArea mensagem = new TextArea();
        if (mensagem.getText().isEmpty()) {
            return;
        }
        Object usuario = liUsarios.getSelectedValue();
        if (usuario != null) {
            try {
                System.out.println("Eu: " + mensagem.getText());

                escritor.writeObject(Comandos.MENSAGEM + usuario);
                escritor.writeObject(mensagem.getText());
                mensagem.setText(null);
            } catch (IOException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            JOptionPane.showMessageDialog(null, "Selecione um usuario");
            return;
        }

    }

    public static Socket iniciarChat() {//Continua na classe cliente
        Socket cliente = null;
        try {
            cliente = new Socket("127.0.0.1", 9999);
        } catch (UnknownHostException e) {
            System.out.println("o endereço passado é inválido");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("o servidor pode estar fora ar");
            e.printStackTrace();
        }
        return cliente;
    }

    public static void main(String[] args) throws IOException {
        Cliente cliente = new Cliente();
        cliente.iniciarChat();
        cliente.iniciarEscritor();
        cliente.inicarLeitor();
    }

    private void atualizarListaUsuarios() throws IOException {
        escritor.writeObject(Comandos.LISTA_USUARIOS);
    }

    private void inicarLeitor() throws IOException {
        leitor = new ObjectInputStream(Cliente.iniciarChat().getInputStream());

// lendo mensagens do servidor
        try {
            while (true) {
                String mensagem = leitor.readObject().toString();
                if (mensagem == null || mensagem.isEmpty()) {
                    continue;
                }

                // recebe o texto
                if (mensagem.equals(Comandos.LISTA_USUARIOS)) {
                    String[] usuarios = leitor.readObject().toString().split(",");
                    preencherListaUsuarios(usuarios);
                } else if (mensagem.equals(Comandos.LOGIN)) {
                    //Pegar o login da Interface FXMLLogin no pacote chatLogin
                    String login = JOptionPane.showInputDialog("Qual o seu login?");
                    escritor.writeObject(login);
                } else if (mensagem.equals(Comandos.LOGIN_NEGADO)) {
                    JOptionPane.showMessageDialog(null, "Login Inválido");
                } else if (mensagem.equals(Comandos.LOGIN_ACEITO)) {
                    atualizarListaUsuarios();
                } else {
                    //Enviar pra interface FXMLDocument do pacote chat
                    JOptionPane.showMessageDialog(null, mensagem);
                }
            }

        } catch (IOException e) {
            System.out.println("impossivel ler a mensagem do servidor" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private DefaultListModel getListaUsuarios() {
        return (DefaultListModel) liUsarios.getModel();
    }
}
