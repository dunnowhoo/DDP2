package assignments.assignment2;

import java.util.ArrayList;

public class Restaurant {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String nama;
    private ArrayList<Menu> menu;

    public Restaurant(String nama) {
        this.nama = nama;
        this.menu = new ArrayList<Menu>();
    }

    // Method untuk menambahkan menu ke ArrayList
    public void tambahMenu(Menu menu) {
        this.menu.add(menu);
    }

    public void hapusMenu(Menu menus) {
        this.menu.remove(menus);
    }
    // Getter untuk menu
    public ArrayList<Menu> getMenu() {
        return menu;
    }

    public String getNama() {
        return this.nama;
    }
}
