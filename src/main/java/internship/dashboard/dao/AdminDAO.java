package internship.dashboard.dao;

import internship.dashboard.DBConnection;
import internship.dashboard.model.Admin;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    // CREATE
    public void addAdmin(Admin admin) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO admin (name, email, password, role) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getRole());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Admin> getAllAdmins() throws Exception {
        List<Admin> admins = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT admin_id, name, email, password, role FROM admin";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Admin admin = new Admin(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role")
                );
                admin.setAdminId(rs.getInt("admin_id"));
                admins.add(admin);
            }
        }
        return admins;
    }

    // UPDATE
    public void updateAdmin(Admin admin) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE admin SET name = ?, email = ?, password = ?, role = ? WHERE admin_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, admin.getName());
            stmt.setString(2, admin.getEmail());
            stmt.setString(3, admin.getPassword());
            stmt.setString(4, admin.getRole());
            stmt.setInt(5, admin.getAdminId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteAdmin(int adminId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM admin WHERE admin_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, adminId);
            stmt.executeUpdate();
        }
    }

    // Validate login
    public boolean validateLogin(String email, String password) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM admin WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}