package sistema.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import sistema.model.MateriaPrima;

public class MateriaPrimaDAO {

    public void criarTabela() {
        String sql = """
            CREATE TABLE IF NOT EXISTS materia_prima (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                data TEXT,
                placa TEXT,
                material TEXT,
                fornecedor TEXT,
                hora_chegada TEXT,
                hora_finalizou_pendencia TEXT,
                numero_nota TEXT,
                autorizacao TEXT,
                status TEXT,
                observacao TEXT,
                nota_substituta TEXT,
                usuario TEXT,
                usuario_alteracao TEXT,
                hora_alteracao TEXT
            )
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.execute();

            adicionarColunaSeNaoExistir(conn, "usuario_alteracao");
            adicionarColunaSeNaoExistir(conn, "hora_alteracao");

        } catch (SQLException e) {
            System.out.println("Erro ao criar tabela materia_prima: " + e.getMessage());
        }
    }

    private void adicionarColunaSeNaoExistir(Connection conn, String coluna) {
        String sql = "ALTER TABLE materia_prima ADD COLUMN " + coluna + " TEXT";

        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            // Se a coluna já existir, o SQLite gera erro.
            // Neste caso pode ignorar.
        }
    }

    public void inserir(MateriaPrima mp) {
        criarTabela();

        String sql = """
            INSERT INTO materia_prima 
            (
                data,
                placa,
                material,
                fornecedor,
                hora_chegada,
                hora_finalizou_pendencia,
                numero_nota,
                autorizacao,
                status,
                observacao,
                nota_substituta,
                usuario
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mp.getData());
            ps.setString(2, mp.getPlaca());
            ps.setString(3, mp.getMaterial());
            ps.setString(4, mp.getFornecedor());
            ps.setString(5, mp.getHoraChegada());
            ps.setString(6, mp.getHoraFinalizouPendencia());
            ps.setString(7, mp.getNumeroNota());
            ps.setString(8, mp.getAutorizacao());
            ps.setString(9, mp.getStatus());
            ps.setString(10, mp.getObservacao());
            ps.setString(11, mp.getNotaSubstituta());
            ps.setString(12, mp.getUsuario());

            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Erro ao inserir matéria-prima: " + e.getMessage());
        }
    }

    public ArrayList<MateriaPrima> listarTodos() {
        criarTabela();

        ArrayList<MateriaPrima> lista = new ArrayList<>();

        String sql = """
            SELECT 
                id,
                data,
                placa,
                material,
                fornecedor,
                hora_chegada,
                hora_finalizou_pendencia,
                numero_nota,
                autorizacao,
                status,
                observacao,
                nota_substituta,
                usuario,
                usuario_alteracao,
                hora_alteracao
            FROM materia_prima
            ORDER BY id DESC
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MateriaPrima mp = new MateriaPrima();

                mp.setId(rs.getInt("id"));
                mp.setData(rs.getString("data"));
                mp.setPlaca(rs.getString("placa"));
                mp.setMaterial(rs.getString("material"));
                mp.setFornecedor(rs.getString("fornecedor"));
                mp.setHoraChegada(rs.getString("hora_chegada"));
                mp.setHoraFinalizouPendencia(rs.getString("hora_finalizou_pendencia"));
                mp.setNumeroNota(rs.getString("numero_nota"));
                mp.setAutorizacao(rs.getString("autorizacao"));
                mp.setStatus(rs.getString("status"));
                mp.setObservacao(rs.getString("observacao"));
                mp.setNotaSubstituta(rs.getString("nota_substituta"));
                mp.setUsuario(rs.getString("usuario"));
                mp.setUsuarioAlteracao(rs.getString("usuario_alteracao"));
                mp.setHoraAlteracao(rs.getString("hora_alteracao"));

                lista.add(mp);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar matéria-prima: " + e.getMessage());
        }

        return lista;
    }

    public boolean atualizar(MateriaPrima mp) {
        criarTabela();

        String sql = """
            UPDATE materia_prima SET
                placa = ?,
                material = ?,
                fornecedor = ?,
                hora_finalizou_pendencia = ?,
                numero_nota = ?,
                autorizacao = ?,
                status = ?,
                observacao = ?,
                nota_substituta = ?,
                usuario_alteracao = ?,
                hora_alteracao = ?
            WHERE id = ?
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, mp.getPlaca());
            ps.setString(2, mp.getMaterial());
            ps.setString(3, mp.getFornecedor());
            ps.setString(4, mp.getHoraFinalizouPendencia());
            ps.setString(5, mp.getNumeroNota());
            ps.setString(6, mp.getAutorizacao());
            ps.setString(7, mp.getStatus());
            ps.setString(8, mp.getObservacao());
            ps.setString(9, mp.getNotaSubstituta());
            ps.setString(10, mp.getUsuarioAlteracao());
            ps.setString(11, mp.getHoraAlteracao());
            ps.setInt(12, mp.getId());

            int linhas = ps.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao atualizar matéria-prima: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        criarTabela();

        String sql = "DELETE FROM materia_prima WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            int linhas = ps.executeUpdate();

            return linhas > 0;

        } catch (SQLException e) {
            System.out.println("Erro ao excluir matéria-prima: " + e.getMessage());
            return false;
        }
    }
}