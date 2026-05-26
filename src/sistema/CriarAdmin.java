package sistema;

import java.sql.Connection;
import java.sql.PreparedStatement;

import sistema.dao.Conexao;

public class CriarAdmin {

    public static void main(String[] args) {

        String sql = """
            INSERT INTO usuarios
            (login, senha, email, administrador)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "admin");
            ps.setString(2, "123");
            ps.setString(3, "admin@empresa.com");
            ps.setInt(4, 1);

            ps.executeUpdate();

            System.out.println("ADMIN CRIADO COM SUCESSO!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}