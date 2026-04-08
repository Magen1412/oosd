package internship.dashboard.dao;

import internship.dashboard.DBConnection;
import internship.dashboard.model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    public void addStudent(Student student) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO student (name, email, password, phone, gender, cv_path, created_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPassword());
            stmt.setString(4, student.getPhone());
            stmt.setString(5, student.getGender());
            stmt.setString(6, student.getCvPath());
            stmt.setTimestamp(7, Timestamp.valueOf(student.getCreatedAt()));
            stmt.executeUpdate();
        }
    }

    public List<Student> getAllStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT studentId, name, email, password, phone, gender, cv_path, created_at FROM student";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("phone"),
                        rs.getString("gender"),
                        rs.getString("cv_path"),
                        rs.getTimestamp("created_at").toLocalDateTime()
                );
                student.setStudentId(rs.getInt("studentId"));
                students.add(student);
            }
        }
        return students;
    }

    public void updateStudent(Student student) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE student SET name = ?, email = ?, password = ?, phone = ?, gender = ?, cv_path = ? WHERE studentId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPassword());
            stmt.setString(4, student.getPhone());
            stmt.setString(5, student.getGender());
            stmt.setString(6, student.getCvPath());
            stmt.setInt(7, student.getStudentId());
            stmt.executeUpdate();
        }
    }

    public void deleteStudent(int studentId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM student WHERE studentId = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        }
    }

    public boolean existsByEmail(String email) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT COUNT(*) FROM student WHERE email = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        }
        return false;
    }

    public boolean validateLogin(String email, String password) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM student WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }
}