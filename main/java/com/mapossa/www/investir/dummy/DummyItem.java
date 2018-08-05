package com.mapossa.www.investir.dummy;


import java.util.HashMap;

/**
 * A dummy item representing a piece of content.
 */

public  class DummyItem {
    public static HashMap<String,DummyItem> ITEM_MAP = new HashMap<String,DummyItem>();
    public  String id;
    public  String content;
    public  String details;
    private boolean professional;
    private String nom;
    private String entreprise;
    private String phone;
    private String phoneEntreprise;
    private String email;
    private String emailEntreprise;
    private String ville;
    private String adresse;
    private String emailPro;
    private String nomPro;

    public void setContent(String content) {
        this.content = content;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setEmailPro(String emailPro) {
        this.emailPro = emailPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public DummyItem(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
        this.setProfessional(false);
    }

    public DummyItem() {

    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }

    public boolean isProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }

    public String getNom() {
        return nom;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEntreprise() {
        return entreprise;
    }

    public void setEntreprise(String entreprise) {
        this.entreprise = entreprise;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneEntreprise() {
        return phoneEntreprise;
    }

    public void setPhoneEntreprise(String phoneEntreprise) {
        this.phoneEntreprise = phoneEntreprise;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailEntreprise() {
        return emailEntreprise;
    }

    public void setEmailEntreprise(String emailEntreprise) {
        this.emailEntreprise = emailEntreprise;
    }

    @Override
    public String toString() {
        return content;
    }

    public String getVille() {
        return ville;
    }
    public void setVille(String ville) {
        this.ville= ville;
    }

    public String getAdresse() {
        return this.adresse;
    }

    public String getEmailPro() {
        return this.emailPro;
    }

    public String getNomPro() {
        return this.nomPro;
    }

    public class Params {

        public static final String ID = "id";
        public static final String NAME = "nom";
        public static final String COUNTRY = "country";
        public static final String CITY = "ville";
        public static final String PHONE = "telephone";

    }
}