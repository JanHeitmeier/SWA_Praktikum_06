package de.hsos.swa.control;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@ApplicationScoped
public class KundenService {
    List<Kunde> kunden = new ArrayList<Kunde>();

    public void kundeAnlegen(String name) {
        kunden.add(new Kunde(name));
    }

    public Collection<Kunde> kundenAbfragen() {
        return kunden;
    }

    public Kunde kundeAbfragen(Long kundennr) {
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                return k;
            }
        }
        return null;
    }

    public boolean kundeLoeschen(Long kundennr) {
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                kunden.remove(k);
                return true;
            }
        }
        return false;
    }

    public void adresseAnlegen(Long kundennr, Adresse adr) {
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                k.setAdresse(adr);
            }
        }
    }

    public void adresseAendern(Long kundennr, Adresse neueAdr) {
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                k.setAdresse(neueAdr);
            }
        }
    }

    public Adresse adresseAbfragen(Long kundennr) {
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                return k.getAdresse();
            }
        }
        return null;
    }

    public boolean adresseLoeschen(Long kundennr) {
        for (Kunde k : kunden) {
            if (k.getId() == kundennr) {
                k.setAdresse(null);
                return true;
            }
        }
        return false;
    }
}
