package lk.ijse.model;

public class Admin {
    private String username;
    private String password;

    // Constructor
    public Admin(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter for username
    public String getUsername() {
        return username;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Static method to retrieve an admin by username (for example)
    public static Admin getAdmin(String username) {
        // Here you would implement your logic to retrieve the admin from your data source
        // For demonstration purposes, let's assume a hardcoded list of admins
        Admin[] admins = {
                new Admin("admin1", "password1"),
                new Admin("admin2", "password2"),
                // Add more admins as needed
        };

        // Iterate through the list to find the matching admin
        for (Admin admin : admins) {
            if (admin.getUsername().equals(username)) {
                return admin;
            }
        }

        // Return null if admin with the given username is not found
        return null;
    }
}
