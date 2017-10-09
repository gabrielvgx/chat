package Service;

import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import java.util.ArrayList;

/**
 *
 * @author bella
 */
public interface IPercisteUsuario {
    public String cadastrar(Usuario usuario) throws ExcecaoPersistencia;
    public boolean excluir(String usuario) throws ExcecaoPersistencia;
    public Usuario getUserLogin(String nome, String senha) throws ExcecaoPersistencia;
    public ArrayList<Usuario> listarUsuario() throws ExcecaoPersistencia;
}
