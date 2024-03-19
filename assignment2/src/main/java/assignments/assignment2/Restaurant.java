package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
    // Attributes yang diperlukan untuk class ini
    private String nama; // Nama restoran
    private ArrayList<Menu> menu; // Daftar menu yang ditawarkan oleh restoran

    // Constructor untuk inisialisasi objek Restaurant dengan nama restoran
    public Restaurant(String nama) {
        this.nama = nama;
        this.menu = new ArrayList<Menu>(); // Inisialisasi ArrayList menu
    }

    // Method untuk menambahkan menu ke daftar menu restoran
    public void tambahMenu(Menu menu) {
        this.menu.add(menu);
    }

    // Method untuk menghapus menu dari daftar menu restoran
    public void hapusMenu(Menu menus) {
        this.menu.remove(menus);
    }

    // Getter untuk mendapatkan daftar menu restoran
    public ArrayList<Menu> getMenu() {
        return menu;
    }

    // Getter untuk mendapatkan nama restoran
    public String getNama() {
        return this.nama;
    }

    // Method untuk menampilkan daftar menu restoran dalam format yang diminta
    @Override
    public String toString() {
        // Lakukan bubble sort untuk mengurutkan menu berdasarkan harga terendah ke terbesar
        int n = menu.size();
        for (int i = 0; i < n-1; i++) {
            for (int j = 0; j < n-i-1; j++) {
                if (menu.get(j).getHarga() > menu.get(j+1).getHarga()) {
                    // Tukar elemen jika harga menu pada posisi j lebih besar dari harga menu pada posisi j+1
                    Menu temp = menu.get(j);
                    menu.set(j, menu.get(j+1));
                    menu.set(j+1, temp);
                }
            }
        }

        // Bangun string hasil pengurutan menu
        StringBuilder sb = new StringBuilder();
        sb.append("Menu :\n");
        for (int i = 0; i < menu.size(); i++) {
            sb.append(i + 1).append(". ").append(menu.get(i).toString()).append("\n");
        }
        return sb.toString();
    }
}

