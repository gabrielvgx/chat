package DAO;

import Connection.ConnectionManager;
import Domain.ExcecaoPersistencia;
import Domain.Sala;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SalaDAO implements ISalaDAO{

    @Override
    public String cadastrar(Sala sala) throws ExcecaoPersistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sqlConfere = "SELECT * FROM sala";
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            ResultSet rs = pstmtConfere.executeQuery();

            boolean confere = false;
            while (rs.next()) {
                if (rs.getString("nom_sala").equals(sala.getNomeSala())) {
                    confere = true;
                }
            }

            pstmtConfere.close();
            rs.close();

            if (!confere) {
                String sql = "INSERT INTO sala (nom_sala)"
                        + " VALUES(?)";

                PreparedStatement pstmt = connection.prepareStatement(sql);
                pstmt.setString(1, sala.getNomeSala());
                
                pstmt.executeUpdate();

                pstmt.close();
                connection.close();

                return sala.getNomeSala();
            } else {

                connection.close();
                return "Sala Já Cadastrada";
            }

        } catch (Exception e) {
            e.printStackTrace();
            throw new ExcecaoPersistencia(e.getMessage(), e);
        }
    }

    @Override
    public boolean excluir(String nomeSala) throws ExcecaoPersistencia {

        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "DELETE FROM sala WHERE nom_Sala LIKE '"+nomeSala+"'";

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
    public Sala getSala(String nomeSala) throws ExcecaoPersistencia {
        Sala sala = null;
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM sala WHERE nom_sala LIKE '" + nomeSala+"'";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sala = new Sala();
                sala.setIdSala(rs.getInt("id_Sala"));
                sala.setNomeSala(rs.getString("nom_sala"));
            }
            rs.close();
            pstmt.close();
            connection.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return sala;
    }

    @Override
    public Sala getSala(int idSala) throws ExcecaoPersistencia {
        Sala sala = null;
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sql = "SELECT * FROM sala WHERE id_sala =" + idSala;
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                sala = new Sala();
                sala.setIdSala(rs.getInt("id_Sala"));
                sala.setNomeSala(rs.getString("nom_sala"));
            }
            rs.close();
            pstmt.close();
            connection.close();
            
        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
        }
        return sala;
    }

    @Override
    public boolean updateSala(Sala sala) {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();
            String sqlConfere = "UPDATE `chat`.`sala` SET `nom_sala` = ? WHERE `sala`.`id_Sala` = ?;";
            PreparedStatement pstmt = connection.prepareStatement(sqlConfere);
            pstmt.setString(1, sala.getNomeSala());
            pstmt.setInt(2, sala.getIdSala());
            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException | ClassNotFoundException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<Sala> listarSala() throws ExcecaoPersistencia {
        ArrayList<Sala> result = new ArrayList<>();
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sqlConfere = "SELECT * FROM sala";
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            ResultSet rs = pstmtConfere.executeQuery();
            while (rs.next()) {
                result.add(new Sala(rs.getInt("id_Sala"), rs.getString("nom_sala")));
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
