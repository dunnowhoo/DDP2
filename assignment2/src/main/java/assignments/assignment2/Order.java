package assignments.assignment2;

import java.util.ArrayList;

public class Order {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String orderID;
    private String tanggalPemesanan;
    private int biayaOngkosKirim;
    private ArrayList<Menu> items;
    private boolean orderFinished;

    // Constructor
    public Order(String orderID, String tanggalPemesanan, int biayaOngkosKirim, Menu[] items) {
        this.orderID = orderID;
        this.tanggalPemesanan = tanggalPemesanan;
        this.biayaOngkosKirim = biayaOngkosKirim;
        this.items = new ArrayList<Menu>();
        for (Menu item : items) {
            this.items.add(item);
        }
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
}

