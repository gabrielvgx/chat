package DAO;

import Connection.ConnectionManager;
import Domain.ExcecaoPersistencia;
import Domain.Mensagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
/**
 *
 * @author bella
 */
public class MensagemDAO implements IMensagemDAO {

    @Override
    public boolean Envia_Msg(Mensagem mensagem) throws ExcecaoPersistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "INSERT INTO mensagem(txt_msg, id_Usuario, datetime, id_sala)"
                    + "VALUES(?,?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, mensagem.getTxtMensagem());
            pstmt.setInt(2, mensagem.getIdUsuario());
 

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale); 
            mensagem.setDateTime(formatador.format(calendar.getTime()));
            pstmt.setString(3, mensagem.getDateTime());
            pstmt.setInt(4, mensagem.getIdSala());

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
    public ArrayList<Mensagem> Mostra_Msg(int idSala) throws ExcecaoPersistencia {
        try{
            Connection connection = ConnectionManager.getInstance().getConnection();

            String sql = "SELECT * FROM `mensagem` WHERE `id_sala` = ?"
                    + " ORDER BY `mensagem`.`datetime` DESC";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idSala);
            ResultSet rs = pstmt.executeQuery();
            
            ArrayList<Mensagem> mensagens = new ArrayList<>();
            Mensagem msg;
            
            while(rs.next()){
                msg = new Mensagem();
                msg.setDateTime(rs.getString("datetime"));
                msg.setUsuario(rs.getInt("id_usuario"));
                msg.setTxtMensagem(rs.getString("txt_mensagem"));
                msg.setIdSala(rs.getInt("id_sala"));
                mensagens.add(msg);
            }
            
            rs.close();
            pstmt.close();
            connection.close();
            
            return mensagens;
            
        }catch(Exception e){
            
            e.printStackTrace();
            return null;
            
        }
    }

}
