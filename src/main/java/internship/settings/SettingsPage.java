package internship.settings;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.ChangeEvent;
import java.awt.*;
import java.awt.event.*;
import internship.dashboard.StudentDashboard;
import internship.companydashboard.Companydashboard;
import internship.admindashboard.Admindashboard;

public class SettingsPage extends JPanel {

    private static final Color CONTENT_BG  = new Color(240, 240, 240);
    private static final Color CARD_BG     = Color.WHITE;
    private static final Color HEADER_BG   = new Color(200, 60, 50);
    private static final Color ACCENT      = new Color(200, 60, 50);
    private static final Color LABEL_COLOR = new Color(80, 80, 80);
    private static final Color BTN_GREEN   = new Color(60, 160, 80);
    private static final Color BTN_ORANGE  = new Color(200, 120, 40);
    private static final Color BTN_BACK    = new Color(60, 63, 65);

    private final String callerRole;
    private final CardLayout cardLayout;
    private final JPanel mainContent;

    private String selectedTheme = "Light";
    private JPanel themeLight, themeDark, themeSystem;

    private JComboBox<String> cmbLanguage, cmbDateFormat, cmbTimeZone;
    private JCheckBox chkEmail, chkApp, chkSMS;
    private JComboBox<String> cmbFontSize;
    private JSlider sldRows;
    private JLabel lblRowsVal;
    private JCheckBox chkAutoSave, chkCompact, chkTips;

    public SettingsPage(String callerRole, CardLayout cardLayout, JPanel mainContent) {
        this.callerRole = callerRole;
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(CONTENT_BG);

        add(buildContent(), BorderLayout.CENTER);
    }

    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);

        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setBackground(CONTENT_BG);
        pageHeader.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));

        JLabel pageTitle = new JLabel("System Settings");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(new Color(40, 40, 40));
        pageHeader.add(pageTitle, BorderLayout.WEST);

        wrapper.add(pageHeader, BorderLayout.NORTH);

        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(CONTENT_BG);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        scrollContent.add(buildThemeCard());
        scrollContent.add(Box.createVerticalStrut(14));
        scrollContent.add(buildLanguageCard());
        scrollContent.add(Box.createVerticalStrut(14));
        scrollContent.add(buildNotificationsCard());
        scrollContent.add(Box.createVerticalStrut(14));
        scrollContent.add(buildDisplayCard());
        scrollContent.add(Box.createVerticalStrut(14));
        scrollContent.add(buildActionsCard());

        JScrollPane scroll = new JScrollPane(scrollContent);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(14);
        wrapper.add(scroll, BorderLayout.CENTER);
        return wrapper;
    }

    private JPanel buildThemeCard() {
        JPanel card = card();
        card.add(sectionHeader("Appearance & Theme"), BorderLayout.NORTH);

        JPanel body = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 12));
        body.setBackground(CARD_BG);

        themeLight  = themeTile("Light",  new Color(240, 240, 240), new Color(60, 63, 65),   "Light");
        themeDark   = themeTile("Dark",   new Color(45,  47,  49),  new Color(200, 200, 200), "Dark");
        themeSystem = themeTile("System", new Color(180, 200, 230), new Color(50,  80,  130), "System");

        body.add(themeLight);
        body.add(themeDark);
        body.add(themeSystem);
        refreshThemeBorders();
        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel themeTile(String label, Color bg, Color textColor, String key) {
        JPanel tile = new JPanel(new BorderLayout());
        tile.setPreferredSize(new Dimension(175, 108));
        tile.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tile.setBorder(new LineBorder(new Color(210, 210, 210), 2, true));

        JPanel preview = new JPanel(new BorderLayout());
        preview.setBackground(bg);
        preview.setPreferredSize(new Dimension(175, 74));

        JPanel miniSide = new JPanel();
        miniSide.setBackground(bg.darker());
        miniSide.setPreferredSize(new Dimension(42, 74));
        preview.add(miniSide, BorderLayout.WEST);

        JPanel miniBody = new JPanel();
        miniBody.setLayout(new BoxLayout(miniBody, BoxLayout.Y_AXIS));
        miniBody.setBackground(bg);
        miniBody.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (int i = 0; i < 3; i++) {
            JPanel line = new JPanel();
            line.setBackground(bg.darker());
            int w = i == 0 ? 55 : 85;
            line.setMaximumSize(new Dimension(w, 6));
            line.setPreferredSize(new Dimension(w, 6));
            miniBody.add(line);
            miniBody.add(Box.createVerticalStrut(6));
        }
        JPanel topBar = new JPanel();
        topBar.setBackground(ACCENT);
        topBar.setPreferredSize(new Dimension(0, 6));
        preview.add(topBar, BorderLayout.NORTH);
        preview.add(miniBody, BorderLayout.CENTER);

        JLabel lbl = new JLabel(label, JLabel.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lbl.setForeground(textColor);
        lbl.setBackground(CARD_BG);
        lbl.setOpaque(true);
        lbl.setBorder(BorderFactory.createEmptyBorder(6, 0, 6, 0));

        tile.add(preview, BorderLayout.CENTER);
        tile.add(lbl, BorderLayout.SOUTH);

        tile.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                selectedTheme = key;
                refreshThemeBorders();
            }
        });
        return tile;
    }

    private void refreshThemeBorders() {
        themeLight .setBorder(new LineBorder(selectedTheme.equals("Light")  ? ACCENT : new Color(210,210,210), selectedTheme.equals("Light")  ? 2 : 1, true));
        themeDark  .setBorder(new LineBorder(selectedTheme.equals("Dark")   ? ACCENT : new Color(210,210,210), selectedTheme.equals("Dark")   ? 2 : 1, true));
        themeSystem.setBorder(new LineBorder(selectedTheme.equals("System") ? ACCENT : new Color(210,210,210), selectedTheme.equals("System") ? 2 : 1, true));
    }

    private JPanel buildLanguageCard() {
        JPanel card = card();
        card.add(sectionHeader("Language & Region"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD_BG);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 0, 10, 28);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        cmbLanguage   = combo("English","French","Spanish","German","Chinese","Arabic","Portuguese","Hindi");
        cmbDateFormat = combo("DD/MM/YYYY","MM/DD/YYYY","YYYY-MM-DD","DD-MMM-YYYY");
        cmbTimeZone   = combo("UTC+00:00 (London)","UTC+01:00 (Paris)","UTC+02:00 (Cairo)",
                "UTC+04:00 (Mauritius)","UTC+05:30 (India)","UTC-05:00 (New York)","UTC-08:00 (Los Angeles)");
        cmbTimeZone.setSelectedItem("UTC+04:00 (Mauritius)");

        g.gridx=0; g.gridy=0; g.weightx=0.18; form.add(fLabel("Display Language"), g);
        g.gridx=1; g.weightx=0.32; form.add(cmbLanguage, g);
        g.gridx=2; g.weightx=0.18; form.add(fLabel("Date Format"), g);
        g.gridx=3; g.weightx=0.32; form.add(cmbDateFormat, g);

        g.gridx=0; g.gridy=1; g.weightx=0.18; form.add(fLabel("Time Zone"), g);
        g.gridx=1; g.weightx=0.32; form.add(cmbTimeZone, g);

        card.add(form, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildNotificationsCard() {
        JPanel card = card();
        card.add(sectionHeader("Notification Preferences"), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(CARD_BG);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(8, 0, 8, 16);
        g.anchor = GridBagConstraints.WEST;

        chkEmail = new JCheckBox(); chkEmail.setSelected(true);
        chkApp   = new JCheckBox(); chkApp.setSelected(true);
        chkSMS   = new JCheckBox(); chkSMS.setSelected(false);

        g.gridx=0; g.gridy=0; body.add(checkCard(chkEmail, "Email Notifications",  "Receive updates and alerts via email"), g);
        g.gridx=1;             body.add(checkCard(chkApp,   "In-App Notifications", "Show alerts and pop-ups within the system"), g);
        g.gridx=0; g.gridy=1;  body.add(checkCard(chkSMS,   "SMS Notifications",    "Text messages for critical updates"), g);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildDisplayCard() {
        JPanel card = card();
        card.add(sectionHeader("Display & Accessibility"), BorderLayout.NORTH);

        JPanel body = new JPanel(new GridBagLayout());
        body.setBackground(CARD_BG);
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(10, 0, 10, 28);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;

        cmbFontSize = combo("Small (12px)","Medium (14px)","Large (16px)","Extra Large (18px)");
        cmbFontSize.setSelectedIndex(1);

        sldRows = new JSlider(5, 50, 10);
        sldRows.setBackground(CARD_BG);
        sldRows.setMajorTickSpacing(10);
        sldRows.setMinorTickSpacing(5);
        sldRows.setPaintTicks(true);
        sldRows.setPaintLabels(true);
        sldRows.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sldRows.setPreferredSize(new Dimension(270, 52));

        lblRowsVal = new JLabel("10 rows");
        lblRowsVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblRowsVal.setForeground(ACCENT);
        sldRows.addChangeListener((ChangeEvent e) ->
                lblRowsVal.setText(sldRows.getValue() + " rows"));

        g.gridx=0; g.gridy=0; g.weightx=0.18; body.add(fLabel("Font Size"), g);
        g.gridx=1; g.weightx=0.82; body.add(cmbFontSize, g);

        g.gridx=0; g.gridy=1; g.weightx=0.18; g.fill=GridBagConstraints.NONE;
        body.add(fLabel("Rows per Page"), g);
        JPanel sliderRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        sliderRow.setBackground(CARD_BG);
        sliderRow.add(sldRows);
        sliderRow.add(lblRowsVal);
        g.gridx=1; g.weightx=0.82; g.fill=GridBagConstraints.HORIZONTAL;
        body.add(sliderRow, g);

        chkAutoSave = new JCheckBox(); chkAutoSave.setSelected(true);
        chkCompact  = new JCheckBox(); chkCompact.setSelected(false);
        chkTips     = new JCheckBox(); chkTips.setSelected(true);

        g.insets = new Insets(10, 0, 6, 16);
        g.fill = GridBagConstraints.NONE;
        g.gridx=0; g.gridy=2; g.weightx=0;
        body.add(checkCard(chkAutoSave, "Auto-Save Changes",  "Automatically save edits without prompting"), g);
        g.gridx=1;
        body.add(checkCard(chkCompact,  "Compact Table View", "Reduce row height for denser data display"), g);
        g.gridx=0; g.gridy=3;
        body.add(checkCard(chkTips,     "Show Usage Tips",    "Display helpful hints throughout the UI"), g);

        card.add(body, BorderLayout.CENTER);
        return card;
    }

    private JPanel buildActionsCard() {
        JPanel card = card();

        JPanel row = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        row.setBackground(CARD_BG);

        JButton btnBack  = btn("< Back to Dashboard", BTN_BACK);
        btnBack.setPreferredSize(new Dimension(170, 34));
        btnBack.addActionListener(e -> {
            if ("Student".equalsIgnoreCase(callerRole)) {
                cardLayout.show(mainContent, "studentDashboard");
            } else if ("Company".equalsIgnoreCase(callerRole)) {
                cardLayout.show(mainContent, "companyDashboard");
            } else if ("Admin".equalsIgnoreCase(callerRole)) {
                cardLayout.show(mainContent, "adminDashboard");
            }
        });

        JButton btnReset = btn("Reset Defaults", BTN_ORANGE);
        btnReset.setPreferredSize(new Dimension(140, 34));
        btnReset.addActionListener(e -> resetDefaults());

        JButton btnSave  = btn("Save Settings", BTN_GREEN);
        btnSave.setPreferredSize(new Dimension(130, 34));
        btnSave.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                    "Settings saved!");
        });

        row.add(btnBack);
        row.add(btnReset);
        row.add(btnSave);
        card.add(row, BorderLayout.EAST);

        return card;
    }

    private void resetDefaults() {
        selectedTheme = "Light";
        refreshThemeBorders();

        cmbLanguage.setSelectedItem("English");
        cmbDateFormat.setSelectedIndex(0);
        cmbTimeZone.setSelectedItem("UTC+04:00 (Mauritius)");
        cmbFontSize.setSelectedIndex(1);
        sldRows.setValue(10);

        chkEmail.setSelected(true);
        chkApp.setSelected(true);
        chkSMS.setSelected(false);
        chkAutoSave.setSelected(true);
        chkCompact.setSelected(false);
        chkTips.setSelected(true);
    }

    private JPanel card() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(24, 32, 24, 32)
        ));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return card;
    }

    private JPanel sectionHeader(String title) {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CARD_BG);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        JLabel lbl = new JLabel(title);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lbl.setForeground(new Color(40, 40, 40));
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));
        wrapper.add(lbl, BorderLayout.NORTH);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        wrapper.add(sep, BorderLayout.SOUTH);
        return wrapper;
    }

    private JLabel fLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lbl.setForeground(LABEL_COLOR);
        return lbl;
    }

    private JComboBox<String> combo(String... items) {
        JComboBox<String> c = new JComboBox<>(items);
        c.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        c.setPreferredSize(new Dimension(200, 34));
        c.setBackground(Color.WHITE);
        return c;
    }

    private JPanel checkCard(JCheckBox chk, String title, String subtitle) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_BG);
        panel.setPreferredSize(new Dimension(255, 56));
        panel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateCheckCardBorder(panel, chk.isSelected());

        JPanel textBlock = new JPanel();
        textBlock.setLayout(new BoxLayout(textBlock, BoxLayout.Y_AXIS));
        textBlock.setBackground(CARD_BG);
        JLabel tLbl = new JLabel(title);
        tLbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        tLbl.setForeground(new Color(40, 40, 40));
        JLabel sLbl = new JLabel(subtitle);
        sLbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        sLbl.setForeground(new Color(130, 130, 130));
        textBlock.add(tLbl);
        textBlock.add(sLbl);

        chk.setBackground(CARD_BG);
        chk.setFocusPainted(false);
        chk.putClientProperty("cardPanel", panel);

        panel.add(chk, BorderLayout.WEST);
        panel.add(textBlock, BorderLayout.CENTER);

        panel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                chk.setSelected(!chk.isSelected());
                updateCheckCardBorder(panel, chk.isSelected());
            }
        });
        return panel;
    }

    private void updateCheckCardBorder(JPanel panel, boolean selected) {
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(selected ? ACCENT : new Color(220, 220, 220), selected ? 2 : 1, true),
                BorderFactory.createEmptyBorder(selected ? 7 : 8, 10, selected ? 7 : 8, 10)
        ));
    }

    private JButton btn(String text, Color bg) {
        JButton b = new JButton(text);
        b.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        b.setForeground(Color.WHITE);
        b.setBackground(bg);
        b.setFocusPainted(false);
        b.setBorderPainted(false);
        b.setOpaque(true);
        b.setPreferredSize(new Dimension(110, 34));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { b.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e)  { b.setBackground(bg); }
        });
        return b;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Settings Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(860, 800);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            JPanel dashboard = new JPanel();
            dashboard.setBackground(new Color(230, 240, 250));
            dashboard.add(new JLabel("Dashboard Page"));
            mainContent.add(dashboard, "studentDashboard");
            mainContent.add(dashboard, "companyDashboard");
            mainContent.add(dashboard, "adminDashboard");

            mainContent.add(new StudentDashboard(cardLayout, mainContent), "studentDashboard");

            cardLayout.show(mainContent, "studentSettings");

            mainContent.add(new StudentDashboard(cardLayout, mainContent), "companyDashboard");
            mainContent.add(new StudentDashboard(cardLayout, mainContent), "adminDashboard");

            mainContent.add(new SettingsPage("Student", cardLayout, mainContent), "studentSettings");
            mainContent.add(new SettingsPage("Company", cardLayout, mainContent), "companySettings");
            mainContent.add(new SettingsPage("Admin", cardLayout, mainContent), "adminSettings");

            frame.setContentPane(mainContent);
            frame.setVisible(true);

            cardLayout.show(mainContent, "settings");
        });
    }
}

