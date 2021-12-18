package il.ac.hit;

import javax.swing.*;
import java.awt.*;

public class AddingCategoriesPage {
    private JFrame frame;
    private IViewModel vm;
    private GridBagConstraints constraints;
    private JLabel note, addNewCategory, addNewSubCategory, categoryName,
            rootCategoryName, subCategoryName, backSlash, whitespaces;
    private JButton newCategoryBtn, newSubCategoryBtn, logOutBtn;
    private JTextField categoryTextField, rootCategoryTextField, subCategoryTextField;
    private JPanel panelCenter, panelNorth, panelSouth;

    AddingCategoriesPage() {
        frame = new JFrame("Adding Categories & Sub-Categories Page");

        //note = new JLabel("Please choose whether you want to insert a new Category or a Sub-Category: ");
        backSlash = new JLabel("\n");
        whitespaces = new JLabel("               ");

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

        panelNorth = new JPanel();
        panelCenter = new JPanel();

        panelSouth = new JPanel();

        constraints = new GridBagConstraints();
    }

    public void start() {
        panelCenter.setFont(Font.getFont(Font.SANS_SERIF));
        panelSouth.setFont(Font.getFont(Font.SANS_SERIF));
        panelNorth.setFont(Font.getFont(Font.SANS_SERIF));



        // Handling North Panel
        /*panelNorth.setLayout(new GridBagLayout());
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 40);
        panelNorth.add(note, constraints);*/

        panelCenter.setLayout(new GridBagLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Insert a new Category or a Sub-Category: "));
        setMyConstraints(constraints, 0, 0, GridBagConstraints.WEST, 0, 0);
        panelCenter.add(addNewCategory, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(categoryName, constraints);
        setMyConstraints(constraints, 1, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(categoryTextField, constraints);
        setMyConstraints(constraints, 0, 2, GridBagConstraints.CENTER, 10, 10);
        panelCenter.add(backSlash, constraints);
        setMyConstraints(constraints, 0, 3, GridBagConstraints.CENTER, 10, 10);
        panelCenter.add(whitespaces);
        setMyConstraints(constraints, 1, 3, GridBagConstraints.CENTER, 10, 10);
        panelCenter.add(newCategoryBtn, constraints);


        panelSouth.setLayout(new GridBagLayout());
        panelSouth.setBorder(BorderFactory.createEmptyBorder());
        setMyConstraints(constraints, 10, 0, GridBagConstraints.WEST, 0, 10);
        panelSouth.add(logOutBtn, constraints);

        setFrameSettings(frame, panelCenter, panelNorth, panelSouth);
    }


    void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
        frame.setLayout(new BorderLayout());
        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelNorth, BorderLayout.NORTH);
        frame.add(panelSouth, BorderLayout.SOUTH);

        frame.setSize(500, 500);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void setMyConstraints(GridBagConstraints c, int gridx, int gridy, int anchor, int ipadx, int ipady) {
        c.gridx = gridx;
        c.gridy = gridy;
        c.anchor = anchor;
        c.ipadx = ipadx;
        c.ipady = ipady;
    }
}
