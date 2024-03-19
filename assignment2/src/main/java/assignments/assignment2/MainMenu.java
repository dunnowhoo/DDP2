package assignments.assignment2;

import java.util.ArrayList;
import java.util.Scanner;
import assignments.assignment1.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static ArrayList<Restaurant> restoList;
    private static ArrayList<User> userList;
    private static ArrayList<String> orderIDs;
    private static final String code39CharacterSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static void main(String[] args) {
        boolean programRunning = true;
        initUser();
        restoList = new ArrayList<Restaurant>();
        orderIDs = new ArrayList<>();
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
                    System.out.println("Selamat Datang " + nama);
                    while (isLoggedIn){
                        menuCustomer();
                        int commandCust = input.nextInt();
                        input.nextLine();

                        switch(commandCust){
                            case 1 -> handleBuatPesanan(userLoggedIn);
                            case 2 -> handleCetakBill(userLoggedIn);
                            case 3 -> handleLihatMenu();
                            case 4 -> handleUpdateStatusPesanan(userLoggedIn);
                            case 5 -> isLoggedIn = false;
                            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
                        }
                    }
                }else{
                    System.out.println("Selamat Datang Admin!");
                    while (isLoggedIn){
                        menuAdmin();
                        int commandAdmin = input.nextInt();
                        input.nextLine();

                        switch(commandAdmin){
                            case 1 -> handleTambahRestoran();
                            case 2 -> handleHapusRestoran();
                            case 3 -> isLoggedIn = false;
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
        Restaurant restaurant = null;
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
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
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

            orderID = OrderGenerator.generateOrderID(namaRestoran, tanggalOrder, noTelepon);
            orderIDs.add(orderID);

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
            boolean available;

            while (true) {
                available = true;
                for (int i = 0; i < jumlahPesanan; i++) {
                    namaMenu[i] = input.nextLine().trim();
                }

                int index = 0;
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                        for (Menu m : resto.getMenu()) {
                            if (m.getNamaMakanan().equals(namaMenu[index])) {
                                menu[index] = m;
                                index++;
                                break; // Keluar dari loop setelah menemukan menu yang cocok
                            }
                        }
                        break; // Keluar dari loop setelah menemukan restoran yang cocok
                    }
                }

                // Periksa apakah semua menu tersedia
                if (index < jumlahPesanan - 1) {
                    System.out.println("Mohon memesan menu yang tersedia pada pesanan.");
                    available = false;
                }

                if (available) {
                    userOrder = new Order(orderID, tanggalOrder, ongkos, restaurant, menu);
                    user.addOrder(userOrder);
                    break;
                }
            }
            break;

        }
        // Generate Order ID
        System.out.println("Pesanan dengan ID " + orderID + " diterima!" + "\n");
    }

    public static void handleCetakBill(User user){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("------------Cetak Bill------------");
        String orderID;

        while (true) {
            System.out.print("Masukkan Order ID: ");
            orderID = input.nextLine().trim(); // Membaca perintah dari pengguna

            if (orderIDs.contains(orderID)) {
                ArrayList<Order> order = user.getOrderHistory();
                for (Order tempOrder : order){
                    if (tempOrder.getOrderID().equals(orderID)) {
                       String output = tempOrder.toString();
                       System.out.println(output);
                    }
                }
                break;
            }
            else {
                System.out.println("Order ID tidak dapat ditemukan.");
            }
        }

    }

    public static void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("------------Lihat Menu------------");
        String namaRestoran;
        while (true){
            System.out.print("Nama: ");
            namaRestoran= input.nextLine().trim();

            if (restoList != null && !restoList.isEmpty()){
                for (Restaurant resto : restoList) {
                    if (resto.getNama().equalsIgnoreCase(namaRestoran)) {
                        String output = resto.toString();
                        System.out.println(output);
                        break;
                    }
                }
                break;
            }
            System.out.println("Restoran tidak terdaftar pada sistem!");
        }
    }

    public static void handleUpdateStatusPesanan(User user){
        // TODO: Implementasi method untuk handle ketika customer ingin update status pesanan
        System.out.println("------------Update Status Pesanan------------");
        String orderID;
        String status;
        while (true) {
            System.out.print("Order ID: ");
            orderID = input.nextLine().trim();

            if (orderIDs.contains(orderID)) {
                status = input.nextLine().trim();

                for (Order ord : user.getOrderHistory()){
                    if (ord.getOrderID().equals(orderID)){
                        if (ord.isOrderFinished()){
                            System.out.println("Status pesanan dengan ID " + orderID + " tidak berhasil diupdate!");
                            break;
                        }
                        else {
                            ord.finishOrder();
                            System.out.println("Status pesanan dengan ID " + orderID + " berhasil diupdate!");
                            break;
                        }
                    }
                }
                break;
            }
            else {
                System.out.println("Order ID tidak dapat ditemukan.");
            }
        }
    }

    public static void handleTambahRestoran(){
        // TODO: Implementasi method untuk handle ketika admin ingin tambah restoran
        String namaResto;
        int jumlahMakanan;
        boolean found;
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
                    if (resto.getNama().equalsIgnoreCase(namaResto)){
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
                assert restoList != null;
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
                if (restoList.get(i).getNama().equalsIgnoreCase(namaResto)) {
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
}
