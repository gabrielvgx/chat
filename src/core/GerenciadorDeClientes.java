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
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenciadorDeClientes extends Thread {

    private Socket cliente;
    private String nomeCliente;
    private ObjectInputStream leitor;
    private ObjectOutputStream escritor;
    private static final Map<Usuario, GerenciadorDeClientes> clientes = 
                         new HashMap<Usuario, GerenciadorDeClientes>();

    public GerenciadorDeClientes(Socket cliente) {
        this.cliente = cliente;
        start();
    }

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
                    String nomeDestinario = msg.substring(Comandos.MENSAGEM.length(), msg.length());
                    System.out.println("enviando para " + nomeDestinario);
                    //GerenciadorDeClientes destinario = clientes.get(new Usuario(nomeDestinario, null));
                    ArrayList<GerenciadorDeClientes> destinatario = new ArrayList<>();
                    
                    String mensagem =  leitor.readObject().toString();
                    for(int i=0; i<clientes.size();i++){
                        destinatario.add(clientes.get(usr.listarUsuarioSala(usr.getUserLogin(nomeCliente, null).getIdSala()).get(i)));
                        destinatario.get(i).getEscritor().writeObject(this.nomeCliente + " disse: " + mensagem);
                    }
                    
                

                    // lista o nome de todos os clientes logados
                } else if (msg.equals(Comandos.LISTA_USUARIOS)) {
                    atualizarListaUsuarios(null);
                } else {
                    escritor.writeObject(this.nomeCliente + ", você disse: " + msg);
                }
            }
            } catch (ExcecaoPersistencia ex) {
            Logger.getLogger(GerenciadorDeClientes.class.getName()).log(Level.SEVERE, null, ex);
        }catch (IOException ex) {
            Logger.getLogger(GerenciadorDeClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
        //clientes.remove(this.nomeCliente);
         catch (ClassNotFoundException ex) {
            Logger.getLogger(GerenciadorDeClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    private synchronized void efetuarLogin() throws IOException, ClassNotFoundException, ExcecaoPersistencia {
        PersisteUsuario usr = new PersisteUsuario();
        while (true) {

            escritor.writeObject(Comandos.LOGIN);
            this.nomeCliente = leitor.readObject().toString();
            System.out.println(nomeCliente);
            if (this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()) {
                escritor.writeObject(Comandos.LOGIN_NEGADO);
            } else if (usr.getUserLogin(nomeCliente, null) != null) {
                escritor.writeObject(Comandos.LOGIN_NEGADO);
            } else {
                escritor.writeObject(Comandos.LOGIN_ACEITO);
                escritor.writeObject("olá " + this.nomeCliente);
                usr.cadastrar(new Usuario(this.nomeCliente, null));
                clientes.put(usr.getUserLogin(nomeCliente, null), this);
                break;
            }
        }
    }

    private void atualizarListaUsuarios(Usuario cliente) throws IOException, ExcecaoPersistencia {
        PersisteUsuario usr = new PersisteUsuario();
        ArrayList<Usuario> usuario = usr.listarUsuarioSala(cliente.getIdSala());
        usuario.add(cliente);
        

        
        ObjectOutputStream c = new ObjectOutputStream(this.cliente.getOutputStream());
        c.writeObject(Comandos.LISTA_USUARIOS);
        c.writeObject(usuario);
    
    }
    public ObjectOutputStream getEscritor() {
        return escritor;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

}