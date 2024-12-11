package de.hsos.swa.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ADRESSE")
public class Adresse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String plz;
    private String ort;
    private String strasse;
    private String hausnr;

    public Adresse() {
    }

    public Adresse(String plz, String ort, String strasse, String hausnr) {
        this.plz = plz;
        this.ort = ort;
        this.strasse = strasse;
        this.hausnr = hausnr;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getStrasse() {
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public String getHausnr() {
        return hausnr;
    }

    public void setHausnr(String hausnr) {
        this.hausnr = hausnr;
    }
}