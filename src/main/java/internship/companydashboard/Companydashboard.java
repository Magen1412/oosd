package internship.companydashboard;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

import internship.ApplicationSubmissionPage.ApplicationStatusPage;
import internship.addoffer.AddInternshipOfferPage;
import internship.ApplicationView.ApplicationViewPage;
import internship.choiceapplicationpage.ChoiceApplicationPage;
import internship.dashboard.dao.CompanyDAO;
import internship.dashboard.model.Company;
import internship.editofferpage.EditOfferPage;
import internship.profile.ProfilePage;

public class Companydashboard extends JPanel {

    private DefaultTableModel offersTableModel;
    private JTable offersTable;
    private JPanel mainContent;
    private CardLayout cardLayout;

    // Sample internship offers data
    private Object[][] offersData = {
            {"TechSoft", "Java Intern", "Port Louis", "Full-Time", "Open"},
            {"CyberNet", "Web Developer Intern", "Ebene", "Part-Time", "Open"},
            {"SmartTech", "Database Intern", "Curepipe", "Full-Time", "Closed"},
            {"FutureLabs", "Software Engineer Intern", "Rose Hill", "Full-Time", "Open"}
    };

    public Companydashboard(JPanel mainContent, CardLayout cardLayout) {
        this.mainContent = mainContent;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 114));
        header.setPreferredSize(new Dimension(getWidth(), 65));
        header.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel companyLabel = new JLabel("Company Dashboard");
        companyLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        companyLabel.setForeground(Color.WHITE);
        header.add(companyLabel, BorderLayout.WEST);

        add(header, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setBackground(new Color(40, 40, 60));

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSidebarButton("Dashboard", "companyDashboard"));
        sidebar.add(createSidebarButton("Company Profile ", "CompanyProfile"));
        sidebar.add(createSidebarButton("Internship Offers", "AddOffer"));
        sidebar.add(createSidebarButton("View Applications", "ApplicationViewPage"));
        sidebar.add(createSidebarButton("Edit Internship Offer ", "EditOfferPage"));
        sidebar.add(createSidebarButton("Schedule interview ", "internshipschedule"));

        sidebar.add(Box.createVerticalGlue());

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(80, 80, 100));
        sidebar.add(sep);
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
        statsPanel.add(createStatCard("Active Offers", "4", new Color(30, 60, 114)));
        statsPanel.add(createStatCard("Applications", "18", new Color(20, 130, 90)));
        statsPanel.add(createStatCard("Interviews", "6", new Color(180, 100, 20)));
        statsPanel.add(createStatCard("Hired", "3", new Color(140, 30, 100)));
        contentPanel.add(statsPanel, BorderLayout.NORTH);

        // Internship Offers Table
        JPanel offersPanel = new JPanel(new BorderLayout());
        offersPanel.setBackground(Color.WHITE);
        offersPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        String[] columns = {"Company", "Position", "Location", "Type", "Status"};
        offersTableModel = new DefaultTableModel(offersData, columns) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        offersTable = new JTable(offersTableModel);
        offersTable.setRowHeight(30);
        offersTable.setFont(new Font("SansSerif", Font.PLAIN, 14));
        offersTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 14));
        offersTable.getTableHeader().setBackground(new Color(30, 60, 114));
        offersTable.getTableHeader().setForeground(Color.WHITE);
        offersTable.setSelectionBackground(new Color(200, 220, 255));

        JScrollPane scrollPane = new JScrollPane(offersTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Internship Offers"));
        offersPanel.add(scrollPane, BorderLayout.CENTER);

        contentPanel.add(offersPanel, BorderLayout.CENTER);

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

        JPanel topBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topBar.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("  " + title);
        lblTitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblTitle.setForeground(new Color(100, 110, 130));
        topBar.add(lblTitle);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 32));
        lblValue.setForeground(accent);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(topBar, BorderLayout.NORTH);
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
            JLabel lbl = new JLabel("🔑 Please log in", SwingConstants.CENTER);
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

            // Add pages to CardLayout
            mainContent.add(new Companydashboard(mainContent, cl), "companyDashboard");

            // Example placeholder pages
            mainContent.add(new AddInternshipOfferPage(cl, mainContent), "AddOffer");
            mainContent.add(new ApplicationViewPage(mainContent ,cl), "ApplicationViewPage");
            mainContent.add(new ApplicationStatusPage(mainContent, cl), "ApplicationStatusPage");
            mainContent.add(new internshipschedule(cl, mainContent), "internshipschedule");
            mainContent.add(new EditOfferPage(cl,mainContent), "EditOfferPage");
            mainContent.add(new CompanyProfile(cl , mainContent), "companyProfile");

            frame.add(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Show dashboard by default
            cl.show(mainContent, "companyDashboard");
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
