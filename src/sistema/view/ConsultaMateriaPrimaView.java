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

import sistema.dao.MateriaPrimaDAO;
import sistema.model.MateriaPrima;

public class ConsultaMateriaPrimaView extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTable tabela;
    private DefaultTableModel modelo;

    private JButton btnAtualizar;
    private JButton btnAlterar;
    private JButton btnVoltar;

    private String usuarioLogado;

    public ConsultaMateriaPrimaView(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Consulta de Matéria-Prima - Usuário: " + usuarioLogado);
        setSize(1350, 650);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        criarTabela();
        criarBotoes();
        carregarDados();
    }

    private void criarTabela() {

        modelo = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modelo.addColumn("ID");
        modelo.addColumn("Data");
        modelo.addColumn("Hora Chegada");
        modelo.addColumn("Placa");
        modelo.addColumn("Material");
        modelo.addColumn("Fornecedor");
        modelo.addColumn("Hora Finalizou Pendência");
        modelo.addColumn("Número Nota");
        modelo.addColumn("Autorização");
        modelo.addColumn("Status");
        modelo.addColumn("Observação");
        modelo.addColumn("Nota Substituta");
        modelo.addColumn("Usuário Cadastro");
        modelo.addColumn("Usuário Alteração");
        modelo.addColumn("Hora Alteração");

        tabela = new JTable(modelo);
        tabela.setFont(new Font("Arial", Font.PLAIN, 13));
        tabela.setRowHeight(26);
        tabela.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tabela.setAutoCreateRowSorter(true);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabela.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        configurarLarguraColunas();

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setPreferredSize(new Dimension(1300, 500));

        add(scroll, BorderLayout.CENTER);
    }

    private void configurarLarguraColunas() {
        tabela.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabela.getColumnModel().getColumn(1).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(3).setPreferredWidth(90);
        tabela.getColumnModel().getColumn(4).setPreferredWidth(150);
        tabela.getColumnModel().getColumn(5).setPreferredWidth(200);
        tabela.getColumnModel().getColumn(6).setPreferredWidth(170);
        tabela.getColumnModel().getColumn(7).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(8).setPreferredWidth(120);
        tabela.getColumnModel().getColumn(9).setPreferredWidth(110);
        tabela.getColumnModel().getColumn(10).setPreferredWidth(300);
        tabela.getColumnModel().getColumn(11).setPreferredWidth(140);
        tabela.getColumnModel().getColumn(12).setPreferredWidth(130);
        tabela.getColumnModel().getColumn(13).setPreferredWidth(130);
        tabela.getColumnModel().getColumn(14).setPreferredWidth(120);
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

        MateriaPrimaDAO dao = new MateriaPrimaDAO();
        List<MateriaPrima> lista = dao.listarTodos();

        for (MateriaPrima mp : lista) {

            modelo.addRow(new Object[] {
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

    private void alterarCadastro() {

        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um cadastro para alterar.");
            return;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        int id = Integer.parseInt(modelo.getValueAt(linhaModelo, 0).toString());

        JTextField txtData = new JTextField(valorTabela(linhaModelo, 1));
        JTextField txtHoraChegada = new JTextField(valorTabela(linhaModelo, 2));
        JTextField txtPlaca = new JTextField(valorTabela(linhaModelo, 3));
        JTextField txtMaterial = new JTextField(valorTabela(linhaModelo, 4));
        JTextField txtFornecedor = new JTextField(valorTabela(linhaModelo, 5));
        JTextField txtHoraFinalizou = new JTextField(valorTabela(linhaModelo, 6));
        JTextField txtNumeroNota = new JTextField(valorTabela(linhaModelo, 7));
        JTextField txtAutorizacao = new JTextField(valorTabela(linhaModelo, 8));

        JComboBox<String> cbStatus = new JComboBox<>(new String[] {
                "LIBERADO",
                "AGUARDANDO",
                "RECUSADO"
        });
        cbStatus.setSelectedItem(valorTabela(linhaModelo, 9));

        JTextArea txtObservacao = new JTextArea(valorTabela(linhaModelo, 10), 4, 20);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);

        JTextField txtNotaSubstituta = new JTextField(valorTabela(linhaModelo, 11));

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
                "Alterar Cadastro de Matéria-Prima ID " + id,
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
            JOptionPane.showMessageDialog(this, "Cadastro de matéria-prima alterado com sucesso.");
            carregarDados();
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao alterar cadastro de matéria-prima.");
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