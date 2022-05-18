package org.primefaces.california.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.california.model.entity.*;
import org.primefaces.california.util.HibernateSessionFactoryUtil;

import java.util.List;

public class AssortmentDao {
    public AssortmentData findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        AssortmentData obj = session.get(AssortmentData.class, id);
        session.close();
        return obj;
    }

    public void save(AssortmentData assortmentData) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(assortmentData);
        tx1.commit();
        session.close();
    }

    public void saveList(List<AssortmentData> assortmentDataList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (AssortmentData assortmentData : assortmentDataList) {
            session.save(assortmentData);
        }
        tx1.commit();
        session.close();
    }

    public void update(AssortmentData assortmentData) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(assortmentData);
        tx1.commit();
        session.close();
    }

    public void delete(AssortmentData assortmentData) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(assortmentData);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<AssortmentData> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<AssortmentData> list = (List<AssortmentData>)session.createQuery("From AssortmentData").list();
        session.close();
        return list;
    }

    public List<AssortmentData> findByMarket(Market market) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<AssortmentData> list = (List<AssortmentData>) session.createQuery("From AssortmentData where market = :market")
                .setParameter("market", market)
                .list();
        session.close();
        return list;
    }

    public AssortmentData findByMarketAndProduct(Market market, Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        AssortmentData data = (AssortmentData) session.createQuery("From AssortmentData where market = :market and product = :product")
                .setParameter("market", market)
                .setParameter("product", product)
                .uniqueResult();
        session.close();
        return data;
    }
}
