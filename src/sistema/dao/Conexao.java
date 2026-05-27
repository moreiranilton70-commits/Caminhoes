package sistema.dao;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

    private static final String URL = "jdbc:sqlite:Caminhoes.db";

    public static Connection conectar() {

        try {
            return DriverManager.getConnection(URL);
        } catch (Exception e) {
            System.out.println("Erro ao conectar no banco: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}