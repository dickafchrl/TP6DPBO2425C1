import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JPanel {
    private JFrame frame;
    private Image background;

    public MainMenu(JFrame frame) {
        this.frame = frame;
        setLayout(null);

        // 🔹 Muat gambar background dari folder assets
        background = new ImageIcon(getClass().getResource("/assets/background.png")).getImage();

        // === Teks judul ===
        JLabel title = new JLabel("Flappy Bird", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 36));
        title.setForeground(Color.WHITE);
        title.setBounds(0, 150, 360, 50);
        add(title);

        // === Tombol Start ===
        JButton startButton = new JButton("Start Game");
        startButton.setBounds(110, 250, 140, 40);
        startButton.setFocusPainted(false);
        add(startButton);

        // === Tombol Exit ===
        JButton exitButton = new JButton("Exit");
        exitButton.setBounds(110, 310, 140, 40);
        exitButton.setFocusPainted(false);
        add(exitButton);

        // Event tombol start
        startButton.addActionListener(e -> startGame());

        // Event tombol exit
        exitButton.addActionListener(e -> System.exit(0));
    }

    // 🔹 Gambar background image
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
    }

    // 🔹 Fungsi memulai game
    private void startGame() {
        frame.getContentPane().removeAll();

        Logic logika = new Logic();
        View tampilan = new View(logika, frame); // ← tambahkan frame di sini
        logika.setView(tampilan);

        frame.add(tampilan);
        frame.revalidate();
        frame.repaint();
        tampilan.requestFocusInWindow();
    }
}