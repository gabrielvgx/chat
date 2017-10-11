package Domain;

/**
 * @author bella
 */
public class Usuario {

    private int idUsuario;
    private String nomeUsuario;
    private String senha;
    private int idSala;
    private boolean admSala;

    public Usuario() {
    }

    public Usuario(String nomeusuario, String senha) {
        this.nomeUsuario = nomeusuario;
        this.senha = senha;
    }

    public Usuario(String nomeusuario, String senha, boolean admSala) {
        this.nomeUsuario = nomeusuario;
        this.senha = senha;
        this.admSala = admSala;
    }

    public Usuario(String nomeusuario, String senha, boolean admSala, int idSala) {
        this.nomeUsuario = nomeusuario;
        this.senha = senha;
        this.admSala = admSala;
        this.idSala = idSala;
    }
    
    public int getIdUsuario() {
        return idUsuario;
    }
    
    public void setIdUsuario(int idUsuario){
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public boolean getAdmSala() {
        return this.admSala;
    }

    public void setAdmSala(boolean admSala) {
        this.admSala = admSala;
    }

    public int getIdSala() {
        return this.idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }
}
