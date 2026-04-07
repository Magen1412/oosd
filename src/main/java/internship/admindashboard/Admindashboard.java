package internship.admindashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Admindashboard extends JFrame {

    private DefaultTableModel companiesTableModel;
    private JTable companiesTable;

    // Sample companies data
    private Object[][] companiesData = {
            {"TechSoft", "Software", "Port Louis", "Active"},
            {"CyberNet", "Web Services", "Ebene", "Active"},
            {"SmartTech", "Database Solutions", "Curepipe", "Inactive"},
            {"FutureLabs", "AI Research", "Rose Hill", "Active"}
    };

    public Admindashboard() {
        setTitle("Internship Management System - Admin Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 114));
        header.setPreferredSize(new Dimension(getWidth(), 65));
        header.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel adminLabel = new JLabel("Admin Dashboard");
        adminLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        adminLabel.setForeground(Color.WHITE);
        header.add(adminLabel, BorderLayout.WEST);

        JLabel subLabel = new JLabel("Internship Management Portal");
        subLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        subLabel.setForeground(new Color(180, 210, 255));
        header.add(subLabel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(240, getHeight()));
        sidebar.setBackground(new Color(40, 40, 60));

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSidebarButton("📊 Dashboard"));
        sidebar.add(createSidebarButton("🏢 Manage Companies"));
        sidebar.add(createSidebarButton("👥 Manage Users"));
        sidebar.add(createSidebarButton("🎓 Manage Students"));
        sidebar.add(createSidebarButton("📋 Approve/Reject Applications"));
        sidebar.add(createSidebarButton("⚙ System Settings"));

        sidebar.add(Box.createVerticalGlue());
        sidebar.add(new JSeparator());
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("🚪 Log Out"));
        sidebar.add(Box.createVerticalStrut(20));

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN CONTENT =====
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 252));

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        statsPanel.setBackground(new Color(245, 247, 252));
        statsPanel.add(createStatCard("Companies", "12", new Color(30, 60, 114)));
        statsPanel.add(createStatCard("Users", "50", new Color(20, 130, 90)));
        statsPanel.add(createStatCard("Students", "200", new Color(180, 100, 20)));
        statsPanel.add(createStatCard("Applications", "120", new Color(140, 30, 100)));
        mainContent.add(statsPanel, BorderLayout.NORTH);

        // ===== COMPANIES TABLE =====
        JPanel companiesPanel = new JPanel(new BorderLayout());
        companiesPanel.setBackground(Color.WHITE);
        companiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Action buttons row
        JPanel actionsBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionsBar.setBackground(Color.WHITE);

        JButton btnAdd = createActionButton("Add Company", new Color(30, 60, 114));
        JButton btnEdit = createActionButton("Edit Company", new Color(20, 130, 90));
        JButton btnDelete = createActionButton("Delete Company", new Color(180, 50, 50));

        actionsBar.add(btnAdd);
        actionsBar.add(btnEdit);
        actionsBar.add(btnDelete);
        companiesPanel.add(actionsBar, BorderLayout.NORTH);

        // Table
        String[] columns = {"Company", "Industry", "Location", "Status"};
        companiesTableModel = new DefaultTableModel(companiesData, columns) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        companiesTable = new JTable(companiesTableModel);
        companiesTable.setRowHeight(30);
        companiesTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        companiesTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        companiesTable.getTableHeader().setBackground(new Color(30, 60, 114));
        companiesTable.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(companiesTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Registered Companies"));
        companiesPanel.add(scrollPane, BorderLayout.CENTER);
        mainContent.add(companiesPanel, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====
        btnAdd.addActionListener(e -> showAddCompanyDialog());
        btnEdit.addActionListener(e -> showEditCompanyDialog());
        btnDelete.addActionListener(e -> deleteCompany());
    }

    // ===== ADD COMPANY DIALOG =====
    private void showAddCompanyDialog() {
        JOptionPane.showMessageDialog(this, "Add Company dialog would appear here.");
    }

    // ===== EDIT COMPANY DIALOG =====
    private void showEditCompanyDialog() {
        JOptionPane.showMessageDialog(this, "Edit Company dialog would appear here.");
    }

    // ===== DELETE COMPANY =====
    private void deleteCompany() {
        int selectedRow = companiesTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a company to delete.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }
        companiesTableModel.removeRow(selectedRow);
        JOptionPane.showMessageDialog(this, "Company deleted successfully!");
    }

    // ===== HELPERS =====
    private JButton createSidebarButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
        button.setBackground(new Color(40, 40, 60));
        button.setForeground(new Color(200, 215, 255));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.PLAIN, 14));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(0, 20, 0, 0));
        return button;
    }

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JPanel createStatCard(String title, String value, Color accent) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 225, 235), 1),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTitle.setForeground(new Color(100, 110, 130));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblValue.setForeground(accent);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        return card;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Admindashboard().setVisible(true));
    }
}
