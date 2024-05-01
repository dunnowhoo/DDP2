package assignments.assignment3.payment;

import assignments.assignment3.User;

public class DebitPayment implements DepeFoodPaymentSystem {
    private static final double MINIMUM_TOTAL_PRICE = 50000; // Harga total minimum untuk metode pembayaran ini
    private User user; // Pengguna yang melakukan pembayaran

    // Metode untuk mengatur pengguna yang melakukan pembayaran
    public void setUser(User user) {
        this.user = user;
    }

    // Metode untuk memproses pembayaran
    @Override
    public long processPayment(long amount) {
        // Jika pengguna tidak memiliki metode pembayaran Debit
        if (!(user.getPayment() instanceof DebitPayment)) {
            System.out.println("User belum memiliki metode pembayaran ini!");
            return 0;
        }
        // Jika jumlah pesanan kurang dari harga total minimum
        if (amount < MINIMUM_TOTAL_PRICE) {
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
            return 0;
        }
        // Jika saldo pengguna tidak mencukupi
        else if (amount > user.getSaldo()) {
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return 0;
        }
        // Jika semua syarat terpenuhi, proses pembayaran
        else {
            user.setSaldo(user.getSaldo() - amount); // Kurangi saldo pengguna
            System.out.println("Berhasil Membayar Bill sebesar Rp" + amount + "\n"); // Tampilkan pesan sukses
            return amount; // Mengembalikan jumlah yang dibayar
        }
    }
}