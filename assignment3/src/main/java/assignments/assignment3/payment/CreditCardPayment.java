package assignments.assignment3.payment;

import assignments.assignment3.User;

public class CreditCardPayment implements DepeFoodPaymentSystem {
    private static final double TRANSACTION_FEE_PERCENTAGE = 0.02;
    private User user;

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean processPayment(long amount) {
        if (!(user.getPayment() instanceof CreditCardPayment)) {
            System.out.println("User belum memiliki metode pembayaran ini!");
            return false;
        }
        long transactionFee = countTransactionFee(amount);
        long total = amount + transactionFee;
        if (total > user.getSaldo()) {
            System.out.println("Saldo tidak mencukupi mohon menggunakan metode pembayaran yang lain");
            return false;
        } else {
            user.setSaldo(user.getSaldo() - total);
            System.out.println("Berhasil Membayar Bill sebesar Rp " + amount + " dengan biaya transaksi sebesar Rp " + transactionFee + "\n");
            return true;
        }
    }

    public long countTransactionFee(long amount) {
        return Math.round(amount * TRANSACTION_FEE_PERCENTAGE);
    }
}
