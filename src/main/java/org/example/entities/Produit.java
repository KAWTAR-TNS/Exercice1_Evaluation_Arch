package org.example.entities;


import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;

@Entity
@NamedQuery(
        name = "Produit.findPrixSup100",
        query = "SELECT p FROM Produit p WHERE p.prix > 100"
)
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String reference;
    private double prix;

    @ManyToOne
    private Categorie categorie;

    // Getters & Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }



    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}
