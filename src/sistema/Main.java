package sistema;

import sistema.dao.MateriaPrimaDAO;
import sistema.dao.UsuarioDAO;
import sistema.view.LoginView;

public class Main {

    public static void main(String[] args) {

        try {
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            usuarioDAO.criarTabela();

            MateriaPrimaDAO materiaPrimaDAO = new MateriaPrimaDAO();
            materiaPrimaDAO.criarTabela();

            LoginView login = new LoginView();
            login.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}