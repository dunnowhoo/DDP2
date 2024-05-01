package assignments.assignment3.systemCLI;

import assignments.assignment1.OrderGenerator;
import assignments.assignment2.Menu;
import assignments.assignment2.Order;
import assignments.assignment2.Restaurant;
import assignments.assignment3.User;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DebitPayment;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//TODO: Extends abstract class yang diberikan
public class CustomerSystemCLI extends UserSystemCLI{

    public CustomerSystemCLI(ArrayList<Restaurant> restoList) {
        super(restoList);
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    boolean handleMenu(int choice){
        switch(choice){
            case 1 -> handleBuatPesanan();
            case 2 -> handleCetakBill();
            case 3 -> handleLihatMenu();
            case 4 -> handleBayarBill();
            case 5 -> handleCekSaldo();
            case 6 -> {
                return false;
            }
            default -> System.out.println("Perintah tidak diketahui, silakan coba kembali");
        }
        return true;
    }

    //TODO: Tambahkan modifier dan buatlah metode ini mengoverride dari Abstract class
    @Override
    void displayMenu() {
        System.out.println("\n--------------------------------------------");
        System.out.println("Pilih menu:");
        System.out.println("1. Buat Pesanan");
        System.out.println("2. Cetak Bill");
        System.out.println("3. Lihat Menu");
        System.out.println("4. Bayar Bill");
        System.out.println("5. Cek Saldo");
        System.out.println("6. Keluar");
        System.out.println("--------------------------------------------");
        System.out.print("Pilihan menu: ");
    }

    void handleBuatPesanan(){
        // TODO: Implementasi method untuk handle ketika customer membuat pesanan
        System.out.println("--------------Buat Pesanan----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print("Tanggal Pemesanan (DD/MM/YYYY): ");
            String tanggalPemesanan = input.nextLine().trim();
            if(!OrderGenerator.validateDate(tanggalPemesanan)){
                System.out.println("Masukkan tanggal sesuai format (DD/MM/YYYY)");
                System.out.println();
                continue;
            }
            System.out.print("Jumlah Pesanan: ");
            int jumlahPesanan = Integer.parseInt(input.nextLine().trim());
            System.out.println("Order: ");
            List<String> listMenuPesananRequest = new ArrayList<>();
            for(int i=0; i < jumlahPesanan; i++){
                listMenuPesananRequest.add(input.nextLine().trim());
            }
            if(! validateRequestPesanan(restaurant, listMenuPesananRequest)){
                System.out.println("Mohon memesan menu yang tersedia di Restoran!");
                continue;
            };
            Order order = new Order(
                    OrderGenerator.generateOrderID(restaurantName, tanggalPemesanan, userLoggedIn.getNomorTelepon()),
                    tanggalPemesanan,
                    OrderGenerator.calculateDeliveryCost(userLoggedIn.getLokasi()),
                    restaurant,
                    getMenuRequest(restaurant, listMenuPesananRequest));
            System.out.printf("Pesanan dengan ID %s diterima!", order.getOrderId());
            userLoggedIn.addOrderHistory(order);
            return;
        }
    }

    void handleCetakBill(){
        // TODO: Implementasi method untuk handle ketika customer ingin cetak bill
        System.out.println("--------------Cetak Bill----------------");
        while (true) {
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            Order order = getOrderOrNull(orderId);
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            return;
        }
    }

    void handleLihatMenu(){
        // TODO: Implementasi method untuk handle ketika customer ingin melihat menu
        System.out.println("--------------Lihat Menu----------------");
        while (true) {
            System.out.print("Nama Restoran: ");
            String restaurantName = input.nextLine().trim();
            Restaurant restaurant = getRestaurantByName(restaurantName);
            if(restaurant == null){
                System.out.println("Restoran tidak terdaftar pada sistem.\n");
                continue;
            }
            System.out.print(restaurant.printMenu());
            return;
        }
    }

    void handleBayarBill(){
        // Menampilkan header "Bayar Bill"
        System.out.println("--------------Bayar Bill----------------");
        while (true) {
            // Meminta ID Order dari pengguna
            System.out.print("Masukkan Order ID: ");
            String orderId = input.nextLine().trim();
            // Mencari order berdasarkan ID
            Order order = getOrderOrNull(orderId);
            // Jika order tidak ditemukan, tampilkan pesan error dan ulangi loop
            if(order == null){
                System.out.println("Order ID tidak dapat ditemukan.\n");
                continue;
            }
            // Jika order sudah selesai (sudah dibayar), tampilkan pesan dan keluar dari metode
            if(order.getOrderFinished()){
                System.out.printf("Pesanan dengan ID ini sudah lunas!\n", order.getOrderId());
                return;
            }
            // Tampilkan detail bill pesanan
            System.out.println("");
            System.out.print(outputBillPesanan(order));
            // Tampilkan opsi metode pembayaran
            System.out.println("\nOpsi Pembayaran:");
            System.out.println("1. Credit Card");
            System.out.println("2. Debit");
            // Meminta pilihan metode pembayaran dari pengguna
            System.out.print("Pilihan Metode Pembayaran: ");
            int paymentMethod = Integer.parseInt(input.nextLine().trim());
            boolean paymentSuccess = false;
            long totalHarga = Math.round(order.getTotalHarga());
            // Jika pengguna memilih metode pembayaran Credit Card
            if(paymentMethod == 1){
                // Jika pengguna memiliki metode pembayaran Credit Card
                if (userLoggedIn.getPayment() instanceof CreditCardPayment) {
                    CreditCardPayment creditCardPayment = (CreditCardPayment) userLoggedIn.getPayment();
                    creditCardPayment.setUser(userLoggedIn);
                    // Proses pembayaran
                    paymentSuccess = creditCardPayment.processPayment(totalHarga);
                } else {
                    // Jika pengguna tidak memiliki metode pembayaran Credit Card, tampilkan pesan error
                    System.out.println("Metode pembayaran kartu kredit tidak tersedia untuk pengguna ini.\n");
                }
                // Jika pengguna memilih metode pembayaran Debit
            } else if(paymentMethod == 2){
                // Jika pengguna memiliki metode pembayaran Debit
                if (userLoggedIn.getPayment() instanceof DebitPayment) {
                    DebitPayment debitPayment = (DebitPayment) userLoggedIn.getPayment();
                    debitPayment.setUser(userLoggedIn);
                    // Proses pembayaran
                    paymentSuccess = debitPayment.processPayment(totalHarga);
                } else {
                    // Jika pengguna tidak memiliki metode pembayaran Debit, tampilkan pesan error
                    System.out.println("Metode pembayaran debit tidak tersedia untuk pengguna ini.\n");
                }
            } else {
                // Jika pengguna memilih metode pembayaran yang tidak valid, tampilkan pesan error
                System.out.println("Metode pembayaran tidak valid.\n");
            }
            // Jika pembayaran berhasil
            if(paymentSuccess){
                // Tambahkan total harga ke saldo restoran
                Restaurant restaurant = order.getRestaurant();
                restaurant.addSaldo(totalHarga);
                // Update status pesanan menjadi selesai
                handleUpdateStatusPesanan(orderId);
            } else {
                // Jika pembayaran gagal, tampilkan pesan error
                System.out.println("Pembayaran gagal.\n");
            }
            // Keluar dari metode
            return;
        }
    }

    void handleUpdateStatusPesanan(String orderId){
        // Mencari order berdasarkan ID
        Order order = getOrderOrNull(orderId);
        // Jika order ditemukan, set status pesanan menjadi selesai
        assert order != null;
        order.setOrderFinished(true);
    }

    void handleCekSaldo(){
        // TODO: Implementasi method untuk handle ketika customer ingin mengecek saldo yang dimiliki
        System.out.printf("Saldo Anda saat ini: Rp %d\n", userLoggedIn.getSaldo());
    }

    public static Restaurant getRestaurantByName(String name){
        Optional<Restaurant> restaurantMatched = restoList.stream().filter(restoran -> restoran.getNama().toLowerCase().equals(name.toLowerCase())).findFirst();
        if(restaurantMatched.isPresent()){
            return restaurantMatched.get();
        }
        return null;
    }

    public static Order getOrderOrNull(String orderId) {
        for (Order order : userLoggedIn.getOrderHistory()) {
            if (order.getOrderId().equals(orderId)) {
                return order;
            }
        }
        return null;
    }

    public static boolean validateRequestPesanan(Restaurant restaurant, List<String> listMenuPesananRequest){
        return listMenuPesananRequest.stream().allMatch(pesanan ->
                restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().equals(pesanan))
        );
    }

    public static Menu[] getMenuRequest(Restaurant restaurant, List<String> listMenuPesananRequest){
        Menu[] menu = new Menu[listMenuPesananRequest.size()];
        for(int i=0;i<menu.length;i++){
            for(Menu existMenu : restaurant.getMenu()){
                if(existMenu.getNamaMakanan().equals(listMenuPesananRequest.get(i))){
                    menu[i] = existMenu;
                }
            }
        }
        return menu;
    }

    public static String outputBillPesanan(Order order) {
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        return String.format("Bill:%n" +
                        "Order ID: %s%n" +
                        "Tanggal Pemesanan: %s%n" +
                        "Lokasi Pengiriman: %s%n" +
                        "Status Pengiriman: %s%n"+
                        "Pesanan:%n%s%n"+
                        "Biaya Ongkos Kirim: Rp %s%n"+
                        "Total Biaya: Rp %s%n",
                order.getOrderId(),
                order.getTanggal(),
                userLoggedIn.getLokasi(),
                !order.getOrderFinished()? "Not Finished":"Finished",
                getMenuPesananOutput(order),
                decimalFormat.format(order.getOngkir()),
                decimalFormat.format(order.getTotalHarga())
        );
    }

    public static String getMenuPesananOutput(Order order){
        StringBuilder pesananBuilder = new StringBuilder();
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('\u0000');
        decimalFormat.setDecimalFormatSymbols(symbols);
        for (Menu menu : order.getSortedMenu()) {
            pesananBuilder.append("- ").append(menu.getNamaMakanan()).append(" ").append(decimalFormat.format(menu.getHarga())).append("\n");
        }
        if (pesananBuilder.length() > 0) {
            pesananBuilder.deleteCharAt(pesananBuilder.length() - 1);
        }
        return pesananBuilder.toString();
    }
}