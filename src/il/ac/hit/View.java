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

public class View implements IView
{
    IViewModel viewModel = null;

    /*
    LoginWindow loginWindow;
    RegistrationWindow registrationWindow;
    MainWindow mainWindow;
    TransactionsWindow transactionsWindow;
    CategoriesWindow categoriesWindow;
    ReportsWindow reportsWindow;
    */

    public class LoginWindow
    {
        private GridBagConstraints constraints;
        private JFrame frame;
        private JPanel panelCenter, panelNorth, panelSouth;
        private JLabel note, emailLabel, passwordLabel;
        private JTextField emailTextField;
        private JPasswordField passwordField;
        private JCheckBox showPasswordCheckBox;
        private JButton loginBtn, createAccountBtn;
        private char defaultEchoChar;

        public LoginWindow()
        {
            // Generating Frame
            frame = new JFrame("Cost Manager Program");

            // Generating labels
            note = new JLabel("Welcome to Cost Manager!");
            emailLabel = new JLabel("E-Mail: ");
            passwordLabel = new JLabel("Password: ");

            // Generating text fields
            emailTextField = new JTextField(20);

            // Generating password field
            passwordField = new JPasswordField(20);

            // Generating check box
            showPasswordCheckBox = new JCheckBox("Show password");

            // Generating buttons
            loginBtn = new JButton("Login");
            loginBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    // vm.handleAuthentications(emailTextField.getText(), passwordField.getPassword().toString());
                }
            });

            createAccountBtn = new JButton("Create an account");
            createAccountBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.setVisible(false);
                    // changeView(new RegistrationPage());
                }
            });

            // Generating panels
            panelNorth = new JPanel();
            panelCenter = new JPanel();
            panelSouth = new JPanel();

            // Generating grid bag constraints
            constraints = new GridBagConstraints();
        }

        public void show()
        {
            note.setFont(Font.getFont(Font.SANS_SERIF));
            emailLabel.setFont(Font.getFont(Font.SANS_SERIF));
            passwordLabel.setFont(Font.getFont(Font.SANS_SERIF));
            showPasswordCheckBox.setFont(Font.getFont(Font.SANS_SERIF));

            // Handling show password checkbox
            defaultEchoChar = passwordField.getEchoChar();
            showPasswordCheckBox.setSelected(false);
            showPasswordCheckBox.addItemListener(new ItemListener()
            {
                @Override
                public void itemStateChanged(ItemEvent e)
                {
                    if (e.getStateChange() == ItemEvent.SELECTED)
                    {
                        passwordField.setEchoChar((char) 0);
                    }
                    else
                    {
                        passwordField.setEchoChar(defaultEchoChar);
                    }
                }
            });

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 40);
            panelNorth.add(note, constraints);

            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Please enter your login information: "));
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(emailLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(emailTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(passwordLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(passwordField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.WEST, 15, 15);
            panelCenter.add(showPasswordCheckBox, constraints);

            panelSouth.setLayout(new GridBagLayout());
            panelSouth.setBorder(BorderFactory.createTitledBorder("Actions"));
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
            panelSouth.add(loginBtn, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            panelSouth.add(createAccountBtn, constraints);

            setFrameSettings(frame, panelCenter, panelNorth, panelSouth);
        }

        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth)
        {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public class RegistrationWindow
    {
        private User user;
        private JFrame frame;
        private GridBagConstraints constraints;
        private JLabel note, fullNameLabel, emailLabel, passwordLabel;
        private JButton registerBtn;
        private JTextField emailTextField, fullNameTextField;
        private JPasswordField passwordTextField;
        private JPanel panelCenter, panelNorth, panelSouth;

        RegistrationWindow() {
            frame = new JFrame("Registration page");

            note = new JLabel("Please fill the following form:");

            emailLabel = new JLabel("E-Mail: ");
            emailTextField = new JTextField(20);
            passwordLabel = new JLabel("Password: ");
            passwordTextField = new JPasswordField(20);
            fullNameLabel = new JLabel("Full Name: ");
            fullNameTextField = new JTextField(20);

            registerBtn = new JButton("Create Account");

            panelCenter = new JPanel();
            panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));
            panelNorth = new JPanel();
            panelSouth = new JPanel();

            constraints = new GridBagConstraints();
        }

        public void show() {

            panelCenter.setFont(Font.getFont(Font.SANS_SERIF));
            panelSouth.setFont(Font.getFont(Font.SANS_SERIF));
            panelNorth.setFont(Font.getFont(Font.SANS_SERIF));

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 20);
            panelNorth.add(note, constraints);

            // Handling Center Panel
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));

            GUIUtils.setConstraintsSettings(constraints, 0,0, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(emailLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(emailTextField, constraints);

            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(passwordLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(passwordTextField, constraints);

            GUIUtils.setConstraintsSettings(constraints, 0,2, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(fullNameLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1,2, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(fullNameTextField, constraints);

            // Handling South Panel
            panelSouth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0,0, GridBagConstraints.CENTER, 10, 15);
            panelSouth.add(registerBtn, constraints);

            setFrameSettings(frame, panelCenter, panelNorth, panelSouth);
        }

        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public class MainWindow
    {
        private JFrame  window                                 = null;
        private JLabel  windowTitle                            = null;
        private JButton manageTransactionsButton               = null;
        private JButton generateReportButton                   = null;
        private JButton manageCategoriesAndSubCategoriesButton = null;
        private JButton logoutButton                           = null;
        private JPanel  menuSection                            = null;
        private JPanel  titleSection                           = null;
        private JPanel  logoutSection                          = null;
        private GridBagConstraints constraints                 = null;

        public MainWindow()
        {
            this.InitializeComponents();
            this.PrepareWindowProperties();

            this.PrepareTitleSection();
            // this.PrepareTitleProperties();
            this.PrepareMenuSection();
            this.PrepareLogoutSection();
            // this.window.add(this.windowTitle);
            // this.window.add(this.menuSection);
        }

        private void InitializeComponents()
        {
            this.window = new JFrame();

            // Components of the Title Section:
            this.titleSection  = new JPanel();
            this.windowTitle   = new JLabel();

            // Components of the Menu Section:
            this.menuSection                            = new JPanel();
            this.manageTransactionsButton               = new JButton("Manage Transactions");
            this.generateReportButton                   = new JButton("Generate Transactions Report");
            this.manageCategoriesAndSubCategoriesButton = new JButton("Edit Categories & Sub-Categories");
            this.logoutButton                           = new JButton("Logout");

            // Components of the Logout Section:
            this.logoutSection = new JPanel();

            this.constraints        = new GridBagConstraints();
            this.constraints.insets = new Insets(5, 0, 5, 0);
        }

        private void PrepareWindowProperties()
        {
            // Add the different sections to the window
            this.window.setLayout(new BorderLayout());
            this.window.add(this.titleSection , BorderLayout.NORTH);
            this.window.add(this.menuSection  , BorderLayout.CENTER);
            this.window.add(this.logoutSection, BorderLayout.SOUTH);

            // Set the visual properties of the window
            this.window.setTitle("CostMan - Main Window");
            this.window.setSize(500, 800);
            this.window.setResizable(false);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.window.setLocationRelativeTo(null);

            // Define event handlers
            this.window.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    GUIUtils.ShowExitMessageBox();
                }
            });
        }

        private void PrepareTitleSection()
        {
            this.titleSection.setLayout(new GridBagLayout());

            // Set the futural location of the window's title
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);

            // Set the properties of the title
            this.PrepareTitleProperties();

            // Add the title to it's section with the current constraints
            this.titleSection.add(this.windowTitle, this.constraints);
        }

        private void PrepareTitleProperties()
        {
            this.windowTitle.setText("CostMan - Main Window");
            this.windowTitle.setFont(new Font("Consolas", Font.BOLD, 24));
        }

        private void PrepareMenuSection()
        {
            this.menuSection.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
            this.manageTransactionsButton.setPreferredSize(new Dimension(230, this.manageTransactionsButton.getPreferredSize().height));
            this.manageTransactionsButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    TransactionsWindow transactionsWindow = new TransactionsWindow();
                    transactionsWindow.Show();

                    // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                    View.MainWindow.this.window.dispose();
                }
            });

            this.menuSection.add(this.manageTransactionsButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            this.generateReportButton.setPreferredSize(new Dimension(230, this.generateReportButton.getPreferredSize().height));
            this.generateReportButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    ReportsWindow reportsWindow = new ReportsWindow();
                    reportsWindow.Show();

                    // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                    View.MainWindow.this.window.dispose();
                }
            });

            this.menuSection.add(this.generateReportButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
            this.manageCategoriesAndSubCategoriesButton.setPreferredSize(new Dimension(230, this.manageCategoriesAndSubCategoriesButton.getPreferredSize().height));
            this.manageCategoriesAndSubCategoriesButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    CategoriesWindow categoriesWindow = new CategoriesWindow();
                    categoriesWindow.show();

                    // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                    View.MainWindow.this.window.dispose();
                }
            });

            this.menuSection.add(this.manageCategoriesAndSubCategoriesButton, this.constraints);
        }

        private void PrepareLogoutSection()
        {
            this.logoutButton.setPreferredSize(new Dimension(100, this.manageTransactionsButton.getPreferredSize().height));
            this.logoutButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    GUIUtils.ShowExitMessageBox();
                }
            });

            // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
            this.logoutSection.setLayout(new BorderLayout());
            JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            subPanel.add(this.logoutButton);
            this.logoutSection.add(subPanel, BorderLayout.SOUTH);
        }

        public void Show()
        {
            this.window.setVisible(true);
        }
    }

    public class TransactionsWindow
    {
        private JFrame window      = null;
        private JLabel windowTitle = null;

        private JPanel panelNorth  = null;
        private JPanel panelCenter = null;
        private JPanel panelSouth  = null;
        private JPanel screenPanel = null;

        private JButton logoutButton = null;

        private GridBagConstraints constraints = null;

        public TransactionsWindow()
        {
            this.window = new JFrame();

            // Set the visual properties of the window
            this.window.setTitle("CostMan - Transactions Window");
            this.window.setSize(500, 800);
            this.window.setResizable(false);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.window.setLocationRelativeTo(null);

            // Define event handlers
            this.window.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    GUIUtils.HandleGoOutFromWindows(View.TransactionsWindow.this.window);
                }
            });
        }

        public void Show()
        {
            this.window.setVisible(true);
        }
    }

    public class CategoriesWindow
    {
        private JFrame frame;
        private GridBagConstraints constraints;
        private JLabel addNewCategory, addNewSubCategory, categoryName, rootCategoryName, subCategoryName, backSlash;
        private JButton newCategoryBtn, newSubCategoryBtn, logOutBtn;
        private JTextField categoryTextField, rootCategoryTextField, subCategoryTextField;
        private JPanel panelCenter, panelSouth;

        CategoriesWindow() {
            frame = new JFrame("Categories & Sub-Categories Page");

            backSlash = new JLabel("\n");

            addNewCategory = new JLabel("Add a new Category: ");
            categoryName = new JLabel("Category name: ");
            categoryTextField = new JTextField(15);
            newCategoryBtn = new JButton("Add New Category");

            addNewSubCategory = new JLabel("Add a new Sub-Category: ");
            rootCategoryName = new JLabel("Root Category: ");
            rootCategoryTextField = new JTextField(15);
            subCategoryName = new JLabel("Sub-Category name: ");
            subCategoryTextField = new JTextField(15);
            newSubCategoryBtn = new JButton("Add New Sub-Category");

            logOutBtn = new JButton("Log Out");

            panelCenter = new JPanel();
            panelSouth = new JPanel();

            constraints = new GridBagConstraints();
        }

        public void show() {
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Insert a new Category or a Sub-Category: "));

            // Handling the main category section
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.WEST, 0, 0);
            panelCenter.add(addNewCategory, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(categoryName, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(categoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(backSlash, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 3, GridBagConstraints.CENTER, 10, 10);
            panelCenter.add(newCategoryBtn, constraints);

            // Handling the sub category section
            GUIUtils.setConstraintsSettings(constraints, 0, 4, GridBagConstraints.WEST, 0, 0);
            panelCenter.add(addNewSubCategory, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 5, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(rootCategoryName, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 5, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(rootCategoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 6, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(subCategoryName, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 6, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(subCategoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 7, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(backSlash, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 8, GridBagConstraints.CENTER, 10, 10);
            panelCenter.add(newSubCategoryBtn, constraints);

            panelSouth.setLayout(new GridBagLayout());
            panelSouth.setBorder(BorderFactory.createEmptyBorder());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
            panelSouth.add(logOutBtn, constraints);

            setFrameSettings(frame, panelCenter, panelSouth);
        }
        
        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }
    
    public class ReportsWindow
    {
        private JFrame window                   = null;
        private JPanel reportsGenerationSection = null;
        private GridBagConstraints constraints  = null;

        private JLabel labelFromDate        = null;
        private JComboBox comboBoxFromDay   = null;
        private JComboBox comboBoxFromMonth = null;
        private JComboBox comboBoxFromYear  = null;

        private JLabel labelToDate        = null;
        private JComboBox comboBoxToDay   = null;
        private JComboBox comboBoxToMonth = null;
        private JComboBox comboBoxToYear  = null;

        private JCheckBox checkBoxSelectAllItems = null;
        private JButton buttonGenerateReport     = null;

        private JPanel  logoutSection = null;
        private JButton logoutButton  = null;

        private String[] days = null;
        private String[] months = null;
        private String[] years = null;

        private String fromDate = null;
        private String toDate   = null;

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
            this.window = new JFrame();
            this.reportsGenerationSection = new JPanel();

            this.labelFromDate     = new JLabel();
            this.comboBoxFromDay   = new JComboBox();
            this.comboBoxFromMonth = new JComboBox();
            this.comboBoxFromYear  = new JComboBox();

            this.labelToDate      = new JLabel();
            this.comboBoxToDay    = new JComboBox();
            this.comboBoxToMonth  = new JComboBox();
            this.comboBoxToYear   = new JComboBox();

            this.checkBoxSelectAllItems = new JCheckBox("Show all transactions");
            this.logoutSection = new JPanel();
            this.logoutButton = new JButton("Logout");

            this.buttonGenerateReport = new JButton();

            this.constraints        = new GridBagConstraints();
            this.constraints.insets = new Insets(5, 0, 5, 5);
        }

        private void PrepareWindowProperties()
        {
            // Add the different sections to the window
            this.window.setLayout(new BorderLayout());
            this.window.add(this.reportsGenerationSection , BorderLayout.NORTH);
            this.window.add(this.logoutSection, BorderLayout.SOUTH);

            // Set the visual properties of the window
            this.window.setTitle("CostMan - Reports Window");
            this.window.setSize(500, 280);
            this.window.setResizable(false);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.window.setLocationRelativeTo(null);

            // Define event handlers
            this.window.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    GUIUtils.HandleGoOutFromWindows(View.ReportsWindow.this.window);
                }
            });
        }

        private void PrepareReportsGenerationSection()
        {
            this.reportsGenerationSection.setLayout(new GridBagLayout());
            this.reportsGenerationSection.setBorder(BorderFactory.createTitledBorder("Generate a report"));

            this.checkBoxSelectAllItems.addItemListener(new ItemListener()
            {
                @Override
                public void itemStateChanged(ItemEvent e)
                {
                    // Check if the checkbox is checked (Source: https://www.javatpoint.com/java-jcheckbox)
                    if (e.getStateChange() == 1)
                    {
                        View.ReportsWindow.this.labelFromDate.setEnabled(false);
                        View.ReportsWindow.this.comboBoxFromDay.setEnabled(false);
                        View.ReportsWindow.this.comboBoxFromMonth.setEnabled(false);
                        View.ReportsWindow.this.comboBoxFromYear.setEnabled(false);

                        View.ReportsWindow.this.labelToDate.setEnabled(false);
                        View.ReportsWindow.this.comboBoxToDay.setEnabled(false);
                        View.ReportsWindow.this.comboBoxToMonth.setEnabled(false);
                        View.ReportsWindow.this.comboBoxToYear.setEnabled(false);
                    }
                    else
                    {
                        View.ReportsWindow.this.labelFromDate.setEnabled(true);
                        View.ReportsWindow.this.comboBoxFromDay.setEnabled(true);
                        View.ReportsWindow.this.comboBoxFromMonth.setEnabled(true);
                        View.ReportsWindow.this.comboBoxFromYear.setEnabled(true);

                        View.ReportsWindow.this.labelToDate.setEnabled(true);
                        View.ReportsWindow.this.comboBoxToDay.setEnabled(true);
                        View.ReportsWindow.this.comboBoxToMonth.setEnabled(true);
                        View.ReportsWindow.this.comboBoxToYear.setEnabled(true);
                    }
                }
            });

            this.setMyConstraints(this.constraints, 0, 0, GridBagConstraints.FIRST_LINE_START, 0, 10);
            this.reportsGenerationSection.add(this.checkBoxSelectAllItems, this.constraints);

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
                    if (View.ReportsWindow.this.checkBoxSelectAllItems.isSelected() == true)
                    {
                        View.ReportsWindow.this.fromDate = "01-01-1970";

                        String toDay   = String.valueOf(LocalDate.now().getDayOfMonth());
                        String toMonth = String.valueOf(LocalDate.now().getMonth().getValue());
                        String toYear  = String.valueOf(LocalDate.now().getYear());
                        View.ReportsWindow.this.toDate = toDay + "-" + toMonth + "-" + toYear;
                    }
                    else
                    {
                        String fromDay   = View.ReportsWindow.this.comboBoxFromDay.getSelectedItem().toString();
                        String fromMonth = View.ReportsWindow.this.comboBoxFromMonth.getSelectedItem().toString();
                        String fromYear  = View.ReportsWindow.this.comboBoxFromYear.getSelectedItem().toString();
                        View.ReportsWindow.this.fromDate = fromDay + "-" + fromMonth + "-" + fromYear;

                        String toDay   = View.ReportsWindow.this.comboBoxToDay.getSelectedItem().toString();
                        String toMonth = View.ReportsWindow.this.comboBoxToMonth.getSelectedItem().toString();
                        String toYear  = View.ReportsWindow.this.comboBoxToYear.getSelectedItem().toString();
                        View.ReportsWindow.this.toDate = toDay + "-" + toMonth + "-" + toYear;
                    }


                    // Check that the dates are valid
                    // TODO: move this to the viewmmodel
                    try
                    {
                        SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

                        Date firstDate = sdf.parse(View.ReportsWindow.this.fromDate);
                        Date secondDate = sdf.parse(View.ReportsWindow.this.toDate);

                        long diffInMillies = secondDate.getTime() - firstDate.getTime();
                        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                        GUIUtils.ShowOkMessageBox("", String.valueOf(diffInMillies) + "\n" + String.valueOf(diff));
                    }
                    catch (ParseException ex)
                    {
                        ex.printStackTrace();
                    }


                    GUIUtils.ShowOkMessageBox("Selected Dates", "FromDate: " + View.ReportsWindow.this.fromDate + "\nToDate: " + View.ReportsWindow.this.toDate);
                }
            });
            this.setMyConstraints(this.constraints, 4, 3, GridBagConstraints.CENTER, 0, 10);
            this.reportsGenerationSection.add(this.buttonGenerateReport, this.constraints);
        }

        private void PrepareFromAndToLabelsProperties()
        {
            this.labelFromDate.setText("Start Date:");
            this.labelFromDate.setFont(new Font("Consolas", Font.BOLD, 14));
            this.setMyConstraints(this.constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.labelFromDate, this.constraints);

            this.labelToDate.setText("End Date:");
            this.labelToDate.setFont(new Font("Consolas", Font.BOLD, 14));
            this.setMyConstraints(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.labelToDate, this.constraints);
        }

        private void PrepareFromComboBoxesProperties()
        {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
            this.comboBoxFromDay   = new JComboBox();
            this.comboBoxFromDay.setModel(new DefaultComboBoxModel(this.days));
            this.setMyConstraints(this.constraints, 1, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromDay, this.constraints);

            this.comboBoxFromMonth = new JComboBox();
            this.comboBoxFromMonth.setModel(new DefaultComboBoxModel(this.months));
            this.setMyConstraints(this.constraints, 2, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromMonth, this.constraints);

            this.comboBoxFromYear  = new JComboBox();
            this.comboBoxFromYear.setModel(new DefaultComboBoxModel(this.years));
            this.setMyConstraints(this.constraints, 3, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromYear, this.constraints);
        }

        private void PrepareToComboBoxesProperties()
        {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
            this.comboBoxToDay   = new JComboBox();
            this.comboBoxToDay.setModel(new DefaultComboBoxModel(this.days));
            this.setMyConstraints(this.constraints, 1, 2, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToDay, this.constraints);

            this.comboBoxToMonth = new JComboBox();
            this.comboBoxToMonth.setModel(new DefaultComboBoxModel(this.months));
            this.setMyConstraints(this.constraints, 2, 2, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToMonth, this.constraints);

            this.comboBoxToYear  = new JComboBox();
            this.comboBoxToYear.setModel(new DefaultComboBoxModel(this.years));
            this.setMyConstraints(this.constraints, 3, 2, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToYear, this.constraints);
        }

        private void PrepareLogoutSection()
        {
            this.logoutButton.setPreferredSize(new Dimension(100, this.logoutButton.getPreferredSize().height));
            this.logoutButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    GUIUtils.ShowExitMessageBox();
                }
            });

            // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
            this.logoutSection.setLayout(new BorderLayout());
            JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            subPanel.add(this.logoutButton);
            this.logoutSection.add(subPanel, BorderLayout.SOUTH);
        }

        public void Show()
        {
            this.window.setVisible(true);
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

    @Override
    public void setIViewModel(IViewModel vm)
    {
        this.viewModel = vm;
    }

    @Override
    public void init()
    {
    }

    @Override
    public void start()
    {
    }
}