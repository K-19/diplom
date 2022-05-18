package org.primefaces.california.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.california.model.entity.AssortmentData;
import org.primefaces.california.model.entity.Market;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.model.entity.SalesData;
import org.primefaces.california.util.HibernateSessionFactoryUtil;

import java.util.List;

public class SalesDataDao {
    public SalesData findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        SalesData obj = session.get(SalesData.class, id);
        session.close();
        return obj;
    }

    public void save(SalesData salesData) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(salesData);
        session.save(salesData);
        tx1.commit();
        session.close();
    }

    public void saveList(List<SalesData> salesDataList) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        for (SalesData salesData : salesDataList) {
            session.save(salesData);
        }
        tx1.commit();
        session.close();
    }

    public void update(SalesData salesData) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(salesData);
        tx1.commit();
        session.close();
    }

    public void delete(SalesData salesData) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(salesData);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<SalesData> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<SalesData> list = (List<SalesData>)session.createQuery("From SalesData").list();
        session.close();
        return list;
    }

    public List<SalesData> findByMarketAndProduct(Market market, Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<SalesData> data = (List<SalesData>) session.createQuery("From SalesData where market = :market and product = :product")
                .setParameter("market", market)
                .setParameter("product", product)
                .list();
        session.close();
        return data;
    }
}
