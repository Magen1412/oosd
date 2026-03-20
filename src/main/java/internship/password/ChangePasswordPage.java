package internship.password;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChangePasswordPage extends JFrame {

    private JPasswordField txtOldPass;
    private JPasswordField txtNewPass;
    private JPasswordField txtConfirmPass;

    private JButton btnChange;
    private JButton btnBack;

    private String currentPassword = "1234";

    public ChangePasswordPage(){

        setTitle("Change Password");
        setSize(900,500);
        setMinimumSize(new Dimension(700,400));
        setLayout(new GridLayout(1,2));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ===== LEFT PANEL (LOGO) =====
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(Color.WHITE);

        ImageIcon logoIcon = new ImageIcon("src/smart.png");
        Image img = logoIcon.getImage();
        Image scaledImg = img.getScaledInstance(200,200,Image.SCALE_SMOOTH);

        JLabel logoLabel = new JLabel(new ImageIcon(scaledImg));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);

        leftPanel.add(logoLabel, BorderLayout.CENTER);

        // ===== RIGHT PANEL =====
        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10,10,10,10);
        gbc.anchor = GridBagConstraints.CENTER;

        // ===== FORM PANEL (CENTERED CONTAINER) =====
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(Color.WHITE);

        GridBagConstraints fgbc = new GridBagConstraints();
        fgbc.insets = new Insets(10,10,10,10);
        fgbc.fill = GridBagConstraints.HORIZONTAL;

        // ===== TITLE =====
        JLabel title = new JLabel("CHANGE PASSWORD");
        title.setFont(new Font("Arial", Font.BOLD, 26));

        fgbc.gridx = 0;
        fgbc.gridy = 0;
        fgbc.gridwidth = 2;
        fgbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(title, fgbc);

        fgbc.gridwidth = 1;

        // ===== OLD PASSWORD =====
        fgbc.gridy++;
        fgbc.gridx = 0;
        formPanel.add(new JLabel("Old Password:"), fgbc);

        fgbc.gridx = 1;
        txtOldPass = new JPasswordField(15);
        formPanel.add(txtOldPass, fgbc);

        // ===== NEW PASSWORD =====
        fgbc.gridy++;
        fgbc.gridx = 0;
        formPanel.add(new JLabel("New Password:"), fgbc);

        fgbc.gridx = 1;
        txtNewPass = new JPasswordField(15);
        formPanel.add(txtNewPass, fgbc);

        // ===== CONFIRM PASSWORD =====
        fgbc.gridy++;
        fgbc.gridx = 0;
        formPanel.add(new JLabel("Confirm Password:"), fgbc);

        fgbc.gridx = 1;
        txtConfirmPass = new JPasswordField(15);
        formPanel.add(txtConfirmPass, fgbc);

        // ===== BUTTONS (CENTERED) =====
        fgbc.gridy++;
        fgbc.gridx = 0;
        fgbc.gridwidth = 2;
        fgbc.anchor = GridBagConstraints.CENTER;

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(Color.WHITE);

        btnChange = new JButton("Change Password");
        btnBack = new JButton("Back");

        btnPanel.add(btnChange);
        btnPanel.add(btnBack);

        formPanel.add(btnPanel, fgbc);

        // Add form panel to center
        rightPanel.add(formPanel, gbc);

        // ===== ADD PANELS =====
        add(leftPanel);
        add(rightPanel);

        // ===== BUTTON EVENTS =====
        btnChange.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                changepassword();
            }
        });

        btnBack.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===== CHANGE PASSWORD LOGIC =====
    public void changepassword(){

        String oldPass = new String(txtOldPass.getPassword());
        String newPass = new String(txtNewPass.getPassword());
        String confirmPass = new String(txtConfirmPass.getPassword());

        if(oldPass.equals("") || newPass.equals("") || confirmPass.equals("")){
            JOptionPane.showMessageDialog(this,"All fields are required");
        }
        else if(!oldPass.equals(currentPassword)){
            JOptionPane.showMessageDialog(this,"Old password is incorrect");
        }
        else if(!newPass.equals(confirmPass)){
            JOptionPane.showMessageDialog(this,"New passwords do not match");
        }
        else{
            currentPassword = newPass;

            JOptionPane.showMessageDialog(this,"Password changed successfully");

            txtOldPass.setText("");
            txtNewPass.setText("");
            txtConfirmPass.setText("");
        }
    }

    public static void main(String[] args){
        new ChangePasswordPage();
    }
}