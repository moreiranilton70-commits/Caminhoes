package sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Usuario;

public class UsuarioDAO {

    public UsuarioDAO() {
        criarTabela();
    }

    public void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS usuarios (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                usuario TEXT UNIQUE,
                senha TEXT,
                tipo TEXT
            )
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.execute();

            adicionarColunaSeNaoExistir(conn, "tipo");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela usuarios: " + e.getMessage());
        }
    }

    private void adicionarColunaSeNaoExistir(Connection conn, String coluna) {
        String sql = "ALTER TABLE usuarios ADD COLUMN " + coluna + " TEXT";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            // Se a coluna já existir, pode ignorar.
        }
    }

    public boolean validarLogin(String usuario, String senha) {
        criarTabela();

        String sql = "SELECT * FROM usuarios WHERE usuario = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erro ao validar login: " + e.getMessage());
            return false;
        }
    }

    public boolean usuarioExiste(String usuario) {
        criarTabela();

        String sql = "SELECT * FROM usuarios WHERE usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (SQLException e) {
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean cadastrarUsuario(String usuario, String senha) {
        criarTabela();

        if (usuarioExiste(usuario)) {
            return false;
        }

        String sql = "INSERT INTO usuarios (usuario, senha, tipo) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, senha);
            ps.setString(3, "USUARIO");

            ps.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarSenha(String usuario, String novaSenha) {
        criarTabela();

        String sql = "UPDATE usuarios SET senha = ? WHERE usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novaSenha);
            ps.setString(2, usuario);

            int linhasAlteradas = ps.executeUpdate();

            return linhasAlteradas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao alterar senha: " + e.getMessage());
            return false;
        }
    }

    public boolean alterarSenhaPorUsuario(String usuario, String novaSenha) {
        return alterarSenha(usuario, novaSenha);
    }

    public List<Usuario> listarTodos() {
        criarTabela();

        List<Usuario> lista = new ArrayList<>();

        String sql = """
            SELECT 
                id,
                usuario,
                tipo
            FROM usuarios
            ORDER BY id ASC
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();

                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setTipo(rs.getString("tipo"));

                lista.add(u);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
        }

        return lista;
    }

    public boolean excluir(int id) {
        criarTabela();

        String sql = "DELETE FROM usuarios WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int linhas = ps.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            return false;
        }
    }
}