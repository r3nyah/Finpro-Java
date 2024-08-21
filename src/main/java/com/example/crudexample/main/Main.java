package com.example.crudexample.main;

import com.example.crudexample.dao.UserDAO;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        // Adding 10 accounts
        for (int i = 1; i <= 10; i++) {
            String username = "user" + i;
            String email = "user" + i + "@example.com";
            String password = "password" + i;
            userDAO.addUser(username, email, password);
        }

        // Read all users to verify they were added
        System.out.println("All users after addition:");
        userDAO.getAllUsers();

        // Update 3 accounts
        userDAO.updateUser(1, "updatedUser1", "updatedUser1@example.com", "newPassword1");
        userDAO.updateUser(2, "updatedUser2", "updatedUser2@example.com", "newPassword2");
        userDAO.updateUser(3, "updatedUser3", "updatedUser3@example.com", "newPassword3");

        // Read all users to verify updates
        System.out.println("\nAll users after updates:");
        userDAO.getAllUsers();

        // Delete 2 accounts
        userDAO.deleteUser(4); // Assuming ID 4 exists
        userDAO.deleteUser(5); // Assuming ID 5 exists

        // Read all users to verify deletions
        System.out.println("\nAll users after deletions:");
        userDAO.getAllUsers();
    }
}
