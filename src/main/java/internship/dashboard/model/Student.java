package internship.dashboard.model;

import java.time.LocalDateTime;

public class Student {
    private int studentId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String gender;
    private String cvPath;
    private LocalDateTime createdAt;

    public Student(String name, String email, String password,
                   String phone, String gender, String cvPath, LocalDateTime createdAt) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.gender = gender;
        this.cvPath = cvPath;
        this.createdAt = createdAt;
    }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getCvPath() { return cvPath; }
    public void setCvPath(String cvPath) { this.cvPath = cvPath; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}