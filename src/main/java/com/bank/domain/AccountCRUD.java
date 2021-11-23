package com.bank.domain;

import com.bank.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.math.BigDecimal;
import java.util.List;


public class AccountCRUD {

    public Integer saveAccount (Account account) {
        Transaction transaction = null;
        Session session = null;
        Integer id = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            id = (Integer) session.save(account);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }
        return id;
    }

    public Account getAccount(int id){
        Transaction transaction = null;
        Session session = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            account = session.get(Account.class, id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return account;
    }

    public List getAccounts(){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Account";
            Query query = session.createQuery(hql);
            results = query.list();
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return results;
    }

    public Account getAccountByLoginAndPassword(String login, String password){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Account WHERE login = '" + login + "' and password = '" + password +"'";
            Query query = session.createQuery(hql);
            results = query.list();
            if (results.size() > 0) account = (Account) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return account;
    }

    public boolean getAccountByLogin(String login){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Account WHERE login = '" + login + "'";
            Query query = session.createQuery(hql);
            results = query.list();
            if (results.size() > 0) account = (Account) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(account == null)
            return false;
        else
            return true;
    }

    public boolean getAccountByNumber(int accountNumber){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Account WHERE accountNumber = " + accountNumber;
            Query query = session.createQuery(hql);
            results = query.list();
            if (results.size() > 0) account = (Account) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(account == null)
            return false;
        else
            return true;
    }

    public void updateAccount(int id, int accountNumber, BigDecimal balance, String login, String password){
        Transaction transaction = null;
        Session session = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            account = session.get(Account.class, id);
            account.setAccountNumber(accountNumber);
            account.setBalance(balance);
            account.setLogin(login);
            account.setPassword(password);
            session.update(account);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void updateAccountBalance(int id, BigDecimal balance){
        Transaction transaction = null;
        Session session = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            account = session.get(Account.class, id);
            account.setBalance(balance);
            session.update(account);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void deleteAccount(int id){
        Transaction transaction = null;
        Session session = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            account = session.get(Account.class, id);
            session.delete(account);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            if(session != null) session.close();
        }
    }

}
