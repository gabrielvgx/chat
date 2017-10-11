package core;

import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import Service.PersisteUsuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeClientes extends Thread {

    private Socket cliente;
    private String nomeCliente;
    private ObjectInputStream leitor;
    private ObjectOutputStream escritor;
    public static final Map<String, GerenciadorDeClientes> clientes
            = new HashMap<String, GerenciadorDeClientes>();

//OK
    public GerenciadorDeClientes(Socket cliente, Object nomeCliente) {
        clientes.put(nomeCliente.toString(), this);
        this.cliente = cliente;
        start();
    }
    public GerenciadorDeClientes(Socket cliente) {
        this.cliente = cliente;
        start();
    }
//OK
    @Override
    public void run() {
        PersisteUsuario usr = new PersisteUsuario();
        try {
            leitor = new ObjectInputStream(cliente.getInputStream());
            escritor = new ObjectOutputStream(cliente.getOutputStream());

            efetuarLogin();

            String msg;
            while (true) {
                msg = leitor.readObject().toString();

                if (msg.equalsIgnoreCase(Comandos.SAIR)) {
                    this.cliente.close();
                } else if (msg.startsWith(Comandos.MENSAGEM)) {
                    ArrayList<GerenciadorDeClientes> destinatario = new ArrayList<>();
                    String mensagem = leitor.readObject().toString();
                    for (int j = 0; j < usr.listarUsuarioSala(usr.getUserLogin(nomeCliente, null).getIdSala()).size(); j++) {
                        destinatario.add(clientes.get(usr.listarUsuarioSala(usr.getUserLogin(nomeCliente, null).getIdSala()).get(j).getNomeUsuario()));
                    }
                    for (int i = 0; i < destinatario.size(); i++) {
                        if (destinatario.get(i) != null) {
                            destinatario.get(i).getEscritor().writeObject(this.nomeCliente + " disse: " + mensagem);
                        }
                    }
                    // lista o nome de todos os clientes logados
                } else if (msg.equals(Comandos.LISTA_USUARIOS)) {
                    atualizarListaUsuarios(this);
                } else {
                    escritor.writeObject(this.nomeCliente + ", você disse: " + msg);
                }
            }
        } catch (ExcecaoPersistencia | IOException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }        
    }

    private synchronized void efetuarLogin() throws IOException, ClassNotFoundException, ExcecaoPersistencia {
        PersisteUsuario usr = new PersisteUsuario();
        while (true) {

            escritor.writeObject(Comandos.LOGIN);System.out.println("GG");
            this.nomeCliente = leitor.readObject().toString();
            System.out.println(nomeCliente);
            if (this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()) {
                escritor.writeObject(Comandos.LOGIN_NEGADO);
            } else if (usr.getUserLogin(nomeCliente, null) != null) {
                escritor.writeObject(Comandos.LOGIN_NEGADO);
            } else {
                escritor.writeObject(Comandos.LOGIN_ACEITO);
                escritor.writeObject("olá " + this.nomeCliente);

              //  clientes.put(nomeCliente, this);
                for (String cliente : clientes.keySet()) {
                    atualizarListaUsuarios(clientes.get(cliente));
                }
                System.out.println("jkejijfjid: " + nomeCliente);
                System.out.println(clientes.get(nomeCliente));
                break;
            }
        }
    }
//OK
    public void atualizarListaUsuarios(GerenciadorDeClientes cliente) throws IOException, ExcecaoPersistencia {
        PersisteUsuario usr = new PersisteUsuario();
        ArrayList<Usuario> usuario = usr.listarUsuarioSala(new Usuario(cliente.getNomeCliente(), null).getIdSala());
        Usuario atual = usr.getUserLogin(cliente.getNomeCliente(), null);
        if (!usuario.contains(atual)) {
            usuario.add(atual);
        }
        cliente.getEscritor().writeObject(Comandos.LISTA_USUARIOS);
        cliente.getEscritor().writeObject(usuario);

    }
//OK

    public ObjectOutputStream getEscritor() {
        return escritor;
    }
//OK

    public String getNomeCliente() {
        return nomeCliente;
    }

}
