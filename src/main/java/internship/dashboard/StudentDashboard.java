package internship.dashboard;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

import internship.searchpage.SearchPage;
import internship.settings.SettingsPage;
import internship.support.SupportPage;
import java.util.Arrays;

import com.formdev.flatlaf.extras.FlatSVGIcon;

public class StudentDashboard extends JPanel {

    static final Color SIDEBAR_TOP = new Color(61,138,181);
    static final Color SIDEBAR_BOT = new Color(30,78,112);
    static final Color BG = new Color(240,245,249);
    static final Color WHITE = Color.WHITE;
    static final Color TEXT_DARK = new Color(30,58,95);

    static final Color CARD1_A = new Color(74,127,160);
    static final Color CARD1_B = new Color(90,154,184);
    static final Color CARD2_A = new Color(90,181,200);
    static final Color CARD2_B = new Color(110,203,219);
    static final Color CARD3_A = new Color(46,74,107);
    static final Color CARD3_B = new Color(58,95,136);

    private CardLayout cardLayout;
    private JPanel mainContent;

    public StudentDashboard(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        add(buildSidebar(), BorderLayout.WEST);
        add(buildMain(), BorderLayout.CENTER);
    }

    // Sidebar separator line
    private JPanel sidebarSeparator() {
        JPanel line = new JPanel();
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        line.setBackground(new Color(255, 255, 255, 60));
        return line;
    }

    // Sidebar with buttons
    JPanel buildSidebar() {
        JPanel sidebar = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0,0,SIDEBAR_TOP,0,getHeight(),SIDEBAR_BOT);
                g2.setPaint(gp);
                g2.fillRect(0,0,getWidth(),getHeight());
            }
        };

        sidebar.setPreferredSize(new Dimension(220,0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.add(Box.createVerticalStrut(30));

        String[] items = {"Dashboard","Browse Internships","My Applications","Support","Log Out"};
        for (int i = 0; i < items.length; i++) {
            sidebar.add(createSidebarButton(items[i]));
            sidebar.add(Box.createVerticalStrut(5));
            if (i < items.length - 1) {
                sidebar.add(sidebarSeparator());
                sidebar.add(Box.createVerticalStrut(5));
            }
        }

        return sidebar;
    }

    // Unified sidebar button method
    JButton createSidebarButton(String text) {
        JButton btn = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw hover background
                if (getModel().isRollover()) {
                    g2.setColor(new Color(255,255,255,40)); // translucent white
                    g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                }

                // Let Swing paint the text once
                super.paintComponent(g);
            }
        };

        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false); // no default background
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setBorder(new EmptyBorder(10,20,10,10));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setMaximumSize(new Dimension(200,40));

        // Navigation
        btn.addActionListener(e -> {
            switch (text) {
                case "Dashboard": cardLayout.show(mainContent, "studentDashboard"); break;
                case "Browse Internships": cardLayout.show(mainContent, "browseInternships"); break;
                case "My Applications": cardLayout.show(mainContent, "applicationsPage"); break;
                case "Support": cardLayout.show(mainContent, "supportPage"); break;
                case "Log Out": cardLayout.show(mainContent, "loginPage"); break;
            }
        });

        return btn;
    }

    // Main content
    JPanel buildMain() {
        JPanel main = new JPanel();
        main.setBackground(BG);
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBorder(new EmptyBorder(20,20,20,20));

        main.add(topBar());
        main.add(Box.createVerticalStrut(20));
        main.add(statsRow());
        main.add(Box.createVerticalStrut(25));
        main.add(recommendedSection());
        main.add(Box.createVerticalStrut(25));
        main.add(loadMore());

        return main;
    }

    JPanel topBar() {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);

        JLabel greeting = new JLabel("Welcome Student");
        greeting.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greeting.setForeground(TEXT_DARK);

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
                JOptionPane.showMessageDialog(null, "Opening Profile Page...");
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

    JPanel statsRow() {
        JPanel p = new JPanel(new GridLayout(1,3,20,0));
        p.setOpaque(false);
        p.add(new StatCard("Applied","12","Applications sent",CARD1_A,CARD1_B));
        p.add(new StatCard("Shortlisted","5","Companies interested",CARD2_A,CARD2_B));
        p.add(new StatCard("Offers","2","Offers received",CARD3_A,CARD3_B));
        return p;
    }

    JPanel recommendedSection() {
        JPanel panel = new RoundedCard(20, WHITE);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Recommended Internships", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        title.setForeground(TEXT_DARK);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(title);
        panel.add(Box.createVerticalStrut(15));

        panel.add(jobItem("Software Engineering Intern","Google"));
        panel.add(jobItem("UI/UX Design Intern","Adobe"));
        panel.add(jobItem("Data Science Intern","Microsoft"));
        panel.add(jobItem("Cybersecurity Intern","IBM"));
        panel.add(jobItem("Cloud Engineering Intern","Amazon Web Services"));
        panel.add(jobItem("AI Research Intern","OpenAI"));
        panel.add(jobItem("Machine Learning Intern","NVIDIA"));
        panel.add(jobItem("Mobile App Development Intern","Meta"));
        panel.add(jobItem("Game Development Intern","Electronic Arts"));
        panel.add(jobItem("DevOps Intern","Red Hat"));
        panel.add(jobItem("Full Stack Developer Intern","Oracle"));
        panel.add(jobItem("Business Analyst Intern (Tech)","Deloitte"));

        JScrollPane scroll = new JScrollPane(panel);
        scroll.setBorder(null);
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setPreferredSize(new Dimension(600,500));

        JPanel container = new JPanel(new BorderLayout());
        container.setOpaque(false);
        container.add(scroll, BorderLayout.CENTER);

        return container;
    }

    JPanel jobItem(String role, String company) {
        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.setBorder(new EmptyBorder(10,0,10,0));

        JLabel l = new JLabel("<html><b>"+role+"</b><br>"+company+"</html>");
        l.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        l.setForeground(TEXT_DARK);

        JButton apply = new JButton("Apply");
        apply.setPreferredSize(new Dimension(100, 30));
        apply.setBackground(new Color(61,138,181));
        apply.setForeground(Color.WHITE);
        apply.setFocusPainted(false);

        apply.addActionListener(e -> JOptionPane.showMessageDialog(this,"Applied to "+role));

        p.add(l,BorderLayout.WEST);
        p.add(apply,BorderLayout.EAST);

        return p;
    }

    // Search Internships
    JPanel loadMore() {
        JPanel p = new JPanel();
        p.setBackground(new Color(200,230,240));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE,50));

        JLabel l = new JLabel("Search Internships");
        l.setFont(new Font("Segoe UI", Font.BOLD, 14));
        l.setForeground(TEXT_DARK);
        p.add(l);

        p.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        p.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                cardLayout.show(mainContent, "browseInternships");
            }
        });

        return p;
    }

    // ---------------- Main ----------------
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Student Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(860, 800);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            // Add dashboard and settings page
            mainContent.add(new StudentDashboard(cardLayout, mainContent), "dashboard");
            mainContent.add(new SettingsPage("Student", cardLayout, mainContent), "settings");
            //mainContent.add(new SearchPage(cardLayout, mainContent), "browseInternships");
            // mainContent.add(new ApplicationsPage(cardLayout, mainContent), "applicationsPage");
            mainContent.add(new SupportPage(cardLayout, mainContent), "supportPage");
            // mainContent.add(new LoginPage(cardLayout, mainContent), "loginPage");

            // Show dashboard first
            cardLayout.show(mainContent, "studentDashboard");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}

class RoundedCard extends JPanel {
    int r; Color bg;
    RoundedCard(int r, Color bg) {
        this.r = r; this.bg = bg;
        setOpaque(false);
    }
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(bg);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),r,r);
    }
}

// Statistic card with gradient background
class StatCard extends JPanel {
    Color a,b;
    String label,value,sub;
    StatCard(String label,String value,String sub,Color a,Color b) {
        this.a=a; this.b=b;
        this.label=label; this.value=value; this.sub=sub;
        setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(15,15,15,15));
        setOpaque(false);

        JLabel l=new JLabel(label);
        JLabel v=new JLabel(value);
        JLabel s=new JLabel(sub);

        l.setForeground(Color.WHITE);
        v.setForeground(Color.WHITE);
        s.setForeground(Color.WHITE);

        l.setFont(new Font("Segoe UI",Font.PLAIN,14));
        v.setFont(new Font("Segoe UI",Font.BOLD,28));
        s.setFont(new Font("Segoe UI",Font.PLAIN,12));

        add(l); add(Box.createVerticalStrut(5));
        add(v); add(Box.createVerticalStrut(5));
        add(s);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2=(Graphics2D)g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint gp=new GradientPaint(0,0,a,getWidth(),getHeight(),b);
        g2.setPaint(gp);
        g2.fillRoundRect(0,0,getWidth(),getHeight(),25,25);
        g2.setColor(new Color(0,0,0,40));
        g2.drawRoundRect(0,0,getWidth()-1,getHeight()-1,25,25);
    }
}