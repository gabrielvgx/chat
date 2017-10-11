package Domain;

public class Sala {
    private int idSala;
    private String nomeSala;
    
    public Sala() {
    }

    public Sala(int idSala, String nomeSala) {
        this.nomeSala = nomeSala;
        this.idSala = idSala;
    }

    public Sala(String nomeSala) {
        this.nomeSala = nomeSala;
    }
    
    public int getIdSala() {
        return idSala;
    }
    
    public void setIdSala(int idSala){
        this.idSala = idSala;
    }

    public String getNomeSala() {
        return nomeSala;
    }

    public void setNomeSala(String nomeSala) {
        this.nomeSala = nomeSala;
    }
    
    
}
