package com.bank.GUI;

import com.bank.domain.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BankFrame extends JFrame implements ActionListener {

    User user = new User();
    Account account= new Account();
    Transferlog transferlog = new Transferlog();
    UserCRUD userCRUD = new UserCRUD();
    AccountCRUD accountCRUD = new AccountCRUD();
    TransferlogCRUD transferlogCRUD = new TransferlogCRUD();

    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();


    List<Transferlog> transferlogs = new ArrayList<>();

    JTable transferlogTable = new JTable();

    public BankFrame(Account account){

        this.account = account;

        ImageIcon image = new ImageIcon("src/main/java/com/bank/Icons/bank.png");
        this.setTitle("Bank");
        this.setIconImage(image.getImage());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //this.setResizable(false);
        this.setSize(600, 340);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
        this.setVisible(true);

        transferlogs = transferlogCRUD.getAccountTransferlog(account.getId());

        DefaultTableModel transferlogModel = new DefaultTableModel();
        Object[] columnsName = new Object[6];
        columnsName[0] = "Date";
        columnsName[1] = "Description";
        columnsName[2] = "Amount";
        columnsName[3] = "Amount Before";
        columnsName[4] = "Amount After";
        columnsName[5] = "Account Number";

        transferlogModel.setColumnIdentifiers(columnsName);

        Object[] rowData = new Object[6];

        for(int i = 0; i < transferlogs.size(); i++){
            transferlog = transferlogs.get(i);
            rowData[0] = transferlog.getDate();
            rowData[1] = transferlog.getDescription();
            rowData[2] = transferlog.getValue();
            rowData[3] = transferlog.getValueBefore();
            rowData[4] = transferlog.getValueAfter();
            if(transferlog.getOtherAccount() == 0)
                rowData[5] = "ATM";
            else
                rowData[5] = transferlog.getOtherAccount();
            transferlogModel.addRow(rowData);
        }

        transferlogTable.setModel(transferlogModel);
        transferlogTable.getColumnModel().getColumn(1).setPreferredWidth(200);
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        JScrollPane pane = new JScrollPane(transferlogTable);
        panel.add(pane, BorderLayout.CENTER);
        this.setContentPane(panel);




    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
