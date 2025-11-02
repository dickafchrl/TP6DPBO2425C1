import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class Logic implements ActionListener, KeyListener {
    int frameWidth = 360;
    int frameHeight = 640;

    // Posisi atribut dan ukuran player
    int playerStartPosX = frameWidth / 2;
    int playerStartPosY = frameHeight / 2;
    int playerWidht = 34;
    int playerHeight = 24;

    // Posisi atribut dan ukuran pipa
    int pipeStarPosX = frameWidth;
    int pipeStarPosY = 0;
    int pipeHeight = 512;
    int pipeWidth = 64;

    View view;
    Image birdImage;
    Player player;

    // Menambahkan list pipa dan gambar
    Image lowerPipeImage;
    Image upperPipeImage;
    ArrayList<Pipe> pipes;

    Timer gameloop;
    Timer pipesCooldown;
    int gravity = 1;

    // Velositi pipa
    int pipeVelocityX = -2;

    // Menu dan restart game
    boolean startMenu = true;
    boolean gameOver = false;

    int score = 0;
    JLabel scoreLabel;

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    private void updateScoreLabel() {
        if (scoreLabel != null) {
            scoreLabel.setText("Score: " + score);
        }
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
        updateScoreLabel();
    }


    public Logic() {
        playBackgroundMusic();

        birdImage = new ImageIcon(getClass().getResource("/assets/bird.png")).getImage();
        player = new Player(playerStartPosX, playerStartPosY, playerWidht, playerHeight, birdImage);

        lowerPipeImage = new ImageIcon(getClass().getResource("/assets/lowerPipe.png")).getImage();
        upperPipeImage = new ImageIcon(getClass().getResource("/assets/upperPipe.png")).getImage();
        pipes = new ArrayList<Pipe>();

        pipesCooldown = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Pipa");
                placePipe();
            }
        });
        pipesCooldown.start();

        gameloop = new Timer(1000/60, this);
        gameloop.start();
    }

    public void setView(View view) {
        this.view = view;
    }

    public Player getPlayer() {
        return player;
    }

    public ArrayList<Pipe> getPipes () {
        return pipes;
    }

    public void placePipe() {
        int randomPosY = (int) (pipeStarPosY - pipeHeight / 4 - Math.random() * (pipeHeight / 2));
        int openingSpace = frameHeight / 4;

        Pipe upperPipe = new Pipe(pipeStarPosX, randomPosY, pipeWidth, pipeHeight, upperPipeImage);
        pipes.add(upperPipe);

        Pipe lowerPipe = new Pipe(pipeStarPosX, (randomPosY + openingSpace + pipeHeight), pipeWidth, pipeHeight,
                lowerPipeImage);
        pipes.add(lowerPipe);
    }

    public void move() {
        if (startMenu || gameOver) return;

        player.setVelocityY(player.getVelocityY() + gravity);
        player.setPosY(player.getPosY() + player.getVelocityY());
        player.setPosY(Math.max(player.getPosY(), 0));

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Cek jika player melewati pipa bagian atas
            if (!pipe.passed && pipe.getImage() == upperPipeImage) {
                if (player.getPosX() > pipe.getPosX() + pipe.getWidth()) {
                    pipe.passed = true;
                    score++;
                    updateScoreLabel();
                }
            }
        }

        for (int i = 0; i < pipes.size(); i++) {
            Pipe pipe = pipes.get(i);
            pipe.setPosX(pipe.getPosX() + pipeVelocityX);

            // Deteksi tabrakan
            Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeigth());

            if (playerRect.intersects(pipeRect)) {
                gameOver();
            }
        }

        // Deteksi jatuh atau keluar layar
        if (player.getPosY() + player.getHeight() >= frameHeight) {
            gameOver();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (view != null) {
            view.repaint();
        }
    }

    // Game over
    public void gameOver() {
        if (gameOver) return; // hindari pemanggilan ganda
        stopBackgroundMusic();
        playSound("/assets/game_over.wav");

        gameOver = true;
        gameloop.stop();
        pipesCooldown.stop();
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }

    // Tambahkan logika pengecekan tabrakan dan jatuh
    public void checkCollision() {
        // Jika burung keluar layar bawah
        if (player.getPosY() > frameHeight) {
            gameOver = true;
            gameloop.stop();
            pipesCooldown.stop();
        }

        // Jika tabrakan dengan pipa
        for (Pipe pipe : pipes) {
            Rectangle pipeRect = new Rectangle(pipe.getPosX(), pipe.getPosY(), pipe.getWidth(), pipe.getHeigth());
            Rectangle playerRect = new Rectangle(player.getPosX(), player.getPosY(), player.getWidth(), player.getHeight());
            if (playerRect.intersects(pipeRect)) {
                gameOver = true;
                gameloop.stop();
                pipesCooldown.stop();
            }
        }
    }


    // Reset game
    public void resetGame() {
        // Reset semua nilai ke kondisi awal
        player.setPosX(playerStartPosX);
        player.setPosY(playerStartPosY);
        player.setVelocityY(0);

        // hapus semua pipa dari layar
        pipes.clear();

        startMenu = true;
        gameOver = false;

        // reset score menjadi 0
        score = 0;
        updateScoreLabel();

        // Me
        playBackgroundMusic();

        // Hidupkan kembali timer
        gameloop.start();
        pipesCooldown.start();
    }

    // Back to menu
    public void backToMenu() {
        stopBackgroundMusic(); // hentikan musik game
        if (view != null) {
            JFrame frame = view.getFrame();
            frame.getContentPane().removeAll();
            frame.add(new MainMenu(frame)); // kembali ke menu utama
            frame.revalidate();
            frame.repaint();
        }
    }


    // BGM
    Clip bgMusic;

    public void playSound(String soundFile) {
        try {
            URL soundPath = getClass().getResource(soundFile);
            if (soundPath == null) {
                System.out.println("File sound tidak ditemukan: " + soundFile);
                return;
            }
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundPath);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBackgroundMusic() {
        try {
            if (bgMusic != null && bgMusic.isRunning()) {
                bgMusic.stop();
                bgMusic.close();
            }

            URL soundPath = getClass().getResource("/assets/bgm_1.wav");
            if (soundPath == null) {
                System.out.println("Background music tidak ditemukan!");
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(soundPath);
            bgMusic = AudioSystem.getClip();
            bgMusic.open(audioIn);
            bgMusic.loop(Clip.LOOP_CONTINUOUSLY);
            bgMusic.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (bgMusic != null && bgMusic.isRunning()) {
            bgMusic.stop();
            bgMusic.close();
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) {
        if (startMenu && e.getKeyCode() == KeyEvent.VK_SPACE) {
            startMenu = false;
            pipes.clear();
            player.setPosY(playerStartPosY);
            player.setVelocityY(0);
            gameloop.start();
            pipesCooldown.start();
            return;
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            player.setVelocityY(-10);
            playSound("/assets/flap.wav");
        }

        if (e.getKeyCode() == KeyEvent.VK_R && gameOver) {
            resetGame();
        }

        if (e.getKeyCode() == KeyEvent.VK_M && gameOver) {
            backToMenu();
        }
    }
    public void keyReleased(KeyEvent e) {}
}