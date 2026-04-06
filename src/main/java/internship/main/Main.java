package internship.main;

import internship.dashboard.DashboardMain;
import internship.profile.ProfilePage;
import internship.registration.RegistrationPage;
//import internship.settings.SettingsPage;
//import internship.search.SearchInternships;
import internship.support.SupportPage;
//import internship.auth.Logout;

import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainContent;

    public Main() {
        setTitle("InternPath Dashboard");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainContent = new JPanel(cardLayout);

        // Register all pages
        mainContent.add(new DashboardMain(), "dashboard");
        mainContent.add(new ProfilePage(), "profile");
        //mainContent.add(new SettingsPage(), "settings");
        //mainContent.add(new SearchInternships(), "search");
        mainContent.add(new SupportPage(), "support");
        //mainContent.add(new Logout(), "logout");

        // Sidebar navigation
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.add(navButton("Dashboard", "dashboard"));
        sidebar.add(navButton("Profile", "profile"));
        sidebar.add(navButton("Settings", "settings"));
        sidebar.add(navButton("Search Internships", "search"));
        sidebar.add(navButton("Support", "support"));
        sidebar.add(navButton("Logout", "logout"));

        // Top bar with greeting + icons
        JPanel topBar = buildTopBar();

        // Layout: sidebar left, topBar north, main content center
        add(sidebar, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);
        add(mainContent, BorderLayout.CENTER);

        cardLayout.show(mainContent, "dashboard"); // start with dashboard
    }

    private JButton navButton(String text, String page) {
        JButton btn = new JButton(text);
        btn.addActionListener(e -> cardLayout.show(mainContent, page));
        return btn;
    }

    private JPanel buildTopBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel greeting = new JLabel("Welcome Student");
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greeting.setForeground(Color.DARK_GRAY);

        JPanel right = new JPanel();
        right.setOpaque(false);

        FlatSVGIcon avatarIcon = new FlatSVGIcon("icons/user.svg", 25, 25);
        FlatSVGIcon gearIcon   = new FlatSVGIcon("icons/settings.svg", 25, 25);

        JLabel avatar = new JLabel(avatarIcon, SwingConstants.CENTER);
        JLabel gear   = new JLabel(gearIcon, SwingConstants.CENTER);

        // Make them clickable
        avatar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        avatar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainContent, "profile"); // go to Profile page
            }
        });

        gear.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        gear.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainContent, "settings"); // go to Settings page
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
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}
