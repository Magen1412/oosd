package internship.ApplicationSubmissionPage;

import internship.dashboard.StudentDashboard;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ApplicationSubmissionPage extends JPanel {

    private JTextField nameField, emailField, phoneField, locationField;
    private JComboBox<String> roleBox;
    private ButtonGroup workGroup;
    private JTextArea reasonArea;
    private JLabel fileName;

    private JPanel mainContent;
    private CardLayout cardLayout;

    public ApplicationSubmissionPage(JPanel mainContent, CardLayout cardLayout) {
        this.mainContent = mainContent;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());
        add(buildContent(), BorderLayout.CENTER);
    }

    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 247, 250));

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        Dimension fieldSize = new Dimension(400, 35);

        // Title
        JLabel title = new JLabel("Application Submission");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        card.add(title);

        // Full Name
        card.add(createLabel("Full Name"));
        nameField = createField(fieldSize);
        card.add(nameField);
        card.add(Box.createVerticalStrut(15));

        // Email
        card.add(createLabel("Email"));
        emailField = createField(fieldSize);
        card.add(emailField);
        card.add(Box.createVerticalStrut(15));

        // Phone
        card.add(createLabel("Phone"));
        phoneField = createField(fieldSize);
        card.add(phoneField);
        card.add(Box.createVerticalStrut(15));

        // Preferred Role
        card.add(createLabel("Preferred Role"));
        roleBox = new JComboBox<>(new String[]{
                "Select Role",
                "Software Development Intern",
                "Web Development Intern",
                "Mobile App Development Intern",
                "Data Analyst Intern",
                "Database Intern"
        });
        roleBox.setMaximumSize(fieldSize);
        card.add(roleBox);
        card.add(Box.createVerticalStrut(15));

        // Work Preference
        card.add(createLabel("Work Preference"));
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBackground(Color.WHITE);

        JRadioButton remote = new JRadioButton("Remote");
        JRadioButton onsite = new JRadioButton("Onsite");
        JRadioButton hybrid = new JRadioButton("Hybrid");

        remote.setBackground(Color.WHITE);
        onsite.setBackground(Color.WHITE);
        hybrid.setBackground(Color.WHITE);

        workGroup = new ButtonGroup();
        workGroup.add(remote);
        workGroup.add(onsite);
        workGroup.add(hybrid);

        radioPanel.add(remote);
        radioPanel.add(onsite);
        radioPanel.add(hybrid);
        card.add(radioPanel);
        card.add(Box.createVerticalStrut(15));

        // Skills
        card.add(createLabel("Skills"));
        JPanel skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        skillPanel.setBackground(Color.WHITE);
        skillPanel.add(new JCheckBox("HTML"));
        skillPanel.add(new JCheckBox("CSS"));
        skillPanel.add(new JCheckBox("JavaScript"));
        card.add(skillPanel);
        card.add(Box.createVerticalStrut(15));

        // Location
        card.add(createLabel("Preferred Location"));
        locationField = createField(fieldSize);
        card.add(locationField);
        card.add(Box.createVerticalStrut(15));

        // Reason
        card.add(createLabel("Why are you applying?"));
        reasonArea = new JTextArea(3, 20);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        reasonArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        reasonArea.setMaximumSize(new Dimension(400, 80));
        card.add(reasonArea);
        card.add(Box.createVerticalStrut(15));

        // Resume Upload
        card.add(createLabel("Resume"));
        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filePanel.setBackground(Color.WHITE);
        fileName = new JLabel("No file chosen");
        JButton uploadBtn = new JButton("Choose File");
        uploadBtn.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                fileName.setText(chooser.getSelectedFile().getName());
            }
        });
        filePanel.add(uploadBtn);
        filePanel.add(fileName);
        card.add(filePanel);
        card.add(Box.createVerticalStrut(20));
        // Back Button
        JButton backBtn = new JButton("← Back");
        backBtn.setAlignmentX(Component.LEFT_ALIGNMENT);
        backBtn.setFocusPainted(false);
        backBtn.setBackground(new Color(220, 220, 220));

        backBtn.addActionListener(e -> {
            cardLayout.show(mainContent, "dashboard");
        });

        card.add(backBtn);
        card.add(Box.createVerticalStrut(10));

        // Submit Button
        JButton submitBtn = new JButton("Submit Application");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setMaximumSize(new Dimension(400, 45));
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);
        submitBtn.addActionListener(e -> handleSubmit());
        card.add(submitBtn);

        centerPanel.add(card);

        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        wrapper.add(scrollPane, BorderLayout.CENTER);
        return wrapper;
    }

    private void handleSubmit() {
        if (nameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty() ||
                locationField.getText().trim().isEmpty() ||
                reasonArea.getText().trim().isEmpty() ||
                roleBox.getSelectedIndex() == 0 ||
                workGroup.getSelection() == null ||
                fileName.getText().equals("No file chosen")) {

            JOptionPane.showMessageDialog(this,
                    "❌ Please fill in all fields before submitting.",
                    "Validation Error",
                    JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this,
                    "✅ Application submitted successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
            cardLayout.show(mainContent, "dashboard"); // redirect back to dashboard
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createField(Dimension size) {
        JTextField field = new JTextField();
        field.setMaximumSize(size);
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }

    // ===== MAIN for testing =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            CardLayout cl = new CardLayout();
            JPanel mainContent = new JPanel(cl);
            mainContent.add(new StudentDashboard(cl, mainContent), "dashboard");

            mainContent.add(new ApplicationSubmissionPage(mainContent, cl), "Application");

            frame.add(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cl.show(mainContent, "Application");
        });
    }
}
