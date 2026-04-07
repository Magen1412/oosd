package internship.registration;

import internship.login.LoginPage;
import javax.swing.*;
import java.awt.*;

/**
 * RegistrationPage for the Internship Management System.
 */
public class RegistrationPage extends JFrame {

    // Theme Colors
    private final Color PRIMARY_BLUE = new Color(0, 0, 255); 
    private final Color BACKGROUND_LIGHT = new Color(236, 240, 241);
    private final Color TEXT_DARK = new Color(44, 62, 80);

    public RegistrationPage() {
        setTitle("Internship Management System - Registration");
        setSize(650, 900); // Adjusted height
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Form Panel (Main Container) ---
        JPanel mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBackground(BACKGROUND_LIGHT);
        
        // --- The "Form Card" ---
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(40, 60, 40, 60)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        // ===== LOGO SECTION =====
        ImageIcon logoIcon = new ImageIcon("src/main/resources/logo/img.png");
        Image img = logoIcon.getImage();
        Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setHorizontalAlignment(JLabel.CENTER);
        
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0); // Spacing between logo and title
        formCard.add(logoLabel, gbc);

        // ===== REGISTER TITLE (Moved from top bar to here) =====
        JLabel registerTitle = new JLabel("REGISTER", SwingConstants.CENTER);
        registerTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        registerTitle.setForeground(TEXT_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 25, 0); // Spacing below the title
        formCard.add(registerTitle, gbc);

        // Reset insets for the form fields
        gbc.insets = new Insets(8, 0, 8, 0);

        JTextField nameField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JPasswordField passField = createStyledPasswordField();
        JPasswordField confirmPassField = createStyledPasswordField();

        // Adding components to formCard (shifted gridy to account for logo and title)
        addFormField(formCard, "Full Name:", nameField, gbc, 2);
        addFormField(formCard, "Email Address:", emailField, gbc, 4);
        addFormField(formCard, "Password:", passField, gbc, 6);
        addFormField(formCard, "Confirm Password:", confirmPassField, gbc, 8);

        // --- Register Button ---
        JButton regButton = new JButton("CREATE ACCOUNT");
        regButton.setBackground(PRIMARY_BLUE);
        regButton.setForeground(Color.WHITE);
        regButton.setFocusPainted(false);
        regButton.setOpaque(true);
        regButton.setBorderPainted(false);
        regButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        regButton.setPreferredSize(new Dimension(450, 50));
        regButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 10;
        gbc.insets = new Insets(25, 0, 10, 0);
        formCard.add(regButton, gbc);

        // --- Login Section ---
        JLabel loginLabel = new JLabel("Already have an account?", SwingConstants.CENTER);
        loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        loginLabel.setForeground(TEXT_DARK);
        gbc.gridy = 11;
        gbc.insets = new Insets(15, 0, 5, 0);
        formCard.add(loginLabel, gbc);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(PRIMARY_BLUE);
        loginButton.setFocusPainted(false);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginButton.setPreferredSize(new Dimension(450, 45));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 1));
        
        gbc.gridy = 12;
        gbc.insets = new Insets(0, 0, 0, 0);
        formCard.add(loginButton, gbc);

        mainContainer.add(formCard);

        // --- ACTION LISTENERS ---
        regButton.addActionListener(e -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String pass = new String(passField.getPassword());
            String confirm = new String(confirmPassField.getPassword());
            
            if (name.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(this, "Registration Successful for " + name);
        });

        loginButton.addActionListener(e -> {
            this.dispose(); 
            LoginPage.main(null);
        });

        add(mainContainer, BorderLayout.CENTER);
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_DARK);
        gbc.gridy = y;
        panel.add(label, gbc);
        gbc.gridy = y + 1;
        panel.add(field, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(450, 40));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(450, 40));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
        return field;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) { e.printStackTrace(); }
        
        SwingUtilities.invokeLater(() -> {
            new RegistrationPage().setVisible(true);
        });
    }
}