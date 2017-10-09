package Domain;

/**
 * @author bella
 */
public class Usuario {

    private String nomeusuario;
    private String senha;
    private Long idUsuario;
    private String proprietarioSala;
    private String participanteSala;

    public Usuario() {
    }

    public Usuario(String nomeusuario, String senha) {
        this.nomeusuario = nomeusuario;
        this.senha = senha;
    }

    public Usuario(String nomeusuario, String senha, String proprietarioSala) {
        this.nomeusuario = nomeusuario;
        this.senha = senha;
        this.proprietarioSala = proprietarioSala;
    }

    public Usuario(String nomeusuario, String senha, String proprietarioSala, String participanteSala) {
        this.nomeusuario = nomeusuario;
        this.senha = senha;
        this.proprietarioSala = proprietarioSala;
        this.participanteSala = participanteSala;
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

    public void setProprietarioSala(String proprietarioSala) {
        this.proprietarioSala = proprietarioSala;
    }

    public String getProprietarioSala() {
        return proprietarioSala;
    }

    public void setParticipanteSala(String participanteSala) {
        this.participanteSala = participanteSala;
    }

    public String getParticipanteSala() {
        return participanteSala;
    }

    public void adicionarParticipanteSala(String sala) {
        if (participanteSala == null) {
            this.participanteSala = sala;
        } else {
            this.participanteSala += "," + sala;
        }
    }
}
