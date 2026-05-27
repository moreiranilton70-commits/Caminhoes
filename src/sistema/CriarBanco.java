package sistema;

import java.sql.Connection;
import java.sql.Statement;

import sistema.dao.Conexao;

public class CriarBanco {

    public static void criarTabelas() {

        String sqlCadastro = "CREATE TABLE IF NOT EXISTS cadastro ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "data TEXT,"
                + "placa TEXT,"
                + "numeroOF TEXT,"
                + "horaCadastro TEXT,"
                + "numeroPager TEXT,"
                + "ofTroca TEXT,"
                + "status TEXT,"
                + "autorizacao TEXT,"
                + "horaAutorizacao TEXT,"
                + "observacao TEXT,"
                + "usuario TEXT,"
                + "usuarioAlteracao TEXT,"
                + "horaAlteracao TEXT"
                + ")";

        String sqlUsuario = "CREATE TABLE IF NOT EXISTS usuario ("
                + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "usuario TEXT UNIQUE,"
                + "senha TEXT,"
                + "tipo TEXT"
                + ")";

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            stmt.execute(sqlCadastro);
            stmt.execute(sqlUsuario);

            try {
                stmt.execute("ALTER TABLE cadastro ADD COLUMN usuarioAlteracao TEXT");
            } catch (Exception e) {
                // coluna já existe
            }

            try {
                stmt.execute("ALTER TABLE cadastro ADD COLUMN horaAlteracao TEXT");
            } catch (Exception e) {
                // coluna já existe
            }

            System.out.println("Tabelas criadas/verificadas com sucesso.");

        } catch (Exception e) {
            System.out.println("Erro ao criar tabelas: " + e.getMessage());
            e.printStackTrace();
        }
    }
}