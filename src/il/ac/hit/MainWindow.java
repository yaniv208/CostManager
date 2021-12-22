package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Aviv
 */
public class MainWindow
{
    private JFrame frame;
    private JLabel windowTitle;
    private JButton manageTransactionButton, generateReportButton, manageCategoriesAndSubCategoriesButton, logoutButton;
    private JPanel panelNorth, panelCenter, panelSouth;
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
        // Generating the frame:
        this.frame = new JFrame();

        // Components of the Title Section:
        this.panelNorth = new JPanel();
        this.windowTitle = new JLabel();

        // Components of the Menu Section:
        this.panelCenter = new JPanel();
        this.manageTransactionButton = new JButton("Manage Transactions");
        this.generateReportButton = new JButton("Generate Transactions Report");
        this.manageCategoriesAndSubCategoriesButton = new JButton("Edit Categories & Sub-Categories");
        this.logoutButton = new JButton("Log out");

        // Components of the Logout Section:
        this.panelSouth = new JPanel();

        this.constraints = new GridBagConstraints();
        this.constraints.insets = new Insets(5, 0, 5, 0);
    }

    private void PrepareWindowProperties()
    {
        // Add the different sections to the window
        this.frame.setLayout(new BorderLayout());
        this.frame.add(this.panelNorth, BorderLayout.NORTH);
        this.frame.add(this.panelCenter, BorderLayout.CENTER);
        this.frame.add(this.panelSouth, BorderLayout.SOUTH);

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
                GUIUtils.showExitMessageBox();
            }
        });
    }

    private void PrepareTitleSection()
    {
        this.panelNorth.setLayout(new GridBagLayout());

        // Set the future location of the window's title
        GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);

        // Set the properties of the title
        this.PrepareTitleProperties();

        // Add the title to it's section with the current constraints
        this.panelNorth.add(this.windowTitle, this.constraints);
    }

    private void PrepareTitleProperties()
    {
        this.windowTitle.setText("CostMan - Main Window");
        this.windowTitle.setFont(new Font("Consolas", Font.BOLD, 24));
    }

    private void PrepareMenuSection()
    {
        this.panelCenter.setLayout(new GridBagLayout());
        GUIUtils.setConstraintsSettings(this.constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
        this.manageTransactionButton.setPreferredSize(new Dimension(230, this.manageTransactionButton.getPreferredSize().height));
        this.manageTransactionButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                TransactionsWindow transactionsWindow = new TransactionsWindow();
                //transactionsWindow.Show();

                // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                MainWindow.this.frame.dispose();
            }
        });

        this.panelCenter.add(this.manageTransactionButton, this.constraints);

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
                MainWindow.this.frame.dispose();
            }
        });

        this.panelCenter.add(this.generateReportButton, this.constraints);

        GUIUtils.setConstraintsSettings(this.constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
        this.manageCategoriesAndSubCategoriesButton.setPreferredSize(new Dimension(230, this.manageCategoriesAndSubCategoriesButton.getPreferredSize().height));
        this.manageCategoriesAndSubCategoriesButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                CategoriesWindow categoriesWindow = new CategoriesWindow();
                categoriesWindow.start();

                // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                MainWindow.this.frame.dispose();
            }
        });

        this.panelCenter.add(this.manageCategoriesAndSubCategoriesButton, this.constraints);
    }

    private void PrepareLogoutSection()
    {
        this.logoutButton.setPreferredSize(new Dimension(100, this.manageTransactionButton.getPreferredSize().height));
        this.logoutButton.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                GUIUtils.showExitMessageBox();
            }
        });

        // Place the button in the bottom-right of the window (Source: https://stackoverflow.com/a/11165906/2196301)
        this.panelSouth.setLayout(new BorderLayout());
        JPanel subPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        subPanel.add(this.logoutButton);
        this.panelSouth.add(subPanel, BorderLayout.SOUTH);
    }

    public void show()
    {
        this.frame.setVisible(true);
    }
}
