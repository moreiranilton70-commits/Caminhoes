package sistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:sqlite:Caminhoes.db";

    public static Connection conectar() {

        try {

            Connection conn = DriverManager.getConnection(URL);

            System.out.println("BANCO CONECTADO!");

            return conn;

        } catch (SQLException e) {

            e.printStackTrace();

            return null;
        }
    }
}