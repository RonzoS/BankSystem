package com.bank.GUI;

import com.bank.domain.*;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

public class ATMFrame extends JFrame implements ActionListener {

    User user = new User();
    Account account= new Account();
    Transferlog transferlog = new Transferlog();
    UserCRUD userCRUD = new UserCRUD();
    AccountCRUD accountCRUD = new AccountCRUD();
    TransferlogCRUD transferlogCRUD = new TransferlogCRUD();

    int amount;
    BigDecimal amountBefore;
    BigDecimal amountAfter;
    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();


    JLabel atmLabel = new JLabel();
    JLabel welcomeLabel = new JLabel();
    JLabel balanceLabel = new JLabel();
    JLabel amountLabel = new JLabel();
    JTextField amountField = new JTextField();
    JButton depositButton = new JButton();
    JButton withdrawButton = new JButton();
    JButton logoutButton = new JButton();

    public ATMFrame(Account account){

        this.account = account;

        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        this.setTitle("ATM");
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setResizable(false);
        this.setSize(420, 440);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);

        user = userCRUD.getAccountUser(account.getId());

        atmLabel.setText("ATM");
        atmLabel.setBounds(180, 15 , 200, 50);
        atmLabel.setFont(new Font("Serif", Font.PLAIN, 20));
        this.add(atmLabel);

        welcomeLabel.setText("Welcome " + user.getName());
        welcomeLabel.setBounds(150, 70, 200, 50);
        this.add(welcomeLabel);

        balanceLabel.setText("Balance: " + account.getBalance());
        balanceLabel.setBounds(150, 100, 200, 50);
        this.add(balanceLabel);

        amountLabel.setText("Amount:");
        amountLabel.setBounds(120, 155, 100, 50);
        this.add(amountLabel);

        amountField.setBounds(180, 170, 100, 20);
        this.add(amountField);

        depositButton.setText("Deposit");
        depositButton.setBounds(150, 230, 100, 20);
        depositButton.addActionListener(this);
        this.add(depositButton);

        withdrawButton.setText("Withdraw");
        withdrawButton.setBounds(150, 270, 100, 20);
        withdrawButton.addActionListener(this);
        this.add(withdrawButton);

        logoutButton.setText("Logout");
        logoutButton.setBounds(150, 340, 100, 20);
        logoutButton.addActionListener(this);
        this.add(logoutButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==depositButton){
            amount = intCheck(amountField.getText());
            if(amount>0){
                amountBefore = account.getBalance();
                amountAfter = amountBefore.add(BigDecimal.valueOf(amount));
                accountCRUD.updateAccountBalance(account.getId(), amountAfter);
                account = accountCRUD.getAccount(account.getId());
                transferlog = new Transferlog("ATM deposit", Double.parseDouble(amountField.getText()), amountBefore.doubleValue(),
                        amountAfter.doubleValue(), 0);
                transferlogCRUD.saveTransferlog(transferlog, account.getId());
                balanceLabel.setText("Balance: " + account.getBalance());
                amountField.setText("");
                JOptionPane.showMessageDialog(this, "Successfully deposited");
            }
            else if(amount!=0){
                JOptionPane.showMessageDialog(this, "Amount can be only positive integer");
                amountField.setText("");
            }
        }
        else if(e.getSource()==withdrawButton){
            amount = intCheck(amountField.getText());
            if(amount > account.getBalance().doubleValue()){
                JOptionPane.showMessageDialog(this, "You don't have enough money");
                amountField.setText("");
            }
            else if(amount>0){
                amountBefore = account.getBalance();
                amountAfter = amountBefore.subtract(BigDecimal.valueOf(amount));
                accountCRUD.updateAccountBalance(account.getId(), amountAfter);
                account = accountCRUD.getAccount(account.getId());
                transferlog = new Transferlog("ATM withdraw", Double.parseDouble(amountField.getText()), amountBefore.doubleValue(),
                        amountAfter.doubleValue(), 0);
                transferlogCRUD.saveTransferlog(transferlog, account.getId());
                balanceLabel.setText("Balance: " + account.getBalance());
                amountField.setText("");
                JOptionPane.showMessageDialog(this, "Successfully withdrawn");
            }
            else if(amount!=0){
                JOptionPane.showMessageDialog(this, "Amount can be only positive integer");
                amountField.setText("");
            }
        }
        else if(e.getSource()==logoutButton){
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
            JOptionPane.showMessageDialog(this, "Amount can be only positive integer");
            amountField.setText("");
        }
        return 0;
    }
}
