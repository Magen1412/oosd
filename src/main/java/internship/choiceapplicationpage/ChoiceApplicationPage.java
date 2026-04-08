package internship.choiceapplicationpage;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ChoiceApplicationPage extends JFrame {

    private static final Color PRIMARY      = new Color(30, 90, 160);
    private static final Color PRIMARY_HOV  = new Color(20, 65, 120);
    private static final Color ACCENT       = new Color(0, 168, 120);
    private static final Color ACCENT_LIGHT = new Color(225, 248, 240);
    private static final Color WARNING      = new Color(210, 120, 0);
    private static final Color WARNING_LIGHT= new Color(255, 243, 224);
    private static final Color DANGER       = new Color(200, 45,  45);
    private static final Color DANGER_LIGHT = new Color(255, 236, 236);
    private static final Color PENDING_CLR  = new Color(60, 100, 200);
    private static final Color PENDING_LITE = new Color(230, 238, 255);
    private static final Color BG           = new Color(245, 247, 252);
    private static final Color CARD_BG      = Color.WHITE;
    private static final Color SECTION_HDR  = new Color(248, 249, 253);
    private static final Color TEXT_DARK    = new Color(30,  35,  50);
    private static final Color TEXT_MUTED   = new Color(110, 120, 140);
    private static final Color BORDER_CLR   = new Color(210, 215, 230);
    private static final Color ROW_ALT      = new Color(248, 250, 255);
    private static final Color ROW_SEL      = new Color(220, 234, 255);
    private static final Color HEADER_BG    = PRIMARY;

    private static final Font FONT_TITLE    = new Font("Segoe UI", Font.BOLD,  22);
    private static final Font FONT_SUBTITLE = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_SECTION  = new Font("Segoe UI", Font.BOLD,  13);
    private static final Font FONT_LABEL    = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_HINT     = new Font("Segoe UI", Font.PLAIN, 11);
    private static final Font FONT_FIELD    = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font FONT_BTN      = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_TABLE_H  = new Font("Segoe UI", Font.BOLD,  12);
    private static final Font FONT_TABLE    = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font FONT_BADGE    = new Font("Segoe UI", Font.BOLD,  11);
    private static final Font FONT_STAT_NUM = new Font("Segoe UI", Font.BOLD,  20);
    private static final Font FONT_STAT_LBL = new Font("Segoe UI", Font.PLAIN, 11);

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private static class Application {
        int    id;
        String studentName, studentEmail, studentCourse;
        String internshipTitle, company, industry;
        String appliedDate, status, remarks;
        boolean hasCV;

        Application(int id, String name, String email, String course,
                    String title, String company, String industry,
                    String date, String status, boolean hasCV) {
            this.id = id; this.studentName = name; this.studentEmail = email;
            this.studentCourse = course; this.internshipTitle = title;
            this.company = company; this.industry = industry;
            this.appliedDate = date; this.status = status;
            this.remarks = ""; this.hasCV = hasCV;
        }
    }

    private final List<Application> allApplications = new ArrayList<>(Arrays.asList(
            new Application(1001,"Aisha Ramdhun",   "aisha@uni.mu",   "BSc CS",          "Software Engineering Intern","TechNova Ltd",       "IT & Software","15/03/2026","Pending",  true),
            new Application(1002,"Kevin Bhunjun",   "kevin@uni.mu",   "BSc Finance",     "Finance Intern",             "Horizon Bank",        "Finance",      "16/03/2026","Pending",  true),
            new Application(1003,"Priya Soobah",    "priya@uni.mu",   "BA Marketing",    "Marketing Intern",           "BlueWave Agency",     "Marketing",    "14/03/2026","Approved", true),
            new Application(1004,"Dylan Fowdar",    "dylan@uni.mu",   "BSc IT",          "Network Engineering Intern", "NetCore Systems",     "IT & Software","17/03/2026","Pending",  false),
            new Application(1005,"Nadia Curpen",    "nadia@uni.mu",   "BDes",            "Graphic Design Intern",      "PixelCraft Studio",   "Design",       "18/03/2026","Rejected", true),
            new Application(1006,"Rohit Seebah",    "rohit@uni.mu",   "BSc CS",          "Data Analyst Intern",        "DataBridge Co.",      "Analytics",    "12/03/2026","Approved", true),
            new Application(1007,"Ananya Pillay",   "ananya@uni.mu",  "LLB",             "Legal Intern",               "Chambers & Partners", "Legal",        "19/03/2026","Pending",  true),
            new Application(1008,"Marc Etiennne",   "marc@uni.mu",    "BSc CS",          "Cybersecurity Intern",       "SecureNet MRU",       "IT & Software","20/03/2026","Interviewed",true),
            new Application(1009,"Fatima Oozeer",   "fatima@uni.mu",  "BBA",             "Business Development Intern","Nexus Consulting",    "Business",     "21/03/2026","Pending",  true),
            new Application(1010,"James Leclezio",  "james@uni.mu",   "BDes",            "UI/UX Design Intern",        "CreativeMind Co.",    "Design",       "22/03/2026","Pending",  false),
            new Application(1011,"Sneha Daby",      "sneha@uni.mu",   "BSc Stats",       "Machine Learning Intern",    "AI Innovations Ltd",  "Analytics",    "23/03/2026","Approved", true),
            new Application(1012,"Yannick Mootien", "yannick@uni.mu", "BCom Accounting", "Accounting Intern",          "Grant & Moore",       "Finance",      "24/03/2026","Rejected", true),
            new Application(1013,"Layla Beeharry",  "layla@uni.mu",   "BA Marketing",    "Social Media Intern",        "ViralBoost Agency",   "Marketing",    "25/03/2026","Pending",  true),
            new Application(1014,"Chris Appanah",   "chris@uni.mu",   "BSc CS",          "Software Engineering Intern","TechNova Ltd",        "IT & Software","26/03/2026","Interviewed",true),
            new Application(1015,"Meera Hurreeram", "meera@uni.mu",   "BSc Supply Chain","Supply Chain Intern",        "LogiFlow Ltd",        "Logistics",    "27/03/2026","Pending",  true)
    ));

    private final List<String> auditLog = new ArrayList<>();
    private JTextField       txtSearch;
    private JComboBox<String> cmbStatusFilter;
    private JComboBox<String> cmbIndustryFilter;
    private JButton          btnApplyFilter, btnClearFilter;

    private JLabel lblStatPending, lblStatApproved, lblStatRejected, lblStatInterviewed;
    private JLabel lblResultCount;

    private JTable            table;
    private DefaultTableModel tableModel;
    private JPanel  detailPanel;
    private JLabel  detailName, detailEmail, detailCourse;
    private JLabel  detailInternship, detailCompany, detailIndustry;
    private JLabel  detailDate, detailStatus;
    private JTextArea  txtRemarks;
    private JButton btnApprove, btnReject, btnInterview, btnViewCV;
    private JLabel  lblNoSelection;

    private DefaultListModel<String> auditListModel;

    private Application selectedApp = null;

    public ChoiceApplicationPage() {
        setTitle("Internship Management System – Approve / Reject Applications");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1000, 680));
        setSize(1300, 820);

        JPanel root = new JPanel(new BorderLayout(0, 0));
        root.setBackground(BG);
        setContentPane(root);

        root.add(buildTopBar(),      BorderLayout.NORTH);
        root.add(buildStatsBar(),    BorderLayout.CENTER);

        root.remove(buildStatsBar());
        JPanel body = new JPanel(new BorderLayout(0, 0));
        body.setBackground(BG);
        body.add(buildStatsBar(),   BorderLayout.NORTH);
        body.add(buildContentArea(),BorderLayout.CENTER);
        root.add(body, BorderLayout.CENTER);

        refreshTable(allApplications);
        updateStats();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildTopBar() {
        JPanel bar = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY, getWidth(), 0, new Color(10, 55, 120));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(new Color(255, 255, 255, 14));
                g2.fillOval(getWidth() - 160, -80, 300, 300);
                g2.dispose();
            }
        };
        bar.setLayout(new BorderLayout());
        bar.setBorder(new EmptyBorder(14, 28, 14, 28));
        bar.setPreferredSize(new Dimension(0, 88));

        JPanel left = new JPanel(new GridBagLayout());
        left.setOpaque(false);
        GridBagConstraints lc = new GridBagConstraints();
        lc.anchor = GridBagConstraints.WEST;

        JPanel badge = makeBadgePanel("⚙");
        lc.gridx = 0; lc.gridy = 0; lc.gridheight = 2;
        lc.insets = new Insets(0, 0, 0, 14);
        left.add(badge, lc);

        JPanel tb = new JPanel();
        tb.setLayout(new BoxLayout(tb, BoxLayout.Y_AXIS));
        tb.setOpaque(false);
        JLabel title = new JLabel("Approve / Reject Applications");
        title.setFont(FONT_TITLE);
        title.setForeground(Color.WHITE);
        JLabel sub = new JLabel("Review student applications and manage their status");
        sub.setFont(FONT_SUBTITLE);
        sub.setForeground(new Color(180, 210, 255));
        sub.setPreferredSize(new Dimension(460, 20));
        tb.add(title);
        tb.add(Box.createVerticalStrut(3));
        tb.add(sub);
        lc.gridx = 1; lc.gridy = 0; lc.gridheight = 2; lc.insets = new Insets(0,0,0,0);
        left.add(tb, lc);

        JPanel chip = makeCompanyChip("👤  Admin Panel");
        bar.add(left, BorderLayout.WEST);
        bar.add(chip, BorderLayout.EAST);
        return bar;
    }

    private JPanel buildStatsBar() {
        JPanel bar = new JPanel(new GridLayout(1, 4, 12, 0));
        bar.setBackground(BG);
        bar.setBorder(new EmptyBorder(16, 24, 0, 24));

        lblStatPending     = new JLabel("0", SwingConstants.CENTER);
        lblStatApproved    = new JLabel("0", SwingConstants.CENTER);
        lblStatRejected    = new JLabel("0", SwingConstants.CENTER);
        lblStatInterviewed = new JLabel("0", SwingConstants.CENTER);

        bar.add(makeStatCard("Pending",     lblStatPending,     PENDING_CLR,  PENDING_LITE));
        bar.add(makeStatCard("Approved",    lblStatApproved,    ACCENT,       ACCENT_LIGHT));
        bar.add(makeStatCard("Rejected",    lblStatRejected,    DANGER,       DANGER_LIGHT));
        bar.add(makeStatCard("Interviewed", lblStatInterviewed, WARNING,      WARNING_LIGHT));
        return bar;
    }

    private JPanel makeStatCard(String label, JLabel numLabel,
                                Color accent, Color bg) {
        JPanel card = new JPanel(new GridBagLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                g2.setColor(accent);
                g2.fillRoundRect(0, 0, 5, getHeight(), 4, 4);
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(14, 18, 14, 18));

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0; c.gridy = 0; c.anchor = GridBagConstraints.CENTER;

        numLabel.setFont(FONT_STAT_NUM);
        numLabel.setForeground(accent);

        JLabel lbl = new JLabel(label);
        lbl.setFont(FONT_STAT_LBL);
        lbl.setForeground(TEXT_MUTED);

        c.gridy = 0; card.add(numLabel, c);
        c.gridy = 1; c.insets = new Insets(2,0,0,0);
        card.add(lbl, c);
        return card;
    }

    private JPanel buildContentArea() {
        JPanel content = new JPanel(new BorderLayout(0, 12));
        content.setBackground(BG);
        content.setBorder(new EmptyBorder(16, 24, 16, 24));

        content.add(buildFilterBar(), BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                buildTablePanel(), buildBottomRow());
        split.setResizeWeight(0.55);
        split.setDividerSize(6);
        split.setBorder(null);
        split.setBackground(BG);
        split.setContinuousLayout(true);

        content.add(split, BorderLayout.CENTER);
        return content;
    }

    private JPanel buildFilterBar() {
        JPanel bar = new JPanel(new GridBagLayout());
        bar.setBackground(CARD_BG);
        bar.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(12, 16, 12, 16)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, 10);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0; c.gridy = 0; c.weightx = 0;
        bar.add(makeFilterLabel("Search:"), c);

        c.gridx = 1; c.weightx = 0.35;
        txtSearch = makeTextField();
        txtSearch.setToolTipText("Student name, email or internship title");
        bar.add(txtSearch, c);

        c.gridx = 2; c.weightx = 0;
        bar.add(makeFilterLabel("Status:"), c);

        c.gridx = 3; c.weightx = 0.18;
        cmbStatusFilter = makeCombo(new String[]{
                "All Statuses","Pending","Approved","Rejected","Interviewed"});
        bar.add(cmbStatusFilter, c);

        c.gridx = 4; c.weightx = 0;
        bar.add(makeFilterLabel("Industry:"), c);

        c.gridx = 5; c.weightx = 0.18;
        cmbIndustryFilter = makeCombo(new String[]{
                "All Industries","IT & Software","Analytics","Marketing","Finance",
                "Human Resources","Design","Business","Legal","Logistics"});
        bar.add(cmbIndustryFilter, c);

        c.gridx = 6; c.weightx = 0; c.insets = new Insets(0, 6, 0, 4);
        btnApplyFilter = makeButton("Filter", PRIMARY, Color.WHITE);
        btnApplyFilter.addActionListener(e -> applyFilter());
        bar.add(btnApplyFilter, c);

        c.gridx = 7; c.insets = new Insets(0, 0, 0, 0);
        btnClearFilter = makeButton("Clear", CARD_BG, PRIMARY);
        btnClearFilter.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY, 1, true), new EmptyBorder(7, 14, 7, 14)));
        btnClearFilter.addActionListener(e -> clearFilter());
        bar.add(btnClearFilter, c);

        return bar;
    }

    private JPanel buildTablePanel() {
        JPanel panel = new JPanel(new BorderLayout(0, 8));
        panel.setBackground(BG);

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setOpaque(false);
        lblResultCount = new JLabel("Showing all 15 applications");
        lblResultCount.setFont(FONT_HINT);
        lblResultCount.setForeground(TEXT_MUTED);
        topRow.add(lblResultCount, BorderLayout.WEST);
        panel.add(topRow, BorderLayout.NORTH);

        String[] cols = {"✓", "#", "Student Name", "Course", "Internship Title",
                "Company", "Industry", "Applied On", "Status"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public Class<?> getColumnClass(int c) {
                return c == 0 ? Boolean.class : Object.class;
            }
            @Override public boolean isCellEditable(int r, int c) { return c == 0; }
        };

        table = new JTable(tableModel);
        styleTable();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) onRowSelected();
        });

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new LineBorder(BORDER_CLR, 1, true));
        scroll.getViewport().setBackground(CARD_BG);

        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildBottomRow() {
        JPanel row = new JPanel(new GridLayout(1, 2, 12, 0));
        row.setBackground(BG);
        row.add(buildDetailPanel());
        row.add(buildAuditPanel());
        return row;
    }

    private JPanel buildDetailPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(CARD_BG);
        outer.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(0, 0, 0, 0)));

        JPanel hdr = buildSectionHeader("📄  Application Details");
        outer.add(hdr, BorderLayout.NORTH);

        lblNoSelection = new JLabel(
                "<html><center>Select an application from the table<br>to review its details.</center></html>",
                SwingConstants.CENTER);
        lblNoSelection.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblNoSelection.setForeground(TEXT_MUTED);

        detailPanel = new JPanel(new GridBagLayout());
        detailPanel.setBackground(CARD_BG);
        detailPanel.setBorder(new EmptyBorder(14, 16, 10, 16));
        detailPanel.setVisible(false);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.WEST;
        c.insets = new Insets(3, 0, 3, 16);
        int row = 0;

        c.gridx = 0; c.gridy = row; c.gridwidth = 2;
        detailPanel.add(makeDetailSection("👤  Student"), c);
        row++;

        detailName    = makeDetailValue("—");
        detailEmail   = makeDetailValue("—");
        detailCourse  = makeDetailValue("—");

        row = addDetailRow(detailPanel, c, row, "Name:",    detailName);
        row = addDetailRow(detailPanel, c, row, "Email:",   detailEmail);
        row = addDetailRow(detailPanel, c, row, "Course:",  detailCourse);

        c.gridy = row; c.gridx = 0; c.gridwidth = 2;
        c.insets = new Insets(10, 0, 3, 16);
        detailPanel.add(makeDetailSection("🏢  Internship"), c);
        c.insets = new Insets(3, 0, 3, 16);
        row++;

        detailInternship = makeDetailValue("—");
        detailCompany    = makeDetailValue("—");
        detailIndustry   = makeDetailValue("—");
        detailDate       = makeDetailValue("—");
        detailStatus     = makeDetailValue("—");

        row = addDetailRow(detailPanel, c, row, "Title:",    detailInternship);
        row = addDetailRow(detailPanel, c, row, "Company:",  detailCompany);
        row = addDetailRow(detailPanel, c, row, "Industry:", detailIndustry);
        row = addDetailRow(detailPanel, c, row, "Applied:",  detailDate);
        row = addDetailRow(detailPanel, c, row, "Status:",   detailStatus);

        c.gridy = row; c.gridx = 0; c.gridwidth = 2;
        c.insets = new Insets(10, 0, 4, 0);
        detailPanel.add(makeDetailSection("💬  Remarks / Feedback"), c);
        row++;

        txtRemarks = new JTextArea(3, 0);
        txtRemarks.setFont(FONT_FIELD);
        txtRemarks.setForeground(TEXT_DARK);
        txtRemarks.setBackground(new Color(250, 251, 255));
        txtRemarks.setLineWrap(true);
        txtRemarks.setWrapStyleWord(true);
        txtRemarks.setBorder(new EmptyBorder(6, 8, 6, 8));

        JScrollPane rsPane = new JScrollPane(txtRemarks);
        rsPane.setBorder(new LineBorder(BORDER_CLR, 1, true));
        c.gridy = row++; c.gridx = 0; c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL; c.weightx = 1;
        c.insets = new Insets(0, 0, 10, 0);
        detailPanel.add(rsPane, c);

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        actionRow.setOpaque(false);

        btnApprove  = makeButton("✓ Approve",           ACCENT,   Color.WHITE);
        btnReject   = makeButton("✗ Reject",             DANGER,   Color.WHITE);
        btnInterview= makeButton("📅 Schedule Interview", WARNING,  Color.WHITE);
        btnViewCV   = makeButton("📄 View CV",            PRIMARY,  Color.WHITE);

        btnApprove.addActionListener(e   -> handleDecision("Approved"));
        btnReject.addActionListener(e    -> handleDecision("Rejected"));
        btnInterview.addActionListener(e -> handleDecision("Interviewed"));
        btnViewCV.addActionListener(e    -> handleViewCV());

        actionRow.add(btnApprove);
        actionRow.add(btnReject);
        actionRow.add(btnInterview);
        actionRow.add(btnViewCV);

        c.gridy = row; c.gridx = 0; c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(4, 0, 6, 0);
        detailPanel.add(actionRow, c);

        JPanel bulkBar = buildBulkBar();

        JPanel wrapper = new JPanel(new CardLayout());
        wrapper.add(lblNoSelection, "empty");
        wrapper.add(detailPanel,    "detail");
        wrapper.setBackground(CARD_BG);

        outer.add(new JScrollPane(wrapper) {{
            setBorder(null);
            getViewport().setBackground(CARD_BG);
        }}, BorderLayout.CENTER);
        outer.add(bulkBar, BorderLayout.SOUTH);
        return outer;
    }

    private JPanel buildBulkBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(248, 249, 253));
        bar.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, BORDER_CLR),
                new EmptyBorder(8, 16, 8, 16)));

        JLabel lbl = new JLabel("Bulk actions (use checkboxes in table):");
        lbl.setFont(FONT_HINT);
        lbl.setForeground(TEXT_MUTED);

        JPanel btns = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        btns.setOpaque(false);

        JButton bulkApprove = makeButton("Approve Selected", ACCENT,  Color.WHITE);
        JButton bulkReject  = makeButton("Reject Selected",  DANGER,  Color.WHITE);

        bulkApprove.addActionListener(e -> handleBulk("Approved"));
        bulkReject.addActionListener(e  -> handleBulk("Rejected"));

        btns.add(bulkApprove);
        btns.add(bulkReject);

        bar.add(lbl,  BorderLayout.WEST);
        bar.add(btns, BorderLayout.EAST);
        return bar;
    }

    private JPanel buildAuditPanel() {
        JPanel outer = new JPanel(new BorderLayout());
        outer.setBackground(CARD_BG);
        outer.setBorder(new LineBorder(BORDER_CLR, 1, true));

        outer.add(buildSectionHeader("🕑  Session Audit Trail"), BorderLayout.NORTH);

        auditListModel = new DefaultListModel<>();
        auditListModel.addElement("  No actions taken yet this session.");

        JList<String> auditList = new JList<>(auditListModel);
        auditList.setFont(FONT_HINT);
        auditList.setForeground(TEXT_MUTED);
        auditList.setBackground(CARD_BG);
        auditList.setSelectionBackground(ROW_SEL);
        auditList.setCellRenderer(new AuditCellRenderer());

        JScrollPane auditScroll = new JScrollPane(auditList);
        auditScroll.setBorder(null);
        outer.add(auditScroll, BorderLayout.CENTER);

        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(SECTION_HDR);
        footer.setBorder(BorderFactory.createCompoundBorder(
                new MatteBorder(1, 0, 0, 0, BORDER_CLR),
                new EmptyBorder(6, 16, 6, 16)));
        JLabel footerLbl = new JLabel("All changes are logged for compliance.");
        footerLbl.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        footerLbl.setForeground(TEXT_MUTED);
        footer.add(footerLbl, BorderLayout.WEST);
        outer.add(footer, BorderLayout.SOUTH);

        return outer;
    }

    private void styleTable() {
        table.setFont(FONT_TABLE);
        table.setForeground(TEXT_DARK);
        table.setBackground(CARD_BG);
        table.setRowHeight(34);
        table.setShowVerticalLines(false);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(230, 233, 242));
        table.setSelectionBackground(ROW_SEL);
        table.setSelectionForeground(TEXT_DARK);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setFillsViewportHeight(true);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader hdr = table.getTableHeader();
        hdr.setFont(FONT_TABLE_H);
        hdr.setBackground(HEADER_BG);
        hdr.setForeground(Color.WHITE);
        hdr.setPreferredSize(new Dimension(0, 38));
        hdr.setReorderingAllowed(false);

        int[] widths = {34, 44, 150, 120, 190, 130, 110, 90, 90};
        for (int i = 0; i < widths.length; i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);
        table.getColumnModel().getColumn(0).setMaxWidth(34);
        table.getColumnModel().getColumn(1).setMaxWidth(50);

        table.getColumnModel().getColumn(8).setCellRenderer(new StatusBadgeRenderer());

        DefaultTableCellRenderer base = new DefaultTableCellRenderer() {
            @Override public Component getTableCellRendererComponent(
                    JTable t, Object v, boolean sel, boolean foc, int r, int c) {
                super.getTableCellRendererComponent(t, v, sel, foc, r, c);
                setFont(FONT_TABLE);
                setBorder(new EmptyBorder(0, 10, 0, 10));
                setBackground(sel ? ROW_SEL : (r % 2 == 0 ? CARD_BG : ROW_ALT));
                setForeground(TEXT_DARK);
                setHorizontalAlignment(c == 1 ? SwingConstants.CENTER : SwingConstants.LEFT);
                return this;
            }
        };
        for (int i = 1; i < 8; i++)
            table.getColumnModel().getColumn(i).setCellRenderer(base);
    }

    private void refreshTable(List<Application> apps) {
        tableModel.setRowCount(0);
        for (Application a : apps) {
            tableModel.addRow(new Object[]{
                    false,
                    a.id,
                    a.studentName,
                    a.studentCourse,
                    a.internshipTitle,
                    a.company,
                    a.industry,
                    a.appliedDate,
                    a.status
            });
        }
        int n = apps.size();
        lblResultCount.setText("Showing " + n + " application" + (n == 1 ? "" : "s"));
    }

    private void applyFilter() {
        String kw  = txtSearch.getText().trim().toLowerCase();
        String st  = (String) cmbStatusFilter.getSelectedItem();
        String ind = (String) cmbIndustryFilter.getSelectedItem();

        List<Application> filtered = allApplications.stream().filter(a -> {
            boolean kMatch = kw.isEmpty()
                    || a.studentName.toLowerCase().contains(kw)
                    || a.studentEmail.toLowerCase().contains(kw)
                    || a.internshipTitle.toLowerCase().contains(kw)
                    || a.company.toLowerCase().contains(kw);
            boolean sMatch = st.startsWith("All") || a.status.equals(st);
            boolean iMatch = ind.startsWith("All") || a.industry.equals(ind);
            return kMatch && sMatch && iMatch;
        }).collect(Collectors.toList());

        refreshTable(filtered);
        clearDetailPanel();
        updateStats();
    }

    private void clearFilter() {
        txtSearch.setText("");
        cmbStatusFilter.setSelectedIndex(0);
        cmbIndustryFilter.setSelectedIndex(0);
        refreshTable(allApplications);
        clearDetailPanel();
        updateStats();
    }

    private void updateStats() {
        long pending    = allApplications.stream().filter(a -> "Pending".equals(a.status)).count();
        long approved   = allApplications.stream().filter(a -> "Approved".equals(a.status)).count();
        long rejected   = allApplications.stream().filter(a -> "Rejected".equals(a.status)).count();
        long interviewed= allApplications.stream().filter(a -> "Interviewed".equals(a.status)).count();
        lblStatPending.setText(String.valueOf(pending));
        lblStatApproved.setText(String.valueOf(approved));
        lblStatRejected.setText(String.valueOf(rejected));
        lblStatInterviewed.setText(String.valueOf(interviewed));
    }

    private void onRowSelected() {
        int viewRow = table.getSelectedRow();
        if (viewRow < 0) { clearDetailPanel(); return; }

        int appId = (int) tableModel.getValueAt(viewRow, 1);
        selectedApp = allApplications.stream()
                .filter(a -> a.id == appId).findFirst().orElse(null);

        if (selectedApp == null) { clearDetailPanel(); return; }

        detailName.setText(selectedApp.studentName);
        detailEmail.setText(selectedApp.studentEmail);
        detailCourse.setText(selectedApp.studentCourse);
        detailInternship.setText(selectedApp.internshipTitle);
        detailCompany.setText(selectedApp.company);
        detailIndustry.setText(selectedApp.industry);
        detailDate.setText(selectedApp.appliedDate);
        detailStatus.setText(selectedApp.status);
        detailStatus.setForeground(statusColour(selectedApp.status));
        txtRemarks.setText(selectedApp.remarks);

        boolean isPending = "Pending".equals(selectedApp.status);
        btnApprove.setEnabled(!("Approved".equals(selectedApp.status)));
        btnReject.setEnabled(!("Rejected".equals(selectedApp.status)));
        btnInterview.setEnabled(!("Interviewed".equals(selectedApp.status)));
        btnViewCV.setEnabled(selectedApp.hasCV);

        lblNoSelection.setVisible(false);
        detailPanel.setVisible(true);
    }

    private void clearDetailPanel() {
        selectedApp = null;
        lblNoSelection.setVisible(true);
        detailPanel.setVisible(false);
        table.clearSelection();
    }

    private void handleDecision(String newStatus) {
        if (selectedApp == null) return;

        String icon = switch (newStatus) {
            case "Approved"    -> "✓";
            case "Rejected"    -> "✗";
            case "Interviewed" -> "📅";
            default            -> "•";
        };

        String msg = String.format(
                "<html><b>%s %s</b><br><br>" +
                        "Student: <b>%s</b><br>" +
                        "Internship: <b>%s</b> at <b>%s</b><br><br>" +
                        "Remarks will be saved with this decision.</html>",
                icon, newStatus,
                selectedApp.studentName,
                selectedApp.internshipTitle,
                selectedApp.company);

        int confirm = JOptionPane.showConfirmDialog(
                this, msg, "Confirm: " + newStatus,
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirm != JOptionPane.YES_OPTION) return;

        selectedApp.status  = newStatus;
        selectedApp.remarks = txtRemarks.getText().trim();

        int viewRow = table.getSelectedRow();
        if (viewRow >= 0) tableModel.setValueAt(newStatus, viewRow, 8);

        detailStatus.setText(newStatus);
        detailStatus.setForeground(statusColour(newStatus));

        btnApprove.setEnabled(!("Approved".equals(newStatus)));
        btnReject.setEnabled(!("Rejected".equals(newStatus)));
        btnInterview.setEnabled(!("Interviewed".equals(newStatus)));

        updateStats();
        logAudit(icon + " " + newStatus + "  –  " + selectedApp.studentName
                + " for " + selectedApp.internshipTitle);

        JOptionPane.showMessageDialog(this,
                "Application " + newStatus.toLowerCase() + " successfully.",
                newStatus, JOptionPane.INFORMATION_MESSAGE);
    }

    private void handleViewCV() {
        if (selectedApp == null || !selectedApp.hasCV) return;
        JOptionPane.showMessageDialog(this,
                "<html>Opening CV for <b>" + selectedApp.studentName + "</b><br>" +
                        "<i>(In production this would open the stored file from the DB path.)</i></html>",
                "View CV", JOptionPane.INFORMATION_MESSAGE);
        logAudit("📄 CV viewed  –  " + selectedApp.studentName);
    }

    private void handleBulk(String newStatus) {

        List<Integer> checkedIds = new ArrayList<>();
        for (int r = 0; r < tableModel.getRowCount(); r++) {
            if (Boolean.TRUE.equals(tableModel.getValueAt(r, 0))) {
                checkedIds.add((int) tableModel.getValueAt(r, 1));
            }
        }
        if (checkedIds.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Tick at least one application using the checkboxes.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "Set " + checkedIds.size() + " application(s) to " + newStatus + "?",
                "Bulk " + newStatus, JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);
        if (confirm != JOptionPane.YES_OPTION) return;

        for (int id : checkedIds) {
            allApplications.stream()
                    .filter(a -> a.id == id).findFirst()
                    .ifPresent(a -> a.status = newStatus);
        }

        for (int r = 0; r < tableModel.getRowCount(); r++) {
            int id = (int) tableModel.getValueAt(r, 1);
            if (checkedIds.contains(id)) {
                tableModel.setValueAt(false,     r, 0);
                tableModel.setValueAt(newStatus, r, 8);
            }
        }

        updateStats();
        logAudit("Bulk " + newStatus + "  –  " + checkedIds.size() + " applications");
        JOptionPane.showMessageDialog(this,
                checkedIds.size() + " application(s) marked as " + newStatus + ".",
                "Bulk Done", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logAudit(String entry) {
        String timestamp = LocalDate.now().format(DATE_FMT);
        String line = timestamp + "  ·  " + entry;
        if (auditListModel.size() == 1 &&
                auditListModel.get(0).startsWith("  No actions")) {
            auditListModel.clear();
        }
        auditListModel.add(0, line);
        auditLog.add(line);
    }

    private Color statusColour(String status) {
        return switch (status) {
            case "Approved"    -> ACCENT;
            case "Rejected"    -> DANGER;
            case "Interviewed" -> WARNING;
            default            -> PENDING_CLR;
        };
    }

    private JPanel buildSectionHeader(String text) {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(SECTION_HDR);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.setColor(PRIMARY);
                g2.fillRect(0, 0, 4, getHeight());
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(9, 14, 9, 14));
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_SECTION);
        lbl.setForeground(PRIMARY);
        p.add(lbl, BorderLayout.WEST);
        return p;
    }

    private JLabel makeDetailSection(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(PRIMARY);
        return l;
    }

    private JLabel makeDetailValue(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_FIELD);
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JLabel makeDetailKey(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_MUTED);
        return l;
    }

    /** Adds a key-value detail row; returns next row index */
    private int addDetailRow(JPanel p, GridBagConstraints c,
                             int row, String key, JLabel val) {
        c.gridy = row; c.gridx = 0; c.gridwidth = 1;
        c.weightx = 0; c.fill = GridBagConstraints.NONE;
        c.insets = new Insets(2, 0, 2, 12);
        p.add(makeDetailKey(key), c);
        c.gridx = 1; c.weightx = 1; c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(2, 0, 2, 0);
        p.add(val, c);
        return row + 1;
    }

    private JLabel makeFilterLabel(String text) {
        JLabel l = new JLabel(text);
        l.setFont(FONT_LABEL);
        l.setForeground(TEXT_DARK);
        return l;
    }

    private JTextField makeTextField() {
        JTextField f = new JTextField() {
            @Override public Dimension getPreferredSize() {
                return new Dimension(super.getPreferredSize().width, 34);
            }
        };
        f.setFont(FONT_FIELD);
        f.setForeground(TEXT_DARK);
        f.setBackground(new Color(250, 251, 255));
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(5, 9, 5, 9)));
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(PRIMARY, 2, true),
                        new EmptyBorder(4, 8, 4, 8)));
            }
            @Override public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_CLR, 1, true),
                        new EmptyBorder(5, 9, 5, 9)));
            }
        });
        return f;
    }

    private JComboBox<String> makeCombo(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(FONT_FIELD);
        cb.setForeground(TEXT_DARK);
        cb.setBackground(Color.WHITE);
        cb.setBorder(new LineBorder(BORDER_CLR, 1, true));
        return cb;
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                Color draw = !isEnabled() ? new Color(200, 202, 210)
                        : getModel().isPressed() ? bg.darker()
                        : getModel().isRollover() ? bg.darker()
                        : bg;
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
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    private JPanel makeBadgePanel(String icon) {
        JPanel p = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 35));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        p.setOpaque(false);
        p.setPreferredSize(new Dimension(46, 46));
        p.setLayout(new GridBagLayout());
        JLabel l = new JLabel(icon);
        l.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 22));
        l.setForeground(Color.WHITE);
        p.add(l);
        return p;
    }

    private JPanel makeCompanyChip(String label) {
        JPanel chip = new JPanel() {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 28));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.dispose();
            }
        };
        chip.setOpaque(false);
        chip.setLayout(new FlowLayout(FlowLayout.CENTER, 8, 8));
        JLabel l = new JLabel(label);
        l.setFont(new Font("Segoe UI", Font.BOLD, 12));
        l.setForeground(Color.WHITE);
        chip.add(l);
        return chip;
    }

    /** Colour-coded status badge */
    private class StatusBadgeRenderer extends DefaultTableCellRenderer {
        @Override public Component getTableCellRendererComponent(
                JTable t, Object v, boolean sel, boolean foc, int r, int c) {
            super.getTableCellRendererComponent(t, v, sel, foc, r, c);
            String status = v == null ? "" : v.toString();
            setFont(FONT_BADGE);
            setHorizontalAlignment(SwingConstants.CENTER);
            setBorder(new EmptyBorder(0, 6, 0, 6));

            if (sel) {
                setBackground(ROW_SEL);
                setForeground(statusColour(status));
            } else {
                setBackground(r % 2 == 0 ? CARD_BG : ROW_ALT);
                setForeground(statusColour(status));
            }
            return this;
        }
    }

    /** Audit trail list row renderer */
    private class AuditCellRenderer extends DefaultListCellRenderer {
        @Override public Component getListCellRendererComponent(
                JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            JLabel l = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);
            l.setFont(FONT_HINT);
            l.setBorder(new EmptyBorder(5, 12, 5, 12));
            String text = value.toString();
            if (text.contains("Approved"))    l.setForeground(ACCENT);
            else if (text.contains("Rejected")) l.setForeground(DANGER);
            else if (text.contains("Interview")) l.setForeground(WARNING);
            else l.setForeground(TEXT_MUTED);
            if (!isSelected) l.setBackground(index % 2 == 0 ? CARD_BG : ROW_ALT);
            return l;
        }
    }

    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}
        SwingUtilities.invokeLater(ChoiceApplicationPage::new);
    }
}