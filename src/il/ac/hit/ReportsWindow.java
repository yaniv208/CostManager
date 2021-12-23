package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class ReportsWindow
{
    private JFrame m_Window                   = null;
    private JPanel m_ReportsGenerationSection = null;
    private GridBagConstraints m_Constraints  = null;

    private JLabel m_LabelFromDate        = null;
    private JComboBox m_ComboBoxFromDay   = null;
    private JComboBox m_ComboBoxFromMonth = null;
    private JComboBox m_ComboBoxFromYear  = null;

    private JLabel m_LabelToDate        = null;
    private JComboBox m_ComboBoxToDay   = null;
    private JComboBox m_ComboBoxToMonth = null;
    private JComboBox m_ComboBoxToYear  = null;

    private JCheckBox m_CheckBoxSelectAllItems = null;
    private JButton m_ButtonGenerateReport     = null;

    private JPanel  m_LogoutSection = null;
    private JButton m_LogoutButton  = null;

    private String[] m_days   = null;
    private String[] m_months = null;
    private String[] m_years  = null;

    private String m_FromDate = null;
    private String m_ToDate   = null;

    public ReportsWindow()
    {
        this.m_days = new String[31];
        for (int i = 0; i < 31; i++)
        {
            this.m_days[i] = String.valueOf(i + 1);
        }

        this.m_months = new String[12];
        for (int i = 0; i < 12; i++)
        {
            this.m_months[i] = String.valueOf(i + 1);
        }

        this.m_years = new String[LocalDate.now().getYear() - 1970 + 1];
        for (int i = 0; i < LocalDate.now().getYear() - 1970 + 1; i++)
        {
            this.m_years[i] = String.valueOf(1970 + i);
        }

        this.InitializeComponents();
        this.PrepareWindowProperties();
        this.PrepareReportsGenerationSection();
        this.PrepareLogoutSection();
    }

    private void InitializeComponents()
    {
        this.m_Window = new JFrame();
        this.m_ReportsGenerationSection = new JPanel();

        this.m_LabelFromDate     = new JLabel();
        this.m_ComboBoxFromDay   = new JComboBox();
        this.m_ComboBoxFromMonth = new JComboBox();
        this.m_ComboBoxFromYear  = new JComboBox();

        this.m_LabelToDate      = new JLabel();
        this.m_ComboBoxToDay    = new JComboBox();
        this.m_ComboBoxToMonth  = new JComboBox();
        this.m_ComboBoxToYear   = new JComboBox();

        this.m_CheckBoxSelectAllItems = new JCheckBox("Show all transactions");
        this.m_LogoutSection = new JPanel();
        this.m_LogoutButton = new JButton("Logout");

        this.m_ButtonGenerateReport = new JButton();

        this.m_Constraints        = new GridBagConstraints();
        this.m_Constraints.insets = new Insets(5, 0, 5, 5);
    }

    private void PrepareWindowProperties()
    {
        // Add the different sections to the window
        this.m_Window.setLayout(new BorderLayout());
        this.m_Window.add(this.m_ReportsGenerationSection , BorderLayout.NORTH);
        this.m_Window.add(this.m_LogoutSection, BorderLayout.SOUTH);

        // Set the visual properties of the window
        this.m_Window.setTitle("CostMan - Reports Window");
        this.m_Window.setSize(500, 280);
        this.m_Window.setResizable(false);

        // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
        this.m_Window.setLocationRelativeTo(null);

        // Define event handlers
        this.m_Window.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                GUIUtils.HandleGoOutFromWindows(ReportsWindow.this.m_Window);
            }
        });
    }

    private void PrepareReportsGenerationSection()
    {
        this.m_ReportsGenerationSection.setLayout(new GridBagLayout());
        this.m_ReportsGenerationSection.setBorder(BorderFactory.createTitledBorder("Generate a report"));

        this.m_CheckBoxSelectAllItems.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                // Check if the checkbox is checked (Source: https://www.javatpoint.com/java-jcheckbox)
                if (e.getStateChange() == 1)
                {
                    ReportsWindow.this.m_LabelFromDate.setEnabled(false);
                    ReportsWindow.this.m_ComboBoxFromDay.setEnabled(false);
                    ReportsWindow.this.m_ComboBoxFromMonth.setEnabled(false);
                    ReportsWindow.this.m_ComboBoxFromYear.setEnabled(false);

                    ReportsWindow.this.m_LabelToDate.setEnabled(false);
                    ReportsWindow.this.m_ComboBoxToDay.setEnabled(false);
                    ReportsWindow.this.m_ComboBoxToMonth.setEnabled(false);
                    ReportsWindow.this.m_ComboBoxToYear.setEnabled(false);
                }
                else
                {
                    ReportsWindow.this.m_LabelFromDate.setEnabled(true);
                    ReportsWindow.this.m_ComboBoxFromDay.setEnabled(true);
                    ReportsWindow.this.m_ComboBoxFromMonth.setEnabled(true);
                    ReportsWindow.this.m_ComboBoxFromYear.setEnabled(true);

                    ReportsWindow.this.m_LabelToDate.setEnabled(true);
                    ReportsWindow.this.m_ComboBoxToDay.setEnabled(true);
                    ReportsWindow.this.m_ComboBoxToMonth.setEnabled(true);
                    ReportsWindow.this.m_ComboBoxToYear.setEnabled(true);
                }
            }
        });

        this.setMyConstraints(this.m_Constraints, 0, 0, GridBagConstraints.FIRST_LINE_START, 0, 10);
        this.m_ReportsGenerationSection.add(this.m_CheckBoxSelectAllItems, this.m_Constraints);

        this.PrepareFromAndToLabelsProperties();
        this.PrepareFromComboBoxesProperties();
        this.PrepareToComboBoxesProperties();

        this.m_ButtonGenerateReport.setText("Generate Report");
        this.m_ButtonGenerateReport.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // If the "Generate all transactions" checkbox is checked
                if (ReportsWindow.this.m_CheckBoxSelectAllItems.isSelected() == true)
                {
                    ReportsWindow.this.m_FromDate = "01-01-1970";

                    String toDay   = String.valueOf(LocalDate.now().getDayOfMonth());
                    String toMonth = String.valueOf(LocalDate.now().getMonth().getValue());
                    String toYear  = String.valueOf(LocalDate.now().getYear());
                    ReportsWindow.this.m_ToDate = toDay + "-" + toMonth + "-" + toYear;
                }
                else
                {
                    String fromDay   = ReportsWindow.this.m_ComboBoxFromDay.getSelectedItem().toString();
                    String fromMonth = ReportsWindow.this.m_ComboBoxFromMonth.getSelectedItem().toString();
                    String fromYear  = ReportsWindow.this.m_ComboBoxFromYear.getSelectedItem().toString();
                    ReportsWindow.this.m_FromDate = fromDay + "-" + fromMonth + "-" + fromYear;

                    String toDay   = ReportsWindow.this.m_ComboBoxToDay.getSelectedItem().toString();
                    String toMonth = ReportsWindow.this.m_ComboBoxToMonth.getSelectedItem().toString();
                    String toYear  = ReportsWindow.this.m_ComboBoxToYear.getSelectedItem().toString();
                    ReportsWindow.this.m_ToDate = toDay + "-" + toMonth + "-" + toYear;
                }


                // Check that the dates are valid
                // TODO: move this to the viewmmodel
                try
                {
                    SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

                    Date firstDate = sdf.parse(ReportsWindow.this.m_FromDate);
                    Date secondDate = sdf.parse(ReportsWindow.this.m_ToDate);

                    long diffInMillies = secondDate.getTime() - firstDate.getTime();
                    long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                    GUIUtils.ShowOkMessageBox("", String.valueOf(diffInMillies) + "\n" + String.valueOf(diff));
                }
                catch (ParseException ex)
                {
                    ex.printStackTrace();
                }


                GUIUtils.ShowOkMessageBox("Selected Dates", "FromDate: " + ReportsWindow.this.m_FromDate + "\nToDate: " + ReportsWindow.this.m_ToDate);
            }
        });
        this.setMyConstraints(this.m_Constraints, 4, 3, GridBagConstraints.CENTER, 0, 10);
        this.m_ReportsGenerationSection.add(this.m_ButtonGenerateReport, this.m_Constraints);
    }

    private void PrepareFromAndToLabelsProperties()
    {
        this.m_LabelFromDate.setText("Start Date:");
        this.m_LabelFromDate.setFont(new Font("Consolas", Font.BOLD, 14));
        this.setMyConstraints(this.m_Constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_LabelFromDate, this.m_Constraints);

        this.m_LabelToDate.setText("End Date:");
        this.m_LabelToDate.setFont(new Font("Consolas", Font.BOLD, 14));
        this.setMyConstraints(this.m_Constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_LabelToDate, this.m_Constraints);
    }

    private void PrepareFromComboBoxesProperties()
    {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
        this.m_ComboBoxFromDay   = new JComboBox();
        this.m_ComboBoxFromDay.setModel(new DefaultComboBoxModel(this.m_days));
        this.setMyConstraints(this.m_Constraints, 1, 1, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_ComboBoxFromDay, this.m_Constraints);

        this.m_ComboBoxFromMonth = new JComboBox();
        this.m_ComboBoxFromMonth.setModel(new DefaultComboBoxModel(this.m_months));
        this.setMyConstraints(this.m_Constraints, 2, 1, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_ComboBoxFromMonth, this.m_Constraints);

        this.m_ComboBoxFromYear  = new JComboBox();
        this.m_ComboBoxFromYear.setModel(new DefaultComboBoxModel(this.m_years));
        this.setMyConstraints(this.m_Constraints, 3, 1, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_ComboBoxFromYear, this.m_Constraints);
    }

    private void PrepareToComboBoxesProperties()
    {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
        this.m_ComboBoxToDay   = new JComboBox();
        this.m_ComboBoxToDay.setModel(new DefaultComboBoxModel(this.m_days));
        this.setMyConstraints(this.m_Constraints, 1, 2, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_ComboBoxToDay, this.m_Constraints);

        this.m_ComboBoxToMonth = new JComboBox();
        this.m_ComboBoxToMonth.setModel(new DefaultComboBoxModel(this.m_months));
        this.setMyConstraints(this.m_Constraints, 2, 2, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_ComboBoxToMonth, this.m_Constraints);

        this.m_ComboBoxToYear  = new JComboBox();
        this.m_ComboBoxToYear.setModel(new DefaultComboBoxModel(this.m_years));
        this.setMyConstraints(this.m_Constraints, 3, 2, GridBagConstraints.CENTER, 10, 10);
        this.m_ReportsGenerationSection.add(this.m_ComboBoxToYear, this.m_Constraints);
    }

    private void PrepareLogoutSection()
    {
        this.m_LogoutButton.setPreferredSize(new Dimension(100, this.m_LogoutButton.getPreferredSize().height));
        this.m_LogoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIUtils.ShowExitMessageBox();
            }
        });

        // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
        this.m_LogoutSection.setLayout(new BorderLayout());
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel.add(this.m_LogoutButton);
        this.m_LogoutSection.add(subPanel, BorderLayout.SOUTH);
    }

    public void Show()
    {
        this.m_Window.setVisible(true);
    }

    // TODO: rename method to more indicative name
    private void setMyConstraints(GridBagConstraints c, int gridx, int gridy, int anchor, int ipadx, int ipady) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.anchor = anchor;
        c.ipadx = ipadx;
        c.ipady = ipady;
    }
}

