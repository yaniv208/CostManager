package il.ac.hit;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
                TransactionsWindow transactionsWindow = new TransactionsWindow();
                transactionsWindow.Show();

                // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                MainWindow.this.m_Window.dispose();
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
                ReportsWindow reportsWindow = new ReportsWindow();
                reportsWindow.Show();

                // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                MainWindow.this.m_Window.dispose();
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
                CategoriesWindow categoriesWindow = new CategoriesWindow();
                categoriesWindow.start();

                // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
                MainWindow.this.m_Window.dispose();
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
