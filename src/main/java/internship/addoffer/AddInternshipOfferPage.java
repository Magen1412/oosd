package internship.addoffer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class AddInternshipOfferPage extends JFrame {

    // Database connection details (adjust to your setup)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/internship_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "blackpowa4****";

    public AddInternshipOfferPage() {
        setTitle("Add Internship Offer - Company Interface");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------------- Form Panel ----------------
        JPanel formPanel = new JPanel(new GridLayout(8, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));

        JTextField companyField = new JTextField();
        JTextField positionField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField durationField = new JTextField();
        JTextField stipendField = new JTextField();
        JTextArea descriptionArea = new JTextArea(4, 20);

        formPanel.add(new JLabel("Company Name:"));
        formPanel.add(companyField);
        formPanel.add(new JLabel("Position Title:"));
        formPanel.add(positionField);
        formPanel.add(new JLabel("Location:"));
        formPanel.add(locationField);
        formPanel.add(new JLabel("Duration (e.g., 3 months):"));
        formPanel.add(durationField);
        formPanel.add(new JLabel("Stipend (optional):"));
        formPanel.add(stipendField);
        formPanel.add(new JLabel("Description:"));
        formPanel.add(new JScrollPane(descriptionArea));

        JButton submitBtn = new JButton("Add Offer");
        submitBtn.setBackground(new Color(46, 204, 113));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        JButton clearBtn = new JButton("Clear");
        clearBtn.setBackground(new Color(231, 76, 60));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFont(new Font("Segoe UI", Font.BOLD, 16));

        formPanel.add(submitBtn);
        formPanel.add(clearBtn);

        // ---------------- Button Actions ----------------
        submitBtn.addActionListener((ActionEvent e) -> {
            String company = companyField.getText().trim();
            String position = positionField.getText().trim();
            String location = locationField.getText().trim();
            String duration = durationField.getText().trim();
            String stipend = stipendField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (company.isEmpty() || position.isEmpty() || location.isEmpty() || duration.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields (Company, Position, Location, Duration).",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO internship (internship_id, company_id, description, requirements, duration) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, company);
                stmt.setString(2, position);
                stmt.setString(3, location);
                stmt.setString(4, duration);
                stmt.setString(5, stipend);
                stmt.setString(6, description);
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Internship offer added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                companyField.setText("");
                positionField.setText("");
                locationField.setText("");
                durationField.setText("");
                stipendField.setText("");
                descriptionArea.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving internship offer: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener((ActionEvent e) -> {
            companyField.setText("");
            positionField.setText("");
            locationField.setText("");
            durationField.setText("");
            stipendField.setText("");
            descriptionArea.setText("");
        });

        // ---------------- Layout ----------------
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddInternshipOfferPage().setVisible(true));
    }
}


