package de.hsos.swa.entity;

import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Vetoed;

import java.util.logging.Logger;
@Vetoed
@Dependent
public class Kunde {
    private static long counter = 0;
    private static final Logger LOGGER = Logger.getLogger(Kunde.class.getName());
    private long id;
    private Adresse adresse;
    private String name;

    public Kunde() {
        LOGGER.info("Kunde erstellt (ohne Name)");
    }

    public Kunde(String name) {
        this.name = name;

        this.id = ++counter;
        LOGGER.info("Kunde erstellt: " + "Name:"+name+ " ID:" + id);
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