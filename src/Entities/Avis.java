
package Entities;
import java.util.Date;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
package entities;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author LENOVO
 */





public class Avis {
    private int id;
    private int rating;
    private String commentaire;
    private String titre;

    public Avis(int rating, String commentaire, String titre) {
        this.rating = rating;
        this.commentaire = commentaire;
        this.titre = titre;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }


    public Avis() {
    }

    public Avis(int id, int rating, String commentaire, String titre) {
        this.id = id;
        this.rating = rating;
        this.commentaire = commentaire;
        this.titre = titre;
    }

    
    

}