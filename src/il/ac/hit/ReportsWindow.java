package il.ac.hit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Aviv
 */
public class ReportsWindow implements IView
{
    private JFrame frame;
    private JPanel reportsGenerationSectionPanel;
    private GridBagConstraints constraints;

    private JLabel labelFromDate;
    private JComboBox<String> comboBoxFromDay, comboBoxFromMonth, comboBoxFromYear;

    private JLabel labelToDate;
    private JComboBox<String> comboBoxToDay, comboBoxToMonth, comboBoxToYear;

    private JCheckBox checkBoxSelectAllItems;
    private JButton buttonGenerateReport;

    private JPanel logoutSectionPanel, subPanel;
    private JButton logoutButton;

    private String[] days;
    private String[] months;
    private String[] years;

    private String fromDate, fromDay, fromMonth, fromYear, toDate, toDay, toMonth, toYear;

    private SimpleDateFormat sdf;
    private Date firstDate, secondDate;
    private long diff, diffInMillis;

    private JTable itemsTable;
    private String[] columnNames;
    private JScrollPane scrollPane;
    private DefaultTableModel data;

    private IViewModel vm;

    public ReportsWindow()
    {
        this.days = new String[31];
        for (int i = 0; i < 31; i++)
        {
            this.days[i] = String.valueOf(i + 1);
        }

        this.months = new String[12];
        for (int i = 0; i < 12; i++)
        {
            this.months[i] = String.valueOf(i + 1);
        }

        this.years = new String[LocalDate.now().getYear() - 1970 + 1];
        for (int i = 0; i < LocalDate.now().getYear() - 1970 + 1; i++)
        {
            this.years[i] = String.valueOf(1970 + i);
        }

        this.InitializeComponents();
        this.PrepareWindowProperties();
        this.PrepareReportsGenerationSection();
        this.PrepareLogoutSection();
    }

    private void InitializeComponents()
    {
        this.frame = new JFrame();
        this.reportsGenerationSectionPanel = new JPanel();

        this.subPanel = new JPanel();

        this.labelFromDate = new JLabel();
        this.comboBoxFromDay = new JComboBox<>();
        this.comboBoxFromMonth = new JComboBox<>();
        this.comboBoxFromYear = new JComboBox<>();

        this.labelToDate = new JLabel();
        this.comboBoxToDay = new JComboBox<>();
        this.comboBoxToMonth = new JComboBox<>();
        this.comboBoxToYear = new JComboBox<>();

        this.checkBoxSelectAllItems = new JCheckBox("Show all transactions");
        this.logoutSectionPanel = new JPanel();
        this.logoutButton = new JButton("Logout");

        this.buttonGenerateReport = new JButton();

        this.constraints = new GridBagConstraints();
        this.constraints.insets = new Insets(5, 0, 5, 5);

        this.sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

        this.comboBoxFromDay = new JComboBox<>();
        this.comboBoxFromMonth = new JComboBox<>();
        this.comboBoxFromYear = new JComboBox<>();
        this.comboBoxToDay = new JComboBox<>();
        this.comboBoxToMonth = new JComboBox<>();
        this.comboBoxToYear = new JComboBox<>();

        //this.scrollPane = new JScrollPane(itemsTable);

    }

    private void PrepareWindowProperties()
    {
        // Add the different sections to the window
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.reportsGenerationSectionPanel, BorderLayout.NORTH);
        this.frame.add(this.logoutSectionPanel, BorderLayout.SOUTH);

        // Set the visual properties of the window
        this.frame.setTitle("CostMan - Reports Window");
        this.frame.setSize(500, 280);
        this.frame.setResizable(false);

        // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
        this.frame.setLocationRelativeTo(null);

        // Define event handlers
        this.frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                GUIUtils.handleGoOutFromWindows(ReportsWindow.this.frame);
            }
        });
    }

    private void PrepareReportsGenerationSection()
    {
        this.reportsGenerationSectionPanel.setLayout(new GridBagLayout());
        this.reportsGenerationSectionPanel.setBorder(BorderFactory.createTitledBorder("Generate a report"));

        this.checkBoxSelectAllItems.addItemListener(new ItemListener()
        {
            @Override
            public void itemStateChanged(ItemEvent e)
            {
                // Check if the checkbox is checked (Source: https://www.javatpoint.com/java-jcheckbox)
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    ReportsWindow.this.labelFromDate.setEnabled(false);
                    ReportsWindow.this.comboBoxFromDay.setEnabled(false);
                    ReportsWindow.this.comboBoxFromMonth.setEnabled(false);
                    ReportsWindow.this.comboBoxFromYear.setEnabled(false);

                    ReportsWindow.this.labelToDate.setEnabled(false);
                    ReportsWindow.this.comboBoxToDay.setEnabled(false);
                    ReportsWindow.this.comboBoxToMonth.setEnabled(false);
                    ReportsWindow.this.comboBoxToYear.setEnabled(false);
                }
                else {
                    ReportsWindow.this.labelFromDate.setEnabled(true);
                    ReportsWindow.this.comboBoxFromDay.setEnabled(true);
                    ReportsWindow.this.comboBoxFromMonth.setEnabled(true);
                    ReportsWindow.this.comboBoxFromYear.setEnabled(true);

                    ReportsWindow.this.labelToDate.setEnabled(true);
                    ReportsWindow.this.comboBoxToDay.setEnabled(true);
                    ReportsWindow.this.comboBoxToMonth.setEnabled(true);
                    ReportsWindow.this.comboBoxToYear.setEnabled(true);
                }
            }
        });

        this.setMyConstraints(this.constraints, 0, 0, GridBagConstraints.FIRST_LINE_START, 0, 10);
        this.reportsGenerationSectionPanel.add(this.checkBoxSelectAllItems, this.constraints);

        this.PrepareFromAndToLabelsProperties();
        this.PrepareFromComboBoxesProperties();
        this.PrepareToComboBoxesProperties();

        this.buttonGenerateReport.setText("Generate Report");
        this.buttonGenerateReport.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                // If the "Generate all transactions" checkbox is checked
                if (ReportsWindow.this.checkBoxSelectAllItems.isSelected()) {
                    ReportsWindow.this.fromDate = "01-01-1970";

                    toDay = String.valueOf(LocalDate.now().getDayOfMonth());
                    toMonth = String.valueOf(LocalDate.now().getMonth().getValue());
                    toYear = String.valueOf(LocalDate.now().getYear());
                    ReportsWindow.this.toDate = toDay + "-" + toMonth + "-" + toYear;
                }
                else {
                    fromDay = Objects.requireNonNull(ReportsWindow.this.comboBoxFromDay.getSelectedItem()).toString();
                    fromMonth = Objects.requireNonNull(ReportsWindow.this.comboBoxFromMonth.getSelectedItem()).toString();
                    fromYear = Objects.requireNonNull(ReportsWindow.this.comboBoxFromYear.getSelectedItem()).toString();
                    ReportsWindow.this.fromDate = fromDay + "-" + fromMonth + "-" + fromYear;

                    toDay = Objects.requireNonNull(ReportsWindow.this.comboBoxToDay.getSelectedItem()).toString();
                    toMonth = Objects.requireNonNull(ReportsWindow.this.comboBoxToMonth.getSelectedItem()).toString();
                    toYear = Objects.requireNonNull(ReportsWindow.this.comboBoxToYear.getSelectedItem()).toString();
                    ReportsWindow.this.toDate = toDay + "-" + toMonth + "-" + toYear;
                }

                // Check that the dates are valid
                // TODO: move this to the viewmmodel
                try
                {
                    firstDate = sdf.parse(ReportsWindow.this.fromDate);
                    secondDate = sdf.parse(ReportsWindow.this.toDate);

                    diffInMillis = secondDate.getTime() - firstDate.getTime();
                    diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

                    GUIUtils.showOkMessageBox("Differences between dates",
                            "Difference in ms: " + diffInMillis + "\n"
                            + "Difference in days: " + diff);

                    // TODO get items from DB
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            vm.getItems(fromDate, toDate);
                        }
                    });
                }
                catch (ParseException ex)
                {
                    GUIUtils.showErrorMessageBox("Parsing Error", "There was a problem parsing the data.");
                }

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        GUIUtils.showOkMessageBox("Selected Dates", "FromDate: " + ReportsWindow.this.fromDate + "\nToDate: " + ReportsWindow.this.toDate);
                    }
                });
                }
        });
        this.setMyConstraints(this.constraints, 4, 3, GridBagConstraints.CENTER, 0, 10);
        this.reportsGenerationSectionPanel.add(this.buttonGenerateReport, this.constraints);
    }

    private void PrepareFromAndToLabelsProperties()
    {
        this.labelFromDate.setText("Start Date: ");
        this.labelFromDate.setFont(new Font("Consolas", Font.BOLD, 14));
        this.setMyConstraints(this.constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.labelFromDate, this.constraints);

        this.labelToDate.setText("End Date: ");
        this.labelToDate.setFont(new Font("Consolas", Font.BOLD, 14));
        this.setMyConstraints(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.labelToDate, this.constraints);
    }

    private void PrepareFromComboBoxesProperties()
    {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
        this.comboBoxFromDay.setModel(new DefaultComboBoxModel<>(this.days));
        this.setMyConstraints(this.constraints, 1, 1, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.comboBoxFromDay, this.constraints);

        this.comboBoxFromMonth.setModel(new DefaultComboBoxModel<>(this.months));
        this.setMyConstraints(this.constraints, 2, 1, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.comboBoxFromMonth, this.constraints);

        this.comboBoxFromYear.setModel(new DefaultComboBoxModel<>(this.years));
        this.setMyConstraints(this.constraints, 3, 1, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.comboBoxFromYear, this.constraints);
    }

    private void PrepareToComboBoxesProperties()
    {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
        this.comboBoxToDay.setModel(new DefaultComboBoxModel<>(this.days));
        this.setMyConstraints(this.constraints, 1, 2, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.comboBoxToDay, this.constraints);

        this.comboBoxToMonth.setModel(new DefaultComboBoxModel<>(this.months));
        this.setMyConstraints(this.constraints, 2, 2, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.comboBoxToMonth, this.constraints);

        this.comboBoxToYear.setModel(new DefaultComboBoxModel<>(this.years));
        this.setMyConstraints(this.constraints, 3, 2, GridBagConstraints.CENTER, 10, 10);
        this.reportsGenerationSectionPanel.add(this.comboBoxToYear, this.constraints);
    }

    private void PrepareLogoutSection()
    {
        this.logoutButton.setPreferredSize(new Dimension(100, this.logoutButton.getPreferredSize().height));
        this.logoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIUtils.showExitMessageBox();
            }
        });

        // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
        this.logoutSectionPanel.setLayout(new BorderLayout());
        this.subPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        this.subPanel.add(this.logoutButton);
        this.logoutSectionPanel.add(subPanel, BorderLayout.SOUTH);
    }

    public void Show()
    {
        this.frame.setVisible(true);
    }

    // TODO: rename method to more indicative name
    private void setMyConstraints(GridBagConstraints c, int gridx, int gridy, int anchor, int ipadx, int ipady) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.anchor = anchor;
        c.ipadx = ipadx;
        c.ipady = ipady;
    }

    @Override
    public void setIViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void showItems(DefaultTableModel model) {
        System.out.println("ShowItems log");
        this.data = model;
        resizeTable(itemsTable);
        System.out.println("showItems returned " + data.getRowCount() + "rows.");

        if(data == null) { // if table wasn't populated before
            this.itemsTable = new JTable(data);

            this.setMyConstraints(this.constraints, 0,  4, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSectionPanel.add(new JScrollPane(itemsTable), this.constraints);

            this.itemsTable.setRowHeight(30); // setting row size
            this.itemsTable.getColumnModel().getColumn(0).setMinWidth(25); // setting width of characteristics
        }
        else { // updating table that has been created previously
            this.itemsTable.setModel(data); // updating table if there was previous data

            this.itemsTable.setRowHeight(30); // setting row size
            this.itemsTable.getColumnModel().getColumn(0).setMinWidth(25); // setting width of characteristics

        }
    }

    // TODO DELETE AFTER COMBINING TO ONE FILE
    @Override
    public void setUserID(int userID) {

    }

    // Source: https://tips4java.wordpress.com/2008/11/10/table-column-adjuster/
    public void resizeTable(JTable table){
        TableColumnModel model = table.getColumnModel();

        for (int column = 0; column < table.getColumnCount(); column++)
        {
            TableColumn tableColumn = table.getColumnModel().getColumn(column);
            int preferredWidth = tableColumn.getMinWidth();
            int maxWidth = tableColumn.getMaxWidth();

            for (int row = 0; row < table.getRowCount(); row++)
            {
                TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                Component c = table.prepareRenderer(cellRenderer, row, column);
                int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                preferredWidth = Math.max(preferredWidth, width);

                //  We've exceeded the maximum width, no need to check other rows

                if (preferredWidth >= maxWidth) {
                    preferredWidth = maxWidth;
                    break;
                }
            }

            tableColumn.setPreferredWidth(preferredWidth);
        }
    }
}

