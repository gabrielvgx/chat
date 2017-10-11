package Service;

import DAO.UsuarioDAO;
import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import java.util.ArrayList;
/**
 *
 * @author bella
 */
public class PersisteUsuario implements IPersisteUsuario {

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
    public synchronized Usuario getUserLogin(String nome, String senha) throws ExcecaoPersistencia {
        UsuarioDAO instance = new UsuarioDAO();
        Usuario result = instance.getUserLogin(nome, senha);
        return result;
    }
    
    @Override
    public synchronized ArrayList<Usuario> listarUsuario() throws ExcecaoPersistencia{
        UsuarioDAO instance = new UsuarioDAO();
        ArrayList<Usuario> result = instance.listarUsuario();
        return result;
    }
    
    @Override
    public boolean updateUsuario(Usuario usuario) throws ExcecaoPersistencia{
        UsuarioDAO instance = new UsuarioDAO();
        boolean result = instance.updateUsuario(usuario);
        return result;
    }
    
    @Override
    public synchronized ArrayList<Usuario> listarUsuarioSala(int idSala) throws ExcecaoPersistencia{
        UsuarioDAO instance = new UsuarioDAO();
        ArrayList<Usuario> result = instance.listarUsuarioSala(idSala);
        return result;
    }
            
}
