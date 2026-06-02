package sistema.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
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
import javax.swing.border.EmptyBorder;

import sistema.dao.CadastroDAO;
import sistema.model.Cadastro;

public class CadastroView extends JFrame {

    private static final long serialVersionUID = 1L;

    // Coloque sua imagem dentro da pasta: src/images/
    private static final String CAMINHO_IMAGEM_FUNDO = "images/fundo_caminhao.jpg";

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
        setMinimumSize(new Dimension(1000, 700));

        criarTela();

        setLocationRelativeTo(null);
    }

    private void criarTela() {

        BackgroundPanel painelFundo = new BackgroundPanel(CAMINHO_IMAGEM_FUNDO);
        painelFundo.setLayout(new BorderLayout());
        setContentPane(painelFundo);

        JPanel topo = criarTopo();
        painelFundo.add(topo, BorderLayout.NORTH);

        JPanel centro = new JPanel(new GridBagLayout());
        centro.setOpaque(false);
        centro.setBorder(new EmptyBorder(25, 25, 25, 25));

        JPanel cardFormulario = criarCardFormulario();
        centro.add(cardFormulario);

        painelFundo.add(centro, BorderLayout.CENTER);

        JPanel rodape = criarRodape();
        painelFundo.add(rodape, BorderLayout.SOUTH);

        configurarEventos();
    }

    private JPanel criarTopo() {

        JPanel topo = new JPanel(new BorderLayout());
        topo.setOpaque(false);
        topo.setBorder(new EmptyBorder(25, 40, 10, 40));

        JLabel titulo = new JLabel("Cadastro de Caminhões", SwingConstants.CENTER);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);

        JLabel subtitulo = new JLabel("Usuário logado: " + usuarioLogado, SwingConstants.CENTER);
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        subtitulo.setForeground(new Color(230, 230, 230));

        topo.add(titulo, BorderLayout.CENTER);
        topo.add(subtitulo, BorderLayout.SOUTH);

        return topo;
    }

    private JPanel criarCardFormulario() {

        JPanel card = new JPanel(new GridBagLayout());
        card.setOpaque(true);
        card.setBackground(new Color(255, 255, 255, 235));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                new EmptyBorder(25, 35, 25, 35)
        ));

        Font fonteLabel = new Font("Segoe UI", Font.BOLD, 15);
        Font fonteCampo = new Font("Segoe UI", Font.PLAIN, 15);

        txtData = criarCampoTexto(fonteCampo);
        txtData.setEditable(false);

        txtHoraCadastro = criarCampoTexto(fonteCampo);
        txtHoraCadastro.setEditable(false);

        txtPlaca = criarCampoTexto(fonteCampo);
        txtNumeroOF = criarCampoTexto(fonteCampo);
        txtNumeroPager = criarCampoTexto(fonteCampo);
        txtOfTroca = criarCampoTexto(fonteCampo);

        cbStatus = new JComboBox<>(new String[] {
                "OK",
                "Pendente",
                "Aguardando",
                "Finalizado"
        });
        cbStatus.setFont(fonteCampo);
        cbStatus.setPreferredSize(new Dimension(300, 38));
        cbStatus.setBackground(Color.WHITE);

        txtAutorizacao = criarCampoTexto(fonteCampo);
        txtHoraAutorizacao = criarCampoTexto(fonteCampo);

        txtObservacao = new JTextArea(4, 20);
        txtObservacao.setFont(fonteCampo);
        txtObservacao.setLineWrap(true);
        txtObservacao.setWrapStyleWord(true);
        txtObservacao.setBorder(new EmptyBorder(8, 8, 8, 8));

        atualizarDataHora();

        adicionarCampo(card, 0, 0, "Data:", txtData, fonteLabel);
        adicionarCampo(card, 0, 2, "Hora Cadastro:", txtHoraCadastro, fonteLabel);

        adicionarCampo(card, 1, 0, "Placa:", txtPlaca, fonteLabel);
        adicionarCampo(card, 1, 2, "Número OF:", txtNumeroOF, fonteLabel);

        adicionarCampo(card, 2, 0, "Número Pager:", txtNumeroPager, fonteLabel);
        adicionarCampo(card, 2, 2, "OF de Troca:", txtOfTroca, fonteLabel);

        adicionarCampo(card, 3, 0, "Status:", cbStatus, fonteLabel);
        adicionarCampo(card, 3, 2, "Autorização:", txtAutorizacao, fonteLabel);

        adicionarCampo(card, 4, 0, "Hora Autorização:", txtHoraAutorizacao, fonteLabel);

        adicionarObservacao(card, fonteLabel);

        return card;
    }

    private void adicionarCampo(JPanel painel, int linha, int coluna,
                                String texto, Component campo, Font fonteLabel) {

        JLabel label = new JLabel(texto);
        label.setFont(fonteLabel);
        label.setForeground(new Color(45, 45, 45));

        GridBagConstraints gbcLabel = new GridBagConstraints();
        gbcLabel.gridx = coluna;
        gbcLabel.gridy = linha;
        gbcLabel.insets = new Insets(9, 12, 9, 12);
        gbcLabel.weightx = 0;
        gbcLabel.anchor = GridBagConstraints.WEST;
        gbcLabel.fill = GridBagConstraints.HORIZONTAL;

        painel.add(label, gbcLabel);

        GridBagConstraints gbcCampo = new GridBagConstraints();
        gbcCampo.gridx = coluna + 1;
        gbcCampo.gridy = linha;
        gbcCampo.insets = new Insets(9, 12, 9, 12);
        gbcCampo.weightx = 1;
        gbcCampo.anchor = GridBagConstraints.WEST;
        gbcCampo.fill = GridBagConstraints.HORIZONTAL;

        painel.add(campo, gbcCampo);
    }

    private void adicionarObservacao(JPanel card, Font fonteLabel) {

        JLabel lblObservacao = new JLabel("Observação:");
        lblObservacao.setFont(fonteLabel);
        lblObservacao.setForeground(new Color(45, 45, 45));

        GridBagConstraints gbcLabelObs = new GridBagConstraints();
        gbcLabelObs.gridx = 0;
        gbcLabelObs.gridy = 5;
        gbcLabelObs.insets = new Insets(9, 12, 9, 12);
        gbcLabelObs.weightx = 0;
        gbcLabelObs.anchor = GridBagConstraints.NORTHWEST;
        gbcLabelObs.fill = GridBagConstraints.HORIZONTAL;

        card.add(lblObservacao, gbcLabelObs);

        JScrollPane scrollObs = new JScrollPane(txtObservacao);
        scrollObs.setPreferredSize(new Dimension(720, 110));
        scrollObs.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));

        GridBagConstraints gbcScrollObs = new GridBagConstraints();
        gbcScrollObs.gridx = 1;
        gbcScrollObs.gridy = 5;
        gbcScrollObs.gridwidth = 3;
        gbcScrollObs.insets = new Insets(9, 12, 9, 12);
        gbcScrollObs.weightx = 1;
        gbcScrollObs.fill = GridBagConstraints.HORIZONTAL;
        gbcScrollObs.anchor = GridBagConstraints.WEST;

        card.add(scrollObs, gbcScrollObs);
    }

    private JPanel criarRodape() {

        JPanel painelBotoes = new JPanel();
        painelBotoes.setOpaque(false);
        painelBotoes.setBorder(new EmptyBorder(5, 20, 30, 20));

        Font fonteBotao = new Font("Segoe UI", Font.BOLD, 15);

        btnSalvar = criarBotao("Salvar", new Color(20, 120, 70), fonteBotao);
        btnConsultar = criarBotao("Consultar", new Color(35, 90, 160), fonteBotao);
        btnAtualizarDataHora = criarBotao("Atualizar Data/Hora", new Color(230, 145, 30), fonteBotao);
        btnSair = criarBotao("Sair", new Color(150, 45, 45), fonteBotao);

        painelBotoes.add(btnSalvar);
        painelBotoes.add(btnConsultar);
        painelBotoes.add(btnAtualizarDataHora);
        painelBotoes.add(btnSair);

        return painelBotoes;
    }

    private JTextField criarCampoTexto(Font fonte) {

        JTextField campo = new JTextField(20);
        campo.setFont(fonte);
        campo.setPreferredSize(new Dimension(300, 38));
        campo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(190, 190, 190), 1),
                new EmptyBorder(5, 10, 5, 10)
        ));

        return campo;
    }

    private JButton criarBotao(String texto, Color cor, Font fonte) {

        JButton botao = new JButton(texto);
        botao.setFont(fonte);
        botao.setForeground(Color.WHITE);
        botao.setBackground(cor);
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        botao.setPreferredSize(new Dimension(180, 42));

        return botao;
    }

    private void configurarEventos() {

        btnSalvar.addActionListener(e -> salvar());

        btnConsultar.addActionListener(e -> {
            new ConsultaView(usuarioLogado).setVisible(true);
        });

        btnAtualizarDataHora.addActionListener(e -> {
            atualizarDataHora();
            JOptionPane.showMessageDialog(this, "Data e hora atualizadas.");
        });

        btnSair.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    private void atualizarDataHora() {

        txtData.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        txtHoraCadastro.setText(LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
    }

    private void salvar() {

        atualizarDataHora();

        if (txtPlaca.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a placa.");
            txtPlaca.requestFocus();
            return;
        }

        Cadastro c = new Cadastro();

        c.setData(txtData.getText());
        c.setPlaca(txtPlaca.getText().trim().toUpperCase());
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

    private static class BackgroundPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        private Image imagemFundo;

        public BackgroundPanel(String caminhoImagem) {
            try {
                ImageIcon icon = new ImageIcon(caminhoImagem);
                imagemFundo = icon.getImage();
            } catch (Exception e) {
                imagemFundo = null;
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (imagemFundo != null) {
                g.drawImage(imagemFundo, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.setColor(new Color(35, 65, 90));
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            g.setColor(new Color(0, 0, 0, 95));
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
    
}