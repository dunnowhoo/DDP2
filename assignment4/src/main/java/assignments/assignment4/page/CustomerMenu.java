package assignments.assignment4.page;

import assignments.assignment3.*;
import assignments.assignment3.Menu;
import assignments.assignment3.payment.CreditCardPayment;
import assignments.assignment3.payment.DepeFoodPaymentSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter;
import assignments.assignment4.components.BillPrinter.MenuItem;
import javafx.util.StringConverter;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CustomerMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private Scene addOrderScene;
    private Scene printBillScene;
    private Scene payBillScene;
    private Scene cekSaldoScene;
    private BillPrinter billPrinter; // Instance of BillPrinter
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private static Label label = new Label();
    private MainApp mainApp;
    private List<Restaurant> restoList = new ArrayList<>();
    private User user;
    private TableView<MenuItem> tableView = new TableView<>();
    private DatePicker datePicker = new DatePicker();


    public CustomerMenu(Stage stage, MainApp mainApp, User user, List<Restaurant> restoList) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.scene = createBaseMenu();
        this.restoList = restoList;
        this.addOrderScene = createTambahPesananForm();
        this.billPrinter = new BillPrinter(stage, mainApp, this.user); // Pass user to BillPrinter constructor
        this.printBillScene = createBillPrinter();
        this.payBillScene = createBayarBillForm();
        this.cekSaldoScene = createCekSaldoScene();
    }

    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Customer
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        Label welcomeLabel = new Label("Welcome, " + user.getNama() + "!");
        welcomeLabel.setFont(new Font("System Bold", 42));
        AnchorPane.setTopAnchor(welcomeLabel, 42.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 40.0);

        Button makeOrderButton = new Button("Buat Pesanan");
        makeOrderButton.setFont(new Font(15));
        makeOrderButton.setLayoutX(40);
        makeOrderButton.setLayoutY(120);
        makeOrderButton.setPrefSize(185, 40);

        Button printBillButton = new Button("Cetak Bill");
        printBillButton.setFont(new Font(15));
        printBillButton.setLayoutX(40);
        printBillButton.setLayoutY(172);
        printBillButton.setPrefSize(185, 40);

        Button payBillButton = new Button("Bayar Bill");
        payBillButton.setFont(new Font(15));
        payBillButton.setLayoutX(40);
        payBillButton.setLayoutY(224);
        payBillButton.setPrefSize(185, 40);

        Button checkSaldoButton = new Button("Cek Saldo");
        checkSaldoButton.setFont(new Font(15));
        checkSaldoButton.setLayoutX(40);
        checkSaldoButton.setLayoutY(276);
        checkSaldoButton.setPrefSize(185, 40);

        Button logoutButton = new Button("Log Out");
        logoutButton.setFont(new Font("System Bold", 13));
        logoutButton.setLayoutX(442);
        logoutButton.setLayoutY(337);
        logoutButton.setPrefSize(111, 34);

        anchorPane.getChildren().addAll(welcomeLabel, makeOrderButton, printBillButton, payBillButton, checkSaldoButton, logoutButton);

        // Set action untuk buttons
        makeOrderButton.setOnAction(e -> stage.setScene(createTambahPesananForm()));
        printBillButton.setOnAction(e -> stage.setScene(createBillPrinter()));
        payBillButton.setOnAction(e -> stage.setScene(createBayarBillForm()));
        checkSaldoButton.setOnAction(e -> stage.setScene(createCekSaldoScene()));
        logoutButton.setOnAction(e -> mainApp.logout());

        scene = new Scene(anchorPane);
        return scene;
    }

    private Scene createTambahPesananForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah pesanan
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 789);

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setLayoutX(24.0);
        kembaliButton.setLayoutY(24.0);
        kembaliButton.setPrefSize(71, 13);

        Label restaurantLabel = new Label("Restaurant");
        restaurantLabel.setLayoutX(251.0);
        restaurantLabel.setLayoutY(60.0);
        restaurantLabel.setFont(new Font("System Bold", 23.0));

        restaurantComboBox = new ComboBox<>();
        restaurantComboBox.setLayoutX(151.0);
        restaurantComboBox.setLayoutY(88.0);
        restaurantComboBox.setPrefSize(299, 35.0);

        // Membersihkan ComboBox
        restaurantComboBox.getItems().clear();

        // Menambahkan semua nama restoran ke ComboBox
        Set<String> restaurantNames = restoList.stream()
                .map(Restaurant::getNama)
                .collect(Collectors.toSet());
        restaurantComboBox.getItems().addAll(restaurantNames);

        Label dateLabel = new Label("Date (DD/MM/YYYY)");
        dateLabel.setLayoutX(198.0);
        dateLabel.setLayoutY(130.0);
        dateLabel.setFont(new Font("System Bold", 23.0));

        datePicker = new DatePicker();
        datePicker.setLayoutX(154.0);
        datePicker.setLayoutY(164.0);
        datePicker.setPrefSize(299, 35.0);

        // Mengubah format tanggal
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        datePicker.setConverter(new StringConverter<LocalDate>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }
            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        });

        Button menuButton = new Button("Menu");
        menuButton.setLayoutX(252.0);
        menuButton.setLayoutY(213.0);
        menuButton.setPrefSize(95, 31.0);
        menuButton.setFont(new Font(16.0));

        tableView = new TableView<>();
        tableView.setLayoutX(39.0);
        tableView.setLayoutY(258.0);
        tableView.setPrefSize(528, 413.0);

        // membuat table columns
        TableColumn<MenuItem, String> menuColumn = new TableColumn<>("Menu");
        menuColumn.setPrefWidth(221);

        TableColumn<MenuItem, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setPrefWidth(227);

       tableView.getColumns().addAll(menuColumn, priceColumn);
       tableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        // Set cell value factories
        menuColumn.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        priceColumn.setCellFactory(column -> {
            return new TableCell<MenuItem, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);

                    if (item == null || empty) {
                        setText(null);
                        setStyle("");
                    } else {
                        // Format price
                        setText("Rp" + item);
                    }
                }
            };
        });

        Button buatPesananButton = new Button("Buat Pesanan");
        buatPesananButton.setLayoutX(225.0);
        buatPesananButton.setLayoutY(701.0);
        buatPesananButton.setPrefSize(151, 43.0);
        buatPesananButton.setFont(new Font(16.0));

        root.getChildren().addAll(kembaliButton, restaurantLabel, restaurantComboBox, dateLabel, datePicker, menuButton,
                tableView, buatPesananButton);

        kembaliButton.setOnAction(e -> stage.setScene(scene));
        // Set action untuk search button
        menuButton.setOnAction(e -> {
            String restaurantName = restaurantComboBox.getValue();
            if (!restaurantName.isEmpty()) {
                Restaurant restaurant = restoList.stream()
                        .filter(resto -> resto.getNama().equalsIgnoreCase(restaurantName))
                        .findFirst()
                        .orElse(null);

                if (restaurant != null) {
                    List<MenuItem> menuItems = restaurant.getMenu().stream()
                            .map(menu -> new MenuItem(menu.getNamaMakanan(), String.valueOf(menu.getHarga())))
                            .collect(Collectors.toList());
                    tableView.setItems(FXCollections.observableArrayList(menuItems));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Restoran tidak ditemukan.");
                    alert.showAndWait();
                    tableView.setItems(FXCollections.observableArrayList()); // Clear the table
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nama restoran tidak boleh kosong.");
                alert.showAndWait();
            }
        });

        buatPesananButton.setOnAction(e -> {
            String namaRestoran = restaurantComboBox.getValue();
            String tanggalPemesanan = datePicker.getValue().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            List<String> listMenuPesananRequest = tableView.getSelectionModel().getSelectedItems().stream()
                    .map(MenuItem::getItemName)
                    .collect(Collectors.toList());

            handleBuatPesanan(namaRestoran, tanggalPemesanan, listMenuPesananRequest);
        });

        return new Scene(root);
    }

    private Scene createBillPrinter(){
        // Root pane
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);

        // Button kembali
        Button kembaliButton = new Button("Kembali");
        kembaliButton.setLayoutX(14.0);
        kembaliButton.setLayoutY(14.0);
        kembaliButton.setPrefHeight(26.0);
        kembaliButton.setPrefWidth(67.0);
        kembaliButton.setOnAction(e -> stage.setScene(scene)); // Mengembalikan ke scene sebelumnya

        // TextField untuk memasukkan Order ID
        TextField orderIDField = new TextField();
        orderIDField.setLayoutX(96.0);
        orderIDField.setLayoutY(187.0);
        orderIDField.setPrefHeight(26.0);
        orderIDField.setPrefWidth(409.0);

        // Label Masukkan Order ID
        Label orderIDLabel = new Label("Masukkan Order ID");
        orderIDLabel.setLayoutX(202.0);
        orderIDLabel.setLayoutY(146.0);
        orderIDLabel.setPrefHeight(29.0);
        orderIDLabel.setPrefWidth(196.0);
        orderIDLabel.setFont(new Font("System Bold", 24.0));

        // Button Print Bill
        Button printBillButton = new Button("Print Bill");
        printBillButton.setLayoutX(247.0);
        printBillButton.setLayoutY(244.0);
        printBillButton.setPrefHeight(40.0);
        printBillButton.setPrefWidth(107.0);
        printBillButton.setOnAction(e ->   billPrinter.printBill(orderIDField.getText()));

        // Menambahkan semua komponen ke root
        root.getChildren().addAll(kembaliButton, orderIDField, orderIDLabel, printBillButton);

        // Mengembalikan scene baru
        return new Scene(root);
    }

    private Scene createBayarBillForm() {
        // TODO: Implementasikan method ini untuk menampilkan page bayar bill
        // Create layout
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        // Create Kembali button
        Button kembaliButton = new Button("Kembali");
        kembaliButton.setLayoutX(14);
        kembaliButton.setLayoutY(14);
        kembaliButton.setPrefSize(71, 26);
        kembaliButton.setOnAction(e -> stage.setScene(scene));

        // Create label for Order ID
        Label orderIDLabel = new Label("Masukkan Order ID");
        orderIDLabel.setLayoutX(188);
        orderIDLabel.setLayoutY(148);
        orderIDLabel.setFont(new Font("System Bold", 28));

        // Create text field for Order ID
        TextField orderIDField = new TextField();
        orderIDField.setLayoutX(96);
        orderIDField.setLayoutY(191);
        orderIDField.setPrefSize(409, 26);

        // Create ComboBox for payment options
        ComboBox<String> paymentOptions = new ComboBox<>();
        paymentOptions.setLayoutX(215);
        paymentOptions.setLayoutY(231);
        paymentOptions.setPrefSize(170, 26);
        paymentOptions.setPromptText("Pilih Opsi Pembayaran");
        paymentOptions.getItems().addAll("Credit Card", "Debit");

        // Create Bayar button
        Button bayarButton = new Button("Bayar");
        bayarButton.setLayoutX(250);
        bayarButton.setLayoutY(299);
        bayarButton.setPrefSize(101, 40);
        bayarButton.setOnAction(e -> handleBayarBill(orderIDField.getText(), paymentOptions.getValue()));

        // Add all elements to layout
        anchorPane.getChildren().addAll(kembaliButton, orderIDLabel, orderIDField, paymentOptions, bayarButton);

        // Create and return scene
        return new Scene(anchorPane);
    }


    private Scene createCekSaldoScene() {
        // TODO: Implementasikan method ini untuk menampilkan page cetak saldo
        // Create layout
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        // Create Kembali button
        Button kembaliButton = new Button("Kembali");
        kembaliButton.setLayoutX(14);
        kembaliButton.setLayoutY(14);
        kembaliButton.setPrefSize(71, 26);
        kembaliButton.setOnAction(e -> stage.setScene(scene));

        // Create label for greeting the user
        Label greetingLabel = new Label("Halo, " + user.getNama() + "!");
        greetingLabel.setLayoutX(61);
        greetingLabel.setLayoutY(104);
        greetingLabel.setFont(new Font("System Bold", 41));

        // Create label for saldo
        DecimalFormat decimalFormat = new DecimalFormat();
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(symbols);
        Label saldoLabel = new Label("Rp " + decimalFormat.format(user.getSaldo()));
        saldoLabel.setLayoutX(61);
        saldoLabel.setLayoutY(200);
        saldoLabel.setPrefSize(441, 102);
        saldoLabel.setFont(new Font(63));

        // Create label for app name
        Label appNameLabel = new Label("DepeWallet");
        appNameLabel.setLayoutX(484);
        appNameLabel.setLayoutY(27);
        appNameLabel.setFont(new Font("System Bold", 19));

        // Create label for saldo text
        Label saldoTextLabel = new Label("Sisa Saldo Anda Sebesar : ");
        saldoTextLabel.setLayoutX(61);
        saldoTextLabel.setLayoutY(175);
        saldoTextLabel.setPrefSize(463, 51);
        saldoTextLabel.setFont(new Font(21));

        // Add all elements to layout
        anchorPane.getChildren().addAll(kembaliButton, greetingLabel, saldoLabel, appNameLabel, saldoTextLabel);

        // Create and return scene
        return new Scene(anchorPane);
    }

    private void handleBuatPesanan(String namaRestoran, String tanggalPemesanan, List<String> menuItems) {
        //TODO: Implementasi validasi isian pesanan
        try {
            // Find the restaurant by name
            Restaurant restaurant = restoList.stream()
                    .filter(resto -> resto.getNama().equalsIgnoreCase(namaRestoran))
                    .findFirst()
                    .orElse(null);

            // If the restaurant doesn't exist, show an alert and return
            if (restaurant == null) {
                showAlert("Error", "Restaurant not found", "The restaurant " + namaRestoran + " does not exist.", AlertType.ERROR);
                return;
            }

            // Validate the menu items
            boolean isValidMenuItems = DepeFood.validateRequestPesanan(restaurant, menuItems);
            if (!isValidMenuItems) {
                showAlert("Error", "Invalid menu items", "One or more of the menu items are not available in the restaurant.", AlertType.ERROR);
                return;
            }

            int jumlahPesanan = menuItems.size();

            String orderID = DepeFood.handleBuatPesanan(namaRestoran, tanggalPemesanan, jumlahPesanan, menuItems);

            // Show a success message
            showAlert("Order Confirmation", "Order Received", "Pesanan dengan ID " + orderID + " diterima!", AlertType.INFORMATION);
        } catch (Exception e) {
            // Show an error message if something goes wrong
            showAlert("Error", "An error occurred", e.getMessage(), AlertType.ERROR);
        }
    }

    private void handleBayarBill(String orderID, String paymentOption) {
        //TODO: Implementasi validasi pembayaran
        try {
            Order order = DepeFood.getOrderOrNull(orderID);
            if (order == null) {
                showAlert("Error", "Order ID Not Found", "Order ID tidak dapat ditemukan.", Alert.AlertType.ERROR);
                return;
            }
            if (order.getOrderFinished()) {
                showAlert("Information", "Lunas!","Pesanan dengan ID ini sudah lunas!", Alert.AlertType.INFORMATION);
                return;
            }

            DepeFoodPaymentSystem paymentSystem = user.getPaymentSystem();

            boolean isCreditCard = paymentSystem instanceof CreditCardPayment;

            if ((isCreditCard && paymentOption.equals("Debit")) || (!isCreditCard && paymentOption.equals("Credit Card"))) {
                showAlert("Error", "Change Method!", "User belum memiliki metode pembayaran ini!", Alert.AlertType.ERROR);
                return;
            }

            long amountToPay = 0;

            try {
                amountToPay = paymentSystem.processPayment(user.getSaldo(), (long) order.getTotalHarga());
            } catch (Exception e) {
                showAlert("Error","Error", e.getMessage(), Alert.AlertType.ERROR);
                return;
            }

            long saldoLeft = user.getSaldo() - amountToPay;

            user.setSaldo(saldoLeft);
            DepeFood.handleUpdateStatusPesanan(order);

            DecimalFormat decimalFormat = new DecimalFormat();
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            decimalFormat.setDecimalFormatSymbols(symbols);

            if (paymentOption.equals("Credit Card")) {
                String message1 = "Berhasil Membayar Bill sebesar Rp " + decimalFormat.format(amountToPay/1.02) + " dengan biaya transaksi sebesar Rp " + decimalFormat.format(amountToPay-(amountToPay/1.02));
                showAlert("Information","Success!", message1 , Alert.AlertType.INFORMATION);
            } else if (paymentOption.equals("Debit")) {
                String message2 = "Berhasil Membayar Bill sebesar Rp " + decimalFormat.format(amountToPay);
                showAlert("Information","Success!", message2, Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            showAlert("Error", "An error occurred", e.getMessage(), AlertType.ERROR);
        }
    }
}