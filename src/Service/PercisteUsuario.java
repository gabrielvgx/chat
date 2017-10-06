package chat.Service;

import chat.DAO.UsuarioDAO;
import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Usuario;

/**
 *
 * @author bella
 */
public class PercisteUsuario implements IPercisteUsuario {

    @Override
    public String cadastrar(Usuario usuario) throws ExcessoesPercistencia {
        UsuarioDAO instace = new UsuarioDAO();
        String nom_Usuario = instace.cadastrar(usuario);
        return nom_Usuario;
    }

    @Override
    public boolean excluir(String usuario) throws ExcessoesPercistencia {
        UsuarioDAO instace = new UsuarioDAO();
        boolean result = instace.excluir(usuario);
        return result;
    }

    @Override
    public Usuario getUserLogin(String nome, String senha) throws ExcessoesPercistencia {
        UsuarioDAO instace = new UsuarioDAO();
        Usuario result = instace.getUserLogin(nome, senha);
        return result;
    }
    
}
