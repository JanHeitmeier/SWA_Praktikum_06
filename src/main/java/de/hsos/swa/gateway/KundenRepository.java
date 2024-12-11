package de.hsos.swa.gateway;

import de.hsos.swa.control.KundenService;
import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.Collection;
import java.util.logging.Logger;

@ApplicationScoped
public class KundenRepository implements KundenService {
    private static final Logger LOGGER = Logger.getLogger(KundenRepository.class.getName());

    @PersistenceContext
    EntityManager em;

    @Override
    @Transactional
    public Kunde kundeAnlegen(String name) {
        LOGGER.info("Kunde anlegen: " + name);
        Kunde kunde = new Kunde(name);
        em.persist(kunde);
        em.flush();
        return kunde;
    }

    @Override
    public Collection<Kunde> kundenAbfragen() {
        LOGGER.info("Alle Kunden abfragen");
        return em.createQuery("SELECT k FROM Kunde k", Kunde.class).getResultList();
    }

    @Override
    public Kunde kundeAbfragen(Long kundennr) {
        LOGGER.info("Kunde abfragen: " + kundennr);
        return em.find(Kunde.class, kundennr);
    }

    @Override
    @Transactional
    public boolean kundeLoeschen(Long kundennr) {
        LOGGER.info("Kunde löschen: " + kundennr);
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null) {
            em.remove(kunde);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public void adresseAnlegen(Long kundennr, Adresse adr) {
        LOGGER.info("Adresse anlegen für Kunde: " + kundennr);
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null) {
            em.persist(adr);
            kunde.setAdresse(adr);
            em.merge(kunde);
        }
    }

    @Override
    @Transactional
    public void adresseAendern(Long kundennr, Adresse neueAdr) {
        LOGGER.info("Adresse ändern für Kunde: " + kundennr);
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null) {
            Adresse alteAdr = kunde.getAdresse();
            if (alteAdr != null) {
                em.remove(alteAdr);
            }
            em.persist(neueAdr);
            kunde.setAdresse(neueAdr);
            em.merge(kunde);
        }
    }

    @Override
    public Adresse adresseAbfragen(Long kundennr) {
        LOGGER.info("Adresse abfragen für Kunde: " + kundennr);
        Kunde kunde = em.find(Kunde.class, kundennr);
        return (kunde != null) ? kunde.getAdresse() : null;
    }

    @Override
    @Transactional
    public boolean adresseLoeschen(Long kundennr) {
        LOGGER.info("Adresse löschen für Kunde: " + kundennr);
        Kunde kunde = em.find(Kunde.class, kundennr);
        if (kunde != null && kunde.getAdresse() != null) {
            em.remove(kunde.getAdresse());
            kunde.setAdresse(null);
            em.merge(kunde);
            return true;
        }
        return false;
    }
}