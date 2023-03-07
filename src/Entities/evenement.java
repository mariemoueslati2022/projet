package Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;

/**
 *
 * @author user
 */
public class evenement {
    private int id,nbre_deplaces;
    private String nom,type;
    private Date date ; 
    private float prix;
    
    
    public evenement(){
       id=0;
       nbre_deplaces=0;
       nom="";
     
       type="";
       prix=0.f;
    }
    public evenement(int id){
        this.id=id;
    }
    
    
    public evenement(int id,String nom,int nbre_deplaces,String type,float prix,Date date){
        this.id=id;
        this.nom=nom;
        this.nbre_deplaces=nbre_deplaces;
        this.type=type;
        this.prix=prix;
        this.date=date;
        
    }

    

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getNbre_deplaces() {
        return nbre_deplaces;
    }

    public String getType() {
        return type;
    }

    public float getPrix() {
        return prix;
    }

    public Date getDate() {
        return date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNbre_deplaces(int nbre_deplaces) {
        this.nbre_deplaces = nbre_deplaces;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "evenement{" + "id=" + id + ", nbre_deplaces=" + nbre_deplaces + ", nom=" + nom + ", date=" + date + ", type=" + type + ", prix=" + prix + '}';
    }

   
    
}
