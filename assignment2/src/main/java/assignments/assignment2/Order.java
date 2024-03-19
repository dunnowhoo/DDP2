package assignments.assignment2;

import java.util.ArrayList;
import java.util.Collections;

public class Order {
    // Attributes yang diperlukan untuk class ini
    private String orderID; // ID pesanan
    private String tanggalPemesanan; // Tanggal pesanan
    private int biayaOngkosKirim; // Biaya ongkos kirim
    private Restaurant restaurant; // Restoran terkait
    private ArrayList<Menu> items; // Daftar menu dalam pesanan
    private boolean orderFinished; // Status pesanan

    // Constructor untuk membuat objek Order dengan atribut yang diberikan
    public Order(String orderID, String tanggalPemesanan, int biayaOngkosKirim, Restaurant restaurant, Menu[] items) {
        this.orderID = orderID;
        this.tanggalPemesanan = tanggalPemesanan;
        this.biayaOngkosKirim = biayaOngkosKirim;
        this.restaurant = restaurant;
        this.items = new ArrayList<Menu>();
        Collections.addAll(this.items, items); // Menyalin array Menu ke ArrayList items
        this.orderFinished = false; // Pesanan awalnya belum selesai
    }

    // Getter untuk orderID
    public String getOrderID() {
        return orderID;
    }

    // Getter untuk tanggalPemesanan
    public String getTanggalPemesanan() {
        return tanggalPemesanan;
    }

    // Getter untuk biayaOngkosKirim
    public int getBiayaOngkosKirim() {
        return biayaOngkosKirim;
    }

    // Getter untuk items
    public ArrayList<Menu> getItems() {
        return items;
    }

    // Method untuk menambahkan menu ke dalam pesanan
    public void addItem(Menu item) {
        items.add(item);
    }

    // Method untuk menghapus menu dari pesanan
    public void removeItem(Menu item) {
        items.remove(item);
    }

    // Method untuk menyelesaikan pesanan
    public void finishOrder() {
        orderFinished = true;
    }

    // Method untuk memeriksa apakah pesanan sudah selesai
    public boolean isOrderFinished() {
        return orderFinished;
    }

    // Method toString() dengan format yang diminta
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Bill :\n");
        sb.append("Order ID : ").append(orderID).append("\n");
        sb.append("Tanggal Pemesanan : ").append(tanggalPemesanan).append("\n");
        sb.append("Restaurant : ").append(restaurant != null ? restaurant.getNama() : "Belum ditentukan").append("\n");
        sb.append("Status Pengiriman : ").append(orderFinished ? "Selesai" : "Belum selesai").append("\n");
        sb.append("Pesanan :\n");
        // Konversi ArrayList menjadi array
        Menu[] itemsArray = items.toArray(new Menu[items.size()]);

        // Bubble sort untuk mengurutkan array berdasarkan harga
        int n = itemsArray.length;
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                // Bandingkan harga makanan pada posisi j dengan harga makanan pada posisi j+1
                if (itemsArray[j].getHarga() > itemsArray[j+1].getHarga()) {
                    // Tukar elemen jika harga makanan pada posisi j lebih besar dari harga makanan pada posisi j+1
                    Menu temp = itemsArray[j];
                    itemsArray[j] = itemsArray[j+1];
                    itemsArray[j+1] = temp;
                }
            }
        }

        // Proses array yang sudah diurutkan dalam loop
        for (Menu item : itemsArray) {
            sb.append("- ").append(item.toString()).append("\n");
        }
        sb.append("Biaya Ongkos Kirim : Rp ").append(biayaOngkosKirim).append("\n");
        sb.append("Total Biaya : Rp ").append(hitungTotalBiaya());
        return sb.toString();
    }

    // Method untuk menghitung total biaya
    private int hitungTotalBiaya() {
        int totalBiaya = biayaOngkosKirim;
        for (Menu item : items) {
            totalBiaya += (int) item.getHarga();
        }
        return totalBiaya;
    }
}

