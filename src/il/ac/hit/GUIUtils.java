package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * A class providing various functionality regarding software's GUI.
 */
public class GUIUtils {
    /**
     * Show a message box to the user with an "OK" option.
     *
     * @param title Title of the message box
     * @param message Main content of the message box
     */
    public static void showOkMessageBox(String title, String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.INFORMATION_MESSAGE));
    }

    /**
     * Show a message box to the user approving exit from the software.
     */
    public static void showExitMessageBox() {
        SwingUtilities.invokeLater(() -> {
            if (GUIUtils.showYesNoMessageBox("Exit?", "Are you sure that you want to exit?")) {
                System.exit(0);
            }
        });
    }

    /**
     * Show a message box to the user stating that an error has occurred
     *
     * @param title Title of the message box
     * @param message Main content of the message box
     */
    public static void showErrorMessageBox(String title, String message) {
        SwingUtilities.invokeLater(() ->
                JOptionPane.showMessageDialog(null, message, title, JOptionPane.ERROR_MESSAGE));
    }

    /**
     * Show a message box to the user with 2 options: "Yes" and "No"
     *
     * @param title Title of the message box
     * @param message Main content of the message box
     */
    public static boolean showYesNoMessageBox(String title, String message) {
        boolean isYes = true;
        int dialogButton = JOptionPane.YES_NO_OPTION;
        int dialogResult = JOptionPane.showConfirmDialog(null, message, title, dialogButton);
        if (dialogResult == JOptionPane.NO_OPTION || dialogResult == JOptionPane.CLOSED_OPTION) {
            isYes = false;
        }

        return isYes;
    }

    /**
     * Set the constraints of each item of the screen according to specific conditions
     *
     * @param currentConstraintsObject current object being modified
     * @param columnNumberInGrid "x" axis of item
     * @param lineNumberInGrid "y" axis of item
     * @param anchor general padding
     * @param ipadx vertical padding
     * @param ipady horizontal padding
     */
    public static void setConstraintsSettings(GridBagConstraints currentConstraintsObject, int columnNumberInGrid,
                                              int lineNumberInGrid, int anchor, int ipadx, int ipady) {
        currentConstraintsObject.gridx = columnNumberInGrid;
        currentConstraintsObject.gridy = lineNumberInGrid;
        currentConstraintsObject.anchor = anchor;
        currentConstraintsObject.ipadx = ipadx;
        currentConstraintsObject.ipady = ipady;
    }

    /**
     * A function that checks if the given input date is valid date or not
     *
     * @param date The given date which should be checked
     * @return true if valid, otherwise false
     */
    public static boolean isDateValid(String date) {
        DateFormat df;
        try {
            df = new SimpleDateFormat("dd-MM-yyyy");

            // Do a strict-check with the format and not relying on heuristics.
            df.setLenient(false);
            df.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
}