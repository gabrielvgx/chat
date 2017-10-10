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
    public String cadastrar(Usuario usuario) throws ExcecaoPersistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sqlConfere = "SELECT * FROM usuario";
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            ResultSet rs = pstmtConfere.executeQuery();
            boolean confere = false;
            while (rs.next()) {
                if (rs.getString("nom_usuario").equals(usuario.getNomeUsuario())) {
                    confere = true;
                }
            }

            pstmtConfere.close();
            rs.close();

            if (!confere) {
                String sql = "INSERT INTO usuario (nom_usuario, senha, id_sala, admSala)"
                        + " VALUES(?,?,?,?)";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, usuario.getNomeUsuario());
                pstmt.setString(2, usuario.getSenha());
                pstmt.setInt(3, 2);
                pstmt.setBoolean(4, usuario.getAdmSala());

                pstmt.executeUpdate();

                pstmt.close();
                connection.close();

                return usuario.getNomeUsuario();
                
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

            String sql = "DELETE FROM Usuario WHERE nom_usuario LIKE '" + usuario + "'";

            PreparedStatement pstmt = connection.prepareStatement(sql);
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
            String sql = "";
            if (senha == null) {
                sql = "SELECT * FROM Usuario WHERE nom_usuario LIKE '" + nome + "' AND senha IS NULL";
            } else {
                sql = "SELECT * FROM Usuario WHERE nom_usuario LIKE '" + nome + "' AND senha LIKE '" + senha + "'";
            }
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();

            Usuario usuario = null;
            if (rs.next()) {
                usuario = new Usuario();
                usuario.setSenha(rs.getString("senha"));
                usuario.setNomeUsuario(rs.getString("nom_usuario"));
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setAdmSala(rs.getBoolean("admSala"));
                usuario.setIdSala(rs.getInt("id_sala"));
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
    public boolean updateUsuario(Usuario usuario) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sqlConfere = "UPDATE `chat`.`usuario` SET `admSala` = ?, `id_sala` = ? WHERE `usuario`.`nom_usuario` LIKE '" + usuario.getNomeUsuario() + "'";
            PreparedStatement pstmt = connection.prepareStatement(sqlConfere);
            pstmt.setBoolean(1, usuario.getAdmSala());
            pstmt.setInt(2, usuario.getIdSala());
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
            while (rs.next()) {
                result.add(new Usuario(rs.getString("nom_usuario"), rs.getString("senha"), rs.getBoolean("admSala"), rs.getInt("id_sala")));
            }
            pstmtConfere.close();
            rs.close();
            return result;

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return result;
    }

    public ArrayList<Usuario> listarUsuarioSala(int idSala) {
        ArrayList<Usuario> result = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sqlConfere = "SELECT * FROM `usuario` WHERE `id_sala` = "+idSala;
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            ResultSet rs = pstmtConfere.executeQuery();
            while (rs.next()) {
                result.add(new Usuario(rs.getString("nom_usuario"), rs.getString("senha"), rs.getBoolean("admSala"), rs.getInt("id_sala")));
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
