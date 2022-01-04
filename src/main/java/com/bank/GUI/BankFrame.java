package com.bank.GUI;

import com.bank.domain.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankFrame extends JFrame implements ActionListener {

    Boolean showPin = false;

    User user = new User();
    Account account= new Account();
    Transferlog transferlog = new Transferlog();
    Card card = new Card();
    UserCRUD userCRUD = new UserCRUD();
    AccountCRUD accountCRUD = new AccountCRUD();
    TransferlogCRUD transferlogCRUD = new TransferlogCRUD();
    CardCRUD cardCRUD = new CardCRUD();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    List<Transferlog> transferlogs;

    JLabel tittleLabel = new JLabel();
    JLabel accountInfoLabel = new JLabel();
    JLabel accountNumberLabel = new JLabel();
    JLabel displayAccountNumberLabel = new JLabel();
    JLabel cardNumberLabel = new JLabel();
    JLabel displayCardNumberLabel = new JLabel();
    JLabel pinLabel = new JLabel();
    JLabel displayPinLabel = new JLabel();
    JLabel expirationDateLabel = new JLabel();
    JLabel displayExpirationDateLabel= new JLabel();
    JLabel balanceLabel = new JLabel();
    JLabel showBalanceLabel = new JLabel();
    JLabel amountLabel = new JLabel();
    JLabel otherAccountLabel = new JLabel();

    JTextField amountField = new JTextField();
    JTextField otherAccountField = new JTextField();

    JTable transferlogTable = new JTable();

    JButton logoutButton = new JButton();
    JButton showPinButton = new JButton();
    JButton transferButton = new JButton();
    JButton changePasswordButton = new JButton();
    JButton changePinButton = new JButton();

    public BankFrame(Account account){

        this.account = account;
        transferlogs = transferlogCRUD.getAccountTransferlog(account.getId());
        user = userCRUD.getAccountUser(account.getId());
        card = cardCRUD.getAccountCard(account.getId());

        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        this.setTitle("Bank");
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(null);
        this.setSize(800, 600);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);

        DefaultTableModel transferlogModel = new DefaultTableModel(){

            //Making all cells not editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

            @Override
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return BigDecimal.class;
                    case 3:
                        return BigDecimal.class;
                    case 4:
                        return BigDecimal.class;
                    case 5:
                        return String.class;
                    default:
                        return String.class;
                }
            }

        };
        transferlogModel.setColumnIdentifiers(createRows());

        for(int i = 0; i < transferlogs.size(); i++){
            transferlog = transferlogs.get(i);
            transferlogModel.addRow(addTransferlogRow(transferlog));
        }

        tittleLabel.setText("Welcome "+user.getName()+" to your bank account");
        tittleLabel.setBounds(215, 20, 450, 30);
        tittleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(tittleLabel);

        accountInfoLabel.setText("Account Information");
        accountInfoLabel.setBounds(20, 80, 160, 20);
        accountInfoLabel.setFont(new Font("Arial", Font.BOLD, 16));
        this.add(accountInfoLabel);

        accountNumberLabel.setText("Account Number:");
        accountNumberLabel.setBounds(20,110, 100,20);
        this.add(accountNumberLabel);

        displayAccountNumberLabel.setText(String.valueOf(account.getAccountNumber()));
        displayAccountNumberLabel.setBounds(140,110, 100,20);
        this.add(displayAccountNumberLabel);

        cardNumberLabel.setText("Card Number:");
        cardNumberLabel.setBounds(20,140, 100,20);
        this.add(cardNumberLabel);

        displayCardNumberLabel.setText(String.valueOf(card.getCardNumber()));
        displayCardNumberLabel.setBounds(140,140, 100,20);
        this.add(displayCardNumberLabel);

        pinLabel.setText("Card PIN:");
        pinLabel.setBounds(20,170, 100,20);
        this.add(pinLabel);

        displayPinLabel.setText("****");
        displayPinLabel.setBounds(140,170, 100,20);
        this.add(displayPinLabel);

        expirationDateLabel.setText("Card Expiration:");
        expirationDateLabel.setBounds(20,200, 100,20);
        this.add(expirationDateLabel);

        displayExpirationDateLabel.setText(card.getExpirationDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        displayExpirationDateLabel.setBounds(140,200, 100,20);
        this.add(displayExpirationDateLabel);

        balanceLabel.setText("Balance:");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        balanceLabel.setBounds(330,70, 100,20);
        this.add(balanceLabel);

        showBalanceLabel.setText(String.valueOf(account.getBalance()));
        showBalanceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        showBalanceLabel.setBounds(400,70, 100,20);
        this.add(showBalanceLabel);

        amountLabel.setText("Amount");
        amountLabel.setBounds(365, 105, 80, 20);
        this.add(amountLabel);

        otherAccountLabel.setText("Account");
        otherAccountLabel.setBounds(365, 155, 80, 20);
        this.add(otherAccountLabel);

        amountField.setBounds(320, 130, 140, 20);
        this.add(amountField);

        otherAccountField.setBounds(320, 180, 140, 20);
        //Making frame only digits
        otherAccountField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if ((c < '0') || (c > '9') && (c != KeyEvent.VK_BACK_SPACE)) {
                    e.consume();
                }
                else if(otherAccountField.getDocument().getLength() >= 9)
                    e.consume();
            }
        });
        this.add(otherAccountField);

        showPinButton.setText("\uD83D\uDC41️");
        showPinButton.addActionListener(this);
        showPinButton.setMargin( new Insets(0, 0, 0, 0) );
        showPinButton.setBounds(220, 170, 20, 20);
        this.add(showPinButton);

        transferButton.setText("Make a transfer");
        transferButton.addActionListener(this);
        transferButton.setMargin( new Insets(5, 5, 5, 5) );
        transferButton.setBounds(330, 220, 120, 20);
        this.add(transferButton);

        changePasswordButton.setText("Change Password");
        changePasswordButton.addActionListener(this);
        changePasswordButton.setBounds(580, 90, 140, 20);
        this.add(changePasswordButton);

        changePinButton.setText("Change PIN");
        changePinButton.addActionListener(this);
        changePinButton.setBounds(580, 130, 140, 20);
        this.add(changePinButton);

        logoutButton.setText("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setBounds(10,10,100, 20);
        this.add(logoutButton);

        transferlogTable.setModel(transferlogModel);
        transferlogTable.setAutoCreateRowSorter(true);
        transferlogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane pane = new JScrollPane(transferlogTable);
        pane.setBounds(30,300,720,240);
        this.add(pane);





    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==logoutButton){
            LoginFrame loginFrame = new LoginFrame();
            this.dispose();
        }
        else if(e.getSource()==showPinButton){
            if(!showPin) {
                displayPinLabel.setText(String.valueOf(card.getPin()));
                showPin=true;
            }
            else {
                displayPinLabel.setText("****");
                showPin=false;
            }
        }
        else if(e.getSource()==changePasswordButton) {
            ChangeFrame changeFrame = new ChangeFrame(account, 0);
            this.dispose();
        }
        else if(e.getSource()==changePinButton) {
            ChangeFrame changeFrame = new ChangeFrame(account, 1);
            this.dispose();
        }


    }

    private Object[] createRows(){
        Object[] columnsName = new Object[6];
        columnsName[0] = "Date";
        columnsName[1] = "Description";
        columnsName[2] = "Amount";
        columnsName[3] = "Amount Before";
        columnsName[4] = "Amount After";
        columnsName[5] = "Account Number";
        return  columnsName;
    }

    private Object[] addTransferlogRow(Transferlog transferlog){
        Object[] rowData = new Object[6];
        rowData[0] = transferlog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        rowData[1] = transferlog.getDescription();
        rowData[2] = transferlog.getValue();
        rowData[3] = transferlog.getValueBefore();
        rowData[4] = transferlog.getValueAfter();
        if(transferlog.getOtherAccount() == 0)
            rowData[5] = "ATM";
        else
            rowData[5] = String.valueOf(transferlog.getOtherAccount());
        return rowData;
    }




}