package chatLogin;

import core.GerenciadorDeClientes;
import java.util.HashMap;
import java.util.Map;

public class teste {
    public final static Map<String,GerenciadorDeClientes> clientes = new HashMap<>();
    public teste(){
        
    }
    public static Map<String,GerenciadorDeClientes> getClientes(){
        return clientes;
    }
}
