package sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Usuario;

public class UsuarioDAO {

    public void inserir(Usuario u) {

        String sql = "INSERT INTO usuario (usuario, senha, tipo) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, u.getUsuario());
            ps.setString(2, u.getSenha());
            ps.setString(3, u.getTipo());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir usuário: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean existeUsuario(String usuario) {

        String sql = "SELECT id FROM usuario WHERE usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);

            ResultSet rs = ps.executeQuery();

            return rs.next();

        } catch (Exception e) {
            System.out.println("Erro ao verificar usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public Usuario logar(String usuario, String senha) {

        String sql = "SELECT * FROM usuario WHERE usuario = ? AND senha = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, senha);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));
                return u;
            }

        } catch (Exception e) {
            System.out.println("Erro ao fazer login: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }

    public List<Usuario> listarTodos() {

        List<Usuario> lista = new ArrayList<>();

        String sql = "SELECT id, usuario, senha, tipo FROM usuario ORDER BY usuario ASC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Usuario u = new Usuario();
                u.setId(rs.getInt("id"));
                u.setUsuario(rs.getString("usuario"));
                u.setSenha(rs.getString("senha"));
                u.setTipo(rs.getString("tipo"));

                lista.add(u);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar usuários: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public boolean alterarSenhaPorUsuario(String usuario, String novaSenha) {

        String sql = "UPDATE usuario SET senha = ? WHERE usuario = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, novaSenha);
            ps.setString(2, usuario);

            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (Exception e) {
            System.out.println("Erro ao alterar senha: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {

        String sql = "DELETE FROM usuario WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (Exception e) {
            System.out.println("Erro ao excluir usuário: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}