package Service;

import DAO.MensagemDAO;
import chat.Domain.ExcessoesPercistencia;
import Domain.Mensagem;
import chat.Service.IPercisteMsg;
import java.util.ArrayList;
/**
 *
 * @author bella
 */
public class PercisteMsg implements IPercisteMsg{

    @Override
    public boolean Envia_Msg(Mensagem mensagem) throws ExcessoesPercistencia {
        MensagemDAO instace = new MensagemDAO();
        boolean result = instace.Envia_Msg(mensagem);
        return result;
    }
    
     public ArrayList<Mensagem> Mostra_msg(String id_sala) throws ExcessoesPercistencia{
         MensagemDAO instace = new MensagemDAO();
         ArrayList<Mensagem> result = instace.Mostra_Msg(id_sala);
         return result;
     }
    
}