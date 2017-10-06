package chat.Service;

import chat.DAO.MensagemDAO;
import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Mensagem;

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
    
}
