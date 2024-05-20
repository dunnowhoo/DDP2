package assignments.assignment4.components.form;

import assignments.assignment3.DepeFood;
import assignments.assignment3.User;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import assignments.assignment4.MainApp;
import assignments.assignment4.page.AdminMenu;
import assignments.assignment4.page.CustomerMenu;
import java.util.function.Consumer;

public class LoginForm {
    private Stage stage;
    private MainApp mainApp; // MainApp instance
    private TextField nameInput;
    private TextField phoneInput;

    public LoginForm(Stage stage, MainApp mainApp) { // Pass MainApp instance to constructor
        this.stage = stage;
        this.mainApp = mainApp; // Store MainApp instance
        this.nameInput = new TextField();
        this.phoneInput = new TextField();
    }

    private Scene createLoginForm() {
        //TODO: Implementasi method untuk menampilkan komponen form login
        // Main container
        AnchorPane root = new AnchorPane();
        root.setPrefSize(600, 400);

        Label welcomeLabel = new Label("Welcome To DepeFood");
        welcomeLabel.setFont(new Font("Verdana Bold", 35));
        welcomeLabel.setLayoutX(73);
        welcomeLabel.setLayoutY(91);
        welcomeLabel.setPrefSize(454, 80);

        Label nameLabel = new Label("Name:");
        nameLabel.setFont(new Font(16));
        nameLabel.setLayoutX(73);
        nameLabel.setLayoutY(171);

        Label phoneLabel = new Label("Phone Number:");
        phoneLabel.setFont(new Font(16));
        phoneLabel.setLayoutX(73);
        phoneLabel.setLayoutY(241);

        nameInput.setLayoutX(73);
        nameInput.setLayoutY(200);
        nameInput.setPrefSize(446, 26);

        phoneInput.setLayoutX(73);
        phoneInput.setLayoutY(270);
        phoneInput.setPrefSize(446, 26);

        Button loginButton = new Button("Login");
        loginButton.setFont(new Font("System Bold", 13));
        loginButton.setLayoutX(239);
        loginButton.setLayoutY(328);
        loginButton.setPrefSize(115, 26);
        loginButton.setOnAction(e -> handleLogin());

        root.getChildren().addAll(welcomeLabel, nameLabel, phoneLabel, nameInput, phoneInput, loginButton);

        return new Scene(root, 600, 400);
    }


    private void handleLogin() {
        //TODO: Implementasi validasi isian form login
        String name = nameInput.getText();
        String phone = phoneInput.getText();

        if (name.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Both fields must be filled!");
            alert.showAndWait();
        } else {
            // Handle login logic here
            User userLoggedIn = DepeFood.getUser(name, phone);

            if (userLoggedIn == null) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Pengguna dengan data tersebut tidak ditemukan!");
                alert.showAndWait();
                return;
            }

            DepeFood.setPenggunaLoggedIn(userLoggedIn);

            // Check the role of the logged-in user and display the appropriate menu
            if (userLoggedIn.getRole().equals("Admin")) {
                // Display AdminMenu
                AdminMenu adminMenu = new AdminMenu(stage, mainApp, userLoggedIn, DepeFood.getRestoList());
                Scene adminScene = adminMenu.createBaseMenu();
                mainApp.addScene("AdminMenu", adminScene); // Add the scene to the map
                mainApp.setScene(adminScene);
            } else if (userLoggedIn.getRole().equals("Customer")) {
                // Display CustomerMenu
                CustomerMenu customerMenu = new CustomerMenu(stage, mainApp, userLoggedIn, DepeFood.getRestoList());
                Scene customerScene = customerMenu.createBaseMenu();
                mainApp.addScene("CustomerMenu", customerScene); // Add the scene to the map
                mainApp.setScene(customerScene);
            }
            nameInput.clear();
            phoneInput.clear();
            stage.show(); // Display the new scene
        }
    }


    public Scene getScene(){
        return this.createLoginForm();
    }

}
