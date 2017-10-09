package Service;

import DAO.UsuarioDAO;
import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import java.util.ArrayList;
/**
 *
 * @author bella
 */
public class PercisteUsuario implements IPercisteUsuario {

    @Override
    public String cadastrar(Usuario usuario) throws ExcecaoPersistencia {
        UsuarioDAO instance = new UsuarioDAO();
        String nom_Usuario = instance.cadastrar(usuario);
        return nom_Usuario;
    }

    @Override
    public boolean excluir(String usuario) throws ExcecaoPersistencia {
        UsuarioDAO instance = new UsuarioDAO();
        boolean result = instance.excluir(usuario);
        return result;
    }

    @Override
    public Usuario getUserLogin(String nome, String senha) throws ExcecaoPersistencia {
        UsuarioDAO instance = new UsuarioDAO();
        Usuario result = instance.getUserLogin(nome, senha);
        return result;
    }
    
    @Override
    public ArrayList<Usuario> listarUsuario() throws ExcecaoPersistencia{
        UsuarioDAO instance = new UsuarioDAO();
        ArrayList<Usuario> result = instance.listarUsuario();
        return result;
    }
    
}
