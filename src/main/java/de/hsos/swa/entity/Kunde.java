package de.hsos.swa.entity;

import jakarta.enterprise.context.Dependent;

@Dependent
public class Kunde {
    private long id;
    private Adresse adresse;
    private String name;

    public Kunde(String name) {
        this.name = name;
        this.id=name.hashCode();
    }

    public Adresse getAdresse() {
        return adresse;
    }

    public void setAdresse(Adresse adresse) {
        this.adresse = adresse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
