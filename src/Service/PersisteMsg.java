package Service;

import DAO.MensagemDAO;
import Domain.ExcecaoPersistencia;
import Domain.Mensagem;
import java.util.ArrayList;
/**
 *
 * @author bella
 */
public class PersisteMsg implements IPersisteMsg{

    @Override
    public boolean Envia_Msg(Mensagem mensagem) throws ExcecaoPersistencia {
        MensagemDAO instace = new MensagemDAO();
        boolean result = instace.Envia_Msg(mensagem);
        return result;
    }
    
     public ArrayList<Mensagem> Mostra_msg(int idSala) throws ExcecaoPersistencia{
         MensagemDAO instace = new MensagemDAO();
         ArrayList<Mensagem> result = instace.Mostra_Msg(idSala);
         return result;
     }
    
}
