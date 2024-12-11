package de.hsos.swa.control;

import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.RequestScoped;

import java.util.Collection;


@RequestScoped
public interface KundenService {

    public Kunde kundeAnlegen(String name);

    public Collection<Kunde> kundenAbfragen();

    public Kunde kundeAbfragen(Long kundennr);

    public boolean kundeLoeschen(Long kundennr);

    public void adresseAnlegen(Long kundennr, Adresse adr);

    public void adresseAendern(Long kundennr, Adresse neueAdr);

    public Adresse adresseAbfragen(Long kundennr);

    public boolean adresseLoeschen(Long kundennr);
}