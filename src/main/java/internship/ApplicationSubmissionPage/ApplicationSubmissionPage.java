package com.internship.ApplicationSubmissionPage;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class ApplicationSubmissionPage extends JFrame {

    public ApplicationSubmissionPage() {
        setTitle("Application Submission");
        setSize(700, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        add(buildContent());

        setVisible(true);
    }

    private JPanel buildContent() {

        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(new Color(245, 247, 250));

        // ===== CENTER PANEL (keeps form centered) =====
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(245, 247, 250));

        // ===== CARD =====
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        ));

        Dimension fieldSize = new Dimension(400, 35);

        // ===== TITLE =====
        JLabel title = new JLabel("Application Submission");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        card.add(title);

        // ===== NAME =====
        card.add(createLabel("Full Name"));
        card.add(createField(fieldSize));
        card.add(Box.createVerticalStrut(15));

        // ===== EMAIL =====
        card.add(createLabel("Email"));
        card.add(createField(fieldSize));
        card.add(Box.createVerticalStrut(15));

        // ===== PHONE =====
        card.add(createLabel("Phone"));
        card.add(createField(fieldSize));
        card.add(Box.createVerticalStrut(15));

        // ===== ROLE =====
        card.add(createLabel("Preferred Role"));
        JComboBox<String> roleBox = new JComboBox<>(new String[]{
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

        // ===== WORK TYPE =====
        card.add(createLabel("Work Preference"));

        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.setBackground(Color.WHITE);

        JRadioButton remote = new JRadioButton("Remote");
        JRadioButton onsite = new JRadioButton("Onsite");
        JRadioButton hybrid = new JRadioButton("Hybrid");

        remote.setBackground(Color.WHITE);
        onsite.setBackground(Color.WHITE);
        hybrid.setBackground(Color.WHITE);

        ButtonGroup group = new ButtonGroup();
        group.add(remote);
        group.add(onsite);
        group.add(hybrid);

        radioPanel.add(remote);
        radioPanel.add(onsite);
        radioPanel.add(hybrid);

        card.add(radioPanel);
        card.add(Box.createVerticalStrut(15));

        // ===== SKILLS =====
        card.add(createLabel("Skills"));

        JPanel skillPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        skillPanel.setBackground(Color.WHITE);

        skillPanel.add(new JCheckBox("HTML"));
        skillPanel.add(new JCheckBox("CSS"));
        skillPanel.add(new JCheckBox("JavaScript"));

        card.add(skillPanel);
        card.add(Box.createVerticalStrut(15));

        // ===== LOCATION =====
        card.add(createLabel("Preferred Location"));
        card.add(createField(fieldSize));
        card.add(Box.createVerticalStrut(15));

        // ===== REASON =====
        card.add(createLabel("Why are you applying?"));

        JTextArea reasonArea = new JTextArea(3, 20);
        reasonArea.setLineWrap(true);
        reasonArea.setWrapStyleWord(true);
        reasonArea.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        reasonArea.setMaximumSize(new Dimension(400, 80));

        card.add(reasonArea);
        card.add(Box.createVerticalStrut(15));

        // ===== FILE =====
        card.add(createLabel("Resume"));

        JPanel filePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filePanel.setBackground(Color.WHITE);

        JLabel fileName = new JLabel("No file chosen");

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

        // ===== SUBMIT =====
        JButton submitBtn = new JButton("Submit Application");
        submitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        submitBtn.setMaximumSize(new Dimension(400, 45));
        submitBtn.setBackground(new Color(0, 123, 255));
        submitBtn.setForeground(Color.WHITE);
        submitBtn.setFocusPainted(false);

        card.add(submitBtn);

        // ===== ADD CARD TO CENTER =====
        centerPanel.add(card);

        // ===== SCROLL (IMPORTANT FIX) =====
        JScrollPane scrollPane = new JScrollPane(centerPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ApplicationSubmissionPage::new);
    }
}