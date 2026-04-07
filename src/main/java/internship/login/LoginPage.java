package internship.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

public class LoginPage extends JPanel {

    private String userId;
    private String password;

    public static HashMap<String,String> users = new HashMap<>();

    private JPanel mainContent;
    private CardLayout cardLayout;

    public LoginPage(JPanel mainContent, CardLayout cardLayout) {
        this.mainContent = mainContent;
        this.cardLayout = cardLayout;

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
        title.setFont(new Font("Arial",Font.PLAIN,30));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(title);
        formPanel.add(Box.createVerticalStrut(20));

        JLabel lblUser = new JLabel("User ID:");
        lblUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblUser);

        JTextField txtUser = new JTextField(15);
        txtUser.setMaximumSize(new Dimension(200,30));
        txtUser.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(txtUser);
        formPanel.add(Box.createVerticalStrut(10));

        JLabel lblPass = new JLabel("Password:");
        lblPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(lblPass);

        JPasswordField txtPass = new JPasswordField(15);
        txtPass.setMaximumSize(new Dimension(200,30));
        txtPass.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(txtPass);
        formPanel.add(Box.createVerticalStrut(20));

        JButton btnLogin = new JButton("LOGIN");
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(btnLogin);
        formPanel.add(Box.createVerticalStrut(15));

        JLabel signupLink = new JLabel("<HTML><U>Don't have an account? Sign Up</U></HTML>");
        signupLink.setForeground(Color.BLUE);
        signupLink.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(signupLink);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);

        // ===== LOGIN LOGIC =====
        LoginPage login = this;

        btnLogin.addActionListener(e -> {
            login.setUserId(txtUser.getText().trim());
            login.setPassword(new String(txtPass.getPassword()));

            String result = login.checkLogin();

            switch (result) {
                case "empty_all":
                    JOptionPane.showMessageDialog(this,"Enter User ID and Password");
                    break;
                case "empty_user":
                    JOptionPane.showMessageDialog(this,"Enter User ID");
                    break;
                case "empty_pass":
                    JOptionPane.showMessageDialog(this,"Enter Password");
                    break;
                case "invalid":
                    JOptionPane.showMessageDialog(this,"Invalid Login Details");
                    break;
                case "success":
                    JOptionPane.showMessageDialog(this,"Login Successful");
                    cardLayout.show(mainContent, "Dashboard"); // redirect to dashboard
                    break;
            }
        });

        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(LoginPage.this,"Sign Up Window Here");
            }
        });
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String checkLogin(){
        if(userId == null || password == null) return "empty_all";

        if(userId.equals("") && password.equals("")){
            return "empty_all";
        }
        if(userId.equals("")){
            return "empty_user";
        }
        if(password.equals("")){
            return "empty_pass";
        }

        if(users.containsKey(userId) &&
                users.get(userId).equals(password)){
            return "success";
        }
        else{
            return "invalid";
        }
    }

    // ===== MAIN for testing =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            users.put("admin","1234");

            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900,500);

            CardLayout cl = new CardLayout();
            JPanel mainContent = new JPanel(cl);

            // Add login page
            mainContent.add(new LoginPage(mainContent, cl), "Login");

            // Add dashboard placeholder
            JPanel dashboard = new JPanel(new BorderLayout());
            dashboard.add(new JLabel("🏢 Dashboard Page", SwingConstants.CENTER), BorderLayout.CENTER);
            mainContent.add(dashboard, "Dashboard");

            frame.add(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cl.show(mainContent, "Login");
        });
    }
}
