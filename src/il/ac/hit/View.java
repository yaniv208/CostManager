package il.ac.hit;

import javax.swing.*;

public class View implements IView
{
    IViewModel viewModel = null;
    private LoginWindow loginWindow = null;
    private RegistrationWindow registrationWindow = null;
    private MainWindow mainWindow = null;
    private TransactionsWindow transactionsWindow = null;
    private CategoriesWindow categoriesWindow = null;
    private ReportsWindow reportsWindow = null;

    /*
    private JFrame currentActiveWindow;

    public enum eWindowType
    {
        LoginWindow,
        RegistrationWindow,
        MainWindow,
        TransactionsWindow,
        CategoriesWindow,
        ReportsWindow
    }

    public void moveFromCurrentWindowToOtherWindow(eWindowType currentActiveWindow)
    {
        this.currentActiveWindow.setVisible(false);

        switch (currentActiveWindow)
        {
            case LoginWindow:
                this.currentActiveWindow = loginWindow;
                break;

            case RegistrationWindow:
                this.currentActiveWindow = registrationWindow;
                break;

            case MainWindow:
                this.currentActiveWindow = mainWindow;
                break;

            case TransactionsWindow:
                this.currentActiveWindow = transactionsWindow;
                break;

            case CategoriesWindow:
                this.currentActiveWindow = categoriesWindow;
                break;

            case ReportsWindow:
                this.currentActiveWindow = reportsWindow;
                break;
        }

        this.currentActiveWindow.setVisible(true);
    }
    */

    /*
    LoginWindow loginWindow;
    RegistrationWindow registrationWindow;
    MainWindow mainWindow;
    TransactionsWindow transactionsWindow;
    CategoriesWindow categoriesWindow;
    ReportsWindow reportsWindow;
    */

    /*
    public void moveFromLoginWindowToRegistrationWindow()
    {
        this.loginWindow.setVisible(false);
        this.registrationWindow.showWindow();
    }

    public void moveFromRegistrationWindowToLoginWindow()
    {
        this.registrationWindow.setVisible(false);
        this.loginWindow.showWindow();
    }

    public void moveFromLoginWindowToMainWindow()
    {
        this.loginWindow.setVisible(false);
        this.registrationWindow.showWindow();
    }

    public void moveFromMainWindowToTransactionsWindow()
    {
        this.mainWindow.setVisible(false);
        this.transactionsWindow.showWindow();
    }

    public void moveFromMainWindowToCategoriesWindow()
    {
        this.mainWindow.setVisible(false);
        this.categoriesWindow.showWindow();
    }

    public void moveFromMainWindowToReportsWindow()
    {
        this.mainWindow.setVisible(false);
        this.reportsWindow.showWindow();
    }

    public void moveFromTransactionsWindowToMainWindow()
    {
        this.transactionsWindow.setVisible(false);
        this.mainWindow.showWindow();
    }

    public void moveFromCategoriesWindowToMainWindow()
    {
        this.categoriesWindow.setVisible(false);
        this.mainWindow.showWindow();
    }

    public void moveFromReportsWindowToMainWindow()
    {
        this.reportsWindow.setVisible(false);
        this.mainWindow.showWindow();
    }
    */

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

        this.currentActiveWindow = loginWindow;
    }

    @Override
    public void start()
    {
        this.currentActiveWindow.setVisible(true);
    }
}