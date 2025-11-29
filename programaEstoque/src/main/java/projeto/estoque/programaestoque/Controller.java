package projeto.estoque.programaestoque;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Controller {

    @FXML
    private TextField tfProduto;

    @FXML
    private TextField tfUnidadeMedida;

    @FXML
    private ComboBox<String> cbProduto;

    @FXML
    protected void CriarProduto() {

        String Produto = tfProduto.getText();
        String UnidadeMedida = tfUnidadeMedida.getText();

        cbProduto.getItems().add(Produto);

    }
}
