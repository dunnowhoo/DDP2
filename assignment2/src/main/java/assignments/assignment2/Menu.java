package assignments.assignment2;

public class Menu {
    // Attributes yang diperlukan untuk class ini
    private String namaMakanan; // Nama makanan
    private double harga; // Harga makanan

    // Constructor untuk inisialisasi objek Menu dengan nama makanan dan harga
    public Menu(String namaMakanan, double harga){
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    // Getter untuk mendapatkan nama makanan
    public String getNamaMakanan() {
        return namaMakanan;
    }

    // Getter untuk mendapatkan harga makanan
    public double getHarga() {
        return harga;
    }

    // Method toString() untuk menampilkan objek Menu dalam format yang diminta
    @Override
    public String toString() {
        return namaMakanan + " " + (int) harga; // Format: namaMakanan spasi harga
    }
}
