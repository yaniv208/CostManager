package il.ac.hit;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TransactionsWindow
{
    private JFrame m_Window      = null;
    private JLabel m_WindowTitle = null;

    private JPanel m_PanelNorth  = null;
    private JPanel m_PanelCenter = null;
    private JPanel m_PanelSouth  = null;
    private JPanel m_ScreenPanel = null;

    private JButton m_LogoutButton = null;

    private GridBagConstraints m_Constraints = null;

    public TransactionsWindow()
    {
        this.m_Window = new JFrame();

        // Set the visual properties of the window
        this.m_Window.setTitle("CostMan - Transactions Window");
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
                GUIUtils.HandleGoOutFromWindows(TransactionsWindow.this.m_Window);
            }
        });
    }

    public void Show()
    {
        this.m_Window.setVisible(true);
    }
}

