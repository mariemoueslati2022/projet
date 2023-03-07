/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entities;
import java.sql.Date;

/**
 *
 * @author user
 */
public class offre {
    private int id_offre;
    private String nom;
    Date date_datedeb,date_fin;
    private float pourcentage;

    public offre(int id_offre, String nom, Date date_datedeb, Date date_fin, float pourcentage) {
        this.id_offre = id_offre;
        this.nom = nom;
        this.date_datedeb = date_datedeb;
        this.date_fin = date_fin;
        this.pourcentage = pourcentage;
    }

    public offre() {
    }

    public int getId_offre() {
        return id_offre;
    }

    public void setId_offre(int id_offre) {
        this.id_offre = id_offre;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate_datedeb() {
        return date_datedeb;
    }

    public void setDate_datedeb(Date date_datedeb) {
        this.date_datedeb = date_datedeb;
    }

    public Date getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(Date date_fin) {
        this.date_fin = date_fin;
    }

    public float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(float pourcentage) {
        this.pourcentage = pourcentage;
    }

    @Override
    public String toString() {
        return "offre{" + "id_offre=" + id_offre + ", nom=" + nom + ", date_datedeb=" + date_datedeb + ", date_fin=" + date_fin + ", pourcentage=" + pourcentage + '}';
    }
    
     
    
     
}
