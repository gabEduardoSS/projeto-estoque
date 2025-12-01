package projeto.estoque.programaestoque;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LauncherPrograma extends Application {
    ConexaoDB conexaoDB = new ConexaoDB();
    Connection conexao = ConexaoDB.conexao;
    private static LauncherPrograma instance;
    private Stage stage;
    public List<Map<String, String>> produtos = new ArrayList<>();

    public LauncherPrograma() {
        instance = this;
    }

    public static LauncherPrograma getInstance() {
        return instance;
    }
    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(LauncherPrograma.class.getResource("tela-login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void trocarTela(String fxml, Object... params) throws IOException {
        Parent pane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxml)));

        double x = stage.getX();
        double y = stage.getY();

        Scene scene = new Scene(pane);
        stage.setTitle("Cadastro de Produtos");
        stage.setScene(scene);
        stage.setX(x);
        stage.setY(y);
    }
}