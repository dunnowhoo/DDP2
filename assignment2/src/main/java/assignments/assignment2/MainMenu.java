package assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;
    private static final String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        boolean programRunning = true;
        initUser();
        restoList = new ArrayList<Restaurant>();
        while(programRunning){
            printHeader();
            startMenu();
            int command = input.nextInt();
            input.nextLine();

            if(command == 1){
                String nama;
                String noTelp;
                
                while (true){
                    System.out.println("\nSilakan Login:");
                    System.out.print("Nama: ");
                    nama = input.nextLine();
                    System.out.print("Nomor Telepon: ");
                    noTelp = input.nextLine();
                    boolean found = false;

                    // TODO: Validasi input login
                    for (User user : userList) {
                        if (user.getName().equals(nama) && user.getNoPhone().equals(noTelp)){
                            found = true;
                            break;
                        }
                    }
                    if (!found){
                        System.out.println("Pengguna dengan data tersebut tidak ditemukan!");
                        continue;
                    }
                    break;
                }
                
                User userLoggedIn = getUser(nama, noTelp);// TODO: lengkapi
                boolean isLoggedIn = true;
                //
                if(userLoggedIn.getRole().equals("Customer")){
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill();
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    while (isLoggedIn){
                        System.out.println("Selamat Datang Admin!");
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }
            }else if(command == 2){
                programRunning = false;
            }else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("\nTerima kasih telah menggunakan DepeFood ^___^");
    }

    public static User getUser(String nama, String nomorTelepon){
        // TODO: Implementasi method untuk mendapat user dari userList
        for (User user : userList) {
            if (user.getName().equals(nama) && user.getNoPhone().equals(nomorTelepon)){
                return user;
            }
        }
        return null;
    }

    public static void handleBuatPesanan(User user) {
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        String namaRestoran;
        String tanggalOrder;
        String noTelepon;
        int jumlahPesanan;
        boolean found = false;
        Restaurant restaurant;
        String orderID;
        Order userOrder;

        // Input data pesanan dari pengguna
        while (true) {
            System.out.print("Nama Restoran: ");
            namaRestoran = input.nextLine().trim();
            if (namaRestoran.length() < 4) {
                System.out.println("Nama Restoran tidak valid!");
                continue;
            }
            if (restoList != null && !restoList.isEmpty()){
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equals(namaRestoran)) {
                        restaurant = resto;
                        found = true;
                        break;
                    }
                }
            }

            if (!found) {
                System.out.println("Restoran tidak terdaftar pada sistem!");
                continue;
            }

            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
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

            noTelepon = user.getNoPhone();

            orderID = generateOrderID(namaRestoran, tanggalOrder, noTelepon);

            String lokasi = user.getLokasi();
            int ongkos = 0;

            // Menentukan harga ongkos kirim
            switch (lokasi) {
                case "P":
                    ongkos = 10000;
                    break;
                case "U":
                    ongkos = 20000;
                    break;
                case "T":
                    ongkos = 35000;
                    break;
                case "S":
                    ongkos = 40000;
                    break;
                case "B":
                    ongkos = 60000;
                    break;
            }

            System.out.print("Jumlah Pesanan: ");
            jumlahPesanan = Integer.parseInt(input.nextLine().trim());

            Menu[] menu = new Menu[jumlahPesanan];
            String[] namaMenu = new String[jumlahPesanan];
            boolean available = true;

            while (true) {
                for (int i = 0; i < jumlahPesanan; i++) {
                    namaMenu[i] = input.nextLine().trim();
                }

                int index = 0;
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equals(namaRestoran)){
                        for (Menu m : resto.getMenu()) {
                            if (m.getNamaMakanan().equals(namaMenu[index])) {
                                menu[index] = m;
                                index++;
                            } else {
                                System.out.println("Mohon memesan menu yang tersedia pada pesanan.");
                                available = false;
                                break;
                            }
                        }
                        break;
                    }
                }

                if (available) {
                    userOrder = new Order(orderID, tanggalOrder, ongkos, menu);
                    user.addOrder(userOrder);
                    break;
                }
            }
            break;

        }
        // Generate Order ID
        System.out.println("Pesanan dengan ID " + orderID + " diterima!" + "\n");
    }

    public static void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
    }

    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
    }

    public static void handleUpdateStatusPesanan(){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
    }

    public static void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        String namaResto;
        int jumlahMakanan;
        boolean found = false;
        String [] menu;
        double [] harga;
        Restaurant newResto;
        Menu newMenu;

        while (true){
            found = false;
            System.out.print("Nama: ");
            namaResto = input.nextLine().trim();

            if (namaResto.length() < 4) {
                System.out.println("Nama Restoran tidak valid!");
                continue;
            }

            if (restoList != null && !restoList.isEmpty()){
                for (Restaurant resto : restoList){
                    if (resto.getNama().equals(namaResto)){
                        System.out.println("Restoran dengan nama " + resto.getNama() + " sudah pernah terdaftar. Mohon masukkan nama yang berbeda!");
                        found = true;
                        break;
                    }
                }
            }

            if (found){
                continue;
            }
            else{
                newResto = new Restaurant(namaResto);
                restoList.add(newResto);
            }

            System.out.print("Jumlah Makanan: ");
            jumlahMakanan = Integer.parseInt(input.nextLine().trim());

            // Variabel untuk menandai apakah terjadi kesalahan saat mengolah input
            menu = new String[jumlahMakanan];
            harga = new double[jumlahMakanan];
            boolean terjadiKesalahan;

            do {
                terjadiKesalahan = false; // Setel terjadiKesalahan ke false pada awal setiap iterasi

                for (int i = 0; i < jumlahMakanan; i++) {
                    String inputMenu;
                    inputMenu = input.nextLine().trim();

                    // Memisahkan input berdasarkan spasi
                    String[] parts = inputMenu.split(" ");

                    // Variabel menu akan mengambil bagian pertama dari array
                    String menuString = "";

                    // Menggabungkan kembali bagian menu
                    for (int j = 0; j < parts.length - 1; j++) {
                        menuString += parts[j] + " ";
                    }
                    menuString = menuString.trim(); // Menghapus spasi ekstra di akhir

                    menu[i] = menuString; // Menghapus spasi ekstra di akhir

                    // Variabel harga akan mengambil bagian terakhir dari array dan mengonversinya ke tipe double
                    double hargaDouble;
                    try {
                        hargaDouble = Double.parseDouble(parts[parts.length - 1]);
                        harga[i] = hargaDouble;
                    } catch (NumberFormatException e) {
                        terjadiKesalahan = true; // Menandai bahwa terjadi kesalahan saat mengolah input
                    }

                }

                // Jika terjadi kesalahan saat mengolah input, minta pengguna untuk memasukkan kembali menu yang valid
                if (terjadiKesalahan) {
                    System.out.println("Harga menu harus bilangan bulat!");
                }
                else {
                    for (int k = 0; k < jumlahMakanan; k++){
                        newMenu = new Menu(menu[k], harga[k]);
                        newResto.tambahMenu(newMenu);
                    }
                }
            } while (terjadiKesalahan); // Ulangi selama terjadi kesalahan
            System.out.println("Restaurant " + namaResto + " Berhasil terdaftar.");
            break;
        }
    }

    public static void handleHapusRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        String namaResto;
        boolean success = false;
        while (true){
            System.out.print("Nama Restoran: ");
            namaResto = input.nextLine().trim();

            for (int i = 0; i < restoList.size(); i++) {
                if (restoList.get(i).getNama().equals(namaResto)) {
                    restoList.remove(i);
                    System.out.println("Restoran berhasil dihapus.");
                    success = true;
                    break; // Menghentikan iterasi setelah menghapus objek
                }
            }
            if (success) {
                break;
            }
            System.out.println("Restoran tidak terdaftar pada sistem.");
        }
    }

    public static void initUser(){
       userList = new ArrayList<User>();
       userList.add(new User("Thomas N", "9928765403", "thomas.n@gmail.com", "P", "Customer"));
       userList.add(new User("Sekar Andita", "089877658190", "dita.sekar@gmail.com", "B", "Customer"));
       userList.add(new User("Sofita Yasusa", "084789607222", "sofita.susa@gmail.com", "T", "Customer"));
       userList.add(new User("Dekdepe G", "080811236789", "ddp2.gampang@gmail.com", "S", "Customer"));
       userList.add(new User("Aurora Anum", "087788129043", "a.anum@gmail.com", "U", "Customer"));

       userList.add(new User("Admin", "123456789", "admin@gmail.com", "-", "Admin"));
       userList.add(new User("Admin Baik", "9123912308", "admin.b@gmail.com", "-", "Admin"));
    }

    public static void printHeader(){
        System.out.println("\n>>=======================================<<");
        System.out.println("|| ___                 ___             _ ||");
        System.out.println("||| . \\ ___  ___  ___ | __>___  ___  _| |||");
        System.out.println("||| | |/ ._>| . \\/ ._>| _>/ . \\/ . \\/ . |||");
        System.out.println("|||___/\\___.|  _/\\___.|_| \\___/\\___/\\___|||");
        System.out.println("||          |_|                          ||");
        System.out.println(">>=======================================<<");
    }

    public static void startMenu(){
        System.out.println("Selamat datang di DepeFood!");
        System.out.println("--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Login");
        System.out.println("2. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuAdmin(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Tambah Restoran");
        System.out.println("2. Hapus Restoran");
        System.out.println("3. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    public static void menuCustomer(){
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Update Status Pesanan");
        System.out.println("5. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

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

    public static String generateBill(String orderID, String lokasi){
        // TODO:Lengkapi method ini sehingga dapat mengenerate Bill sesuai ketentuan
        lokasi = lokasi.toUpperCase();
        String bill = "";
        String tanggalOrder = "";
        String ongkos = "";

        // validasi orderID
        if (orderID.length() < 16) {
            return ("Order ID minimal 16 karakter");
        }
        // checksum
        int evenSum = 0;
        int oddSum = 0;
        for (int i = 0; i < orderID.length() - 2 ; i++) {
            char currentChar = orderID.charAt(i);
            int charIndex = code39CharacterSet.indexOf(currentChar);
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

        // Jika orderID tidak valid
        if (evenChar != orderID.charAt(orderID.length() - 2) || oddChar != orderID.charAt(orderID.length() - 1)){
            return ("Silahkan masukkan Order ID yang valid!");
        }

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
        bill = "Bill:\n" + //
                "Order ID: " + orderID + "\n" + //
                "Tanggal Pemesanan: " + tanggalOrder + "\n" + //
                "Lokasi Pengiriman: " + lokasi + "\n" + //
                "Biaya Ongkos Kirim: " + "Rp " + ongkos + "\n" + //
                "";
        return bill;
    }
}
