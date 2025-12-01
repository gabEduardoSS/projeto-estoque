package projeto.estoque.programaestoque;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class extratoController {

    @FXML
    private TableView<Produto> tabela;

    @FXML
    private TableColumn<Produto, Integer> colunaID;

    @FXML
    private TableColumn<Produto, String> colunaNome;

    @FXML
    private TableColumn<Produto, String> colunaUN;

    @FXML
    private TableColumn<Produto, String> colunaQuantidade;

    private ConexaoDB conexaoDB;

    @FXML
    public void initialize() {
        conexaoDB = new ConexaoDB();

        // Configura as colunas do TableView
        colunaID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaUN.setCellValueFactory(new PropertyValueFactory<>("unidadeMedida"));
        colunaQuantidade.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Carrega os produtos do banco e preenche a tabela
        carregarTabela();
    }

    private void carregarTabela() {
        List<Map<String, String>> produtosDB = conexaoDB.pegarProdutos(ConexaoDB.conexao);
        ObservableList<Produto> produtos = FXCollections.observableArrayList();

        for (Map<String, String> linha : produtosDB) {
            Produto p = new Produto(
                    linha.get("id_produto"),
                    linha.get("nome"),
                    linha.get("quantidade"),
                    linha.get("unidade_medida")
            );
            produtos.add(p);
        }

        tabela.setItems(produtos);
    }

    @FXML
    protected void retornar() throws IOException {
        LauncherPrograma.getInstance().trocarTela("Cadastro.view.fxml");
    }
}
