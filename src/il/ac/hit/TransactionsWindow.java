package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @author Aviv
 */
public class TransactionsWindow {
    private JFrame frame;
    private JPanel panelNorth, panelCenter, panelSouth;
    private JLabel note, categoryLabel, subCategoryLabel, sumLabel, currencyLabel, idLabel,
    dateLabel, descriptionLabel;
    private JTextField categoryTextField, subCategoryTextField, sumTextField,
    dateTextField, descriptionTextField, idTextField;
    private JButton logOutBtn, insertBtn, deleteBtn;
    private GridBagConstraints constraints;
    private String[] currencies;
    private SpinnerListModel spinnerListModel;
    private JSpinner currencySpinner;

    public TransactionsWindow(){

        // Handling currency spinner
        currencies = new String[]{"ILS", "USD", "EUR", "GBP"};
        spinnerListModel = new SpinnerListModel(currencies);
        currencySpinner = new JSpinner(spinnerListModel);

        // Generating frame
        frame = new JFrame("CostMan - Transactions Window");

        // Define event handlers
        this.frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
               // GUIUtils.HandleGoOutFromWindows(TransactionsWindow.this.frame);
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

    public void start() {
        // Set the visual properties of the window
        frame.setSize(500, 800);
        frame.setResizable(false);

        // Place the window in the center of the screen (Source: https://stackoverflow.com/a/2442614/2196301)
        frame.setLocationRelativeTo(null);

        // Handling North Panel
        panelNorth.setLayout(new GridBagLayout());
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 40);
        panelNorth.add(note, constraints);

        // Handling Center Panel
        panelCenter.setLayout(new GridBagLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Add a new item: "));

        setMyConstraints(constraints, 0, 0, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(categoryLabel, constraints);
        setMyConstraints(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(categoryTextField, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(subCategoryLabel, constraints);
        setMyConstraints(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(subCategoryTextField, constraints);
        setMyConstraints(constraints, 0, 2, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(sumLabel, constraints);
        setMyConstraints(constraints, 1, 2, GridBagConstraints.WEST, 5, 5);
        panelCenter.add(sumTextField, constraints);
        setMyConstraints(constraints, 0, 3, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(currencyLabel, constraints);
        setMyConstraints(constraints, 1, 3, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(currencySpinner, constraints);
        setMyConstraints(constraints, 0, 4, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(dateLabel, constraints);
        setMyConstraints(constraints, 1, 4, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(dateTextField, constraints);
        setMyConstraints(constraints, 0, 5, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(descriptionLabel, constraints);
        setMyConstraints(constraints, 1, 5, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(descriptionTextField, constraints);
        setMyConstraints(constraints, 1, 6, GridBagConstraints.WEST, 15, 15);
        panelCenter.add(insertBtn, constraints);

        panelSouth.setLayout(new GridBagLayout());
        panelSouth.setBorder(BorderFactory.createTitledBorder("Delete an item"));
        setMyConstraints(constraints, 0, 0, GridBagConstraints.WEST, 0, 0);
        panelSouth.add(idLabel, constraints);
        setMyConstraints(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
        panelSouth.add(idTextField, constraints);
        setMyConstraints(constraints, 2, 0, GridBagConstraints.WEST, 10, 10);
        panelSouth.add(deleteBtn, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.WEST, 10, 10);
        panelSouth.add(logOutBtn, constraints);

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

        frame.addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                GUIUtils.handleGoOutFromWindows(TransactionsWindow.this.frame);
            }
        });
    }

    private void setMyConstraints(GridBagConstraints c, int gridx, int gridy, int anchor, int ipadx, int ipady) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.anchor = anchor;
        c.ipadx = ipadx;
        c.ipady = ipady;
    }
}
