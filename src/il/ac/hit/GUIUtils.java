package il.ac.hit;
import javax.swing.*;
import java.awt.*;

public class GUIUtils
{
    public static boolean showYesNoMessageBox(String title, String message)
    {
        boolean isYes = true;
        int dialogButtons = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, message, title, dialogButtons);
        if (dialogResult == JOptionPane.NO_OPTION) {
            isYes = false;
        }

        return isYes;
    }

    public static void showOkMessageBox(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static void showExitMessageBox()
    {
        if (GUIUtils.showYesNoMessageBox("Exit?", "Are you sure that you want to exit?"))
        {
            System.exit(0);
        }
        // TODO FIX BUG, CHOOSING NO ALWAYS CLOSING PROGRAM
    }

    public static void showErrorMessageBox(String title, String message)
    {
        JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE);
    }

    public static void handleGoOutFromWindows(JFrame currentActiveWindow)
    {
        boolean result = GUIUtils.showYesNoMessageBox("Get out of this window?",
                "Are you sure you want to get out of this window?\nAll unsaved changes will be lost!");

        if (result) {
            MainWindow mainWindow = new MainWindow();
            mainWindow.show();

            // Close current window (Source: https://stackoverflow.com/a/17716008/2196301)
            currentActiveWindow.dispose();
        }
    }

    public static void setConstraintsSettings(GridBagConstraints currentConstraintsObject, int columnNumberInGrid, int lineNumberInGrid, int anchor, int ipadx, int ipady) {
        currentConstraintsObject.gridx = columnNumberInGrid;
        currentConstraintsObject.gridy = lineNumberInGrid;
        currentConstraintsObject.anchor = anchor;
        currentConstraintsObject.ipadx = ipadx;
        currentConstraintsObject.ipady = ipady;
    }
}

