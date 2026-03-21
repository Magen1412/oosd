package internship.ApplicationSubmissionPage;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class ApplicationStatusPage extends JFrame {

    // ===== COLORS (matching Dashboard theme) =====
    private static final Color SIDEBAR_BG    = new Color(60, 63, 65);
    private static final Color SIDEBAR_HOVER = new Color(80, 83, 85);
    private static final Color SIDEBAR_TEXT  = new Color(220, 220, 220);
    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color CARD_BG       = Color.WHITE;
    private static final Color HEADER_BG     = new Color(200, 60, 50);
    private static final Color LABEL_COLOR   = new Color(80, 80, 80);
    private static final Color BTN_BACK      = new Color(60, 63, 65);

    // Status badge colors
    private static final Color STATUS_PENDING  = new Color(230, 150, 40);
    private static final Color STATUS_ACCEPTED = new Color(60, 160, 80);
    private static final Color STATUS_REJECTED = new Color(200, 60, 50);
    private static final Color STATUS_DEFAULT  = new Color(120, 120, 120);

    private JTable table;
    private JButton btnBack;
    private JComboBox<String> cmbFilterStatus;
    private JTextField txtSearch;
    private DefaultTableModel tableModel;

    // Sample data
    private final String[][] allData = {
            {"1", "TechSoft",    "Java Intern",             "Port Louis",  "Pending"},
            {"2", "CyberNet",    "Web Developer Intern",    "Ebene",       "Accepted"},
            {"3", "SmartTech",   "Database Intern",         "Curepipe",    "Rejected"},
            {"4", "FutureLabs",  "Software Engineer Intern","Rose Hill",   "Pending"},
            {"5", "DataCore",    "IT Support Intern",       "Port Louis",  "Accepted"},
    };

    public ApplicationStatusPage() {
        setTitle("Internship Management System");
        setSize(960, 620);
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
            sidebar.add(createNavItem(icons[i], items[i], items[i].equals("Application Status")));
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

        // ===== SUMMARY BADGES =====
        card.add(buildSummaryRow(), BorderLayout.NORTH);

        // ===== FILTER + SEARCH BAR =====
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
        txtSearch.putClientProperty("JTextField.placeholderText", "Search company or position...");

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

        // ===== TABLE =====
        String[] columnNames = {"App ID", "Company", "Position", "Location", "Status"};
        tableModel = new DefaultTableModel(allData, columnNames) {
            public boolean isCellEditable(int row, int col) { return false; }
        };

        table = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250));
                }
                return c;
            }
        };

        // Table styling
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(235, 235, 235));
        table.setSelectionBackground(new Color(232, 240, 254));
        table.setSelectionForeground(new Color(30, 30, 30));
        table.setIntercellSpacing(new Dimension(0, 1));
        table.setFocusable(false);

        // Header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(245, 245, 245));
        header.setForeground(LABEL_COLOR);
        header.setPreferredSize(new Dimension(0, 42));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(220, 220, 220)));
        header.setReorderingAllowed(false);

        // Column widths
        table.getColumnModel().getColumn(0).setPreferredWidth(60);
        table.getColumnModel().getColumn(1).setPreferredWidth(160);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        table.getColumnModel().getColumn(3).setPreferredWidth(130);
        table.getColumnModel().getColumn(4).setPreferredWidth(110);

        // Custom renderer for Status column (colored badges)
        table.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t, Object value,
                                                           boolean isSelected, boolean hasFocus, int row, int col) {

                JLabel badge = new JLabel(value != null ? value.toString() : "", JLabel.CENTER);
                badge.setFont(new Font("Segoe UI", Font.BOLD, 11));
                badge.setOpaque(true);

                String status = value != null ? value.toString() : "";
                Color bg = switch (status) {
                    case "Accepted" -> STATUS_ACCEPTED;
                    case "Rejected" -> STATUS_REJECTED;
                    case "Pending"  -> STATUS_PENDING;
                    default         -> STATUS_DEFAULT;
                };

                badge.setBackground(isSelected ? bg.darker() : bg);
                badge.setForeground(Color.WHITE);
                badge.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));

                JPanel cell = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 6));
                cell.setBackground(isSelected
                        ? table.getSelectionBackground()
                        : (row % 2 == 0 ? Color.WHITE : new Color(248, 249, 250)));

                // Rounded badge via panel with custom paint
                JPanel roundBadge = new JPanel() {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(bg);
                        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        g2.dispose();
                    }
                };
                roundBadge.setLayout(new BorderLayout());
                roundBadge.setOpaque(false);
                roundBadge.setPreferredSize(new Dimension(80, 24));
                roundBadge.add(badge);
                cell.add(roundBadge);
                return cell;
            }
        });

        // Center-align App ID column
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 1));
        scrollPane.getViewport().setBackground(Color.WHITE);

        JPanel tableArea = new JPanel(new BorderLayout());
        tableArea.setBackground(CARD_BG);
        tableArea.add(filterBar, BorderLayout.NORTH);
        tableArea.add(scrollPane, BorderLayout.CENTER);
        card.add(tableArea, BorderLayout.CENTER);

        // ===== BUTTONS =====
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setBackground(CARD_BG);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(18, 0, 0, 0));

        btnBack = styledButton("← Back", BTN_BACK);
        buttonPanel.add(btnBack);
        card.add(buttonPanel, BorderLayout.SOUTH);

        cardWrapper.add(card);
        wrapper.add(cardWrapper, BorderLayout.CENTER);

        // ===== FILTER / SEARCH LOGIC =====
        cmbFilterStatus.addActionListener(e -> applyFilter());
        txtSearch.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) { applyFilter(); }
        });

        btnBack.addActionListener(e -> dispose());

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
        JPanel badge = new JPanel(new BorderLayout());
        badge.setBackground(color);
        badge.setPreferredSize(new Dimension(150, 62));
        badge.setBorder(BorderFactory.createEmptyBorder(8, 14, 8, 14));

        JLabel numLabel = new JLabel(count);
        numLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        numLabel.setForeground(Color.WHITE);

        JLabel txtLabel = new JLabel(label);
        txtLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        txtLabel.setForeground(new Color(255, 255, 255, 200));

        badge.add(numLabel, BorderLayout.CENTER);
        badge.add(txtLabel, BorderLayout.SOUTH);

        // Rounded corners
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

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}
        SwingUtilities.invokeLater(ApplicationStatusPage::new);
    }
}