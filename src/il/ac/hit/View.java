package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class View implements IView
{
    IViewModel viewModel;
    User currentLoggedOnUser;

    LoginWindow loginWindow;
    RegistrationWindow registrationWindow;
    MainWindow mainWindow;
    TransactionsWindow transactionsWindow;
    CategoriesWindow categoriesWindow;
    ReportsWindow reportsWindow;

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
        private JFrame  m_Window                                 = null;
        private JLabel  m_WindowTitle                            = null;
        private JButton m_ManageTransactionsButton               = null;
        private JButton m_GenerateReportButton                   = null;
        private JButton m_ManageCategoriesAndSubCategoriesButton = null;
        private JButton m_LogoutButton                           = null;
        private JPanel  m_MenuSection                            = null;
        private JPanel  m_TitleSection                           = null;
        private JPanel  m_LogoutSection                          = null;
        private GridBagConstraints m_Constraints                 = null;

        public MainWindow()
        {
            this.InitializeComponents();
            this.PrepareWindowProperties();

            this.PrepareTitleSection();
            // this.PrepareTitleProperties();
            this.PrepareMenuSection();
            this.PrepareLogoutSection();
            // this.m_Window.add(this.m_WindowTitle);
            // this.m_Window.add(this.m_MenuSection);
        }

        private void InitializeComponents()
        {
            this.m_Window = new JFrame();

            // Components of the Title Section:
            this.m_TitleSection  = new JPanel();
            this.m_WindowTitle   = new JLabel();

            // Components of the Menu Section:
            this.m_MenuSection                            = new JPanel();
            this.m_ManageTransactionsButton               = new JButton("Manage Transactions");
            this.m_GenerateReportButton                   = new JButton("Generate Transactions Report");
            this.m_ManageCategoriesAndSubCategoriesButton = new JButton("Edit Categories & Sub-Categories");
            this.m_LogoutButton                           = new JButton("Logout");

            // Components of the Logout Section:
            this.m_LogoutSection = new JPanel();

            this.m_Constraints        = new GridBagConstraints();
            this.m_Constraints.insets = new Insets(5, 0, 5, 0);
        }

        private void PrepareWindowProperties()
        {
            // Add the different sections to the window
            this.m_Window.setLayout(new BorderLayout());
            this.m_Window.add(this.m_TitleSection , BorderLayout.NORTH);
            this.m_Window.add(this.m_MenuSection  , BorderLayout.CENTER);
            this.m_Window.add(this.m_LogoutSection, BorderLayout.SOUTH);

            // Set the visual properties of the window
            this.m_Window.setTitle("CostMan - Main Window");
            this.m_Window.setSize(500, 800);
            this.m_Window.setResizable(false);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.m_Window.setLocationRelativeTo(null);

            // Define event handlers
            this.m_Window.addWindowListener(new WindowAdapter()
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
            this.m_TitleSection.setLayout(new GridBagLayout());

            // Set the futural location of the window's title
            GUIUtils.setConstraintsSettings(this.m_Constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);

            // Set the properties of the title
            this.PrepareTitleProperties();

            // Add the title to it's section with the current constraints
            this.m_TitleSection.add(this.m_WindowTitle, this.m_Constraints);
        }

        private void PrepareTitleProperties()
        {
            this.m_WindowTitle.setText("CostMan - Main Window");
            this.m_WindowTitle.setFont(new Font("Consolas", Font.BOLD, 24));
        }

        private void PrepareMenuSection()
        {
            this.m_MenuSection.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(this.m_Constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
            this.m_ManageTransactionsButton.setPreferredSize(new Dimension(230, this.m_ManageTransactionsButton.getPreferredSize().height));
            this.m_ManageTransactionsButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });

            this.m_MenuSection.add(this.m_ManageTransactionsButton, this.m_Constraints);

            GUIUtils.setConstraintsSettings(this.m_Constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            this.m_GenerateReportButton.setPreferredSize(new Dimension(230, this.m_GenerateReportButton.getPreferredSize().height));
            this.m_GenerateReportButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });

            this.m_MenuSection.add(this.m_GenerateReportButton, this.m_Constraints);

            GUIUtils.setConstraintsSettings(this.m_Constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
            this.m_ManageCategoriesAndSubCategoriesButton.setPreferredSize(new Dimension(230, this.m_ManageCategoriesAndSubCategoriesButton.getPreferredSize().height));
            this.m_ManageCategoriesAndSubCategoriesButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                }
            });

            this.m_MenuSection.add(this.m_ManageCategoriesAndSubCategoriesButton, this.m_Constraints);
        }

        private void PrepareLogoutSection()
        {
            this.m_LogoutButton.setPreferredSize(new Dimension(100, this.m_ManageTransactionsButton.getPreferredSize().height));
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
            this.frame.setTitle("Categories & Sub-Categories Page");

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
                }
            });
        }
    }

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
}