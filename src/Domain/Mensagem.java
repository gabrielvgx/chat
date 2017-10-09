package Domain;

/**
 *
 * @author bella
 */
public class Mensagem {
    
    private int idMensagem;
    private String txtMensagem;
    private String dateTime;
    private int idUsuario;
    private int idSala;

    public Mensagem(){}
    
    public Mensagem(String txtMensagem, int idUsuario, int idSala) {
        this.txtMensagem = txtMensagem;
        this.idUsuario = idUsuario;
        this.idSala = idSala;
        
    }

    public int getIdSala() {
        return idSala;
    }

    public void setIdSala(int idSala) {
        this.idSala = idSala;
    }

    public String getTxtMensagem() {
        return txtMensagem;
    }

    public void setTxtMensagem(String txtMensagem) {
        this.txtMensagem = txtMensagem;
    }

    public int getUsuario() {
        return idUsuario;
    }

    public void setUsuario(int idUsuário) {
        this.idUsuario = idUsuário;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public int getIdMensagem() {
        return idMensagem;
    }

    public void setIdMensagem(int idMensagem) {
        this.idMensagem = idMensagem;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
    
    
    
}
