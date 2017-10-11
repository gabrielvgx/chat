package chatLogin;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author M
 */
public class Cadastro extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLLogin.fxml"));
            Scene scene = new Scene(root);
          
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    public static void main(String[] args) {
        String teste = "Gabriel Victor Guima";
        System.out.println("length = " + teste.length());
        launch(args);
    }
    
}
