package org.primefaces.california.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.model.entity.User;
import org.primefaces.california.util.HibernateSessionFactoryUtil;

import java.util.List;

public class UserDao {
    public User findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User obj = session.get(User.class, id);
        session.close();
        return obj;
    }

    public User findByLogin(String login) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = (User) session.createQuery("From User where login = :login")
                .setParameter("login", login)
                .uniqueResult();
        session.close();
        return user;
    }

    public User findByLoginAndPassword(String login, int password) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        User user = (User) session.createQuery("From User where login = :login and password = :password")
                .setParameter("login", login)
                .setParameter("password", password)
                .uniqueResult();
        session.close();
        return user;
    }

    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<User> list = (List<User>)session.createQuery("From User").list();
        session.close();
        return list;
    }
}
