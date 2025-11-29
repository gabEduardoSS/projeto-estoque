package projeto.estoque.programaestoque;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LauncherPrograma extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(
                getClass().getResource("/projeto/estoque/programaestoque/Cadastro-view.fxml")
        );
        Scene scene = new Scene(fxmlLoader.load(), 428, 490);
        stage.setScene(scene);
        stage.show();

    }
}
