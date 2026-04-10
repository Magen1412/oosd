package internship.admindashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import internship.ApplicationView.ApplicationViewPage;
import internship.settings.SettingsPage;

public class Admindashboard extends JPanel {

    private DefaultTableModel companiesTableModel;
    private JTable companiesTable;
    private JPanel mainContent;
    private CardLayout cardLayout;

    // Sample companies data
    private Object[][] companiesData = {
            {"TechSoft", "Software", "Port Louis", "Active"},
            {"CyberNet", "Web Services", "Ebene", "Active"},
            {"SmartTech", "Database Solutions", "Curepipe", "Inactive"},
            {"FutureLabs", "AI Research", "Rose Hill", "Active"}
    };

    public Admindashboard(JPanel mainContent, CardLayout cardLayout) {
        this.mainContent = mainContent;
        this.cardLayout = cardLayout;

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

        add(header, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(240, getHeight()));
        sidebar.setBackground(new Color(40, 40, 60));

        sidebar.add(Box.createVerticalStrut(20));

        sidebar.add(createSidebarButton("Dashboard", "adminDashboard"));
        sidebar.add(createSidebarButton("Admin Profile", "adminProfile"));
        sidebar.add(createSidebarButton("Approve/Reject Application ", "Choiceapplication"));
        sidebar.add(createSidebarButton("System Settings", "Settings"));




        sidebar.add(Box.createVerticalGlue());
        sidebar.add(new JSeparator());
        sidebar.add(Box.createVerticalStrut(10));

        // Logout button with confirmation
        JButton btnLogout = createSidebarButton("Log Out", null);
        btnLogout.addActionListener(e -> {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "Are you sure you want to log out?",
                    "Confirm Logout",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );
            if (choice == JOptionPane.YES_OPTION) {
                JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                topFrame.dispose();
                SwingUtilities.invokeLater(() -> new LoginPage().setVisible(true));
            }
        });
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(20));

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN CONTENT =====
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(245, 247, 252));

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        statsPanel.setBackground(new Color(245, 247, 252));
        statsPanel.add(createStatCard("Companies", "12", new Color(30, 60, 114)));
        statsPanel.add(createStatCard("Users", "50", new Color(20, 130, 90)));
        statsPanel.add(createStatCard("Students", "200", new Color(180, 100, 20)));
        statsPanel.add(createStatCard("Applications", "120", new Color(140, 30, 100)));
        contentPanel.add(statsPanel, BorderLayout.NORTH);

        // Companies Table
        JPanel companiesPanel = new JPanel(new BorderLayout());
        companiesPanel.setBackground(Color.WHITE);
        companiesPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

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

        contentPanel.add(companiesPanel, BorderLayout.CENTER);

        add(contentPanel, BorderLayout.CENTER);
    }

    // ===== HELPERS =====
    private JButton createSidebarButton(String text, String pageName) {
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

        if (pageName != null) {
            button.addActionListener(e -> cardLayout.show(mainContent, pageName));
        }

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(30, 60, 114));
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(new Color(40, 40, 60));
                button.setForeground(new Color(200, 215, 255));
            }
        });
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

    // ===== Simple Login Page =====
    static class LoginPage extends JFrame {
        public LoginPage() {
            setTitle("Internship Management System - Login");
            setSize(500, 300);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);

            JPanel panel = new JPanel(new BorderLayout());
            JLabel lbl = new JLabel("Please log in", SwingConstants.CENTER);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
            panel.add(lbl, BorderLayout.CENTER);

            add(panel);
        }
    }

    // ===== MAIN =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1100, 700);

            CardLayout cl = new CardLayout();
            JPanel mainContent = new JPanel(cl);

            // Add pages

            mainContent.add(new Admindashboard(mainContent, cl), "adminDashboard");
            mainContent.add(createPage("Manage Companies Page"), "Companies");
            mainContent.add(createPage("Manage Users Page"), "Users");
            mainContent.add(createPage("Manage Students Page"), "Students");
            mainContent.add(createPage("Applications Approval Page"), "Applications");
            mainContent.add(createPage("System Settings Page"), "Settings");

            mainContent.add(new Admindashboard(mainContent, cl), "Dashboard");
            mainContent.add(createPage("🏢 Manage Companies Page"), "Companies");
            mainContent.add(createPage("👥 Manage Users Page"), "Users");
            mainContent.add(createPage("🎓 Manage Students Page"), "Students");
            mainContent.add(createPage("📋 Applications Approval Page"), "Applications");
            // Add AdminProfilePage
            mainContent.add(new AdminProfilePage(cl, mainContent), "adminProfile");

            // FIXED: pass all three arguments to SettingsPage
            mainContent.add(new SettingsPage("Admin", cl, mainContent), "Settings");



            frame.add(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cl.show(mainContent, "adminDashboard");
        });
    }
    // Helper to create placeholder pages
    private static JPanel createPage(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lbl = new JLabel(title, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 24));
        panel.add(lbl, BorderLayout.CENTER);
        return panel;
    }
}
