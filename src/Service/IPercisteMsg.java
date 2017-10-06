package Service;

import chat.Domain.ExcessoesPercistencia;
import Domain.Mensagem;
import java.util.ArrayList;

/**
 *
 * @author bella
 */
public interface IPercisteMsg {
   public boolean Envia_Msg(Mensagem mensagem) throws ExcessoesPercistencia;
   public ArrayList<Mensagem> Mostra_msg(String id_sala) throws ExcessoesPercistencia;
 
}
