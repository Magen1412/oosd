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
        setBackground(new Color(236, 240, 241));

        JLabel title = new JLabel("Add Internship Offer", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 24));
        title.setForeground(new Color(44, 62, 80));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        add(title, BorderLayout.NORTH);

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JTextField companyIdField = createStyledField();
        JTextField companyNameField = createStyledField();
        JTextField titleField = createStyledField();
        JTextArea descriptionArea = createStyledTextArea();
        JTextField startDateField = createStyledField();
        JTextField endDateField = createStyledField();

        addFormField(formCard, "Company ID:", companyIdField, gbc, 0);
        addFormField(formCard, "Company Name:", companyNameField, gbc, 2);
        addFormField(formCard, "Title:", titleField, gbc, 4);
        addFormField(formCard, "Description:", new JScrollPane(descriptionArea), gbc, 6);
        addFormField(formCard, "Start Date (yyyy-MM-dd):", startDateField, gbc, 8);
        addFormField(formCard, "End Date (yyyy-MM-dd):", endDateField, gbc, 10);

        add(formCard, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 15));
        buttonPanel.setOpaque(false);

        JButton submitBtn = createStyledButton("Add Offer", new Color(46, 204, 113));
        JButton clearBtn = createStyledButton("Clear", new Color(231, 76, 60));
        JButton backBtn = createStyledButton("← Back", new Color(52, 152, 219));

        buttonPanel.add(submitBtn);
        buttonPanel.add(clearBtn);
        buttonPanel.add(backBtn);

        add(buttonPanel, BorderLayout.SOUTH);

        submitBtn.addActionListener((ActionEvent e) -> {
            String companyIdStr = companyIdField.getText().trim();
            String companyName = companyNameField.getText().trim();
            String titleTxt = titleField.getText().trim();
            String description = descriptionArea.getText().trim();
            String startDateStr = startDateField.getText().trim();
            String endDateStr = endDateField.getText().trim();

            if (companyIdStr.isEmpty() || companyName.isEmpty() || titleTxt.isEmpty()
                    || startDateStr.isEmpty() || endDateStr.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Please fill in all required fields (Company ID, Company Name, Title, Start/End Date).",
                        "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
                String sql = "INSERT INTO internship (company_id, company_name, title, description, start_date, end_date) VALUES (?, ?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, Integer.parseInt(companyIdStr));
                stmt.setString(2, companyName);
                stmt.setString(3, titleTxt);
                stmt.setString(4, description);
                stmt.setDate(5, java.sql.Date.valueOf(startDateStr));
                stmt.setDate(6, java.sql.Date.valueOf(endDateStr));
                stmt.executeUpdate();

                JOptionPane.showMessageDialog(this,
                        "Internship offer added successfully!",
                        "Success", JOptionPane.INFORMATION_MESSAGE);

                companyIdField.setText("");
                companyNameField.setText("");
                titleField.setText("");
                descriptionArea.setText("");
                startDateField.setText("");
                endDateField.setText("");

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Error saving internship offer: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        clearBtn.addActionListener((ActionEvent e) -> {
            companyIdField.setText("");
            companyNameField.setText("");
            titleField.setText("");
            descriptionArea.setText("");
            startDateField.setText("");
            endDateField.setText("");
        });

        backBtn.addActionListener(e -> cardLayout.show(mainContent, "companyDashboard"));
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(new Color(44, 62, 80));
        gbc.gridy = y;
        gbc.gridx = 0;
        panel.add(label, gbc);
        gbc.gridy = y + 1;
        gbc.gridx = 0;
        panel.add(field, gbc);
    }

    private JTextField createStyledField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(300, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JTextArea createStyledTextArea() {
        JTextArea area = new JTextArea(4, 20);
        area.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)));
        return area;
    }

    private JButton createStyledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(140, 35));
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(bg.darker(), 1),
                BorderFactory.createEmptyBorder(5, 15, 5, 15)
        ));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            mainContent.add(new Companydashboard(mainContent, cardLayout), "companyDashboard");
            mainContent.add(new AddInternshipOfferPage(cardLayout, mainContent), "AddOffer");

            cardLayout.show(mainContent, "AddOffer");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}