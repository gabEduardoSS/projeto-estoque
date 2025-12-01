package projeto.estoque.programaestoque;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;

import java.util.UUID;


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
        //Seção ID
        String Id = UUID.randomUUID().toString();
        String IdFormatado = Id.substring(0, 8).toUpperCase();

        String produto = tfProduto.getText().trim();
        String unidadeMedida = tfUnidadeMedida.getText().trim();

        cbProduto.getItems().add(produto);

        //debugar
        System.out.println(String.format("O produto: %s tem o ID: %s", produto, IdFormatado));


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

    @FXML
    public Button btnExtrato;

    public void MostrarExtrato(ActionEvent event) {
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
