package projeto.estoque.programaestoque;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ConexaoDB {
    private static final String URL = "jdbc:mysql://localhost:3306/db_estoque";
    private static final String usuario = "root";
    private static final String senha = "rootPass";

    public static Connection conexao = conectar();


    public static Connection conectar(){
        Connection conexao = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            conexao = DriverManager.getConnection(URL, usuario, senha);
            System.out.println("DB conectado");
        } catch(ClassNotFoundException e) {
            System.err.println("JDBC driver não encontrado no classpath");
            System.out.println(e.getMessage());
        } catch(SQLException e){
            System.err.println("Falha ao conectar no DB");
            System.out.println(e.getMessage());
        }
        return conexao;
    }

    private void fecharConexao(Connection conexao){
        if(conexao != null){
            try {
                conexao.close();
                System.out.println("conexão fechada");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão");
                System.out.println(e.getMessage());
            }
        }
    }

    public List<Map<String, String>> select(Connection conexao, String query){
        List<Map<String, String>> registros = new ArrayList<>();
        try(PreparedStatement prepararQuery = conexao.prepareStatement(query);
            ResultSet resultado = prepararQuery.executeQuery()) {
            int quantidadeCol = resultado.getMetaData().getColumnCount();

            while (resultado.next()) {
                Map<String, String> linha = new LinkedHashMap<>();
                for (int col = 1; col <= quantidadeCol; col++) {
                    String coluna = resultado.getMetaData().getColumnName(col);
                    String valor = resultado.getString(col);
                    linha.put(coluna, valor);
                }
                registros.add(linha);
            }
        } catch(SQLException e) {
            System.err.println("Erro ao selecionar: " + e.getMessage());
        }
        return registros;
    }

    public boolean validarLogin(Connection conexao, String usuario, String senha){
        String query = "SELECT * FROM usuario WHERE nome = ? AND senhaHash = ?";

        try (PreparedStatement st = conexao.prepareStatement(query)) {

            st.setString(1, usuario);
            st.setString(2, senha);

            ResultSet rs = st.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return false;
        }
    }

    public Boolean adicionarProduto(Connection conexao, String nome, String unidadeMedida){
        String query = "INSERT INTO produto(nome, unidade_medida, quantidade) VALUES (?, ?, 0)";

        try (PreparedStatement st = conexao.prepareStatement(query)) {

            st.setString(1, nome);
            st.setString(2, unidadeMedida);

            int rs = st.executeUpdate();

            return rs > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar o produto: " + e.getMessage());
            return false;
        }
    }

    public List<Map<String, String>> pegarProdutos(Connection conexao){
        String query = "SELECT * FROM produto";
        return select(conexao, query);
    }

    public boolean removerProduto(Connection conexao, int idProduto) {
        String query = "DELETE FROM produto WHERE id_produto = ?";

        try (PreparedStatement st = conexao.prepareStatement(query)) {
            st.setInt(1, idProduto);

            int linhasAfetadas = st.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao remover o produto: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarQuantidadeProduto(Connection conexao, int idProduto, int novaQuantidade) {
        String query = "UPDATE produto SET quantidade = ? WHERE id_produto = ?";

        try (PreparedStatement st = conexao.prepareStatement(query)) {
            st.setInt(1, novaQuantidade);
            st.setInt(2, idProduto);

            int linhasAfetadas = st.executeUpdate();
            return linhasAfetadas > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao alterar a quantidade do produto: " + e.getMessage());
            return false;
        }
    }
}
