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
        String orderID = "";

        // 4 Karakter Nama Restoran
        int count = 0;
        for (int i = 0; i < namaRestoran.length(); i++) {
            char currentChar = namaRestoran.charAt(i);
            if (currentChar == ' ' && count < 4) {
            }
            else if (Character.isLetter(currentChar) && count < 4) {
                orderID += Character.toUpperCase(currentChar);
                count++;
            }
        }

        // 8 Karakter Tanggal Pemesanan
        for (int i = 0; i < tanggalOrder.length(); i++) {
            char currentChar = tanggalOrder.charAt(i);
            if (currentChar == '/') {
            }
            else {
                orderID += currentChar;
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
        orderID += noTelepon;

        // 2 Karakter Checksum
        String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String code39Encoded = "";
        // Mengonversi setiap karakter dalam inputString menjadi kode numerik Code 39 sekaligus menjumlahkan checksum
        int evenSum = 0;
        int oddSum = 0;
        for (int i = 0; i < orderID.length() ; i++) {
            char currentChar = orderID.charAt(i);
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

        //Generate Order ID
        orderID =orderID + evenChar + oddChar;
        return orderID;
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
    public static String generateBill(String orderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        String bill = "";
        String tanggalOrder = "";
        String ongkos = "";
        // Menentukan harga ongkos kirim
        switch (lokasi) {
            case "P":
                ongkos = "10.000";
                break;
            case "U":
                ongkos = "20.000";
                break;
            case "T":
                ongkos = "35.000";
                break;
            case "S":
                ongkos = "40.000";
                break;
            case "B":
                ongkos = "60.000";
                break;
        }

        // Mendapatkan tanggal order dari order id
        for (int i = 4; i < orderID.length() - 4 ; i++) {
            char currentChar = orderID.charAt(i);
            if (i == 5 || i== 7) {
                tanggalOrder += currentChar + "/";
            }
            else {
                tanggalOrder += currentChar;
            }
        }
        //Generate Bill
        bill = "Bill:" + "\n" + "Order ID: " + orderID + "\n" + "Tanggal Pemesanan: " + tanggalOrder + "\n" + "Lokasi Pengiriman: " + lokasi + "\n" + "Biaya Ongkos Kirim: " + "Rp " + ongkos + "\n" + "";
        return bill;
    }

    public static void main(String[] args) {
        String[] orderIDs = new String[10000];
        String[] bills = new String[10000];
        String[] dataLokasi = {"P","U","T","S","B"};
        int indexOrder = 0;
        int indexBill = 0;

// Loop utama program
        while (true) {
            showMenu(); // Menampilkan menu
            String command;
            System.out.print("Pilihan menu: ");
            command = input.nextLine().trim(); // Membaca perintah dari pengguna

            // Menambahkan pesanan baru
            if (command.equals("1")) {
                String namaRestoran;
                String tanggalOrder;
                String noTelepon;

                // Input data pesanan dari pengguna
                while (true) {
                    System.out.print("Nama Restoran: ");
                    namaRestoran = input.nextLine().trim();
                    if (namaRestoran.length() < 4) {
                        System.out.println("Nama Restoran tidak valid!");
                        continue;
                    }

                    System.out.print("Tanggal Pemesanan: ");
                    tanggalOrder = input.nextLine().trim();
                    // Validasi format tanggal pemesanan
                    if (tanggalOrder.length() != 10 || tanggalOrder.charAt(2) != '/' || tanggalOrder.charAt(5) != '/') {
                        System.out.println("Format Tanggal Pemesanan harus dalam format DD/MM/YYYY");
                        continue;
                    }
                    // Validasi hari (DD)
                    int day = Integer.parseInt(tanggalOrder.substring(0, 2));
                    if (day < 1 || day > 31) {
                        System.out.println("Tanggal tidak valid (1-31)");
                        continue;
                    }
                    // Validasi bulan (MM)
                    int month = Integer.parseInt(tanggalOrder.substring(3, 5));
                    if (month < 1 || month > 12) {
                        System.out.println("Bulan tidak valid (1-12)");
                        continue;
                    }

                    System.out.print("No. Telpon: ");
                    noTelepon = input.nextLine().trim();
                    // Validasi nomor telepon (angka bulat)
                    if (!noTelepon.matches("\\d+")) {
                        System.out.println("Input harus berupa bilangan bulat");
                        continue;
                    }
                    break;
                }
                // Generate dan simpan Order ID
                orderIDs[indexOrder] = generateOrderID(namaRestoran,tanggalOrder,noTelepon);
                System.out.println("Order ID " + orderIDs[indexOrder] + " diterima!" + "\n");
                indexOrder++;
            }
            // Membuat tagihan
            else if (command.equals("2")) {
                while (true) {
                    // Validasi input Order ID
                    if (orderIDs[0] == null){
                        System.out.println("Belum ada data Order ID yang tersedia!");
                        break;
                    }

                    boolean found = false;
                    boolean available = false;
                    System.out.print("Order ID: ");
                    String orderID = input.nextLine().trim();

                    // Mencari Order ID yang cocok
                    for (int i = 0; i < orderIDs.length; i++) {
                        if (orderIDs[i].equals(orderID)) {
                            indexBill = i;
                            if (bills[indexBill] != null) {
                                System.out.println("Bill sudah tersedia");
                                System.out.println(bills[indexBill]);
                                available = true;
                                break;
                            }
                            else {
                                found = true;
                                break;
                            }
                        }
                    }
                    // Jika Bill sudah tersedia
                    if (available) {
                        break;
                    }
                    // Jika Order ID tidak valid
                    if (!found) {
                        System.out.println("Silahkan masukkan Order ID yang valid!");
                        continue;
                    }
                    // Validasi input Lokasi Pengiriman
                    System.out.print("Lokasi Pengiriman: ");
                    String lokasi = input.nextLine().trim().toUpperCase();
                    found = false;
                    // Memeriksa apakah lokasi pengiriman valid
                    for (String loc : dataLokasi) {
                        if (loc.equals(lokasi)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {
                        System.out.println("Harap masukkan lokasi pengiriman yang ada pada jangkauan");
                        continue;
                    }
                    // Generate dan simpan tagihan
                    bills[indexBill] = generateBill(orderID, lokasi);
                    System.out.println(bills[indexBill]);
                    break;
                }
            }
            // Keluar dari program
            else {
                System.out.println("Terima kasih telah menggunakan DepeFood!");
                break;
            }
        }

    }
}
