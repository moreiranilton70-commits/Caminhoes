package sistema;

import sistema.dao.UsuarioDAO;
import sistema.model.Usuario;

public class CriarAdm {

    public static void criarUsuarioAdmin() {

        UsuarioDAO dao = new UsuarioDAO();

        if (!dao.existeUsuario("admin")) {

            Usuario u = new Usuario();
            u.setUsuario("admin");
            u.setSenha("admin");
            u.setTipo("admin");

            dao.inserir(u);

            System.out.println("Usuário admin criado.");
        }
    }
}