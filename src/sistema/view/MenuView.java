package sistema.view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuView extends JFrame {

    private static final long serialVersionUID = 1L;

    private String usuario;

    public MenuView(String usuario) {
        this.usuario = usuario;

        setTitle("Menu Principal - Usuário: " + usuario);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel painelPrincipal = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(
                        RenderingHints.KEY_RENDERING,
                        RenderingHints.VALUE_RENDER_QUALITY
                );

                GradientPaint gradiente = new GradientPaint(
                        0, 0, new Color(22, 54, 70),
                        getWidth(), getHeight(), new Color(12, 28, 38)
                );

                g2.setPaint(gradiente);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        painelPrincipal.setLayout(null);
        setContentPane(painelPrincipal);

        JLabel lblTitulo = new JLabel("Menu Principal");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 42));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setBounds(0, 70, 1366, 60);
        painelPrincipal.add(lblTitulo);

        JLabel lblSubtitulo = new JLabel("Escolha qual tela de cadastro deseja acessar");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 22));
        lblSubtitulo.setForeground(new Color(220, 220, 220));
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblSubtitulo.setBounds(0, 130, 1366, 40);
        painelPrincipal.add(lblSubtitulo);

        JLabel lblUsuario = new JLabel("Usuário logado: " + usuario);
        lblUsuario.setFont(new Font("Arial", Font.BOLD, 16));
        lblUsuario.setForeground(new Color(230, 230, 230));
        lblUsuario.setHorizontalAlignment(SwingConstants.RIGHT);
        lblUsuario.setBounds(0, 20, 1320, 30);
        painelPrincipal.add(lblUsuario);

        CardPanel cardOF = new CardPanel();
        cardOF.setLayout(null);
        cardOF.setBounds(260, 230, 360, 280);
        painelPrincipal.add(cardOF);

        JLabel lblIconeOF = new JLabel("OF");
        lblIconeOF.setFont(new Font("Arial", Font.BOLD, 45));
        lblIconeOF.setForeground(new Color(27, 122, 95));
        lblIconeOF.setHorizontalAlignment(SwingConstants.CENTER);
        lblIconeOF.setBounds(0, 25, 360, 70);
        cardOF.add(lblIconeOF);

        JLabel lblTituloOF = new JLabel("Cadastro de Caminhões");
        lblTituloOF.setFont(new Font("Arial", Font.BOLD, 24));
        lblTituloOF.setForeground(new Color(35, 35, 35));
        lblTituloOF.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloOF.setBounds(0, 100, 360, 40);
        cardOF.add(lblTituloOF);

        JLabel lblDescOF = new JLabel("<html><center>Acessar a tela principal de cadastro<br>de caminhões e ordens.</center></html>");
        lblDescOF.setFont(new Font("Arial", Font.PLAIN, 15));
        lblDescOF.setForeground(new Color(90, 90, 90));
        lblDescOF.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescOF.setBounds(30, 145, 300, 55);
        cardOF.add(lblDescOF);

        JButton btnCadastroOF = criarBotao("Acessar Cadastro");
        btnCadastroOF.setBounds(75, 215, 210, 42);
        cardOF.add(btnCadastroOF);

        CardPanel cardMateriaPrima = new CardPanel();
        cardMateriaPrima.setLayout(null);
        cardMateriaPrima.setBounds(740, 230, 360, 280);
        painelPrincipal.add(cardMateriaPrima);

        JLabel lblIconeMP = new JLabel("MP");
        lblIconeMP.setFont(new Font("Arial", Font.BOLD, 45));
        lblIconeMP.setForeground(new Color(27, 122, 95));
        lblIconeMP.setHorizontalAlignment(SwingConstants.CENTER);
        lblIconeMP.setBounds(0, 25, 360, 70);
        cardMateriaPrima.add(lblIconeMP);

        JLabel lblTituloMP = new JLabel("Matéria-Prima");
        lblTituloMP.setFont(new Font("Arial", Font.BOLD, 25));
        lblTituloMP.setForeground(new Color(35, 35, 35));
        lblTituloMP.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloMP.setBounds(0, 100, 360, 40);
        cardMateriaPrima.add(lblTituloMP);

        JLabel lblDescMP = new JLabel("<html><center>Cadastrar chegada, placa, material,<br>fornecedor, nota e status.</center></html>");
        lblDescMP.setFont(new Font("Arial", Font.PLAIN, 15));
        lblDescMP.setForeground(new Color(90, 90, 90));
        lblDescMP.setHorizontalAlignment(SwingConstants.CENTER);
        lblDescMP.setBounds(30, 145, 300, 55);
        cardMateriaPrima.add(lblDescMP);

        JButton btnMateriaPrima = criarBotao("Acessar Matéria-Prima");
        btnMateriaPrima.setBounds(75, 215, 210, 42);
        cardMateriaPrima.add(btnMateriaPrima);

        JButton btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 16));
        btnSair.setForeground(Color.WHITE);
        btnSair.setBackground(new Color(180, 55, 55));
        btnSair.setFocusPainted(false);
        btnSair.setBorderPainted(false);
        btnSair.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSair.setBounds(583, 560, 200, 45);
        painelPrincipal.add(btnSair);

        btnCadastroOF.addActionListener(e -> {
            dispose();
            new CadastroView(usuario).setVisible(true);
        });

        btnMateriaPrima.addActionListener(e -> {
            dispose();
            new MateriaPrimaView(usuario).setVisible(true);
        });

        btnSair.addActionListener(e -> {
            dispose();
            new LoginView().setVisible(true);
        });
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 15));
        botao.setForeground(Color.WHITE);
        botao.setBackground(new Color(27, 122, 95));
        botao.setFocusPainted(false);
        botao.setBorderPainted(false);
        botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return botao;
    }

    private static class CardPanel extends JPanel {

        private static final long serialVersionUID = 1L;

        public CardPanel() {
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(new Color(255, 255, 255));
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 28, 28);

            g2.setColor(new Color(0, 0, 0, 35));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 28, 28);

            g2.dispose();

            super.paintComponent(g);
        }
    }
}