package internship.searchpage;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import internship.dashboard.StudentDashboard;

/**
 * Internship Search Page
 * Converted from JFrame -> JPanel
 */
public class SearchPage extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContent;

    // ===================================================
    // THEME
    // ===================================================
    private static final Color PRIMARY      = new Color(30, 90, 160);
    private static final Color PRIMARY_HOV  = new Color(20, 65, 120);
    private static final Color ACCENT       = new Color(0, 168, 120);
    private static final Color DANGER       = new Color(210, 50, 50);
    private static final Color BG           = new Color(245, 247, 252);
    private static final Color CARD_BG      = Color.WHITE;
    private static final Color TEXT_DARK    = new Color(30, 35, 50);
    private static final Color TEXT_MUTED   = new Color(110, 120, 140);
    private static final Color BORDER_CLR   = new Color(210, 215, 230);
    private static final Color ROW_ALT      = new Color(248, 250, 255);
    private static final Color ROW_SEL      = new Color(220, 234, 255);
    private static final Color HEADER_BG    = new Color(30, 90, 160);

    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_LABEL    = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_FIELD    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN      = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_TABLE_H  = new Font("Segoe UI", Font.BOLD, 12);
    private static final Font FONT_TABLE    = new Font("Segoe UI", Font.PLAIN, 12);

    // ===================================================
    // FILTER CONTROLS
    // ===================================================
    private JTextField txtSearch;
    private JComboBox<String> cmbIndustry;
    private JComboBox<String> cmbLocation;
    private JComboBox<String> cmbDuration;
    private JComboBox<String> cmbWorkMode;
    private JSlider sldStipend;
    private JLabel lblStipendVal;
    private JButton btnSearch;
    private JButton btnClear;

    // ===================================================
    // TABLE
    // ===================================================
    private JTable resultsTable;
    private DefaultTableModel tableModel;
    private JLabel lblResultCount;

    // ===================================================
    // MOCK DATA
    // ===================================================
    private static final String[][] ALL_INTERNSHIPS = {
            {"Software Engineering Intern",  "TechNova Ltd",        "IT & Software",    "Port Louis",   "On-site",  "3",  "15000", "Open"},
            {"Data Analyst Intern",          "DataBridge Co.",      "Analytics",        "Ebene",        "Hybrid",   "6",  "18000", "Open"},
            {"Marketing Intern",             "BlueWave Agency",     "Marketing",        "Quatre Bornes","Remote",   "3",  "8000",  "Open"},
            {"Finance Intern",               "Horizon Bank",        "Finance",          "Port Louis",   "On-site",  "6",  "12000", "Open"},
            {"HR Intern",                    "PeoplePlus HR",       "Human Resources",  "Rose Hill",    "On-site",  "3",  "7000",  "Closed"},
            {"Network Engineering Intern",   "NetCore Systems",     "IT & Software",    "Ebene",        "Hybrid",   "6",  "20000", "Open"},
            {"Graphic Design Intern",        "PixelCraft Studio",   "Design",           "Curepipe",     "Remote",   "3",  "9000",  "Open"},
            {"Cybersecurity Intern",         "SecureNet MRU",       "IT & Software",    "Port Louis",   "On-site",  "6",  "22000", "Open"},
            {"Accounting Intern",            "Grant & Moore",       "Finance",          "Quatre Bornes","On-site",  "3",  "10000", "Open"},
            {"Business Development Intern",  "Nexus Consulting",    "Business",         "Ebene",        "Hybrid",   "6",  "13000", "Open"},
            {"UI/UX Design Intern",          "CreativeMind Co.",    "Design",           "Rose Hill",    "Remote",   "3",  "11000", "Open"},
            {"Machine Learning Intern",      "AI Innovations Ltd",  "Analytics",        "Ebene",        "Hybrid",   "6",  "25000", "Open"},
            {"Legal Intern",                 "Chambers & Partners", "Legal",            "Port Louis",   "On-site",  "3",  "6000",  "Open"},
            {"Supply Chain Intern",          "LogiFlow Ltd",        "Logistics",        "Baie du Tombeau","On-site","6",  "10000", "Closed"},
            {"Social Media Intern",          "ViralBoost Agency",   "Marketing",        "Curepipe",     "Remote",   "3",  "7500",  "Open"},
    };

    // ===================================================
    // CONSTRUCTOR
    // ===================================================
    public SearchPage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(BG);

        add(buildTopBar(), BorderLayout.NORTH);
        add(buildMainArea(), BorderLayout.CENTER);
    }

    // ===================================================
    // TOP BAR
    // ===================================================
    private JPanel buildTopBar() {
        JPanel bar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, PRIMARY, getWidth(), 0, new Color(10, 55, 120));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        bar.setLayout(new BorderLayout());
        bar.setBorder(new EmptyBorder(14, 28, 14, 28));
        bar.setPreferredSize(new Dimension(0, 88));

        JPanel leftSide = new JPanel(new GridBagLayout());
        leftSide.setOpaque(false);

        GridBagConstraints lc = new GridBagConstraints();
        lc.gridx = 0;
        lc.fill = GridBagConstraints.HORIZONTAL;
        lc.weightx = 1;
        lc.anchor = GridBagConstraints.WEST;

        JLabel title = new JLabel("Internship Search");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);

        JLabel sub = new JLabel("Browse and filter available internship opportunities");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(new Color(180, 210, 255));
        sub.setPreferredSize(new Dimension(500, 20));

        lc.gridy = 0;
        lc.insets = new Insets(0, 0, 3, 0);
        leftSide.add(title, lc);

        lc.gridy = 1;
        lc.insets = new Insets(0, 0, 0, 0);
        leftSide.add(sub, lc);

        JButton btnBack = makeButton("← Back to Dashboard", new Color(255, 255, 255, 30), Color.WHITE);
        btnBack.addActionListener(e -> {
            if (cardLayout != null && mainContent != null) {
                cardLayout.show(mainContent, "studentDashboard");
            }
        });

        bar.add(leftSide, BorderLayout.WEST);
        bar.add(btnBack, BorderLayout.EAST);
        return bar;
    }

    // ===================================================
    // MAIN AREA
    // ===================================================
    private JSplitPane buildMainArea() {
        JSplitPane split = new JSplitPane(
                JSplitPane.HORIZONTAL_SPLIT,
                buildFilterPanel(),
                buildResultsPanel()
        );
        split.setDividerLocation(290);
        split.setDividerSize(4);
        split.setBorder(null);
        split.setBackground(BG);
        split.setContinuousLayout(true);
        return split;
    }

    // ===================================================
    // FILTER SIDEBAR
    // ===================================================
    private JPanel buildFilterPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(16, 16, 16, 8));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(20, 18, 20, 18)
        ));

        card.add(sectionHeader("🔍  Search"));
        card.add(Box.createVerticalStrut(8));

        txtSearch = new JTextField();
        txtSearch.setFont(FONT_FIELD);
        txtSearch.setForeground(TEXT_DARK);
        txtSearch.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
        txtSearch.setToolTipText("Job title, company or keyword");
        addFocusBorder(txtSearch);
        card.add(txtSearch);
        card.add(Box.createVerticalStrut(18));

        card.add(sectionHeader("⚙  Filters"));
        card.add(Box.createVerticalStrut(10));

        String[] industries = {"All Industries","IT & Software","Analytics","Marketing",
                "Finance","Human Resources","Design","Business","Legal","Logistics"};
        cmbIndustry = makeCombo(industries);
        card.add(filterRow("Industry", cmbIndustry));
        card.add(Box.createVerticalStrut(10));

        String[] locations = {"All Locations","Port Louis","Ebene","Quatre Bornes",
                "Rose Hill","Curepipe","Baie du Tombeau","Remote"};
        cmbLocation = makeCombo(locations);
        card.add(filterRow("Location", cmbLocation));
        card.add(Box.createVerticalStrut(10));

        String[] durations = {"Any Duration","3 Months","6 Months"};
        cmbDuration = makeCombo(durations);
        card.add(filterRow("Duration", cmbDuration));
        card.add(Box.createVerticalStrut(10));

        String[] modes = {"Any Mode","On-site","Remote","Hybrid"};
        cmbWorkMode = makeCombo(modes);
        card.add(filterRow("Work Mode", cmbWorkMode));
        card.add(Box.createVerticalStrut(18));

        card.add(sectionHeader("💰  Min. Stipend (Rs)"));
        card.add(Box.createVerticalStrut(8));

        sldStipend = new JSlider(0, 25000, 0);
        sldStipend.setBackground(CARD_BG);
        sldStipend.setMajorTickSpacing(5000);
        sldStipend.setPaintTicks(true);
        sldStipend.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        sldStipend.setForeground(TEXT_MUTED);

        lblStipendVal = new JLabel("Rs 0 +");
        lblStipendVal.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblStipendVal.setForeground(PRIMARY);
        lblStipendVal.setAlignmentX(Component.LEFT_ALIGNMENT);

        sldStipend.addChangeListener(e -> {
            int val = sldStipend.getValue();
            int rounded = (val / 1000) * 1000;
            lblStipendVal.setText("Rs " + String.format("%,d", rounded) + " +");
        });

        card.add(lblStipendVal);
        card.add(Box.createVerticalStrut(4));
        card.add(sldStipend);
        card.add(Box.createVerticalStrut(24));

        JPanel btnPanel = new JPanel(new GridLayout(2, 1, 0, 8));
        btnPanel.setOpaque(false);
        btnPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 84));

        btnSearch = makeButton("Search Internships", PRIMARY, Color.WHITE);
        btnClear  = makeButton("Clear Filters", CARD_BG, PRIMARY);

        btnSearch.addActionListener(e -> runSearch());
        btnClear.addActionListener(e -> clearFilters());

        btnPanel.add(btnSearch);
        btnPanel.add(btnClear);
        card.add(btnPanel);

        card.add(Box.createVerticalGlue());

        JScrollPane scrollFilter = new JScrollPane(card);
        scrollFilter.setBorder(null);
        scrollFilter.getVerticalScrollBar().setUnitIncrement(12);

        outer.add(scrollFilter, BorderLayout.CENTER);
        return outer;
    }

    // ===================================================
    // RESULTS PANEL
    // ===================================================
    private JPanel buildResultsPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 0));
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(16, 8, 16, 16));

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(BG);
        topBar.setBorder(new EmptyBorder(0, 0, 10, 0));

        lblResultCount = new JLabel("Showing all internships");
        lblResultCount.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblResultCount.setForeground(TEXT_MUTED);

        JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        actionBtns.setOpaque(false);

        JButton btnApply = makeButton("✓ Apply", ACCENT, Color.WHITE);
        JButton btnDetails = makeButton("👁 View Details", PRIMARY, Color.WHITE);

        btnApply.addActionListener(e -> onApply());
        btnDetails.addActionListener(e -> onViewDetails());

        actionBtns.add(btnDetails);
        actionBtns.add(btnApply);

        topBar.add(lblResultCount, BorderLayout.WEST);
        topBar.add(actionBtns, BorderLayout.EAST);

        outer.add(topBar, BorderLayout.NORTH);

        String[] columns = {"#", "Job Title", "Company", "Industry", "Location",
                "Mode", "Duration", "Stipend (Rs)", "Status"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };

        resultsTable = new JTable(tableModel);
        styleTable();

        JScrollPane tableScroll = new JScrollPane(resultsTable);
        tableScroll.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(0, 0, 0, 0)
        ));
        tableScroll.getViewport().setBackground(CARD_BG);

        outer.add(tableScroll, BorderLayout.CENTER);

        populateTable(ALL_INTERNSHIPS);
        return outer;
    }

    // ===================================================
    // SEARCH LOGIC
    // ===================================================
    private void runSearch() {
        String keyword  = txtSearch.getText().trim().toLowerCase();
        String industry = (String) cmbIndustry.getSelectedItem();
        String location = (String) cmbLocation.getSelectedItem();
        String duration = (String) cmbDuration.getSelectedItem();
        String mode     = (String) cmbWorkMode.getSelectedItem();
        int minStip     = (sldStipend.getValue() / 1000) * 1000;

        List<String[]> filtered = Arrays.stream(ALL_INTERNSHIPS).filter(row -> {
            boolean kw = keyword.isEmpty()
                    || row[0].toLowerCase().contains(keyword)
                    || row[1].toLowerCase().contains(keyword);

            boolean ind = industry.startsWith("All") || row[2].equals(industry);
            boolean loc = location.startsWith("All") || row[3].equals(location)
                    || (location.equals("Remote") && row[4].equals("Remote"));
            boolean dur = duration.startsWith("Any")
                    || row[5].equals(duration.replace(" Months",""));
            boolean wm  = mode.startsWith("Any") || row[4].equals(mode);
            boolean stip = Integer.parseInt(row[6]) >= minStip;

            return kw && ind && loc && dur && wm && stip;
        }).collect(Collectors.toList());

        populateTable(filtered.toArray(new String[0][]));

        int count = filtered.size();
        lblResultCount.setText(count == 0
                ? "No internships match your filters."
                : "Showing " + count + " internship" + (count == 1 ? "" : "s"));
    }

    private void clearFilters() {
        txtSearch.setText("");
        cmbIndustry.setSelectedIndex(0);
        cmbLocation.setSelectedIndex(0);
        cmbDuration.setSelectedIndex(0);
        cmbWorkMode.setSelectedIndex(0);
        sldStipend.setValue(0);
        lblStipendVal.setText("Rs 0 +");
        populateTable(ALL_INTERNSHIPS);
        lblResultCount.setText("Showing all internships");
    }

    private void populateTable(String[][] data) {
        tableModel.setRowCount(0);
        int i = 1;
        for (String[] row : data) {
            tableModel.addRow(new Object[]{
                    i++,
                    row[0],
                    row[1],
                    row[2],
                    row[3],
                    row[4],
                    row[5] + " months",
                    "Rs " + String.format("%,d", Integer.parseInt(row[6])),
                    row[7]
            });
        }
    }

    // ===================================================
    // ROW ACTIONS
    // ===================================================
    private void onApply() {
        int row = resultsTable.getSelectedRow();
        if (row < 0) {
            showInfo("Please select an internship from the list first.");
            return;
        }

        String title   = (String) tableModel.getValueAt(row, 1);
        String company = (String) tableModel.getValueAt(row, 2);
        String status  = (String) tableModel.getValueAt(row, 8);

        if ("Closed".equals(status)) {
            JOptionPane.showMessageDialog(this,
                    "Applications for \"" + title + "\" are currently closed.",
                    "Closed", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Apply for:\n  " + title + "\n  at " + company + "?",
                "Confirm Application", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this,
                    "Application submitted successfully for\n\"" + title + "\"!",
                    "Applied", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void onViewDetails() {
        int row = resultsTable.getSelectedRow();
        if (row < 0) {
            showInfo("Please select an internship from the list first.");
            return;
        }

        String title    = (String) tableModel.getValueAt(row, 1);
        String company  = (String) tableModel.getValueAt(row, 2);
        String industry = (String) tableModel.getValueAt(row, 3);
        String location = (String) tableModel.getValueAt(row, 4);
        String mode     = (String) tableModel.getValueAt(row, 5);
        String duration = (String) tableModel.getValueAt(row, 6);
        String stipend  = (String) tableModel.getValueAt(row, 7);
        String status   = (String) tableModel.getValueAt(row, 8);

        JPanel detail = new JPanel(new GridLayout(0, 2, 8, 6));
        detail.setBorder(new EmptyBorder(10, 10, 10, 10));

        String[][] fields = {
                {"Job Title", title}, {"Company", company},
                {"Industry", industry}, {"Location", location},
                {"Work Mode", mode}, {"Duration", duration},
                {"Stipend", stipend}, {"Status", status}
        };

        for (String[] f : fields) {
            JLabel k = new JLabel(f[0] + ":");
            k.setFont(new Font("Segoe UI", Font.BOLD, 12));

            JLabel v = new JLabel(f[1]);
            v.setFont(FONT_FIELD);

            detail.add(k);
            detail.add(v);
        }

        JOptionPane.showMessageDialog(this, detail, "Internship Details",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void showInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    // ===================================================
    // TABLE STYLING
    // ===================================================
    private void styleTable() {
        resultsTable.setFont(FONT_TABLE);
        resultsTable.setForeground(TEXT_DARK);
        resultsTable.setBackground(CARD_BG);
        resultsTable.setRowHeight(38);
        resultsTable.setShowVerticalLines(true);
        resultsTable.setShowHorizontalLines(true);
        resultsTable.setGridColor(new Color(230, 233, 242));
        resultsTable.setSelectionBackground(ROW_SEL);
        resultsTable.setSelectionForeground(TEXT_DARK);
        resultsTable.setIntercellSpacing(new Dimension(0, 0));
        resultsTable.setFillsViewportHeight(true);

        JTableHeader header = resultsTable.getTableHeader();
        header.setFont(FONT_TABLE_H);
        header.setBackground(Color.WHITE);
        header.setForeground(TEXT_DARK);
        header.setPreferredSize(new Dimension(0, 44));
        header.setReorderingAllowed(false);
        header.setBorder(new LineBorder(new Color(50, 110, 190)));

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        headerRenderer.setBackground(Color.WHITE);
        headerRenderer.setForeground(TEXT_DARK);
        headerRenderer.setFont(FONT_TABLE_H);
        headerRenderer.setBorder(new CompoundBorder(
                new MatteBorder(1, 1, 1, 1, new Color(50, 110, 190)),
                new EmptyBorder(0, 12, 0, 12)
        ));

        for (int i = 0; i < resultsTable.getColumnCount(); i++) {
            resultsTable.getColumnModel().getColumn(i).setHeaderRenderer(headerRenderer);
        }

        int[] widths = {40, 230, 190, 150, 130, 95, 95, 120, 80};
        for (int i = 0; i < widths.length; i++) {
            resultsTable.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        }
        resultsTable.getColumnModel().getColumn(0).setMaxWidth(45);

        resultsTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int col) {

                super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                setFont(FONT_TABLE);
                setBorder(new EmptyBorder(0, 12, 0, 12));

                if (isSelected) {
                    setBackground(ROW_SEL);
                    setForeground(TEXT_DARK);
                } else {
                    setBackground(CARD_BG);
                    setForeground(TEXT_DARK);
                }

                if (col == 8) {
                    String status = value == null ? "" : value.toString();
                    if ("Open".equals(status)) {
                        setForeground(ACCENT);
                        setFont(new Font("Segoe UI", Font.BOLD, 12));
                    } else {
                        setForeground(DANGER);
                        setFont(new Font("Segoe UI", Font.BOLD, 12));
                    }
                }

                setHorizontalAlignment(col == 0 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return this;
            }
        });
    }

    // ===================================================
    // UI HELPERS
    // ===================================================
    private JLabel sectionHeader(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(PRIMARY);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        return lbl;
    }

    private JPanel filterRow(String labelText, JComponent control) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 62));

        JLabel lbl = new JLabel(labelText);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);

        control.setMaximumSize(new Dimension(Integer.MAX_VALUE, 34));
        control.setAlignmentX(Component.LEFT_ALIGNMENT);

        p.add(lbl);
        p.add(Box.createVerticalStrut(4));
        p.add(control);
        return p;
    }

    private JComboBox<String> makeCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD);
        cb.setForeground(TEXT_DARK);
        cb.setBackground(Color.WHITE);
        cb.setBorder(new CompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(2, 6, 2, 6)
        ));
        return cb;
    }

    /**
     * FIXED BUTTON:
     * Text stays visible at all times (no disappearing until hover)
     */
    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                Color drawBg = bg;
                if (getModel().isPressed()) {
                    drawBg = bg.darker();
                } else if (getModel().isRollover()) {
                    if (bg.equals(PRIMARY)) drawBg = PRIMARY_HOV;
                    else if (bg.equals(ACCENT)) drawBg = bg.darker();
                    else drawBg = new Color(235, 240, 248);
                }

                g2.setColor(drawBg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();

                super.paintComponent(g);
            }
        };

        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setBackground(new Color(0, 0, 0, 0));
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(10, 20, 10, 20));

        return btn;
    }

    private void addFocusBorder(JTextField f) {
        Border normal = BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(6, 10, 6, 10)
        );
        Border focused = BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY, 2, true),
                new EmptyBorder(5, 9, 5, 9)
        );
        f.setBorder(normal);
        f.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                f.setBorder(focused);
            }

            @Override
            public void focusLost(FocusEvent e) {
                f.setBorder(normal);
            }
        });
    }

    // ===================================================
    // ENTRY POINT
    // ===================================================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1200, 760);
            frame.setMinimumSize(new Dimension(900, 620));

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            JPanel dashboard = new JPanel(new BorderLayout());

            SearchPage searchPage = new SearchPage(cardLayout, mainContent);

            mainContent.add(dashboard, "studentDashboard");
            mainContent.add(searchPage, "browseInternships");

            frame.setContentPane(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cardLayout.show(mainContent, "browseInternships");
        });
    }
}