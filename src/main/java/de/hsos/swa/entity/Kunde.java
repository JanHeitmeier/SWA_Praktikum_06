package de.hsos.swa.entity;

import jakarta.persistence.*;
import java.util.logging.Logger;

@Entity
@Table(name = "KUNDE")
public class Kunde {
    private static final Logger LOGGER = Logger.getLogger(Kunde.class.getName());

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "adresse_id")
    private Adresse adresse;
    private String name;

    public Kunde() {
        LOGGER.info("Kunde erstellt (ohne Name)");
    }

    public Kunde(String name) {
        this.name = name;
        LOGGER.info("Kunde erstellt: " + "Name:" + name + " ID:" + id);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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
}