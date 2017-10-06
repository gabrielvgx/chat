package Domain;

/**
 *
 * @author bella
 */
public class Mensagem {
    
    private String txtMensagem;
    private String usuario;
    private String dataTime;
    private String id_sala;

    public Mensagem(){}
    
    public Mensagem(String txtMensagem, String usuario, String id_sala) {
        this.txtMensagem = txtMensagem;
        this.usuario = usuario;
        this.id_sala = id_sala;
        
    }

    public String getId_sala() {
        return id_sala;
    }

    public void setId_sala(String id_sala) {
        this.id_sala = id_sala;
    }

    public String getTxtMensagem() {
        return txtMensagem;
    }

    public void setTxtMensagem(String txtMensagem) {
        this.txtMensagem = txtMensagem;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuário) {
        this.usuario = usuário;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    
    
}
