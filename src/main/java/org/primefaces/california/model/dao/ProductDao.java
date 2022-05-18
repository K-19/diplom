package org.primefaces.california.model.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.primefaces.california.model.entity.Product;
import org.primefaces.california.util.HibernateSessionFactoryUtil;

import java.util.List;

public class ProductDao {
    public Product findById(long id) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Product obj = session.get(Product.class, id);
        session.close();
        return obj;
    }

    public void save(Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(product);
        tx1.commit();
        session.close();
    }

    public void update(Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(product);
        tx1.commit();
        session.close();
    }

    public void delete(Product product) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(product);
        tx1.commit();
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<Product> findAll() {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        List<Product> list = (List<Product>)session.createQuery("From Product").list();
        session.close();
        return list;
    }
}
