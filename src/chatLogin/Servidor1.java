package chatLogin;

import core.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor1 implements java.io.Serializable{
	public static void main(String[] args) {
		
		ServerSocket servidor = null;
		try {
			System.out.println("startando o servidor");
			servidor = new ServerSocket(9999);
			System.out.println("servidor startado");
			
			while(true){
				Socket cliente = servidor.accept();
				new GerenciadorDeClientes(cliente);
			}
			
		} catch (IOException e) {
			
			try {
				if(servidor != null)
					servidor.close();
			} catch (IOException e1) {}
			
			System.err.println("a porta está ocupada ou servidor foi fechado");
			e.printStackTrace();
		}
		
	}
}
