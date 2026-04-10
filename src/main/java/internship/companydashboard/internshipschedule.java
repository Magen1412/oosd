package internship.companydashboard;


import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class internshipschedule extends JPanel {

    // ===== COLORS (matching Dashboard theme) =====
    private static final Color SIDEBAR_BG    = new Color(60, 63, 65);
    private static final Color SIDEBAR_HOVER = new Color(80, 83, 85);
    private static final Color SIDEBAR_TEXT  = new Color(220, 220, 220);
    private static final Color CONTENT_BG    = new Color(240, 240, 240);
    private static final Color CARD_BG       = Color.WHITE;
    private static final Color HEADER_BG     = new Color(200, 60, 50);
    private static final Color ACCENT        = new Color(200, 60, 50);
    private static final Color LABEL_COLOR   = new Color(80, 80, 80);
    private static final Color FIELD_BORDER  = new Color(200, 200, 200);
    private static final Color FIELD_ACTIVE  = new Color(200, 60, 50);
    private static final Color BTN_PRIMARY   = new Color(200, 60, 50);
    private static final Color BTN_BLUE      = new Color(70, 130, 180);
    private static final Color BTN_GREEN     = new Color(60, 160, 80);
    private static final Color BTN_GREY      = new Color(150, 150, 150);
    private static final Color BTN_BACK      = new Color(60, 63, 65);
    private static final Color UPLOAD_BG     = new Color(245, 248, 252);
    private static final Color UPLOAD_BORDER = new Color(180, 200, 230);

    // Status colors
    private static final Color STATUS_SCHEDULED = new Color(70, 130, 180);
    private static final Color STATUS_CONFIRMED  = new Color(60, 160, 80);
    private static final Color STATUS_CANCELLED  = new Color(200, 60, 50);
    private static final Color STATUS_PENDING    = new Color(230, 150, 40);

    // ===== CARDLAYOUT NAVIGATION =====
    private final CardLayout cardLayout;
    private final JPanel mainContent;

    // Form fields
    private JComboBox<String> cmbCandidate, cmbPosition, cmbInterviewType,
            cmbInterviewDay, cmbInterviewMonth, cmbInterviewYear,
            cmbStartHour, cmbStartMin, cmbEndHour, cmbEndMin,
            cmbInterviewer, cmbFormat;
    private JTextField txtMeetingLink, txtLocation;
    private JTextArea  txtNotes;
    private JLabel     lblAttachmentName;
    private File       attachedFile = null;

    // Table
    private DefaultTableModel tableModel;
    private JTable table;

    // Sample data
    private final Object[][] scheduledData = {
            {"001", "John Doe",   "Software Intern",   "20/03/2026", "10:00 - 11:00", "Online", "Alice Manager", "Scheduled"},
            {"002", "Jane Smith", "Web Dev Intern",    "21/03/2026", "14:00 - 15:00", "Onsite", "Bob Director",  "Confirmed"},
            {"003", "Mike Brown", "Database Intern",   "22/03/2026", "09:00 - 09:30", "Online", "Alice Manager", "Pending"},
            {"004", "Sara Lee",   "IT Support Intern", "23/03/2026", "11:00 - 12:00", "Hybrid", "Carol HR",      "Cancelled"},
    };

    // ===== CONSTRUCTOR =====
    public internshipschedule(CardLayout cardLayout, JPanel mainContent) {
        this.cardLayout  = cardLayout;
        this.mainContent = mainContent;

        setLayout(new BorderLayout());
        setBackground(CONTENT_BG);
        add(buildContent(), BorderLayout.CENTER);
    }

    // ===== MAIN CONTENT =====
    private JPanel buildContent() {
        JPanel wrapper = new JPanel(new BorderLayout());
        wrapper.setBackground(CONTENT_BG);

        JPanel pageHeader = new JPanel(new BorderLayout());
        pageHeader.setBackground(CONTENT_BG);
        pageHeader.setBorder(BorderFactory.createEmptyBorder(22, 28, 10, 28));
        JLabel pageTitle = new JLabel("Interview Scheduling");
        pageTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        pageTitle.setForeground(new Color(40, 40, 40));
        pageHeader.add(pageTitle, BorderLayout.WEST);
        wrapper.add(pageHeader, BorderLayout.NORTH);

        JPanel scrollContent = new JPanel();
        scrollContent.setLayout(new BoxLayout(scrollContent, BoxLayout.Y_AXIS));
        scrollContent.setBackground(CONTENT_BG);
        scrollContent.setBorder(BorderFactory.createEmptyBorder(0, 28, 28, 28));
        scrollContent.add(buildScheduleFormCard());
        scrollContent.add(Box.createVerticalStrut(16));
        scrollContent.add(buildScheduledListCard());

        JScrollPane scrollPane = new JScrollPane(scrollContent);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.setBackground(CONTENT_BG);
        wrapper.add(scrollPane, BorderLayout.CENTER);

        return wrapper;
    }

    // ===== CARD 1: SCHEDULE FORM =====
    private JPanel buildScheduleFormCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220, 220, 220), 1, true),
                BorderFactory.createEmptyBorder(28, 36, 28, 36)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        JLabel cardTitle = new JLabel("Schedule New Interview");
        cardTitle.setFont(new Font("Segoe UI", Font.BOLD, 15));
        cardTitle.setForeground(new Color(40, 40, 40));
        cardTitle.setBorder(BorderFactory.createEmptyBorder(0, 0, 6, 0));

        JPanel sepWrapper = new JPanel(new BorderLayout());
        sepWrapper.setBackground(CARD_BG);
        sepWrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 18, 0));
        sepWrapper.add(cardTitle, BorderLayout.NORTH);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230, 230, 230));
        sepWrapper.add(sep, BorderLayout.SOUTH);
        card.add(sepWrapper, BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(CARD_BG);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 0, 8, 20);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbCandidate   = styledCombo(new String[]{"Select Candidate","John Doe","Jane Smith","Mike Brown","Sara Lee","Chris Wong"});
        cmbPosition    = styledCombo(new String[]{"Select Position","Software Intern","Web Dev Intern","Database Intern","IT Support Intern","Software Engineer Intern"});
        cmbInterviewer = styledCombo(new String[]{"Select Interviewer","Alice Manager","Bob Director","Carol HR","David Tech Lead"});
        cmbInterviewType = styledCombo(new String[]{"Select Type","Technical","HR","Final Round","Group","Culture Fit"});
        cmbFormat      = styledCombo(new String[]{"Online","Onsite","Hybrid"});

        String[] days  = new String[31]; for (int i = 0; i < 31; i++) days[i] = String.format("%02d", i + 1);
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        String[] years  = {"2025","2026","2027"};
        cmbInterviewDay   = styledCombo(days);
        cmbInterviewMonth = styledCombo(months);
        cmbInterviewYear  = styledCombo(years); cmbInterviewYear.setSelectedItem("2026");

        String[] hours = new String[24]; for (int i = 0; i < 24; i++) hours[i] = String.format("%02d", i);
        String[] mins  = {"00","15","30","45"};
        cmbStartHour = styledCombo(hours); cmbStartHour.setSelectedItem("09");
        cmbStartMin  = styledCombo(mins);
        cmbEndHour   = styledCombo(hours); cmbEndHour.setSelectedItem("10");
        cmbEndMin    = styledCombo(mins);

        txtMeetingLink = createStyledField("https://meet.google.com/...");
        txtLocation    = createStyledField("e.g. Room 3B, Head Office");

        // Row 0: Candidate | Position
        gbc.gridx=0; gbc.gridy=0; gbc.weightx=0.2; formLabel(form,"Candidate *",gbc);
        gbc.gridx=1; gbc.weightx=0.8; form.add(cmbCandidate,gbc);
        gbc.gridx=2; gbc.weightx=0.2; formLabel(form,"Position *",gbc);
        gbc.gridx=3; gbc.weightx=0.8; form.add(cmbPosition,gbc);

        // Row 1: Interviewer | Interview Type
        gbc.gridx=0; gbc.gridy=1; gbc.weightx=0.2; formLabel(form,"Interviewer *",gbc);
        gbc.gridx=1; gbc.weightx=0.8; form.add(cmbInterviewer,gbc);
        gbc.gridx=2; gbc.weightx=0.2; formLabel(form,"Interview Type",gbc);
        gbc.gridx=3; gbc.weightx=0.8; form.add(cmbInterviewType,gbc);

        // Row 2: Date | Format
        gbc.gridx=0; gbc.gridy=2; gbc.weightx=0.2; formLabel(form,"Interview Date *",gbc);
        gbc.gridx=1; gbc.weightx=0.8;
        JPanel dateRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        dateRow.setBackground(CARD_BG);
        cmbInterviewDay.setPreferredSize(new Dimension(60,34));
        cmbInterviewMonth.setPreferredSize(new Dimension(70,34));
        cmbInterviewYear.setPreferredSize(new Dimension(75,34));
        dateRow.add(cmbInterviewDay); dateRow.add(cmbInterviewMonth); dateRow.add(cmbInterviewYear);
        form.add(dateRow,gbc);
        gbc.gridx=2; gbc.weightx=0.2; formLabel(form,"Format",gbc);
        gbc.gridx=3; gbc.weightx=0.8; form.add(cmbFormat,gbc);

        // Row 3: Start Time | End Time
        gbc.gridx=0; gbc.gridy=3; gbc.weightx=0.2; formLabel(form,"Start Time *",gbc);
        gbc.gridx=1; gbc.weightx=0.8; form.add(buildTimeRow(cmbStartHour,cmbStartMin),gbc);
        gbc.gridx=2; gbc.weightx=0.2; formLabel(form,"End Time *",gbc);
        gbc.gridx=3; gbc.weightx=0.8; form.add(buildTimeRow(cmbEndHour,cmbEndMin),gbc);

        // Row 4: Meeting Link | Location
        gbc.gridx=0; gbc.gridy=4; gbc.weightx=0.2; formLabel(form,"Meeting Link",gbc);
        gbc.gridx=1; gbc.weightx=0.8; form.add(txtMeetingLink,gbc);
        gbc.gridx=2; gbc.weightx=0.2; formLabel(form,"Location",gbc);
        gbc.gridx=3; gbc.weightx=0.8; form.add(txtLocation,gbc);

        // Row 5: Notes (full width)
        gbc.gridx=0; gbc.gridy=5; gbc.weightx=0.2;
        gbc.insets = new Insets(8,0,0,20);
        formLabel(form,"Notes",gbc);
        txtNotes = new JTextArea(3,30);
        txtNotes.setFont(new Font("Segoe UI",Font.PLAIN,13));
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
        txtNotes.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER,1,true),
                BorderFactory.createEmptyBorder(6,10,6,10)));
        JScrollPane notesScroll = new JScrollPane(txtNotes);
        notesScroll.setBorder(null);
        notesScroll.setPreferredSize(new Dimension(400,72));
        gbc.gridx=1; gbc.gridwidth=3; gbc.weightx=1.0;
        form.add(notesScroll,gbc);
        gbc.gridwidth=1;

        card.add(form, BorderLayout.CENTER);

        // Attachment + action buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(CARD_BG);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));

        JPanel attachPanel = new JPanel(new BorderLayout());
        attachPanel.setBackground(UPLOAD_BG);
        attachPanel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(UPLOAD_BORDER,1,true),
                BorderFactory.createEmptyBorder(12,16,12,16)));

        JPanel attachLeft = new JPanel(new FlowLayout(FlowLayout.LEFT,10,0));
        attachLeft.setBackground(UPLOAD_BG);
        JLabel attachIcon = new JLabel("[Attach]");
        attachIcon.setFont(new Font("Segoe UI",Font.BOLD,13));
        attachIcon.setForeground(new Color(70,130,180));
        JPanel attachText = new JPanel();
        attachText.setLayout(new BoxLayout(attachText,BoxLayout.Y_AXIS));
        attachText.setBackground(UPLOAD_BG);
        JLabel attachTitle = new JLabel("Attach Documents");
        attachTitle.setFont(new Font("Segoe UI",Font.BOLD,13));
        attachTitle.setForeground(new Color(50,50,50));
        lblAttachmentName = new JLabel("No file attached  -  Interview brief, assessment, or any relevant document");
        lblAttachmentName.setFont(new Font("Segoe UI",Font.PLAIN,11));
        lblAttachmentName.setForeground(new Color(130,130,130));
        attachText.add(attachTitle);
        attachText.add(lblAttachmentName);
        attachLeft.add(attachIcon);
        attachLeft.add(attachText);
        attachPanel.add(attachLeft,BorderLayout.CENTER);

        JPanel attachBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT,8,0));
        attachBtns.setBackground(UPLOAD_BG);
        JButton btnAttach = styledButton("Upload",BTN_BLUE); btnAttach.setPreferredSize(new Dimension(100,30));
        JButton btnClearAttach = styledButton("Clear",BTN_GREY); btnClearAttach.setPreferredSize(new Dimension(90,30));
        btnClearAttach.setEnabled(false);
        attachBtns.add(btnAttach);
        attachBtns.add(btnClearAttach);
        attachPanel.add(attachBtns,BorderLayout.EAST);

        btnAttach.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Attach Document");
            chooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                    "Documents (PDF, DOC, DOCX, XLSX, PNG, JPG)","pdf","doc","docx","xlsx","png","jpg","jpeg"));
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                attachedFile = chooser.getSelectedFile();
                long kb = attachedFile.length()/1024;
                lblAttachmentName.setText(attachedFile.getName()+"  ("+kb+" KB)");
                lblAttachmentName.setForeground(new Color(40,40,40));
                lblAttachmentName.setFont(new Font("Segoe UI",Font.BOLD,11));
                btnClearAttach.setEnabled(true);
            }
        });
        btnClearAttach.addActionListener(e -> {
            attachedFile = null;
            lblAttachmentName.setText("No file attached  -  Interview brief, assessment, or any relevant document");
            lblAttachmentName.setForeground(new Color(130,130,130));
            lblAttachmentName.setFont(new Font("Segoe UI",Font.PLAIN,11));
            btnClearAttach.setEnabled(false);
        });

        JPanel actionBtns = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        actionBtns.setBackground(CARD_BG);
        actionBtns.setBorder(BorderFactory.createEmptyBorder(14,0,0,0));
        JButton btnClearForm = styledButton("Clear Form",BTN_GREY);
        JButton btnSchedule  = styledButton("Schedule",BTN_PRIMARY); btnSchedule.setPreferredSize(new Dimension(130,34));
        actionBtns.add(btnClearForm);
        actionBtns.add(btnSchedule);
        btnClearForm.addActionListener(e -> clearForm());
        btnSchedule.addActionListener(e -> scheduleInterview());

        bottomPanel.add(attachPanel,BorderLayout.CENTER);
        bottomPanel.add(actionBtns,BorderLayout.SOUTH);
        card.add(bottomPanel,BorderLayout.SOUTH);

        return card;
    }

    // ===== CARD 2: SCHEDULED LIST =====
    private JPanel buildScheduledListCard() {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(220,220,220),1,true),
                BorderFactory.createEmptyBorder(24,28,24,28)));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE,Integer.MAX_VALUE));

        JLabel cardTitle = new JLabel("Scheduled Interviews");
        cardTitle.setFont(new Font("Segoe UI",Font.BOLD,15));
        cardTitle.setForeground(new Color(40,40,40));
        cardTitle.setBorder(BorderFactory.createEmptyBorder(0,0,6,0));

        JPanel sepWrapper = new JPanel(new BorderLayout());
        sepWrapper.setBackground(CARD_BG);
        sepWrapper.setBorder(BorderFactory.createEmptyBorder(0,0,14,0));
        sepWrapper.add(cardTitle,BorderLayout.NORTH);
        JSeparator sep = new JSeparator();
        sep.setForeground(new Color(230,230,230));
        sepWrapper.add(sep,BorderLayout.SOUTH);
        card.add(sepWrapper,BorderLayout.NORTH);

        String[] cols = {"ID","Candidate","Position","Date","Time","Format","Interviewer","Status"};
        tableModel = new DefaultTableModel(scheduledData,cols) {
            public boolean isCellEditable(int r,int c) { return false; }
        };

        table = new JTable(tableModel) {
            public Component prepareRenderer(TableCellRenderer r,int row,int col) {
                Component c = super.prepareRenderer(r,row,col);
                if (!isRowSelected(row))
                    c.setBackground(row%2==0 ? Color.WHITE : new Color(248,249,250));
                return c;
            }
        };

        table.setFont(new Font("Segoe UI",Font.PLAIN,13));
        table.setRowHeight(40);
        table.setShowVerticalLines(false);
        table.setGridColor(new Color(235,235,235));
        table.setSelectionBackground(new Color(232,240,254));
        table.setSelectionForeground(new Color(30,30,30));
        table.setIntercellSpacing(new Dimension(0,1));
        table.setFocusable(false);

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI",Font.BOLD,13));
        header.setBackground(new Color(245,245,245));
        header.setForeground(LABEL_COLOR);
        header.setPreferredSize(new Dimension(0,42));
        header.setBorder(BorderFactory.createMatteBorder(0,0,2,0,new Color(220,220,220)));
        header.setReorderingAllowed(false);

        int[] widths = {40,120,160,90,110,70,120,100};
        for (int i=0;i<widths.length;i++)
            table.getColumnModel().getColumn(i).setPreferredWidth(widths[i]);

        // Status badge renderer
        table.getColumnModel().getColumn(7).setCellRenderer(new DefaultTableCellRenderer() {
            public Component getTableCellRendererComponent(JTable t,Object value,
                                                           boolean sel,boolean focus,int row,int col) {
                String status = value!=null ? value.toString() : "";
                Color bg = switch(status) {
                    case "Scheduled" -> STATUS_SCHEDULED;
                    case "Confirmed" -> STATUS_CONFIRMED;
                    case "Cancelled" -> STATUS_CANCELLED;
                    case "Pending"   -> STATUS_PENDING;
                    default          -> BTN_GREY;
                };
                JLabel badge = new JLabel(status,JLabel.CENTER);
                badge.setFont(new Font("Segoe UI",Font.BOLD,11));
                badge.setOpaque(true);
                badge.setBackground(bg);
                badge.setForeground(Color.WHITE);
                JPanel roundBadge = new JPanel() {
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D)g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
                        g2.setColor(bg);
                        g2.fillRoundRect(0,0,getWidth(),getHeight(),12,12);
                        g2.dispose();
                    }
                };
                roundBadge.setLayout(new BorderLayout());
                roundBadge.setOpaque(false);
                roundBadge.setPreferredSize(new Dimension(85,26));
                roundBadge.add(badge);
                JPanel cell = new JPanel(new FlowLayout(FlowLayout.CENTER,0,7));
                cell.setBackground(sel ? table.getSelectionBackground()
                        : (row%2==0 ? Color.WHITE : new Color(248,249,250)));
                cell.add(roundBadge);
                return cell;
            }
        });

        DefaultTableCellRenderer center = new DefaultTableCellRenderer();
        center.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(center);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(220,220,220),1));
        scrollPane.getViewport().setBackground(Color.WHITE);
        card.add(scrollPane,BorderLayout.CENTER);

        // ===== BUTTONS — Back goes to admin dashboard via CardLayout =====
        JPanel btnRow = new JPanel(new FlowLayout(FlowLayout.RIGHT,10,0));
        btnRow.setBackground(CARD_BG);
        btnRow.setBorder(BorderFactory.createEmptyBorder(14,0,0,0));

        JButton btnBack   = styledButton("< Back",        BTN_BACK);
        JButton btnCancel = styledButton("Cancel Interview", new Color(200,60,50));
        btnCancel.setPreferredSize(new Dimension(160,34));

        btnRow.add(btnBack);
        btnRow.add(btnCancel);
        card.add(btnRow,BorderLayout.SOUTH);

        // Back navigates to the admin dashboard card
        btnBack.addActionListener(e -> cardLayout.show(mainContent,"companyDashboard"));

        btnCancel.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row<0) {
                JOptionPane.showMessageDialog(this,"Please select an interview to cancel.",
                        "No Selection",JOptionPane.WARNING_MESSAGE);
                return;
            }
            String candidate = tableModel.getValueAt(row,1).toString();
            if (JOptionPane.showConfirmDialog(this,"Cancel the interview for "+candidate+"?",
                    "Confirm Cancellation",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
                tableModel.setValueAt("Cancelled",row,7);
            }
        });

        return card;
    }

    // ===== SCHEDULE ACTION =====
    private void scheduleInterview() {
        if (cmbCandidate.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(this,"Please select a candidate.","Validation Error",JOptionPane.WARNING_MESSAGE); return;
        }
        if (cmbPosition.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(this,"Please select a position.","Validation Error",JOptionPane.WARNING_MESSAGE); return;
        }
        if (cmbInterviewer.getSelectedIndex()==0) {
            JOptionPane.showMessageDialog(this,"Please select an interviewer.","Validation Error",JOptionPane.WARNING_MESSAGE); return;
        }
        String candidate   = (String)cmbCandidate.getSelectedItem();
        String position    = (String)cmbPosition.getSelectedItem();
        String date        = cmbInterviewDay.getSelectedItem()+"/"+
                String.format("%02d",cmbInterviewMonth.getSelectedIndex()+1)+"/"+
                cmbInterviewYear.getSelectedItem();
        String time        = cmbStartHour.getSelectedItem()+":"+cmbStartMin.getSelectedItem()+
                " - "+cmbEndHour.getSelectedItem()+":"+cmbEndMin.getSelectedItem();
        String format      = (String)cmbFormat.getSelectedItem();
        String interviewer = (String)cmbInterviewer.getSelectedItem();
        int newId = tableModel.getRowCount()+1;
        tableModel.addRow(new Object[]{
                String.format("%03d",newId),candidate,position,date,time,format,interviewer,"Scheduled"
        });
        JOptionPane.showMessageDialog(this,
                "Interview scheduled!\n\nCandidate: "+candidate+"\nDate: "+date+"  |  Time: "+time+
                        "\nInterviewer: "+interviewer,"Scheduled",JOptionPane.INFORMATION_MESSAGE);
        clearForm();
    }

    // ===== CLEAR FORM =====
    private void clearForm() {
        cmbCandidate.setSelectedIndex(0);
        cmbPosition.setSelectedIndex(0);
        cmbInterviewer.setSelectedIndex(0);
        cmbInterviewType.setSelectedIndex(0);
        cmbFormat.setSelectedIndex(0);
        cmbInterviewDay.setSelectedIndex(0);
        cmbInterviewMonth.setSelectedIndex(0);
        cmbInterviewYear.setSelectedItem("2026");
        cmbStartHour.setSelectedItem("09");
        cmbStartMin.setSelectedIndex(0);
        cmbEndHour.setSelectedItem("10");
        cmbEndMin.setSelectedIndex(0);
        txtMeetingLink.setText("");
        txtLocation.setText("");
        txtNotes.setText("");
        attachedFile = null;
        lblAttachmentName.setText("No file attached  -  Interview brief, assessment, or any relevant document");
        lblAttachmentName.setForeground(new Color(130,130,130));
        lblAttachmentName.setFont(new Font("Segoe UI",Font.PLAIN,11));
    }

    // ===== HELPERS =====
    private void formLabel(JPanel panel,String text,GridBagConstraints gbc) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI",Font.PLAIN,13));
        lbl.setForeground(LABEL_COLOR);
        panel.add(lbl,gbc);
    }

    private JComboBox<String> styledCombo(String[] items) {
        JComboBox<String> cmb = new JComboBox<>(items);
        cmb.setFont(new Font("Segoe UI",Font.PLAIN,13));
        cmb.setPreferredSize(new Dimension(180,34));
        cmb.setBackground(Color.WHITE);
        return cmb;
    }

    private JTextField createStyledField(String placeholder) {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI",Font.PLAIN,13));
        field.setPreferredSize(new Dimension(180,34));
        field.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(FIELD_BORDER,1,true),
                BorderFactory.createEmptyBorder(4,10,4,10)));
        field.setForeground(new Color(160,160,160));
        field.setText(placeholder);
        field.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) { field.setText(""); field.setForeground(new Color(30,30,30)); }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(FIELD_ACTIVE,2,true),BorderFactory.createEmptyBorder(3,9,3,9)));
            }
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) { field.setText(placeholder); field.setForeground(new Color(160,160,160)); }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new LineBorder(FIELD_BORDER,1,true),BorderFactory.createEmptyBorder(4,10,4,10)));
            }
        });
        return field;
    }

    private JPanel buildTimeRow(JComboBox<String> hourBox,JComboBox<String> minBox) {
        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT,6,0));
        row.setBackground(CARD_BG);
        hourBox.setPreferredSize(new Dimension(65,34));
        minBox.setPreferredSize(new Dimension(60,34));
        JLabel colon = new JLabel(":");
        colon.setFont(new Font("Segoe UI",Font.BOLD,15));
        row.add(hourBox); row.add(colon); row.add(minBox);
        return row;
    }

    private JButton styledButton(String text,Color bg) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI",Font.BOLD,13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(bg);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(110,34));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { btn.setBackground(bg.darker()); }
            public void mouseExited(MouseEvent e)  { btn.setBackground(bg); }
        });
        return btn;
    }

    // ===== MAIN — for standalone testing, same pattern as SettingsPage =====
    public static void main(String[] args) {
        try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
        catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Interview Scheduling");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1050, 820);
            frame.setLocationRelativeTo(null);

            CardLayout cardLayout = new CardLayout();
            JPanel mainContent = new JPanel(cardLayout);

            JPanel companydashboard = new Companydashboard(mainContent,cardLayout); // your real panel
            mainContent.add(companydashboard, "companyDashboard");
            mainContent.add(new internshipschedule(cardLayout, mainContent), "interviewScheduling");




            cardLayout.show(mainContent, "interviewScheduling");

            frame.setContentPane(mainContent);
            frame.setVisible(true);
        });
    }
}