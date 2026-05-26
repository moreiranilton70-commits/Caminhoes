package sistema;

import java.sql.Connection;
import java.sql.Statement;

import sistema.dao.Conexao;

public class CriarBanco {

    public static void main(String[] args) {

        System.out.println("INICIANDO CRIAÇÃO DO BANCO...");

        try (Connection conn = Conexao.conectar();
             Statement st = conn.createStatement()) {

            String sqlUsuarios = """
                CREATE TABLE IF NOT EXISTS usuarios (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    login TEXT NOT NULL UNIQUE,
                    senha TEXT NOT NULL,
                    email TEXT,
                    administrador INTEGER DEFAULT 0
                );
            """;

            String sqlCadastro = """
                CREATE TABLE IF NOT EXISTS cadastro (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    data TEXT,
                    placa TEXT,
                    numeroOF TEXT,
                    horaCadastro TEXT,
                    numeroPager TEXT,
                    ofTroca TEXT,
                    status TEXT,
                    autorizacao TEXT,
                    horaAutorizacao TEXT,
                    observacao TEXT,
                    usuario TEXT
                );
            """;

            st.execute(sqlUsuarios);
            st.execute(sqlCadastro);

            System.out.println("TABELA usuarios CRIADA OU JÁ EXISTENTE!");
            System.out.println("TABELA cadastro CRIADA OU JÁ EXISTENTE!");
            System.out.println("BANCO CONFIGURADO COM SUCESSO!");

        } catch (Exception e) {

            e.printStackTrace();

        }
    }
}