package projeto.estoque.programaestoque;

import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Duration;

import java.sql.Connection;

public class ControlerLogin {
    @FXML public Label lblAviso;
    @FXML public TextField tfUsuario;
    @FXML public PasswordField tfSenha;
    @FXML public Button btnEntrar;
    ConexaoDB conexaoDB = new ConexaoDB();
    Connection conexao = conexaoDB.conectar();

    @FXML public void logar() {
        btnEntrar.setDisable(true);
        String stUsuario = tfUsuario.getText().trim();
        String stSenha = tfSenha.getText().trim();

        if (!stUsuario.isEmpty() && !stSenha.isEmpty()) {
            Task<Boolean> thread = new Task<Boolean>() {
                @Override
                protected Boolean call(){
                    return conexaoDB.validarLogin(conexao, stUsuario, stSenha);
                }
            };
            thread.setOnSucceeded(e -> {
                if (thread.getValue()) {
                    lblAviso.setText("Login efetuado com sucesso!");
                } else {
                    lblAviso.setText("Senha ou usuario incorretos");
                }
                fade(lblAviso, "out", 3);
                btnEntrar.setDisable(false);
            });

            thread.setOnFailed(e -> {
                btnEntrar.setDisable(false);
            });

            new Thread(thread).start();
        } else{
            btnEntrar.setDisable(false);
            lblAviso.setText("Preencha todos os campos");
            fade(lblAviso, "out", 3);
        }
    }
    public void fade(Node widget, String tipo, double duracao){
        double in = 0;
        double out = 0;
        if(tipo.equalsIgnoreCase("in")){
            in = 0;
            out = 1;
        } else if(tipo.equalsIgnoreCase("out")){
            in = 1;
            out = 0;
        }
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(duracao), widget);
        fadeOut.setFromValue(in);
        fadeOut.setToValue(out);
        fadeOut.play();

    }
}