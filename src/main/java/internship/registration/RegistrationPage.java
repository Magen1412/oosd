package internship.registration;

import javax.swing.*;
import java.awt.*;

/**
 * RegistrationPage for the Internship Management System.
 */
public class RegistrationPage extends JFrame {

    // Theme Colors from Screenshot
    private final Color PRIMARY_BLUE = new Color(0, 0, 255); 
    private final Color SIDEBAR_GRAY = new Color(127, 140, 141);  // #7F8C8D
    private final Color BACKGROUND_LIGHT = new Color(236, 240, 241); // #ECF0F1
    private final Color TEXT_DARK = new Color(44, 62, 80);        // #2C3E50

    public RegistrationPage() {
        setTitle("Internship Management System - Registration");
        setSize(450, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(SIDEBAR_GRAY);
        headerPanel.setPreferredSize(new Dimension(450, 100));
        
        JLabel headerLabel = new JLabel("REGISTER");
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setForeground(Color.WHITE);
        headerPanel.add(headerLabel);

        // --- Form Panel (Main Container) ---
        JPanel mainContainer = new JPanel(new GridBagLayout());
        mainContainer.setBackground(BACKGROUND_LIGHT);
        
        // --- The "Form Card" ---
        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 0, 8, 0);
        gbc.gridx = 0;

        // Fields
        JTextField nameField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JPasswordField passField = createStyledPasswordField();
        JPasswordField confirmPassField = createStyledPasswordField();

        // Adding components to formCard
        addFormField(formCard, "Full Name:", nameField, gbc, 0);
        addFormField(formCard, "Email Address:", emailField, gbc, 2);
        addFormField(formCard, "Password:", passField, gbc, 4);
        addFormField(formCard, "Confirm Password:", confirmPassField, gbc, 6);

        // --- Register Button ---
        JButton regButton = new JButton("CREATE ACCOUNT");
        regButton.setBackground(PRIMARY_BLUE);
        regButton.setForeground(Color.WHITE);
        regButton.setFocusPainted(false);
        regButton.setOpaque(true);
        regButton.setBorderPainted(false);
        regButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        regButton.setPreferredSize(new Dimension(150, 45));
        regButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        gbc.gridy = 10;
        gbc.insets = new Insets(25, 0, 10, 0);
        formCard.add(regButton, gbc);

        // Add the card to the main container
        mainContainer.add(formCard);

        // Action Listeners
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

        add(headerPanel, BorderLayout.NORTH);
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
        field.setPreferredSize(new Dimension(300, 35));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(300, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
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