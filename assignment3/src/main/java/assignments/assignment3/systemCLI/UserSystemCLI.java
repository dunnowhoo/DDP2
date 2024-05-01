package assignments.assignment3.systemCLI;

import java.util.Scanner;
import java.util.ArrayList;
import assignments.assignment2.Restaurant;
import assignments.assignment3.User;

public abstract class UserSystemCLI {
    protected static Scanner input; // Scanner untuk menerima input dari pengguna
    protected static ArrayList<Restaurant> restoList; // Daftar restoran yang tersedia
    protected static User userLoggedIn; // Pengguna yang sedang login

    // Konstruktor untuk UserSystemCLI
    public UserSystemCLI(ArrayList<Restaurant> restoList) {
        this.restoList = restoList; // Inisialisasi daftar restoran
        this.input = new Scanner(System.in); // Inisialisasi Scanner untuk input
    }

    // Metode untuk menjalankan sistem CLI
    public void run(String name, String noTelp) {
        boolean isLoggedIn = true; // Variabel untuk mengecek apakah pengguna masih login
        while (isLoggedIn) { // Selama pengguna masih login
            displayMenu(); // Tampilkan menu
            int command = input.nextInt(); // Menerima perintah dari pengguna
            input.nextLine(); // Mengkonsumsi newline
            isLoggedIn = handleMenu(command); // Menangani perintah dan memeriksa apakah pengguna masih login
        }
    }

    // Metode untuk mengatur pengguna yang sedang login
    public void setUserLoggedIn(User user) {
        // Mengatur pengguna yang sedang login
        this.userLoggedIn = user;
    }

    // Metode abstrak untuk menampilkan menu, akan diimplementasikan di kelas turunan
    abstract void displayMenu();

    // Metode abstrak untuk menangani menu, akan diimplementasikan di kelas turunan
    // Mengembalikan boolean untuk menentukan apakah pengguna masih login
    abstract boolean handleMenu(int command);
}
