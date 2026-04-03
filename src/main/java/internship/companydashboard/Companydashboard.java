package internship.companydashboard;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class Companydashboard extends JFrame {

    private DefaultTableModel offersTableModel;
    private JTable offersTable;

    // Sample internship offers data
    private Object[][] offersData = {
            {"TechSoft", "Java Intern", "Port Louis", "Full-Time", "Open"},
            {"CyberNet", "Web Developer Intern", "Ebene", "Part-Time", "Open"},
            {"SmartTech", "Database Intern", "Curepipe", "Full-Time", "Closed"},
            {"FutureLabs", "Software Engineer Intern", "Rose Hill", "Full-Time", "Open"}
    };

    public Companydashboard() {
        setTitle("Internship Management System - Company Dashboard");
        setSize(1100, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // ===== HEADER =====
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(30, 60, 114));
        header.setPreferredSize(new Dimension(getWidth(), 65));
        header.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));

        JLabel companyLabel = new JLabel("🏢  Company Dashboard");
        companyLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
        companyLabel.setForeground(Color.WHITE);
        header.add(companyLabel, BorderLayout.WEST);

        JLabel subLabel = new JLabel("Internship Management Portal");
        subLabel.setFont(new Font("SansSerif", Font.ITALIC, 13));
        subLabel.setForeground(new Color(180, 210, 255));
        header.add(subLabel, BorderLayout.EAST);

        add(header, BorderLayout.NORTH);

        // ===== SIDEBAR =====
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(220, getHeight()));
        sidebar.setBackground(new Color(40, 40, 60));

        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(createSidebarButton("📊  Dashboard"));
        sidebar.add(createSidebarButton("💼  Internship Offers"));
        sidebar.add(createSidebarButton("📋  View Applications"));
        sidebar.add(createSidebarButton("📅  Interview Scheduling"));
        sidebar.add(createSidebarButton("🏢  Company Profile"));

        sidebar.add(Box.createVerticalGlue());

        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        sep.setForeground(new Color(80, 80, 100));
        sidebar.add(sep);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(createSidebarButton("🚪  Log Out"));
        sidebar.add(Box.createVerticalStrut(20));

        add(sidebar, BorderLayout.WEST);

        // ===== MAIN CONTENT =====
        JPanel mainContent = new JPanel(new BorderLayout());
        mainContent.setBackground(new Color(245, 247, 252));

        // Stats Panel
        JPanel statsPanel = new JPanel(new GridLayout(1, 4, 15, 0));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        statsPanel.setBackground(new Color(245, 247, 252));
        statsPanel.add(createStatCard("📢 Active Offers", "4", new Color(30, 60, 114)));
        statsPanel.add(createStatCard("📋 Applications", "18", new Color(20, 130, 90)));
        statsPanel.add(createStatCard("📅 Interviews", "6", new Color(180, 100, 20)));
        statsPanel.add(createStatCard("✅ Hired", "3", new Color(140, 30, 100)));
        mainContent.add(statsPanel, BorderLayout.NORTH);

        // ===== INTERNSHIP OFFERS TABLE =====
        JPanel offersPanel = new JPanel(new BorderLayout());
        offersPanel.setBackground(Color.WHITE);
        offersPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // Action buttons row
        JPanel actionsBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        actionsBar.setBackground(Color.WHITE);

        JButton btnAdd = createActionButton("➕  Add Internship Offer", new Color(30, 60, 114));
        JButton btnEdit = createActionButton("✏️  Edit Offer", new Color(20, 130, 90));
        JButton btnViewApps = createActionButton("📋  View Applications", new Color(180, 100, 20));
        JButton btnSchedule = createActionButton("📅  Schedule Interview", new Color(140, 30, 100));

        actionsBar.add(btnAdd);
        actionsBar.add(btnEdit);
        actionsBar.add(btnViewApps);
        actionsBar.add(btnSchedule);
        offersPanel.add(actionsBar, BorderLayout.NORTH);

        // Table
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
        mainContent.add(offersPanel, BorderLayout.CENTER);

        add(mainContent, BorderLayout.CENTER);

        // ===== BUTTON ACTIONS =====

        btnAdd.addActionListener(e -> showAddOfferDialog());
        btnEdit.addActionListener(e -> showEditOfferDialog());
        btnViewApps.addActionListener(e -> showViewApplicationsDialog());
        btnSchedule.addActionListener(e -> showInterviewSchedulingDialog());
    }

    // ===== ADD INTERNSHIP OFFER DIALOG =====
    private void showAddOfferDialog() {
        JDialog dialog = new JDialog(this, "Add Internship Offer", true);
        dialog.setSize(450, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(30, 60, 114));
        titleBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("➕  Add New Internship Offer");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        titleBar.add(title);
        dialog.add(titleBar, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JTextField tfCompany = new JTextField();
        JTextField tfPosition = new JTextField();
        JTextField tfLocation = new JTextField();
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Open", "Closed"});

        addFormRow(form, gbc, 0, "Company:", tfCompany);
        addFormRow(form, gbc, 1, "Position:", tfPosition);
        addFormRow(form, gbc, 2, "Location:", tfLocation);
        addFormRowComponent(form, gbc, 3, "Type:", cbType);
        addFormRowComponent(form, gbc, 4, "Status:", cbStatus);

        dialog.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnSave = createActionButton("💾  Save", new Color(30, 60, 114));
        JButton btnCancel = createActionButton("✖  Cancel", new Color(150, 50, 50));
        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            String company = tfCompany.getText().trim();
            String position = tfPosition.getText().trim();
            String location = tfLocation.getText().trim();
            String type = (String) cbType.getSelectedItem();
            String status = (String) cbStatus.getSelectedItem();

            if (company.isEmpty() || position.isEmpty() || location.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please fill in all fields.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            offersTableModel.addRow(new Object[]{company, position, location, type, status});
            JOptionPane.showMessageDialog(dialog, "✅ Internship offer added successfully!");
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ===== EDIT INTERNSHIP OFFER DIALOG =====
    private void showEditOfferDialog() {
        int selectedRow = offersTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select an offer to edit.", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JDialog dialog = new JDialog(this, "Edit Internship Offer", true);
        dialog.setSize(450, 380);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(20, 130, 90));
        titleBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("✏️  Edit Internship Offer");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        titleBar.add(title);
        dialog.add(titleBar, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JTextField tfCompany = new JTextField((String) offersTableModel.getValueAt(selectedRow, 0));
        JTextField tfPosition = new JTextField((String) offersTableModel.getValueAt(selectedRow, 1));
        JTextField tfLocation = new JTextField((String) offersTableModel.getValueAt(selectedRow, 2));
        JComboBox<String> cbType = new JComboBox<>(new String[]{"Full-Time", "Part-Time"});
        cbType.setSelectedItem(offersTableModel.getValueAt(selectedRow, 3));
        JComboBox<String> cbStatus = new JComboBox<>(new String[]{"Open", "Closed"});
        cbStatus.setSelectedItem(offersTableModel.getValueAt(selectedRow, 4));

        addFormRow(form, gbc, 0, "Company:", tfCompany);
        addFormRow(form, gbc, 1, "Position:", tfPosition);
        addFormRow(form, gbc, 2, "Location:", tfLocation);
        addFormRowComponent(form, gbc, 3, "Type:", cbType);
        addFormRowComponent(form, gbc, 4, "Status:", cbStatus);

        dialog.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnSave = createActionButton("💾  Update", new Color(20, 130, 90));
        JButton btnCancel = createActionButton("✖  Cancel", new Color(150, 50, 50));
        btnPanel.add(btnCancel);
        btnPanel.add(btnSave);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        btnSave.addActionListener(e -> {
            offersTableModel.setValueAt(tfCompany.getText().trim(), selectedRow, 0);
            offersTableModel.setValueAt(tfPosition.getText().trim(), selectedRow, 1);
            offersTableModel.setValueAt(tfLocation.getText().trim(), selectedRow, 2);
            offersTableModel.setValueAt(cbType.getSelectedItem(), selectedRow, 3);
            offersTableModel.setValueAt(cbStatus.getSelectedItem(), selectedRow, 4);
            JOptionPane.showMessageDialog(dialog, "✅ Offer updated successfully!");
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ===== VIEW APPLICATIONS DIALOG =====
    private void showViewApplicationsDialog() {
        JDialog dialog = new JDialog(this, "View Applications", true);
        dialog.setSize(700, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(180, 100, 20));
        titleBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("📋  Internship Applications");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        titleBar.add(title);
        dialog.add(titleBar, BorderLayout.NORTH);

        String[] cols = {"Applicant", "Position", "Company", "Applied Date", "Status"};
        Object[][] appData = {
                {"Alice Martin", "Java Intern", "TechSoft", "2025-06-01", "Under Review"},
                {"Bob Leclaire", "Web Developer Intern", "CyberNet", "2025-06-03", "Shortlisted"},
                {"Clara Dumont", "Database Intern", "SmartTech", "2025-06-05", "Rejected"},
                {"David Okonkwo", "Software Engineer Intern", "FutureLabs", "2025-06-07", "Under Review"},
                {"Eva Chen", "Java Intern", "TechSoft", "2025-06-08", "Shortlisted"},
        };
        JTable appTable = new JTable(new DefaultTableModel(appData, cols) {
            public boolean isCellEditable(int r, int c) { return false; }
        });
        appTable.setRowHeight(28);
        appTable.setFont(new Font("SansSerif", Font.PLAIN, 13));
        appTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        appTable.getTableHeader().setBackground(new Color(180, 100, 20));
        appTable.getTableHeader().setForeground(Color.WHITE);
        appTable.setSelectionBackground(new Color(255, 220, 180));

        JScrollPane sp = new JScrollPane(appTable);
        sp.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        dialog.add(sp, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        JButton btnClose = createActionButton("✖  Close", new Color(100, 100, 100));
        btnPanel.add(btnClose);
        btnClose.addActionListener(e -> dialog.dispose());
        dialog.add(btnPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }

    // ===== INTERVIEW SCHEDULING DIALOG =====
    private void showInterviewSchedulingDialog() {
        JDialog dialog = new JDialog(this, "Interview Scheduling", true);
        dialog.setSize(480, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setBackground(new Color(140, 30, 100));
        titleBar.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        JLabel title = new JLabel("📅  Schedule an Interview");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(Color.WHITE);
        titleBar.add(title);
        dialog.add(titleBar, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(20, 25, 10, 25));
        form.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        JComboBox<String> cbApplicant = new JComboBox<>(new String[]{
                "Alice Martin", "Bob Leclaire", "Clara Dumont", "David Okonkwo", "Eva Chen"
        });
        JComboBox<String> cbPosition = new JComboBox<>(new String[]{
                "Java Intern", "Web Developer Intern", "Database Intern", "Software Engineer Intern"
        });
        JTextField tfDate = new JTextField("YYYY-MM-DD");
        JTextField tfTime = new JTextField("HH:MM");
        JComboBox<String> cbMode = new JComboBox<>(new String[]{"In-Person", "Online (Zoom)", "Online (Teams)", "Phone"});
        JTextField tfNotes = new JTextField();

        addFormRowComponent(form, gbc, 0, "Applicant:", cbApplicant);
        addFormRowComponent(form, gbc, 1, "Position:", cbPosition);
        addFormRow(form, gbc, 2, "Date:", tfDate);
        addFormRow(form, gbc, 3, "Time:", tfTime);
        addFormRowComponent(form, gbc, 4, "Mode:", cbMode);
        addFormRow(form, gbc, 5, "Notes:", tfNotes);

        dialog.add(form, BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        btnPanel.setBackground(Color.WHITE);
        JButton btnSchedule = createActionButton("📅  Schedule", new Color(140, 30, 100));
        JButton btnCancel = createActionButton("✖  Cancel", new Color(100, 100, 100));
        btnPanel.add(btnCancel);
        btnPanel.add(btnSchedule);
        dialog.add(btnPanel, BorderLayout.SOUTH);

        btnSchedule.addActionListener(e -> {
            String applicant = (String) cbApplicant.getSelectedItem();
            String date = tfDate.getText().trim();
            String time = tfTime.getText().trim();
            String mode = (String) cbMode.getSelectedItem();
            if (date.equals("YYYY-MM-DD") || time.equals("HH:MM") || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a valid date and time.", "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(dialog,
                    "✅ Interview scheduled!\n\n" +
                            "Applicant: " + applicant + "\n" +
                            "Date: " + date + " at " + time + "\n" +
                            "Mode: " + mode,
                    "Interview Scheduled", JOptionPane.INFORMATION_MESSAGE);
            dialog.dispose();
        });

        btnCancel.addActionListener(e -> dialog.dispose());
        dialog.setVisible(true);
    }

    // ===== HELPERS =====

    private void addFormRow(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 13));
        field.setPreferredSize(new Dimension(200, 30));
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(lbl, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(field, gbc);
    }

    private void addFormRowComponent(JPanel panel, GridBagConstraints gbc, int row, String label, JComponent comp) {
        comp.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 0; gbc.gridy = row; gbc.weightx = 0.3;
        JLabel lbl = new JLabel(label);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        panel.add(lbl, gbc);
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(comp, gbc);
    }

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

    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setFont(new Font("SansSerif", Font.BOLD, 13));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setMargin(new Insets(6, 14, 6, 14));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(color.darker()); }
            public void mouseExited(MouseEvent e) { button.setBackground(color); }
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
        JLabel dot = new JLabel("●");
        dot.setForeground(accent);
        dot.setFont(new Font("SansSerif", Font.BOLD, 18));
        topBar.add(dot);

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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Companydashboard().setVisible(true));
    }
}