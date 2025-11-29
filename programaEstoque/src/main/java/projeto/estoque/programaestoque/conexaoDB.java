package projeto.estoque.programaestoque;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class conexaoDB {
    private static final String URL = "jdbc:mysql://localhost:3306/db_estoque";
    private static final String usuario = "root";
    private static final String senha = "rootPass";

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

    private static void fecharConexao(Connection conexao){
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

    public static List<Map<String, String>> select(String query){
        List<Map<String, String>> registros = new ArrayList<>();
        try(Connection conexao = conectar(); PreparedStatement prepararQuery = conexao.prepareStatement(query);
            ResultSet resultado = prepararQuery.executeQuery()) {
            int quantidadeCol = resultado.getMetaData().getColumnCount();

            if(resultado.next()){
                while(resultado.next()){
                    Map<String, String> linha = new LinkedHashMap<>();
                    for (int col = 1; col <= resultado.getMetaData().getColumnCount(); col++) {
                        String coluna = resultado.getMetaData().getColumnName(col);
                        String valor = resultado.getString(col);
                        linha.put(coluna, valor);
                    }
                    registros.add(linha);
                }
            }
            fecharConexao(conexao);
        } catch(SQLException e) {
            System.err.println("Erro ao selecionar: " + e.getMessage());
        }
        return registros;
    }

    public static void alterQuery(String query){
        try(Connection conexao = conectar(); PreparedStatement prepararQuery = conexao.prepareStatement(query);) {

        } catch(SQLException e){
            System.err.println("Erro ao inserir: " + e.getMessage());
        }
    }

    public static boolean validarLogin(String usuario, String senha){
        String query = "SELECT * FROM usuario WHERE nome = ? AND senhaHash = ?";

        try (Connection conexao = conectar();
             PreparedStatement st = conexao.prepareStatement(query)) {

            st.setString(1, usuario);
            st.setString(2, senha);

            ResultSet rs = st.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.err.println("Erro ao validar login: " + e.getMessage());
            return false;
        }
    }
}
