package chatLogin;

import core.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Servidor1 {

    public static void main(String[] args) {

        ServerSocket servidor = null;
        int i = 0;
        try {
            System.out.println("startando o servidor");
            servidor = new ServerSocket(9999);
            System.out.println("servidor startado");

            while (true) {
                Socket cliente = servidor.accept();
                if (i < 1) {
                    i++;
                    ObjectInputStream obj = new ObjectInputStream(cliente.getInputStream());
                    new GerenciadorDeClientes(cliente, obj.readObject());
                }else{
                    new GerenciadorDeClientes(cliente);
                }
            }

        } catch (IOException e) {

            try {
                if (servidor != null) {
                    servidor.close();
                }
            } catch (IOException e1) {
            }

            System.err.println("a porta está ocupada ou servidor foi fechado");
            e.printStackTrace();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Servidor1.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
