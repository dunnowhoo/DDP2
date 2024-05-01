package assignments.assignment3.systemCLI;

import java.util.Scanner;
import java.util.ArrayList;
import assignments.assignment2.Restaurant;
import assignments.assignment3.User;

public abstract class UserSystemCLI {
    protected static Scanner input;
    protected static ArrayList<Restaurant> restoList;
    protected static User userLoggedIn;

    public UserSystemCLI(ArrayList<Restaurant> restoList) {
        this.restoList = restoList;
        this.input = new Scanner(System.in);
    }

    public void run(String name, String noTelp) {
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            displayMenu();
            int command = input.nextInt();
            input.nextLine();
            isLoggedIn = handleMenu(command);
        }
    }

    public void setUserLoggedIn(User user) {
        this.userLoggedIn = user;
    }
    abstract void displayMenu();
    abstract boolean handleMenu(int command);
}
