# TP6DPBO2425C1

## JANJI
Saya Dicka Fachrunaldo Kartamiharja dengan NIM 2407846 mengerjakan Tugas Praktikum 6 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

#DAFTAR CLASS
- App – Inisialisasi utama aplikasi
- MainMenu – Tampilan menu awal
- Logic – Inti logika permainan
- View – Komponen tampilan grafis (panel permainan)
- Player – Representasi burung (pemain)
- Pipe – Representasi pipa (rintangan)

# Desain Program
## Komponen Utama
- JFrame (Frame Utama)
  - App: Merupakan entry point dari program (class dengan main()).
  - Membuat JFrame berjudul "Flappy Bird".
  - Mengatur ukuran, posisi tengah layar, dan agar tidak bisa di-resize.
  - Menambahkan panel awal berupa MainMenu.
- Jpanel
  - MainMenu: Panel tampilan menu utama game.
  - Background (gambar latar dari /assets/background.png).
  - Judul game: “Flappy Bird”.
  - Tombol Start Game: Memulai permainan dan mengganti panel menjadi tampilan utama (View).
  - Tombol Exit: Menutup aplikasi.

- Gambar / Aset
  - Background.png : Gambar latar belakang.
  - Bgm_1.wav : Background music ketika game dimulai.
  - bird.png : Gambar dari burung yang dimainkan player.
  - flap.wav : Suara yang dikeluarkan burung ketika terbang (Spasi).
  - game_over.wav : Suara ketika burung menabrak / terjatuh.
  - lowerPipe.png : Gambar dari pipa bawah.
  - upperPipe.png : Gambar dari pipa atas.

- Object Game
  - Player: Objek karakter utama (burung).
    - Menyimpan posisi (X, Y), ukuran, gambar, serta kecepatan vertikal (velocityY).
  - Pipe: Objek rintangan.
    - Menyimpan posisi, ukuran, gambar, kecepatan horizontal (velocityX), dan status apakah sudah dilewati pemain (passed).

## Class dan Object
- Class App
  - Fungsi utama program.
    - Membuat JFrame berukuran 360×640.
    - Menambahkan panel MainMenu ke dalam frame.
    - Menampilkan frame dengan frame.setVisible(true).
  
- Class MainMenu (Extend Jpanel)
  -  Berfungsi sebagai tampilan menu awal.
    -  Background dengan metode paintComponent(Graphics g).
    -  Teks Judul dengan font besar dan warna putih.
    -  Tombol Start Game dan Exit.
  - Event Handling:
    - startButton → Menjalankan fungsi startGame().
    - exitButton → Menutup aplikasi (System.exit(0)).

- Class Player
  - Representasi burung (karakter utama).
  - Menyimpan atribut: Posisi (posX, posY), Ukuran (width, height), Gambar (image), Kecepatan vertikal (velocityY).
  - Digunakan untuk mengontrol gerak naik-turun burung.
  - Getter & Setter lengkap untuk setiap atribut.

- Class Pipe
  - Representasi pipa rintangan.
  - Atribut: Posisi (posX, posY), Ukuran (width, height), Gambar (image), Kecepatan gerak horizontal (velocityX).
  - Status passed: menandakan apakah pipa sudah dilewati burung.
  - Getter & Setter digunakan agar logika game bisa mengupdate posisi, kecepatan, dan status pipa.

## Penjelasan Alur Program
  - Inisialisasi Aplikasi (Class App)
    - Membuat jendela utama (JFrame) dengan ukuran 360×640 piksel.
    - Menambahkan panel MainMenu ke dalamnya.
    - Menampilkan frame di tengah layar.
  
  - Menu Utama (Class MainMenu)
    - Gambar latar belakang.
    - Teks "Flappy Bird" sebagai judul.
    - Tombol Start dan Exit.
    
  - Game Loop
    - Game akan berjalan terus dengan timer loop (biasanya 60 FPS).
    - Setiap frame:
      - Burung digerakkan oleh gravitasi.
      - Pipa bergeser ke kiri.
      - Deteksi tabrakan antara burung dan pipa dilakukan.
      - Jika burung melewati pipa → skor bertambah.

  - Game Over
    - Jika burung menyentuh pipa atau tanah:
      - Game berhenti.
      - Menampilkan pesan “Game Over”.
      - Pemain bisa restart atau kembali ke menu.

## Dokumentasi
https://github.com/user-attachments/assets/a05fbd55-f433-4891-b50b-c4754cd213d3
