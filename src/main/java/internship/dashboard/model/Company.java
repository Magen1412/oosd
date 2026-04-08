package internship.dashboard.model;

import java.time.LocalDateTime;

public class Company {
    private int companyId;
    private String name;
    private String email;
    private String password;
    private String description;
    private LocalDateTime createdAt;
    private String role;

    public Company(String name, String email, String password, String description, LocalDateTime createdAt, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.description = description;
        this.createdAt = createdAt;
        this.role = role;
    }

    // Getters and setters
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }

    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getDescription() { return description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public String getRole() { return role; }
}