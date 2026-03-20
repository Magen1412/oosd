package internship.dashboard.test;

import internship.dashboard.DBConnection;

import java.sql.Connection;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Connected to internship_db!");
        } catch (Exception e) {
            System.out.println("Connection failed!");
            e.printStackTrace();
        }
    }
}
