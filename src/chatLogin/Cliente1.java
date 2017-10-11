package chatLogin;

import Domain.ExcecaoPersistencia;
import Domain.Mensagem;
import Domain.Sala;
import Domain.Usuario;
import Service.PersisteMsg;
import Service.PersisteSala;
import Service.PersisteUsuario;
import core.*;
import java.awt.AWTException;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Cliente1 implements java.io.Serializable{

    private  ObjectOutputStream escritor;
    private ObjectInputStream leitor;
    private AnchorPane painelPrincipal;
    private Socket cliente = null;
    private PersisteUsuario usr = new PersisteUsuario();
    private PersisteSala sala = new PersisteSala();
    private PersisteMsg msg = new PersisteMsg();
    public Cliente1() {
    }

    /**
     * Preenche a lista de usuarios
     *
     * @param usuarios
     * @param painelParticipantes
     */
    protected void preencherListaUsuarios(ArrayList<Usuario> usuarios, AnchorPane painelParticipantes) {
        ArrayList<Usuario> usuariosSala = usuarios;
        painelParticipantes.getChildren().clear();
        double x1 = painelParticipantes.getLayoutX() + 20;
        double y1 = painelParticipantes.getLayoutY() + 10;
        for (Usuario usuariosSala1 : usuariosSala) {
            RadioButton r = new RadioButton(usuariosSala1.getNomeUsuario());
            r.setLayoutX(x1);
            r.setLayoutY(y1);
            painelParticipantes.getChildren().add(r);
            y1 += 20;
            painelParticipantes.setPrefHeight(y1);
        }
    }
    
    protected void preencherListaMensagem(ArrayList<Mensagem> mensagem, AnchorPane Aconversas) {
        ArrayList<Mensagem> mensagemSala = mensagem;
        Aconversas.getChildren().clear();
        double x1 = Aconversas.getLayoutX() + 20;
        double y1 = Aconversas.getLayoutY() + 10;
        for (Mensagem mensagemSala1 : mensagemSala) {
            Label l = new Label(mensagemSala1.getTxtMensagem());
            l.setLayoutX(x1);
            l.setLayoutY(y1);
            Aconversas.getChildren().add(l);
            y1 += 20;
            Aconversas.setPrefHeight(y1);
        }
    }

    public void carregarTelaChat(String login, AnchorPane painelPrincipal) throws IOException, ExcecaoPersistencia {

        painelPrincipal.getChildren().clear();
        AnchorPane novaTela = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        painelPrincipal.setTopAnchor(novaTela, 0.0);
        painelPrincipal.setBottomAnchor(novaTela, 0.0);
        painelPrincipal.setLeftAnchor(novaTela, 0.0);
        painelPrincipal.setRightAnchor(novaTela, 0.0);
        painelPrincipal.getChildren().add(novaTela);
        //painelPrincipal.requestLayout();
        AnchorPane teste = (AnchorPane) novaTela.getChildren().get(0);

        Button botaoEnviar = (Button) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) novaTela.getChildren().get(0))
                .getChildren().get(0)).getChildren().get(0)).getItems().get(1)).getChildren().get(0)).getItems().get(1)).getChildren().get(0);
        botaoEnviar.setOnAction(event -> {

            RadioButton privado = (RadioButton) (((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) (((AnchorPane) ((AnchorPane) ((AnchorPane) painelPrincipal.getChildren().get(0)).getChildren().get(0)).getChildren()
                    .get(0)).getChildren().get(0))).getItems().get(0)).getChildren().get(0)).getItems().get(1)).getChildren().get(0));

            if (!privado.isSelected()) {
                try {
                    
                    if (usr.getUserLogin(login, null) == null) {
                        gerarNotificacao("Ops!", "Crie uma sala ou entre em uma existente para enviar uma mensagem!");
                    } else {
                        escritor.writeObject(Comandos.MENSAGEM + login);
                        escritor.writeObject(((TextArea) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) novaTela.getChildren().get(0))
                                .getChildren().get(0)).getChildren().get(0)).getItems().get(1)).getChildren().get(0)).getItems().get(1)).getChildren().get(1)).getText());
                        
                        AnchorPane Aconversas = (AnchorPane) ((TitledPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) (((AnchorPane) (((AnchorPane) (((AnchorPane) (painelPrincipal
                                .getChildren().get(0))).getChildren().get(0))).getChildren().get(0)))
                                .getChildren().get(0))).getItems().get(1)).getChildren().get(0)).getItems().get(0)).getChildren().get(0)).getContent();

                        Label msg = new Label(leitor.readObject().toString());
                        Usuario user = usr.getUserLogin(login, null);
                        this.msg.Envia_Msg(new Mensagem(msg.getText(),user.getIdUsuario(),user.getIdSala()));
                        msg.setLayoutX(Aconversas.getLayoutX()+10);
                        if(!Aconversas.getChildren().isEmpty()){
                        msg.setLayoutY(Aconversas.getChildren().get(Aconversas.getChildren().size()-1).getLayoutY()+20);
                        
                        }else{
                            msg.setLayoutY(Aconversas.getLayoutY()+20);
                        }
                        Aconversas.getChildren().add(msg);
                    }

                } catch (IOException | ExcecaoPersistencia ex) {
                    System.out.println("ID 08");
                    System.out.println(ex.getMessage());
                } catch (ClassNotFoundException ex) {
                    System.out.println("ID 09");
                    System.out.println(ex.getMessage());
                }
            }
        }
        );
        Label criarSala = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(3)));
        Label sair = ((Label) (((AnchorPane) (teste.getChildren().get(1))).getChildren().get(2)));
        sair.setOnMouseClicked(((event -> {
            try {
                gerarNotificacao("Obrigado por usar nosso Software", "Volte Sempre, " + login + "!");
                if (usr.getUserLogin(login, null) != null) {
                    Usuario aux = usr.getUserLogin(login, null);
                    usr.excluir(login);
                    if (usr.listarUsuarioSala(aux.getIdSala()).isEmpty()) {
                        sala.excluir(sala.getSala(aux.getIdSala()).getNomeSala());
                    }
                }
                System.exit(0);
            } catch (ExcecaoPersistencia ex) {
                System.out.println("ID 10");
                System.out.println(ex.getMessage());
            }
        })));

        criarSala.setOnMouseClicked(((event) -> {
            try {
                painelPrincipal.getChildren().clear();
                AnchorPane novaSala = (AnchorPane) FXMLLoader.load(getClass().getResource("FXMLNovaSala.fxml"));
                painelPrincipal.getChildren().add(novaSala);
                AnchorPane a = (AnchorPane) painelPrincipal.getChildren().get(0);
                Button adicionar = (Button) a.getChildren().get(0);

                adicionar.setOnMouseClicked(((MouseEvent -> {
                    String nomeSala = "";
                    nomeSala = ((TextArea) novaSala.getChildren().get(2)).getText();

                    if (nomeSala.length() > 15) {
                        Label notificar = ((Label) a.getChildren().get(3));
                        notificar.setText("Máximo de 15 caracteres");
                    } else {
                        try {
                            sala.cadastrar(new Sala(nomeSala));
                            Usuario aux = new Usuario(login, null, true, sala.getSala(nomeSala).getIdSala());
                            usr.cadastrar(aux);
                            painelPrincipal.getChildren().clear();
                            painelPrincipal.getChildren().add(novaTela);
                            Text titulo = (Text) (((AnchorPane) (((AnchorPane) novaTela.getChildren().get(0)).getChildren().get(1))).getChildren().get(1));
                            titulo.setText(nomeSala);
                            if (nomeSala.length() > 10) {
                                titulo.setStyle("-fx-font-size: 16");
                            }
                        } catch (ExcecaoPersistencia ex) {
                            System.out.println("ID 11");
                            System.out.println(ex.getMessage());
                        }
                    }
                })));

            } catch (IOException ex) {
                System.out.println("ID 05");
                System.out.println(ex.getMessage());
            }

        }));

        SplitPane painel = (SplitPane) (((AnchorPane) teste.getChildren().get(0)).getChildren().get(0));
        AnchorPane painelSalas = (AnchorPane) (((ScrollPane) ((AnchorPane) ((TitledPane) (((AnchorPane) ((SplitPane) (((AnchorPane) ((SplitPane) (((AnchorPane) painel.getItems().get(0))
                .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getContent()).getChildren().get(0))).getContent();

        AnchorPane painelParticipantes = (AnchorPane) ((ScrollPane) ((TitledPane) (((AnchorPane) ((SplitPane) (((AnchorPane) ((SplitPane) (((AnchorPane) painel.getItems().get(0))
                .getChildren().get(0))).getItems().get(0)).getChildren().get(0))).getItems().get(1)).getChildren().get(0))).getContent()).getContent();


        double x = painelSalas.getLayoutX() + 20;
        double y = painelSalas.getLayoutY() + 10;
        ArrayList<Sala> salasDisponiveis = sala.listarSala();
        ToggleGroup grupo = new ToggleGroup();

        for (int i = 0; i < salasDisponiveis.size(); i++) {
            if (usr.listarUsuarioSala(salasDisponiveis.get(i).getIdSala()).isEmpty()) {
                sala.excluir(salasDisponiveis.get(i).getNomeSala());
            } else {
                RadioButton r = new RadioButton(salasDisponiveis.get(i).getNomeSala());
                r.setToggleGroup(grupo);
                r.setLayoutX(x);
                r.setLayoutY(y);
                r.setOnAction(((new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        try {
                            if (usr.getUserLogin(login, null) == null) {
                                usr.cadastrar(new Usuario(login, null, false, sala.getSala(r.getText()).getIdSala()));
                            }
                            ArrayList<Usuario> usuarios = usr.listarUsuarioSala(sala.getSala(r.getText()).getIdSala());
                            ArrayList<Mensagem> mensagens = msg.Mostra_msg(sala.getSala(r.getText()).getIdSala());
                            preencherListaUsuarios(usuarios, painelParticipantes);
                             AnchorPane Aconversas = (AnchorPane) ((TitledPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) (((AnchorPane) (((AnchorPane) (((AnchorPane) (painelPrincipal
                                .getChildren().get(0))).getChildren().get(0))).getChildren().get(0)))
                                .getChildren().get(0))).getItems().get(1)).getChildren().get(0)).getItems().get(0)).getChildren().get(0)).getContent();

                            preencherListaMensagem(mensagens, Aconversas);
                            escritor.writeObject(Comandos.LISTA_USUARIOS);
                        } catch (ExcecaoPersistencia ex) {
                            System.out.println("ID 12");
                            System.out.println(ex.getMessage());
                        } catch (IOException ex) {
                            Logger.getLogger(Cliente1.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                })));

                painelSalas.getChildren().add(r);
                y += 20;
                painelSalas.setPrefHeight(y);
            }
        }
    }

    protected void iniciarEscritor(String login, AnchorPane painelPrincipal) throws IOException, ExcecaoPersistencia {//ActionOnClicked do botao enviar mensagem

        carregarTelaChat(login, painelPrincipal);
        AnchorPane novaTela = (AnchorPane) painelPrincipal.getChildren().get(0);

        TextArea mensagem = (TextArea) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) novaTela.getChildren().get(0))
                .getChildren().get(0)).getChildren().get(0)).getItems().get(1)).getChildren().get(0)).getItems().get(1)).getChildren().get(1);
        if (mensagem.getText().isEmpty()) {
            return;
        }
        SplitPane s = (SplitPane) mensagem.getParent().getParent().getParent().getParent().getParent().getParent();
        AnchorPane terceiroPane = (AnchorPane) s.getParent();//A1A1_A1

        AnchorPane participantes = (AnchorPane) (((ScrollPane) (((TitledPane) ((AnchorPane) (((SplitPane) ((AnchorPane) ((SplitPane) (((AnchorPane) (((SplitPane) (terceiroPane.getChildren().get(0))).getItems().get(0)))
                .getChildren().get(0))).getItems().get(0)).getChildren().get(0)).getItems().get(1))).getChildren().get(0)).getContent())).getContent());

        RadioButton privado = (RadioButton) (((AnchorPane) ((SplitPane) ((AnchorPane) s.getItems().get(0))
                .getChildren().get(0)).getItems().get(1)).getChildren().get(0));
        if (privado.isSelected()) {
            ArrayList<RadioButton> usuarioSalaSelecionado = new ArrayList<>();
            for (int i = 0; i < participantes.getChildren().size(); i++) {
                if (participantes.getChildren().get(i) instanceof RadioButton) {
                    if (((RadioButton) participantes.getChildren().get(i)).isSelected()) {
                        usuarioSalaSelecionado.add((RadioButton) participantes.getChildren().get(i));
                    }
                }
            }
        }
    }

    public void iniciarChat() {//Continua na classe cliente

        //Socket cliente = null;
        try {
            cliente = new Socket("127.0.0.1", 9999);
            escritor = new ObjectOutputStream(cliente.getOutputStream());
            leitor = new ObjectInputStream(cliente.getInputStream());
        } catch (UnknownHostException e) {
            System.out.println("ID 06");
            System.out.println("o endereço passado é inválido");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("ID 07");
            System.out.println("o servidor pode estar fora ar");
            e.printStackTrace();
        }

    }

    protected void atualizarListaUsuarios() throws IOException {
        escritor.writeObject(Comandos.LISTA_USUARIOS);
    }

    private void gerarNotificacao(String titulo, String subTitulo) {
        BufferedImage trayImage = null;
        File a = new File("icon.png");
        String path = "";
        path = a.getAbsolutePath().replace('\\', '/');

        String[] aux = path.split("/");
        path = "";
        for (int i = 0; i < aux.length - 1; i++) {
            path += aux[i] + "/";
        }
        try {
            trayImage = ImageIO.read(new FileInputStream(new File(path + "icones/icon.png")));
            TrayIcon tray = new TrayIcon(trayImage);
            SystemTray sysTray = SystemTray.getSystemTray();
            tray.setImageAutoSize(true);
            sysTray.add(tray);
            tray.displayMessage(titulo, subTitulo, TrayIcon.MessageType.INFO);
        } catch (AWTException | FileNotFoundException e) {
            System.out.println("ID 01");
            System.out.println(e.getMessage());
        } catch (IOException ex) {
            System.out.println("ID 02");
            System.out.println(ex.getMessage());
        }

    }

    protected int inicarLeitor(String login, AnchorPane painelPrincipal) throws IOException {
        this.painelPrincipal = painelPrincipal;
        try {
            while (true) {
                String mensagem = leitor.readObject().toString();//Comandos::Login
                if (mensagem == null || mensagem.isEmpty()) {
                    continue;
                }

                // recebe o texto
                if (mensagem.equals(Comandos.LISTA_USUARIOS)) {
                    AnchorPane painelParticipantes = ((AnchorPane) ((ScrollPane) ((TitledPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((SplitPane) ((AnchorPane) ((AnchorPane) (((AnchorPane) painelPrincipal.getChildren().get(0)).getChildren().get(0)))
                            .getChildren().get(0)).getChildren().get(0)).getItems().get(0))
                            .getChildren().get(0)).getItems().get(0)).getChildren().get(0)).getItems().get(1))
                            .getChildren().get(0)).getContent()).getContent());
                    ArrayList<Usuario> usuarios = (ArrayList<Usuario>) leitor.readObject();
                    
                    preencherListaUsuarios(usuarios, painelParticipantes);
                } else if (mensagem.equals(Comandos.LOGIN)) {
                    escritor.writeObject(login);
                } else if (mensagem.equals(Comandos.LOGIN_NEGADO)) {
                    gerarNotificacao("Ops...", "Login ja esta em uso ou é inválido!");
                    return -1;
                } else if (mensagem.equals(Comandos.LOGIN_ACEITO)) {

                } else {
                    JOptionPane.showMessageDialog(null, mensagem);
                    break;
                    
                }
            }

        } catch (IOException e) {
            System.out.println("ID 03");
            System.out.println("impossivel ler a mensagem do servidor" + e.getMessage());
        } catch (ClassNotFoundException ex) {
            System.out.println("ID 04");
            System.out.println(ex.getMessage());
        }
        return 0;
    }
}
