package Service;

import DAO.SalaDAO;
import Domain.ExcecaoPersistencia;
import Domain.Sala;
import java.util.ArrayList;

public class PersisteSala implements IPersisteSala{

    @Override
    public String cadastrar(Sala sala) throws ExcecaoPersistencia {
        SalaDAO instance = new SalaDAO();
        String nomeSala = instance.cadastrar(sala);
        return nomeSala;
    }

    @Override
    public boolean excluir(String nomeSala) throws ExcecaoPersistencia {
        SalaDAO instance = new SalaDAO();
        boolean result = instance.excluir(nomeSala);
        return result;
    }

    @Override
    public Sala getSala(String nomeSala) throws ExcecaoPersistencia {
        SalaDAO instance = new SalaDAO();
        Sala result = instance.getSala(nomeSala);
        return result;
    }
    
    @Override
    public Sala getSala(int idSala) throws ExcecaoPersistencia {
        SalaDAO instance = new SalaDAO();
        Sala result = instance.getSala(idSala);
        return result;
    }
    
    @Override
    public ArrayList<Sala> listarSala() throws ExcecaoPersistencia{
        SalaDAO instance = new SalaDAO();
        ArrayList<Sala> result = instance.listarSala();
        return result;
    }
    
    @Override
    public boolean updateSala(Sala sala) throws ExcecaoPersistencia{
        SalaDAO instance = new SalaDAO();
        boolean result = instance.updateSala(sala);
        return result;
    }
}
