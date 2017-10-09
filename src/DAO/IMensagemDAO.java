package DAO;

import Domain.ExcecaoPersistencia;
import Domain.Mensagem;
import java.util.ArrayList;

/**
 *
 * @author bella
 */
public interface IMensagemDAO {
    public boolean Envia_Msg(Mensagem mensagem) throws ExcecaoPersistencia;
    public ArrayList<Mensagem> Mostra_Msg(String id_sala) throws ExcecaoPersistencia;
}
