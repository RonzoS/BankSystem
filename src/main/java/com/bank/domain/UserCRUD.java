package com.bank.domain;

import com.bank.util.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class UserCRUD {

    public Integer saveUser (User user, int accountId) {
        Transaction transaction = null;
        Session session = null;
        Integer id = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            Account account = new AccountCRUD().getAccount(accountId);
            user.setAccount_id(account);
            id = (Integer) session.save(user);
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

    public User getUser(int id){
        Transaction transaction = null;
        Session session = null;
        User user = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            transaction.rollback();
        } catch (Exception e) {
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            transaction.commit();
            session.close();
        }
        return user;
    }

    public User getAccountUser(Integer accountId){
        Transaction transaction = null;
        Session session = null;
        User user = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From User WHERE account_id = " + accountId;
            Query query = session.createQuery(hql);
            List results = query.list();
            if(results.size()>0)
                user = (User) results.get(0);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            session.close();
        }
        return user;
    }

    public List getUsers(){
        Transaction transaction = null;
        Session session = null;
        List results = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            String hql = "From User";
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

    public void updateUser(int id, int phone, String name, String surname, String email, LocalDate dateOfBirth){
        Transaction transaction = null;
        Session session = null;
        User user = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            user.setPhone(phone);
            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setDateOfBirth(dateOfBirth);
            session.update(user);
            transaction.commit();
        } catch (Exception e){
            e.printStackTrace();
            if(transaction != null)
                transaction.rollback();
        } finally {
            session.close();
        }
    }

    public void deleteUser(int id){
        Transaction transaction = null;
        Session session = null;
        User user = null;
        try{
            session = HibernateUtil.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            user = session.get(User.class, id);
            user.setAccount_id(null);
            session.delete(user);
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
