package Service;

import Domain.ExcecaoPersistencia;
import Domain.Mensagem;
import java.util.ArrayList;

/**
 *
 * @author bella
 */
public interface IPercisteMsg {
   public boolean Envia_Msg(Mensagem mensagem) throws ExcecaoPersistencia;
   public ArrayList<Mensagem> Mostra_msg(String id_sala) throws ExcecaoPersistencia;
 
}
