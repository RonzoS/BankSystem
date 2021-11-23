package com.bank.domain;

import com.bank.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransferlogCRUD {

    public void saveTransferlog (Transferlog transferlog, int accountId) {
        Transaction transaction = null;
        Session session = null;
        Account account = null;
        List<Transferlog> transferlogs = new ArrayList<>();
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            account = new AccountCRUD().getAccount(accountId);
            transferlogs = getAccountTransferlog(accountId);
            transferlogs.add(transferlog);
            account.setTransferlogs(transferlogs);
            session.update(account);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        }finally {
            session.close();
        }
    }

    public Transferlog getTransferlog(int id){
        Transaction transaction = null;
        Session session = null;
        Transferlog transferlog = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            transferlog = session.get(Transferlog.class, id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return transferlog;
    }

    public List getTransferlogs(){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Transferlog";
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

    public void updateTransferlog(int id, String description, double value, double valueBefore, double valueAfter){
        Transaction transaction = null;
        Session session = null;
        Transferlog transferlog = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            transferlog = session.get(Transferlog.class, id);
            transferlog.setDescription(description);
            transferlog.setValue(value);
            transferlog.setValueBefore(valueBefore);
            transferlog.setValueBefore(valueAfter);
            session.update(transferlog);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    public List getAccountTransferlog(Integer accountId){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Transferlog WHERE account_id = " + accountId;
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

    public void deleteTransferlog(int id){
        Transaction transaction = null;
        Session session = null;
        Transferlog transferlog = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            transferlog = session.get(Transferlog.class, id);
            session.delete(transferlog);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }
}