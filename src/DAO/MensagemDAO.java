package chat.DAO;

import chat.Connection.ConnectionManager;
import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Mensagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 *
 * @author bella
 */
public class MensagemDAO implements IMensagemDAO {

    @Override
    public boolean Envia_Msg(Mensagem mensagem) throws ExcessoesPercistencia {
        try {
            Connection connection = ConnectionManager.getInstance().getConnection();

            /*boolean confere = false;
            String sqlConfere = "SELECT * FROM mensagem WHERE txt_msg = ?";
            PreparedStatement pstmtConfere = connection.prepareStatement(sqlConfere);
            pstmtConfere.setString(1, mensagem.getTxtMensagem());
            ResultSet rs = pstmtConfere.executeQuery();

            if (rs != null) {
                confere = true;
            }

            pstmtConfere.close();
            rs.close();*/
            String sql = "INSERT INTO mensagem(txt_msg, usuario, datatime)"
                    + "VALUES(?,?,?)";

            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, mensagem.getTxtMensagem());
            pstmt.setString(2, mensagem.getUsuário());

            Locale locale = new Locale("pt", "BR");
            GregorianCalendar calendar = new GregorianCalendar();
            SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", locale); 
            mensagem.setDataTime(formatador.format(calendar.getTime()));
            pstmt.setString(3, mensagem.getDataTime());

            pstmt.executeUpdate();

            pstmt.close();
            connection.close();

            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
