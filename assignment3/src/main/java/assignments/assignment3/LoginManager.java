package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    //TODO: Solve the error :) (It's actually easy if you have done the other TODOs)
    // Metode untuk mendapatkan sistem berdasarkan peran pengguna
    // Jika peran pengguna adalah "Customer", maka akan mengembalikan sistem pelanggan
    // Jika bukan, maka akan mengembalikan sistem admin
    public UserSystemCLI getSystem(String role){
        if(role.equals("Customer")){
            return customerSystem;
        }else{
            return adminSystem;
        }
    }
}
