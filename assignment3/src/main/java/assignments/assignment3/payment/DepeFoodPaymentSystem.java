package assignments.assignment3.payment;

import assignments.assignment3.User;

public interface DepeFoodPaymentSystem {
    //TODO implementasikan interface di sini
    // Atribut harap mengikuti dokumen soal
    // Anda dibebaskan untuk membuat method yang diperlukan

    // Metode untuk memproses pembayaran dengan jumlah tertentu
    // Parameter: jumlah pembayaran
    // Mengembalikan: true jika pembayaran berhasil, false jika gagal
    boolean processPayment(long amount);

    // Metode untuk mengatur pengguna yang melakukan pembayaran
    // Parameter: objek User
    void setUser(User user);
}
