package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import sistema.dao.Conexao;

public class CriarAdmin {

    public static void main(String[] args) {

        try (Connection conn = Conexao.conectar();
             Statement stmt = conn.createStatement()) {

            criarTabelaUsuarios(stmt);
            adicionarColunaSeNaoExistir(conn, "usuarios", "tipo", "TEXT");

            criarOuAtualizarAdmin(conn);

            System.out.println("Admin criado/atualizado com sucesso.");
            System.out.println("Usuário: admin");
            System.out.println("Senha: 123");
            System.out.println("Tipo: ADMIN");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void criarTabelaUsuarios(Statement stmt) throws SQLException {

        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT UNIQUE,
                senha TEXT,
                tipo TEXT
            )
        """;

        stmt.execute(sql);
    }

    private static void adicionarColunaSeNaoExistir(Connection conn, String tabela, String coluna, String tipo) {

        if (colunaExiste(conn, tabela, coluna)) {
            return;
        }

        String sql = "ALTER TABLE " + tabela + " ADD COLUMN " + coluna + " " + tipo;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Coluna adicionada: " + coluna);
        } catch (SQLException e) {
            System.out.println("Erro ao adicionar coluna " + coluna + ": " + e.getMessage());
        }
    }

    private static boolean colunaExiste(Connection conn, String tabela, String coluna) {

        String sql = "PRAGMA table_info(" + tabela + ")";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nomeColuna = rs.getString("name");

                if (coluna.equalsIgnoreCase(nomeColuna)) {
                    return true;
                }
            }

        } catch (SQLException e) {
            System.out.println("Erro ao verificar coluna: " + e.getMessage());
        }

        return false;
    }

    private static void criarOuAtualizarAdmin(Connection conn) throws SQLException {

        if (adminExiste(conn)) {
            atualizarAdmin(conn);
        } else {
            inserirAdmin(conn);
        }
    }

    private static boolean adminExiste(Connection conn) throws SQLException {

        String sql = "SELECT id FROM usuarios WHERE usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "admin");

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private static void inserirAdmin(Connection conn) throws SQLException {

        String sql = "INSERT INTO usuarios (usuario, senha, tipo) VALUES (?, ?, ?)";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "admin");
            ps.setString(2, "123");
            ps.setString(3, "ADMIN");

            ps.executeUpdate();
        }
    }

    private static void atualizarAdmin(Connection conn) throws SQLException {

        String sql = "UPDATE usuarios SET senha = ?, tipo = ? WHERE usuario = ?";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "123");
            ps.setString(2, "ADMIN");
            ps.setString(3, "admin");

            ps.executeUpdate();
        }
    }
}