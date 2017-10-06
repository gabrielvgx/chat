package DAO;

import chat.Domain.ExcessoesPercistencia;
import Domain.Mensagem;
import java.util.ArrayList;

/**
 *
 * @author bella
 */
public interface IMensagemDAO {
    public boolean Envia_Msg(Mensagem mensagem) throws ExcessoesPercistencia;
    public ArrayList<Mensagem> Mostra_Msg(String id_sala) throws ExcessoesPercistencia;
}
