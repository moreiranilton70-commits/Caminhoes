package sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sistema.model.Cadastro;

public class CadastroDAO {

    public void inserir(Cadastro c) {
        String sql = "INSERT INTO cadastro (data, placa, numeroOF, horaCadastro, numeroPager, ofTroca, status, autorizacao, horaAutorizacao, observacao, usuario) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getData());
            ps.setString(2, c.getPlaca());
            ps.setString(3, c.getNumeroOF());
            ps.setLong(4, c.getHoraCadastro());
            ps.setString(5, c.getNumeroPager());
            ps.setString(6, c.getOfTroca());
            ps.setString(7, c.getStatus());
            ps.setString(8, c.getAutorizacao());
            ps.setString(9, c.getHoraAutorizacao());
            ps.setString(10, c.getObservacao());
            ps.setString(11, c.getUsuario());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Cadastro> listarTodos() {
        List<Cadastro> lista = new ArrayList<>();
        String sql = "SELECT * FROM cadastro";

        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cadastro c = new Cadastro();

                c.setHoraCadastro(rs.getInt("id"));
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

                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void atualizar(Cadastro c) {
        String sql = "UPDATE cadastro SET data=?, placa=?, numeroOF=?, horaCadastro=?, numeroPager=?, ofTroca=?, status=?, autorizacao=?, horaAutorizacao=?, observacao=?, usuario=? WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, c.getData());
            ps.setString(2, c.getPlaca());
            ps.setString(3, c.getNumeroOF());
            ps.setLong(4, c.getHoraCadastro());
            ps.setString(5, c.getNumeroPager());
            ps.setString(6, c.getOfTroca());
            ps.setString(7, c.getStatus());
            ps.setString(8, c.getAutorizacao());
            ps.setString(9, c.getHoraAutorizacao());
            ps.setString(10, c.getObservacao());
            ps.setString(11, c.getUsuario());
            ps.setInt(12, c.getHoraCadastro());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void excluir(int id) {
        String sql = "DELETE FROM cadastro WHERE id=?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}