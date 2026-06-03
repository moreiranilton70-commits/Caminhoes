package sistema.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sistema.dao.UsuarioDAO;

public class LoginView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    private JButton btnEntrar;
    private JButton btnEsqueciSenha;
    private JButton btnCadastrarUsuario;

    public LoginView() {

        setTitle("Login - Sistema de Caminhões");
        setSize(480, 390);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);
        getContentPane().setBackground(new Color(245, 245, 245));

        JLabel lblTitulo = new JLabel("Acesso ao Sistema");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBounds(0, 30, 480, 35);
        lblTitulo.setHorizontalAlignment(JLabel.CENTER);
        add(lblTitulo);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 15));
        lblUsuario.setBounds(70, 100, 100, 25);
        add(lblUsuario);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 15));
        txtUsuario.setBounds(170, 100, 220, 30);
        add(txtUsuario);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.BOLD, 15));
        lblSenha.setBounds(70, 145, 100, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 15));
        txtSenha.setBounds(170, 145, 220, 30);
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 16));
        btnEntrar.setBounds(170, 200, 220, 40);
        add(btnEntrar);

        btnEsqueciSenha = new JButton("Esqueci minha senha");
        btnEsqueciSenha.setFont(new Font("Arial", Font.BOLD, 14));
        btnEsqueciSenha.setBounds(170, 250, 220, 35);
        add(btnEsqueciSenha);

        btnCadastrarUsuario = new JButton("Cadastrar usuário");
        btnCadastrarUsuario.setFont(new Font("Arial", Font.BOLD, 14));
        btnCadastrarUsuario.setBounds(170, 295, 220, 35);
        add(btnCadastrarUsuario);

        btnEntrar.addActionListener(e -> fazerLogin());
        btnEsqueciSenha.addActionListener(e -> esqueciSenha());
        btnCadastrarUsuario.addActionListener(e -> cadastrarUsuario());

        txtSenha.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    fazerLogin();
                }
            }
        });
    }

    private void fazerLogin() {

        String usuario = txtUsuario.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();

        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe usuário e senha.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        boolean loginValido = dao.validarLogin(usuario, senha);

        if (loginValido) {

            JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            dispose();

            /*
             * CORREÇÃO PRINCIPAL:
             * Se for admin, abre AdminView.
             * Se for usuário comum, abre MenuView.
             */
            if ("admin".equalsIgnoreCase(usuario)) {
                new AdminView(usuario).setVisible(true);
            } else {
                new MenuView(usuario).setVisible(true);
            }

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Usuário ou senha inválidos.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void esqueciSenha() {

        String usuario = JOptionPane.showInputDialog(
                this,
                "Informe o usuário para confirmar:",
                "Confirmar usuário",
                JOptionPane.QUESTION_MESSAGE
        );

        if (usuario == null) {
            return;
        }

        usuario = usuario.trim();

        if (usuario.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe o usuário.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        boolean usuarioExiste = dao.usuarioExiste(usuario);

        if (!usuarioExiste) {
            JOptionPane.showMessageDialog(
                    this,
                    "Usuário não encontrado.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        JPasswordField novaSenhaField = new JPasswordField();
        JPasswordField confirmarSenhaField = new JPasswordField();

        Object[] campos = {
                "Nova senha:", novaSenhaField,
                "Confirmar nova senha:", confirmarSenhaField
        };

        int opcao = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Alterar senha",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String novaSenha = new String(novaSenhaField.getPassword()).trim();
        String confirmarSenha = new String(confirmarSenhaField.getPassword()).trim();

        if (novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Informe e confirme a nova senha.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(
                    this,
                    "As senhas não conferem.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean alterou = dao.alterarSenha(usuario, novaSenha);

        if (alterou) {
            JOptionPane.showMessageDialog(
                    this,
                    "Senha alterada com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível alterar a senha.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void cadastrarUsuario() {

        JTextField campoUsuario = new JTextField();
        JPasswordField campoSenha = new JPasswordField();
        JPasswordField campoConfirmarSenha = new JPasswordField();

        Object[] campos = {
                "Novo usuário:", campoUsuario,
                "Senha:", campoSenha,
                "Confirmar senha:", campoConfirmarSenha
        };

        int opcao = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Cadastrar Usuário",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String usuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String confirmarSenha = new String(campoConfirmarSenha.getPassword()).trim();

        if (usuario.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha usuário, senha e confirmação.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(
                    this,
                    "As senhas não conferem.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.usuarioExiste(usuario)) {
            JOptionPane.showMessageDialog(
                    this,
                    "Este usuário já está cadastrado.",
                    "Atenção",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        boolean cadastrou = dao.cadastrarUsuario(usuario, senha);

        if (cadastrou) {
            JOptionPane.showMessageDialog(
                    this,
                    "Usuário cadastrado com sucesso!",
                    "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE
            );

            txtUsuario.setText(usuario);
            txtSenha.setText("");

        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível cadastrar o usuário.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}