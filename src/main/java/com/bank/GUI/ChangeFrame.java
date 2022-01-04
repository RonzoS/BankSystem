package com.bank.GUI;

import com.bank.domain.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;


public class ChangeFrame extends JFrame implements ActionListener {

    Account account = new Account();
    Card card = new Card();
    AccountCRUD accountCRUD = new AccountCRUD();
    CardCRUD cardCRUD = new CardCRUD();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    JLabel tittleLabel = new JLabel();
    JLabel oldPasswordLabel = new JLabel();
    JLabel newPLabel = new JLabel();

    JPasswordField oldPasswordField = new JPasswordField();
    JPasswordField newPField = new JPasswordField();

    JButton changePasswordButton = new JButton();
    JButton changePinButton = new JButton();
    JButton backButton = new JButton();


    public ChangeFrame(Account account, Integer i) {

        this.account = account;
        card = cardCRUD.getAccountCard(account.getId());

        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        if (i == 0)
            this.setTitle("Change Password");
        else if (i == 1)
            this.setTitle("Change PIN");
        this.setIconImage(image.getImage());
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setSize(300, 320);
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);
        this.setVisible(true);

        if (i == 0) {
            tittleLabel.setText("Change your password");
            tittleLabel.setBounds(75,20,150,20);
            newPLabel.setText("New Password");
            newPLabel.setBounds(100, 130 , 100, 20);
            changePasswordButton.setText("Change");
            changePasswordButton.setBounds(100, 210, 80, 20);
            changePasswordButton.addActionListener(this);
            this.add(changePasswordButton);
        }
        else if (i == 1) {
            tittleLabel.setText("Change your PIN");
            tittleLabel.setBounds(95,20,150,20);
            newPLabel.setText("New PIN");
            newPLabel.setBounds(115, 130 , 100, 20);
            changePinButton.setText("Change");
            changePinButton.setBounds(100, 210, 80, 20);
            changePinButton.addActionListener(this);
            this.add(changePinButton);
        }

        this.add(tittleLabel);
        this.add(newPLabel);

        oldPasswordLabel.setText("Password");
        oldPasswordLabel.setBounds(110, 70 ,100, 20);
        this.add(oldPasswordLabel);

        oldPasswordField.setBounds(65, 100, 150, 20);
        this.add(oldPasswordField);

        newPField.setBounds(65, 160, 150, 20);
        if(i==1){
            newPField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                    char c = e.getKeyChar();
                    if ((c < '0') || (c > '9') && (c != KeyEvent.VK_BACK_SPACE)) {
                        e.consume();
                    }
                    else if(newPField.getDocument().getLength() >= 4)
                        e.consume();
                }
            });
        }
        this.add(newPField);

        backButton.setText("Back");
        backButton.setBounds(100, 240, 80, 20);
        backButton.addActionListener(this);
        this.add(backButton);


    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==changePasswordButton){
            if(!oldPasswordField.getText().equals(account.getPassword()))
                JOptionPane.showMessageDialog(this, "Wrong Password");
            else if(newPField.getDocument().getLength() <= 3)
                JOptionPane.showMessageDialog(this, "Password needs to be longer then 3");
            else if(oldPasswordField.getText().equals(account.getPassword())){
                accountCRUD.updateAccountPassword(account.getId(), newPField.getText());
                account = accountCRUD.getAccount(account.getId());
                BankFrame bankFrame = new BankFrame(account);
                this.dispose();
            }
        }
        else if(e.getSource()==changePinButton){
            if(!oldPasswordField.getText().equals(account.getPassword()))
                JOptionPane.showMessageDialog(this, "Wrong Password");
            else if(newPField.getDocument().getLength() != 4)
                JOptionPane.showMessageDialog(this, "PIN needs to 4 digits");
            else if(oldPasswordField.getText().equals(account.getPassword())){
                cardCRUD.updateCardPin(card.getId(), Integer.parseInt(newPField.getText()));
                account = accountCRUD.getAccount(account.getId());
                BankFrame bankFrame = new BankFrame(account);
                this.dispose();
            }
        }
        else if(e.getSource()==backButton){
            BankFrame bankFrame = new BankFrame(account);
            this.dispose();
        }
    }
}