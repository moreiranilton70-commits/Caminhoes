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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import sistema.dao.CadastroDAO;
import sistema.model.Cadastro;

public class ConsultaView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnAtualizar;
    private JButton btnAlterar;
    private JButton btnVoltar;

    private String usuarioLogado;

    public ConsultaView(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Consulta de Cadastros - Usuário: " + usuarioLogado);
        setSize(1250, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        criarTabela();
        criarBotoes();
        carregarDados();
    }

    private void criarTabela() {

        modelo = new DefaultTableModel() {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Data");
        modelo.addColumn("Hora Cadastro");
        modelo.addColumn("Placa");
        modelo.addColumn("Número OF");
        modelo.addColumn("Número Pager");
        modelo.addColumn("OF Troca");
        modelo.addColumn("Status");
        modelo.addColumn("Autorização");
        modelo.addColumn("Hora Autorização");
        modelo.addColumn("Observação");
        modelo.addColumn("Usuário Cadastro");
        modelo.addColumn("Usuário Alteração");
        modelo.addColumn("Hora Alteração");

        tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(1200, 500));

        add(scroll, BorderLayout.CENTER);
    }

    private void criarBotoes() {

        JPanel painelBotoes = new JPanel();

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setFont(new Font("Arial", Font.BOLD, 14));

        btnAlterar = new JButton("Alterar Cadastro Selecionado");
        btnAlterar.setFont(new Font("Arial", Font.BOLD, 14));

        btnVoltar = new JButton("Voltar");
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));

        painelBotoes.add(btnAtualizar);
        painelBotoes.add(btnAlterar);
        painelBotoes.add(btnVoltar);

        add(painelBotoes, BorderLayout.SOUTH);

        btnAtualizar.addActionListener(e -> carregarDados());

        btnAlterar.addActionListener(e -> alterarCadastro());

        btnVoltar.addActionListener(e -> dispose());
    }

    private void carregarDados() {

        modelo.setRowCount(0);

        CadastroDAO dao = new CadastroDAO();
        List<Cadastro> lista = dao.listarTodos();

        for (Cadastro c : lista) {

            modelo.addRow(new Object[] {
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

    private void alterarCadastro() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cadastro para alterar.");
            return;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        int id = Integer.parseInt(modelo.getValueAt(linhaModelo, 0).toString());

        JTextField txtData = new JTextField(valorTabela(linhaModelo, 1));
        JTextField txtHoraCadastro = new JTextField(valorTabela(linhaModelo, 2));
        JTextField txtPlaca = new JTextField(valorTabela(linhaModelo, 3));
        JTextField txtNumeroOF = new JTextField(valorTabela(linhaModelo, 4));
        JTextField txtNumeroPager = new JTextField(valorTabela(linhaModelo, 5));
        JTextField txtOfTroca = new JTextField(valorTabela(linhaModelo, 6));

        JComboBox<String> cbStatus = new JComboBox<>(new String[] {
                "OK",
                "Pendente",
                "Aguardando",
                "Finalizado"
        });
        cbStatus.setSelectedItem(valorTabela(linhaModelo, 7));

        JTextField txtAutorizacao = new JTextField(valorTabela(linhaModelo, 8));
        JTextField txtHoraAutorizacao = new JTextField(valorTabela(linhaModelo, 9));

        JTextArea txtObservacao = new JTextArea(valorTabela(linhaModelo, 10), 4, 20);
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
                "Alterar Cadastro ID " + id,
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
        c.setPlaca(txtPlaca.getText().trim());
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
            JOptionPane.showMessageDialog(this, "Cadastro alterado com sucesso.");
            carregarDados();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar cadastro.");
        }
    }

    private String valorTabela(int linha, int coluna) {
        Object valor = modelo.getValueAt(linha, coluna);
        return valor == null ? "" : valor.toString();
    }

    private String valorSeguro(String valor) {
        return valor == null ? "" : valor;
    }
}