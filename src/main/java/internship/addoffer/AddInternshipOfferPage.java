package internship.addoffer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import internship.companydashboard.Companydashboard;

public class AddInternshipOfferPage extends JPanel {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/internship_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "blackpowa4****";

    private CardLayout cardLayout;
    private JPanel mainContent;

    public AddInternshipOfferPage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(new Color(240, 245, 249));

        // ---------------- Title Bar ----------------
        JLabel title = new JLabel("Add Internship Offer", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(30, 58, 95));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        // ---------------- Form Panel ----------------
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 15, 15));
        formPanel.setBorder(BorderFactory.createEmptyBorder(30, 200, 30, 200));
        formPanel.setOpaque(false);

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

        add(formPanel, BorderLayout.CENTER);

        // ---------------- Buttons Row ----------------
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        JButton submitBtn = new JButton("Add Offer");
        styleButton(submitBtn, new Color(46, 204, 113));

        JButton clearBtn = new JButton("Clear");
        styleButton(clearBtn, new Color(231, 76, 60));

        JButton backBtn = new JButton("← Back");
        styleButton(backBtn, new Color(46, 134, 193));

        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

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
                String sql = "INSERT INTO internship (company_name, position_title, location, duration, stipend, description) VALUES (?, ?, ?, ?, ?, ?)";
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

        backBtn.addActionListener(e -> cardLayout.show(mainContent, "dashboard"));
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(160, 35));
    }

    // ---------------- Main Function ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            // Add dashboard placeholder
            JPanel dashboardPanel = new JPanel();
            dashboardPanel.setBackground(new Color(220, 240, 250));
            dashboardPanel.add(new JLabel("Company Dashboard"));
            mainContent.add(new Companydashboard(), "dashboard");

            // Add AddInternshipOfferPage
            mainContent.add(new AddInternshipOfferPage(cardLayout, mainContent), "addOffer");

            // Show AddOffer page first
            cardLayout.show(mainContent, "addOffer");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}
