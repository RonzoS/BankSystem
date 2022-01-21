package com.bank.GUI;

import com.bank.domain.*;
import com.bank.service.NumberGenerator;
import com.bank.util.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.LocalDate;

public class RegisterFrame extends JFrame implements ActionListener {

    //Class needs remake

    AccountCRUD accountCRUD = new AccountCRUD();
    CardCRUD cardCRUD = new CardCRUD();
    UserCRUD userCRUD = new UserCRUD();
    Account account = new Account();
    Card card = new Card();
    User user = new User();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    String[] dayStrings = new String[30];
    String[] monthString = new String[11];
    String[] yearString = new String[100];

    JLabel registerForm = new JLabel();
    JLabel loginLabel = new JLabel();
    JLabel loginLabelError = new JLabel();
    JLabel passwordLabel = new JLabel();
    JLabel passwordLabelError = new JLabel();
    JLabel pinLabel = new JLabel();
    JLabel pinLabelError = new JLabel();
    JLabel nameLabel = new JLabel();
    JLabel surnameLabel = new JLabel();
    JLabel emailLabel = new JLabel();
    JLabel phoneLabel = new JLabel();
    JLabel dateLabel = new JLabel();
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JPasswordField pinField = new JPasswordField();
    JTextField nameField = new JTextField();
    JTextField surnameField = new JTextField();
    JTextField emailField = new JTextField();
    JTextField phoneField = new JTextField();
    JComboBox dayField = new JComboBox();
    JComboBox monthField = new JComboBox();
    JComboBox yearField = new JComboBox();
    JButton registerButton = new JButton();
    JButton returnButton = new JButton();

    public RegisterFrame(){
        //creating date ComboBox

        for(int i = 0; i<=dayStrings.length; i++)
            dayField.addItem(Integer.toString(i+1));

        for(int i = 0; i<=monthString.length; i++)
            monthField.addItem(Integer.toString(i+1));

        for(int i = 0; i<=yearString.length; i++)
            yearField.addItem(Integer.toString(i+1908));

        //JFrame basic setting

        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        this.setTitle("Bank");
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(700, 420);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);

        //MainLabel

        registerForm.setText("Create your Bank Account");
        registerForm.setBounds(215, 10, 250, 30);
        registerForm.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(registerForm);

        //Login label and field

        loginLabel.setText("Login:");
        loginLabel.setBounds(30, 40, 100, 50);
        this.add(loginLabel);

        loginLabelError.setText("");
        loginLabelError.setBounds(30, 60, 300, 50);
        this.add(loginLabelError);

        loginField.setBounds(120, 55, 130, 20);
        loginField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(accountCRUD.checkAccountByLogin(loginField.getText())) {
                    loginLabelError.setText("User already exist");
                    loginLabelError.setForeground(Color.RED);
                    checkValid();
                }
                else {
                    loginLabelError.setText("");
                    checkValid();
                }
            }

        });
        this.add(loginField);

        //Password label and field

        passwordLabel.setText("Password:");
        passwordLabel.setBounds(30, 90, 100, 50);
        this.add(passwordLabel);

        passwordLabelError.setText("Password should be longer than 5 characters");
        passwordLabelError.setForeground(Color.RED);
        passwordLabelError.setBounds(30, 110, 300, 50);
        this.add(passwordLabelError);

        passwordField.setBounds(120, 105, 130,20);
        passwordField.addKeyListener(new KeyAdapter() {

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                if(passwordField.getDocument().getLength() <= 5) {
                    passwordLabelError.setText("Password should be longer than 5 characters");
                    passwordLabelError.setForeground(Color.RED);
                }
                else{
                    passwordLabelError.setText("Password should be longer than 5 characters");
                    passwordLabelError.setForeground(Color.GRAY);
                }
                checkValid();
            }
        });
        this.add(passwordField);

        //PIN label and field

        pinLabel.setText("PIN:");
        pinLabel.setBounds(30, 140, 100 ,50);
        this.add(pinLabel);

        pinLabelError.setText("four dignits required");
        pinLabelError.setForeground(Color.GRAY);
        pinLabelError.setBounds(30, 160, 200, 50);
        this.add(pinLabelError);

        pinField.setBounds(120, 155, 130,20);
        pinField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c < '0') || (c > '9') && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                } else if (pinField.getDocument().getLength() >= 4)
                    e.consume();
            }
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkValid();
            }
        });
        this.add(pinField);

        //Date label and comboboxes

        dateLabel.setText("<html>Date of Birth: DD/MM/YYYY</html>");
        dateLabel.setBounds(30, 175, 100, 100);
        this.add(dateLabel);

        dayField.setBounds(120, 215, 40, 20);
        this.add(dayField);

        monthField.setBounds(170, 215, 40, 20);
        this.add(monthField);

        yearField.setBounds(220, 215, 60, 20);
        this.add(yearField);

        //Name label and field

        nameLabel.setText("Name:");
        nameLabel.setBounds(400, 40, 100, 50);
        this.add(nameLabel);

        nameField.setBounds(490, 55, 130, 20);
        nameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkValid();
            }
        });
        this.add(nameField);

        //Surname label and field

        surnameLabel.setText("Surname:");
        surnameLabel.setBounds(400, 90, 100, 50);
        this.add(surnameLabel);

        surnameField.setBounds(490, 105, 130 ,20);
        surnameField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkValid();
            }
        });
        this.add(surnameField);

        //Email label and field

        emailLabel.setText("Email:");
        emailLabel.setBounds(400, 140, 100, 50);
        this.add(emailLabel);

        emailField.setBounds(490, 155, 130,20);
        emailField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkValid();
            }
        });
        this.add(emailField);

        //Phone label and field

        phoneLabel.setText("Phone:");
        phoneLabel.setBounds(400, 190, 100, 50);
        this.add(phoneLabel);

        phoneField.setBounds(490, 205, 130, 20);
        phoneField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c < '0') || (c > '9') && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                } else if (phoneField.getDocument().getLength() >= 9)
                    e.consume();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                checkValid();
            }
        });
        this.add(phoneField);

        //Register Button

        registerButton.setText("Register");
        registerButton.setEnabled(false);
        registerButton.setBounds(140, 310, 100, 20);
        registerButton.addActionListener(this);
        this.add(registerButton);

        //Return Button

        returnButton.setText("Return");
        returnButton.setBounds(440, 310, 100, 20);
        returnButton.addActionListener(this);
        this.add(returnButton);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==registerButton){
            NumberGenerator numberGenerator = new NumberGenerator();
            LocalDate localDate = LocalDate.of(Integer.parseInt(yearField.getSelectedItem().toString()),
                    Integer.parseInt(monthField.getSelectedItem().toString()),
                    Integer.parseInt(dayField.getSelectedItem().toString()));
            int accountID;
            int accountNumber = 0;
            int cardNumber = 0;
            while(accountCRUD.getAccountByNumber(accountNumber) || accountNumber == 0)
                accountNumber = numberGenerator.GenereteNumber(100000000, 999999999);
            while(cardCRUD.getCardByNumber(cardNumber) || cardNumber == 0)
                cardNumber = numberGenerator.GenereteNumber(100000, 999999);
            account = new Account(accountNumber, loginField.getText(), passwordField.getText());
            card = new Card(cardNumber, Integer.parseInt(pinField.getText()));
            user = new User(Integer.parseInt(phoneField.getText()), nameField.getText(), surnameField.getText(), emailField.getText(), localDate);
            if(account != null && card != null && user != null){
                accountID = accountCRUD.saveAccount(account);
                cardCRUD.saveCard(card, accountID);
                userCRUD.saveUser(user, accountID);
            }
            JOptionPane.showMessageDialog(this, "Account created");
            this.dispose();
            LoginFrame loginFrame = new LoginFrame();
        }
        else if(e.getSource()==returnButton){
            LoginFrame loginFrame = new LoginFrame();
            this.dispose();
        }
    }

    private void checkValid(){
        if(pinField.getDocument().getLength() < 4 ||
                passwordField.getDocument().getLength() < 5 ||
                accountCRUD.checkAccountByLogin(loginField.getText()) ||
                userCRUD.checkUserByEmail(emailField.getText()) ||
                userCRUD.checkUserByNumber(Integer.parseInt(phoneField.getText())) ||
                loginField.getDocument().getLength() < 5 ||
                nameField.getDocument().getLength() < 3 ||
                surnameField.getDocument().getLength() < 3 ||
                phoneField.getDocument().getLength() != 9 ||
                emailField.getDocument().getLength() < 5
        )
            registerButton.setEnabled(false);
        else
            registerButton.setEnabled(true);
    }

}
