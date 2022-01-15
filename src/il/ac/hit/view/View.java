package il.ac.hit.view;

import il.ac.hit.CostManException;
import il.ac.hit.GUIUtils;
import il.ac.hit.model.EnumCategoryType;
import il.ac.hit.model.Item;
import il.ac.hit.viewmodel.EnumConsumerOfCategories;
import il.ac.hit.viewmodel.IViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

/**
 * This class implements the IView interface,
 * and represents the User Interface Component of the Model
 */
public class View implements IView {
    private IViewModel viewModel;
    private int userID;

    private LoginWindow loginWindow;
    private RegistrationWindow registrationWindow;
    private MainWindow mainWindow;
    private TransactionsWindow transactionsWindow;
    private CategoriesWindow categoriesWindow;
    private ReportsWindow reportsWindow;

    /**
     * Set the ViewModel instance variable as the implemented ViewModel provided in parameter.
     *
     * @param vm Implemented ViewModel class
     */
    @Override
    public void setIViewModel(IViewModel vm) {
        this.viewModel = vm;
    }

    /**
     * A function that initializes all the windows of the program
     */
    @Override
    public void init() {
        this.loginWindow = new LoginWindow();
        this.registrationWindow = new RegistrationWindow();
        this.mainWindow = new MainWindow();
        this.transactionsWindow = new TransactionsWindow();
        this.categoriesWindow = new CategoriesWindow();
        this.reportsWindow = new ReportsWindow();

        this.viewModel.getCurrenciesRates();
    }

    /**
     * A function that loads loginWindow's components and shows this specific window
     */
    @Override
    public void start() {
        this.loginWindow.start();
    }

    /**
     * Show the current user's items in the table within the reports window.
     *
     * @param data A list of all the items which should be displayed.
     */
    @Override
    public void showItems(List<Item> data) {
        reportsWindow.populateTable(data);
    }

    /**
     * Show categories to the user within the "TransactionsWindow" and the "CategoriesWindow"
     *
     * @param categories A list of all the categories which should be displayed.
     * @param currentCategoriesType Enum variable which indicates what is the type of the current incoming categories,
     * where "Primary" stands for "Categories" and "Secondary" stands for "Sub-Categories".
     */
    @Override
    public void showCategories(List<String> categories, EnumCategoryType currentCategoriesType,
                               EnumConsumerOfCategories caller) {
        String[] arrayOfCategories = new String[categories.size()];
        categories.toArray(arrayOfCategories);

        if (caller == EnumConsumerOfCategories.CategoriesWindow) {
            // Call show categories only for CategoriesWindow
            if (currentCategoriesType == EnumCategoryType.Primary) {
                //Update the categories that categoriesWindow.categoriesComboBox knows (with setModel)
                categoriesWindow.categoriesComboBox.setModel
                        (new DefaultComboBoxModel<>(categories.toArray(arrayOfCategories)));

                // Set the default value of this text combo box to the first primary category
                this.categoriesWindow.categoriesComboBox.setSelectedIndex(0);
            }
        } else {
            if (currentCategoriesType == EnumCategoryType.Primary) {
                // Update the categories that transactionsWindow.categoriesComboBox knows (with setModel)
                transactionsWindow.categoriesComboBox.setModel
                        (new DefaultComboBoxModel<>(categories.toArray(arrayOfCategories)));

                // Set the default value of this text combo box to the first primary category
                this.transactionsWindow.categoriesComboBox.setSelectedIndex(0);

                // Load the sub-categories of the default first primary category which is "default-selected"
                this.viewModel.getSubCategories(Objects.requireNonNull
                        (this.transactionsWindow.categoriesComboBox.getSelectedItem()).toString(), caller);
            } else {
                transactionsWindow.subCategoriesComboBox.removeAllItems();
                transactionsWindow.subCategoriesComboBox.setModel(new DefaultComboBoxModel<>(categories.
                        toArray(arrayOfCategories)));
                transactionsWindow.subCategoriesComboBox.setSelectedIndex(0);
            }
        }
    }

    /**
     * Save the current userID in the View.
     *
     * @param id The ID of the current logged-on user.
     */
    @Override
    public void setID(int id) {
        this.userID = id;
    }

    /**
     * "close" (hide) the login window and "open" (show) the main window of the program.
     * In addition, this method clears all the fields of the login window.
     */
    @Override
    public void switchFromLoginWindowToMainWindow() {
        this.loginWindow.frame.setVisible(false);
        this.loginWindow.clearAllFields();
        this.mainWindow.frame.setVisible(true);
    }

    /**
     * "close" (hide) the registration window and "open" (show) the main window of the program.
     * In addition, this method clears all the fields of the registration window.
     */
    @Override
    public void switchFromRegistrationWindowToMainWindow() {
        this.registrationWindow.frame.setVisible(false);
        this.registrationWindow.clearAllFields();
        this.mainWindow.frame.setVisible(true);
    }

    /**
     * "open" (show) the login window of the program and "close" (hide) all the windows.
     * In addition, this method clears all the fields of all the windows of the program.
     */
    @Override
    public void openLoginWindowOnlyAndCloseOtherWindows() {
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

    /**
     * "open" (show) the main window of the program and "close" (hide) all the windows.
     * In addition, this method clears all the fields of all the windows of the program.
     */
    @Override
    public void openMainWindowOnlyAndCloseOtherWindows() {
        // Set only this window as "open"
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

    /**
     * A function that gets the currency rates array and applies it in the array showed in View.
     *
     * @param currencies array of float variables, containing currencies.
     */
    @Override
    public void saveCurrenciesRates(float[] currencies) {
        this.transactionsWindow.currencyRatesValues = currencies;

        // Set the default value of this text field to 1.0 because the default currency is ILS.
        this.transactionsWindow.currencyRateSumLabel.setText(String.valueOf(currencies[0]));
    }

    /**
     * This class implements the UI of the login window of the program
     */
    public class LoginWindow {
        private GridBagConstraints constraints;
        private JFrame frame;
        private JPanel panelCenter, panelNorth, panelSouth;
        private JLabel note, emailLabel, passwordLabel;
        private JTextField emailTextField;
        private JPasswordField passwordField;
        private JCheckBox showPasswordCheckBox;
        private JButton loginBtn, createAccountBtn;
        private char defaultEchoChar;

        /**
         * public constructor of LoginWindow
         */
        public LoginWindow() {
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

        /**
         * A function that sets properties of components in the window
         */
        public void start() {
            // Handling show password checkbox
            defaultEchoChar = passwordField.getEchoChar();
            showPasswordCheckBox.setSelected(false);
            showPasswordCheckBox.addItemListener(e -> {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar(defaultEchoChar);
                }
            });

            // Handling "Login" button click
            loginBtn.addActionListener(e -> {
                viewModel.handleAuthentication(emailTextField.getText(), new String(passwordField.getPassword()));

                // Sleeping 250ms so user won't have to click "login" twice
                try {
                    Thread.sleep(250);
                } catch (InterruptedException ex) {
                    GUIUtils.showErrorMessageBox("Error", "Problem with the sleeping thread");
                }
            });

            // Handling "Create Account" button click
            createAccountBtn.addActionListener(e -> {
                frame.setVisible(false);
                registrationWindow.frame.setVisible(true);
            });

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER,
                    10, 40);
            panelNorth.add(note, constraints);

            // Handling Center Panel
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Please enter your login information: "));
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(emailLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(emailTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(passwordLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(passwordField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.WEST,
                    15, 15);
            panelCenter.add(showPasswordCheckBox, constraints);

            panelSouth.setLayout(new GridBagLayout());
            panelSouth.setBorder(BorderFactory.createTitledBorder("Actions"));
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER,
                    10, 10);
            panelSouth.add(loginBtn, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.CENTER,
                    10, 10);
            panelSouth.add(createAccountBtn, constraints);

            setFrameSettings(frame, panelCenter, panelNorth, panelSouth);
            setFonts();
        }

        /**
         * A function that sets various settings of the frame
         *
         * @param frame       the frame to be modified
         * @param panelCenter panel center to be modified
         * @param panelNorth  panel north to be modified
         * @param panelSouth  panel south to be modified
         */
        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);

            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }

        /**
         * A function that changes the fonts shown in the window
         */
        private void setFonts() {
            this.emailLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.loginBtn.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.createAccountBtn.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.showPasswordCheckBox.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.frame.setFont(new Font("Consolas", Font.BOLD, 25));
            this.note.setFont(new Font("Consolas", Font.BOLD, 25));
        }

        /**
         * A function that clears all fields in the window
         */
        public void clearAllFields() {
            this.emailTextField.setText("");
            this.passwordField.setText("");
        }
    }

    /**
     * This class implements the UI of the registration window of the program
     */
    public class RegistrationWindow {
        private JFrame frame;
        private GridBagConstraints constraints;
        private JLabel note, emailLabel, passwordLabel;
        private JButton registerBtn;
        private JTextField emailTextField;
        private JPasswordField passwordTextField;
        private JPanel panelCenter, panelNorth, panelSouth;

        /**
         * Constructor of Registration Window
         */
        RegistrationWindow() {
            // Creating Frame
            frame = new JFrame("Registration page");

            // Creating Labels and TextFields
            note = new JLabel("Please fill the following form:");
            emailLabel = new JLabel("E-Mail: ");
            emailTextField = new JTextField(20);
            passwordLabel = new JLabel("Password: ");
            passwordTextField = new JPasswordField(20);

            // Creating Buttons
            registerBtn = new JButton("Create Account");

            // Creating Panels
            panelCenter = new JPanel();
            panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));
            panelNorth = new JPanel();
            panelSouth = new JPanel();

            // Creating Grid Bag Constraints
            constraints = new GridBagConstraints();

            setProperties();
        }

        /**
         * A function that sets the properties of the window.
         */
        public void setProperties() {
            // Handling create account button click
            registerBtn.addActionListener(e -> {
                String email = emailTextField.getText();

                // Converting char[] into a new String to be sent as parameter
                String password = String.valueOf(passwordTextField.getPassword());

                View.this.viewModel.handleRegistrationRequest(email, password);
            });

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER,
                    10, 20);
            panelNorth.add(note, constraints);

            // Handling Center Panel
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));

            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(emailLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(emailTextField, constraints);

            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(passwordLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(passwordTextField, constraints);

            // Handling South Panel
            panelSouth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER,
                    10, 15);
            panelSouth.add(registerBtn, constraints);

            setFonts();
            setFrameSettings(frame, panelCenter, panelNorth, panelSouth);
        }

        /**
         * A function that sets various settings of the frame
         *
         * @param frame the frame to be modified
         * @param panelCenter panel center to be modified
         * @param panelNorth  panel north to be modified
         * @param panelSouth  panel south to be modified
         */
        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelNorth, BorderLayout.NORTH);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);

            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    View.this.openLoginWindowOnlyAndCloseOtherWindows();
                }
            });
        }

        /**
         * A function that changes the fonts shown in the window
         */
        private void setFonts() {
            this.panelCenter.setFont(Font.getFont(Font.SANS_SERIF));
            this.panelSouth.setFont(Font.getFont(Font.SANS_SERIF));
            this.panelNorth.setFont(Font.getFont(Font.SANS_SERIF));

            this.frame.setFont(new Font("Consolas", Font.BOLD, 24));
            this.note.setFont(new Font("Consolas", Font.BOLD, 24));
            this.emailLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.passwordLabel.setFont(new Font("Consolas", Font.PLAIN, 15));
        }

        /**
         * A function that clears all fields in the window
         */
        public void clearAllFields() {
            this.emailTextField.setText("");
            this.passwordTextField.setText("");
        }
    }

    /**
     * This class implements the UI of the main menu window of the program
     */
    public class MainWindow {
        private JFrame frame;
        private JLabel frameTitle;
        private JButton manageTransactionsButton, generateReportButton, manageCategoriesAndSubCategoriesButton,
                logoutButton;
        private JPanel centerPanel, northPanel, southPanel, subPanel;
        private GridBagConstraints constraints;

        /**
         * Public constructor of MainWindow
         */
        public MainWindow() {
            this.InitializeComponents();
            this.PrepareWindowProperties();
            this.PrepareTitleSection();
            this.PrepareMenuSection();
            this.PrepareLogoutSection();
            this.setFonts();
        }

        /**
         * A function that changes the fonts shown in the window
         */
        private void setFonts() {
            this.frame.setFont(new Font("Consolas", Font.BOLD, 24));
            this.frameTitle.setFont(new Font("Consolas", Font.BOLD, 24));
            this.manageTransactionsButton.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.generateReportButton.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.manageCategoriesAndSubCategoriesButton.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.logoutButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        }

        /**
         * A function that initializes all the components in the window
         */
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
            this.subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            this.constraints = new GridBagConstraints();
            this.constraints.insets = new Insets(5, 0, 5, 0);
        }

        /**
         * A function that sets properties of the frame itself
         */
        private void PrepareWindowProperties() {
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
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    View.this.viewModel.logout();
                }
            });
        }

        /**
         * A function that prepares the properties of the title section itself
         */
        private void PrepareTitleSection() {
            this.northPanel.setLayout(new GridBagLayout());

            // Set the future location of the window's title
            GUIUtils.setConstraintsSettings(this.constraints, 0, 0,
                    GridBagConstraints.CENTER, 10, 10);

            // Set the properties of the title
            this.frameTitle.setText("CostMan - Main Window");
            this.frameTitle.setFont(new Font("Consolas", Font.BOLD, 24));

            // Add the title to it's section with the current constraints
            this.northPanel.add(this.frameTitle, this.constraints);
        }

        /**
         * A function that prepares the properties of the menu section itself
         */
        private void PrepareMenuSection() {
            this.centerPanel.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(this.constraints, 0,
                    0, GridBagConstraints.CENTER, 10, 10);
            this.manageTransactionsButton.setPreferredSize(new Dimension(300,
                    this.manageTransactionsButton.getPreferredSize().height));

            this.manageTransactionsButton.addActionListener(e -> {
                frame.setVisible(false);
                transactionsWindow.openWindow();
            });

            this.centerPanel.add(this.manageTransactionsButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 1,
                    GridBagConstraints.CENTER, 10, 10);
            this.generateReportButton.setPreferredSize
                    (new Dimension(300, this.generateReportButton.getPreferredSize().height));

            this.generateReportButton.addActionListener(e -> {
                frame.setVisible(false);
                reportsWindow.frame.setVisible(true);
            });

            this.centerPanel.add(this.generateReportButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 2,
                    GridBagConstraints.CENTER, 10, 10);
            this.manageCategoriesAndSubCategoriesButton.setPreferredSize
                    (new Dimension(300, this.manageCategoriesAndSubCategoriesButton.getPreferredSize().height));

            this.manageCategoriesAndSubCategoriesButton.addActionListener(e -> {
                frame.setVisible(false);
                // categoriesWindow.frame.setVisible(true);
                categoriesWindow.openWindow();
            });

            this.centerPanel.add(this.manageCategoriesAndSubCategoriesButton, this.constraints);
        }

        /**
         * A function that prepares the properties of the logout section itself
         */
        private void PrepareLogoutSection() {
            this.logoutButton.setPreferredSize(new Dimension(100,
                    this.manageTransactionsButton.getPreferredSize().height));
            this.logoutButton.addActionListener(e -> View.this.viewModel.logout());

            // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
            this.southPanel.setLayout(new BorderLayout());
            this.subPanel.add(this.logoutButton);
            this.southPanel.add(subPanel, BorderLayout.SOUTH);
        }
    }

    /**
     * This class implements the UI of the window in the program
     * which responsible to allow the user to add and delete items
     */
    public class TransactionsWindow {
        private JPanel panelNorth, panelCenter, panelSouth;
        private JFrame frame;
        private JLabel note, categoryLabel, subCategoryLabel, sumLabel, currencyLabel, currencyRateLabel, idLabel,
                dateLabel, descriptionLabel, currencyRateSumLabel;
        private JTextField sumTextField,
                dateTextField, descriptionTextField, idTextField;
        private JButton logOutBtn, insertBtn, deleteBtn;
        private GridBagConstraints constraints;
        private String[] currenciesArray;
        private JComboBox<String> categoriesComboBox, subCategoriesComboBox, currenciesComboBox;
        private float[] currencyRatesValues;

        /**
         * Public constructor of TransactionsWindow
         */
        public TransactionsWindow() {
            frame = new JFrame();

            // Handling Combo Boxes
            currenciesArray = new String[]{"ILS", "USD", "EUR", "GBP"};
            currencyRatesValues = new float[4];
            currenciesComboBox = new JComboBox<>(currenciesArray);

            categoriesComboBox = new JComboBox<>();
            subCategoriesComboBox = new JComboBox<>();

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
            sumTextField = new JTextField(10);
            currencyRateSumLabel = new JLabel();
            dateTextField = new JTextField(20);
            descriptionTextField = new JTextField(25);
            idTextField = new JTextField(10);

            // Generating grid bag constraints
            constraints = new GridBagConstraints();

            setProperties();
        }

        /**
         * A function that gets called when this window is opened.
         * Categories are being fetched, and then the window is shown.
         */
        public void openWindow() {
            // Get the primary categories specifically for the "TransactionsWindow"
            View.this.viewModel.getPrimaryCategories(EnumConsumerOfCategories.TransactionsWindow);

            // Show the window
            this.frame.setVisible(true);

            // Setting the default currency rate to the first item in the array
            this.currencyRateSumLabel.setText(String.valueOf(this.currencyRatesValues[0]));
        }

        /**
         * Set properties of TransactionsWindow's window
         */
        public void setProperties() {
            // In order to block the user from changing the displayed rate of the selected currency.
            this.currencyRateSumLabel.setEnabled(false);
            this.categoriesComboBox.addItemListener(e -> {
                // Load dynamically the sub-categories which belongs to the current selected category

                int index = TransactionsWindow.this.categoriesComboBox.getSelectedIndex();
                String item = TransactionsWindow.this.categoriesComboBox.getItemAt(index);
                View.this.viewModel.getSubCategories(item, EnumConsumerOfCategories.TransactionsWindow);
            });

            String selectedCurrencyRate = String.valueOf(currencyRatesValues[0]);
            currencyRateSumLabel.setText(selectedCurrencyRate);

            this.currenciesComboBox.addItemListener(e -> {
                // Show the currency rate of the selected currency
                int indexOfSelectedCurrency = TransactionsWindow.this.currenciesComboBox.getSelectedIndex();
                String chosenCurrencyRate = String.valueOf(currencyRatesValues[indexOfSelectedCurrency]);
                TransactionsWindow.this.currencyRateSumLabel.setText(chosenCurrencyRate);
            });

            // Handling insert button click
            this.insertBtn.addActionListener(e -> {
                Item item;
                try {
                    item = new Item(userID,
                            Objects.requireNonNull(categoriesComboBox.getSelectedItem()).toString(),
                            Objects.requireNonNull(subCategoriesComboBox.getSelectedItem()).toString(),
                            dateTextField.getText(),
                            Integer.parseInt(sumTextField.getText()),
                            Objects.requireNonNull(currenciesComboBox.getSelectedItem()).toString(),
                            Float.parseFloat(currencyRateSumLabel.getText()),
                            descriptionTextField.getText());

                    View.this.viewModel.addItem(item);
                } catch (CostManException ex) {
                    GUIUtils.showErrorMessageBox("Error", "Error inserting new item!");
                }
            });

            // Handling "Delete Item" button click
            deleteBtn.addActionListener(e -> viewModel.deleteItem(idTextField.getText()));

            // Handling "Logout" button click
            logOutBtn.addActionListener(e -> View.this.viewModel.logout());

            // Handling North Panel
            panelNorth.setLayout(new GridBagLayout());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER,
                    10, 40);
            panelNorth.add(note, constraints);

            // Handling Center Panel
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Add a new item: "));

            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(categoryLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(categoriesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(subCategoryLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(subCategoriesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(sumLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 2, GridBagConstraints.WEST,
                    5, 5);
            panelCenter.add(sumTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 3, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(currencyLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 3, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(currenciesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 4, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(currencyRateLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 4, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(currencyRateSumLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 5, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(dateLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 5, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(dateTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 6, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(descriptionLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 6, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(descriptionTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 7, GridBagConstraints.WEST,
                    15, 15);
            panelCenter.add(insertBtn, constraints);

            // Handling South Panel
            panelSouth.setLayout(new GridBagLayout());
            panelSouth.setBorder(BorderFactory.createTitledBorder("Delete an item"));
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.WEST,
                    0, 0);
            panelSouth.add(idLabel, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 0, GridBagConstraints.WEST,
                    10, 10);
            panelSouth.add(idTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 2, 0, GridBagConstraints.WEST,
                    10, 10);
            panelSouth.add(deleteBtn, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.WEST,
                    10, 10);
            panelSouth.add(logOutBtn, constraints);

            setFrameSettings(this.frame, panelCenter, panelNorth, panelSouth);
        }

        /**
         * A function that sets various settings of the frame
         *
         * @param frame the frame to be modified
         * @param panelCenter panel center to be modified
         * @param panelNorth  panel north to be modified
         * @param panelSouth  panel south to be modified
         */
        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
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
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    View.this.viewModel.handleClosingOfFeatureWindow();
                }
            });
        }

        /**
         * A function that clears all fields in the window
         */
        public void clearAllFields() {
            this.sumTextField.setText("");
            this.currencyRateSumLabel.setText("");
            this.dateTextField.setText("");
            this.descriptionTextField.setText("");
            this.idTextField.setText("");

            if (this.categoriesComboBox.getModel().getSize() != 0) {
                this.categoriesComboBox.setSelectedIndex(0);
            }
            this.subCategoriesComboBox.removeAllItems();
            // this.subCategoriesComboBox.setSelectedIndex(0);
            this.currenciesComboBox.setSelectedIndex(0);
        }
    }

    /**
     * This class implements the UI of the window in the program which responsible to
     * allow the user to add and categories and sub-categories
     */
    public class CategoriesWindow {
        private JFrame frame;
        private GridBagConstraints constraints;
        private JLabel addNewCategory, addNewSubCategory, categoryName, rootCategoryName, subCategoryName, backSlash;
        private JButton newCategoryBtn, newSubCategoryBtn, logoutButton;
        private JTextField categoryTextField, subCategoryTextField;
        private JComboBox<String> categoriesComboBox;
        private JPanel panelCenter, panelSouth;

        /**
         * Constructor of CategoriesWindow
         */
        CategoriesWindow() {
            frame = new JFrame("Categories & Sub-Categories Page");

            backSlash = new JLabel("\n");

            addNewCategory = new JLabel("Add a new Category: ");
            categoryName = new JLabel("Category name: ");
            categoryTextField = new JTextField(15);
            newCategoryBtn = new JButton("Add New Category");

            addNewSubCategory = new JLabel("Add a new Sub-Category: ");
            rootCategoryName = new JLabel("Root Category: ");
            categoriesComboBox = new JComboBox<>();
            subCategoryName = new JLabel("Sub-Category name: ");
            subCategoryTextField = new JTextField(15);
            newSubCategoryBtn = new JButton("Add New Sub-Category");

            logoutButton = new JButton("Log Out");

            panelCenter = new JPanel();
            panelSouth = new JPanel();

            constraints = new GridBagConstraints();

            setProperties();
        }

        /**
         * A function that gets called when this window is opened.
         * Categories are being fetched, and then the window is shown.
         */
        public void openWindow() {
            // Get the primary categories specifically for the "CategoriesWindow"
            View.this.viewModel.getPrimaryCategories(EnumConsumerOfCategories.CategoriesWindow);

            // Show the window
            this.frame.setVisible(true);
        }

        /**
         * Set properties of CategoriesWindow's window
         */
        public void setProperties() {
            panelCenter.setLayout(new GridBagLayout());
            panelCenter.setBorder(BorderFactory.createTitledBorder("Insert a new Category or a Sub-Category: "));

            // Handling the main category section
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.WEST,
                    0, 0);
            panelCenter.add(addNewCategory, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 1, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(categoryName, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 1, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(categoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 2, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(backSlash, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 3, GridBagConstraints.CENTER,
                    10, 10);
            panelCenter.add(newCategoryBtn, constraints);

            // Handling the sub category section
            GUIUtils.setConstraintsSettings(constraints, 0, 4, GridBagConstraints.WEST,
                    0, 0);
            panelCenter.add(addNewSubCategory, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 5, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(rootCategoryName, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 5, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(categoriesComboBox, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 6, GridBagConstraints.WEST,
                    10, 10);
            panelCenter.add(subCategoryName, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 6, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(subCategoryTextField, constraints);
            GUIUtils.setConstraintsSettings(constraints, 0, 7, GridBagConstraints.EAST,
                    10, 10);
            panelCenter.add(backSlash, constraints);
            GUIUtils.setConstraintsSettings(constraints, 1, 8, GridBagConstraints.CENTER,
                    10, 10);
            panelCenter.add(newSubCategoryBtn, constraints);

            panelSouth.setLayout(new GridBagLayout());
            panelSouth.setBorder(BorderFactory.createEmptyBorder());
            GUIUtils.setConstraintsSettings(constraints, 0, 0, GridBagConstraints.CENTER,
                    10, 10);
            panelSouth.add(logoutButton, constraints);

            setFrameSettings(this.frame, panelCenter, panelSouth);
            setFonts();

            newCategoryBtn.addActionListener(e -> {
                String newCategoryName = categoryTextField.getText();

                View.this.viewModel.addCategory(newCategoryName, null, EnumCategoryType.Primary);
            });

            newSubCategoryBtn.addActionListener(e -> {
                String newSubCategoryName = subCategoryTextField.getText();
                String ownerOfNewSubCategoryName = Objects.requireNonNull
                        (categoriesComboBox.getSelectedItem()).toString();

                View.this.viewModel.addCategory(newSubCategoryName, ownerOfNewSubCategoryName,
                        EnumCategoryType.Secondary);
            });
        }

        /**
         * A function that changes the fonts shown in the window
         */
        private void setFonts() {
            this.addNewCategory.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.addNewSubCategory.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.categoryName.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.rootCategoryName.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.subCategoryName.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.newCategoryBtn.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.newSubCategoryBtn.setFont(new Font("Consolas", Font.PLAIN, 15));
            this.logoutButton.setFont(new Font("Consolas", Font.PLAIN, 15));
        }

        /**
         * A function that sets various settings of the frame
         *
         * @param frame       the frame to be modified
         * @param panelCenter panel center to be modified
         * @param panelSouth  panel south to be modified
         */
        void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelSouth) {
            frame.setLayout(new BorderLayout());
            frame.add(panelCenter, BorderLayout.CENTER);
            frame.add(panelSouth, BorderLayout.SOUTH);

            frame.setSize(500, 500);
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            frame.setLocationRelativeTo(null);

            frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    View.this.viewModel.handleClosingOfFeatureWindow();
                }
            });
        }

        /**
         * A function that clears all fields in the window
         */
        public void clearAllFields() {
            this.categoryTextField.setText("");

            if (this.categoriesComboBox.getModel().getSize() != 0) {
                this.categoriesComboBox.setSelectedIndex(0);
            }
            this.subCategoryTextField.setText("");
        }
    }

    /**
     * This class implements the UI of the window in the program which responsible to
     * allow the user to generate a report with his items within range of dates (or all of his items)
     */
    public class ReportsWindow {
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
        private JTable reportsTable;
        private JPanel reportsPanel;

        private JPanel subPanel;

        /**
         * Public constructor of ReportsWindow
         */
        public ReportsWindow() {
            this.days = new String[31];
            for (int i = 0; i < 31; i++) {
                this.days[i] = String.valueOf(i + 1);
            }

            this.months = new String[12];
            for (int i = 0; i < 12; i++) {
                this.months[i] = String.valueOf(i + 1);
            }

            this.years = new String[LocalDate.now().getYear() - 1970 + 1];
            for (int i = 0; i < LocalDate.now().getYear() - 1970 + 1; i++) {
                this.years[i] = String.valueOf(1970 + i);
            }

            this.InitializeComponents();
            this.PrepareWindowProperties();
            this.PrepareReportsGenerationSection();
            this.PrepareLogoutSection();
            this.prepareTable();
        }

        /**
         * A function that initializes all the components in the window
         */
        private void InitializeComponents() {
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

            this.reportsPanel = new JPanel();
            this.subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        }

        /**
         * A function that adds the table to the window and adds its columns
         */
        private void prepareTable() {
            reportsPanel.add(new JScrollPane(reportsTable));

            reportsTable.setLayout(new FlowLayout());
            reportsTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            reportsTable.setModel(tableModel);

            tableModel.addColumn("ItemID");
            tableModel.addColumn("Category ID");
            tableModel.addColumn("SubCategory ID");
            tableModel.addColumn("Date");
            tableModel.addColumn("Price");
            tableModel.addColumn("Currency Rate");
            tableModel.addColumn("Description");

            // Taking care of width in specific columns
            reportsTable.getColumnModel().getColumn(1).setPreferredWidth(82);
            reportsTable.getColumnModel().getColumn(2).setPreferredWidth(92);
            reportsTable.getColumnModel().getColumn(4).setPreferredWidth(75);
            reportsTable.getColumnModel().getColumn(5).setPreferredWidth(100);
            reportsTable.getColumnModel().getColumn(6).setPreferredWidth(100);

        }

        /**
         * A function that sets properties of the frame itself
         */
        private void PrepareWindowProperties() {
            // Add the different sections to the window
            this.frame.setLayout(new BorderLayout());
            this.frame.add(this.reportsGenerationSection, BorderLayout.NORTH);
            this.frame.add(this.logoutSection, BorderLayout.SOUTH);

            // Set the visual properties of the window
            this.frame.setTitle("CostMan - Reports Window");
            this.frame.setSize(1000, 750);
            this.frame.setResizable(false);
            this.frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

            // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
            this.frame.setLocationRelativeTo(null);

            // Define event handlers
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    View.this.viewModel.handleClosingOfFeatureWindow();
                }
            });
        }

        /**
         * A function that sets properties of the "Generate Report" section
         */
        private void PrepareReportsGenerationSection() {
            this.reportsGenerationSection.setLayout(new GridBagLayout());
            this.reportsGenerationSection.setBorder(BorderFactory.createTitledBorder("Generate a report"));

            this.selectAllItemsCheckBox.addItemListener(e -> {
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
                } else {
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

            GUIUtils.setConstraintsSettings(this.constraints, 0, 0,
                    GridBagConstraints.FIRST_LINE_START, 0, 10);
            this.reportsGenerationSection.add(this.selectAllItemsCheckBox, this.constraints);

            this.PrepareFromAndToLabelsProperties();
            this.PrepareFromComboBoxesProperties();
            this.PrepareToComboBoxesProperties();

            this.generateReportButton.setText("Generate Report");
            this.generateReportButton.addActionListener(e -> {
                // If the "Generate all transactions" checkbox is checked
                if (ReportsWindow.this.selectAllItemsCheckBox.isSelected()) {
                    ReportsWindow.this.fromDate = "01-01-1970";

                    toDay = String.valueOf(LocalDate.now().getDayOfMonth());
                    toMonth = String.valueOf(LocalDate.now().getMonth().getValue());
                    toYear = String.valueOf(LocalDate.now().getYear());
                } else {
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

            GUIUtils.setConstraintsSettings(this.constraints, 4, 3,
                    GridBagConstraints.CENTER, 0, 10);
            this.reportsGenerationSection.add(this.generateReportButton, this.constraints);

            GUIUtils.setConstraintsSettings(this.constraints, 0, 4,
                    GridBagConstraints.CENTER, 0, 10);
            this.reportsGenerationSection.add(this.reportsPanel, this.constraints);
        }

        /**
         * A function that sets properties of the date selection section
         */
        private void PrepareFromAndToLabelsProperties() {
            this.labelFromDate.setText("Start Date:");
            this.labelFromDate.setFont(new Font("Consolas", Font.BOLD, 14));
            GUIUtils.setConstraintsSettings(this.constraints, 0, 1,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.labelFromDate, this.constraints);

            this.labelToDate.setText("End Date:");
            this.labelToDate.setFont(new Font("Consolas", Font.BOLD, 14));
            GUIUtils.setConstraintsSettings(this.constraints, 0, 2,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.labelToDate, this.constraints);
        }

        /**
         * A function that sets properties of the "FROM" dates ComboBoxes
         */
        private void PrepareFromComboBoxesProperties() {
            // In this method the values of the days, months and years are loaded after the constructor

            this.comboBoxFromDay = new JComboBox<>();
            this.comboBoxFromDay.setModel(new DefaultComboBoxModel<>(this.days));
            GUIUtils.setConstraintsSettings(this.constraints, 1, 1,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromDay, this.constraints);

            this.comboBoxFromMonth = new JComboBox<>();
            this.comboBoxFromMonth.setModel(new DefaultComboBoxModel<>(this.months));
            GUIUtils.setConstraintsSettings(this.constraints, 2, 1,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromMonth, this.constraints);

            this.comboBoxFromYear = new JComboBox<>();
            this.comboBoxFromYear.setModel(new DefaultComboBoxModel<>(this.years));
            GUIUtils.setConstraintsSettings(this.constraints, 3, 1,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxFromYear, this.constraints);
        }

        /**
         * A function that sets properties of the "TO" dates ComboBoxes
         */
        private void PrepareToComboBoxesProperties() {
            // In this method the values of the days, months and years are loaded after the constructor

            this.comboBoxToDay = new JComboBox<>();
            this.comboBoxToDay.setModel(new DefaultComboBoxModel<>(this.days));
            GUIUtils.setConstraintsSettings(this.constraints, 1, 2,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToDay, this.constraints);

            this.comboBoxToMonth = new JComboBox<>();
            this.comboBoxToMonth.setModel(new DefaultComboBoxModel<>(this.months));
            GUIUtils.setConstraintsSettings(this.constraints, 2, 2,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToMonth, this.constraints);

            this.comboBoxToYear = new JComboBox<>();
            this.comboBoxToYear.setModel(new DefaultComboBoxModel<>(this.years));
            GUIUtils.setConstraintsSettings(this.constraints, 3, 2,
                    GridBagConstraints.CENTER, 10, 10);
            this.reportsGenerationSection.add(this.comboBoxToYear, this.constraints);
        }

        /**
         * A function that sets properties of the logout section
         */
        private void PrepareLogoutSection() {
            this.logoutButton.setPreferredSize(new Dimension(100, this.logoutButton.getPreferredSize().height));
            this.logoutButton.addActionListener(e -> View.this.viewModel.logout());

            // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
            this.logoutSection.setLayout(new BorderLayout());
            subPanel.add(this.logoutButton);
            this.logoutSection.add(subPanel, BorderLayout.SOUTH);
        }

        /**
         * A function that populates the JTable with the items retrieved from DB.
         *
         * @param data List of items fetched from the DB.
         */
        public void populateTable(List<Item> data) {
            // Initialize table
            tableModel.setRowCount(0);

            // Re-populating table using for-each loop
            for (Item item : data) {
                tableModel.addRow(new Object[]{
                        item.getItemId(), item.getCategory(), item.getSubCategory(), item.getDate(),
                        item.getPrice(), item.getCurrency() + " (" + item.getCurrencyRate() + ")",
                        item.getDescription()});
            }
        }

        /**
         * A function that clears all fields in the window
         */
        public void clearAllFields() {
            this.comboBoxFromDay.setSelectedIndex(0);
            this.comboBoxFromMonth.setSelectedIndex(0);
            this.comboBoxFromYear.setSelectedIndex(0);

            this.comboBoxToDay.setSelectedIndex(0);
            this.comboBoxToMonth.setSelectedIndex(0);
            this.comboBoxToYear.setSelectedIndex(0);

            this.selectAllItemsCheckBox.setSelected(false);

            for (int i = 0; i < reportsTable.getRowCount(); i++) {
                for (int j = 0; j < reportsTable.getColumnCount(); j++) {
                    reportsTable.setValueAt("", i, j);
                }
            }
        }
    }
}