package il.ac.hit;

import javax.swing.*;
import java.awt.*;

public class AddingCategoriesPage {
    private JFrame frame;
    private GridBagConstraints constraints;
    private JLabel addNewCategory, addNewSubCategory, categoryName, rootCategoryName, subCategoryName, backSlash;
    private JButton newCategoryBtn, newSubCategoryBtn, logOutBtn;
    private JTextField categoryTextField, rootCategoryTextField, subCategoryTextField;
    private JPanel panelCenter, panelSouth;

    AddingCategoriesPage() {
        frame = new JFrame("Categories & Sub-Categories Page");

        backSlash = new JLabel("\n");

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

        panelCenter = new JPanel();
        panelSouth = new JPanel();

        constraints = new GridBagConstraints();
    }

    public void start() {
        panelCenter.setLayout(new GridBagLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Insert a new Category or a Sub-Category: "));

        // Handling the main category section
        setMyConstraints(constraints, 0, 0, GridBagConstraints.WEST, 0, 0);
        panelCenter.add(addNewCategory, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(categoryName, constraints);
        setMyConstraints(constraints, 1, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(categoryTextField, constraints);
        setMyConstraints(constraints, 0, 2, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(backSlash, constraints);
        setMyConstraints(constraints, 1, 3, GridBagConstraints.CENTER, 10, 10);
        panelCenter.add(newCategoryBtn, constraints);

        // Handling the sub category section
        setMyConstraints(constraints, 0, 4, GridBagConstraints.WEST, 0, 0);
        panelCenter.add(addNewSubCategory, constraints);
        setMyConstraints(constraints, 0, 5, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(rootCategoryName, constraints);
        setMyConstraints(constraints, 1, 5, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(rootCategoryTextField, constraints);
        setMyConstraints(constraints, 0, 6, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(subCategoryName, constraints);
        setMyConstraints(constraints, 1, 6, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(subCategoryTextField, constraints);
        setMyConstraints(constraints, 0, 7, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(backSlash, constraints);
        setMyConstraints(constraints, 1, 8, GridBagConstraints.CENTER, 10, 10);
        panelCenter.add(newSubCategoryBtn, constraints);

        panelSouth.setLayout(new GridBagLayout());
        panelSouth.setBorder(BorderFactory.createEmptyBorder());
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
        panelSouth.add(logOutBtn, constraints);

        setFrameSettings(frame, panelCenter, panelSouth);
    }


    void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelSouth) {
        frame.setLayout(new BorderLayout());
        frame.add(panelCenter, BorderLayout.CENTER);
        frame.add(panelSouth, BorderLayout.SOUTH);

        frame.setSize(500, 500);
        frame.setResizable(false);
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