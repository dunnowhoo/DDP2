package assignments.assignment2;

import java.util.ArrayList;

public class User {
    // Attributes yang diperlukan untuk class ini
    private String nama; // Nama pengguna
    private String nomorTelepon; // Nomor telepon pengguna
    private String email; // Alamat email pengguna
    private String lokasi; // Lokasi pengguna
    public String role; // Peran pengguna
    private ArrayList<Order> orderHistory; // Riwayat pesanan pengguna

    // Constructor untuk inisialisasi objek User dengan atribut yang diberikan
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.orderHistory = new ArrayList<Order>(); // Inisialisasi ArrayList orderHistory
    }

    // Getter untuk mendapatkan peran pengguna
    public String getRole() {
        return this.role;
    }

    // Getter untuk mendapatkan nama pengguna
    public String getName() {
        return this.nama;
    }

    // Getter untuk mendapatkan nomor telepon pengguna
    public String getNoPhone() {
        return this.nomorTelepon;
    }

    // Getter untuk mendapatkan lokasi pengguna
    public String getLokasi(){
        return this.lokasi;
    }

    // Method untuk menambahkan pesanan ke orderHistory
    public void addOrder(Order order) {
        orderHistory.add(order);
    }

    // Method untuk mendapatkan orderHistory
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }
}
