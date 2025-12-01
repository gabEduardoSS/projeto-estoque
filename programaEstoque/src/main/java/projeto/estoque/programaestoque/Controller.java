package projeto.estoque.programaestoque;

import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URL;

import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private TextField tfProduto;
    @FXML
    private TextField tfUnidadeMedida;
    @FXML
    private ComboBox<Produto> cbProduto;
    @FXML
    private Label lblAviso;
    @FXML
    private Button btnAdicionar;
    @FXML
    private Button btnRemover;
    @FXML
    private TextField tfQuantidade;
    @FXML
    Label lblObservacao;

    ConexaoDB conexaoDB = new ConexaoDB();
    Connection conexao = ConexaoDB.conexao;
    Utilitarios util = new Utilitarios();
    List<Map<String, String>> produtos = null;

    @FXML
    public void initialize() {
        pegarProdutos();
        System.out.println("foi");
        cbProduto.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                carregarDadosProduto(novo.toString());
            }
        });
    }

    @FXML
    protected void CriarProduto() {

        String produto = tfProduto.getText().trim();
        String unidadeMedida = tfUnidadeMedida.getText().trim();

        if (!produto.isEmpty() && !unidadeMedida.isEmpty() && unidadeMedida.length() <= 8) {
            Task<Boolean> thread = new Task<Boolean>() {
                @Override
                protected Boolean call(){
                    return conexaoDB.adicionarProduto(conexao, produto, unidadeMedida);
                }
            };
            thread.setOnSucceeded(e -> {
                if (thread.getValue()) {
                    pegarProdutos();
                    tfProduto.setText("");
                    tfUnidadeMedida.setText("");
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

        } else if (unidadeMedida.length() > 8) {
            btnAdicionar.setDisable(false);
            lblAviso.setText("O campo unidade de medida aceita no máximo 8 caracteres");
            util.fade(lblAviso, "out", 3);
        } else{
            btnAdicionar.setDisable(false);
            lblAviso.setText("Preencha todos os campos");
            util.fade(lblAviso, "out", 3);
        }
    }

    @FXML
    protected void pegarProdutos() {

        Task<List<Map<String, String>>> task = new Task<>() {
            @Override
            protected List<Map<String, String>> call() {
                return conexaoDB.pegarProdutos(conexao);
            }
        };

        task.setOnSucceeded(e -> {
            produtos = task.getValue();

            if (produtos == null) {
                System.out.println("Nenhum produto retornado do banco!");
                return;
            }

            cbProduto.getItems().clear();

            for (Map<String, String> produto : produtos) {
                String id = produto.get("id_produto");
                String nome = produto.get("nome");
                String quantidade = produto.get("quantidade");
                String unidadeMedida = produto.get("unidade_medida");
                System.out.println("Produto: " + id + ", " + nome);

                cbProduto.getItems().add(new Produto(id, nome, quantidade, unidadeMedida));
            }

            System.out.println("Produtos carregados: " + cbProduto.getItems().size());
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        // ⚠️ SEM ISSO, NADA EXECUTA!
        new Thread(task).start();
    }

    private void carregarDadosProduto(String produtoSelecionado) {
        Map<String, String> dados = produtos.stream()
                .filter(p -> {
                    String nome = p.get("nome");
                    String id = p.get("id");
                    return produtoSelecionado.contains(nome);
                })
                .findFirst()
                .orElse(null);

        if (dados != null) {
            lblObservacao.setText("Disponível: " + dados.get("quantidade") + "   Unidade de Medida: " + dados.get("unidade_medida"));
        }
    }

    @FXML
    protected void remover(){
        if(cbProduto.getSelectionModel().getSelectedIndex()!=-1){
            Produto p = cbProduto.getSelectionModel().getSelectedItem();
            boolean sucesso = conexaoDB.removerProduto(conexao, p.getId());
            if (sucesso) {
                cbProduto.getItems().remove(p);
            }
        }
    }

    @FXML
    protected void alterarQuantidade(){
        if(tfQuantidade.getText()!=""){
            Produto p = cbProduto.getSelectionModel().getSelectedItem();
            int novaQuantidade = Integer.parseInt(tfQuantidade.getText());

            boolean sucesso = conexaoDB.alterarQuantidadeProduto(conexao, p.getId(), novaQuantidade);

            if (sucesso) {
                tfQuantidade.setText("");
                p.setQuantidade(String.valueOf(novaQuantidade));
                lblObservacao.setText("Disponível: " + p.getQuantidade() + "   Unidade de Medida: " + p.getUnidadeMedida());
            }
        }
    }

    @FXML
    public void extrato() throws IOException {
        LauncherPrograma.getInstance().trocarTela("Extrato.fxml");
    }
}
