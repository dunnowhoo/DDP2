package assignments.assignment2;

public class Menu {
     // TODO: tambahkan attributes yang diperlukan untuk class ini
    private String namaMakanan;
    private double harga;
    public Menu(String namaMakanan, double harga){
        // TODO: buat constructor untuk class ini
        this.namaMakanan = namaMakanan;
        this.harga = harga;
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    // Getter untuk nama makanan
    public String getNamaMakanan() {
        return namaMakanan;
    }
    // Getter untuk harga
    public double getHarga() {
        return harga;
    }

    // Method toString() dengan format namaMakanan spasi harga
    @Override
    public String toString() {
        return namaMakanan + " " + (int) harga;
    }
}
