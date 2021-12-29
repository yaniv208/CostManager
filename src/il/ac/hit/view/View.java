package il.ac.hit.view;

import il.ac.hit.CostManException;
import il.ac.hit.GUIUtils;
import il.ac.hit.model.Item;
import il.ac.hit.viewmodel.IViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public class View implements IView
{
    private IViewModel viewModel;
    private int userID;

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
            createAccountBtn = new JButton("Create an account");

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
            showPasswordCheckBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED)
                {
                    passwordField.setEchoChar((char) 0);
                }
                else
                {
                    passwordField.setEchoChar(defaultEchoChar);
                }
            });

            // Handling "Login" button click
            loginBtn.addActionListener(e -> {
                viewModel.handleAuthentication(emailTextField.getText(), new String(passwordField.getPassword()));

                // sleeping 250 ms so user won't have to click "login" twice
                try
                {
                    Thread.sleep(250);
                }
                catch (InterruptedException ex)
                {
                    GUIUtils.ShowErrorMessageBox("Error", "Problem with the sleeping thread");
                }
            });

            // Handling "Create Account" button click
            createAccountBtn.addActionListener(e -> {
                frame.setVisible(false);
                registrationWindow.frame.setVisible(true);
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

        public void clearAllFields()
        {
            this.emailTextField.setText("");
            this.passwordField.setText("");
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

        RegistrationWindow()
        {
            // Creating Frame
            frame = new JFrame("Registration page");

            // Creating Labels and TextFields
            note = new JLabel("Please fill the following form:");
            emailLabel = new JLabel("E-Mail: ");
            emailTextField = new JTextField(20);
            passwordLabel = new JLabel("Password: ");
            passwordTextField = new JPasswordField(20);
            fullNameLabel = new JLabel("Full Name: ");
            fullNameTextField = new JTextField(20);

            // Creating Buttons
            registerBtn = new JButton("Create Account");

            // Creating Panels
            panelCenter = new JPanel();
            panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));
            panelNorth = new JPanel();
            panelSouth = new JPanel();

            // Creating Grid Bag Constraints
            constraints = new GridBagConstraints();

            start();
        }

        public void start()
        {
            panelCenter.setFont(Font.getFont(Font.SANS_SERIF));
            panelSouth.setFont(Font.getFont(Font.SANS_SERIF));
            panelNorth.setFont(Font.getFont(Font.SANS_SERIF));

            // Handling create account button click
            registerBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String email = emailTextField.getText();
                    String password = new String(passwordTextField.getPassword());
                    String fullName = fullNameTextField.getText();

                    View.this.viewModel.handleRegistrationRequest(email, password, fullName);
                }
            });

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 20);
            panelNorth.add(note, constraints);

            // Handling Center Panel
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));

            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(emailLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(emailTextField, constraints);

            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(passwordLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(passwordTextField, constraints);

            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.EAST, 10, 10);
            panelCenter.add(fullNameLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 2, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(fullNameTextField, constraints);

            // Handling South Panel
            panelSouth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER, 10, 15);
            panelSouth.add(registerBtn, constraints);

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
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    View.this.openLoginWindowOnlyAndCloseOtherWindows();
                }
            });
        }

        public void clearAllFields()
        {
            this.emailTextField.setText("");
            this.passwordTextField.setText("");
            this.fullNameTextField.setText("");
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

        public MainWindow()
        {
            this.InitializeComponents();
            this.PrepareWindowProperties();
            this.PrepareTitleSection();
            this.PrepareMenuSection();
            this.PrepareLogoutSection();
        }

        private void InitializeComponents()
        {
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
            this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.frame.setLocationRelativeTo(null);

            // Define event handlers
            this.frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    View.this.viewModel.logout();
                }
            });
        }

        private void PrepareTitleSection()
        {
            this.northPanel.setLayout(new GridBagLayout());

            // Set the future location of the window's title
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);

            // Set the properties of the title
            this.frameTitle.setText("CostMan - Main Window");
            this.frameTitle.setFont(new Font("Consolas", Font.BOLD, 24));

            // Add the title to it's section with the current constraints
            this.northPanel.add(this.frameTitle, this.constraints);
        }

        private void PrepareMenuSection()
        {
            this.centerPanel.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
            this.manageTransactionsButton.setPreferredSize(new Dimension(230, this.manageTransactionsButton.getPreferredSize().height));
            this.manageTransactionsButton.addActionListener(e -> {
                frame.setVisible(false);
                transactionsWindow.frame.setVisible(true);
            });

            this.centerPanel.add(this.manageTransactionsButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
            this.generateReportButton.setPreferredSize(new Dimension(230, this.generateReportButton.getPreferredSize().height));
            this.generateReportButton.addActionListener(e -> {
                frame.setVisible(false);
                reportsWindow.frame.setVisible(true);
            });

            this.centerPanel.add(this.generateReportButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
            this.manageCategoriesAndSubCategoriesButton.setPreferredSize(new Dimension(230, this.manageCategoriesAndSubCategoriesButton.getPreferredSize().height));
            this.manageCategoriesAndSubCategoriesButton.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    frame.setVisible(false);
                    categoriesWindow.frame.setVisible(true);
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
                    View.this.viewModel.logout();
                }
            });

            // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
            this.southPanel.setLayout(new BorderLayout());
            JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            subPanel.add(this.logoutButton);
            this.southPanel.add(subPanel, BorderLayout.SOUTH);
        }
    }

    /**
     * @author Aviv
     */
    public class TransactionsWindow
    {
        private JPanel panelNorth, panelCenter, panelSouth;
        private JFrame frame;
        private JLabel note, categoryLabel, subCategoryLabel, sumLabel, currencyLabel, currencyRateLabel, idLabel,
                dateLabel, descriptionLabel;
        private JTextField categoryTextField, subCategoryTextField, sumTextField, currencyRateTextField,
                dateTextField, descriptionTextField, idTextField;
        private JButton logOutBtn, insertBtn, deleteBtn;
        private GridBagConstraints constraints;
        private String[] currenciesArray, categoriesArray, subCategoriesArray;
        private JComboBox<String> categoriesComboBox, subCategoriesComboBox, currenciesComboBox;


        public TransactionsWindow()
        {
            frame = new JFrame();

            // Handling Combo Boxes
            currenciesArray = new String[]{"ILS", "USD", "EUR", "GBP"};
            categoriesArray = new String[]{"Bills", "House", "Entertainment"};
            subCategoriesArray = new String[]{"Electricity bill", "Water bill",
                    "Furniture", "Decoration", "Football games", "Bars"};

            currenciesComboBox = new JComboBox<>(currenciesArray);
            categoriesComboBox = new JComboBox<>(categoriesArray);
            subCategoriesComboBox = new JComboBox<>(subCategoriesArray);

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
            currencyRateLabel = new JLabel("Currency Rate: ");
            dateLabel = new JLabel("Date: ");
            descriptionLabel = new JLabel("Description: ");
            idLabel = new JLabel("ID: ");

            // Generating Text fields
            categoryTextField = new JTextField(20);
            subCategoryTextField = new JTextField(20);
            sumTextField = new JTextField(10);
            currencyRateTextField = new JTextField(10);
            dateTextField = new JTextField(20);
            descriptionTextField = new JTextField(25);
            idTextField = new JTextField(10);

            // Generating grid bag constraints
            constraints = new GridBagConstraints();

            setProperties();
        }

        public void setProperties()
        {
            // Handling insert button click
            insertBtn.addActionListener(e -> {
                Item item = null;
                try
                {
                    // TODO FIX CHANGE FROM NUMBER TO CATEGORY STRING USING AVIV'S METHODS
                    item = new Item(userID,
                            1, 2,
                            dateTextField.getText(),
                            Integer.parseInt(sumTextField.getText()),
                            currenciesComboBox.getSelectedItem().toString(),
                            Float.parseFloat(currencyRateTextField.getText()),
                            descriptionTextField.getText());

                    viewModel.addItem(item);
                }
                catch (CostManException ex)
                {
                    GUIUtils.ShowErrorMessageBox("Error", "Error inserting new item!");
                }
            });

            // Handling delete item button click
            deleteBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    viewModel.deleteItem(idTextField.getText());
                }
            });

            logOutBtn.addActionListener(new ActionListener()
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    View.this.viewModel.logout();
                }
            });

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
            panelCenter.add(categoriesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(subCategoryLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(subCategoriesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(sumLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 2, GridBagConstraints.WEST, 5, 5);
            panelCenter.add(sumTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 3, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(currencyLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 3, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(currenciesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 4, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(currencyRateLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 4, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(currencyRateTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 5, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(dateLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 5, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(dateTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 6, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(descriptionLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 6, GridBagConstraints.WEST, 10, 10);
            panelCenter.add(descriptionTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 7, GridBagConstraints.WEST, 15, 15);
            panelCenter.add(insertBtn, constraints);

            // Handling South Panel
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

        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth)
        {
            // Set the visual properties of the window
            this.frame.setSize(500, 800);
            this.frame.setResizable(false);
            this.frame.setTitle("CostMan - Transactions Window");
            this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.frame.setLocationRelativeTo(null);

            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelSouth, BorderLayout.SOUTH);

            // Define event handlers
            this.frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    View.this.viewModel.handleClosingOfFeatureWindow();
                }
            });
        }

        public void clearAllFields()
        {
            this.categoryTextField.setText("");
            this.subCategoryTextField.setText("");
            this.sumTextField.setText("");
            this.currencyRateTextField.setText("");
            this.dateTextField.setText("");
            this.descriptionTextField.setText("");
            this.idTextField.setText("");
            this.categoriesComboBox.setSelectedIndex(0);
            this.subCategoriesComboBox.setSelectedIndex(0);
            this.currenciesComboBox.setSelectedIndex(0);
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

            setProperties();
        }

        public void setProperties()
        {
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

        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelSouth)
        {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    View.this.viewModel.handleClosingOfFeatureWindow();
                }
            });
        }

        public void clearAllFields()
        {
            this.categoryTextField.setText("");
            this.rootCategoryTextField.setText("");
            this.subCategoryTextField.setText("");
        }
    }

    public class ReportsWindow
    {
        private JFrame frame;
        private JPanel reportsGenerationSection;
        private GridBagConstraints constraints;

        private JLabel labelFromDate;
        private JComboBox<String> comboBoxFromDay, comboBoxFromMonth, comboBoxFromYear;

        private JLabel labelToDate;
        private JComboBox<String> comboBoxToDay, comboBoxToMonth, comboBoxToYear;

        private JCheckBox selectAllItemsCheckBox;
        private JButton generateReportButton;

        private JPanel logoutSection;
        private JButton logoutButton;

        private String[] days;
        private String[] months;
        private String[] years;

        private String fromDate, toDate, fromDay, fromMonth, fromYear, toDay, toMonth, toYear;

        private DefaultTableModel tableModel;
        private DefaultListModel<Item> listModel;
        private JTable reportsTable;
        private JPanel reportsPanel;

        private JPanel subPanel;

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
            this.prepareTable();
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

            this.selectAllItemsCheckBox = new JCheckBox("Show all transactions");
            this.logoutSection = new JPanel();
            this.logoutButton = new JButton("Logout");

            this.generateReportButton = new JButton();

            this.constraints = new GridBagConstraints();
            this.constraints.insets = new Insets(5, 0, 5, 5);

            this.tableModel = new DefaultTableModel();
            this.reportsTable = new JTable();
            this.listModel = new DefaultListModel<>();

            this.reportsPanel = new JPanel();
            this.subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        }

        private void prepareTable()
        {
            reportsPanel.add(new JScrollPane(reportsTable));

            reportsTable.setLayout(new FlowLayout());

            reportsTable.setModel(tableModel);
            tableModel.addColumn("ItemID");
            tableModel.addColumn("Category ID");
            tableModel.addColumn("SubCategory ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Price");
            tableModel.addColumn("Currency Rate");
            tableModel.addColumn("Description");
        }

        private void PrepareWindowProperties()
        {
            // Add the different sections to the window
            this.frame.setLayout(new BorderLayout());
            this.frame.add(this.reportsGenerationSection, BorderLayout.NORTH);
            this.frame.add(this.logoutSection, BorderLayout.SOUTH);

            // Set the visual properties of the window
            this.frame.setTitle("CostMan - Reports Window");
            this.frame.setSize(1000, 900);
            this.frame.setResizable(false);
            this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.frame.setLocationRelativeTo(null);

            // Define event handlers
            this.frame.addWindowListener(new WindowAdapter()
            {
                @Override
                public void windowClosing(WindowEvent e)
                {
                    View.this.viewModel.handleClosingOfFeatureWindow();
                }
            });
        }

        private void PrepareReportsGenerationSection()
        {
            this.reportsGenerationSection.setLayout(new GridBagLayout());
            this.reportsGenerationSection.setBorder(BorderFactory.createTitledBorder("Generate a report"));

            this.selectAllItemsCheckBox.addItemListener(e -> {
                // Check if the checkbox is checked (Source: https://www.javatpoint.com/java-jcheckbox)
                if (e.getStateChange() == ItemEvent.SELECTED)
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
            });

            GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.FIRST_LINE_START, 0, 10);
            this.reportsGenerationSection.add(this.selectAllItemsCheckBox, this.constraints);

            this.PrepareFromAndToLabelsProperties();
            this.PrepareFromComboBoxesProperties();
            this.PrepareToComboBoxesProperties();

            this.generateReportButton.setText("Generate Report");
            this.generateReportButton.addActionListener(e -> {
                // If the "Generate all transactions" checkbox is checked
                if (ReportsWindow.this.selectAllItemsCheckBox.isSelected())
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

                viewModel.getItemsBetweenDates(fromDate, toDate);
            });

            GUIUtils.setConstraintsSettings(this.constraints, 4, 3, GridBagConstraints.CENTER, 0, 10);
            this.reportsGenerationSection.add(this.generateReportButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 4, GridBagConstraints.CENTER, 0, 10);
            this.reportsGenerationSection.add(this.reportsPanel, this.constraints);
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
                    View.this.viewModel.logout();
                }
            });

            // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
            this.logoutSection.setLayout(new BorderLayout());
            subPanel.add(this.logoutButton);
            this.logoutSection.add(subPanel, BorderLayout.SOUTH);
        }

        public void populateTable(List<Item> data)
        {
            // Initialize table
            tableModel.setRowCount(0);

            // Re-populating table using for-each loop
            for (Item item : data)
            {
                tableModel.addRow(new Object[]{
                        item.getItemId(), item.getCategoryId(), item.getSubCategoryId(), item.getDate(),
                        item.getPrice(), item.getCategoryId(), item.getDescription()});
            }
        }

        public void clearAllFields()
        {
            this.comboBoxFromDay.setSelectedIndex(0);
            this.comboBoxFromMonth.setSelectedIndex(0);
            this.comboBoxFromYear.setSelectedIndex(0);

            this.comboBoxToDay.setSelectedIndex(0);
            this.comboBoxToMonth.setSelectedIndex(0);
            this.comboBoxToYear.setSelectedIndex(0);

            this.selectAllItemsCheckBox.setSelected(false);

            for (int i = 0; i < reportsTable.getRowCount(); i++)
            {
                for(int j = 0; j < reportsTable.getColumnCount(); j++)
                {
                    reportsTable.setValueAt("", i, j);
                }
            }
        }
    }

    @Override
    public void showItems(List<Item> data)
    {
        reportsWindow.populateTable(data);
    }

    @Override
    public void setID(int id)
    {
        this.userID = id;
    }

    @Override
    public void switchFromLoginWindowToMainWindow()
    {
        this.loginWindow.frame.setVisible(false);
        this.loginWindow.clearAllFields();
        this.mainWindow.frame.setVisible(true);
    }

    @Override
    public void switchFromRegistrationWindowToMainWindow()
    {
        this.registrationWindow.frame.setVisible(false);
        this.registrationWindow.clearAllFields();
        this.mainWindow.frame.setVisible(true);
    }

    @Override
    public void openLoginWindowOnlyAndCloseOtherWindows()
    {
        // Leave only this window "open"
        this.loginWindow.frame.setVisible(true);

        // "Close" (hide) all the other windows
        this.registrationWindow.frame.setVisible(false);
        this.registrationWindow.clearAllFields();
        this.mainWindow.frame.setVisible(false);
        this.transactionsWindow.frame.setVisible(false);
        this.transactionsWindow.clearAllFields();
        this.categoriesWindow.frame.setVisible(false);
        this.categoriesWindow.clearAllFields();
        this.reportsWindow.frame.setVisible(false);
        this.reportsWindow.clearAllFields();
    }

    @Override
    public void openMainWindowOnlyAndCloseOtherWindows()
    {
        // Leave only this window "open"
        this.mainWindow.frame.setVisible(true);

        // "Close" (hide) all the other windows
        this.loginWindow.frame.setVisible(false);
        this.loginWindow.clearAllFields();
        this.registrationWindow.frame.setVisible(false);
        this.registrationWindow.clearAllFields();
        this.transactionsWindow.frame.setVisible(false);
        this.transactionsWindow.clearAllFields();
        this.categoriesWindow.frame.setVisible(false);
        this.categoriesWindow.clearAllFields();
        this.reportsWindow.frame.setVisible(false);
        this.reportsWindow.clearAllFields();
    }
}