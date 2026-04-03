package internship.dashboard;

import internship.dashboard.dao.StudentDAO;
import internship.dashboard.model.Student;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class DashboardMain extends JFrame {

    public DashboardMain() {
        setTitle("Internship Management System");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setBackground(Color.LIGHT_GRAY);

        sidebar.add(createSidebarButton("Dashboard", "/icons/home.png"));
        sidebar.add(createSidebarButton("Jobs", "/icons/briefcase.png"));
        sidebar.add(createSidebarButton("Companies", "/icons/building.png"));
        sidebar.add(createSidebarButton("Application Status", "/icons/status.png"));
        sidebar.add(createSidebarButton("Profile", "/icons/user.png"));

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 2));
        separator.setForeground(Color.WHITE);
        sidebar.add(separator);

        sidebar.add(createSidebarButton("Log Out", "/icons/logout.png"));

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN CONTENT =====
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(Color.WHITE);

        // ===== TOP PANEL (Search Bar) =====
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        topPanel.setBackground(Color.WHITE);

        JTextField searchField = new JTextField("Search Jobs...");
        searchField.setBounds(0, 0, 400, 40);
        searchField.setBackground(Color.WHITE);
        searchField.setForeground(Color.BLACK);
        searchField.setCaretColor(Color.WHITE);
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 16));
        searchField.setBorder(new RoundedBorder(20, 3));

        ImageIcon rawIcon = new ImageIcon(getClass().getResource("/icons/search.png"));
        Image scaledImage = rawIcon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
        ImageIcon searchIcon = new ImageIcon(scaledImage);

        JButton searchButton = new JButton(searchIcon);
        searchButton.setBounds(370, 5, 30, 30);
        searchButton.setBorderPainted(false);
        searchButton.setContentAreaFilled(false);
        searchButton.setFocusPainted(false);

        JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(400, 40));
        layeredPane.add(searchField, Integer.valueOf(0));
        layeredPane.add(searchButton, Integer.valueOf(1));

        topPanel.add(layeredPane);
        mainContent.add(topPanel, BorderLayout.NORTH);

        // ===== CENTER CONTENT =====
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // Welcome Panel
        JPanel welcomePanel = new JPanel();
        welcomePanel.setBackground(Color.WHITE);
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel welcomeLabel = new JLabel("Welcome to the Internship Dashboard");
        welcomeLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        welcomePanel.add(welcomeLabel);

        centerPanel.add(welcomePanel, BorderLayout.NORTH);

        // Statistics Cards
        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 20));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        statsPanel.setBackground(Color.WHITE);

        statsPanel.add(createStatCard("Available Jobs", "24"));
        statsPanel.add(createStatCard("Companies", "12"));
        statsPanel.add(createStatCard("Applications", "5"));

        centerPanel.add(statsPanel, BorderLayout.CENTER);

        // Recent Jobs Table
        String[] columns = {"Company", "Position", "Location", "Status"};
        Object[][] data = {
                {"TechSoft", "Java Intern", "Port Louis", "Open"},
                {"CyberNet", "Web Developer Intern", "Ebene", "Open"},
                {"SmartTech", "Database Intern", "Curepipe", "Closed"},
                {"FutureLabs", "Software Engineer Intern", "Rose Hill", "Open"}
        };

        JTable jobsTable = new JTable(data, columns);
        JScrollPane scrollPane = new JScrollPane(jobsTable);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Recent Internship Opportunities"));

        centerPanel.add(scrollPane, BorderLayout.SOUTH);

        mainContent.add(centerPanel, BorderLayout.CENTER);
        add(mainContent, BorderLayout.CENTER);

        // ===== ACTIONS =====
        searchField.addActionListener(e -> performSearch(searchField.getText()));
        searchButton.addActionListener(e -> performSearch(searchField.getText()));
    }

    private JButton createSidebarButton(String text, String iconPath) {
        ImageIcon rawIcon = new ImageIcon(getClass().getResource(iconPath));
        Image scaledImage = rawIcon.getImage().getScaledInstance(15, 15, Image.SCALE_SMOOTH);
        ImageIcon icon = new ImageIcon(scaledImage);

        JButton button = new JButton(text, icon);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        button.setBackground(Color.GRAY);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 16));

        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMargin(new Insets(0, 20, 0, 0));
        button.setHorizontalTextPosition(SwingConstants.RIGHT);
        button.setIconTextGap(10);

        return button;
    }

    private JPanel createStatCard(String title, String value) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(new Color(240, 240, 240));
        card.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 16));

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("SansSerif", Font.BOLD, 28));
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);

        return card;
    }

    // Custom rounded border class
    public static class RoundedBorder implements Border {
        private final int radius;
        private final int thickness;

        public RoundedBorder(int radius, int thickness) {
            this.radius = radius;
            this.thickness = thickness;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(thickness, thickness, thickness, thickness);
        }

        @Override
        public boolean isBorderOpaque() {
            return false;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setColor(Color.BLACK);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setStroke(new BasicStroke(thickness));
            g2.drawRoundRect(x + thickness / 2, y + thickness / 2,
                    width - thickness, height - thickness,
                    radius, radius);
            g2.dispose();
        }
    }

    private void performSearch(String query) {
        JOptionPane.showMessageDialog(this,
                "Searching for: " + query,
                "Search Results",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new DashboardMain().setVisible(true));

        StudentDAO studentDAO = new StudentDAO();

        try {
            // CREATE
            Student student = new Student("Krishnen Chinien", "M", "kchinien@gmail.com", "krishnen2411");
            studentDAO.addStudent(student);
            System.out.println("✅ Student added successfully!");

            // READ
            List<Student> students = studentDAO.getAllStudents();
            System.out.println("📋 Student List:");
            for (Student s : students) {
                System.out.println(s.getStudentId() + " | " + s.getName() + " | " + s.getGender() + " | " + s.getEmail());
            }

            // UPDATE (example: change name of first student)
            if (!students.isEmpty()) {
                Student first = students.get(0);
                first.setName("Roubina Persand");
                studentDAO.updateStudent(first);
                System.out.println("✏️ Student updated successfully!");
            }

            // DELETE (example: delete last student)
            if (!students.isEmpty()) {
                int lastId = students.get(students.size() - 1).getStudentId();
                studentDAO.deleteStudent(lastId);
                System.out.println("🗑️ Student deleted successfully!");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}