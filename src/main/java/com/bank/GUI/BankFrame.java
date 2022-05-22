package com.bank.GUI;

import com.bank.domain.*;
import com.bank.service.ExportToPDF;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BankFrame extends JFrame implements ActionListener {

    Boolean showPin = false;
    String[] dayStrings = new String[30];
    String[] monthString = new String[11];
    String[] yearString = new String[30];

    User user = new User();
    Account account= new Account();
    Transferlog transferlog = new Transferlog();
    Card card = new Card();
    UserCRUD userCRUD = new UserCRUD();
    AccountCRUD accountCRUD = new AccountCRUD();
    TransferlogCRUD transferlogCRUD = new TransferlogCRUD();
    CardCRUD cardCRUD = new CardCRUD();

    ExportToPDF exportToPDF = new ExportToPDF();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

    List<Transferlog> transferlogs;
    List<Transferlog> searchTransferlogs;

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
    JLabel descriptionLabel = new JLabel();
    JLabel searchValueLabel = new JLabel();
    JLabel fromLabel = new JLabel("From");
    JLabel toLabel = new JLabel("To");

    JTextField amountField = new JTextField();
    JTextField otherAccountField = new JTextField();
    JTextField descriptionField = new JTextField();
    JTextField searchField = new JTextField();

    JTable transferlogTable = new JTable();

    JButton logoutButton = new JButton();
    JButton showPinButton = new JButton();
    JButton transferButton = new JButton();
    JButton changePasswordButton = new JButton();
    JButton changePinButton = new JButton();
    JButton exportButton = new JButton();
    JButton resetRows = new JButton();
    JButton searchButton = new JButton();

    JComboBox startDayField = new JComboBox();
    JComboBox startMonthField = new JComboBox();
    JComboBox startYearField = new JComboBox();
    JComboBox endDayField = new JComboBox();
    JComboBox endMonthField = new JComboBox();
    JComboBox endYearField = new JComboBox();

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
                case 6:
                    return Integer.class;
                default:
                    return String.class;
            }
        }

    };

    public BankFrame(Account account){

        for(int i = 0; i<=dayStrings.length; i++) {
            startDayField.addItem(Integer.toString(i + 1));
            endDayField.addItem(Integer.toString(i + 1));
        }

        for(int i = 0; i<=monthString.length; i++) {
            startMonthField.addItem(Integer.toString(i + 1));
            endMonthField.addItem(Integer.toString(i + 1));
        }

        for(int i = 0; i<=yearString.length; i++) {
            startYearField.addItem(Integer.toString(i + 2010));
            endYearField.addItem(Integer.toString(i + 2010));
        }

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
        this.setSize(800, 650);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);

        transferlogModel.setColumnIdentifiers(createRows());

        fillAllRows();

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

        descriptionLabel.setText("Description");
        descriptionLabel.setBounds(357, 205, 80, 20);
        this.add(descriptionLabel);

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

        descriptionField.setBounds(320, 230, 140, 20);
        this.add(descriptionField);

        showPinButton.setText("\uD83D\uDC41️");
        showPinButton.addActionListener(this);
        showPinButton.setMargin( new Insets(0, 0, 0, 0) );
        showPinButton.setBounds(220, 170, 20, 20);
        this.add(showPinButton);

        transferButton.setText("Make a transfer");
        transferButton.addActionListener(this);
        transferButton.setMargin( new Insets(5, 5, 5, 5) );
        transferButton.setBounds(330, 265, 120, 20);
        this.add(transferButton);

        changePasswordButton.setText("Change Password");
        changePasswordButton.addActionListener(this);
        changePasswordButton.setBounds(580, 90, 140, 20);
        this.add(changePasswordButton);

        changePinButton.setText("Change PIN");
        changePinButton.addActionListener(this);
        changePinButton.setBounds(580, 130, 140, 20);
        this.add(changePinButton);

        exportButton.setText("Export to PDF");
        exportButton.addActionListener(this);
        exportButton.setBounds(580, 170, 140, 20);
        this.add(exportButton);

        logoutButton.setText("Logout");
        logoutButton.addActionListener(this);
        logoutButton.setBounds(580,210,140, 20);
        this.add(logoutButton);

        fromLabel.setBounds(15, 550, 40, 20);
        this.add(fromLabel);

        toLabel.setBounds(20, 580, 40, 20);
        this.add(toLabel);

        startDayField.setBounds(50, 550, 40, 20);
        this.add(startDayField);

        startMonthField.setBounds(90, 550, 40, 20);
        this.add(startMonthField);

        startYearField.setBounds(130, 550, 60, 20);
        this.add(startYearField);

        endDayField.setBounds(50, 580, 40, 20);
        this.add(endDayField);

        endMonthField.setBounds(90, 580, 40, 20);
        this.add(endMonthField);

        endYearField.setBounds(130, 580, 60, 20);
        endYearField.setSelectedIndex(30);
        this.add(endYearField);

        searchValueLabel.setText("Value");
        searchValueLabel.setBounds(210, 545, 140, 20);
        this.add(searchValueLabel);

        searchField.setBounds(210, 575, 140, 20);
        this.add(searchField);

        searchButton.setText("Search");
        searchButton.addActionListener(this);
        searchButton.setBounds(390, 575, 140, 20);
        this.add(searchButton);

        resetRows.setText("Reset Rows");
        resetRows.addActionListener(this);
        resetRows.setBounds(580, 575, 140, 20);
        this.add(resetRows);

        transferlogTable.setModel(transferlogModel);
        transferlogTable.setAutoCreateRowSorter(true);
        transferlogTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        transferlogTable.getColumnModel().getColumn(6).setMinWidth(0);
        transferlogTable.getColumnModel().getColumn(6).setMaxWidth(0);
        JScrollPane pane = new JScrollPane(transferlogTable);
        pane.setBounds(30,300,720,240);
        this.add(pane);

        this.setVisible(true);
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
        else if(e.getSource()==exportButton){
            if(transferlogTable.getSelectionModel().isSelectionEmpty())
                JOptionPane.showMessageDialog(this, "No row selected");
            else{
                int id = Integer.parseInt(transferlogTable.getValueAt(transferlogTable.getSelectedRow(), 6).toString());
                try {
                    exportToPDF.generatePDF(account, transferlogCRUD.getTransferlog(id));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "Transfer exported");
            }
        }
        else if (e.getSource()==transferButton){
            if(!accountCRUD.getAccountByNumber(Integer.parseInt(otherAccountField.getText()))){
                otherAccountField.setText("");
                JOptionPane.showMessageDialog(this, "Wrong account number");
            }
            else if (new BigDecimal(amountField.getText()).compareTo(account.getBalance()) > 0){
                amountField.setText("");
                JOptionPane.showMessageDialog(this, "Not enough money");
            }
            else {
                account = makeTransaction(account, Integer.parseInt(otherAccountField.getText()),new BigDecimal(amountField.getText()), descriptionField.getText());
                showBalanceLabel.setText(String.valueOf(account.getBalance()));
                amountField.setText("");
                otherAccountField.setText("");
                descriptionField.setText("");
                JOptionPane.showMessageDialog(this, "Transfer completed");
            }
        }
        else if(e.getSource()==resetRows){
            removeAllRows(transferlogTable);
            fillAllRows();
        }
        else if(e.getSource()==searchButton){
            removeAllRows(transferlogTable);
            LocalDate startLocalDate = LocalDate.of(Integer.parseInt(startYearField.getSelectedItem().toString()),
                    Integer.parseInt(startMonthField.getSelectedItem().toString()),
                    Integer.parseInt(startDayField.getSelectedItem().toString()));
            LocalDate endLocalDate = LocalDate.of(Integer.parseInt(endYearField.getSelectedItem().toString()),
                    Integer.parseInt(endMonthField.getSelectedItem().toString()),
                    Integer.parseInt(endDayField.getSelectedItem().toString()));
            String searchValue;
            if(searchField.getText().equals(""))
                searchValue = "*";
            else
                searchValue = searchField.getText();
            fillAllSearchRows(startLocalDate.toString(), endLocalDate.toString(), searchValue);
            searchField.setText("");
        }


    }

    private Object[] createRows(){
        Object[] columnsName = new Object[7];
        columnsName[0] = "Date";
        columnsName[1] = "Description";
        columnsName[2] = "Amount";
        columnsName[3] = "Amount Before";
        columnsName[4] = "Amount After";
        columnsName[5] = "Account Number";
        columnsName[6] = "ID";
        return  columnsName;
    }

    private Object[] addTransferlogRow(Transferlog transferlog){
        Object[] rowData = new Object[7];
        rowData[0] = transferlog.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        rowData[1] = transferlog.getDescription();
        rowData[2] = transferlog.getValue();
        rowData[3] = transferlog.getValueBefore();
        rowData[4] = transferlog.getValueAfter();
        if(transferlog.getOtherAccount() == 0)
            rowData[5] = "ATM";
        else
            rowData[5] = String.valueOf(transferlog.getOtherAccount());
        rowData[6] = transferlog.getId();
        return rowData;
    }

    private Account makeTransaction(Account account, int otherAccountNumber, BigDecimal amount, String description){
        Account account1 = accountCRUD.getAccountByNumberCorrect(otherAccountNumber);
        BigDecimal amountBefore = account.getBalance();
        BigDecimal amountAfter = amountBefore.subtract(amount.setScale(2, RoundingMode.HALF_UP));
        accountCRUD.updateAccountBalance(account.getId(), amountAfter);
        account = accountCRUD.getAccount(account.getId());
        transferlog = new Transferlog(description, amount.setScale(2, RoundingMode.HALF_UP).doubleValue(), amountBefore.doubleValue(),
                amountAfter.doubleValue(), otherAccountNumber);
        transferlogCRUD.saveTransferlog(transferlog, account.getId());
        transferlogModel.insertRow(0, addTransferlogRow(transferlog));
        transferlogTable.setModel(transferlogModel);
        amountBefore = account1.getBalance();
        amountAfter = amountBefore.add(amount.setScale(2, RoundingMode.HALF_UP));
        accountCRUD.updateAccountBalance(account1.getId(), amountAfter);
        account1 = accountCRUD.getAccount(account1.getId());
        transferlog = new Transferlog(description, amount.setScale(2, RoundingMode.HALF_UP).doubleValue(), amountBefore.doubleValue(),
                amountAfter.doubleValue(), account.getAccountNumber());
        transferlogCRUD.saveTransferlog(transferlog, account1.getId());
        return account;
    }

    private void removeAllRows(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int tableSize = table.getRowCount();
        for (int i = tableSize - 1; i >= 0; i--) {
            model.removeRow(i);
        }
    }

    private void fillAllRows(){
        transferlogs = transferlogCRUD.getAccountTransferlog(account.getId());
        for(int i = transferlogs.size() - 1; i >=0 ; i--){
            transferlog = transferlogs.get(i);
            transferlogModel.addRow(addTransferlogRow(transferlog));
        }
    }

    private void fillAllSearchRows(String start, String end, String value){
        searchTransferlogs = transferlogCRUD.searchTransferlogs(account.getId(), start, end, value);
        for(int i = searchTransferlogs.size() - 1; i >=0 ; i--){
            transferlog = searchTransferlogs.get(i);
            transferlogModel.addRow(addTransferlogRow(transferlog));
        }
    }



}