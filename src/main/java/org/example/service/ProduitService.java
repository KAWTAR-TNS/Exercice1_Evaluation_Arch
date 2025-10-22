package org.example.service;

import org.example.entities.Categorie;
import org.example.entities.Commande;
import org.example.entities.Produit;
import org.example.dao.IDao;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;


import java.util.Date;
import java.util.List;

public class ProduitService implements IDao<Produit> {

    @Override
    public void create(Produit o) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(o);
        tx.commit();
        session.close();
    }


    @Override
    public Produit getById(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Produit p = session.get(Produit.class, id);
        session.close();
        return p;
    }

    @Override
    public List<Produit> getAll() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Produit> produits = session.createQuery("from Produit", Produit.class).list();
        session.close();
        return produits;
    }

    @Override
    public void update(Produit p) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.update(p);
        tx.commit();
        session.close();
    }

    @Override
    public void delete(Produit p) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.delete(p);
        tx.commit();
        session.close();
    }

    // Produits par catégorie
    public List<Produit> getByCategorie(Categorie c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> query = session.createQuery("FROM Produit p WHERE p.categorie = :cat", Produit.class);
        query.setParameter("cat", c);
        List<Produit> list = query.list();
        session.close();
        return list;
    }

    // Produits commandés entre deux températures
    public List<Produit> getProduitsBetweenDates(Date d1, Date d2) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> query = session.createQuery(
                "SELECT lc.produit FROM LigneCommande lc WHERE lc.commande.date BETWEEN :d1 AND :d2", Produit.class);
        query.setParameter("d1", d1);
        query.setParameter("d2", d2);
        List<Produit> list = query.list();
        session.close();
        return list;
    }

    // Produits commandés dans une commande donnée
    public List<Produit> getProduitsByCommande(Commande c) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> query = session.createQuery(
                "SELECT lc.produit FROM LigneCommande lc WHERE lc.commande = :cmd", Produit.class);
        query.setParameter("cmd", c);
        List<Produit> list = query.list();
        session.close();
        return list;
    }

    // Produits dont le prix > 100 DH (requête nommée)
    public List<Produit> getProduitsPrixSup100() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Query<Produit> query = session.createNamedQuery("Produit.findPrixSup100", Produit.class);
        List<Produit> produits = query.getResultList();
        session.close();
        return produits;
    }
}