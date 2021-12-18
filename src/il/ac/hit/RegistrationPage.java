package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegistrationPage implements IView{
    private User user;
    private JFrame frame;
    private IViewModel vm;
    private GridBagConstraints constraints;
    private JLabel note, fullNameLabel, emailLabel, passwordLabel;
    private JButton registerBtn;
    private JTextField emailTextField, fullNameTextField;
    private JPasswordField passwordTextField;
    private JPanel panelCenter, panelNorth, panelSouth;

    RegistrationPage() {
        frame = new JFrame("Registration page");

        note = new JLabel("Please fill the following form:");

        emailLabel = new JLabel("E-Mail: ");
        emailTextField = new JTextField(20);
        passwordLabel = new JLabel("Password: ");
        passwordTextField = new JPasswordField(20);
        fullNameLabel = new JLabel("Full Name: ");
        fullNameTextField = new JTextField(20);

        registerBtn = new JButton("Create Account");

        panelCenter = new JPanel();
        panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));
        panelNorth = new JPanel();
        panelSouth = new JPanel();

        constraints = new GridBagConstraints();
    }

    @Override
    public void setIViewModel(IViewModel vm) {
        this.vm = vm;
    }

    @Override
    public void init() {

    }

    public void start() {

        panelCenter.setFont(Font.getFont(Font.SANS_SERIF));
        panelSouth.setFont(Font.getFont(Font.SANS_SERIF));
        panelNorth.setFont(Font.getFont(Font.SANS_SERIF));

        // Handling North Panel
        panelNorth.setLayout(new GridBagLayout());
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 20);
        panelNorth.add(note, constraints);

        // Handling Center Panel
        panelCenter.setLayout(new GridBagLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Details"));

        setMyConstraints(constraints, 0,0, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(emailLabel, constraints);
        setMyConstraints(constraints, 1, 0, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(emailTextField, constraints);

        setMyConstraints(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(passwordLabel, constraints);
        setMyConstraints(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(passwordTextField, constraints);

        setMyConstraints(constraints, 0,2, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(fullNameLabel, constraints);
        setMyConstraints(constraints, 1,2, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(fullNameTextField, constraints);

        // Handling South Panel
        panelSouth.setLayout(new GridBagLayout());
        setMyConstraints(constraints, 0,0, GridBagConstraints.CENTER, 10, 15);
        panelSouth.add(registerBtn, constraints);

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
