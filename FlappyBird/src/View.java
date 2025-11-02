import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class View extends JPanel {
    int width = 360;
    int heigth = 640;

    private Image backgroundImage;
    private Logic logic;
    private JFrame frame;

    public View(Logic logic, JFrame frame) {
        this.logic = logic;
        this.frame = frame;

        setPreferredSize(new Dimension(width, heigth));
        setBackground(Color.cyan);

        setFocusable(true);
        addKeyListener(logic);

        // Tambahkan background
        backgroundImage = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();

        setLayout(null); // Supaya bisa atur posisi bebas

        JLabel scoreLabel = new JLabel("Score: 0");
        scoreLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setBounds(20, 20, 200, 30);

        add(scoreLabel);

        // Hubungkan ke logic
        logic.setScoreLabel(scoreLabel);
    }

    public JFrame getFrame() {
        return frame;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.drawImage(backgroundImage, 0, 0, width, heigth, null);

        Player player = logic.getPlayer();
        ArrayList<Pipe> pipes = logic.getPipes();

        // Gambar player
        if (player != null) {
            g.drawImage(player.getImage(), player.getPosX(), player.getPosY(),
                    player.getWidth(), player.getHeight(), null);
        }

        // Gambar pipa
        if (pipes != null) {
            for (Pipe pipe : pipes) {
                g.drawImage(pipe.getImage(), pipe.getPosX(), pipe.getPosY(),
                        pipe.getWidth(), pipe.getHeigth(), null);
            }
        }

        if (logic.startMenu == true) {
            g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            g.setColor(Color.BLACK); // biar kelihatan

            String text = "SPACE - MULAI";

            // Ambil ukuran teks berdasarkan font sekarang
            FontMetrics fm = g.getFontMetrics();
            int textWidth = fm.stringWidth(text);

            // Hitung posisi tengah horizontal
            int x = (getWidth() - textWidth) / 2;

            // Atur posisi tinggi (y) — misalnya 80px dari atas
            int y = 400;

            // Gambar teks
            g.drawString(text, x, y);
        }

        // === TAMPILKAN MENU GAME OVER ===
        if (logic.isGameOver()) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, width, heigth);

            g.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            g.setColor(Color.white);

            FontMetrics fm = g.getFontMetrics();

            // Teks yang akan di tampilkan
            String text0 = "GAME OVER";
            String text1 = "R - Restart";
            String text2 = "M - Menu";

            // Hitung posisi X agar teks berada di tengah
            int x0 = (width - fm.stringWidth(text0)) / 2;
            int x1 = (width - fm.stringWidth(text1)) / 2;
            int x2 = (width - fm.stringWidth(text2)) / 2;

            // Tentukan posisi Y (tinggi tetap)
            int y0 = heigth / 2 - 30;
            int y1 = heigth / 2;
            int y2 = heigth / 2 + 30;

            // Gambar teks
            g.drawString(text0, x0, y0);
            g.drawString(text1, x1, y1);
            g.drawString(text2, x2, y2);
        }
    }
}
