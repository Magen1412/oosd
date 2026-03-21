package internship.Profile;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class ProfilePage extends JFrame {

    // ===== COLORS (matching Dashboard theme) =====
    private static final Color SIDEBAR_BG    = new Color(60, 63, 65);
    private static final Color SIDEBAR_HOVER = new Color(80, 83, 85);
    private static final Color SIDEBAR_TEXT  = new Color(220, 220, 220);
    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color CARD_BG       = Color.WHITE;
    private static final Color HEADER_BG     = new Color(200, 60, 50);
    private static final Color ACCENT        = new Color(200, 60, 50);
    private static final Color LABEL_COLOR   = new Color(80, 80, 80);
    private static final Color FIELD_BORDER  = new Color(200, 200, 200);
    private static final Color FIELD_ACTIVE  = new Color(200, 60, 50);
    private static final Color BTN_EDIT      = new Color(70, 130, 180);
    private static final Color BTN_UPDATE    = new Color(60, 160, 80);
    private static final Color BTN_CANCEL    = new Color(150, 150, 150);
    private static final Color BTN_BACK      = new Color(60, 63, 65);
    private static final Color CV_UPLOAD_BG  = new Color(245, 248, 252);
    private static final Color CV_BORDER     = new Color(180, 200, 230);

    // ===== FIELDS =====
    private JTextField txtUserId, txtName, txtEmail, txtPhone, txtRole;
    private JComboBox<String> cmbGender;
    private JTextArea txtBio;
    private JButton btnEdit, btnUpdate, btnCancel, btnBack;

    // CV section
    private JLabel lblCvFileName;
    private JLabel fileIcon;
    private JButton btnUploadCv, btnViewCv, btnRemoveCv;
    private File uploadedCvFile = null;

    public ProfilePage() {
        setTitle("Internship Management System");
        setSize(960, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== TITLE BAR =====
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(HEADER_BG);
        titleBar.setPreferredSize(new Dimension(0, 38));
        JLabel appTitle = new JLabel("  Internship Management System");
        appTitle.setForeground(Color.WHITE);
        appTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleBar.add(appTitle, BorderLayout.WEST);
        add(titleBar, BorderLayout.NORTH);

        add(buildSidebar(), BorderLayout.WEST);
        add(buildContent(), BorderLayout.CENTER);

        loadData();
        disableEdit();
        setVisible(true);
    }

    // ===== SIDEBAR =====
    private JPanel buildSidebar() {
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(SIDEBAR_BG);
        sidebar.setPreferredSize(new Dimension(200, 0));

        String[] items = {"Dashboard", "Jobs", "Companies", "Application Status", "Profile", "Log Out"};
        String[] icons = {"⊞", "✎", "⊟", "☰", "👤", "⏻"};

        sidebar.add(Box.createVerticalStrut(10));
        for (int i = 0; i < items.length; i++) {
            sidebar.add(createNavItem(icons[i], items[i], items[i].equals("Profile")));
        }
        sidebar.add(Box.createVerticalGlue());
        return sidebar;
    }

    private JPanel createNavItem(String icon, String label, boolean active) {
        JPanel item = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 10));
        item.setBackground(active ? SIDEBAR_HOVER : SIDEBAR_BG);
        item.setMaximumSize(new Dimension(200, 45));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        JLabel lbl = new JLabel(icon + "  " + label);
        lbl.setForeground(active ? Color.WHITE : SIDEBAR_TEXT);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        item.add(lbl);
        if (!active) {
            item.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) { item.setBackground(SIDEBAR_HOVER); }
                public void mouseExited(MouseEvent e)  { item.setBackground(SIDEBAR_BG); }
            });
        }
        return item;
    }

    // ===== CONTENT AREA =====
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);

        // Page header
        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setBackground(CONTENT_BG);
        pageHeader.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));
        JLabel pageTitle = new JLabel("Profile");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(new Color(40, 40, 40));
        pageHeader.add(pageTitle, BorderLayout.WEST);
        wrapper.add(pageHeader, BorderLayout.NORTH);

        // Scrollable area
        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(CONTENT_BG);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        scrollContent.add(buildProfileCard());
        scrollContent.add(Box.createVerticalStrut(16));
        scrollContent.add(buildCvCard());

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(12);
        scrollPane.setBackground(CONTENT_BG);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    // ===== PROFILE CARD =====
    private JPanel buildProfileCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // --- Avatar + name row ---
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
                g2.setFont(new Font("Segoe UI", Font.BOLD, 26));
                FontMetrics fm = g2.getFontMetrics();
                String initials = "JD";
                g2.drawString(initials, (64 - fm.stringWidth(initials)) / 2, 42);
            }
        };
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setOpaque(false);

        JPanel nameBlock = new JPanel();
        nameBlock.setLayout(new BoxLayout(nameBlock, BoxLayout.Y_AXIS));
        nameBlock.setBackground(CARD_BG);
        nameBlock.setBorder(BorderFactory.createEmptyBorder(6, 16, 0, 0));
        JLabel nameLabel = new JLabel("John Doe");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        nameLabel.setForeground(new Color(30, 30, 30));
        JLabel roleLabel = new JLabel("Student");
        roleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        roleLabel.setForeground(new Color(130, 130, 130));
        nameBlock.add(nameLabel);
        nameBlock.add(roleLabel);

        topRow.add(avatar);
        topRow.add(nameBlock);
        card.add(topRow, BorderLayout.NORTH);

        // --- Center block ---
        JPanel centerBlock = new JPanel(new BorderLayout());
        centerBlock.setBackground(CARD_BG);

        JPanel sepWrapper = new JPanel(new BorderLayout());
        sepWrapper.setBackground(CARD_BG);
        sepWrapper.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        sepWrapper.add(sep);
        centerBlock.add(sepWrapper, BorderLayout.NORTH);

        // Form
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtUserId = createStyledField();
        txtName   = createStyledField();
        txtEmail  = createStyledField();
        txtPhone  = createStyledField();
        txtRole   = createStyledField();
        txtUserId.setEditable(false);
        txtUserId.setBackground(new Color(248, 248, 248));
        txtRole.setEditable(false);
        txtRole.setBackground(new Color(248, 248, 248));

        cmbGender = new JComboBox<>(new String[]{"Select Gender", "Male", "Female", "Prefer not to say"});
        cmbGender.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbGender.setPreferredSize(new Dimension(180, 34));
        cmbGender.setBackground(Color.WHITE);
        cmbGender.setEnabled(false);

        // Row 0: User ID | Full Name
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.25;
        formPanel.add(styledLabel("User ID"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        formPanel.add(txtUserId, gbc);
        gbc.gridx = 2; gbc.weightx = 0.25;
        formPanel.add(styledLabel("Full Name"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.75;
        formPanel.add(txtName, gbc);

        // Row 1: Email | Phone
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0.25;
        formPanel.add(styledLabel("Email"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        formPanel.add(txtEmail, gbc);
        gbc.gridx = 2; gbc.weightx = 0.25;
        formPanel.add(styledLabel("Phone"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.75;
        formPanel.add(txtPhone, gbc);

        // Row 2: Role | Gender
        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0.25;
        formPanel.add(styledLabel("Role"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.75;
        formPanel.add(txtRole, gbc);
        gbc.gridx = 2; gbc.weightx = 0.25;
        formPanel.add(styledLabel("Gender"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.75;
        formPanel.add(cmbGender, gbc);

        // Row 3: About Me (spans full width)
        gbc.gridx = 0; gbc.gridy = 3; gbc.weightx = 0.25;
        gbc.insets = new Insets(8, 0, 0, 20);
        formPanel.add(styledLabel("About Me"), gbc);

        txtBio = new JTextArea(3, 30);
        txtBio.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtBio.setLineWrap(true);
        txtBio.setWrapStyleWord(true);
        txtBio.setEditable(false);
        txtBio.setBackground(new Color(248, 248, 248));
        txtBio.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        JScrollPane bioScroll = new JScrollPane(txtBio);
        bioScroll.setBorder(null);
        bioScroll.setPreferredSize(new Dimension(400, 72));

        gbc.gridx = 1; gbc.gridwidth = 3; gbc.weightx = 1.0;
        formPanel.add(bioScroll, gbc);
        gbc.gridwidth = 1;

        centerBlock.add(formPanel, BorderLayout.CENTER);
        card.add(centerBlock, BorderLayout.CENTER);

        // --- Buttons ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        btnBack   = styledButton("← Back",   BTN_BACK);
        btnEdit   = styledButton("✎ Edit",   BTN_EDIT);
        btnCancel = styledButton("✕ Cancel", BTN_CANCEL);
        btnUpdate = styledButton("✔ Update", BTN_UPDATE);

        buttonPanel.add(btnBack);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnUpdate);
        card.add(buttonPanel, BorderLayout.SOUTH);

        btnEdit.addActionListener(e -> enableEdit());
        btnUpdate.addActionListener(e -> updateProfile());
        btnCancel.addActionListener(e -> { loadData(); disableEdit(); });
        btnBack.addActionListener(e -> dispose());

        return card;
    }

    // ===== CV CARD =====
    private JPanel buildCvCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(24, 40, 24, 40)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        // Section title
        JLabel cvTitle = new JLabel("Curriculum Vitae (CV)");
        cvTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cvTitle.setForeground(new Color(40, 40, 40));
        cvTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        card.add(cvTitle, BorderLayout.NORTH);

        // Drop zone
        JPanel cvArea = new JPanel(new BorderLayout());
        cvArea.setBackground(CV_UPLOAD_BG);
        cvArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(CV_BORDER, 1, true),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));

        JPanel cvInfo = new JPanel();
        cvInfo.setLayout(new BoxLayout(cvInfo, BoxLayout.Y_AXIS));
        cvInfo.setBackground(CV_UPLOAD_BG);

        fileIcon = new JLabel("📄", JLabel.CENTER);
        fileIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        fileIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblCvFileName = new JLabel("No CV uploaded yet", JLabel.CENTER);
        lblCvFileName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblCvFileName.setForeground(new Color(120, 120, 120));
        lblCvFileName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblCvFileName.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        cvInfo.add(fileIcon);
        cvInfo.add(lblCvFileName);
        cvArea.add(cvInfo, BorderLayout.CENTER);
        card.add(cvArea, BorderLayout.CENTER);

        // CV buttons
        JPanel cvButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        cvButtons.setBackground(CARD_BG);
        cvButtons.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));

        btnUploadCv = styledButton("⬆ Upload CV", new Color(70, 130, 180));
        btnUploadCv.setPreferredSize(new Dimension(130, 34));

        btnViewCv = styledButton("👁 View CV", new Color(100, 160, 100));
        btnViewCv.setPreferredSize(new Dimension(120, 34));
        btnViewCv.setEnabled(false);

        btnRemoveCv = styledButton("✕ Remove", new Color(200, 80, 70));
        btnRemoveCv.setPreferredSize(new Dimension(110, 34));
        btnRemoveCv.setEnabled(false);

        JLabel cvHint = new JLabel("  Accepted: PDF, DOC, DOCX  |  Max: 5 MB");
        cvHint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        cvHint.setForeground(new Color(160, 160, 160));

        cvButtons.add(btnUploadCv);
        cvButtons.add(btnViewCv);
        cvButtons.add(btnRemoveCv);
        cvButtons.add(cvHint);
        card.add(cvButtons, BorderLayout.SOUTH);

        // CV actions
        btnUploadCv.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select your CV");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "CV Files (PDF, DOC, DOCX)", "pdf", "doc", "docx"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File selected = chooser.getSelectedFile();
                long sizeKB = selected.length() / 1024;
                if (sizeKB > 5120) {
                    JOptionPane.showMessageDialog(this, "File too large. Max size is 5 MB.",
                            "Upload Error", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                uploadedCvFile = selected;
                fileIcon.setText("📋");
                lblCvFileName.setText("📎  " + selected.getName() + "  (" + sizeKB + " KB)");
                lblCvFileName.setForeground(new Color(40, 40, 40));
                lblCvFileName.setFont(new Font("Segoe UI", Font.BOLD, 13));
                btnViewCv.setEnabled(true);
                btnRemoveCv.setEnabled(true);
                JOptionPane.showMessageDialog(this, "CV uploaded: " + selected.getName(),
                        "Upload Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnViewCv.addActionListener(e -> {
            if (uploadedCvFile != null) {
                try {
                    Desktop.getDesktop().open(uploadedCvFile);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Cannot open file: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnRemoveCv.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Remove your uploaded CV?",
                    "Remove CV", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                uploadedCvFile = null;
                fileIcon.setText("📄");
                lblCvFileName.setText("No CV uploaded yet");
                lblCvFileName.setForeground(new Color(120, 120, 120));
                lblCvFileName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                btnViewCv.setEnabled(false);
                btnRemoveCv.setEnabled(false);
            }
        });

        return card;
    }

    // ===== HELPERS =====
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
                BorderFactory.createEmptyBorder(4, 10, 4, 10)
        ));
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
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(110, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg); }
        });
        return btn;
    }

    // ===== LOGIC =====
    public void loadData() {
        txtUserId.setText("1001");
        txtName.setText("John Doe");
        txtEmail.setText("john@gmail.com");
        txtPhone.setText("57894521");
        txtRole.setText("Student");
        cmbGender.setSelectedIndex(1); // Male
        txtBio.setText("Final year Computer Science student with a passion for software development " +
                "and data systems. Looking for internship opportunities in backend development or data engineering.");
    }

    public void enableEdit() {
        txtName.setEditable(true);
        txtEmail.setEditable(true);
        txtPhone.setEditable(true);
        txtBio.setEditable(true);
        txtBio.setBackground(Color.WHITE);
        cmbGender.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnCancel.setEnabled(true);
        btnEdit.setEnabled(false);
    }

    public void disableEdit() {
        txtName.setEditable(false);
        txtEmail.setEditable(false);
        txtPhone.setEditable(false);
        txtBio.setEditable(false);
        txtBio.setBackground(new Color(248, 248, 248));
        cmbGender.setEnabled(false);
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
            JOptionPane.showMessageDialog(this, "Profile updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            disableEdit();
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(ProfilePage::new);
    }
}