package DAO;

import Connection.ConnectionManager;
import Domain.ExcecaoPersistencia;
import Domain.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * @author bella
 */
public class UsuarioDAO implements IUsuarioDAO {

    @Override
    public String cadastrar(Usuario pessoa) throws ExcecaoPersistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sqlConfere = "SELECT * FROM usuario";
            PreparedStatement  pstmtConfere = connection.prepareStatement(sqlConfere);
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
                String sql = "INSERT INTO usuario (nom_usuario, senha, proprietarioSala)"
                        + " VALUES(?,?,?)";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, pessoa.getNomeusuario());
                pstmt.setString(2, pessoa.getSenha());
                pstmt.setString(3, pessoa.getProprietarioSala());
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
            throw new ExcecaoPersistencia(e.getMessage(), e);
        }
    }

    @Override
    public boolean excluir(String usuario) throws ExcecaoPersistencia {

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
    public Usuario getUserLogin(String nome, String senha) throws ExcecaoPersistencia {
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
                usuario.setProprietarioSala(rs.getString("proprietarioSala"));
            }

            rs.close();
            pstmt.close();
            connection.close();

            return usuario;

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecaoPersistencia(e.getMessage(), e);
        }
    }

    @Override
    public boolean updateUsuario(Usuario usuario){
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sqlConfere = "UPDATE `chat`.`usuario` SET `proprietarioSala` = ?, `participanteSala` = ? WHERE `usuario`.`nom_usuario` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sqlConfere);
            pstmt.setString(1, usuario.getProprietarioSala());
            pstmt.setString(2, usuario.getParticipanteSala());
            pstmt.setString(3, usuario.getNomeusuario());
            pstmt.executeUpdate();
            pstmt.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
    @Override
    public ArrayList<Usuario> listarUsuario() throws ExcecaoPersistencia {
            ArrayList<Usuario> result = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            
            String sqlConfere = "SELECT * FROM usuario";
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            ResultSet rs = pstmtConfere.executeQuery();
            while(rs.next()){
                result.add(new Usuario(rs.getString("nom_usuario"),rs.getString("senha"),rs.getString("proprietarioSala")));
            }
            pstmtConfere.close();
            rs.close();
            return result;
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }
}
