package assignments.assignment3.payment;

import assignments.assignment3.User;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02; // Persentase biaya transaksi untuk pembayaran dengan kartu kredit
    private User user; // Pengguna yang melakukan pembayaran

    // Metode untuk mengatur pengguna yang melakukan pembayaran
    public void setUser(User user) {
        this.user = user;
    }

    // Metode untuk memproses pembayaran
    @Override
    public boolean processPayment(long amount) {
        // Jika pengguna tidak memiliki metode pembayaran Credit Card
        if (!(user.getPayment() instanceof CreditCardPayment)) {
            System.out.println("User belum memiliki metode pembayaran ini!");
            return false;
        }
        // Menghitung biaya transaksi
        long transactionFee = countTransactionFee(amount);
        // Menghitung total yang harus dibayar (jumlah pesanan + biaya transaksi)
        long total = amount + transactionFee;
        // Jika saldo pengguna tidak mencukupi
        if (total > user.getSaldo()) {
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return false;
        } else {
            // Jika saldo mencukupi, kurangi saldo pengguna sebesar total
            user.setSaldo(user.getSaldo() - total);
            // Tampilkan pesan sukses
            System.out.println("Berhasil Membayar Bill sebesar Rp " + amount + " dengan biaya transaksi sebesar Rp " + transactionFee + "\n");
            return true; // Pembayaran berhasil
        }
    }

    // Metode untuk menghitung biaya transaksi
    public long countTransactionFee(long amount) {
        // Biaya transaksi adalah persentase dari jumlah pesanan
        return Math.round(amount * TRANSACTION_FEE_PERCENTAGE);
    }
}
