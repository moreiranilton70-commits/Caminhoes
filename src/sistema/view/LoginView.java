package sistema.view;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import sistema.dao.UsuarioDAO;
import sistema.model.Usuario;

public class LoginView extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;
    private JButton btnEsqueceuSenha;
    private JButton btnNovoCadastro;

    public LoginView() {

        setTitle("Login");
        setSize(480, 380);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        criarTela();
    }

    private void criarTela() {

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Arial", Font.BOLD, 16);
        Font fonteCampo = new Font("Arial", Font.PLAIN, 16);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setFont(fonteLabel);

        txtUsuario = new JTextField(18);
        txtUsuario.setFont(fonteCampo);

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(fonteLabel);

        txtSenha = new JPasswordField(18);
        txtSenha.setFont(fonteCampo);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(fonteCampo);

        btnSair = new JButton("Sair");
        btnSair.setFont(fonteCampo);

        btnEsqueceuSenha = new JButton("Esqueceu a senha?");
        btnEsqueceuSenha.setFont(fonteCampo);

        btnNovoCadastro = new JButton("Novo cadastro");
        btnNovoCadastro.setFont(fonteCampo);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        add(lblUsuario, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        add(txtUsuario, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(lblSenha, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(btnEntrar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        add(btnSair, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(btnNovoCadastro, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        add(btnEsqueceuSenha, gbc);

        btnEntrar.addActionListener(e -> logar());

        btnSair.addActionListener(e -> System.exit(0));

        btnEsqueceuSenha.addActionListener(e -> alterarSenhaEsquecida());

        btnNovoCadastro.addActionListener(e -> fazerNovoCadastro());
    }

    private void logar() {

        String usuarioDigitado = txtUsuario.getText().trim();
        String senhaDigitada = new String(txtSenha.getPassword()).trim();

        if (usuarioDigitado.isEmpty() || senhaDigitada.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe usuário e senha.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.logar(usuarioDigitado, senhaDigitada);

        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos.");
            return;
        }

        dispose();

        if ("admin".equalsIgnoreCase(usuario.getTipo())) {
            new AdminView(usuario.getUsuario()).setVisible(true);
        } else {
            new CadastroView(usuario.getUsuario()).setVisible(true);
        }
    }

    private void fazerNovoCadastro() {

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
                "Novo cadastro de usuário",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String novoUsuario = campoUsuario.getText().trim();
        String senha = new String(campoSenha.getPassword()).trim();
        String confirmarSenha = new String(campoConfirmarSenha.getPassword()).trim();

        if (novoUsuario.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Preencha todos os campos.");
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não conferem.");
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();

        if (dao.existeUsuario(novoUsuario)) {
            JOptionPane.showMessageDialog(this, "Este usuário já existe.");
            return;
        }

        Usuario usuario = new Usuario();
        usuario.setUsuario(novoUsuario);
        usuario.setSenha(senha);
        usuario.setTipo("usuario");

        dao.inserir(usuario);

        JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso.");

        txtUsuario.setText(novoUsuario);
        txtSenha.setText("");
        txtSenha.requestFocus();
    }

    private void alterarSenhaEsquecida() {

        String usuarioDigitado = JOptionPane.showInputDialog(
                this,
                "Informe o usuário para alterar a senha:"
        );

        if (usuarioDigitado == null || usuarioDigitado.trim().isEmpty()) {
            return;
        }

        usuarioDigitado = usuarioDigitado.trim();

        UsuarioDAO dao = new UsuarioDAO();

        if (!dao.existeUsuario(usuarioDigitado)) {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.");
            return;
        }

        JPasswordField campoNovaSenha = new JPasswordField();
        JPasswordField campoConfirmarSenha = new JPasswordField();

        Object[] campos = {
                "Nova senha:", campoNovaSenha,
                "Confirmar nova senha:", campoConfirmarSenha
        };

        int opcao = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Refazer senha",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        String novaSenha = new String(campoNovaSenha.getPassword()).trim();
        String confirmarSenha = new String(campoConfirmarSenha.getPassword()).trim();

        if (novaSenha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe e confirme a nova senha.");
            return;
        }

        if (!novaSenha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não conferem.");
            return;
        }

        boolean alterou = dao.alterarSenhaPorUsuario(usuarioDigitado, novaSenha);

        if (alterou) {
            JOptionPane.showMessageDialog(this, "Senha alterada com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar senha.");
        }
    }
}