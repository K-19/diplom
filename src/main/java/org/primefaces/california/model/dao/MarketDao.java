package org.primefaces.california.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.california.model.entity.Market;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.util.HibernateSessionFactoryUtil;

import java.util.List;

public class MarketDao {
    public Market findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Market obj = session.get(Market.class, id);
        session.close();
        return obj;
    }

    public void save(Market market) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(market);
        tx1.commit();
        session.close();
    }

    public void update(Market market) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(market);
        tx1.commit();
        session.close();
    }

    public void delete(Market market) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(market);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<Market> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Market> list = (List<Market>)session.createQuery("From Market").list();
        session.close();
        return list;
    }
}
