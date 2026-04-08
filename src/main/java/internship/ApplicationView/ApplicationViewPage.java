package internship.ApplicationView;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class ApplicationViewPage extends JPanel {

    private JPanel mainContent;
    private CardLayout cardLayout;

    public ApplicationViewPage(JPanel mainContent, CardLayout cardLayout) {
        this.mainContent = mainContent;
        this.cardLayout = cardLayout;

        setLayout(new BorderLayout());

        // Main Container with Padding
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));
        mainPanel.setBackground(new Color(248, 249, 250));

        // --- Top Navigation ---
        JPanel navPanel = new JPanel(new BorderLayout());
        navPanel.setOpaque(false);

        JButton backButton = new JButton(" <  Back");
        backButton.setFocusPainted(false);
        backButton.setBackground(Color.BLACK);
        backButton.setForeground(Color.WHITE);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Navigation Logic for Back Button
        backButton.addActionListener(e -> cardLayout.show(mainContent, "Dashboard"));

        JButton statusButton = new JButton("Update Status");
        statusButton.setBackground(new Color(15, 23, 42));
        statusButton.setForeground(Color.WHITE);
        statusButton.setFocusPainted(false);
        statusButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // --- POP-UP LOGIC FOR UPDATE STATUS ---
        statusButton.addActionListener(e -> {
            String[] options = {"Pending", "Accepted", "Rejected"};
            String selection = (String) JOptionPane.showInputDialog(
                    ApplicationViewPage.this,
                    "Select the new status for this application:",
                    "Update Application Status",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (selection != null) {
                JOptionPane.showMessageDialog(
                        ApplicationViewPage.this,
                        "Status successfully updated to: " + selection,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            }
        });

        navPanel.add(backButton, BorderLayout.WEST);
        navPanel.add(statusButton, BorderLayout.EAST);

        // --- Content Area ---
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 0, 20);

        // Left Column
        JPanel leftCol = new JPanel();
        leftCol.setLayout(new BoxLayout(leftCol, BoxLayout.Y_AXIS));
        leftCol.setOpaque(false);

        // Header Card
        JPanel headerCard = createCard();
        headerCard.setLayout(new BorderLayout(20, 0));

        JLabel avatar = new JLabel("?", SwingConstants.CENTER);
        avatar.setPreferredSize(new Dimension(80, 80));
        avatar.setOpaque(true);
        avatar.setBackground(new Color(15, 23, 42));
        avatar.setForeground(Color.WHITE);
        avatar.setFont(new Font("SansSerif", Font.BOLD, 32));

        JPanel nameInfo = new JPanel(new GridLayout(2, 1));
        nameInfo.setOpaque(false);
        JLabel nameLabel = new JLabel("Applicant Name");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        JLabel subLabel = new JLabel("Position Not Specified • Location Not Specified");
        subLabel.setForeground(Color.GRAY);
        nameInfo.add(nameLabel);
        nameInfo.add(subLabel);

        headerCard.add(avatar, BorderLayout.WEST);
        headerCard.add(nameInfo, BorderLayout.CENTER);

        // Personal Statement Card
        JPanel statementCard = createCard();
        statementCard.setLayout(new BorderLayout(0, 10));
        JLabel statementTitle = new JLabel("Personal Statement");
        statementTitle.setFont(new Font("SansSerif", Font.BOLD, 16));

        JTextArea statementText = new JTextArea("No personal statement provided.");
        statementText.setEditable(false);
        statementText.setLineWrap(true);
        statementText.setWrapStyleWord(true);
        statementText.setBackground(new Color(241, 245, 249));
        statementText.setBorder(new EmptyBorder(15, 15, 15, 15));
        statementText.setFont(new Font("Serif", Font.ITALIC, 16));
        statementText.setForeground(new Color(100, 116, 139));

        statementCard.add(statementTitle, BorderLayout.NORTH);
        statementCard.add(statementText, BorderLayout.CENTER);

        // Skills Card
        JPanel skillsCard = createCard();
        skillsCard.setLayout(new BorderLayout(0, 15));
        JLabel skillsTitle = new JLabel("Skills & Expertise");
        skillsTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel skillsPlaceholder = new JLabel("No skills listed.");
        skillsPlaceholder.setFont(new Font("SansSerif", Font.ITALIC, 14));
        skillsPlaceholder.setForeground(Color.GRAY);
        skillsCard.add(skillsTitle, BorderLayout.NORTH);
        skillsCard.add(skillsPlaceholder, BorderLayout.CENTER);

        // Documents Card
        JPanel docsCard = createCard();
        docsCard.setLayout(new BorderLayout(0, 15));
        JLabel docsTitle = new JLabel("Attached Documents");
        docsTitle.setFont(new Font("SansSerif", Font.BOLD, 16));

        JPanel dashedBox = new JPanel(new GridBagLayout());
        dashedBox.setBackground(new Color(248, 250, 252));
        dashedBox.setPreferredSize(new Dimension(0, 100));
        dashedBox.setBorder(BorderFactory.createDashedBorder(new Color(203, 213, 225), 3, 3));

        JLabel docsPlaceholder = new JLabel("No documents attached.");
        docsPlaceholder.setFont(new Font("SansSerif", Font.ITALIC, 14));
        docsPlaceholder.setForeground(new Color(148, 163, 184));
        dashedBox.add(docsPlaceholder);

        docsCard.add(docsTitle, BorderLayout.NORTH);
        docsCard.add(dashedBox, BorderLayout.CENTER);

        leftCol.add(headerCard);
        leftCol.add(Box.createVerticalStrut(20));
        leftCol.add(statementCard);
        leftCol.add(Box.createVerticalStrut(20));
        leftCol.add(skillsCard);
        leftCol.add(Box.createVerticalStrut(20));
        leftCol.add(docsCard);

        // Right Column
        JPanel rightCol = new JPanel();
        rightCol.setLayout(new BoxLayout(rightCol, BoxLayout.Y_AXIS));
        rightCol.setOpaque(false);
        rightCol.setPreferredSize(new Dimension(280, 0));

        JPanel contactCard = createCard();
        contactCard.setLayout(new GridLayout(4, 1, 0, 15));
        JLabel contactTitle = new JLabel("CONTACT DETAILS");
        contactTitle.setFont(new Font("SansSerif", Font.BOLD, 11));
        contactTitle.setForeground(Color.LIGHT_GRAY);

        contactCard.add(contactTitle);
        contactCard.add(new JLabel("Email: —"));
        contactCard.add(new JLabel("Phone: —"));
        contactCard.add(new JLabel("Preference: —"));

        rightCol.add(contactCard);
        rightCol.add(Box.createVerticalGlue());

        gbc.weightx = 0.7;
        gbc.gridx = 0;
        gbc.weighty = 1.0;
        contentPanel.add(leftCol, gbc);

        gbc.weightx = 0.3;
        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        contentPanel.add(rightCol, gbc);

        JScrollPane scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        mainPanel.add(navPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createCard() {
        JPanel card = new JPanel();
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        card.setBorder(new CompoundBorder(
                new LineBorder(new Color(226, 232, 240), 1),
                new EmptyBorder(25, 25, 25, 25)
        ));
        return card;
    }

    // ===== MAIN for testing =====
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Internship Management System");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 850);

            CardLayout cl = new CardLayout();
            JPanel mainContent = new JPanel(cl);

            // Add Application Status placeholder
            JPanel statusPage = new JPanel(new BorderLayout());
            statusPage.add(new JLabel("📋 Application Status Page", SwingConstants.CENTER), BorderLayout.CENTER);
            mainContent.add(statusPage, "ApplicationStatusPage");

            // Add Application View page
            mainContent.add(new ApplicationViewPage(mainContent, cl), "ApplicationViewPage");

            frame.add(mainContent);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            cl.show(mainContent, "ApplicationViewPage");
        });
    }
}
