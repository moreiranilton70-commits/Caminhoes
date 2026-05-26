package sistema.view;

import javax.swing.*;

import sistema.dao.UsuarioDAO;

public class LoginView extends JFrame {

    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnEsqueceuSenha;

    public LoginView() {
        setTitle("Login");
        setSize(320, 230);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblLogin = new JLabel("Login:");
        lblLogin.setBounds(30, 30, 80, 25);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(100, 30, 170, 25);
        add(txtLogin);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(30, 70, 80, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(100, 70, 170, 25);
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(110, 110, 100, 25);
        add(btnEntrar);

        btnEsqueceuSenha = new JButton("Esqueceu sua senha?");
        btnEsqueceuSenha.setBounds(70, 145, 180, 25);
        add(btnEsqueceuSenha);
        
        JButton btnCadastrar = new JButton("Cadastrar Usuário");
        btnCadastrar.setBounds(70, 175, 180, 25);
        add(btnCadastrar);

        btnCadastrar.addActionListener(e -> abrirCadastroUsuario());

        btnEntrar.addActionListener(e -> logar());
        btnEsqueceuSenha.addActionListener(e -> abrirRecuperarSenha());
    }

    private void abrirCadastroUsuario() {

        JTextField txtNovoLogin = new JTextField();
        JTextField txtNovoEmail = new JTextField();
        JPasswordField txtNovaSenha = new JPasswordField();

        Object[] campos = {

                "Login:", txtNovoLogin,
                "E-mail:", txtNovoEmail,
                "Senha:", txtNovaSenha

        };

        int opcao = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Cadastrar Usuário",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcao == JOptionPane.OK_OPTION) {

            String login = txtNovoLogin.getText();
            String email = txtNovoEmail.getText();
            String senha = new String(txtNovaSenha.getPassword());

            if (login.isEmpty() || email.isEmpty() || senha.isEmpty()) {

                JOptionPane.showMessageDialog(this,
                        "Preencha todos os campos.");

                return;
            }

            UsuarioDAO dao = new UsuarioDAO();

            boolean sucesso = dao.cadastrarUsuario(login, senha, email);

            if (sucesso) {

                JOptionPane.showMessageDialog(this,
                        "Usuário cadastrado com sucesso!");

            } else {

                JOptionPane.showMessageDialog(this,
                        "Erro ao cadastrar usuário.");

            }
        }
    }

	private void logar() {
        String login = txtLogin.getText();
        String senha = new String(txtSenha.getPassword());

        UsuarioDAO dao = new UsuarioDAO();
        boolean valido = dao.loginValido(login, senha);

        if (valido) {
            boolean admin = dao.isAdministrador(login);

            JOptionPane.showMessageDialog(this, "Login OK!");

            dispose();

            if (admin) {
                new AdminView(login).setVisible(true);
            } else {
                new CadastroView(login).setVisible(true);
            }

        } else {
            JOptionPane.showMessageDialog(this, "Login ou senha inválidos");
        }
    }

    private void abrirRecuperarSenha() {
        String email = JOptionPane.showInputDialog(
                this,
                "Informe seu e-mail cadastrado:",
                "Recuperar senha",
                JOptionPane.PLAIN_MESSAGE
        );

        if (email == null || email.trim().isEmpty()) {
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        boolean existe = dao.emailExiste(email);

        if (!existe) {
            JOptionPane.showMessageDialog(this, "E-mail não encontrado no sistema.");
            return;
        }

        String senhaProvisoria = gerarSenhaProvisoria();

        boolean atualizado = dao.atualizarSenhaPorEmail(email, senhaProvisoria);

        if (atualizado) {
            JOptionPane.showMessageDialog(
                    this,
                    "Senha provisória gerada com sucesso.\n\n" +
                    "Senha: " + senhaProvisoria + "\n\n" +
                    "Informe esta senha ao usuário."
            );
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao gerar senha provisória.");
        }
    }

    private String gerarSenhaProvisoria() {
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder senha = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int posicao = (int) (Math.random() * caracteres.length());
            senha.append(caracteres.charAt(posicao));
        }

        return senha.toString();
    }
}