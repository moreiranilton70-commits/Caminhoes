package sistema.view;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import sistema.dao.CadastroDAO;
import sistema.dao.UsuarioDAO;
import sistema.model.Cadastro;
import sistema.model.Usuario;

public class AdminView extends JFrame {

    private JTable tabelaCadastros;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloCadastros;
    private DefaultTableModel modeloUsuarios;

    public AdminView(String usuario) {
        setTitle("Administração - " + usuario);
        setSize(1100, 620);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        modeloCadastros = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloCadastros.addColumn("ID");
        modeloCadastros.addColumn("Data");
        modeloCadastros.addColumn("Hora Cadastro");
        modeloCadastros.addColumn("Placa");
        modeloCadastros.addColumn("Número OF");
        modeloCadastros.addColumn("Status");
        modeloCadastros.addColumn("Usuário");

        tabelaCadastros = new JTable(modeloCadastros);
        tabelaCadastros.setRowSelectionAllowed(true);
        tabelaCadastros.setColumnSelectionAllowed(false);
        tabelaCadastros.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollCad = new JScrollPane(tabelaCadastros);
        scrollCad.setBounds(10, 10, 1060, 250);
        add(scrollCad);

        JButton btnAtualizarCad = new JButton("Atualizar Cadastros");
        btnAtualizarCad.setBounds(10, 270, 200, 30);
        btnAtualizarCad.addActionListener(e -> atualizarCadastros());
        add(btnAtualizarCad);

        JButton btnExcluirCad = new JButton("Excluir Cadastro");
        btnExcluirCad.setBounds(220, 270, 200, 30);
        btnExcluirCad.addActionListener(e -> excluirCadastro());
        add(btnExcluirCad);

        modeloUsuarios = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloUsuarios.addColumn("ID");
        modeloUsuarios.addColumn("Login");
        modeloUsuarios.addColumn("Administrador");

        tabelaUsuarios = new JTable(modeloUsuarios);
        tabelaUsuarios.setRowSelectionAllowed(true);
        tabelaUsuarios.setColumnSelectionAllowed(false);
        tabelaUsuarios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scrollUsu = new JScrollPane(tabelaUsuarios);
        scrollUsu.setBounds(10, 310, 1060, 200);
        add(scrollUsu);

        JButton btnAtualizarUsu = new JButton("Atualizar Usuários");
        btnAtualizarUsu.setBounds(10, 520, 200, 30);
        btnAtualizarUsu.addActionListener(e -> atualizarUsuarios());
        add(btnAtualizarUsu);

        JButton btnAdicionarUsu = new JButton("Adicionar Usuário");
        btnAdicionarUsu.setBounds(220, 520, 200, 30);
        btnAdicionarUsu.addActionListener(e -> adicionarUsuario());
        add(btnAdicionarUsu);

        JButton btnExcluirUsu = new JButton("Excluir Usuário");
        btnExcluirUsu.setBounds(430, 520, 200, 30);
        btnExcluirUsu.addActionListener(e -> excluirUsuario());
        add(btnExcluirUsu);

        JButton btnVoltar = new JButton("Voltar para Login");
        btnVoltar.setBounds(640, 520, 200, 30);
        btnVoltar.addActionListener(e -> voltarLogin());
        add(btnVoltar);

        atualizarCadastros();
        atualizarUsuarios();
    }

    private void atualizarCadastros() {
        modeloCadastros.setRowCount(0);

        CadastroDAO dao = new CadastroDAO();
        List<Cadastro> cadastros = dao.listarTodos();

        for (Cadastro c : cadastros) {
            modeloCadastros.addRow(new Object[] {
                    c.getHoraCadastro(),
                    c.getData(),
                    c.getHoraCadastro(),
                    c.getPlaca(),
                    c.getNumeroOF(),
                    c.getStatus(),
                    c.getUsuario()
            });
        }
    }

    private void atualizarUsuarios() {
        modeloUsuarios.setRowCount(0);

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.listarUsuarios();

        for (Usuario u : usuarios) {
            modeloUsuarios.addRow(new Object[] {
                    u.getId(),
                    u.getLogin(),
                    u.isAdministrador()
            });
        }
    }

    private void excluirCadastro() {
        int linha = tabelaCadastros.getSelectedRow();

        if (linha >= 0) {
            int id = Integer.parseInt(modeloCadastros.getValueAt(linha, 0).toString());

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir este cadastro?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                CadastroDAO dao = new CadastroDAO();
                dao.excluir(id);

                atualizarCadastros();

                JOptionPane.showMessageDialog(this, "Cadastro excluído com sucesso.");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Selecione um cadastro para excluir.");
        }
    }

    private void adicionarUsuario() {
        String login = JOptionPane.showInputDialog(this, "Login do usuário:");

        if (login == null || login.trim().isEmpty()) {
            return;
        }

        String senha = JOptionPane.showInputDialog(this, "Senha:");

        if (senha == null || senha.trim().isEmpty()) {
            return;
        }

        int admin = JOptionPane.showConfirmDialog(
                this,
                "Administrador?",
                "Pergunta",
                JOptionPane.YES_NO_OPTION
        );

        boolean isAdmin = admin == JOptionPane.YES_OPTION;

        Usuario u = new Usuario();
        u.setLogin(login);
        u.setSenha(senha);
        u.setAdministrador(isAdmin);

        UsuarioDAO dao = new UsuarioDAO();
        dao.inserirUsuario(u);

        atualizarUsuarios();

        JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso.");
    }

    private void excluirUsuario() {
        int linha = tabelaUsuarios.getSelectedRow();

        if (linha >= 0) {
            String login = modeloUsuarios.getValueAt(linha, 1).toString();

            int confirmar = JOptionPane.showConfirmDialog(
                    this,
                    "Deseja realmente excluir o usuário " + login + "?",
                    "Confirmar exclusão",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmar == JOptionPane.YES_OPTION) {
                UsuarioDAO dao = new UsuarioDAO();
                dao.excluirUsuario(login);

                atualizarUsuarios();

                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
            }

        } else {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.");
        }
    }

    private void voltarLogin() {
        dispose();
        new LoginView().setVisible(true);
    }
}