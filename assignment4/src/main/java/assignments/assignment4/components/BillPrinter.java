package assignments.assignment4.components;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import assignments.assignment3.DepeFood;
import assignments.assignment3.Menu;
import assignments.assignment3.Order;
import assignments.assignment3.User;
import assignments.assignment4.MainApp;

public class BillPrinter {
    private Stage stage;
    private MainApp mainApp;
    private User user;
    private TableView<MenuItem> table;
    private Label totalLabel;
    private Label orderIdLabel;
    private Label tanggalLabel;
    private Label restaurantLabel;
    private Label lokasiLabel;
    private Label statusLabel;
    private Label biayaoOngkosKirimLabel;

    public BillPrinter(Stage stage, MainApp mainApp, User user) {
        this.stage = stage;
        this.mainApp = mainApp;
        this.user = user;
        this.table = new TableView<>();
        this.totalLabel = new Label();
        this.orderIdLabel = new Label();
        this.tanggalLabel = new Label();
        this.restaurantLabel = new Label();
        this.lokasiLabel = new Label();
        this.statusLabel = new Label();
        this.biayaoOngkosKirimLabel = new Label();

    }

    private Scene createBillPrinterForm(){
        //TODO: Implementasi untuk menampilkan komponen hasil cetak bill
        AnchorPane root = new AnchorPane();

        Button kembaliButton = new Button("Kembali");
        kembaliButton.setLayoutX(14.0);
        kembaliButton.setLayoutY(14.0);
        kembaliButton.setPrefHeight(26.0);
        kembaliButton.setPrefWidth(67.0);
        kembaliButton.setOnAction(e -> stage.setScene(mainApp.getScene("CustomerMenu")));

        Label titleLabel = new Label("Your Order Bill");
        titleLabel.setFont(new javafx.scene.text.Font("System Bold", 24.0));
        titleLabel.setLayoutX(200.0);
        titleLabel.setLayoutY(40.0);

        Label pesananLabel = new Label("Pesanan:");

        orderIdLabel.setLayoutX(50);
        orderIdLabel.setLayoutY(80);
        tanggalLabel.setLayoutX(50);
        tanggalLabel.setLayoutY(110);
        restaurantLabel.setLayoutX(50);
        restaurantLabel.setLayoutY(140);
        lokasiLabel.setLayoutX(50);
        lokasiLabel.setLayoutY(170);
        statusLabel.setLayoutX(50);
        statusLabel.setLayoutY(200);
        pesananLabel.setLayoutX(50);
        pesananLabel.setLayoutY(230);

        table.setLayoutX(50);
        table.setLayoutY(260);
        table.setPrefSize(500, 200);

        if (table.getColumns().isEmpty()) {
            TableColumn<MenuItem, String> itemColumn = new TableColumn<>("Item");
            itemColumn.setCellValueFactory(cellData -> cellData.getValue().itemNameProperty());
            itemColumn.setPrefWidth(table.getPrefWidth() / 2);

            TableColumn<MenuItem, String> priceColumn = new TableColumn<>("Harga");
            priceColumn.setCellValueFactory(cellData -> cellData.getValue().priceProperty());
            priceColumn.setPrefWidth(table.getPrefWidth() / 2);

            table.getColumns().addAll(itemColumn, priceColumn);
        }

        biayaoOngkosKirimLabel.setLayoutX(50);
        biayaoOngkosKirimLabel.setLayoutY(480);

        totalLabel.setLayoutX(50);
        totalLabel.setLayoutY(510);

        root.getChildren().addAll(kembaliButton, titleLabel, orderIdLabel, tanggalLabel, restaurantLabel, lokasiLabel,
                statusLabel, pesananLabel, table, biayaoOngkosKirimLabel, totalLabel);

        return new Scene(root, 600, 600);
    }

    public void printBill(String orderId) {
        //TODO: Implementasi validasi orderID
        Order order = DepeFood.findUserOrderById(orderId);

        if (order == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Order ID tidak ditemukan.");
            alert.showAndWait();
            return;
        }

        ObservableList<MenuItem> items = FXCollections.observableArrayList();
        for (Menu menu : order.getItems()) {
            items.add(new MenuItem(menu.getNamaMakanan(), "Rp" + menu.getHarga()));
        }
        table.setItems(items);

        totalLabel.setText("Total Biaya: Rp" + order.getTotalHarga());
        orderIdLabel.setText("Order ID: " + order.getOrderId());
        tanggalLabel.setText("Tanggal Pemesanan: " + order.getTanggal());
        restaurantLabel.setText("Restaurant: " + order.getRestaurant().getNama());
        lokasiLabel.setText("Lokasi Pengiriman: " + user.getLokasi());
        statusLabel.setText("Status Pengiriman: " + (!order.getOrderFinished() ? "Not Finished" : "Finished"));
        biayaoOngkosKirimLabel.setText("Biaya Ongkos Kirim: Rp" + order.getOngkir());

        // Call createBillPrinterForm() if orderID exists and set it as the current scene
        Scene billPrinterFormScene = createBillPrinterForm();
        stage.setScene(billPrinterFormScene);
    }

    public Scene getScene() {
        return this.createBillPrinterForm();
    }

    // Class ini opsional
    public static class MenuItem {
        private final StringProperty itemName;
        private final StringProperty price;

        public MenuItem(String itemName, String price) {
            this.itemName = new SimpleStringProperty(itemName);
            this.price = new SimpleStringProperty(price);
        }

        public StringProperty itemNameProperty() {
            return itemName;
        }

        public StringProperty priceProperty() {
            return price;
        }

        public String getItemName() {
            return itemName.get();
        }

        public String getPrice() {
            return price.get();
        }
    }
}
