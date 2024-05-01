package assignments.assignment3.payment;

import assignments.assignment3.User;

public class DebitPayment implements DepeFoodPaymentSystem {
    private static final double MINIMUM_TOTAL_PRICE = 50000;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean processPayment(long amount) {
        if (!(user.getPayment() instanceof DebitPayment)) {
            System.out.println("User belum memiliki metode pembayaran ini!");
            return false;
        }
        if (amount < MINIMUM_TOTAL_PRICE) {
            System.out.println("Jumlah pesanan < 50000 mohon menggunakan metode pembayaran yang lain");
            return false;
        } else if (amount > user.getSaldo()) {
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return false;
        } else {
            user.setSaldo(user.getSaldo() - amount);
            System.out.println("Berhasil Membayar Bill sebesar Rp" + amount + "\n");
            return true;
        }
    }
}