package chat.DAO;

import chat.Domain.Usuario;
import chat.Domain.ExcessoesPercistencia;

/**
 * @author bella
 */
public interface IUsuarioDAO {
    public String cadastrar(Usuario usuario) throws ExcessoesPercistencia;
    public boolean excluir(String usuario) throws ExcessoesPercistencia;
    public Usuario getUserLogin(String nome, String senha) throws ExcessoesPercistencia;
}
