package il.ac.hit;
import javax.swing.*;
import java.awt.*;

public class GUIUtils
{
    public static void ShowOkMessageBox(String title, String message)
    {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE));
    }

    public static void ShowExitMessageBox()
    {
        SwingUtilities.invokeLater(() -> {
            if (GUIUtils.ShowYesNoMessageBox("Exit?", "Are you sure that you want to exit?")) {
                System.exit(0);
            }
        });
    }

    public static void ShowErrorMessageBox(String title, String message)
    {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE));
    }

    public static boolean ShowYesNoMessageBox(String title, String message)
    {
        boolean isYes = true;
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, message, title, dialogButton);
        if(dialogResult == JOptionPane.NO_OPTION)
        {
            isYes = false;
        }

        return isYes;
    }

    // TODO: move this to VM and do all necessary changes
    /*
    public static void HandleGoOutFromWindows(JFrame currentActiveWindow)
    {
        boolean result = GUIUtils.ShowYesNoMessageBox("Get out of this window?", "Are you sure you want to get out of this window?\nAll unsaved changes will be lost !");

        if (result == true)
        {
            MainWindow mainWindow = new MainWindow();
            mainWindow.Show();

            // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
            currentActiveWindow.dispose();
        }
    }
    */

    public static void setConstraintsSettings(GridBagConstraints currentConstraintsObject, int columnNumberInGrid, int lineNumberInGrid, int anchor, int ipadx, int ipady) {
        currentConstraintsObject.gridx = columnNumberInGrid;
        currentConstraintsObject.gridy = lineNumberInGrid;
        currentConstraintsObject.anchor = anchor;
        currentConstraintsObject.ipadx = ipadx;
        currentConstraintsObject.ipady = ipady;
    }

}

