package com.bank.domain;

import com.bank.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CardCRUD {

    public Integer saveCard (Card card, int accountId) {
        Transaction transaction = null;
        Session session = null;
        Integer id = null;
        Account account = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            account = new AccountCRUD().getAccount(accountId);
            card.setAccount_id(account);
            id = (Integer) session.save(card);
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

    public Card getCard(int id){
        Transaction transaction = null;
        Session session = null;
        Card card = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            card = session.get(Card.class, id);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
        return card;
    }

    public Card getAccountCard(Integer accountId){
        Transaction transaction = null;
        Session session = null;
        Card card = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Card WHERE account_id = " + accountId;
            Query query = session.createQuery(hql);
            List results = query.list();
            if(results.size()>0)
                card = (Card) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return card;
    }

    public List getCards(){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Card";
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

    public boolean getCardByNumber(int cardNumber){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        Card card = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Card WHERE cardNumber = " + cardNumber;
            Query query = session.createQuery(hql);
            results = query.list();
            if (results.size() > 0) card = (Card) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        if(card == null)
            return false;
        else
            return true;
    }

    public Card getCardByLoginAndPin(int cardNumber, int pin){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        Card card = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From Card WHERE cardNumber = " + cardNumber + " and pin = "+ pin;
            Query query = session.createQuery(hql);
            results = query.list();
            if (results.size() > 0) card = (Card) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return card;
    }

    public void updateCard(int id, int cardNumber, int pin){
        Transaction transaction = null;
        Session session = null;
        Card card = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            card = session.get(Card.class, id);
            card.setCardNumber(cardNumber);
            card.setPin(pin);
            session.update(card);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void deleteCard(int id){
        Transaction transaction = null;
        Session session = null;
        Card card = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            card = session.get(Card.class, id);
            card.setAccount_id(null);
            session.delete(card);
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
