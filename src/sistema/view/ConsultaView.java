package sistema.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import sistema.dao.CadastroDAO;
import sistema.model.Cadastro;

public class ConsultaView extends JFrame {

    private JTable tabela;
    private DefaultTableModel modelo;
    private String usuario;

    public ConsultaView() {
        this("");
    }

    public ConsultaView(String usuario) {
        this.usuario = usuario;

        setTitle("Consulta de Cadastros");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Data");
        modelo.addColumn("Hora Cadastro");
        modelo.addColumn("Placa");
        modelo.addColumn("Número OF");
        modelo.addColumn("Número Pager");
        modelo.addColumn("OF de Troca");
        modelo.addColumn("Status");
        modelo.addColumn("Autorização");
        modelo.addColumn("Hora Autorização");
        modelo.addColumn("Observação");
        modelo.addColumn("Usuário");

        tabela = new JTable(modelo);
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scroll = new JScrollPane(tabela);
        scroll.setBounds(10, 10, 960, 450);
        add(scroll);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(10, 480, 140, 35);
        btnAtualizar.addActionListener(e -> atualizarTabela());
        add(btnAtualizar);

        JButton btnAlterar = new JButton("Alterar Selecionado");
        btnAlterar.setBounds(170, 480, 180, 35);
        btnAlterar.addActionListener(e -> alterarSelecionado());
        add(btnAlterar);

        JButton btnFechar = new JButton("Fechar");
        btnFechar.setBounds(370, 480, 120, 35);
        btnFechar.addActionListener(e -> dispose());
        add(btnFechar);

        atualizarTabela();
    }

    private void atualizarTabela() {
        modelo.setRowCount(0);

        CadastroDAO dao = new CadastroDAO();
        List<Cadastro> cadastros = dao.listarTodos();

        for (Cadastro c : cadastros) {
            modelo.addRow(new Object[] {
                    c.getHoraCadastro(),
                    c.getData(),
                    c.getHoraCadastro(),
                    c.getPlaca(),
                    c.getNumeroOF(),
                    c.getNumeroPager(),
                    c.getOfTroca(),
                    c.getStatus(),
                    c.getAutorizacao(),
                    c.getHoraAutorizacao(),
                    c.getObservacao(),
                    c.getUsuario()
            });
        }
    }

    private void alterarSelecionado() {
        int linhaSelecionada = tabela.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione uma linha para alterar.");
            return;
        }

        int linhaModelo = tabela.convertRowIndexToModel(linhaSelecionada);

        int id = Integer.parseInt(modelo.getValueAt(linhaModelo, 0).toString());

        String data = valor(linhaModelo, 1);
        String horaCadastro = valor(linhaModelo, 2);
        String placa = valor(linhaModelo, 3);
        String numeroOF = valor(linhaModelo, 4);
        String numeroPager = valor(linhaModelo, 5);
        String ofTroca = valor(linhaModelo, 6);
        String status = valor(linhaModelo, 7);
        String autorizacao = valor(linhaModelo, 8);
        String horaAutorizacao = valor(linhaModelo, 9);
        String observacao = valor(linhaModelo, 10);
        String usuarioCadastro = valor(linhaModelo, 11);

        abrirTelaAlteracao(
                id,
                data,
                horaCadastro,
                placa,
                numeroOF,
                numeroPager,
                ofTroca,
                status,
                autorizacao,
                horaAutorizacao,
                observacao,
                usuarioCadastro
        );
    }

    private String valor(int linha, int coluna) {
        Object valor = modelo.getValueAt(linha, coluna);
        return valor == null ? "" : valor.toString();
    }

    private void abrirTelaAlteracao(
            int id,
            String data,
            String horaCadastro,
            String placa,
            String numeroOF,
            String numeroPager,
            String ofTroca,
            String status,
            String autorizacao,
            String horaAutorizacao,
            String observacao,
            String usuarioCadastro
    ) {

        JDialog dialog = new JDialog(this, "Alterar Cadastro", true);
        dialog.setSize(500, 600);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel painel = new JPanel(new GridLayout(11, 2, 8, 8));

        JTextField txtData = new JTextField(data);
        JTextField txtHoraCadastro = new JTextField(horaCadastro);
        JTextField txtPlaca = new JTextField(placa);
        JTextField txtNumeroOF = new JTextField(numeroOF);
        JTextField txtNumeroPager = new JTextField(numeroPager);
        JTextField txtOfTroca = new JTextField(ofTroca);

        JComboBox<String> cbStatus = new JComboBox<>(new String[] {
                "OK",
                "Pendente",
                "Aguardando",
                "Finalizado"
        });
        cbStatus.setSelectedItem(status);

        JTextField txtAutorizacao = new JTextField(autorizacao);
        JTextField txtHoraAutorizacao = new JTextField(horaAutorizacao);
        JTextArea txtObservacao = new JTextArea(observacao);

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

        painel.add(new JLabel("OF de Troca:"));
        painel.add(txtOfTroca);

        painel.add(new JLabel("Status:"));
        painel.add(cbStatus);

        painel.add(new JLabel("Autorização:"));
        painel.add(txtAutorizacao);

        painel.add(new JLabel("Hora Autorização:"));
        painel.add(txtHoraAutorizacao);

        painel.add(new JLabel("Observação:"));
        painel.add(new JScrollPane(txtObservacao));

        dialog.add(painel, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel();

        JButton btnSalvar = new JButton("Salvar Alteração");
        JButton btnCancelar = new JButton("Cancelar");

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnCancelar);

        dialog.add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> {

            Cadastro c = new Cadastro();

            c.setHoraCadastro(id);
            c.setData(txtData.getText());
            c.setHoraCadastro(txtHoraCadastro.getText());
            c.setPlaca(txtPlaca.getText());
            c.setNumeroOF(txtNumeroOF.getText());
            c.setNumeroPager(txtNumeroPager.getText());
            c.setOfTroca(txtOfTroca.getText());
            c.setStatus(cbStatus.getSelectedItem().toString());
            c.setAutorizacao(txtAutorizacao.getText());
            c.setHoraAutorizacao(txtHoraAutorizacao.getText());
            c.setObservacao(txtObservacao.getText());
            c.setUsuario(usuarioCadastro);

            CadastroDAO dao = new CadastroDAO();
            dao.atualizar(c);

            JOptionPane.showMessageDialog(dialog, "Cadastro alterado com sucesso!");

            dialog.dispose();
            atualizarTabela();
        });

        btnCancelar.addActionListener(e -> dialog.dispose());

        dialog.setVisible(true);
    }
}