package chat.Domain;

/**
 * @author bella
 */
public class Usuario {
    
    private String nomeusuario;
    private String senha;
    private Long idUsuario;
    
    public Usuario() {}

    public Usuario(String nomeusuario, String senha) {
        this.nomeusuario = nomeusuario;
        this.senha = senha;
    }

    public String getNomeusuario() {
        return nomeusuario;
    }

    public void setNomeusuario(String nomeusuario) {
        this.nomeusuario = nomeusuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
}
