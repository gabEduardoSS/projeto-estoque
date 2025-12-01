package projeto.estoque.programaestoque;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.io.IOException;
import java.util.Objects;

public class LauncherPrograma extends Application {


    private static LauncherPrograma instance;
    private Stage stage;
    /*
        public LauncherPrograma() {
            instance = this;
        }

        public static LauncherPrograma getInstance() {
            return instance;
        }*/
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LauncherPrograma.class.getResource("Cadastro.view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 620, 540);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void trocarTela(String fxml) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));

        double x = stage.getX();
        double y = stage.getY();

        Scene scene = new Scene(pane);
        stage.setTitle("Cadastro de Produtos");
        stage.setScene(scene);
        stage.setX(x);
        stage.setY(y);
    }

    @FXML
    public Button btnExtrato;

    @FXML
    public void MostrarExtrato(Action event) throws IOException {
        try {
            Stage stage = (Stage) btnExtrato.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("Extrato.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            System.err.println("Erro ao carregar extrato");
        }


    }

}

