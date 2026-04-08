package internship.companydashboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import internship.companydashboard.Companydashboard;

public class CompanyProfile extends JPanel {

    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color CARD_BG       = Color.WHITE;
    private static final Color HEADER_BG     = new Color(200, 60, 50);
    private static final Color ACCENT        = new Color(200, 60, 50);
    private static final Color LABEL_COLOR   = new Color(80, 80, 80);
    private static final Color FIELD_BORDER  = new Color(200, 200, 200);
    private static final Color FIELD_ACTIVE  = new Color(200, 60, 50);
    private static final Color BTN_BACK      = new Color(60, 63, 65);
    private static final Color BTN_EDIT      = new Color(40, 42, 44);
    private static final Color BTN_CANCEL    = new Color(40, 42, 44);
    private static final Color BTN_UPDATE    = new Color(40, 42, 44);
    private static final Color UPLOAD_BG     = new Color(245, 248, 252);
    private static final Color UPLOAD_BORDER = new Color(180, 200, 230);

    private JTextField txtCompanyId, txtCompanyName, txtEmail, txtPhone,
            txtWebsite, txtAddress, txtRegNumber;
    private JComboBox<String> cmbIndustry, cmbSize, cmbCountry;
    private JTextArea txtDescription;
    private JButton btnEdit, btnUpdate, btnCancel, btnBack;

    private JLabel lblLogoName, logoIcon;
    private JButton btnUploadLogo, btnRemoveLogo;
    private File uploadedLogo = null;

    private final CardLayout cardLayout;
    private final JPanel mainContent;

    public CompanyProfile(CardLayout cardLayout, JPanel mainContent) {
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
        JLabel pageTitle = new JLabel("Company Profile");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(new Color(40, 40, 40));
        pageHeader.add(pageTitle, BorderLayout.WEST);
        wrapper.add(pageHeader, BorderLayout.NORTH);

        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(CONTENT_BG);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        scrollContent.add(buildProfileCard());
        scrollContent.add(Box.createVerticalStrut(16));
        scrollContent.add(buildLogoCard());

        JScrollPane scroll = new JScrollPane(scrollContent);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(12);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildProfileCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(30, 40, 30, 40)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JPanel topRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topRow.setBackground(CARD_BG);

        JPanel avatar = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(70, 130, 180));
                g2.fillRoundRect(0, 0, 64, 64, 12, 12);
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Segoe UI", Font.BOLD, 24));
                FontMetrics fm = g2.getFontMetrics();
                String initials = "TC";
                g2.drawString(initials, (64 - fm.stringWidth(initials)) / 2, 42);
            }
        };
        avatar.setPreferredSize(new Dimension(64, 64));
        avatar.setOpaque(false);

        JPanel nameBlock = new JPanel();
        nameBlock.setLayout(new BoxLayout(nameBlock, BoxLayout.Y_AXIS));
        nameBlock.setBackground(CARD_BG);
        nameBlock.setBorder(BorderFactory.createEmptyBorder(6, 16, 0, 0));
        JLabel nameLabel = new JLabel("TechCorp Mauritius");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 17));
        nameLabel.setForeground(new Color(30, 30, 30));
        JLabel typeLabel = new JLabel("Technology  •  Company");
        typeLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        typeLabel.setForeground(new Color(130, 130, 130));
        nameBlock.add(nameLabel);
        nameBlock.add(typeLabel);

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

        txtCompanyId   = createStyledField(); txtCompanyId.setEditable(false);  txtCompanyId.setBackground(new Color(248, 248, 248));
        txtCompanyName = createStyledField();
        txtEmail       = createStyledField();
        txtPhone       = createStyledField();
        txtWebsite     = createStyledField();
        txtAddress     = createStyledField();
        txtRegNumber   = createStyledField(); txtRegNumber.setEditable(false);  txtRegNumber.setBackground(new Color(248, 248, 248));

        cmbIndustry = styledCombo(new String[]{"Select Industry","Technology","Finance","Healthcare",
                "Education","Engineering","Retail","Media","Government","Other"});
        cmbSize     = styledCombo(new String[]{"Select Size","1–10","11–50","51–200","201–500","500+"});
        cmbCountry  = styledCombo(new String[]{"Mauritius","France","United Kingdom",
                "South Africa","India","United States","Other"});

        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.22; formPanel.add(styledLabel("Company ID"),   gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(txtCompanyId, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Company Name"),  gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtCompanyName, gbc);

        gbc.gridx=0; gbc.gridy=1; gbc.weightx=0.22; formPanel.add(styledLabel("Email"),         gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(txtEmail, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Phone"),         gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtPhone, gbc);

        gbc.gridx=0; gbc.gridy=2; gbc.weightx=0.22; formPanel.add(styledLabel("Website"),       gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(txtWebsite, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Reg. Number"),   gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtRegNumber, gbc);

        gbc.gridx=0; gbc.gridy=3; gbc.weightx=0.22; formPanel.add(styledLabel("Industry"),      gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(cmbIndustry, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Company Size"),  gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(cmbSize, gbc);

        gbc.gridx=0; gbc.gridy=4; gbc.weightx=0.22; formPanel.add(styledLabel("Country"),       gbc);
        gbc.gridx=1; gbc.weightx=0.78; formPanel.add(cmbCountry, gbc);
        gbc.gridx=2; gbc.weightx=0.22; formPanel.add(styledLabel("Address"),       gbc);
        gbc.gridx=3; gbc.weightx=0.78; formPanel.add(txtAddress, gbc);

        gbc.gridx=0; gbc.gridy=5; gbc.weightx=0.22;
        gbc.insets = new Insets(8, 0, 0, 20);
        formPanel.add(styledLabel("About Company"), gbc);

        txtDescription = new JTextArea(3, 30);
        txtDescription.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtDescription.setLineWrap(true);
        txtDescription.setWrapStyleWord(true);
        txtDescription.setEditable(false);
        txtDescription.setBackground(new Color(248, 248, 248));
        txtDescription.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));
        JScrollPane descScroll = new JScrollPane(txtDescription);
        descScroll.setBorder(null);
        descScroll.setPreferredSize(new Dimension(400, 72));

        gbc.gridx=1; gbc.gridwidth=3; gbc.weightx=1.0;
        formPanel.add(descScroll, gbc);
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
        btnBack.addActionListener(e -> cardLayout.show(mainContent, "companyDashboard"));

        return card;
    }

    private JPanel buildLogoCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(24, 40, 24, 40)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel logoTitle = new JLabel("Company Logo");
        logoTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        logoTitle.setForeground(new Color(40, 40, 40));
        logoTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 12, 0));
        card.add(logoTitle, BorderLayout.NORTH);

        JPanel uploadArea = new JPanel(new BorderLayout());
        uploadArea.setBackground(UPLOAD_BG);
        uploadArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UPLOAD_BORDER, 1, true),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)
        ));

        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(UPLOAD_BG);

        logoIcon = new JLabel("🖼", JLabel.CENTER);
        logoIcon.setFont(new Font("Segoe UI", Font.PLAIN, 32));
        logoIcon.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblLogoName = new JLabel("No logo uploaded yet", JLabel.CENTER);
        lblLogoName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblLogoName.setForeground(new Color(120, 120, 120));
        lblLogoName.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblLogoName.setBorder(BorderFactory.createEmptyBorder(6, 0, 0, 0));

        info.add(logoIcon);
        info.add(lblLogoName);
        uploadArea.add(info, BorderLayout.CENTER);
        card.add(uploadArea, BorderLayout.CENTER);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        btnRow.setBackground(CARD_BG);
        btnRow.setBorder(BorderFactory.createEmptyBorder(14, 0, 0, 0));

        btnUploadLogo = styledButton("Upload Logo", new Color(70, 130, 180));
        btnUploadLogo.setPreferredSize(new Dimension(135, 34));
        btnRemoveLogo = styledButton("Remove", new Color(40, 42, 44));
        btnRemoveLogo.setPreferredSize(new Dimension(110, 34));
        btnRemoveLogo.setEnabled(false);

        JLabel hint = new JLabel("  Accepted: PNG, JPG, SVG  |  Recommended: 200×200 px");
        hint.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        hint.setForeground(new Color(160, 160, 160));

        btnRow.add(btnUploadLogo);
        btnRow.add(btnRemoveLogo);
        btnRow.add(hint);
        card.add(btnRow, BorderLayout.SOUTH);

        btnUploadLogo.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Select Company Logo");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Image Files (PNG, JPG, SVG)", "png", "jpg", "jpeg", "svg"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File f = chooser.getSelectedFile();
                long kb = f.length() / 1024;
                uploadedLogo = f;
                logoIcon.setText("🖼");
                lblLogoName.setText("📎  " + f.getName() + "  (" + kb + " KB)");
                lblLogoName.setForeground(new Color(40, 40, 40));
                lblLogoName.setFont(new Font("Segoe UI", Font.BOLD, 13));
                btnRemoveLogo.setEnabled(true);
                JOptionPane.showMessageDialog(this, "Logo uploaded: " + f.getName(),
                        "Upload Successful", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        btnRemoveLogo.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(this, "Remove the company logo?",
                    "Remove Logo", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                uploadedLogo = null;
                logoIcon.setText("🖼");
                lblLogoName.setText("No logo uploaded yet");
                lblLogoName.setForeground(new Color(120, 120, 120));
                lblLogoName.setFont(new Font("Segoe UI", Font.PLAIN, 13));
                btnRemoveLogo.setEnabled(false);
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

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(180, 34));
        c.setBackground(Color.WHITE);
        c.setEnabled(false);
        return c;
    }

    private JButton styledButton(String text, Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(115, 34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg); }
        });
        return btn;
    }

    public void loadData() {
        txtCompanyId.setText("C001");
        txtCompanyName.setText("TechCorp Mauritius");
        txtEmail.setText("info@techcorp.mu");
        txtPhone.setText("52341234");
        txtWebsite.setText("www.techcorp.mu");
        txtAddress.setText("Cybercity, Ebene, Mauritius");
        txtRegNumber.setText("MU-BRN-20210045");
        cmbIndustry.setSelectedItem("Technology");
        cmbSize.setSelectedItem("51–200");
        cmbCountry.setSelectedItem("Mauritius");
        txtDescription.setText("TechCorp Mauritius is a leading software development company " +
                "based in Ebene Cybercity. We specialise in enterprise solutions, cloud platforms, " +
                "and digital transformation services across the African and Indian Ocean region.");
    }

    public void enableEdit() {
        txtCompanyName.setEditable(true);
        txtEmail.setEditable(true);
        txtPhone.setEditable(true);
        txtWebsite.setEditable(true);
        txtAddress.setEditable(true);
        txtDescription.setEditable(true);
        txtDescription.setBackground(Color.WHITE);
        cmbIndustry.setEnabled(true);
        cmbSize.setEnabled(true);
        cmbCountry.setEnabled(true);
        btnUpdate.setEnabled(true);
        btnCancel.setEnabled(true);
        btnEdit.setEnabled(false);
    }

    public void disableEdit() {
        txtCompanyName.setEditable(false);
        txtEmail.setEditable(false);
        txtPhone.setEditable(false);
        txtWebsite.setEditable(false);
        txtAddress.setEditable(false);
        txtDescription.setEditable(false);
        txtDescription.setBackground(new Color(248, 248, 248));
        cmbIndustry.setEnabled(false);
        cmbSize.setEnabled(false);
        cmbCountry.setEnabled(false);
        btnUpdate.setEnabled(false);
        btnCancel.setEnabled(false);
        btnEdit.setEnabled(true);
    }

    public void updateProfile() {
        String name  = txtCompanyName.getText().trim();
        String email = txtEmail.getText().trim();
        String phone = txtPhone.getText().trim();
        if (name.isEmpty() || email.isEmpty() || phone.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Company Name, Email and Phone cannot be empty.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } else if ("Select Industry".equals(cmbIndustry.getSelectedItem())) {
            JOptionPane.showMessageDialog(this, "Please select an industry.",
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Company profile updated successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            disableEdit();
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Company Profile");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(980, 820);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);



            Companydashboard dashboard = new Companydashboard(mainContent, cardLayout);
            mainContent.add(dashboard, "companyDashboard");
            mainContent.add(new CompanyProfile(cardLayout, mainContent), "companyProfile");

            cardLayout.show(mainContent, "companyProfile");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}