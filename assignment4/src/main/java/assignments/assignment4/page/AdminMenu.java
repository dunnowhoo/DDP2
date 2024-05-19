package assignments.assignment4.page;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Restaurant;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;
import assignments.assignment4.components.BillPrinter.MenuItem;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AdminMenu extends MemberMenu{
    private Stage stage;
    private Scene scene;
    private User user;
    private Scene addRestaurantScene;
    private Scene addMenuScene;
    private Scene viewRestaurantsScene;
    private List<Restaurant> restoList = new ArrayList<>();
    private MainApp mainApp; // Reference to MainApp instance
    private ComboBox<String> restaurantComboBox = new ComboBox<>();
    private ListView<String> menuItemsListView = new ListView<>();

    public AdminMenu(Stage stage, MainApp mainApp, User user, List<Restaurant> restoList) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user; // Store the user
        this.restoList = restoList;
        this.scene = createBaseMenu();
        this.addRestaurantScene = createAddRestaurantForm();
        this.addMenuScene = createAddMenuForm();
        this.viewRestaurantsScene = createViewRestaurantsForm();
    }

    @Override
    public Scene createBaseMenu() {
        // TODO: Implementasikan method ini untuk menampilkan menu untuk Admin
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        Label welcomeLabel = new Label("Welcome, " + user.getNama() + "!");
        welcomeLabel.setFont(new Font("System Bold", 42));
        AnchorPane.setTopAnchor(welcomeLabel, 42.0);
        AnchorPane.setLeftAnchor(welcomeLabel, 40.0);

        Button addRestaurantButton = new Button("Tambah Restoran");
        addRestaurantButton.setFont(new Font(15));
        addRestaurantButton.setLayoutX(40);
        addRestaurantButton.setLayoutY(120);
        addRestaurantButton.setPrefSize(185, 52);

        Button addMenuButton = new Button("Tambah Menu Restoran");
        addMenuButton.setFont(new Font(15));
        addMenuButton.setLayoutX(40);
        addMenuButton.setLayoutY(200);
        addMenuButton.setPrefSize(185, 52);

        Button viewListButton = new Button("Lihat Daftar Restoran");
        viewListButton.setFont(new Font(15));
        viewListButton.setLayoutX(40);
        viewListButton.setLayoutY(276);
        viewListButton.setPrefSize(185, 52);

        Button logoutButton = new Button("Log Out");
        logoutButton.setFont(new Font("System Bold", 13));
        logoutButton.setLayoutX(442);
        logoutButton.setLayoutY(337);
        logoutButton.setPrefSize(111, 34);

        anchorPane.getChildren().addAll(welcomeLabel, addRestaurantButton, addMenuButton, viewListButton, logoutButton);

        // Set action untuk buttons
        addRestaurantButton.setOnAction(e -> stage.setScene(createAddRestaurantForm()));
        addMenuButton.setOnAction(e -> stage.setScene(createAddMenuForm()));
        viewListButton.setOnAction(e -> stage.setScene(createViewRestaurantsForm()));
        logoutButton.setOnAction(e -> handleLogout());

        return new Scene(anchorPane);
    }

    private void handleLogout() {
        mainApp.setScene(mainApp.getScene("Login"));
    }

    private Scene createAddRestaurantForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah restoran
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font("System", 19));
        submitButton.setPrefSize(160, 43);
        submitButton.setLayoutX(221);
        submitButton.setLayoutY(297);

        Button backButton = new Button("Kembali");
        backButton.setPrefSize(71, 13);
        backButton.setLayoutX(14);
        backButton.setLayoutY(14);

        TextField restaurantNameField = new TextField();
        restaurantNameField.setPrefSize(413, 34);
        restaurantNameField.setLayoutX(94);
        restaurantNameField.setLayoutY(203);

        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setFont(new Font("System Bold", 23));
        restaurantNameLabel.setLayoutX(210);
        restaurantNameLabel.setLayoutY(164);

        anchorPane.getChildren().addAll(submitButton, backButton, restaurantNameField, restaurantNameLabel);

        // Set action untuk buttons
        backButton.setOnAction(e -> stage.setScene(createBaseMenu())); // Kembali ke sebelumnya
        submitButton.setOnAction(e -> {
            // Handle form submission
            String restaurantName = restaurantNameField.getText();
            // handling logic
            handleTambahRestoran(restaurantName);

        });

        addRestaurantScene = new Scene(anchorPane);
        return addRestaurantScene;
    }

    private Scene createAddMenuForm() {
        // TODO: Implementasikan method ini untuk menampilkan page tambah menu restoran
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(600, 400);

        // Membuat tombol "Kembali"
        Button backButton = new Button("Kembali");
        backButton.setLayoutX(14);
        backButton.setLayoutY(14);
        backButton.setPrefSize(74, 26);

        // Membuat label dan text field untuk nama restoran
        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setFont(new Font("System Bold", 23));
        restaurantNameLabel.setLayoutX(42);
        restaurantNameLabel.setLayoutY(109);

        TextField restaurantNameField = new TextField();
        restaurantNameField.setLayoutX(42);
        restaurantNameField.setLayoutY(137);
        restaurantNameField.setPrefSize(516, 26);

        // Membuat label dan text field untuk nama menu item
        Label menuItemNameLabel = new Label("Menu Item Name:");
        menuItemNameLabel.setFont(new Font("System Bold", 23));
        menuItemNameLabel.setLayoutX(42);
        menuItemNameLabel.setLayoutY(172);

        TextField menuItemNameField = new TextField();
        menuItemNameField.setLayoutX(42);
        menuItemNameField.setLayoutY(200);
        menuItemNameField.setPrefSize(516, 26);

        // Membuat label dan text field untuk harga
        Label priceLabel = new Label("Price:");
        priceLabel.setFont(new Font("System Bold", 23));
        priceLabel.setLayoutX(42);
        priceLabel.setLayoutY(237);

        TextField priceField = new TextField();
        priceField.setLayoutX(42);
        priceField.setLayoutY(265);
        priceField.setPrefSize(516, 26);

        // Membuat tombol "Submit"
        Button submitButton = new Button("Submit");
        submitButton.setFont(new Font(19));
        submitButton.setLayoutX(241);
        submitButton.setLayoutY(329);
        submitButton.setPrefSize(119, 40);

        // Menambahkan semua elemen ke root layout
        anchorPane.getChildren().addAll(backButton, restaurantNameLabel, restaurantNameField, menuItemNameLabel,
                menuItemNameField, priceLabel, priceField, submitButton);

        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));
        submitButton.setOnAction(e -> {
            // Handle pengiriman form
            String restaurantName = restaurantNameField.getText();
            String menuItemName = menuItemNameField.getText();
            String priceText = priceField.getText();

            Restaurant restaurant = restoList.stream()
                    .filter(resto -> resto.getNama().toLowerCase().equals(restaurantName.toLowerCase()))
                    .findFirst()
                    .orElse(null);
            if (restaurant == null) {
                Alert alert = new Alert(AlertType.WARNING, "Restoran tidak terdaftar pada sistem.");
                alert.showAndWait();
                return;
            }

            try {
                double price = Double.parseDouble(priceText);
                // Panggil metode handleTambahMenuRestoran
                handleTambahMenuRestoran(restaurant, menuItemName, price);
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(AlertType.WARNING, "Invalid price format.");
                alert.showAndWait();
            }
        });

        // Mengembalikan scene yang telah dibuat
        addMenuScene = new Scene(anchorPane);
        return addMenuScene;
    }
    
    
    private Scene createViewRestaurantsForm() {
        // TODO: Implementasikan method ini untuk menampilkan page daftar restoran
        // Membuat AnchorPane layout
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setPrefSize(531, 731);

        // Membuat "Kembali" button
        Button backButton = new Button("Kembali");
        AnchorPane.setTopAnchor(backButton, 14.0);
        AnchorPane.setLeftAnchor(backButton, 14.0);

        // Membuat "Restaurant Name" label
        Label restaurantNameLabel = new Label("Restaurant Name:");
        restaurantNameLabel.setStyle("-fx-font-size: 23px; -fx-font-weight: bold;");
        AnchorPane.setTopAnchor(restaurantNameLabel, 87.0);
        AnchorPane.setLeftAnchor(restaurantNameLabel, 169.0);

        // Membuat text field untuk restaurant name input
        TextField restaurantNameField = new TextField();
        restaurantNameField.setPrefSize(449, 38);
        AnchorPane.setTopAnchor(restaurantNameField, 123.0);
        AnchorPane.setLeftAnchor(restaurantNameField, 31.0);

        // Membuat "Search" button
        Button searchButton = new Button("Search");
        searchButton.setPrefSize(89, 26);
        searchButton.setStyle("-fx-font-size: 16px;");
        AnchorPane.setTopAnchor(searchButton, 177.0);
        AnchorPane.setLeftAnchor(searchButton, 211.0);

        // Membuat table view untuk menu items
        TableView<MenuItem> table = new TableView<>();
        table.setPrefSize(449, 469);
        AnchorPane.setTopAnchor(table, 238.0);
        AnchorPane.setLeftAnchor(table, 31.0);

        // membuat table columns
        TableColumn<MenuItem, String> menuColumn = new TableColumn<>("Menu");
        menuColumn.setPrefWidth(221);

        TableColumn<MenuItem, String> priceColumn = new TableColumn<>("Price");
        priceColumn.setPrefWidth(227);

        table.getColumns().addAll(menuColumn, priceColumn);

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


        // Membuat ScrollBar
        ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);
        scrollBar.setPrefHeight(715);
        scrollBar.setPrefWidth(16);
        AnchorPane.setTopAnchor(scrollBar, 8.0);
        AnchorPane.setRightAnchor(scrollBar, 8.0);

        // Menambahkan components ke layout
        anchorPane.getChildren().addAll(backButton, restaurantNameLabel, restaurantNameField, searchButton, table, scrollBar);

        // Set action untuk search button
        searchButton.setOnAction(e -> {
            String restaurantName = restaurantNameField.getText().trim();
            if (!restaurantName.isEmpty()) {
                Restaurant restaurant = restoList.stream()
                        .filter(resto -> resto.getNama().equalsIgnoreCase(restaurantName))
                        .findFirst()
                        .orElse(null);

                if (restaurant != null) {
                    List<MenuItem> menuItems = restaurant.getMenu().stream()
                            .map(menu -> new MenuItem(menu.getNamaMakanan(), String.valueOf(menu.getHarga())))
                            .collect(Collectors.toList());
                    table.setItems(FXCollections.observableArrayList(menuItems));
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Restoran tidak ditemukan.");
                    alert.showAndWait();
                    table.setItems(FXCollections.observableArrayList()); // Clear the table
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING, "Nama restoran tidak boleh kosong.");
                alert.showAndWait();
            }
        });

        backButton.setOnAction(e -> stage.setScene(createBaseMenu()));

        // return scene
        viewRestaurantsScene = new Scene(anchorPane, 531, 731);
        return viewRestaurantsScene;
    }
    

    private void handleTambahRestoran(String nama) {
        //TODO: Implementasi validasi isian nama Restoran
        // Check if the restaurant name already exists
        boolean isRestaurantExist = restoList.stream().anyMatch(restoran -> restoran.getNama().toLowerCase().equals(nama.toLowerCase()));

        // Check restaurant name length
        boolean isRestaurantNameLengthValid = nama.length() >= 4;

        if (isRestaurantExist) {
            // Alert jika restaurant name sudah exists
            Alert alert = new Alert(AlertType.WARNING, String.format("Restoran dengan nama %s sudah pernah terdaftar. Mohon masukkan nama yang berbeda!", nama));
            alert.showAndWait();
        } else if (!isRestaurantNameLengthValid) {
            // Alert jika restaurant name too short
            Alert alert = new Alert(AlertType.WARNING, "Nama Restoran tidak valid! Minimal 4 karakter diperlukan.");
            alert.showAndWait();
        } else {
            // Jika valid, Membuat restaurant dan add ke list
            Restaurant restaurant = new Restaurant(nama);
            restoList.add(restaurant);
            DepeFood.getRestoList().add(restaurant);
            // Inform successful addition
            Alert alert = new Alert(AlertType.INFORMATION,"Restaurant " + restaurant.getNama() + " Berhasil terdaftar.");
            alert.showAndWait();
        }
    }

    private void handleTambahMenuRestoran(Restaurant restaurant, String itemName, double price) {
        //TODO: Implementasi validasi isian menu Restoran
        // Check if the restaurant exists in the restoList
        String restaurantName = restaurant.getNama();
        boolean isRestaurantExist = restoList.stream().anyMatch(resto -> resto.getNama().toLowerCase().equals(restaurantName.toLowerCase()));
        if (!isRestaurantExist) {
            Alert alert = new Alert(AlertType.WARNING, "Restoran tidak terdaftar pada sistem.");
            alert.showAndWait();
            return;
        }

        // Check apakah the item name valid
        if (itemName == null || itemName.trim().isEmpty()|| itemName.trim().isEmpty()) {
            Alert alert = new Alert(AlertType.WARNING, "Nama item menu tidak boleh kosong.");
            alert.showAndWait();
            return;
        }

        // Check apakah menu item dengan nama sama already exists di restaurant menu
        boolean isMenuItemExist = restaurant.getMenu().stream().anyMatch(menu -> menu.getNamaMakanan().toLowerCase().equals(itemName.toLowerCase()));
        if (isMenuItemExist) {
            Alert alert = new Alert(AlertType.WARNING, "Menu item dengan nama " + itemName + " sudah ada di restoran " + restaurantName + ".");
            alert.showAndWait();
            return;
        }

        // Check apakah price valid
        if (price <= 0) {
            Alert alert = new Alert(AlertType.WARNING, "Harga harus lebih besar dari 0.");
            alert.showAndWait();
            return;
        }

        // add menu item ke restaurant
        restaurant.addMenu(new Menu(itemName, price));

        // Inform successful addition
        Alert alert = new Alert(AlertType.INFORMATION, "Menu item " + itemName + " berhasil ditambahkan ke restoran " + restaurantName + ".");
        alert.showAndWait();
    }
}
