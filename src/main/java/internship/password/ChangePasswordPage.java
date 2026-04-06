package internship.password;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class ChangePasswordPage extends JFrame {

    // ===== UI COMPONENTS =====
    private JPasswordField txtOldPass;
    private JPasswordField txtNewPass;
    private JPasswordField txtConfirmPass;

    private JButton btnChange;
    private JButton btnBack;

    // ===== THEME COLOURS (matches project palette) =====
    private static final Color PRIMARY     = new Color(30, 90, 160);   // deep blue
    private static final Color PRIMARY_HOV = new Color(20, 65, 120);
    private static final Color ACCENT      = new Color(0, 168, 120);   // teal accent
    private static final Color BG          = new Color(245, 247, 252);
    private static final Color CARD_BG     = Color.WHITE;
    private static final Color TEXT_DARK   = new Color(30, 35, 50);
    private static final Color TEXT_MUTED  = new Color(110, 120, 140);
    private static final Color BORDER_CLR  = new Color(210, 215, 230);

    private static final Font  FONT_TITLE  = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font  FONT_LABEL  = new Font("Segoe UI", Font.PLAIN, 13);
    private static final Font  FONT_FIELD  = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font  FONT_BTN    = new Font("Segoe UI", Font.BOLD, 13);

    private String currentPassword = "1234";

    // ===================================================
    public ChangePasswordPage() {

        setTitle("Internship Management System – Change Password");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(680, 440));
        setSize(920, 560);

        // Root content pane with custom background
        JPanel root = new JPanel(new GridLayout(1, 2, 0, 0)) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(BG);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        root.setBackground(BG);
        setContentPane(root);

        root.add(buildLeftPanel());
        root.add(buildRightPanel());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ===================================================
    // LEFT PANEL – branding / logo
    // ===================================================
    private JPanel buildLeftPanel() {

        JPanel panel = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                // Gradient background
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY,
                        0, getHeight(), new Color(10, 50, 110));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());

                // Decorative circle (top-right)
                g2.setColor(new Color(255, 255, 255, 18));
                g2.fillOval(getWidth() - 100, -80, 220, 220);

                // Decorative circle (bottom-left)
                g2.fillOval(-60, getHeight() - 140, 200, 200);
                g2.dispose();
            }
        };
        panel.setOpaque(false);

        // ── Logo area ──
        JPanel logoWrap = new JPanel();
        logoWrap.setLayout(new BoxLayout(logoWrap, BoxLayout.Y_AXIS));
        logoWrap.setOpaque(false);
        logoWrap.setBorder(new EmptyBorder(0, 20, 0, 20));

        // Try to load the logo; fall back to text initials
        JLabel logoLabel;
        ImageIcon raw = new ImageIcon("src/smart.png");
        if (raw.getIconWidth() > 0) {
            Image scaled = raw.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            logoLabel = new JLabel(new ImageIcon(scaled));
        } else {
            logoLabel = new JLabel("IMS") {{
                setFont(new Font("Segoe UI", Font.BOLD, 48));
                setForeground(Color.WHITE);
            }};
        }
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appName = new JLabel("Internship");
        appName.setFont(new Font("Segoe UI", Font.BOLD, 20));
        appName.setForeground(Color.WHITE);
        appName.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appSub = new JLabel("Management System");
        appSub.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        appSub.setForeground(new Color(180, 210, 255));
        appSub.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Thin divider
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(255, 255, 255, 60));
        sep.setMaximumSize(new Dimension(140, 1));
        sep.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel tagline = new JLabel("Secure your account");
        tagline.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        tagline.setForeground(new Color(160, 200, 255));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoWrap.add(Box.createVerticalGlue());
        logoWrap.add(logoLabel);
        logoWrap.add(Box.createVerticalStrut(14));
        logoWrap.add(appName);
        logoWrap.add(Box.createVerticalStrut(4));
        logoWrap.add(appSub);
        logoWrap.add(Box.createVerticalStrut(16));
        logoWrap.add(sep);
        logoWrap.add(Box.createVerticalStrut(12));
        logoWrap.add(tagline);
        logoWrap.add(Box.createVerticalGlue());

        panel.add(logoWrap, BorderLayout.CENTER);
        return panel;
    }

    // ===================================================
    // RIGHT PANEL – change-password form
    // ===================================================
    private JPanel buildRightPanel() {

        // Outer panel centres the card vertically & horizontally
        JPanel outer = new JPanel(new GridBagLayout());
        outer.setBackground(BG);
        outer.setBorder(new EmptyBorder(24, 24, 24, 24));

        // Card (white rounded rectangle effect via border)
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(32, 36, 32, 36)));

        GridBagConstraints c = new GridBagConstraints();
        c.fill      = GridBagConstraints.HORIZONTAL;
        c.insets    = new Insets(6, 0, 6, 0);
        c.weightx   = 1.0;
        c.gridx     = 0;
        int row     = 0;

        // ── Title ──
        JLabel title = new JLabel("Change Password");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_DARK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        c.gridy = row++;
        c.insets = new Insets(0, 0, 18, 0);
        card.add(title, c);
        c.insets = new Insets(6, 0, 4, 0);

        // ── Old Password ──
        card.add(makeLabel("Old Password"), setRow(c, row++));
        txtOldPass = makePassField();
        card.add(txtOldPass, setRow(c, row++));

        // ── New Password ──
        card.add(makeLabel("New Password"), setRow(c, row++));
        txtNewPass = makePassField();
        card.add(txtNewPass, setRow(c, row++));

        // ── Confirm Password ──
        card.add(makeLabel("Confirm New Password"), setRow(c, row++));
        txtConfirmPass = makePassField();
        card.add(txtConfirmPass, setRow(c, row++));

        // ── Buttons (fully centred) ──
        c.gridy  = row;
        c.fill   = GridBagConstraints.NONE;   // <-- don't stretch buttons
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(22, 0, 0, 0);

        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 14, 0));
        btnRow.setBackground(CARD_BG);

        btnChange = makeButton("Change Password", PRIMARY, Color.WHITE);
        btnBack   = makeButton("Back",            CARD_BG, PRIMARY);
        btnBack.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(PRIMARY, 1, true),
                new EmptyBorder(8, 22, 8, 22)));

        btnRow.add(btnChange);
        btnRow.add(btnBack);
        card.add(btnRow, c);

        // Put card inside outer with both weights so it stays centred
        GridBagConstraints oc = new GridBagConstraints();
        oc.weightx = 1; oc.weighty = 1;
        oc.fill    = GridBagConstraints.NONE;
        oc.anchor  = GridBagConstraints.CENTER;
        outer.add(card, oc);

        // ── Wire events ──
        btnChange.addActionListener(e -> changePassword());
        btnBack.addActionListener(e -> dispose());

        // Allow Enter key on the last field to trigger change
        txtConfirmPass.addActionListener(e -> changePassword());

        return outer;
    }

    // ===================================================
    // HELPERS – factory methods
    // ===================================================
    private JLabel makeLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(FONT_LABEL);
        lbl.setForeground(TEXT_MUTED);
        return lbl;
    }

    private JPasswordField makePassField() {
        JPasswordField f = new JPasswordField() {
            @Override public Dimension getPreferredSize() {
                return new Dimension(280, 38);
            }
        };
        f.setFont(FONT_FIELD);
        f.setForeground(TEXT_DARK);
        f.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(BORDER_CLR, 1, true),
                new EmptyBorder(6, 10, 6, 10)));
        f.setBackground(new Color(250, 251, 255));

        // Highlight border on focus
        f.addFocusListener(new FocusAdapter() {
            @Override public void focusGained(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(PRIMARY, 2, true),
                        new EmptyBorder(5, 9, 5, 9)));
            }
            @Override public void focusLost(FocusEvent e) {
                f.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(BORDER_CLR, 1, true),
                        new EmptyBorder(6, 10, 6, 10)));
            }
        });
        return f;
    }

    private JButton makeButton(String text, Color bg, Color fg) {
        JButton btn = new JButton(text) {
            @Override protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                if (getModel().isPressed()) {
                    g2.setColor(bg.darker());
                } else if (getModel().isRollover() && bg.equals(PRIMARY)) {
                    g2.setColor(PRIMARY_HOV);
                } else {
                    g2.setColor(bg);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(FONT_BTN);
        btn.setForeground(fg);
        btn.setBackground(bg);
        btn.setOpaque(false);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setBorder(new EmptyBorder(9, 24, 9, 24));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setFocusPainted(false);
        return btn;
    }

    /** Convenience: set gridy and return the same constraint object */
    private GridBagConstraints setRow(GridBagConstraints c, int row) {
        c.gridy  = row;
        c.fill   = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.WEST;
        return c;
    }

    // ===================================================
    // CHANGE PASSWORD LOGIC
    // ===================================================
    private void changePassword() {

        String oldPass     = new String(txtOldPass.getPassword()).trim();
        String newPass     = new String(txtNewPass.getPassword()).trim();
        String confirmPass = new String(txtConfirmPass.getPassword()).trim();

        if (oldPass.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
            showError("All fields are required.");
            return;
        }
        if (!oldPass.equals(currentPassword)) {
            showError("Old password is incorrect.");
            return;
        }
        if (newPass.length() < 6) {
            showError("New password must be at least 6 characters.");
            return;
        }
        if (!newPass.equals(confirmPass)) {
            showError("New passwords do not match.");
            return;
        }
        if (newPass.equals(currentPassword)) {
            showError("New password must differ from the current password.");
            return;
        }

        currentPassword = newPass;
        JOptionPane.showMessageDialog(this,
                "Password changed successfully!",
                "Success", JOptionPane.INFORMATION_MESSAGE);

        // Clear all fields
        txtOldPass.setText("");
        txtNewPass.setText("");
        txtConfirmPass.setText("");
        txtOldPass.requestFocusInWindow();
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Validation Error",
                JOptionPane.WARNING_MESSAGE);
    }

    // ===================================================
    public static void main(String[] args) {
        // Respect the OS look-and-feel for native controls
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(ChangePasswordPage::new);
    }
}