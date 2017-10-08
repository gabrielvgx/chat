package core;


import Domain.Usuario;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GerenciadorDeClientes extends Thread {
	
	private Socket cliente;
	private String nomeCliente;
	private ObjectInputStream leitor;
	private ObjectOutputStream escritor;
	private static final Map<Usuario,GerenciadorDeClientes> clientes = new HashMap<Usuario,GerenciadorDeClientes>();

	public GerenciadorDeClientes(Socket cliente) {
		this.cliente = cliente;
		start();
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		try {
			leitor = new ObjectInputStream(cliente.getInputStream());
			escritor = new ObjectOutputStream(cliente.getOutputStream());
			
			efetuarLogin();
			
			String msg;
			while(true){
				msg = leitor.readObject().toString();
				if(msg.equalsIgnoreCase(Comandos.SAIR)){
					this.cliente.close();
				}else if(msg.startsWith(Comandos.MENSAGEM)){
					String nomeDestinario = msg.substring(Comandos.MENSAGEM.length(), msg.length());
					System.out.println("enviando para " + nomeDestinario);
					GerenciadorDeClientes destinario = clientes.get(new Usuario(nomeDestinario,null));
					if(destinario == null){
						escritor.writeObject("O cliente informado nao existe");
					}else{
						destinario.getEscritor().writeObject(this.nomeCliente + " disse: " + leitor.readObject());
					}
					
				// lista o nome de todos os clientes logados
				}else if(msg.equals(Comandos.LISTA_USUARIOS)){
					atualizarListaUsuarios(this);
				}else{
					escritor.writeObject(this.nomeCliente + ", você disse: " + msg);
				}
			}
			
		} catch (IOException e) {
			System.err.println("o cliente fechou a conexao");
			clientes.remove(this.nomeCliente);
			e.printStackTrace();
		} catch (ClassNotFoundException ex) {
                Logger.getLogger(GerenciadorDeClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
	}

	private synchronized void  efetuarLogin() throws IOException, ClassNotFoundException {
		
		while(true){
			escritor.writeObject(Comandos.LOGIN);
			this.nomeCliente = leitor.readObject().toString().toLowerCase().replaceAll(",", "");
			if(this.nomeCliente.equalsIgnoreCase("null") || this.nomeCliente.isEmpty()){
				escritor.writeObject(Comandos.LOGIN_NEGADO);
			}else if(clientes.containsKey(this.nomeCliente)){
				escritor.writeObject(Comandos.LOGIN_NEGADO);
			}else{
				escritor.writeObject(Comandos.LOGIN_ACEITO);
				escritor.writeObject("olá " + this.nomeCliente);
				clientes.put(new Usuario(this.nomeCliente, null), this);
				for( Usuario cliente: clientes.keySet()){
					atualizarListaUsuarios(clientes.get(cliente));
				}
				break;
			}
		}
	}

	private void atualizarListaUsuarios(GerenciadorDeClientes cliente) throws IOException {
		StringBuffer str = new StringBuffer();
		for(Usuario c: clientes.keySet()){
			if(cliente.getNomeCliente().equals(c))
				continue;
			
			str.append(c);
			str.append(",");
		}
		if(str.length() > 0)
			str.delete(str.length()-1, str.length());
		cliente.getEscritor().writeObject(Comandos.LISTA_USUARIOS);
		cliente.getEscritor().writeObject(str.toString());
	}

	public ObjectOutputStream getEscritor() {
		return escritor;
	}

	public String getNomeCliente() {
		return nomeCliente;
	}
}



