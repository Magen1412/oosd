package internship.ApplicationSubmissionPage;

import internship.dashboard.StudentDashboard;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ApplicationStatusPage extends JPanel {

    // References to parent container and layout
    private JPanel mainContent;
    private CardLayout cardLayout;

    private JTable table;
    private JButton btnBack;
    private JComboBox<String> cmbFilterStatus;
    private JTextField txtSearch;
    private DefaultTableModel tableModel;

    // ===== COLORS =====

    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color CARD_BG       = Color.WHITE;
    private static final Color HEADER_BG     = new Color(200, 60, 50);
    private static final Color LABEL_COLOR   = new Color(80, 80, 80);
    private static final Color BTN_BACK      = new Color(60, 63, 65);

    private static final Color STATUS_PENDING  = new Color(230, 150, 40);
    private static final Color STATUS_ACCEPTED = new Color(60, 160, 80);
    private static final Color STATUS_REJECTED = new Color(200, 60, 50);
    private static final Color STATUS_DEFAULT  = new Color(120, 120, 120);

    // Sample data
    private final String[][] allData = {
            {"1", "TechSoft",    "Java Intern",             "Port Louis",  "Pending"},
            {"2", "CyberNet",    "Web Developer Intern",    "Ebene",       "Accepted"},
            {"3", "SmartTech",   "Database Intern",         "Curepipe",    "Rejected"},
            {"4", "FutureLabs",  "Software Engineer Intern","Rose Hill",   "Pending"},
            {"5", "DataCore",    "IT Support Intern",       "Port Louis",  "Accepted"},
    };

    public ApplicationStatusPage(JPanel mainContent, CardLayout cardLayout) {
        this.mainContent = mainContent;
        this.cardLayout = cardLayout;

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


        add(buildContent(), BorderLayout.CENTER);
    }


    // ===== CONTENT =====
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);

        // Page header
        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setBackground(CONTENT_BG);
        pageHeader.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));
        JLabel pageTitle = new JLabel("Application Status");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(new Color(40, 40, 40));
        pageHeader.add(pageTitle, BorderLayout.WEST);
        wrapper.add(pageHeader, BorderLayout.NORTH);

        // Card wrapper
        JPanel cardWrapper = new JPanel(new BorderLayout());
        cardWrapper.setBackground(CONTENT_BG);
        cardWrapper.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(24, 28, 24, 28)
        ));

        card.add(buildSummaryRow(), BorderLayout.NORTH);

        // Filter + Search
        JPanel filterBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        filterBar.setBackground(CARD_BG);
        filterBar.setBorder(BorderFactory.createEmptyBorder(16, 0, 14, 0));

        JLabel searchIcon = new JLabel("🔍");
        txtSearch = new JTextField(18);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtSearch.setPreferredSize(new Dimension(200, 32));
        txtSearch.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(3, 8, 3, 8)
        ));

        JLabel filterLabel = new JLabel("Filter:");
        filterLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        filterLabel.setForeground(LABEL_COLOR);

        cmbFilterStatus = new JComboBox<>(new String[]{"All", "Pending", "Accepted", "Rejected"});
        cmbFilterStatus.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbFilterStatus.setPreferredSize(new Dimension(130, 32));
        cmbFilterStatus.setBackground(Color.WHITE);

        filterBar.add(searchIcon);
        filterBar.add(txtSearch);
        filterBar.add(Box.createHorizontalStrut(8));
        filterBar.add(filterLabel);
        filterBar.add(cmbFilterStatus);

        // Table
        String[] columnNames = {"App ID", "Company", "Position", "Location", "Status"};
        tableModel = new DefaultTableModel(allData, columnNames) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        table = new JTable(tableModel);
        table.setRowHeight(40);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableArea = new JPanel(new BorderLayout());
        tableArea.setBackground(CARD_BG);
        tableArea.add(filterBar, BorderLayout.NORTH);
        tableArea.add(scrollPane, BorderLayout.CENTER);
        card.add(tableArea, BorderLayout.CENTER);

        // Back button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        btnBack = styledButton("← Back", BTN_BACK);
        btnBack.addActionListener(e -> cardLayout.show(mainContent, "Dashboard"));
        buttonPanel.add(btnBack);
        card.add(buttonPanel, BorderLayout.SOUTH);

        cardWrapper.add(card);
        wrapper.add(cardWrapper, BorderLayout.CENTER);

        // Filter logic
        cmbFilterStatus.addActionListener(e -> applyFilter());
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { applyFilter(); }
        });

        return wrapper;
    }

    // ===== SUMMARY ROW (counts at top of card) =====
    private JPanel buildSummaryRow() {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        row.setBackground(CARD_BG);
        row.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));

        long total    = allData.length;
        long pending  = countByStatus("Pending");
        long accepted = countByStatus("Accepted");
        long rejected = countByStatus("Rejected");

        row.add(summaryBadge("Total Applications", String.valueOf(total),   new Color(100, 130, 180)));
        row.add(summaryBadge("Pending",            String.valueOf(pending),  STATUS_PENDING));
        row.add(summaryBadge("Accepted",           String.valueOf(accepted), STATUS_ACCEPTED));
        row.add(summaryBadge("Rejected",           String.valueOf(rejected), STATUS_REJECTED));

        return row;
    }

    private JPanel summaryBadge(String label, String count, Color color) {
        JPanel wrapper = new JPanel(new BorderLayout()) {
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(color);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
                g2.dispose();
            }
        };
        wrapper.setOpaque(false);
        wrapper.setPreferredSize(new Dimension(155, 64));
        wrapper.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JLabel numLabel = new JLabel(count);
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        numLabel.setForeground(Color.WHITE);

        JLabel txtLabel = new JLabel(label);
        txtLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtLabel.setForeground(new Color(255, 255, 255, 200));

        wrapper.add(numLabel, BorderLayout.CENTER);
        wrapper.add(txtLabel, BorderLayout.SOUTH);
        return wrapper;
    }

    // ===== FILTER LOGIC =====
    private void applyFilter() {
        String statusFilter = (String) cmbFilterStatus.getSelectedItem();
        String searchText   = txtSearch.getText().trim().toLowerCase();

        tableModel.setRowCount(0);
        for (String[] row : allData) {
            boolean matchStatus = "All".equals(statusFilter) || row[4].equals(statusFilter);
            boolean matchSearch = searchText.isEmpty()
                    || row[1].toLowerCase().contains(searchText)
                    || row[2].toLowerCase().contains(searchText);
            if (matchStatus && matchSearch) {
                tableModel.addRow(row);
            }
        }
    }

    private long countByStatus(String status) {
        long count = 0;
        for (String[] row : allData) if (row[4].equals(status)) count++;
        return count;
    }

    // ===== HELPERS =====
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

    // ===== MAIN for testing =====
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 700);

            CardLayout cl = new CardLayout();
            JPanel mainContent = new JPanel(cl);


            // Add application status page
            mainContent.add(new ApplicationStatusPage(mainContent, cl), "Application Status");
            mainContent.add(new StudentDashboard(new CardLayout(),mainContent ), "Dashboard");

            frame.add(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cl.show(mainContent, "Application Status");
        });
    }
}
