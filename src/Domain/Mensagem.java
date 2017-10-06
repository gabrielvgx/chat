package chat.Domain;

/**
 *
 * @author bella
 */
public class Mensagem {
    
    private String txtMensagem;
    private String usuário;
    private String dataTime;

    public Mensagem(){}
    
    public Mensagem(String txtMensagem, String usuário) {
        this.txtMensagem = txtMensagem;
        this.usuário = usuário;
        this.dataTime = dataTime;
    }

    public String getTxtMensagem() {
        return txtMensagem;
    }

    public void setTxtMensagem(String txtMensagem) {
        this.txtMensagem = txtMensagem;
    }

    public String getUsuário() {
        return usuário;
    }

    public void setUsuário(String usuário) {
        this.usuário = usuário;
    }

    public String getDataTime() {
        return dataTime;
    }

    public void setDataTime(String dataTime) {
        this.dataTime = dataTime;
    }
    
    
}
