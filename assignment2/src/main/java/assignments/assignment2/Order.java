package assignments.assignment2;

import java.util.ArrayList;
import java.util.Collections;

public class Order {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private Restaurant restaurant;
    private ArrayList<Menu> items;
    private boolean orderFinished;

    // Constructor
    public Order(String orderID, String tanggalPemesanan, int biayaOngkosKirim, Restaurant restaurant, Menu[] items) {
        this.orderID = orderID;
        this.tanggalPemesanan = tanggalPemesanan;
        this.biayaOngkosKirim = biayaOngkosKirim;
        this.restaurant = restaurant;
        this.items = new ArrayList<Menu>();
        Collections.addAll(this.items, items);
        this.orderFinished = false;
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
        for (Menu item : items) {
            sb.append("- ").append(item.toString()).append("\n");
        }
        sb.append("Biaya Ongkos Kirim : Rp ").append(biayaOngkosKirim).append("\n");
        sb.append("Total Biaya : Rp ").append(hitungTotalBiaya()).append("\n");
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

