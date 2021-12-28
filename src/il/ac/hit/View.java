package il.ac.hit;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class View implements IView
{
    private IViewModel viewModel;
    private User currentLoggedOnUser;
    
    private LoginWindow loginWindow;
    private RegistrationWindow registrationWindow;
    private MainWindow mainWindow;
    private TransactionsWindow transactionsWindow;
    private CategoriesWindow categoriesWindow;
    private ReportsWindow reportsWindow;

    @Override
    public void setIViewModel(IViewModel vm)
    {
        this.viewModel = vm;
    }

    @Override
    public void init()
    {
        this.loginWindow = new LoginWindow();
        this.registrationWindow = new RegistrationWindow();
        this.mainWindow = new MainWindow();
        this.transactionsWindow = new TransactionsWindow();
        this.categoriesWindow = new CategoriesWindow();
        this.reportsWindow = new ReportsWindow();
    }

    @Override
    public void start()
    {
        this.loginWindow.start();
    }

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
        private boolean isAuthenticated;
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

        public void start()
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

            // Handling Center Panel
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
            
            // TODO CHANGE 
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public class RegistrationWindow
    {
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

        public void start() {

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
        private JFrame frame;
        private JLabel frameTitle;
        private JButton manageTransactionsButton, generateReportButton, manageCategoriesAndSubCategoriesButton,
                logoutButton;
        private JPanel centerPanel, northPanel, southPanel;
        private GridBagConstraints constraints;

        public MainWindow() {
            this.InitializeComponents();
            this.PrepareWindowProperties();
            this.PrepareTitleSection();
            this.PrepareMenuSection();
            this.PrepareLogoutSection();
        }

        private void InitializeComponents() {
            // Initializing the Frame
            this.frame = new JFrame();

            // Components of the Title Section:
            this.northPanel = new JPanel();
            this.frameTitle = new JLabel();

            // Components of the Menu Section:
            this.centerPanel = new JPanel();
            this.manageTransactionsButton = new JButton("Manage Transactions");
            this.generateReportButton = new JButton("Generate Transactions Report");
            this.manageCategoriesAndSubCategoriesButton = new JButton("Edit Categories & Sub-Categories");
            this.logoutButton = new JButton("Logout");

            // Components of the Logout Section:
            this.southPanel = new JPanel();

            this.constraints = new GridBagConstraints();
            this.constraints.insets = new Insets(5, 0, 5, 0);
        }

        private void PrepareWindowProperties()
        {
            // Add the different sections to the window
            this.frame.setLayout(new BorderLayout());
            this.frame.add(this.northPanel, BorderLayout.NORTH);
            this.frame.add(this.centerPanel, BorderLayout.CENTER);
            this.frame.add(this.southPanel, BorderLayout.SOUTH);

            // Set the visual properties of the window
            this.frame.setTitle("CostMan - Main Window");
            this.frame.setSize(500, 800);
            this.frame.setResizable(false);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.frame.setLocationRelativeTo(null);

            // Define event handlers
            this.frame.addWindowListener(new WindowAdapter()
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
            this.northPanel.setLayout(new GridBagLayout());

            // Set the future location of the window's title
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);

            // Set the properties of the title
            this.PrepareTitleProperties();

            // Add the title to it's section with the current constraints
            this.northPanel.add(this.frameTitle, this.constraints);
        }

        private void PrepareTitleProperties()
        {
            this.frameTitle.setText("CostMan - Main Window");
            this.frameTitle.setFont(new Font("Consolas", Font.BOLD, 24));
        }

        private void PrepareMenuSection()
        {
            this.centerPanel.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
            this.manageTransactionsButton.setPreferredSize(new Dimension(230, this.manageTransactionsButton.getPreferredSize().height));
            this.manageTransactionsButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });

            this.centerPanel.add(this.manageTransactionsButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            this.generateReportButton.setPreferredSize(new Dimension(230, this.generateReportButton.getPreferredSize().height));
            this.generateReportButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });

            this.centerPanel.add(this.generateReportButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
            this.manageCategoriesAndSubCategoriesButton.setPreferredSize(new Dimension(230, this.manageCategoriesAndSubCategoriesButton.getPreferredSize().height));
            this.manageCategoriesAndSubCategoriesButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });

            this.centerPanel.add(this.manageCategoriesAndSubCategoriesButton, this.constraints);
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
            this.southPanel.setLayout(new BorderLayout());
            JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            subPanel.add(this.logoutButton);
            this.southPanel.add(subPanel, BorderLayout.SOUTH);
        }

        public void Show()
        {
            this.frame.setVisible(true);
        }
    }

    /**
     * @author Aviv
     */
    public class TransactionsWindow
    {
        private JPanel panelNorth, panelCenter, panelSouth;
        private JFrame frame;
        private JLabel note, categoryLabel, subCategoryLabel, sumLabel, currencyLabel, idLabel,
                dateLabel, descriptionLabel;
        private JTextField categoryTextField, subCategoryTextField, sumTextField,
                dateTextField, descriptionTextField, idTextField;
        private JButton logOutBtn, insertBtn, deleteBtn;
        private GridBagConstraints constraints;
        private String[] currencies;
        private SpinnerListModel spinnerListModel;
        private JSpinner currencySpinner;

        public TransactionsWindow()
        {
            // Handling currency spinner
            currencies = new String[]{"ILS", "USD", "EUR", "GBP"};
            spinnerListModel = new SpinnerListModel(currencies);
            currencySpinner = new JSpinner(spinnerListModel);

            // Generating frame
            this.frame.setTitle("CostMan - Transactions Window");

            // Define event handlers
            this.frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    // GUIUtils.HandleGoOutFromWindows(TransactionsWindow.this.frame.frame);
                }
            });

            // Generating panels
            panelNorth = new JPanel();
            panelCenter = new JPanel();
            panelSouth = new JPanel();

            // Generating buttons
            logOutBtn = new JButton("Log out");
            insertBtn = new JButton("Insert item");
            deleteBtn = new JButton("Delete item");

            // Generating labels
            note = new JLabel("Items management screen");
            categoryLabel = new JLabel("Category: ");
            subCategoryLabel = new JLabel("Sub-Category: ");
            sumLabel = new JLabel("Sum: ");
            currencyLabel = new JLabel("Currency: ");
            dateLabel = new JLabel("Date: ");
            descriptionLabel = new JLabel("Description: ");
            idLabel = new JLabel("ID: ");

            // Generating Text fields
            categoryTextField = new JTextField(20);
            subCategoryTextField = new JTextField(20);
            sumTextField = new JTextField(10);
            dateTextField = new JTextField(20);
            descriptionTextField = new JTextField(25);
            idTextField = new JTextField(10);

            // Generating grid bag constraints
            constraints = new GridBagConstraints();
        }

        public void showWindow() {
            // Set the visual properties of the window
            this.frame.setSize(500, 800);
            this.frame.setResizable(false);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.frame.setLocationRelativeTo(null);

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 40);
            panelNorth.add(note, constraints);

            // Handling Center Panel
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Add a new item: "));

            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(categoryLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(categoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(subCategoryLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(subCategoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(sumLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 2, GridBagConstraints.WEST, 5, 5);
            panelCenter.add(sumTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 3, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(currencyLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 3, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(currencySpinner, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 4, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(dateLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 4, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(dateTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 5, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(descriptionLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 5, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(descriptionTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 6, GridBagConstraints.WEST, 15, 15);
            panelCenter.add(insertBtn, constraints);

            panelSouth.setLayout(new GridBagLayout());
            panelSouth.setBorder(BorderFactory.createTitledBorder("Delete an item"));
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.WEST, 0, 0);
            panelSouth.add(idLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
            panelSouth.add(idTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 2, 0, GridBagConstraints.WEST, 10, 10);
            panelSouth.add(deleteBtn, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.WEST, 10, 10);
            panelSouth.add(logOutBtn, constraints);

            setFrameSettings(this.frame, panelCenter, panelNorth, panelSouth);
        }

        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);

            frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    // TODO ADD
                }
            });
        }
    }

    /**
     * @author Yaniv
     */
    public class CategoriesWindow
    {
        private JFrame frame;
        private GridBagConstraints constraints;
        private JLabel addNewCategory, addNewSubCategory, categoryName, rootCategoryName, subCategoryName, backSlash;
        private JButton newCategoryBtn, newSubCategoryBtn, logOutBtn;
        private JTextField categoryTextField, rootCategoryTextField, subCategoryTextField;
        private JPanel panelCenter, panelSouth;

        CategoriesWindow()
        {
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

        public void showWindow() {
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

            setFrameSettings(this.frame, panelCenter, panelSouth);
        }

        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);

            frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    // TODO ADD
                }
            });
        }
    }

    public class ReportsWindow
    {
        private JFrame frame;
        private JPanel reportsGenerationSection;
        private GridBagConstraints constraints;

        private JLabel labelFromDate;
        private JComboBox<String> comboBoxFromDay;
        private JComboBox<String> comboBoxFromMonth;
        private JComboBox<String> comboBoxFromYear;

        private JLabel labelToDate;
        private JComboBox<String> comboBoxToDay;
        private JComboBox<String> comboBoxToMonth;
        private JComboBox<String> comboBoxToYear;

        private JCheckBox checkBoxSelectAllItems;
        private JButton buttonGenerateReport;

        private JPanel logoutSection;
        private JButton logoutButton;

        private String[] days;
        private String[] months;
        private String[] years;

        private String fromDate, toDate;
        private String fromDay, fromMonth, fromYear, toDay, toMonth, toYear;

        private Date firstDate, secondDate;
        private SimpleDateFormat sdf;
        private long diff, diffInMillis;

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
            this.reportsGenerationSection = new JPanel();

            this.labelFromDate = new JLabel();
            this.comboBoxFromDay = new JComboBox<>();
            this.comboBoxFromMonth = new JComboBox<>();
            this.comboBoxFromYear = new JComboBox<>();

            this.labelToDate = new JLabel();
            this.comboBoxToDay = new JComboBox<>();
            this.comboBoxToMonth = new JComboBox<>();
            this.comboBoxToYear = new JComboBox<>();

            this.checkBoxSelectAllItems = new JCheckBox("Show all transactions");
            this.logoutSection = new JPanel();
            this.logoutButton = new JButton("Logout");

            this.buttonGenerateReport = new JButton();

            this.constraints = new GridBagConstraints();
            this.constraints.insets = new Insets(5, 0, 5, 5);

            this.sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);
        }

        private void PrepareWindowProperties()
        {
            // Add the different sections to the window
            this.frame.setLayout(new BorderLayout());
            this.frame.add(this.reportsGenerationSection, BorderLayout.NORTH);
            this.frame.add(this.logoutSection, BorderLayout.SOUTH);

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
                    // TODO ADD
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
                        ReportsWindow.this.labelFromDate.setEnabled(false);
                        ReportsWindow.this.comboBoxFromDay.setEnabled(false);
                        ReportsWindow.this.comboBoxFromMonth.setEnabled(false);
                        ReportsWindow.this.comboBoxFromYear.setEnabled(false);

                        ReportsWindow.this.labelToDate.setEnabled(false);
                        ReportsWindow.this.comboBoxToDay.setEnabled(false);
                        ReportsWindow.this.comboBoxToMonth.setEnabled(false);
                        ReportsWindow.this.comboBoxToYear.setEnabled(false);
                    }
                    else
                    {
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

            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.FIRST_LINE_START, 0, 10);
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
                    if (ReportsWindow.this.checkBoxSelectAllItems.isSelected())
                    {
                        ReportsWindow.this.fromDate = "01-01-1970";

                        toDay = String.valueOf(LocalDate.now().getDayOfMonth());
                        toMonth = String.valueOf(LocalDate.now().getMonth().getValue());
                        toYear = String.valueOf(LocalDate.now().getYear());
                    }
                    else
                    {
                        fromDay = Objects.requireNonNull(ReportsWindow.this.comboBoxFromDay.getSelectedItem()).toString();
                        fromMonth = Objects.requireNonNull(ReportsWindow.this.comboBoxFromMonth.getSelectedItem()).toString();
                        fromYear = Objects.requireNonNull(ReportsWindow.this.comboBoxFromYear.getSelectedItem()).toString();
                        ReportsWindow.this.fromDate = fromDay + "-" + fromMonth + "-" + fromYear;

                        toDay = Objects.requireNonNull(ReportsWindow.this.comboBoxToDay.getSelectedItem()).toString();
                        toMonth = Objects.requireNonNull(ReportsWindow.this.comboBoxToMonth.getSelectedItem()).toString();
                        toYear = Objects.requireNonNull(ReportsWindow.this.comboBoxToYear.getSelectedItem()).toString();
                    }

                    toDate = toDay + "-" + toMonth + "-" + toYear;

                    // Check that the dates are valid
                    // TODO: move this to the VM
                    try
                    {
                        sdf = new SimpleDateFormat("MM-dd-yyyy", Locale.ENGLISH);

                        firstDate = sdf.parse(ReportsWindow.this.fromDate);
                        secondDate = sdf.parse(ReportsWindow.this.toDate);

                        diffInMillis = secondDate.getTime() - firstDate.getTime();
                        diff = TimeUnit.DAYS.convert(diffInMillis, TimeUnit.MILLISECONDS);

                        GUIUtils.ShowOkMessageBox("Difference between dates",
                                diffInMillis + "\n" + diff);
                    }

                    catch (ParseException ex) {
                        GUIUtils.ShowErrorMessageBox("Error", "Error parsing dates!");
                    }
                    
                    GUIUtils.ShowOkMessageBox("Selected Dates", "FromDate: " + ReportsWindow.this.fromDate + "\nToDate: " + ReportsWindow.this.toDate);
                }
            });
            GUIUtils.setConstraintsSettings(this.constraints, 4, 3, GridBagConstraints.CENTER, 0, 10);
            this.reportsGenerationSection.add(this.buttonGenerateReport, this.constraints);
        }

        private void PrepareFromAndToLabelsProperties()
        {
            this.labelFromDate.setText("Start Date:");
            this.labelFromDate.setFont(new Font("Consolas", Font.BOLD, 14));
            GUIUtils.setConstraintsSettings(this.constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.labelFromDate, this.constraints);

            this.labelToDate.setText("End Date:");
            this.labelToDate.setFont(new Font("Consolas", Font.BOLD, 14));
            GUIUtils.setConstraintsSettings(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.labelToDate, this.constraints);
        }

        private void PrepareFromComboBoxesProperties()
        {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
            this.comboBoxFromDay = new JComboBox<>();
            this.comboBoxFromDay.setModel(new DefaultComboBoxModel<>(this.days));
            GUIUtils.setConstraintsSettings(this.constraints, 1, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromDay, this.constraints);

            this.comboBoxFromMonth = new JComboBox<>();
            this.comboBoxFromMonth.setModel(new DefaultComboBoxModel<>(this.months));
            GUIUtils.setConstraintsSettings(this.constraints, 2, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromMonth, this.constraints);

            this.comboBoxFromYear = new JComboBox<>();
            this.comboBoxFromYear.setModel(new DefaultComboBoxModel<>(this.years));
            GUIUtils.setConstraintsSettings(this.constraints, 3, 1, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromYear, this.constraints);
        }

        private void PrepareToComboBoxesProperties()
        {
        /*
            In this method the values of the days, months and years are loaded after the constructor
            (Source: http://www.java2s.com/Tutorials/Java/javax.swing/JComboBox/1500__JComboBox.setModel_ComboBoxModel_lt_E_gt_aModel_.htm)
        */
            this.comboBoxToDay = new JComboBox<>();
            this.comboBoxToDay.setModel(new DefaultComboBoxModel<>(this.days));
            GUIUtils.setConstraintsSettings(this.constraints, 1, 2,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToDay, this.constraints);

            this.comboBoxToMonth = new JComboBox<>();
            this.comboBoxToMonth.setModel(new DefaultComboBoxModel<>(this.months));
            GUIUtils.setConstraintsSettings(this.constraints, 2, 2, GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToMonth, this.constraints);

            this.comboBoxToYear = new JComboBox<>();
            this.comboBoxToYear.setModel(new DefaultComboBoxModel<>(this.years));
            GUIUtils.setConstraintsSettings(this.constraints, 3, 2, GridBagConstraints.CENTER, 10, 10);
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
            this.frame.setVisible(true);
        }
    }
}