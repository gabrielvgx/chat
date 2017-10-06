package chat.Service;

import chat.Domain.ExcessoesPercistencia;
import chat.Domain.Mensagem;

/**
 *
 * @author bella
 */
public interface IPercisteMsg {
   public boolean Envia_Msg(Mensagem mensagem) throws ExcessoesPercistencia;
 
}
