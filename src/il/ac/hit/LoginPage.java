package il.ac.hit;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

public class LoginPage {
    private GridBagConstraints constraints;
    private IViewModel vm;
    private JFrame frame;
    private JPanel panelCenter, panelNorth, panelSouth;
    private JLabel note, welcomeLabel, emailLabel, passwordLabel;
    private JTextField emailTextField, passwordTextField;
    private JButton loginBtn, createAccountBtn;
    private int validation;

    public LoginPage() {
        frame = new JFrame("Cost Manager Program");
        note = new JLabel("Welcome to Cost Manager!");
        welcomeLabel = new JLabel("Please enter your login information: ");

        emailLabel = new JLabel("E-Mail: ");
        passwordLabel = new JLabel("Password: ");
        emailTextField = new JTextField(20);
        passwordTextField = new JTextField(20);

        loginBtn = new JButton("Login");
        createAccountBtn = new JButton("Create an account");

        panelNorth = new JPanel();
        panelCenter = new JPanel();

        panelSouth = new JPanel();

        constraints = new GridBagConstraints();
    }

    //@Override
    public void setIViewModel(IViewModel vm) {
        this.vm = vm;
    }

//    public void init() {}

    public void start() {
        panelCenter.setFont(Font.getFont(Font.SANS_SERIF));
        panelSouth.setFont(Font.getFont(Font.SANS_SERIF));
        panelNorth.setFont(Font.getFont(Font.SANS_SERIF));

        // Handling North Panel
        panelNorth.setLayout(new GridBagLayout());
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 40);
        panelNorth.add(note, constraints);

        panelCenter.setLayout(new GridBagLayout());
        panelCenter.setBorder(BorderFactory.createTitledBorder("Please enter your login information: "));
        setMyConstraints(constraints, 0, 0, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(emailLabel, constraints);
        setMyConstraints(constraints, 1, 0, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(emailTextField, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.EAST, 10, 10);
        panelCenter.add(passwordLabel, constraints);
        setMyConstraints(constraints, 1, 1, GridBagConstraints.WEST, 10, 10);
        panelCenter.add(passwordTextField, constraints);

        panelSouth.setLayout(new GridBagLayout());
        panelSouth.setBorder(BorderFactory.createTitledBorder("Actions"));
        setMyConstraints(constraints, 0, 0, GridBagConstraints.CENTER, 10, 10);
        panelSouth.add(loginBtn, constraints);
        setMyConstraints(constraints, 0, 1, GridBagConstraints.CENTER, 10, 10);
        panelSouth.add(createAccountBtn, constraints);

        setFrameSettings(frame, panelCenter, panelNorth, panelSouth);
    }

    static void setFrameSettings(JFrame frame, JPanel panelCenter, JPanel panelNorth, JPanel panelSouth) {
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

        /* loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validation = vm.selectUserCredentials(emailTextField.getText(), passwordTextField.getText());
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
