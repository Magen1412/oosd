package internship.main;

import internship.dashboard.StudentDashboard;
import internship.profile.ProfilePage;
import internship.settings.SettingsPage;
import internship.support.SupportPage;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainPage extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContent;

    public MainPage() {
        setTitle("Internship Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        mainContent.add(new StudentDashboard(cardLayout, mainContent), "dashboard");
        mainContent.add(new ProfilePage(cardLayout, mainContent), "profilePage");
        mainContent.add(new SettingsPage("Student", cardLayout, mainContent), "settings");
        mainContent.add(new SupportPage(cardLayout, mainContent), "support");

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setPreferredSize(new Dimension(180, getHeight()));

        sidebar.add(navButton("Dashboard", "dashboard"));
        sidebar.add(navButton("Profile", "profilePage"));
        sidebar.add(navButton("Settings", "settings"));
        sidebar.add(navButton("Support", "support"));

        JPanel topBar = buildTopBar();

        add(sidebar, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);

        cardLayout.show(mainContent, "dashboard");
    }

    private JButton navButton(String text, String page) {
        JButton btn = new JButton(text);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.addActionListener(e -> {
            try {
                cardLayout.show(mainContent, page);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Page '" + page + "' not yet implemented.",
                        "Navigation Error",
                        JOptionPane.WARNING_MESSAGE);
            }
        });
        return btn;
    }

    private JPanel buildTopBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel greeting = new JLabel("Welcome Student");
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greeting.setForeground(Color.DARK_GRAY);

        JPanel right = new JPanel();
        right.setOpaque(false);

        FlatSVGIcon avatarIcon = new FlatSVGIcon("icons/user.svg", 25, 25);
        FlatSVGIcon gearIcon   = new FlatSVGIcon("icons/settings.svg", 25, 25);

        JLabel avatar = new JLabel(avatarIcon, SwingConstants.CENTER);
        JLabel gear   = new JLabel(gearIcon, SwingConstants.CENTER);

        avatar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        avatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainContent, "profilePage");
            }
        });

        gear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainContent, "settings");
            }
        });

        right.add(avatar);
        right.add(Box.createHorizontalStrut(10));
        right.add(gear);

        p.add(greeting, BorderLayout.WEST);
        p.add(right, BorderLayout.EAST);

        return p;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Student Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(860, 800);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            mainContent.add(new StudentDashboard(cardLayout, mainContent), "dashboard");
            mainContent.add(new SettingsPage("Student", cardLayout, mainContent), "settings");

            cardLayout.show(mainContent, "dashboard");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}
