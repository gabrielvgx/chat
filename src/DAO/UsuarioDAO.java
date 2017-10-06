package chat.DAO;

import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import chat.Connection.ConnectionManager;
import java.util.ArrayList;

/**
 * @author bella
 */
public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public String cadastrar(Usuario pessoa) throws ExcessoesPercistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sqlConfere = "SELECT * FROM usuario";
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            ResultSet rs = pstmtConfere.executeQuery();

            //ArrayList usuarios = new ArrayList();
            boolean confere = false;
            while (rs.next()) {
                if (rs.getString("nom_usuario").equals(pessoa.getNomeusuario())) {
                    confere = true;
                }
            }
            
            pstmtConfere.close();
            rs.close();

            if (!confere) {
                String sql = "INSERT INTO usuario (nom_usuario, senha)"
                        + " VALUES(?,?)";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, pessoa.getNomeusuario());
                pstmt.setString(2, pessoa.getSenha());
                pstmt.executeUpdate();

                pstmt.close();
                connection.close();

                return pessoa.getNomeusuario();
            } else {
                
                connection.close();
                return "Usuário Já Cadastrado";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcessoesPercistencia(e.getMessage(), e);
        }
    }

    @Override
    public boolean excluir(String usuario) throws ExcessoesPercistencia {

        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "DELETE FROM Usuario WHERE nom_usuario = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, usuario);
            pstmt.executeUpdate();

            pstmt.close();
            connection.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Usuario getUserLogin(String nome, String senha) throws ExcessoesPercistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT * FROM Usuario WHERE nom_usuario = ? AND senha = ?";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nome);
            pstmt.setString(2, senha);
            ResultSet rs = pstmt.executeQuery();

            Usuario usuario = null;
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setSenha(rs.getString("senha"));
                usuario.setNomeusuario(rs.getString("nom_usuario"));
                usuario.setIdUsuario(rs.getLong("id_usuario"));
            }

            rs.close();
            pstmt.close();
            connection.close();

            return usuario;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcessoesPercistencia(e.getMessage(), e);
        }
    }

}
