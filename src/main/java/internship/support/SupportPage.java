package internship.support;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import internship.dashboard.StudentDashboard;

public class SupportPage extends JPanel {

    private CardLayout cardLayout;
    private JPanel mainContent;

    public SupportPage(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        // Summary cards
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        summaryPanel.add(createCard("FAQs Available", "5", new Color(52, 152, 219)));
        summaryPanel.add(createCard("Requests Submitted", "12", new Color(46, 204, 113)));
        summaryPanel.add(createCard("Pending Responses", "3", new Color(241, 196, 15)));

        // FAQ table
        JPanel faqPanel = new JPanel(new BorderLayout());
        faqPanel.setBorder(BorderFactory.createTitledBorder("Frequently Asked Questions"));
        String[] columns = {"Question", "Answer"};
        Object[][] data = {
                {"Forgot Password?", "Use the Change Password option on login or settings."},
                {"CV Upload?", "PDF/DOCX up to 5MB supported."},
                {"Internship Filters?", "Filter by company, location, duration."},
                {"Application Status?", "Pending, Accepted, Rejected explained."},
                {"Edit Profile", "Click on user icon."}
        };
        JTable faqTable = new JTable(data, columns);
        faqTable.setRowHeight(30);
        faqTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        faqTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        faqPanel.add(new JScrollPane(faqTable), BorderLayout.CENTER);

        // Contact form
        JPanel contactForm = new JPanel(new GridLayout(5, 2, 10, 10));
        contactForm.setBorder(BorderFactory.createTitledBorder("Submit a Support Request"));
        JTextField titleField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField emailField = new JTextField();
        JTextArea issueArea = new JTextArea(3, 20);

        contactForm.add(new JLabel("Title:"));
        contactForm.add(titleField);
        contactForm.add(new JLabel("Name:"));
        contactForm.add(nameField);
        contactForm.add(new JLabel("Email:"));
        contactForm.add(emailField);
        contactForm.add(new JLabel("Issue:"));
        contactForm.add(new JScrollPane(issueArea));

        JButton submitBtn = new JButton("Submit Request");
        submitBtn.setBackground(new Color(52, 152, 219));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contactForm.add(new JLabel());
        contactForm.add(submitBtn);

        submitBtn.addActionListener((ActionEvent e) -> {
            JOptionPane.showMessageDialog(this,
                    "Support request submitted successfully!\nOur team will contact you soon.",
                    "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        });

        // Layout
        setLayout(new BorderLayout());
        add(summaryPanel, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.add(faqPanel);
        contentPanel.add(contactForm);
        add(contentPanel, BorderLayout.CENTER);

        JButton backBtn = new JButton("← Back to Dashboard");
        backBtn.setBackground(new Color(52, 152, 219));
        backBtn.setForeground(Color.WHITE);
        backBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backBtn.addActionListener(e -> cardLayout.show(mainContent, "studentDashboard"));

        JPanel backPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backPanel.add(backBtn);
        add(backPanel, BorderLayout.SOUTH);
    }

    private JPanel createCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setPreferredSize(new Dimension(200, 120));
        card.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        JLabel valueLabel = new JLabel(value, SwingConstants.CENTER);

        titleLabel.setForeground(Color.WHITE);
        valueLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);
        return card;
    }

    // ---------------- Main ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Support Page");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(900, 700);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);
    
            mainContent.add(new StudentDashboard(cardLayout, mainContent), "studentDashboard");
            mainContent.add(new SupportPage(cardLayout, mainContent), "supportPage");

            // Show support page first
            cardLayout.show(mainContent, "supportPage");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}