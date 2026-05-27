package sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Cadastro;

public class CadastroDAO {

    public void inserir(Cadastro c) {

        String sql = "INSERT INTO cadastro "
                + "(data, placa, numeroOF, horaCadastro, numeroPager, ofTroca, status, autorizacao, horaAutorizacao, observacao, usuario) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getData());
            ps.setString(2, c.getPlaca());
            ps.setString(3, c.getNumeroOF());
            ps.setString(4, c.getHoraCadastro());
            ps.setString(5, c.getNumeroPager());
            ps.setString(6, c.getOfTroca());
            ps.setString(7, c.getStatus());
            ps.setString(8, c.getAutorizacao());
            ps.setString(9, c.getHoraAutorizacao());
            ps.setString(10, c.getObservacao());
            ps.setString(11, c.getUsuario());

            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao inserir cadastro: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public List<Cadastro> listarTodos() {

        List<Cadastro> lista = new ArrayList<>();

        String sql = "SELECT * FROM cadastro ORDER BY id DESC";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Cadastro c = new Cadastro();

                c.setId(rs.getInt("id"));
                c.setData(rs.getString("data"));
                c.setPlaca(rs.getString("placa"));
                c.setNumeroOF(rs.getString("numeroOF"));
                c.setHoraCadastro(rs.getString("horaCadastro"));
                c.setNumeroPager(rs.getString("numeroPager"));
                c.setOfTroca(rs.getString("ofTroca"));
                c.setStatus(rs.getString("status"));
                c.setAutorizacao(rs.getString("autorizacao"));
                c.setHoraAutorizacao(rs.getString("horaAutorizacao"));
                c.setObservacao(rs.getString("observacao"));
                c.setUsuario(rs.getString("usuario"));

                try {
                    c.setUsuarioAlteracao(rs.getString("usuarioAlteracao"));
                } catch (Exception e) {
                    c.setUsuarioAlteracao("");
                }

                try {
                    c.setHoraAlteracao(rs.getString("horaAlteracao"));
                } catch (Exception e) {
                    c.setHoraAlteracao("");
                }

                lista.add(c);
            }

        } catch (Exception e) {
            System.out.println("Erro ao listar cadastros: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    public boolean atualizar(Cadastro c) {

        String sql = "UPDATE cadastro SET "
                + "placa = ?, "
                + "numeroOF = ?, "
                + "numeroPager = ?, "
                + "ofTroca = ?, "
                + "status = ?, "
                + "autorizacao = ?, "
                + "horaAutorizacao = ?, "
                + "observacao = ?, "
                + "usuarioAlteracao = ?, "
                + "horaAlteracao = ? "
                + "WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getPlaca());
            ps.setString(2, c.getNumeroOF());
            ps.setString(3, c.getNumeroPager());
            ps.setString(4, c.getOfTroca());
            ps.setString(5, c.getStatus());
            ps.setString(6, c.getAutorizacao());
            ps.setString(7, c.getHoraAutorizacao());
            ps.setString(8, c.getObservacao());
            ps.setString(9, c.getUsuarioAlteracao());
            ps.setString(10, c.getHoraAlteracao());
            ps.setInt(11, c.getId());

            int linhasAfetadas = ps.executeUpdate();

            return linhasAfetadas > 0;

        } catch (Exception e) {
            System.out.println("Erro ao atualizar cadastro: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public void excluir(int id) {

        String sql = "DELETE FROM cadastro WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (Exception e) {
            System.out.println("Erro ao excluir cadastro: " + e.getMessage());
            e.printStackTrace();
        }
    }
}