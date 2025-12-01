package projeto.estoque.programaestoque;

import java.util.ArrayList;
import java.util.List;

public class Produto {
    private String id_produto;
    private String nome;
    private String quantidade;
    private String unidade_medida;

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public Produto(String id, String nome, String quantidade, String unidadeMedida) {
        this.id_produto = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.unidade_medida = unidadeMedida;
    }

    public int getId() {
        return Integer.parseInt(id_produto);
    }

    public String getNome() {
        return nome;
    }

    public String getUnidadeMedida() {
        return unidade_medida;
    }

    public String getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return nome;
    }
}