package core;

import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import Service.PersisteMsg;
import Service.PersisteSala;
import Service.PersisteUsuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeClientes extends Thread implements java.io.Serializable {

    private Socket cliente;
    private String nomeCliente;
    private ObjectInputStream leitor;
    private ObjectOutputStream escritor;
    private PersisteUsuario usr = new PersisteUsuario();
    private PersisteMsg mensagem = new PersisteMsg();
    private PersisteSala sala = new PersisteSala();
    public static final Map<String, GerenciadorDeClientes> clientes
            = new HashMap<String, GerenciadorDeClientes>();
    boolean estaLogando = false;

    public GerenciadorDeClientes(Socket cliente) {
        this.setName("GerenciadorDeClientes");
        this.cliente = cliente;
        start();
    }

    @Override
    public synchronized void run() {
        try {
            leitor = new ObjectInputStream(cliente.getInputStream());
            escritor = new ObjectOutputStream(cliente.getOutputStream());
            System.out.println("cliente: " + cliente);
            estaLogando = true;
            efetuarLogin();
            estaLogando = false;
            String msg;
            while (true) {
                msg = leitor.readObject().toString();

                if (msg.equalsIgnoreCase(Comandos.SAIR)) {
                    this.cliente.close();
                } else if (msg.startsWith(Comandos.MENSAGEM)) {
                    ArrayList<GerenciadorDeClientes> destinatario = new ArrayList<>();
                    ArrayList<Usuario> usuariosSala = new ArrayList<>(usr.listarUsuarioSala(usr.getUserLogin(nomeCliente, null).getIdSala()));
                    String mensagem = leitor.readObject().toString();
                    for (int i = 0; i < usuariosSala.size(); i++) {
                        destinatario.add(clientes.get(usuariosSala.get(i).getNomeUsuario()));
                        destinatario.get(i).getEscritor().writeObject(this.nomeCliente + " disse: " + mensagem);
                    }

                    // lista o nome de todos os clientes logados
                } else if (msg.equals(Comandos.LISTA_USUARIOS)) {
                    System.out.println("Threads\n" + Thread.activeCount() + "\n" + Thread.getAllStackTraces().keySet());

                    atualizarListaUsuarios();
                } else {
                    escritor.writeObject(this.nomeCliente + ", você disse: " + msg);
                }
            }
        } catch (ExcecaoPersistencia ex) {
            System.out.println("_ID 01: " + Thread.activeCount());
            ArrayList<Thread> threads = new ArrayList<>(Thread.getAllStackTraces().keySet());
            for (int i = 0; i < threads.size(); i++) {
                if (threads.get(i).getName().equals("main")) {
                    threads.get(i).interrupt();
                }
                System.out.println(threads.get(i));
            }

            System.out.println(ex.getMessage());
        } catch (IOException ex) {
            System.out.println("_ID 02");
            ex.printStackTrace();
        } //clientes.remove(this.nomeCliente);
        catch (ClassNotFoundException ex) {
            System.out.println("_ID 03");
            System.out.println(ex.getMessage());
        }
    }

    private synchronized void efetuarLogin() throws IOException, ClassNotFoundException, ExcecaoPersistencia {
        System.out.println("Threads ativas: " + Thread.getAllStackTraces().keySet());
        while (true) {

            escritor.writeObject(Comandos.LOGIN);
            this.nomeCliente = leitor.readObject().toString();
            if (this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()) {
                escritor.writeObject(Comandos.LOGIN_NEGADO);
            } else if (usr.getUserLogin(nomeCliente, null) != null) {
                escritor.writeObject(Comandos.LOGIN_NEGADO);
            } else {
                escritor.writeObject(Comandos.LOGIN_ACEITO);
                escritor.writeObject("olá " + this.nomeCliente);

                clientes.put(nomeCliente, this);
                if (clientes.size() == 2) {
                    ArrayList<Thread> threads = new ArrayList(Thread.getAllStackTraces().keySet());
                    for (int i = 0; i < threads.size(); i++) {
                        if (threads.get(i).getName().equals("main")) {
                            threads.get(i).interrupt();
                        }
                        System.out.println(threads.get(i));
                    }
                }
                break;
            }
        }
    }

    private synchronized void atualizarListaUsuarios() throws IOException, ExcecaoPersistencia {

        Usuario u = usr.getUserLogin(nomeCliente, null);

        if (u != null) {
            ArrayList<Usuario> usuariosSala = usr.listarUsuarioSala(u.getIdSala());
            for (int i = 0; i < usuariosSala.size(); i++) {
                clientes.get(usuariosSala.get(i).getNomeUsuario()).getEscritor().writeObject(Comandos.LISTA_USUARIOS);
                clientes.get(usuariosSala.get(i).getNomeUsuario()).getEscritor().writeObject(usuariosSala);

            }
        }

    }

    public ObjectOutputStream getEscritor() {
        return escritor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

}
