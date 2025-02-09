package com.esprit.tests;

import com.esprit.models.User;
import com.esprit.services.UserService2;

import java.util.Date;

public class MainProg {
    public static void main(String[] args) {
        UserService2 userService = new UserService2();

        User newUser = new User("test@example.com", "securePass123", "Doe", "John",
                new Date( 2025,9,23), "ROLE_USER", "Male", "123 Street, City", "123456789", "Active");
        userService.ajouter(newUser);


        System.out.println(userService.rechercher());
    }
}
