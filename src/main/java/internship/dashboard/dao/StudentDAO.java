package internship.dashboard.dao;

import internship.dashboard.DBConnection;
import internship.dashboard.model.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // CREATE
    public void addStudent(Student student) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO Student (name, gender, email, password) VALUES (?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPassword());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Student> getAllStudents() throws Exception {
        List<Student> students = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT student_id, name, gender, email, password FROM Student";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Student student = new Student(
                        rs.getString("name"),
                        rs.getString("gender"),
                        rs.getString("email"),
                        rs.getString("password")
                );
                student.setStudentId(rs.getInt("student_id"));
                students.add(student);
            }
        }
        return students;
    }

    // UPDATE
    public void updateStudent(Student student) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE Student SET name = ?, gender = ?, email = ?, password = ? WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getGender());
            stmt.setString(3, student.getEmail());
            stmt.setString(4, student.getPassword());
            stmt.setInt(5, student.getStudentId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void deleteStudent(int studentId) throws Exception {
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM Student WHERE student_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, studentId);
            stmt.executeUpdate();
        }
    }
}
