package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class LoginPage {
    private GridBagConstraints constraints;
    private IViewModel vm;
    private JFrame frame;
    private JPanel panelCenter, panelNorth, panelSouth;
    private JLabel note, emailLabel, passwordLabel;
    private JTextField emailTextField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox;
    private JButton loginBtn, createAccountBtn;
    private int validation;
    private char defaultEchoChar;

    public LoginPage() {
        // Generating Frame
        frame = new JFrame("Cost Manager Program");

        // Generating labels
        note = new JLabel("Welcome to Cost Manager!");
        emailLabel = new JLabel("E-Mail: ");
        passwordLabel = new JLabel("Password: ");

        // Generating text fields
        emailTextField = new JTextField(20);

        // Generating password field
        passwordField = new JPasswordField(20);

        // Generating check box
        showPasswordCheckBox = new JCheckBox("Show password");

        // Generating buttons
        loginBtn = new JButton("Login");
        createAccountBtn = new JButton("Create an account");

        // Generating panels
        panelNorth = new JPanel();
        panelCenter = new JPanel();
        panelSouth = new JPanel();

        // Generating grid bag constraints
        constraints = new GridBagConstraints();
    }

    //@Override
    public void setIViewModel(IViewModel vm) {
        this.vm = vm;
    }

//    public void init() {}

    public void start() {
        note.setFont(Font.getFont(Font.SANS_SERIF));
        emailLabel.setFont(Font.getFont(Font.SANS_SERIF));
        passwordLabel.setFont(Font.getFont(Font.SANS_SERIF));
        showPasswordCheckBox.setFont(Font.getFont(Font.SANS_SERIF));

        // Handling show password checkbox
        defaultEchoChar = passwordField.getEchoChar();
        showPasswordCheckBox.setSelected(false);
        showPasswordCheckBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    passwordField.setEchoChar((char) 0);
                } else {
                    passwordField.setEchoChar(defaultEchoChar);
                }
            }
        });

        // Handling North Panel
        panelNorth.setLayout(new GridBagLayout());
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 40);
        panelNorth.add(note, constraints);

        panelCenter.setLayout(new GridBagLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Please enter your login information: "));
        setMyConstraints(constraints, 0, 0, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(emailLabel, constraints);
        setMyConstraints(constraints, 1, 0, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(emailTextField, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(passwordLabel, constraints);
        setMyConstraints(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(passwordField, constraints);
        setMyConstraints(constraints, 0, 2, GridBagConstraints.WEST, 15, 15);
        panelCenter.add(showPasswordCheckBox, constraints);

        panelSouth.setLayout(new GridBagLayout());
        panelSouth.setBorder(BorderFactory.createTitledBorder("Actions"));
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
        panelSouth.add(loginBtn, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
        panelSouth.add(createAccountBtn, constraints);

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

        /* loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = vm.selectUserCredentials(emailTextField.getText(), passwordField.getText());
                if(validation != 0){
                    isLogin = true;
                    frame.setVisible(false);
                    changeView(new NavigationPage());
                }
            }
        });
        createAccountBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                changeView(new RegistrationPage());
            }
        });
    }

    }

    */
