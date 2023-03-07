package Entities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.sql.Date;


/**
 *
 * @author genop
 */
public class User {
    
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private Date creation_date;
    private String image_profile;
    private String role;

    public User(String nom, String prenom, String email, String password, Date creation_date,String image_profile,String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.creation_date = creation_date;
        this.role = role;
        this.image_profile =image_profile ;
    }
    public User(int id,String nom, String prenom, String email, String password, Date creation_date,String role,String image_profile) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.password = password;
        this.creation_date = creation_date;
        this.role = role;
        this.image_profile =image_profile ;
    }
    public User(int id,String nom, String prenom, String email,String image_profile,String role) {
        this.id=id;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.image_profile =image_profile ;
        this.role=role;
    }
    public User() {
    }

    public User(int id) {
        this.id = id;
    }

    public User(String nom, String prenom, String email, String password, Date creation_date, String image_profile) {
        this.nom = nom;
        this.prenom = prenom;
        this.email=email;
        this.password = password;
        this.creation_date = creation_date;
        this.image_profile=image_profile;
    }

    public String getImage_profile() {
        return image_profile;
    }

    public void setImage_profile(String image_profile) {
        this.image_profile = image_profile;
    }

    public User(String nom, String prenom, String password, Date creation_date, int id,String email,String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.password = password;
        this.creation_date = creation_date;
        this.id = id;
        this.email=email;
        this.role = role;
    }
    

    public User( String email, String image_profile) {
        this.email = email;
        this.image_profile = image_profile;
    }



    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCretaion_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



    @Override
    public String toString() {
        return "User{" + " nom=" + nom + ", prenom=" + prenom + ", email=" + email + ", password=" + password + ", creation_date=" + creation_date + ", id=" + id + ", role=" + role + ", image_profile=" + image_profile + '}';
    }
    

   
}