package com.bank;

import com.bank.GUI.BankFrame;
import com.bank.GUI.LoginFrame;
import com.bank.domain.*;
import com.bank.service.ExportToPDF;
import com.bank.service.NumberGenerator;
import com.bank.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        /*

        BigDecimal PLN;
        BigDecimal minus = new BigDecimal("0.5555");
        PLN = new BigDecimal("10.00");
        PLN = PLN.subtract(minus);
        PLN = PLN.setScale(2, RoundingMode.HALF_UP);
        System.out.print(PLN+"\n");

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        Account a1 = new Account(1, new BigDecimal ("1000.55"), "test", "test");
        Transferlog t1 = new Transferlog("Przelew do kolegi", 1200, 1500, 300, 2);
        Transferlog t2 = new Transferlog("Przelew od kolegi", 1500, 300, 1800, 2);
        List<Transferlog> transferlogs = new ArrayList<>();
        transferlogs.add(t1);
        transferlogs.add(t2);
        a1.setTransferlogs(transferlogs);
        session.save(a1);

        User u1 = new User(555666777, "Jan", "Kowalski", "JanKowalaski@gmail.com", LocalDate.parse("1997-08-16"));
        u1.setAccount_id(a1);
        session.save(u1);

        Card c1 = new Card(1134252, 9331);
        c1.setAccount_id(a1);
        session.save(c1);

        transaction.commit();

        session.close();
        //sessionFactory.close();

        LocalDateTime d1 = LocalDateTime.now();
        d1 = d1.truncatedTo(ChronoUnit.SECONDS);
        System.out.println(d1);


         */

        //Account a1 = new Account(1, new BigDecimal ("1000.55"), "test", "test");
        //User u1 = new User(555666777, "Jan", "Kowalski", "JanKowalaski@gmail.com", LocalDate.parse("1997-08-16"));
        //Card c1 = new Card(1134252, 9331);
        //Card c2 = new Card(11342525, 9431);
        //Transferlog t1 = new Transferlog("Przelew do kolegi", 1505450, 1800, 11100, 0);
        //Transferlog t2 = new Transferlog("Przelew do kolegi", 1505450, 1800, 11100, 7);
        //Transferlog t3 = new Transferlog("Przelew do kolegi", 1505450, 1800, 11100, 7);
        Account account = new Account();
        AccountCRUD accountCRUD = new AccountCRUD();
        account = accountCRUD.getAccount(6);
        //ExportToPDF exportToPDF = new ExportToPDF();
        //exportToPDF.generatePDF(account, t1);
        //account = accountCRUD.getAccountByLoginAndPassword("test", "test");
        //System.out.println(account);
        //CardCRUD cardCRUD = new CardCRUD();
        //UserCRUD userCRUD = new UserCRUD();
        //TransferlogCRUD transferlogCRUD = new TransferlogCRUD();
        //transferlogCRUD.saveTransferlog(t1, 1);
        //transferlogCRUD.saveTransferlog(t2, 1);
        //transferlogCRUD.saveTransferlog(t3, 1);
        //accountCRUD.saveAccount(a1);
        //userCRUD.saveUser(u1, 1);
        //cardCRUD.saveCard(c1, 1);
        //cardCRUD.saveCard(c2, 1);

        //TransferlogCRUD transferlogCRUD = new TransferlogCRUD();
        //Account account = accountCRUD.getAccount(1);
        //System.out.println(account.getAccountNumber());
        //Account a1 = new Account(546545463, new BigDecimal ("1000.55"), "test111", "test111");
        //accountCRUD.saveAccount(a1);
        //List<Account> results = accountCRUD.getAccounts();
        //System.out.println("\nresults: " + results.size());
        //for(int i = 0; i < results.size(); i++) {
        //System.out.println("Account: " + 0 + ": " + results.get(0));

        //List<Transferlog> resultsT = transferlogCRUD.getAccountTransferlog(1);
        //account.setTransferlogs(resultsT);
        //System.out.println(results.get(0).getId());
        //cardCRUD.deleteCard(1);
        //userCRUD.deleteUser(1);
        //accountCRUD.deleteAccount(1);

        //accountCRUD.deleteAccount(7);
        //transferlogCRUD.deleteTransferlog(1);
        //LoginFrame loginFrame = new LoginFrame();
        BankFrame bankFrame = new BankFrame(account);
    }
}