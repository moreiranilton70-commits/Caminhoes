package sistema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import sistema.dao.CadastroDAO;
import sistema.dao.MateriaPrimaDAO;
import sistema.dao.UsuarioDAO;
import sistema.model.Cadastro;
import sistema.model.MateriaPrima;
import sistema.model.Usuario;

public class AdminView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabelaOF;
    private DefaultTableModel modeloOF;

    private JTable tabelaMateriaPrima;
    private DefaultTableModel modeloMateriaPrima;

    private JTable tabelaUsuarios;
    private DefaultTableModel modeloUsuarios;

    private JButton btnAtualizarOF;
    private JButton btnAlterarOF;
    private JButton btnExcluirOF;

    private JButton btnAtualizarMateriaPrima;
    private JButton btnAlterarMateriaPrima;
    private JButton btnExcluirMateriaPrima;

    private JButton btnAtualizarUsuarios;
    private JButton btnAlterarSenhaUsuario;
    private JButton btnExcluirUsuario;

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
        abas.setFont(new Font("Arial", Font.BOLD, 15));

        abas.addTab("Consulta OF", criarAbaOF());
        abas.addTab("Consulta Matéria-Prima", criarAbaMateriaPrima());
        abas.addTab("Usuários Cadastrados", criarAbaUsuarios());

        add(abas, BorderLayout.CENTER);

        JPanel painelInferior = new JPanel();

        btnVoltar = new JButton("Voltar para Login");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 15));

        painelInferior.add(btnVoltar);

        add(painelInferior, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });

        carregarOF();
        carregarMateriaPrima();
        carregarUsuarios();
    }

    private JPanel criarAbaOF() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloOF = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloOF.addColumn("ID");
        modeloOF.addColumn("Data");
        modeloOF.addColumn("Hora Cadastro");
        modeloOF.addColumn("Placa");
        modeloOF.addColumn("Número OF");
        modeloOF.addColumn("Número Pager");
        modeloOF.addColumn("OF Troca");
        modeloOF.addColumn("Status");
        modeloOF.addColumn("Autorização");
        modeloOF.addColumn("Hora Autorização");
        modeloOF.addColumn("Observação");
        modeloOF.addColumn("Usuário Cadastro");
        modeloOF.addColumn("Usuário Alteração");
        modeloOF.addColumn("Hora Alteração");

        tabelaOF = new JTable(modeloOF);
        tabelaOF.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaOF.setRowHeight(28);
        tabelaOF.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaOF.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaOF.setAutoCreateRowSorter(true);
        tabelaOF.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        configurarLarguraColunasOF();

        JScrollPane scroll = new JScrollPane(tabelaOF);
        scroll.setPreferredSize(new Dimension(1300, 600));

        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnAtualizarOF = new JButton("Atualizar OF");
        btnAtualizarOF.setFont(new Font("Arial", Font.BOLD, 15));

        btnAlterarOF = new JButton("Alterar OF Selecionada");
        btnAlterarOF.setFont(new Font("Arial", Font.BOLD, 15));

        btnExcluirOF = new JButton("Excluir OF");
        btnExcluirOF.setFont(new Font("Arial", Font.BOLD, 15));

        painelBotoes.add(btnAtualizarOF);
        painelBotoes.add(btnAlterarOF);
        painelBotoes.add(btnExcluirOF);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizarOF.addActionListener(e -> carregarOF());
        btnAlterarOF.addActionListener(e -> alterarOF());
        btnExcluirOF.addActionListener(e -> excluirOF());

        return painel;
    }

    private JPanel criarAbaMateriaPrima() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloMateriaPrima = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloMateriaPrima.addColumn("ID");
        modeloMateriaPrima.addColumn("Data");
        modeloMateriaPrima.addColumn("Hora Chegada");
        modeloMateriaPrima.addColumn("Placa");
        modeloMateriaPrima.addColumn("Material");
        modeloMateriaPrima.addColumn("Fornecedor");
        modeloMateriaPrima.addColumn("Hora Finalizou Pendência");
        modeloMateriaPrima.addColumn("Número Nota");
        modeloMateriaPrima.addColumn("Autorização");
        modeloMateriaPrima.addColumn("Status");
        modeloMateriaPrima.addColumn("Observação");
        modeloMateriaPrima.addColumn("Nota Substituta");
        modeloMateriaPrima.addColumn("Usuário Cadastro");
        modeloMateriaPrima.addColumn("Usuário Alteração");
        modeloMateriaPrima.addColumn("Hora Alteração");

        tabelaMateriaPrima = new JTable(modeloMateriaPrima);
        tabelaMateriaPrima.setFont(new Font("Arial", Font.PLAIN, 14));
        tabelaMateriaPrima.setRowHeight(28);
        tabelaMateriaPrima.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        tabelaMateriaPrima.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaMateriaPrima.setAutoCreateRowSorter(true);
        tabelaMateriaPrima.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        configurarLarguraColunasMateriaPrima();

        JScrollPane scroll = new JScrollPane(tabelaMateriaPrima);
        scroll.setPreferredSize(new Dimension(1300, 600));

        painel.add(scroll, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        btnAtualizarMateriaPrima = new JButton("Atualizar Matéria-Prima");
        btnAtualizarMateriaPrima.setFont(new Font("Arial", Font.BOLD, 15));

        btnAlterarMateriaPrima = new JButton("Alterar Matéria-Prima Selecionada");
        btnAlterarMateriaPrima.setFont(new Font("Arial", Font.BOLD, 15));

        btnExcluirMateriaPrima = new JButton("Excluir Matéria-Prima");
        btnExcluirMateriaPrima.setFont(new Font("Arial", Font.BOLD, 15));

        painelBotoes.add(btnAtualizarMateriaPrima);
        painelBotoes.add(btnAlterarMateriaPrima);
        painelBotoes.add(btnExcluirMateriaPrima);

        painel.add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizarMateriaPrima.addActionListener(e -> carregarMateriaPrima());
        btnAlterarMateriaPrima.addActionListener(e -> alterarMateriaPrima());
        btnExcluirMateriaPrima.addActionListener(e -> excluirMateriaPrima());

        return painel;
    }

    private JPanel criarAbaUsuarios() {

        JPanel painel = new JPanel(new BorderLayout());

        modeloUsuarios = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

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

    private void carregarOF() {

        modeloOF.setRowCount(0);

        CadastroDAO dao = new CadastroDAO();
        List<Cadastro> lista = dao.listarTodos();

        for (Cadastro c : lista) {
            modeloOF.addRow(new Object[] {
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
                    valorSeguro(c.getUsuario()),
                    valorSeguro(c.getUsuarioAlteracao()),
                    valorSeguro(c.getHoraAlteracao())
            });
        }
    }

    private void carregarMateriaPrima() {

        modeloMateriaPrima.setRowCount(0);

        MateriaPrimaDAO dao = new MateriaPrimaDAO();
        List<MateriaPrima> lista = dao.listarTodos();

        for (MateriaPrima mp : lista) {
            modeloMateriaPrima.addRow(new Object[] {
                    mp.getId(),
                    valorSeguro(mp.getData()),
                    valorSeguro(mp.getHoraChegada()),
                    valorSeguro(mp.getPlaca()),
                    valorSeguro(mp.getMaterial()),
                    valorSeguro(mp.getFornecedor()),
                    valorSeguro(mp.getHoraFinalizouPendencia()),
                    valorSeguro(mp.getNumeroNota()),
                    valorSeguro(mp.getAutorizacao()),
                    valorSeguro(mp.getStatus()),
                    valorSeguro(mp.getObservacao()),
                    valorSeguro(mp.getNotaSubstituta()),
                    valorSeguro(mp.getUsuario()),
                    valorSeguro(mp.getUsuarioAlteracao()),
                    valorSeguro(mp.getHoraAlteracao())
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

    private void alterarOF() {

        int linhaSelecionada = tabelaOF.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma OF para alterar.");
            return;
        }

        int linhaModelo = tabelaOF.convertRowIndexToModel(linhaSelecionada);

        int id = Integer.parseInt(modeloOF.getValueAt(linhaModelo, 0).toString());

        JTextField txtData = new JTextField(valorTabela(modeloOF, linhaModelo, 1));
        JTextField txtHoraCadastro = new JTextField(valorTabela(modeloOF, linhaModelo, 2));
        JTextField txtPlaca = new JTextField(valorTabela(modeloOF, linhaModelo, 3));
        JTextField txtNumeroOF = new JTextField(valorTabela(modeloOF, linhaModelo, 4));
        JTextField txtNumeroPager = new JTextField(valorTabela(modeloOF, linhaModelo, 5));
        JTextField txtOfTroca = new JTextField(valorTabela(modeloOF, linhaModelo, 6));

        JComboBox<String> cbStatus = new JComboBox<>(new String[] {
                "OK",
                "Pendente",
                "Aguardando",
                "Finalizado"
        });
        cbStatus.setSelectedItem(valorTabela(modeloOF, linhaModelo, 7));

        JTextField txtAutorizacao = new JTextField(valorTabela(modeloOF, linhaModelo, 8));
        JTextField txtHoraAutorizacao = new JTextField(valorTabela(modeloOF, linhaModelo, 9));

        JTextArea txtObservacao = new JTextArea(valorTabela(modeloOF, linhaModelo, 10), 4, 20);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);

        txtData.setEditable(false);
        txtHoraCadastro.setEditable(false);

        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));

        painel.add(new JLabel("Data:"));
        painel.add(txtData);

        painel.add(new JLabel("Hora Cadastro:"));
        painel.add(txtHoraCadastro);

        painel.add(new JLabel("Placa:"));
        painel.add(txtPlaca);

        painel.add(new JLabel("Número OF:"));
        painel.add(txtNumeroOF);

        painel.add(new JLabel("Número Pager:"));
        painel.add(txtNumeroPager);

        painel.add(new JLabel("OF Troca:"));
        painel.add(txtOfTroca);

        painel.add(new JLabel("Status:"));
        painel.add(cbStatus);

        painel.add(new JLabel("Autorização:"));
        painel.add(txtAutorizacao);

        painel.add(new JLabel("Hora Autorização:"));
        painel.add(txtHoraAutorizacao);

        painel.add(new JLabel("Observação:"));
        painel.add(new JScrollPane(txtObservacao));

        int opcao = JOptionPane.showConfirmDialog(
                this,
                painel,
                "Alterar OF ID " + id,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        if (txtPlaca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A placa não pode ficar vazia.");
            return;
        }

        String horaAlteracao = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        Cadastro c = new Cadastro();

        c.setId(id);
        c.setData(txtData.getText());
        c.setHoraCadastro(txtHoraCadastro.getText());
        c.setPlaca(txtPlaca.getText().trim().toUpperCase());
        c.setNumeroOF(txtNumeroOF.getText().trim());
        c.setNumeroPager(txtNumeroPager.getText().trim());
        c.setOfTroca(txtOfTroca.getText().trim());
        c.setStatus(cbStatus.getSelectedItem().toString());
        c.setAutorizacao(txtAutorizacao.getText().trim());
        c.setHoraAutorizacao(txtHoraAutorizacao.getText().trim());
        c.setObservacao(txtObservacao.getText().trim());
        c.setUsuarioAlteracao(usuarioLogado);
        c.setHoraAlteracao(horaAlteracao);

        CadastroDAO dao = new CadastroDAO();
        boolean alterou = dao.atualizar(c);

        if (alterou) {
            JOptionPane.showMessageDialog(this, "OF alterada com sucesso.");
            carregarOF();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar OF.");
        }
    }

    private void alterarMateriaPrima() {

        int linhaSelecionada = tabelaMateriaPrima.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cadastro de matéria-prima para alterar.");
            return;
        }

        int linhaModelo = tabelaMateriaPrima.convertRowIndexToModel(linhaSelecionada);

        int id = Integer.parseInt(modeloMateriaPrima.getValueAt(linhaModelo, 0).toString());

        JTextField txtData = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 1));
        JTextField txtHoraChegada = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 2));
        JTextField txtPlaca = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 3));
        JTextField txtMaterial = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 4));
        JTextField txtFornecedor = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 5));
        JTextField txtHoraFinalizou = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 6));
        JTextField txtNumeroNota = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 7));
        JTextField txtAutorizacao = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 8));

        JComboBox<String> cbStatus = new JComboBox<>(new String[] {
                "LIBERADO",
                "AGUARDANDO",
                "RECUSADO"
        });
        cbStatus.setSelectedItem(valorTabela(modeloMateriaPrima, linhaModelo, 9));

        JTextArea txtObservacao = new JTextArea(valorTabela(modeloMateriaPrima, linhaModelo, 10), 4, 20);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);

        JTextField txtNotaSubstituta = new JTextField(valorTabela(modeloMateriaPrima, linhaModelo, 11));

        txtData.setEditable(false);
        txtHoraChegada.setEditable(false);

        JPanel painel = new JPanel(new GridLayout(0, 2, 8, 8));

        painel.add(new JLabel("Data:"));
        painel.add(txtData);

        painel.add(new JLabel("Hora Chegada:"));
        painel.add(txtHoraChegada);

        painel.add(new JLabel("Placa:"));
        painel.add(txtPlaca);

        painel.add(new JLabel("Material:"));
        painel.add(txtMaterial);

        painel.add(new JLabel("Fornecedor:"));
        painel.add(txtFornecedor);

        painel.add(new JLabel("Hora Finalizou Pendência:"));
        painel.add(txtHoraFinalizou);

        painel.add(new JLabel("Número Nota:"));
        painel.add(txtNumeroNota);

        painel.add(new JLabel("Autorização:"));
        painel.add(txtAutorizacao);

        painel.add(new JLabel("Status:"));
        painel.add(cbStatus);

        painel.add(new JLabel("Observação:"));
        painel.add(new JScrollPane(txtObservacao));

        painel.add(new JLabel("Nota Substituta:"));
        painel.add(txtNotaSubstituta);

        int opcao = JOptionPane.showConfirmDialog(
                this,
                painel,
                "Alterar Matéria-Prima ID " + id,
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (opcao != JOptionPane.OK_OPTION) {
            return;
        }

        if (txtPlaca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A placa não pode ficar vazia.");
            return;
        }

        if (txtMaterial.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O material não pode ficar vazio.");
            return;
        }

        if (txtFornecedor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O fornecedor não pode ficar vazio.");
            return;
        }

        String horaAlteracao = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        MateriaPrima mp = new MateriaPrima();

        mp.setId(id);
        mp.setData(txtData.getText());
        mp.setHoraChegada(txtHoraChegada.getText());
        mp.setPlaca(txtPlaca.getText().trim().toUpperCase());
        mp.setMaterial(txtMaterial.getText().trim().toUpperCase());
        mp.setFornecedor(txtFornecedor.getText().trim().toUpperCase());
        mp.setHoraFinalizouPendencia(txtHoraFinalizou.getText().trim());
        mp.setNumeroNota(txtNumeroNota.getText().trim());
        mp.setAutorizacao(txtAutorizacao.getText().trim());
        mp.setStatus(cbStatus.getSelectedItem().toString());
        mp.setObservacao(txtObservacao.getText().trim());
        mp.setNotaSubstituta(txtNotaSubstituta.getText().trim());
        mp.setUsuarioAlteracao(usuarioLogado);
        mp.setHoraAlteracao(horaAlteracao);

        MateriaPrimaDAO dao = new MateriaPrimaDAO();
        boolean alterou = dao.atualizar(mp);

        if (alterou) {
            JOptionPane.showMessageDialog(this, "Matéria-prima alterada com sucesso.");
            carregarMateriaPrima();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar matéria-prima.");
        }
    }

    private void excluirOF() {

        int linhaSelecionada = tabelaOF.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma OF para excluir.");
            return;
        }

        int linhaModelo = tabelaOF.convertRowIndexToModel(linhaSelecionada);

        Object valorId = modeloOF.getValueAt(linhaModelo, 0);

        int id = Integer.parseInt(valorId.toString());

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir a OF ID " + id + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        CadastroDAO dao = new CadastroDAO();
        dao.excluir(id);

        JOptionPane.showMessageDialog(this, "OF excluída com sucesso.");

        carregarOF();
    }

    private void excluirMateriaPrima() {

        int linhaSelecionada = tabelaMateriaPrima.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cadastro de matéria-prima para excluir.");
            return;
        }

        int linhaModelo = tabelaMateriaPrima.convertRowIndexToModel(linhaSelecionada);

        Object valorId = modeloMateriaPrima.getValueAt(linhaModelo, 0);

        int id = Integer.parseInt(valorId.toString());

        int confirmacao = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o cadastro de matéria-prima ID " + id + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacao != JOptionPane.YES_OPTION) {
            return;
        }

        MateriaPrimaDAO dao = new MateriaPrimaDAO();
        boolean excluiu = dao.excluir(id);

        if (excluiu) {
            JOptionPane.showMessageDialog(this, "Matéria-prima excluída com sucesso.");
            carregarMateriaPrima();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao excluir matéria-prima.");
        }
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

        int id = Integer.parseInt(valorId.toString());

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
        boolean alterou = dao.alterarSenha(usuarioSelecionado, novaSenha);

        if (alterou) {
            JOptionPane.showMessageDialog(this, "Senha alterada com sucesso.");
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar senha.");
        }
    }

    private void configurarLarguraColunasOF() {
        tabelaOF.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaOF.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabelaOF.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabelaOF.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabelaOF.getColumnModel().getColumn(4).setPreferredWidth(110);
        tabelaOF.getColumnModel().getColumn(5).setPreferredWidth(120);
        tabelaOF.getColumnModel().getColumn(6).setPreferredWidth(110);
        tabelaOF.getColumnModel().getColumn(7).setPreferredWidth(100);
        tabelaOF.getColumnModel().getColumn(8).setPreferredWidth(120);
        tabelaOF.getColumnModel().getColumn(9).setPreferredWidth(130);
        tabelaOF.getColumnModel().getColumn(10).setPreferredWidth(300);
        tabelaOF.getColumnModel().getColumn(11).setPreferredWidth(130);
        tabelaOF.getColumnModel().getColumn(12).setPreferredWidth(130);
        tabelaOF.getColumnModel().getColumn(13).setPreferredWidth(120);
    }

    private void configurarLarguraColunasMateriaPrima() {
        tabelaMateriaPrima.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaMateriaPrima.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabelaMateriaPrima.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabelaMateriaPrima.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabelaMateriaPrima.getColumnModel().getColumn(4).setPreferredWidth(160);
        tabelaMateriaPrima.getColumnModel().getColumn(5).setPreferredWidth(220);
        tabelaMateriaPrima.getColumnModel().getColumn(6).setPreferredWidth(180);
        tabelaMateriaPrima.getColumnModel().getColumn(7).setPreferredWidth(120);
        tabelaMateriaPrima.getColumnModel().getColumn(8).setPreferredWidth(120);
        tabelaMateriaPrima.getColumnModel().getColumn(9).setPreferredWidth(110);
        tabelaMateriaPrima.getColumnModel().getColumn(10).setPreferredWidth(300);
        tabelaMateriaPrima.getColumnModel().getColumn(11).setPreferredWidth(140);
        tabelaMateriaPrima.getColumnModel().getColumn(12).setPreferredWidth(130);
        tabelaMateriaPrima.getColumnModel().getColumn(13).setPreferredWidth(130);
        tabelaMateriaPrima.getColumnModel().getColumn(14).setPreferredWidth(120);
    }

    private String valorTabela(DefaultTableModel modelo, int linha, int coluna) {
        Object valor = modelo.getValueAt(linha, coluna);
        return valor == null ? "" : valor.toString();
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }
}