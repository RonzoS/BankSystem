package com.bank.GUI;

import com.bank.domain.Account;
import com.bank.domain.AccountCRUD;
import com.bank.util.HibernateUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {

    HibernateUtil hibernateUtil = new HibernateUtil();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    JLabel loginLabel = new JLabel();
    JLabel passwordLabel = new JLabel();
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton();
    JButton registerButton = new JButton();
    JButton atmButton = new JButton();
    JButton exitButton = new JButton();

    public LoginFrame(){
        hibernateUtil.getSessionFactory();
        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        this.setTitle("Bank");
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(420, 420);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);


        loginLabel.setText("Login:");
        loginLabel.setBounds(60, 30, 100, 50);
        this.add(loginLabel);

        passwordLabel.setText("Password:");
        passwordLabel.setBounds(60, 80, 100, 50);
        this.add(passwordLabel);

        loginField.setBounds(140, 45, 180, 20);
        this.add(loginField);

        passwordField.setBounds(140, 95, 180,20);
        this.add(passwordField);

        loginButton.setText("Login!");
        loginButton.setBounds(150, 160, 100, 20);
        loginButton.addActionListener(this);
        this.add(loginButton);

        registerButton.setText("Register");
        registerButton.setBounds(150, 200, 100, 20);
        registerButton.addActionListener(this);
        this.add(registerButton);

        atmButton.setText("ATM");
        atmButton.setBounds(150, 260, 100, 20);
        atmButton.addActionListener(this);
        this.add(atmButton);

        exitButton.setText("Exit");
        exitButton.setBounds(150, 320, 100, 20);
        exitButton.addActionListener(this);
        this.add(exitButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==loginButton){
            String login = loginField.getText();
            String password = passwordField.getText();
            Account account = new Account();
            AccountCRUD accountCRUD = new AccountCRUD();
            account = accountCRUD.getAccountByLoginAndPassword(login, password);
            if(account == null){
                loginField.setText("");
                passwordField.setText("");
                JOptionPane.showMessageDialog(this, "Wrong Login or Password");
            }
            else {
                JOptionPane.showMessageDialog(this, "Login Successful");
                BankFrame bankFrame = new BankFrame(account);
                this.dispose();
            }
        }
        else if(e.getSource()==registerButton){
            RegisterFrame registerFrame = new RegisterFrame();
            this.dispose();
        }
        else if(e.getSource()==atmButton){
            ATMLoginFrame atmLoginFrame = new ATMLoginFrame();
            this.dispose();
        }
        else if(e.getSource()==exitButton)
            System.exit(0);
    }

}
