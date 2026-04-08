package internship.admindashboard;


import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class AdminProfilePage extends JPanel {

    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color CARD_BG       = Color.WHITE;
    private static final Color HEADER_BG     = new Color(200, 60, 50);
    private static final Color ACCENT        = new Color(200, 60, 50);
    private static final Color LABEL_COLOR   = new Color(80, 80, 80);
    private static final Color FIELD_BORDER  = new Color(200, 200, 200);
    private static final Color FIELD_ACTIVE  = new Color(200, 60, 50);
    private static final Color UPLOAD_BG     = new Color(245, 248, 252);
    private static final Color UPLOAD_BORDER = new Color(180, 200, 230);

    private static final Color BTN_BACK   = new Color(60, 63, 65);
    private static final Color BTN_EDIT   = new Color(40,  42,  44);
    private static final Color BTN_CANCEL = new Color(40,  42,  44);
    private static final Color BTN_UPDATE = new Color(40,  42,  44);

    private static final Color STATS_BLUE  = new Color(70,  130, 180);
    private static final Color STATS_PURP  = new Color(100, 60,  160);
    private static final Color STATS_GREEN = new Color(60,  160, 80);
    private static final Color STATS_ORG   = new Color(230, 150, 40);

    private JTextField txtAdminId, txtName, txtEmail, txtPhone, txtDepartment, txtLastLogin;
    private JComboBox<String> cmbGender, cmbAccessLevel;
    private JTextArea txtNotes;
    private JButton btnEdit, btnUpdate, btnCancel, btnBack;

    private JLabel lblPicName, picIcon;
    private JButton btnUploadPic, btnRemovePic;
    private File uploadedPic = null;

    private final CardLayout cardLayout;
    private final JPanel mainContent;

    public AdminProfilePage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout  = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(CONTENT_BG);
        add(buildContent(), BorderLayout.CENTER);

        loadData();
        disableEdit();
    }

    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);

        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setBackground(CONTENT_BG);
        pageHeader.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));
        JLabel pageTitle = new JLabel("Admin Profile");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(new Color(40, 40, 40));
        pageHeader.add(pageTitle, BorderLayout.WEST);
        wrapper.add(pageHeader, BorderLayout.NORTH);

        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(CONTENT_BG);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        scrollContent.add(buildStatsCard());
        scrollContent.add(Box.createVerticalStrut(14));
        scrollContent.add(buildProfileCard());
        scrollContent.add(Box.createVerticalStrut(14));
        scrollContent.add(buildPictureCard());

        JScrollPane scroll = new JScrollPane(scrollContent);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildStatsCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(20, 32, 20, 32)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        row.setBackground(CARD_BG);
        row.add(statTile("Total Users",     "142",         STATS_BLUE));
        row.add(statTile("Companies",       "18",          STATS_PURP));
        row.add(statTile("Active Interns",  "67",          STATS_GREEN));
        row.add(statTile("Pending Reviews", "9",           STATS_ORG));
        row.add(statTile("Access Level",    "Super Admin", ACCENT));
        card.add(row, BorderLayout.CENTER);
        return card;
    }

    private JPanel statTile(String label, String value, Color color) {
        JPanel tile = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        tile.setOpaque(false);
        tile.setPreferredSize(new Dimension(150, 68));
        tile.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        JLabel valLbl = new JLabel(value);
        valLbl.setFont(new Font("Segoe UI", Font.BOLD, value.length() > 6 ? 14 : 22));
        valLbl.setForeground(Color.WHITE);
        JLabel txtLbl = new JLabel(label);
        txtLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtLbl.setForeground(new Color(255, 255, 255, 200));
        tile.add(valLbl, BorderLayout.CENTER);
        tile.add(txtLbl, BorderLayout.SOUTH);
        return tile;
    }

    private JPanel buildProfileCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topRow.setBackground(CARD_BG);

        JPanel avatar = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT);
                g2.fillOval(0, 0, 64, 64);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
                FontMetrics fm = g2.getFontMetrics();
                String initials = "AD";
                g2.drawString(initials, (64 - fm.stringWidth(initials)) / 2, 42);
            }
        };
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setOpaque(false);

        JPanel nameBlock = new JPanel();
        nameBlock.setLayout(new BoxLayout(nameBlock, BoxLayout.Y_AXIS));
        nameBlock.setBackground(CARD_BG);
        nameBlock.setBorder(BorderFactory.createEmptyBorder(4, 16, 0, 0));

        JPanel nameBadgeRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        nameBadgeRow.setBackground(CARD_BG);

        JLabel nameLabel = new JLabel("Alice Dubois");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        nameLabel.setForeground(new Color(30, 30, 30));

        JPanel badge = new JPanel() {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(ACCENT);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        badge.setOpaque(false);
        badge.setLayout(new BorderLayout());
        badge.setPreferredSize(new Dimension(60, 20));
        JLabel badgeLbl = new JLabel("ADMIN", JLabel.CENTER);
        badgeLbl.setFont(new Font("Segoe UI", Font.BOLD, 10));
        badgeLbl.setForeground(Color.WHITE);
        badge.add(badgeLbl);

        nameBadgeRow.add(nameLabel);
        nameBadgeRow.add(badge);

        JLabel roleLabel = new JLabel("System Administrator - IT Department");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roleLabel.setForeground(new Color(130, 130, 130));
        nameBlock.add(nameBadgeRow);
        nameBlock.add(roleLabel);

        topRow.add(avatar);
        topRow.add(nameBlock);
        card.add(topRow, BorderLayout.NORTH);

        JPanel sepWrapper = new JPanel(new BorderLayout());
        sepWrapper.setBackground(CARD_BG);
        sepWrapper.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        sepWrapper.add(sep);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtAdminId    = createStyledField(); txtAdminId.setEditable(false);   txtAdminId.setBackground(new Color(248, 248, 248));
        txtName       = createStyledField();
        txtEmail      = createStyledField();
        txtPhone      = createStyledField();
        txtDepartment = createStyledField();
        txtLastLogin  = createStyledField(); txtLastLogin.setEditable(false); txtLastLogin.setBackground(new Color(248, 248, 248));

        cmbGender = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Prefer not to say"});
        cmbGender.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbGender.setPreferredSize(new Dimension(180, 34));
        cmbGender.setBackground(Color.WHITE);
        cmbGender.setEnabled(false);

        cmbAccessLevel = new JComboBox<>(new String[]{"Super Admin", "Admin", "Moderator", "Read-Only"});
        cmbAccessLevel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbAccessLevel.setPreferredSize(new Dimension(180, 34));
        cmbAccessLevel.setBackground(Color.WHITE);
        cmbAccessLevel.setEnabled(false);

        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.22; formPanel.add(styledLabel("Admin ID"),    gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(txtAdminId, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Full Name"),   gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtName, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.weightx=0.22; formPanel.add(styledLabel("Email"),        gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(txtEmail, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Phone"),        gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtPhone, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.weightx=0.22; formPanel.add(styledLabel("Department"),   gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(txtDepartment, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Gender"),       gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(cmbGender, gbc);

        gbc.gridx=0; gbc.gridy=3; gbc.weightx=0.22; formPanel.add(styledLabel("Access Level"), gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(cmbAccessLevel, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Last Login"),   gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtLastLogin, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.weightx=0.22;
        gbc.insets = new Insets(8, 0, 0, 20);
        formPanel.add(styledLabel("Admin Notes"), gbc);

        txtNotes = new JTextArea(3, 30);
        txtNotes.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setEditable(false);
        txtNotes.setBackground(new Color(248, 248, 248));
        txtNotes.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)));
        JScrollPane notesScroll = new JScrollPane(txtNotes);
        notesScroll.setBorder(null);
        notesScroll.setPreferredSize(new Dimension(400, 72));

        gbc.gridx=1; gbc.gridwidth=3; gbc.weightx=1.0;
        formPanel.add(notesScroll, gbc);
        gbc.gridwidth = 1;

        JPanel centerBlock = new JPanel(new BorderLayout());
        centerBlock.setBackground(CARD_BG);
        centerBlock.add(sepWrapper, BorderLayout.NORTH);
        centerBlock.add(formPanel, BorderLayout.CENTER);
        card.add(centerBlock, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnBack   = styledButton("< Back to Dashboard", BTN_BACK);
        btnBack.setPreferredSize(new Dimension(170, 34));
        btnEdit   = styledButton("Edit",   BTN_EDIT);
        btnCancel = styledButton("Cancel", BTN_CANCEL);
        btnUpdate = styledButton("Update", BTN_UPDATE);

        buttonPanel.add(btnBack);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnUpdate);
        card.add(buttonPanel, BorderLayout.SOUTH);

        btnEdit.addActionListener(e -> enableEdit());
        btnUpdate.addActionListener(e -> updateProfile());
        btnCancel.addActionListener(e -> { loadData(); disableEdit(); });
        btnBack.addActionListener(e -> cardLayout.show(mainContent, "adminDashboard"));

        return card;
    }

    private JPanel buildPictureCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(24, 40, 24, 40)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel picTitle = new JLabel("Profile Picture");
        picTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        picTitle.setForeground(new Color(40, 40, 40));
        picTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        card.add(picTitle, BorderLayout.NORTH);

        JPanel uploadArea = new JPanel(new BorderLayout());
        uploadArea.setBackground(UPLOAD_BG);
        uploadArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UPLOAD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UPLOAD_BG);

        picIcon = new JLabel("[ No Photo ]", JLabel.CENTER);
        picIcon.setFont(new Font("Segoe UI", Font.BOLD, 18));
        picIcon.setForeground(new Color(160, 160, 160));
        picIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblPicName = new JLabel("No picture uploaded yet", JLabel.CENTER);
        lblPicName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblPicName.setForeground(new Color(120, 120, 120));
        lblPicName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblPicName.setBorder(BorderFactory.createEmptyBorder(8, 0, 0, 0));

        info.add(picIcon);
        info.add(lblPicName);
        uploadArea.add(info, BorderLayout.CENTER);
        card.add(uploadArea, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(CARD_BG);
        btnRow.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));

        btnUploadPic = styledButton("Upload Photo", new Color(30, 100, 160));
        btnUploadPic.setPreferredSize(new Dimension(130, 34));
        btnRemovePic = styledButton("Remove", new Color(40, 42, 44));
        btnRemovePic.setPreferredSize(new Dimension(100, 34));
        btnRemovePic.setEnabled(false);

        JLabel hint = new JLabel("   Accepted: PNG, JPG  |  Recommended: 200x200 px");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(new Color(160, 160, 160));

        btnRow.add(btnUploadPic);
        btnRow.add(btnRemovePic);
        btnRow.add(hint);
        card.add(btnRow, BorderLayout.SOUTH);

        btnUploadPic.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Profile Picture");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image Files (PNG, JPG)", "png", "jpg", "jpeg"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                long kb = f.length() / 1024;
                uploadedPic = f;
                picIcon.setText("[ Photo Uploaded ]");
                picIcon.setForeground(new Color(35, 130, 55));
                lblPicName.setText(f.getName() + "  (" + kb + " KB)");
                lblPicName.setForeground(new Color(40, 40, 40));
                lblPicName.setFont(new Font("Segoe UI", Font.BOLD, 13));
                btnRemovePic.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Profile picture uploaded: " + f.getName(),
                        "Upload Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRemovePic.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Remove the profile picture?",
                    "Remove Picture", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                uploadedPic = null;
                picIcon.setText("[ No Photo ]");
                picIcon.setForeground(new Color(160, 160, 160));
                lblPicName.setText("No picture uploaded yet");
                lblPicName.setForeground(new Color(120, 120, 120));
                lblPicName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                btnRemovePic.setEnabled(false);
            }
        });

        return card;
    }

    private JLabel styledLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(LABEL_COLOR);
        return lbl;
    }

    private JTextField createStyledField() {
        JTextField field = new JTextField(16);
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(180, 34));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(4, 10, 4, 10)));
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.isEditable())
                    field.setBorder(BorderFactory.createCompoundBorder(
                            new LineBorder(FIELD_ACTIVE, 2, true),
                            BorderFactory.createEmptyBorder(3, 9, 3, 9)));
            }
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(FIELD_BORDER, 1, true),
                        BorderFactory.createEmptyBorder(4, 10, 4, 10)));
            }
        });
        return field;
    }

    private JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(100, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg); }
        });
        return btn;
    }

    public void loadData() {
        txtAdminId.setText("A001");
        txtName.setText("Alice Dubois");
        txtEmail.setText("alice.dubois@ims-admin.mu");
        txtPhone.setText("52001122");
        txtDepartment.setText("Information Technology");
        txtLastLogin.setText("20/03/2026  09:14 AM");
        cmbGender.setSelectedIndex(2);
        cmbAccessLevel.setSelectedIndex(0);
        txtNotes.setText("Primary system administrator. Responsible for user management, " +
                "system configuration, and overall platform integrity. Contact for escalations.");
    }

    public void enableEdit() {
        txtName.setEditable(true);
        txtEmail.setEditable(true);
        txtPhone.setEditable(true);
        txtDepartment.setEditable(true);
        txtNotes.setEditable(true);
        txtNotes.setBackground(Color.WHITE);
        cmbGender.setEnabled(true);
        cmbAccessLevel.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnCancel.setEnabled(true);
        btnEdit.setEnabled(false);
    }

    public void disableEdit() {
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtPhone.setEditable(false);
        txtDepartment.setEditable(false);
        txtNotes.setEditable(false);
        txtNotes.setBackground(new Color(248, 248, 248));
        cmbGender.setEnabled(false);
        cmbAccessLevel.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnCancel.setEnabled(false);
        btnEdit.setEnabled(true);
    }

    public void updateProfile() {
        String name   = txtName.getText().trim();
        String email  = txtEmail.getText().trim();
        String phone  = txtPhone.getText().trim();
        String gender = (String) cmbGender.getSelectedItem();
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name, Email and Phone cannot be empty.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } else if ("Select Gender".equals(gender)) {
            JOptionPane.showMessageDialog(this, "Please select a gender.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Admin profile updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            disableEdit();
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Admin Profile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(980, 860);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            mainContent.add(new AdminProfilePage(cardLayout, mainContent), "adminProfile");
            mainContent.add(new Admindashboard(mainContent, cardLayout), "adminDashboard");

            cardLayout.show(mainContent, "adminProfile");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}