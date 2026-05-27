package sistema;

import sistema.view.LoginView;

public class Main {

    public static void main(String[] args) {

        CriarBanco.criarTabelas();
        CriarAdm.criarUsuarioAdmin();

        LoginView login = new LoginView();
        login.setVisible(true);
    }
}