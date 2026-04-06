package internship.support;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SupportPage extends JFrame {

    public SupportPage() {
        setTitle("Student Support Page");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Full screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ---------------- Summary Cards ----------------
        JPanel summaryPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        summaryPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        summaryPanel.add(createCard("FAQs Available", "5", new Color(52, 152, 219)));
        summaryPanel.add(createCard("Requests Submitted", "12", new Color(46, 204, 113)));
        summaryPanel.add(createCard("Pending Responses", "3", new Color(241, 196, 15)));

        // ---------------- Main Content ----------------
        JPanel contentPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // FAQs Section
        JPanel faqPanel = new JPanel(new BorderLayout());
        faqPanel.setBorder(BorderFactory.createTitledBorder("Frequently Asked Questions"));

        String[] columns = {"Question", "Answer"};
        Object[][] data = {
                {"Forgot Password?", "Use the Reset Password option on login."},
                {"CV Upload?", "PDF/DOCX up to 5MB supported."},
                {"Internship Filters?", "Filter by company, location, duration."},
                {"Application Status?", "Pending, Accepted, Rejected explained."},
                {"Notifications?", "Pop-ups + Notifications Page."}
        };
        JTable faqTable = new JTable(data, columns);
        faqTable.setRowHeight(30);
        faqTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        faqTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        faqPanel.add(new JScrollPane(faqTable), BorderLayout.CENTER);

        // Contact Form Section
        JPanel contactForm = new JPanel(new GridLayout(5, 2, 10, 10));
        contactForm.setBorder(BorderFactory.createTitledBorder("Submit a Support Request"));

        JTextField nameField = new JTextField();
        JTextField idField = new JTextField();
        JTextField emailField = new JTextField();
        JTextArea issueArea = new JTextArea(3, 20);

        contactForm.add(new JLabel("Name:"));
        contactForm.add(nameField);
        contactForm.add(new JLabel("Student ID:"));
        contactForm.add(idField);
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

        // Add both sections side by side
        contentPanel.add(faqPanel);
        contentPanel.add(contactForm);

        // ---------------- Layout ----------------
        setLayout(new BorderLayout());
        add(summaryPanel, BorderLayout.CENTER);
        add(contentPanel, BorderLayout.SOUTH);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SupportPage().setVisible(true));
    }
}
