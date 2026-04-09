package internship.login;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import internship.ApplicationView.ApplicationViewPage;
import internship.registration.RegistrationPage;
import internship.settings.SettingsPage;
import internship.ApplicationSubmissionPage.ApplicationStatusPage;
import internship.ApplicationSubmissionPage.ApplicationSubmissionPage;
import internship.choiceapplicationpage.ChoiceApplicationPage;
import internship.addoffer.AddInternshipOfferPage;
import internship.password.ChangePasswordPage;
import internship.support.SupportPage;
import internship.dashboard.ProfilePage;
import internship.companydashboard.Companydashboard;
import internship.companydashboard.CompanyProfile;
import internship.companydashboard.internshipschedule;
import internship.editofferpage.EditOfferPage;
import internship.searchpage.SearchPage;

import internship.dashboard.StudentDashboard;
import internship.dashboard.dao.StudentDAO;
import internship.dashboard.model.Student;

import internship.dashboard.dao.CompanyDAO;
import internship.dashboard.model.Company;

import internship.admindashboard.Admindashboard;
import internship.dashboard.dao.AdminDAO;
import internship.dashboard.model.Admin;

import internship.dashboard.DBConnection;

public class LoginPage extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContent;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/internship_db?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "blackpowa4****";

    public LoginPage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE);

        ImageIcon logoIcon = new ImageIcon("src/main/resources/logo/img.png");
        Image img = logoIcon.getImage();
        Image scaledImg = img.getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImg);

        JLabel logoLabel = new JLabel(scaledIcon);
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(30));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);
        formPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.setMaximumSize(new Dimension(300, 450));

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

        JRadioButton studentBtn = new JRadioButton("Student");
        JRadioButton companyBtn = new JRadioButton("Company");
        JRadioButton adminBtn   = new JRadioButton("Admin");

        ButtonGroup roleGroup = new ButtonGroup();
        roleGroup.add(studentBtn);
        roleGroup.add(companyBtn);
        roleGroup.add(adminBtn);

        studentBtn.setSelected(true);

        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        rolePanel.setBackground(Color.WHITE);
        rolePanel.add(studentBtn);
        rolePanel.add(companyBtn);
        rolePanel.add(adminBtn);

        formPanel.add(rolePanel);
        formPanel.add(Box.createVerticalStrut(20));

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(btnLogin);
        formPanel.add(Box.createVerticalStrut(15));

        JLabel signupLink = new JLabel("Don't have an account? Sign Up");
        signupLink.setForeground(Color.BLUE);
        signupLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        signupLink.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        signupLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel linkPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        linkPanel.setBackground(Color.WHITE);
        linkPanel.add(signupLink);

        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(linkPanel);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);

        JLabel forgotPassLink = new JLabel("Forgot Password? Change Password Here!");
        forgotPassLink.setForeground(Color.BLUE);
        forgotPassLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        forgotPassLink.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        forgotPassLink.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel forgotPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        forgotPanel.setBackground(Color.WHITE);
        forgotPanel.add(forgotPassLink);

        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(forgotPanel);

        btnLogin.addActionListener(e -> {
            String email = txtUser.getText().trim();
            String pass = new String(txtPass.getPassword());
            String role = studentBtn.isSelected() ? "student" :
                    companyBtn.isSelected() ? "company" : "admin";

            if (email.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter both email and password.");
                return;
            }

            try (Connection conn = DBConnection.getConnection()) {
                boolean valid = false;

                if (role.equals("student")) {
                    StudentDAO studentDAO = new StudentDAO();
                    valid = studentDAO.validateLogin(email, pass);
                } else if (role.equals("company")) {
                    CompanyDAO companyDAO = new CompanyDAO();
                    valid = companyDAO.validateLogin(email, pass);
                } else if (role.equals("admin")) {
                    AdminDAO adminDAO = new AdminDAO();
                    valid = adminDAO.validateLogin(email, pass);
                }

                if (valid) {
                    JOptionPane.showMessageDialog(this, "Login successful!");
                    if (role.equals("student")) {
                        cardLayout.show(mainContent, "studentDashboard");
                    } else if (role.equals("company")) {
                        cardLayout.show(mainContent, "companyDashboard");
                    } else if (role.equals("admin")) {
                        cardLayout.show(mainContent, "adminDashboard");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid credentials for " + role);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        signupLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(mainContent, "register");
            }
        });

        forgotPassLink.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                cardLayout.show(mainContent, "changePassword");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 500);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            mainContent.add(new LoginPage(cardLayout, mainContent), "login");
            mainContent.add(new RegistrationPage(mainContent, cardLayout), "register");
            mainContent.add(new StudentDashboard(cardLayout, mainContent), "studentDashboard");
            mainContent.add(new Companydashboard(mainContent, cardLayout), "companyDashboard");
            mainContent.add(new Admindashboard(mainContent, cardLayout), "adminDashboard");
            mainContent.add(new SettingsPage("Student", cardLayout, mainContent), "studentSettings");
            mainContent.add(new SettingsPage("Company", cardLayout, mainContent), "companySettings");
            mainContent.add(new SettingsPage("Admin", cardLayout, mainContent), "adminSettings");
            mainContent.add(new ApplicationStatusPage(mainContent, cardLayout), "applicationStatus");
            mainContent.add(new ApplicationViewPage(mainContent, cardLayout), "ApplicationViewPage");
            mainContent.add(new ApplicationSubmissionPage(mainContent, cardLayout), "ApplicationSubmission");
            mainContent.add(new AddInternshipOfferPage(cardLayout, mainContent), "AddOffer");
            //mainContent.add(new ChangePasswordPage(cardLayout, mainContent), "changePassword");
            mainContent.add(new SupportPage(cardLayout, mainContent), "supportPage");
            mainContent.add(new ProfilePage(cardLayout, mainContent), "profilePage");
            mainContent.add(new SearchPage(cardLayout, mainContent), "browseInternships");
            mainContent.add(new CompanyProfile(cardLayout, mainContent), "companyProfile");
            mainContent.add(new EditOfferPage(cardLayout, mainContent), "EditOffer");
            mainContent.add(new internshipschedule(cardLayout, mainContent), "interviewScheduling");
            //mainContent.add(new ChoiceApplicationPage(cardLayout, mainContent), "choiceApplication");

            frame.setContentPane(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cardLayout.show(mainContent, "login");
        });
    }
}