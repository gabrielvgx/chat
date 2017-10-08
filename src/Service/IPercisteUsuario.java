package Service;

import Domain.ExcessoesPercistencia;
import Domain.Usuario;

/**
 *
 * @author bella
 */
public interface IPercisteUsuario {
    public String cadastrar(Usuario usuario) throws ExcessoesPercistencia;
    public boolean excluir(String usuario) throws ExcessoesPercistencia;
    public Usuario getUserLogin(String nome, String senha) throws ExcessoesPercistencia;
}
