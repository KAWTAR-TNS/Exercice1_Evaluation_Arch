import org.example.entities.Categorie;
import org.example.entities.Commande;
import org.example.entities.LigneCommande;
import org.example.entities.Produit;
import org.example.service.ProduitService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.text.SimpleDateFormat;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ProduitService produitService = new ProduitService();

        // Creation de catégorie
        Categorie cat = new Categorie();
        cat.setLibelle("PC et adapter");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(cat);
        tx.commit();
        session.close();

        // Creation des produits
        Produit p1 = new Produit();
        p1.setReference("ES12");
        p1.setPrix(120);
        p1.setCategorie(cat);
        produitService.create(p1);

        Produit p2 = new Produit();
        p2.setReference("ZR85");
        p2.setPrix(100);
        p2.setCategorie(cat);
        produitService.create(p2);

        Produit p3 = new Produit();
        p3.setReference("EE85");
        p3.setPrix(200);
        p3.setCategorie(cat);
        produitService.create(p3);

        // Creation commande
        Commande cmd = new Commande();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        cmd.setDate(sdf.parse("14/03/2013"));
        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        session.save(cmd);
        tx.commit();
        session.close();

        // Lignes de commande
        LigneCommande lc1 = new LigneCommande();
        lc1.setCommande(cmd);
        lc1.setProduit(p1);
        lc1.setQuantite(7);

        LigneCommande lc2 = new LigneCommande();
        lc2.setCommande(cmd);
        lc2.setProduit(p2);
        lc2.setQuantite(14);

        LigneCommande lc3 = new LigneCommande();
        lc3.setCommande(cmd);
        lc3.setProduit(p3);
        lc3.setQuantite(5);

        session = HibernateUtil.getSessionFactory().openSession();
        tx = session.beginTransaction();
        session.save(lc1);
        session.save(lc2);
        session.save(lc3);
        tx.commit();
        session.close();

        // l'affichage li bghina
        session = HibernateUtil.getSessionFactory().openSession();
        List<LigneCommande> lignes = session.createQuery(
                        "FROM LigneCommande lc WHERE lc.commande.id = :cid", LigneCommande.class)
                .setParameter("cid", cmd.getId())
                .list();
        session.close();

        System.out.println("Commande : " + cmd.getId() + "     Date : " + sdf.format(cmd.getDate()));
        System.out.println("Liste des produits :");
        System.out.println("Référence   Prix    Quantité");
        for (LigneCommande lc : lignes) {
            Produit p = lc.getProduit();
            System.out.println(p.getReference() + "        " + (int)p.getPrix() + " DH  " + lc.getQuantite());
        }
    }
}