package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;

import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        // реализуйте алгоритм здесь
        Util.getConnection();
        UserService userService = new UserServiceImpl();

        userService.createUsersTable();

        userService.saveUser("Victor", "Tehov", (byte) 20);
        userService.saveUser("Igor", "Tyastov", (byte) 25);
        userService.saveUser("Alexander", "Melnikov", (byte) 31);
        userService.saveUser("Anton", "Bessonov", (byte) 38);

        userService.removeUserById(1);
        System.out.print(userService.getAllUsers());
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
