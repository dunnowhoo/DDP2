package assignments.assignment1;

import java.util.Scanner;

public class OrderGenerator {
    private static final Scanner input = new Scanner(System.in);

    /* 
    Anda boleh membuat method baru sesuai kebutuhan Anda
    Namun, Anda tidak boleh menghapus ataupun memodifikasi return type method yang sudah ada.
    */

    /*
     * Method  ini untuk menampilkan menu
     */
    public static void showMenu(){
        System.out.println(">>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
        System.out.println();
        System.out.println("Pilih menu:");
        System.out.println("1. Generate Order ID");
        System.out.println("2. Generate Bill");
        System.out.println("3. Keluar");
    }

    /*
     * Method ini digunakan untuk membuat ID
     * dari nama restoran, tanggal order, dan nomor telepon
     * 
     * @return String Order ID dengan format sesuai pada dokumen soal
     */
    public static String generateOrderID(String namaRestoran, String tanggalOrder, String noTelepon) {
        // TODO:Lengkapi method ini sehingga dapat mengenerate Order ID sesuai ketentuan
        String orderId = "";

        // 4 Karakter Nama Restoran
        int count = 0;
        for (int i = 0; i < namaRestoran.length(); i++) {
            char currentChar = namaRestoran.charAt(i);
            if (currentChar == ' ' && count < 4) {
            } else if (Character.isLetter(currentChar) && count < 4) {
                orderId += Character.toUpperCase(currentChar);
                count++;
            }
        }

        // 8 Karakter Tanggal Pemesanan
        for (int i = 0; i < tanggalOrder.length(); i++) {
            char currentChar = tanggalOrder.charAt(i);
            if (currentChar == '/') {
            } else {
                orderId += currentChar;
            }
        }

        // 2 Karakter No Telepon
        int sum = 0;
        for (int i = 0; i < noTelepon.length() ; i++) {
            sum += Character.getNumericValue(noTelepon.charAt(i));
        }
        sum %= 100;
        noTelepon = String.valueOf(sum);
        if (noTelepon.length() == 1) {
            noTelepon = "0" + noTelepon;
        }
        orderId += noTelepon;

        // 2 Karakter Checksum
        String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code39Encoded = "";
        // Mengonversi setiap karakter dalam inputString menjadi kode numerik Code 39 sekaligus menjumlahkan checksum
        int evenSum = 0;
        int oddSum = 0;
        for (int i = 0; i < orderId.length() ; i++) {
            char currentChar = orderId.charAt(i);
            int charIndex = code39CharacterSet.indexOf(currentChar);
            if (charIndex != -1) {
                code39Encoded += charIndex ;
            }
            if (i % 2 == 0) {
                evenSum += charIndex;
            }
            else {
                oddSum += charIndex;
            }
        }
        evenSum %= 36;
        oddSum %= 36;
        // mengubah Value ke Karakter
        char evenChar = code39CharacterSet.charAt(evenSum);
        char oddChar = code39CharacterSet.charAt(oddSum);

        orderId = "Order ID " + orderId + evenChar + oddChar + " diterima!";
        return orderId;
    }


    /*
     * Method ini digunakan untuk membuat bill
     * dari order id dan lokasi
     * 
     * @return String Bill dengan format sesuai di bawah:
     *          Bill:
     *          Order ID: [Order ID]
     *          Tanggal Pemesanan: [Tanggal Pemesanan]
     *          Lokasi Pengiriman: [Kode Lokasi]
     *          Biaya Ongkos Kirim: [Total Ongkos Kirim]
     */
    public static String generateBill(String OrderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        return "Bill";
    }

    public static void main(String[] args) {
        // TODO: Implementasikan program sesuai ketentuan yang diberikan
        while (true) {

            showMenu();
            String command;
            command = input.nextLine();

            switch (command) {
                case "1": {
                    String namaRestoran;
                    String tanggalOrder;
                    String noTelepon;

                    while (true) {
                        System.out.print("Nama Restoran: ");
                        namaRestoran = input.nextLine().trim();
                        if (namaRestoran.length() < 4) {
                            System.out.println("Nama Restoran tidak valid!");
                            continue;
                        }

                        System.out.print("Tanggal Pemesanan: ");
                        tanggalOrder = input.nextLine().trim();
                        // Memeriksa panjang input
                        if (tanggalOrder.length() != 10 || tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/') {
                            System.out.println("Format Tanggal Pemesanan harus dalam format DD/MM/YYYY");
                            continue;
                        }
                        // Memeriksa karakter ke-3 dan ke-4 (DD)
                        int day = Integer.parseInt(tanggalOrder.substring(0, 2));
                        if (day < 1 || day > 31) {
                            System.out.println("Tanggal tidak valid (1-31)");
                            continue;
                        }
                        // Memeriksa karakter ke-6 dan ke-7 (MM)
                        int month = Integer.parseInt(tanggalOrder.substring(3, 5));
                        if (month < 1 || month > 12) {
                            System.out.println("Bulan tidak valid (1-12)");
                            continue;
                        }

                        System.out.print("No. Telpon: ");
                        noTelepon = input.nextLine().trim();
                        // Memeriksa apakah input hanya mengandung angka
                        if (!noTelepon.matches("\\d+")) {
                            System.out.println("Input harus berupa bilangan bulat");
                            continue;
                        }
                        break;
                    }
                    System.out.println(generateOrderID(namaRestoran,tanggalOrder,noTelepon) + "\n");
                }
                case "2": {
                }
                case "3": {
                    System.out.println("Terima kasih telah menggunakan DepeFood!");
                    break;
                }

            }
        }
    }

    
}
