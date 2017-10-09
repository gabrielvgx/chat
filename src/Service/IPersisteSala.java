package Service;

import Domain.ExcecaoPersistencia;
import Domain.Sala;
import java.util.ArrayList;

public interface IPersisteSala {
    public String cadastrar(Sala sala) throws ExcecaoPersistencia;
    public boolean excluir(String nomeSala) throws ExcecaoPersistencia;
    public Sala getSala(String nomeSala) throws ExcecaoPersistencia;
    public Sala getSala(int idSala) throws ExcecaoPersistencia;
    public ArrayList<Sala> listarSala() throws ExcecaoPersistencia;
    public boolean updateSala(Sala sala) throws ExcecaoPersistencia;
}
