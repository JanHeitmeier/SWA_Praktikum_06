package de.hsos.swa.control;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

@RequestScoped
public class KundenService {
    private static final Logger LOGGER = Logger.getLogger(KundenService.class.getName());
    List<Kunde> kunden = new ArrayList<>();

    public void kundeAnlegen(String name) {
        LOGGER.info("Kunde anlegen: " + name);
        kunden.add(new Kunde(name));
    }

    public Collection<Kunde> kundenAbfragen() {
        LOGGER.info("Alle Kunden abfragen");
        return kunden;
    }

    public Kunde kundeAbfragen(Long kundennr) {
        LOGGER.info("Kunde abfragen: " + kundennr);
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                return k;
            }
        }
        return null;
    }

    public boolean kundeLoeschen(Long kundennr) {
        LOGGER.info("Kunde löschen: " + kundennr);
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                kunden.remove(k);
                return true;
            }
        }
        return false;
    }

    public void adresseAnlegen(Long kundennr, Adresse adr) {
        LOGGER.info("Adresse anlegen für Kunde: " + kundennr);
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                k.setAdresse(adr);
            }
        }
    }

    public void adresseAendern(Long kundennr, Adresse neueAdr) {
        LOGGER.info("Adresse ändern für Kunde: " + kundennr);
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                k.setAdresse(neueAdr);
            }
        }
    }

    public Adresse adresseAbfragen(Long kundennr) {
        LOGGER.info("Adresse abfragen für Kunde: " + kundennr);
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                return k.getAdresse();
            }
        }
        return null;
    }

    public boolean adresseLoeschen(Long kundennr) {
        LOGGER.info("Adresse löschen für Kunde: " + kundennr);
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                k.setAdresse(null);
                return true;
            }
        }
        return false;
    }
}