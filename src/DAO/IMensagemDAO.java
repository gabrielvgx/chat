package chat.DAO;

import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Mensagem;

/**
 *
 * @author bella
 */
public interface IMensagemDAO {
    public boolean Envia_Msg(Mensagem mensagem) throws ExcessoesPercistencia;
}
