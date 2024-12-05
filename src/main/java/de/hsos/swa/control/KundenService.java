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
        //todo
        return false;
    }

    public void adresseAnlegen(Long kundennr, Adresse adr) {
        //todo
    }

    public void adresseAendern(Long kundennr, Adresse neueAdr) {
        //todo
    }

    public Adresse adresseAbfragen(Long kundennr) {
        //todo
        return null;
    }

    public boolean adresseLoeschen(Long kundennr) {
        //todo
        return false;
    }
}
