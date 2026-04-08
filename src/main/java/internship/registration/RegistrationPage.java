package internship.registration;

import internship.dashboard.dao.StudentDAO;
import internship.dashboard.model.Student;
import internship.login.LoginPage;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.regex.Pattern;

public class RegistrationPage extends JPanel {

    private final Color PRIMARY_BLUE = new Color(0, 0, 200);
    private final Color BACKGROUND_LIGHT = new Color(236, 240, 241);
    private final Color TEXT_DARK = new Color(44, 62, 80);

    public RegistrationPage(JPanel mainContent, CardLayout cardLayout) {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_LIGHT);

        JPanel formCard = new JPanel(new GridBagLayout());
        formCard.setBackground(Color.WHITE);
        formCard.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(20, 40, 20, 40)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(6, 0, 6, 0);
        gbc.gridx = 0;

        // ===== LOGO =====
        ImageIcon logoIcon = new ImageIcon("src/main/resources/logo/img.png");
        Image img = logoIcon.getImage();
        Image scaledImg = img.getScaledInstance(150, 100, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(scaledImg));
        logoLabel.setHorizontalAlignment(JLabel.CENTER);

        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0);
        formCard.add(logoLabel, gbc);

        // ===== TITLE =====
        JLabel registerTitle = new JLabel("REGISTER", SwingConstants.CENTER);
        registerTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        registerTitle.setForeground(TEXT_DARK);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 15, 0);
        formCard.add(registerTitle, gbc);

        gbc.insets = new Insets(6, 0, 6, 0);

        JTextField nameField = createStyledTextField();
        JTextField emailField = createStyledTextField();
        JTextField phoneField = createStyledTextField();
        JPasswordField passField = createStyledPasswordField();
        JPasswordField confirmPassField = createStyledPasswordField();

        // Gender radio buttons
        JRadioButton maleBtn = new JRadioButton("Male");
        JRadioButton femaleBtn = new JRadioButton("Female");
        ButtonGroup genderGroup = new ButtonGroup();
        genderGroup.add(maleBtn);
        genderGroup.add(femaleBtn);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        genderPanel.add(maleBtn);
        genderPanel.add(femaleBtn);

        addFormField(formCard, "Full Name:", nameField, gbc, 2);
        addFormField(formCard, "Email Address:", emailField, gbc, 4);
        addFormField(formCard, "Phone:", phoneField, gbc, 6);
        addFormField(formCard, "Password:", passField, gbc, 8);
        addFormField(formCard, "Confirm Password:", confirmPassField, gbc, 10);
        addFormField(formCard, "Gender:", genderPanel, gbc, 12);

        // ===== REGISTER BUTTON =====
        JButton regButton = new JButton("CREATE ACCOUNT");
        regButton.setBackground(PRIMARY_BLUE);
        regButton.setForeground(Color.WHITE);
        regButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        regButton.setPreferredSize(new Dimension(350, 40));
        regButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        gbc.gridy = 14;
        gbc.insets = new Insets(15, 0, 10, 0);
        formCard.add(regButton, gbc);

        // ===== LOGIN LINK + BUTTON =====
        JLabel loginLabel = new JLabel("Already have an account?");
        loginLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        loginLabel.setForeground(TEXT_DARK);
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(PRIMARY_BLUE);
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
        loginButton.setPreferredSize(new Dimension(250, 35));
        loginButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginButton.setBorder(BorderFactory.createLineBorder(PRIMARY_BLUE, 1));

        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLogin = new GridBagConstraints();
        gbcLogin.gridx = 0;
        gbcLogin.fill = GridBagConstraints.HORIZONTAL;
        gbcLogin.insets = new Insets(4, 0, 4, 0);

        gbcLogin.gridy = 0;
        loginPanel.add(loginLabel, gbcLogin);
        gbcLogin.gridy = 1;
        loginPanel.add(loginButton, gbcLogin);

        gbc.gridy = 15;
        formCard.add(loginPanel, gbc);

        add(formCard, BorderLayout.CENTER);

        // ===== ACTIONS =====
        regButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String pass = new String(passField.getPassword());
            String confirm = new String(confirmPassField.getPassword());
            String gender = maleBtn.isSelected() ? "M" : (femaleBtn.isSelected() ? "F" : null);

            // Validations
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || pass.isEmpty() || gender == null) {
                JOptionPane.showMessageDialog(this, "Please fill all fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!pass.equals(confirm)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                StudentDAO dao = new StudentDAO();
                if (dao.existsByEmail(email)) {
                    JOptionPane.showMessageDialog(this, "User already exists with this email", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // CV path left null at registration
                Student student = new Student(name, email, pass, phone, gender, null, LocalDateTime.now());
                dao.addStudent(student);

                JOptionPane.showMessageDialog(this, "Registration successful for " + name);
                cardLayout.show(mainContent, "loginPage");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error saving student: " + ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginButton.addActionListener(e -> cardLayout.show(mainContent, "login"));
        loginLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                cardLayout.show(mainContent, "login");
            }
        });
    }

    private void addFormField(JPanel panel, String labelText, JComponent field, GridBagConstraints gbc, int y) {
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_DARK);
        gbc.gridy = y;
        panel.add(label, gbc);
        gbc.gridy = y + 1;
        panel.add(field, gbc);
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(400, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        return field;
    }

    private JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setPreferredSize(new Dimension(400, 30));
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
        return field;
    }

    // For standalone testing
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JPanel mainContent = new JPanel(new CardLayout());
            CardLayout cardLayout = (CardLayout) mainContent.getLayout();

            RegistrationPage registrationPage = new RegistrationPage(mainContent, cardLayout);

            LoginPage loginPage = new LoginPage(cardLayout, mainContent);

            mainContent.add(registrationPage, "register");
            mainContent.add(loginPage, "login");

            cardLayout.show(mainContent, "register");

            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(500, 700); // smaller frame size so content fits neatly
            frame.setLocationRelativeTo(null);
            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}