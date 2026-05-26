package sistema;

import javax.swing.SwingUtilities;
import sistema.view.LoginView;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {

            LoginView tela = new LoginView();
            tela.setVisible(true);

        });

    }
}