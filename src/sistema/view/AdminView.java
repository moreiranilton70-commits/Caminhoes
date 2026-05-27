package sistema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import sistema.dao.CadastroDAO;
import sistema.dao.UsuarioDAO;
import sistema.model.Cadastro;
import sistema.model.Usuario;

public class AdminView extends JFrame {

    private JTable tabelaCadastros;
    private DefaultTableModel modeloCadastros;

    private JTable tabelaUsuarios;
    private DefaultTableModel modeloUsuarios;

    private JButton btnAtualizarCadastros;
    private JButton btnExcluirCadastro;

    private JButton btnAtualizarUsuarios;
    private JButton btnExcluirUsuario;
    private JButton btnAlterarSenhaUsuario;

    private JButton btnVoltar;

    private String usuarioLogado;

    public AdminView(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Tela do Administrador - Usuário: " + usuarioLogado);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        criarTela();

        setLocationRelativeTo(null);
    }

    private void criarTela() {

        JTabbedPane abas = new JTabbedPane();

        abas.addTab("Cadastros de Caminhões", criarPainelCadastros());
        abas.addTab("Usuários Cadastrados", criarPainelUsuarios());

        add(abas, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel();

        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 15));

        painelInferior.add(btnVoltar);

        add(painelInferior, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        carregarCadastros();
        carregarUsuarios();
    }

    private JPanel criarPainelCadastros() {

        JPanel painel = new JPanel(new BorderLayout());

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
        modeloCadastros.addColumn("Número Pager");
        modeloCadastros.addColumn("OF Troca");
        modeloCadastros.addColumn("Status");
        modeloCadastros.addColumn("Autorização");
        modeloCadastros.addColumn("Hora Autorização");
        modeloCadastros.addColumn("Observação");
        modeloCadastros.addColumn("Usuário");

        tabelaCadastros = new JTable(modeloCadastros);
        tabelaCadastros.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaCadastros.setRowHeight(28);
        tabelaCadastros.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaCadastros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaCadastros.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(tabelaCadastros);
        scroll.setPreferredSize(new Dimension(1000, 600));

        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnAtualizarCadastros = new JButton("Atualizar Cadastros");
        btnAtualizarCadastros.setFont(new Font("Arial", Font.BOLD, 15));

        btnExcluirCadastro = new JButton("Excluir Cadastro");
        btnExcluirCadastro.setFont(new Font("Arial", Font.BOLD, 15));

        painelBotoes.add(btnAtualizarCadastros);
        painelBotoes.add(btnExcluirCadastro);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizarCadastros.addActionListener(e -> carregarCadastros());
        btnExcluirCadastro.addActionListener(e -> excluirCadastro());

        return painel;
    }

    private JPanel criarPainelUsuarios() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloUsuarios = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloUsuarios.addColumn("ID");
        modeloUsuarios.addColumn("Usuário");
        modeloUsuarios.addColumn("Tipo");

        tabelaUsuarios = new JTable(modeloUsuarios);
        tabelaUsuarios.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaUsuarios.setRowHeight(28);
        tabelaUsuarios.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaUsuarios.setAutoCreateRowSorter(true);

        JScrollPane scroll = new JScrollPane(tabelaUsuarios);
        scroll.setPreferredSize(new Dimension(1000, 600));

        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnAtualizarUsuarios = new JButton("Atualizar Usuários");
        btnAtualizarUsuarios.setFont(new Font("Arial", Font.BOLD, 15));

        btnAlterarSenhaUsuario = new JButton("Alterar Senha do Usuário");
        btnAlterarSenhaUsuario.setFont(new Font("Arial", Font.BOLD, 15));

        btnExcluirUsuario = new JButton("Excluir Usuário");
        btnExcluirUsuario.setFont(new Font("Arial", Font.BOLD, 15));

        painelBotoes.add(btnAtualizarUsuarios);
        painelBotoes.add(btnAlterarSenhaUsuario);
        painelBotoes.add(btnExcluirUsuario);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizarUsuarios.addActionListener(e -> carregarUsuarios());
        btnAlterarSenhaUsuario.addActionListener(e -> alterarSenhaUsuario());
        btnExcluirUsuario.addActionListener(e -> excluirUsuario());

        return painel;
    }

    private void carregarCadastros() {

        modeloCadastros.setRowCount(0);

        CadastroDAO dao = new CadastroDAO();
        List<Cadastro> lista = dao.listarTodos();

        for (Cadastro c : lista) {

            modeloCadastros.addRow(new Object[] {
                    c.getId(),
                    valorSeguro(c.getData()),
                    valorSeguro(c.getHoraCadastro()),
                    valorSeguro(c.getPlaca()),
                    valorSeguro(c.getNumeroOF()),
                    valorSeguro(c.getNumeroPager()),
                    valorSeguro(c.getOfTroca()),
                    valorSeguro(c.getStatus()),
                    valorSeguro(c.getAutorizacao()),
                    valorSeguro(c.getHoraAutorizacao()),
                    valorSeguro(c.getObservacao()),
                    valorSeguro(c.getUsuario())
            });
        }
    }

    private void carregarUsuarios() {

        modeloUsuarios.setRowCount(0);

        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> lista = dao.listarTodos();

        for (Usuario u : lista) {

            modeloUsuarios.addRow(new Object[] {
                    u.getId(),
                    valorSeguro(u.getUsuario()),
                    valorSeguro(u.getTipo())
            });
        }
    }

    private void excluirCadastro() {

        int linhaSelecionada = tabelaCadastros.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cadastro para excluir.");
            return;
        }

        int linhaModelo = tabelaCadastros.convertRowIndexToModel(linhaSelecionada);

        Object valorId = modeloCadastros.getValueAt(linhaModelo, 0);

        if (valorId == null || valorId.toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: ID do cadastro não encontrado.");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(valorId.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: ID inválido para exclusão.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o cadastro ID " + id + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        CadastroDAO dao = new CadastroDAO();
        dao.excluir(id);

        JOptionPane.showMessageDialog(this, "Cadastro excluído com sucesso.");

        carregarCadastros();
    }

    private void excluirUsuario() {

        int linhaSelecionada = tabelaUsuarios.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.");
            return;
        }

        int linhaModelo = tabelaUsuarios.convertRowIndexToModel(linhaSelecionada);

        Object valorId = modeloUsuarios.getValueAt(linhaModelo, 0);
        Object valorUsuario = modeloUsuarios.getValueAt(linhaModelo, 1);
        Object valorTipo = modeloUsuarios.getValueAt(linhaModelo, 2);

        if (valorId == null || valorUsuario == null) {
            JOptionPane.showMessageDialog(this, "Erro: usuário inválido.");
            return;
        }

        String usuarioSelecionado = valorUsuario.toString();

        if ("admin".equalsIgnoreCase(usuarioSelecionado)) {
            JOptionPane.showMessageDialog(this, "O usuário admin principal não pode ser excluído.");
            return;
        }

        if (usuarioSelecionado.equalsIgnoreCase(usuarioLogado)) {
            JOptionPane.showMessageDialog(this, "Você não pode excluir o usuário que está logado.");
            return;
        }

        int id;

        try {
            id = Integer.parseInt(valorId.toString());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Erro: ID inválido.");
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o usuário " + usuarioSelecionado + "?",
                "Confirmar exclusão de usuário",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        UsuarioDAO dao = new UsuarioDAO();
        boolean excluiu = dao.excluir(id);

        if (excluiu) {
            JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
            carregarUsuarios();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir usuário.");
        }
    }

    private void alterarSenhaUsuario() {

        int linhaSelecionada = tabelaUsuarios.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para alterar a senha.");
            return;
        }

        int linhaModelo = tabelaUsuarios.convertRowIndexToModel(linhaSelecionada);

        Object valorUsuario = modeloUsuarios.getValueAt(linhaModelo, 1);

        if (valorUsuario == null || valorUsuario.toString().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Erro: usuário inválido.");
            return;
        }

        String usuarioSelecionado = valorUsuario.toString();

        JPasswordField campoNovaSenha = new JPasswordField();
        JPasswordField campoConfirmarSenha = new JPasswordField();

        Object[] campos = {
                "Usuário: " + usuarioSelecionado,
                "Nova senha:", campoNovaSenha,
                "Confirmar nova senha:", campoConfirmarSenha
        };

        int opcao = JOptionPane.showConfirmDialog(
                this,
                campos,
                "Alterar senha do usuário",
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

        UsuarioDAO dao = new UsuarioDAO();
        boolean alterou = dao.alterarSenhaPorUsuario(usuarioSelecionado, novaSenha);

        if (alterou) {
            JOptionPane.showMessageDialog(this, "Senha alterada com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar senha.");
        }
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }
}