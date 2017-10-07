package chat.Service;

import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Usuario;

/**
 *
 * @author bella
 */
public interface IPercisteUsuario {
    public String cadastrar(Usuario usuario) throws ExcessoesPercistencia;
    public boolean excluir(String usuario) throws ExcessoesPercistencia;
    public Usuario getUserLogin(String nome, String senha) throws ExcessoesPercistencia;
}
