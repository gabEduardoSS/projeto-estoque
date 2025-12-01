package projeto.estoque.programaestoque;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Connection;

public class ControlerLogin {

    @FXML
    public Label lblAviso;
    @FXML
    public TextField tfUsuario;
    @FXML
    public PasswordField tfSenha;
    @FXML
    public Button btnEntrar;
    ConexaoDB conexaoDB = new ConexaoDB();
    Connection conexao = ConexaoDB.conexao;
    Utilitarios util = new Utilitarios();

    @FXML
    public void logar() {
        btnEntrar.setDisable(true);
        String stUsuario = tfUsuario.getText().trim();
        String stSenha = tfSenha.getText().trim();

        if (!stUsuario.isEmpty() && !stSenha.isEmpty()) {
            Task<Boolean> thread = new Task<Boolean>() {
                @Override
                protected Boolean call() {
                    return conexaoDB.validarLogin(conexao, stUsuario, stSenha);
                }
            };
            /*thread.setOnSucceeded(e -> {
                if (thread.getValue()) {
                    try {
                        LauncherPrograma.getInstance().trocarTela("Cadastro.view.fxml");
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } else {
                    lblAviso.setText("Senha ou usuario incorretos");
                }
                util.fade(lblAviso, "out", 3);
                btnEntrar.setDisable(false);
            });

            thread.setOnFailed(e -> {
                btnEntrar.setDisable(false);
            });

            new Thread(thread).start();
        } else{
            btnEntrar.setDisable(false);
            lblAviso.setText("Preencha todos os campos");
            util.fade(lblAviso, "out", 3);
        }*/

        }
    }
}



