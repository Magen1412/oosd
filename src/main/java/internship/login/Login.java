package internship.login;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Login {

    private String userId;
    private String password;

    public static HashMap<String,String> users = new HashMap<>();

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String checkLogin(){

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




    public static void main(String[] args) {

        Login.users.put("admin","1234");

        JFrame frame = new JFrame("Login");
        frame.setSize(900,500);
        frame.setLayout(new BorderLayout());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        mainPanel.add(Box.createVerticalGlue()); // push content down to center
        mainPanel.add(logoLabel);
        mainPanel.add(Box.createVerticalStrut(30)); // spacing

        // ===== LOGIN FORM =====
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(Color.WHITE);

        // Center the whole form panel
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

        JLabel signupLink = new JLabel("<HTML><U> <P>Don't have an account? Sign Up</P></U></HTML>");
        signupLink.setForeground(Color.BLUE);
        signupLink.setAlignmentX(Component.CENTER_ALIGNMENT); // center horizontally
        formPanel.add(signupLink);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createVerticalGlue()); // push content up to center

        Login login = new Login();

        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                login.setUserId(txtUser.getText().trim());
                login.setPassword(new String(txtPass.getPassword()));

                String result = login.checkLogin();

                if(result.equals("empty_all")){
                    JOptionPane.showMessageDialog(frame,"Enter User ID and Password");
                }
                else if(result.equals("empty_user")){
                    JOptionPane.showMessageDialog(frame,"Enter User ID");
                }
                else if(result.equals("empty_pass")){
                    JOptionPane.showMessageDialog(frame,"Enter Password");
                }
                else if(result.equals("invalid")){
                    JOptionPane.showMessageDialog(frame,"Invalid Login Details");
                }
                else{
                    JOptionPane.showMessageDialog(frame,"Login Successful");
                }
            }
        });

        signupLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(frame,"Sign Up Window Here");
            }
        });

        frame.add(mainPanel, BorderLayout.CENTER);

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
