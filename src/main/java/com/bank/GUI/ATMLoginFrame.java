package com.bank.GUI;

import com.bank.domain.Account;
import com.bank.domain.AccountCRUD;
import com.bank.domain.Card;
import com.bank.domain.CardCRUD;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ATMLoginFrame extends JFrame implements ActionListener {

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    JLabel cardNumberLabel = new JLabel();
    JLabel pinLabel = new JLabel();
    JTextField cardNumberField = new JTextField();
    JPasswordField pinField = new JPasswordField();
    JButton loginButton = new JButton();
    JButton returnButton = new JButton();

    public ATMLoginFrame() {
        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        this.setTitle("ATM");
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(420, 330);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);

        cardNumberLabel.setText("Card Number:");
        cardNumberLabel.setBounds(50, 30, 100, 50);
        this.add(cardNumberLabel);

        pinLabel.setText("Pin:");
        pinLabel.setBounds(50, 80, 100, 50);
        this.add(pinLabel);

        cardNumberField.setBounds(140, 45, 180, 20);
        this.add(cardNumberField);

        pinField.setBounds(140, 95, 180, 20);
        this.add(pinField);

        loginButton.setText("Login");
        loginButton.setBounds(150, 160, 100, 20);
        loginButton.addActionListener(this);
        this.add(loginButton);

        returnButton.setText("Return");
        returnButton.setBounds(150, 210, 100, 20);
        returnButton.addActionListener(this);
        this.add(returnButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            int cardNumber = intCheck(cardNumberField.getText());
            int pin = intCheck(pinField.getText());
            Card card = new Card();
            CardCRUD cardCRUD = new CardCRUD();
            Account account = new Account();
            AccountCRUD accountCRUD = new AccountCRUD();
            card = cardCRUD.getCardByLoginAndPin(cardNumber, pin);
            if(cardNumber == 0 || pin == 0)
                JOptionPane.showMessageDialog(this, "Card number and PIN should be integer");
            else if(card == null){
                cardNumberField.setText("");
                pinField.setText("");
                JOptionPane.showMessageDialog(this, "Wrong Card Number or Pin");
            }
            else {
                account = card.getAccount_id();
                ATMFrame atmFrame = new ATMFrame(account);
                this.dispose();
            }
        }
        else if (e.getSource() == returnButton) {
            LoginFrame loginFrame = new LoginFrame();
            this.dispose();

        }
    }

    private int intCheck(String stringValue){
        int value;
        try{
            value = Integer.parseInt(stringValue);
            return value;
        }
        catch (NumberFormatException e){
            cardNumberField.setText("");
            pinField.setText("");
        }
        return 0;
    }

}
