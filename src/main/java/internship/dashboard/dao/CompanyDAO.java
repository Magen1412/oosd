package internship.dashboard.dao;

import internship.dashboard.DBConnection;
import internship.dashboard.model.Company;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAO {

    // CREATE
    public void addCompany(Company company) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO company (name, email, password, description, created_at, role) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPassword());
            stmt.setString(4, company.getDescription());
            stmt.setTimestamp(5, Timestamp.valueOf(company.getCreatedAt()));
            stmt.setString(6, company.getRole());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Company> getAllCompanies() throws Exception {
        List<Company> companies = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT company_id, name, email, password, description, created_at, role FROM company";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Company company = new Company(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("description"),
                        rs.getTimestamp("created_at").toLocalDateTime(),
                        rs.getString("role")
                );
                company.setCompanyId(rs.getInt("company_id"));
                companies.add(company);
            }
        }
        return companies;
    }

    // UPDATE
    public void updateCompany(Company company) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE company SET name = ?, email = ?, password = ?, description = ?, role = ? WHERE company_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, company.getName());
            stmt.setString(2, company.getEmail());
            stmt.setString(3, company.getPassword());
            stmt.setString(4, company.getDescription());
            stmt.setString(5, company.getRole());
            stmt.setInt(6, company.getCompanyId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteCompany(int companyId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM company WHERE company_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, companyId);
            stmt.executeUpdate();
        }
    }

    // Validate login
    public boolean validateLogin(String email, String password) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM company WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}