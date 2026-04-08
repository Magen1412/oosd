package internship.login;

import internship.dashboard.dao.StudentDAO;
import javax.swing.*;
import java.awt.*;
import internship.registration.RegistrationPage;

public class LoginPage extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContent;

    public LoginPage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ===== MAIN PANEL =====
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        // ===== LOGO =====
        ImageIcon logoIcon = new ImageIcon("src/main/resources/logo/img.png");
        Image img = logoIcon.getImage();
        Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        // ===== LOGIN FORM =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300, 400));

        JLabel title = new JLabel("LOGIN");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(20));

        JLabel lblUser = new JLabel("Email:");
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblUser);

        JTextField txtUser = new JTextField(15);
        txtUser.setMaximumSize(new Dimension(200, 30));
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(txtUser);
        formPanel.add(Box.createVerticalStrut(10));

        JLabel lblPass = new JLabel("Password:");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblPass);

        JPasswordField txtPass = new JPasswordField(15);
        txtPass.setMaximumSize(new Dimension(200, 30));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(txtPass);
        formPanel.add(Box.createVerticalStrut(20));

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(btnLogin);
        formPanel.add(Box.createVerticalStrut(15));

        JLabel signupLink = new JLabel("Don't have an account? Sign Up");
        signupLink.setForeground(Color.BLUE);
        signupLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(signupLink);

        signupLink.setForeground(Color.BLUE);
        signupLink.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setBackground(Color.WHITE);
        linkPanel.add(signupLink);

        signupLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(mainContent, "register"); // navigate to registration page
            }
        });

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(linkPanel);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 500);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            // Add pages
            mainContent.add(new LoginPage(cardLayout, mainContent), "login");
            mainContent.add(new RegistrationPage(mainContent, cardLayout), "register");
            // mainContent.add(new Companydashboard(mainContent, cardLayout), "dashboard");

            frame.setContentPane(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cardLayout.show(mainContent, "login");
        });
    }
}