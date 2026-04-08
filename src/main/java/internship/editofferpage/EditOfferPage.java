package internship.editofferpage;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import internship.companydashboard.Companydashboard;

/**
 * Edit Internship Offer Page
 * Converted from JFrame to JPanel
 */
public class EditOfferPage extends JPanel {

    // ===================================================
    // CARD NAVIGATION
    // ===================================================
    private CardLayout cardLayout;
    private JPanel mainContent;

    private static final String EDIT_PAGE = "EDIT_PAGE";
    private static final String DASHBOARD_PAGE = "DASHBOARD_PAGE";

    // ===================================================
    // THEME
    // ===================================================
    private static final Color PRIMARY      = new Color(30, 90, 160);
    private static final Color PRIMARY_HOV  = new Color(20, 65, 120);
    private static final Color ACCENT       = new Color(0, 168, 120);
    private static final Color WARNING      = new Color(210, 120, 0);
    private static final Color DANGER       = new Color(200, 45, 45);
    private static final Color BG           = new Color(245, 247, 252);
    private static final Color CARD_BG      = Color.WHITE;
    private static final Color SECTION_HDR  = new Color(248, 249, 253);
    private static final Color TEXT_DARK    = new Color(30, 35, 50);
    private static final Color TEXT_MUTED   = new Color(110, 120, 140);
    private static final Color BORDER_CLR   = new Color(210, 215, 230);
    private static final Color BORDER_FOCUS = PRIMARY;
    private static final Color BORDER_ERR   = DANGER;

    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SECTION  = new Font("Segoe UI", Font.BOLD, 13);
    private static final Font FONT_LABEL    = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_HINT     = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_FIELD    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN      = new Font("Segoe UI", Font.BOLD, 13);

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ===================================================
    // FORM FIELDS
    // ===================================================
    private JTextField txtTitle;
    private JTextArea txtDescription;
    private JComboBox<String> cmbIndustry;
    private JComboBox<String> cmbLocation;
    private JComboBox<String> cmbWorkMode;
    private JComboBox<String> cmbDuration;
    private JTextField txtStipend;
    private JTextField txtVacancies;
    private JTextField txtDeadline;
    private JTextArea txtRequirements;
    private JComboBox<String> cmbStatus;

    // Inline error labels
    private JLabel errTitle, errStipend, errVacancies, errDeadline;

    // Action buttons
    private JButton btnSave;
    private JButton btnDiscard;
    private JButton btnBack;

    // Change-tracking
    private String[] originalValues;
    private boolean isDirty = false;

    // ===================================================
    // MOCK DATA
    // ===================================================
    private static final String MOCK_TITLE        = "Software Engineering Intern";
    private static final String MOCK_DESC         =
            "You will work closely with our engineering team to design, develop, and " +
                    "test scalable web applications. Responsibilities include writing clean, " +
                    "maintainable code, participating in code reviews, and contributing to " +
                    "sprint planning sessions.";
    private static final String MOCK_INDUSTRY     = "IT & Software";
    private static final String MOCK_LOCATION     = "Ebene";
    private static final String MOCK_MODE         = "Hybrid";
    private static final String MOCK_DURATION     = "6 Months";
    private static final String MOCK_STIPEND      = "15000";
    private static final String MOCK_VACANCIES    = "3";
    private static final String MOCK_DEADLINE     = "30/06/2026";
    private static final String MOCK_REQUIREMENTS =
            "- Currently enrolled in a Bachelor's in Computer Science or related field\n" +
                    "- Knowledge of Java, Python, or JavaScript\n" +
                    "- Familiarity with Git version control\n" +
                    "- Strong problem-solving skills";
    private static final String MOCK_STATUS       = "Open";

    // ===================================================
    // CONSTRUCTOR
    // ===================================================
    public EditOfferPage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildMainArea(), BorderLayout.CENTER);
        add(buildBottomBar(), BorderLayout.SOUTH);

        populateFields();
        snapshotOriginal();
        wireChangeListeners();
    }

    // ===================================================
    // TOP BAR
    // ===================================================
    private JPanel buildTopBar() {
        JPanel bar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY, getWidth(), 0, new Color(10, 55, 120));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                g2.setColor(new Color(255, 255, 255, 14));
                g2.fillOval(getWidth() - 140, -70, 260, 260);
                g2.dispose();
            }
        };
        bar.setLayout(new BorderLayout());
        bar.setBorder(new EmptyBorder(14, 28, 14, 28));
        bar.setPreferredSize(new Dimension(0, 88));

        JPanel leftSide = new JPanel(new GridBagLayout());
        leftSide.setOpaque(false);
        GridBagConstraints lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.WEST;

        JPanel badge = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 35));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        badge.setOpaque(false);
        badge.setPreferredSize(new Dimension(46, 46));
        badge.setLayout(new GridBagLayout());

        JLabel badgeIcon = new JLabel("✏");
        badgeIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        badgeIcon.setForeground(Color.WHITE);
        badge.add(badgeIcon);

        JPanel titleBlock = new JPanel();
        titleBlock.setLayout(new BoxLayout(titleBlock, BoxLayout.Y_AXIS));
        titleBlock.setOpaque(false);

        JLabel title = new JLabel("Edit Internship Offer");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Update the details of an existing internship listing");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(new Color(180, 210, 255));

        titleBlock.add(title);
        titleBlock.add(Box.createVerticalStrut(3));
        titleBlock.add(sub);

        lc.gridx = 0;
        lc.gridy = 0;
        lc.insets = new Insets(0, 0, 0, 14);
        leftSide.add(badge, lc);

        lc.gridx = 1;
        lc.insets = new Insets(0, 0, 0, 0);
        leftSide.add(titleBlock, lc);

        JPanel companyChip = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 28));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        companyChip.setOpaque(false);
        companyChip.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 6));
        companyChip.setBorder(new EmptyBorder(0, 4, 0, 4));

        JLabel compIcon = new JLabel("🏢");
        compIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 14));
        JLabel compName = new JLabel("TechNova Ltd");
        compName.setFont(new Font("Segoe UI", Font.BOLD, 12));
        compName.setForeground(Color.WHITE);

        companyChip.add(compIcon);
        companyChip.add(compName);

        bar.add(leftSide, BorderLayout.WEST);
        bar.add(companyChip, BorderLayout.EAST);

        return bar;
    }

    // ===================================================
    // MAIN AREA
    // ===================================================
    private JScrollPane buildMainArea() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(BG);
        form.setBorder(new EmptyBorder(20, 24, 10, 24));

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1.0;
        gc.insets = new Insets(0, 0, 16, 0);
        int row = 0;

        gc.gridy = row++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        form.add(buildSectionHeader("📋  Basic Information"), gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 0, 4, 0);
        form.add(makeLabel("Job Title *"), gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 0, 2, 0);
        txtTitle = makeTextField();
        form.add(txtTitle, gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 0, 14, 0);
        errTitle = makeErrLabel();
        form.add(errTitle, gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 0, 4, 0);
        form.add(makeLabel("Job Description *"), gc);

        gc.gridy = row++;
        gc.weighty = 0.15;
        gc.insets = new Insets(0, 0, 14, 0);
        txtDescription = makeTextArea(4);
        JScrollPane descScroll = new JScrollPane(txtDescription);
        descScroll.setBorder(new LineBorder(BORDER_CLR, 1, true));
        form.add(descScroll, gc);
        gc.weighty = 0;

        gc.gridy = row++;
        gc.insets = new Insets(8, 0, 16, 0);
        form.add(buildSectionHeader("📌  Offer Details"), gc);

        gc.gridwidth = 1;
        gc.insets = new Insets(0, 0, 4, 0);

        gc.gridy = row;
        gc.gridx = 0;
        form.add(makeLabel("Industry *"), gc);
        gc.gridx = 1;
        form.add(makeLabel("Location *"), gc);

        row++;
        gc.gridy = row++;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 14, 8);
        cmbIndustry = makeCombo(new String[]{
                "IT & Software","Analytics","Marketing","Finance",
                "Human Resources","Design","Business","Legal","Logistics"});
        form.add(cmbIndustry, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 14, 0);
        cmbLocation = makeCombo(new String[]{
                "Port Louis","Ebene","Quatre Bornes","Rose Hill",
                "Curepipe","Baie du Tombeau","Remote"});
        form.add(cmbLocation, gc);

        gc.gridy = row;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 4, 8);
        form.add(makeLabel("Work Mode *"), gc);
        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 4, 0);
        form.add(makeLabel("Duration *"), gc);

        row++;
        gc.gridy = row++;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 14, 8);
        cmbWorkMode = makeCombo(new String[]{"On-site","Remote","Hybrid"});
        form.add(cmbWorkMode, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 14, 0);
        cmbDuration = makeCombo(new String[]{"3 Months","6 Months","12 Months"});
        form.add(cmbDuration, gc);

        gc.gridy = row++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets = new Insets(8, 0, 16, 0);
        form.add(buildSectionHeader("💰  Compensation & Recruitment"), gc);

        gc.gridwidth = 1;
        gc.gridy = row;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 4, 8);
        form.add(makeLabel("Monthly Stipend (Rs) *"), gc);
        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 4, 0);
        form.add(makeLabel("Number of Vacancies *"), gc);

        row++;
        gc.gridy = row++;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 2, 8);
        txtStipend = makeTextField();
        form.add(txtStipend, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 2, 0);
        txtVacancies = makeTextField();
        form.add(txtVacancies, gc);

        gc.gridy = row++;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 14, 8);
        errStipend = makeErrLabel();
        form.add(errStipend, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 14, 0);
        errVacancies = makeErrLabel();
        form.add(errVacancies, gc);

        gc.gridy = row;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 4, 8);
        form.add(makeLabel("Application Deadline (dd/MM/yyyy) *"), gc);
        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 4, 0);
        form.add(makeLabel("Listing Status *"), gc);

        row++;
        gc.gridy = row++;
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 2, 8);
        txtDeadline = makeTextField();
        form.add(txtDeadline, gc);

        gc.gridx = 1;
        gc.insets = new Insets(0, 8, 14, 0);
        cmbStatus = makeCombo(new String[]{"Open","Closed","Draft"});
        styleStatusCombo();
        form.add(cmbStatus, gc);

        gc.gridy = row++;
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.insets = new Insets(0, 0, 14, 0);
        errDeadline = makeErrLabel();
        form.add(errDeadline, gc);

        gc.gridy = row++;
        gc.insets = new Insets(8, 0, 16, 0);
        form.add(buildSectionHeader("📝  Requirements"), gc);

        gc.gridy = row++;
        gc.insets = new Insets(0, 0, 4, 0);
        form.add(makeLabel("Candidate Requirements"), gc);

        gc.gridy = row++;
        gc.weighty = 0.2;
        gc.insets = new Insets(0, 0, 24, 0);
        txtRequirements = makeTextArea(5);
        JScrollPane reqScroll = new JScrollPane(txtRequirements);
        reqScroll.setBorder(new LineBorder(BORDER_CLR, 1, true));
        form.add(reqScroll, gc);
        gc.weighty = 1.0;

        gc.gridy = row;
        gc.gridx = 0;
        gc.gridwidth = 2;
        form.add(Box.createVerticalGlue(), gc);

        JScrollPane scroll = new JScrollPane(form);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.getViewport().setBackground(BG);

        return scroll;
    }

    // ===================================================
    // BOTTOM BAR
    // ===================================================
    private JPanel buildBottomBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(CARD_BG);
        bar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, BORDER_CLR),
                new EmptyBorder(14, 24, 14, 24)));

        JLabel hint = new JLabel("Last edited: 01/04/2026  ·  by Admin");
        hint.setFont(FONT_HINT);
        hint.setForeground(TEXT_MUTED);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        btnPanel.setOpaque(false);

        btnBack = makeButton("← Back", CARD_BG, PRIMARY);
        btnBack.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY, 1, true),
                new EmptyBorder(9, 20, 9, 20)));

        btnDiscard = makeButton("Discard Changes", CARD_BG, DANGER);
        btnDiscard.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(DANGER, 1, true),
                new EmptyBorder(9, 20, 9, 20)));
        btnDiscard.setEnabled(false);

        btnSave = makeButton("💾  Save Changes", PRIMARY, Color.WHITE);
        btnSave.setEnabled(false);

        btnBack.addActionListener(e -> cardLayout.show(mainContent, "companyDashboard"));
        btnDiscard.addActionListener(e -> handleDiscard());
        btnSave.addActionListener(e -> handleSave());

        btnPanel.add(btnBack);
        btnPanel.add(btnDiscard);
        btnPanel.add(btnSave);

        bar.add(hint, BorderLayout.WEST);
        bar.add(btnPanel, BorderLayout.EAST);

        return bar;
    }

    // ===================================================
    // POPULATE
    // ===================================================
    private void populateFields() {
        txtTitle.setText(MOCK_TITLE);
        txtDescription.setText(MOCK_DESC);
        selectCombo(cmbIndustry, MOCK_INDUSTRY);
        selectCombo(cmbLocation, MOCK_LOCATION);
        selectCombo(cmbWorkMode, MOCK_MODE);
        selectCombo(cmbDuration, MOCK_DURATION);
        txtStipend.setText(MOCK_STIPEND);
        txtVacancies.setText(MOCK_VACANCIES);
        txtDeadline.setText(MOCK_DEADLINE);
        txtRequirements.setText(MOCK_REQUIREMENTS);
        selectCombo(cmbStatus, MOCK_STATUS);
    }

    private void selectCombo(JComboBox<String> cmb, String value) {
        for (int i = 0; i < cmb.getItemCount(); i++) {
            if (cmb.getItemAt(i).equals(value)) {
                cmb.setSelectedIndex(i);
                return;
            }
        }
    }

    // ===================================================
    // CHANGE TRACKING
    // ===================================================
    private void snapshotOriginal() {
        originalValues = currentValues();
    }

    private String[] currentValues() {
        return new String[]{
                txtTitle.getText(),
                txtDescription.getText(),
                (String) cmbIndustry.getSelectedItem(),
                (String) cmbLocation.getSelectedItem(),
                (String) cmbWorkMode.getSelectedItem(),
                (String) cmbDuration.getSelectedItem(),
                txtStipend.getText(),
                txtVacancies.getText(),
                txtDeadline.getText(),
                txtRequirements.getText(),
                (String) cmbStatus.getSelectedItem()
        };
    }

    private void checkDirty() {
        String[] current = currentValues();
        isDirty = false;
        for (int i = 0; i < originalValues.length; i++) {
            if (!originalValues[i].equals(current[i])) {
                isDirty = true;
                break;
            }
        }
        btnSave.setEnabled(isDirty);
        btnDiscard.setEnabled(isDirty);
    }

    private void wireChangeListeners() {
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { checkDirty(); }
            public void removeUpdate(DocumentEvent e) { checkDirty(); }
            public void changedUpdate(DocumentEvent e) { checkDirty(); }
        };

        txtTitle.getDocument().addDocumentListener(dl);
        txtDescription.getDocument().addDocumentListener(dl);
        txtStipend.getDocument().addDocumentListener(dl);
        txtVacancies.getDocument().addDocumentListener(dl);
        txtDeadline.getDocument().addDocumentListener(dl);
        txtRequirements.getDocument().addDocumentListener(dl);

        ActionListener al = e -> checkDirty();
        cmbIndustry.addActionListener(al);
        cmbLocation.addActionListener(al);
        cmbWorkMode.addActionListener(al);
        cmbDuration.addActionListener(al);
        cmbStatus.addActionListener(e -> {
            styleStatusCombo();
            checkDirty();
        });
    }

    // ===================================================
    // VALIDATION
    // ===================================================
    private boolean validateForm() {
        boolean valid = true;
        clearErrors();

        if (txtTitle.getText().trim().isEmpty()) {
            showErr(errTitle, txtTitle, "Job title is required.");
            valid = false;
        } else if (txtTitle.getText().trim().length() < 5) {
            showErr(errTitle, txtTitle, "Title must be at least 5 characters.");
            valid = false;
        }

        String stipStr = txtStipend.getText().trim();
        if (stipStr.isEmpty()) {
            showErr(errStipend, txtStipend, "Stipend is required.");
            valid = false;
        } else {
            try {
                int s = Integer.parseInt(stipStr);
                if (s < 0) {
                    showErr(errStipend, txtStipend, "Stipend cannot be negative.");
                    valid = false;
                }
            } catch (NumberFormatException ex) {
                showErr(errStipend, txtStipend, "Stipend must be a whole number.");
                valid = false;
            }
        }

        String vacStr = txtVacancies.getText().trim();
        if (vacStr.isEmpty()) {
            showErr(errVacancies, txtVacancies, "Number of vacancies is required.");
            valid = false;
        } else {
            try {
                int v = Integer.parseInt(vacStr);
                if (v < 1) {
                    showErr(errVacancies, txtVacancies, "Must have at least 1 vacancy.");
                    valid = false;
                }
            } catch (NumberFormatException ex) {
                showErr(errVacancies, txtVacancies, "Vacancies must be a whole number.");
                valid = false;
            }
        }

        String dl = txtDeadline.getText().trim();
        if (dl.isEmpty()) {
            showErr(errDeadline, txtDeadline, "Deadline is required.");
            valid = false;
        } else {
            try {
                LocalDate d = LocalDate.parse(dl, DATE_FMT);
                if (!d.isAfter(LocalDate.now())) {
                    showErr(errDeadline, txtDeadline, "Deadline must be a future date.");
                    valid = false;
                }
            } catch (DateTimeParseException ex) {
                showErr(errDeadline, txtDeadline, "Use format dd/MM/yyyy.");
                valid = false;
            }
        }

        if (txtDescription.getText().trim().isEmpty()) {
            highlightError(txtDescription);
            valid = false;
        }

        return valid;
    }

    private void showErr(JLabel errLbl, JComponent field, String msg) {
        errLbl.setText("⚠  " + msg);
        errLbl.setVisible(true);
        highlightError(field);
    }

    private void highlightError(JComponent c) {
        c.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_ERR, 2, true),
                new EmptyBorder(5, 9, 5, 9)));
    }

    private void clearErrors() {
        for (JLabel e : new JLabel[]{errTitle, errStipend, errVacancies, errDeadline}) {
            e.setVisible(false);
        }

        for (JTextField f : new JTextField[]{txtTitle, txtStipend, txtVacancies, txtDeadline}) {
            resetFieldBorder(f);
        }

        resetAreaBorder(txtDescription);
    }

    // ===================================================
    // BUTTON ACTIONS
    // ===================================================
    private void handleSave() {
        clearErrors();
        if (!validateForm()) return;

        String summary = String.format(
                "<html><b>Review changes before saving:</b><br><br>" +
                        "<table>" +
                        "<tr><td><b>Title:&nbsp;</b></td><td>%s</td></tr>" +
                        "<tr><td><b>Stipend:&nbsp;</b></td><td>Rs %s / month</td></tr>" +
                        "<tr><td><b>Vacancies:&nbsp;</b></td><td>%s</td></tr>" +
                        "<tr><td><b>Deadline:&nbsp;</b></td><td>%s</td></tr>" +
                        "<tr><td><b>Status:&nbsp;</b></td><td>%s</td></tr>" +
                        "</table></html>",
                txtTitle.getText().trim(),
                txtStipend.getText().trim(),
                txtVacancies.getText().trim(),
                txtDeadline.getText().trim(),
                cmbStatus.getSelectedItem()
        );

        int confirm = JOptionPane.showConfirmDialog(
                this, summary, "Confirm Save",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Internship offer updated successfully!",
                    "Saved", JOptionPane.INFORMATION_MESSAGE);

            snapshotOriginal();
            isDirty = false;
            btnSave.setEnabled(false);
            btnDiscard.setEnabled(false);

            // Optional navigation after save
            cardLayout.show(mainContent, DASHBOARD_PAGE);
        }
    }

    private void handleDiscard() {
        if (!isDirty) return;

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Discard all unsaved changes and restore the original values?",
                "Discard Changes",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (confirm == JOptionPane.YES_OPTION) {
            populateFields();
            clearErrors();
            snapshotOriginal();
            isDirty = false;
            btnSave.setEnabled(false);
            btnDiscard.setEnabled(false);
        }
    }

    private void handleBack() {
        if (isDirty) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "You have unsaved changes.\nLeave without saving?",
                    "Unsaved Changes",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );

            if (choice != JOptionPane.YES_OPTION) return;
        }

        cardLayout.show(mainContent, DASHBOARD_PAGE);
    }

    // ===================================================
    // UI HELPERS
    // ===================================================
    private JPanel buildSectionHeader(String text) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(SECTION_HDR);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.setColor(PRIMARY);
                g2.fillRoundRect(0, 0, 4, getHeight(), 4, 4);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(8, 14, 8, 14));

        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(PRIMARY);
        p.add(lbl, BorderLayout.WEST);
        return p;
    }

    private JLabel makeLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JLabel makeErrLabel() {
        JLabel l = new JLabel();
        l.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        l.setForeground(DANGER);
        l.setVisible(false);
        return l;
    }

    private JTextField makeTextField() {
        JTextField f = new JTextField() {
            @Override
            public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 36);
            }
        };
        f.setFont(FONT_FIELD);
        f.setForeground(TEXT_DARK);
        f.setBackground(new Color(250, 251, 255));
        resetFieldBorder(f);
        addFocusBorder(f);
        return f;
    }

    private JTextArea makeTextArea(int rows) {
        JTextArea a = new JTextArea(rows, 0);
        a.setFont(FONT_FIELD);
        a.setForeground(TEXT_DARK);
        a.setBackground(new Color(250, 251, 255));
        a.setLineWrap(true);
        a.setWrapStyleWord(true);
        a.setBorder(new EmptyBorder(8, 10, 8, 10));
        return a;
    }

    private JComboBox<String> makeCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD);
        cb.setForeground(TEXT_DARK);
        cb.setBackground(Color.WHITE);
        cb.setBorder(new LineBorder(BORDER_CLR, 1, true));
        return cb;
    }

    private void styleStatusCombo() {
        if (cmbStatus == null) return;
        String sel = (String) cmbStatus.getSelectedItem();

        if ("Open".equals(sel)) {
            cmbStatus.setForeground(ACCENT);
        } else if ("Closed".equals(sel)) {
            cmbStatus.setForeground(DANGER);
        } else {
            cmbStatus.setForeground(WARNING);
        }
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

                Color draw = bg;
                if (!isEnabled()) {
                    draw = new Color(200, 202, 210);
                } else if (getModel().isPressed()) {
                    draw = bg.darker();
                } else if (getModel().isRollover()
                        && (bg.equals(PRIMARY) || bg.equals(ACCENT))) {
                    draw = PRIMARY_HOV;
                }

                g2.setColor(draw);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(10, 24, 10, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    private void resetFieldBorder(JTextField f) {
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
    }

    private void resetAreaBorder(JTextArea a) {
        a.setBorder(new EmptyBorder(8, 10, 8, 10));
    }

    private void addFocusBorder(JTextField f) {
        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_FOCUS, 2, true),
                        new EmptyBorder(5, 9, 5, 9)));
            }

            @Override
            public void focusLost(FocusEvent e) {
                resetFieldBorder(f);
            }
        });
    }

    // ===================================================
    // SAMPLE DASHBOARD PANEL
    // ===================================================
    static class DashboardPage extends JPanel {
        public DashboardPage(CardLayout cardLayout, JPanel mainContent) {
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);

            JLabel lbl = new JLabel("Company Dashboard", SwingConstants.CENTER);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 28));

            JButton btnGoToEdit = new JButton("Go to Edit Offer Page");
            btnGoToEdit.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btnGoToEdit.addActionListener(e -> cardLayout.show(mainContent, EDIT_PAGE));

            JPanel center = new JPanel(new BorderLayout());
            center.setOpaque(false);
            center.add(lbl, BorderLayout.CENTER);

            JPanel south = new JPanel();
            south.setOpaque(false);
            south.add(btnGoToEdit);

            add(center, BorderLayout.CENTER);
            add(south, BorderLayout.SOUTH);
        }
    }

    // ===================================================
    // MAIN METHOD WITH NAVIGATION
    // ===================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1120, 740);
            frame.setMinimumSize(new Dimension(860, 580));

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            DashboardPage dashboardPage = new DashboardPage(cardLayout, mainContent);
            EditOfferPage editOfferPage = new EditOfferPage(cardLayout, mainContent);

            mainContent.add(dashboardPage, DASHBOARD_PAGE);
            mainContent.add(editOfferPage, EDIT_PAGE);
            mainContent.add(new Companydashboard(mainContent, cardLayout), "companyDashboard");
            mainContent.add(new EditOfferPage(cardLayout, mainContent), "EditOffer");

            frame.setContentPane(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Start on dashboard
            cardLayout.show(mainContent, DASHBOARD_PAGE);
        });
    }
}