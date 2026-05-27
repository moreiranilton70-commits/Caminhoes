package sistema.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import sistema.dao.CadastroDAO;
import sistema.model.Cadastro;

public class CadastroView extends JFrame {

    private JTextField txtData;
    private JTextField txtPlaca;
    private JTextField txtNumeroOF;
    private JTextField txtHoraCadastro;
    private JTextField txtNumeroPager;
    private JTextField txtOfTroca;
    private JComboBox<String> cbStatus;
    private JTextField txtAutorizacao;
    private JTextField txtHoraAutorizacao;
    private JTextArea txtObservacao;

    private JButton btnSalvar;
    private JButton btnConsultar;
    private JButton btnAtualizarDataHora;
    private JButton btnSair;

    private String usuarioLogado;

    public CadastroView(String usuarioLogado) {
        this.usuarioLogado = usuarioLogado;

        setTitle("Cadastro de Caminhões - Usuário: " + usuarioLogado);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        criarTela();

        setLocationRelativeTo(null);
    }

    private void criarTela() {

        JLabel titulo = new JLabel("Cadastro de Caminhões", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));
        titulo.setPreferredSize(new Dimension(100, 70));
        add(titulo, BorderLayout.NORTH);

        JPanel painel = new JPanel(new GridBagLayout());
        add(painel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font fonteLabel = new Font("Arial", Font.BOLD, 16);
        Font fonteCampo = new Font("Arial", Font.PLAIN, 16);

        txtData = new JTextField(20);
        txtData.setFont(fonteCampo);
        txtData.setEditable(false);

        txtHoraCadastro = new JTextField(20);
        txtHoraCadastro.setFont(fonteCampo);
        txtHoraCadastro.setEditable(false);

        atualizarDataHora();

        txtPlaca = new JTextField(20);
        txtPlaca.setFont(fonteCampo);

        txtNumeroOF = new JTextField(20);
        txtNumeroOF.setFont(fonteCampo);

        txtNumeroPager = new JTextField(20);
        txtNumeroPager.setFont(fonteCampo);

        txtOfTroca = new JTextField(20);
        txtOfTroca.setFont(fonteCampo);

        cbStatus = new JComboBox<>(new String[] {
                "OK",
                "Pendente",
                "Aguardando",
                "Finalizado"
        });
        cbStatus.setFont(fonteCampo);

        txtAutorizacao = new JTextField(20);
        txtAutorizacao.setFont(fonteCampo);

        txtHoraAutorizacao = new JTextField(20);
        txtHoraAutorizacao.setFont(fonteCampo);

        txtObservacao = new JTextArea(4, 20);
        txtObservacao.setFont(fonteCampo);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);

        adicionarCampo(painel, gbc, 0, "Data:", txtData, fonteLabel);
        adicionarCampo(painel, gbc, 1, "Hora Cadastro:", txtHoraCadastro, fonteLabel);
        adicionarCampo(painel, gbc, 2, "Placa:", txtPlaca, fonteLabel);
        adicionarCampo(painel, gbc, 3, "Número OF:", txtNumeroOF, fonteLabel);
        adicionarCampo(painel, gbc, 4, "Número Pager:", txtNumeroPager, fonteLabel);
        adicionarCampo(painel, gbc, 5, "OF de Troca:", txtOfTroca, fonteLabel);
        adicionarCampo(painel, gbc, 6, "Status:", cbStatus, fonteLabel);
        adicionarCampo(painel, gbc, 7, "Autorização:", txtAutorizacao, fonteLabel);
        adicionarCampo(painel, gbc, 8, "Hora Autorização:", txtHoraAutorizacao, fonteLabel);

        JLabel lblObservacao = new JLabel("Observação:");
        lblObservacao.setFont(fonteLabel);

        gbc.gridx = 0;
        gbc.gridy = 9;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        painel.add(lblObservacao, gbc);

        gbc.gridx = 1;
        gbc.gridy = 9;
        gbc.weightx = 1;

        JScrollPane scrollObs = new JScrollPane(txtObservacao);
        scrollObs.setPreferredSize(new Dimension(350, 100));
        painel.add(scrollObs, gbc);

        JPanel painelBotoes = new JPanel();

        btnSalvar = new JButton("Salvar");
        btnSalvar.setFont(fonteCampo);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setFont(fonteCampo);

        btnAtualizarDataHora = new JButton("Atualizar Data/Hora");
        btnAtualizarDataHora.setFont(fonteCampo);

        btnSair = new JButton("Sair");
        btnSair.setFont(fonteCampo);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnConsultar);
        painelBotoes.add(btnAtualizarDataHora);
        painelBotoes.add(btnSair);

        add(painelBotoes, BorderLayout.SOUTH);

        btnSalvar.addActionListener(e -> salvar());

        btnConsultar.addActionListener(e -> new ConsultaView(usuarioLogado).setVisible(true));

        btnAtualizarDataHora.addActionListener(e -> {
            atualizarDataHora();
            JOptionPane.showMessageDialog(this, "Data e hora atualizadas.");
        });

        btnSair.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    private void adicionarCampo(JPanel painel, GridBagConstraints gbc, int linha, String texto,
                                java.awt.Component campo, Font fonteLabel) {

        JLabel label = new JLabel(texto);
        label.setFont(fonteLabel);

        gbc.gridx = 0;
        gbc.gridy = linha;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(label, gbc);

        gbc.gridx = 1;
        gbc.gridy = linha;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        painel.add(campo, gbc);
    }

    private void atualizarDataHora() {
        txtData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtHoraCadastro.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void salvar() {

        atualizarDataHora();

        if (txtPlaca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a placa.");
            return;
        }

        Cadastro c = new Cadastro();

        c.setData(txtData.getText());
        c.setPlaca(txtPlaca.getText().trim());
        c.setNumeroOF(txtNumeroOF.getText().trim());
        c.setHoraCadastro(txtHoraCadastro.getText());
        c.setNumeroPager(txtNumeroPager.getText().trim());
        c.setOfTroca(txtOfTroca.getText().trim());
        c.setStatus(cbStatus.getSelectedItem().toString());
        c.setAutorizacao(txtAutorizacao.getText().trim());
        c.setHoraAutorizacao(txtHoraAutorizacao.getText().trim());
        c.setObservacao(txtObservacao.getText().trim());
        c.setUsuario(usuarioLogado);

        CadastroDAO dao = new CadastroDAO();
        dao.inserir(c);

        JOptionPane.showMessageDialog(this, "Cadastro salvo com sucesso!");

        limparCampos();
    }

    private void limparCampos() {

        atualizarDataHora();

        txtPlaca.setText("");
        txtNumeroOF.setText("");
        txtNumeroPager.setText("");
        txtOfTroca.setText("");
        cbStatus.setSelectedIndex(0);
        txtAutorizacao.setText("");
        txtHoraAutorizacao.setText("");
        txtObservacao.setText("");

        txtPlaca.requestFocus();
    }
}