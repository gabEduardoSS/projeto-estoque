package projeto.estoque.programaestoque;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Connection;

public class Controller {

    @FXML
    private TextField tfProduto;
    @FXML
    private TextField tfUnidadeMedida;
    @FXML
    private ComboBox<String> cbProduto;
    @FXML
    private Label lblAviso;
    @FXML
    private Button btnAdicionar;

    ConexaoDB conexaoDB = new ConexaoDB();
    Connection conexao = ConexaoDB.conexao;
    Utilitarios util = new Utilitarios();

    @FXML
    protected void CriarProduto() {

        String produto = tfProduto.getText().trim();
        String unidadeMedida = tfUnidadeMedida.getText().trim();

        cbProduto.getItems().add(produto);

        if (!produto.isEmpty() && !unidadeMedida.isEmpty()) {
            Task<Boolean> thread = new Task<Boolean>() {
                @Override
                protected Boolean call(){
                    return conexaoDB.adicionarProduto(conexao, produto, unidadeMedida);
                }
            };
            thread.setOnSucceeded(e -> {
                if (thread.getValue()) {
                    lblAviso.setText("Produto adicionado com sucesso!");
                } else {
                    lblAviso.setText("Erro ao adicionar o produto, tente novamente");
                }
                util.fade(lblAviso, "out", 3);
                btnAdicionar.setDisable(false);
            });

            thread.setOnFailed(e -> {
                btnAdicionar.setDisable(false);
            });

            new Thread(thread).start();
        } else{
            btnAdicionar.setDisable(false);
            lblAviso.setText("Preencha todos os campos");
            util.fade(lblAviso, "out", 3);
        }
    }
}
