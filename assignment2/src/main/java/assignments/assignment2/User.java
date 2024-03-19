package assignments.assignment2;

import java.util.ArrayList;

public class User {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private String nomorTelepon;
    private String email;
    private String lokasi;
    public String role;
    private ArrayList<Order> orderHistory;

    public User(){
    }
    public User(String nama, String nomorTelepon, String email, String lokasi, String role){
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lokasi = lokasi;
        this.role = role;
        this.orderHistory = new ArrayList<Order>();
    }

    public String getRole() {
        return this.role;
    }

    public String getName() {
        return this.nama;
    }

    public String getNoPhone() {
        return this.nomorTelepon;
    }

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


    // TODO: tambahkan methods yang diperlukan untuk class ini
}
