package de.hsos.swa.boundary.rs;

import de.hsos.swa.control.KundenService;
import de.hsos.swa.entity.Adresse;
import de.hsos.swa.entity.Kunde;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.Collection;
import java.util.logging.Logger;

@Path("/kunden")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Kunden", description = "Kundenverwaltung API")
@RequestScoped
public class KundenResource {
    private static final Logger LOGGER = Logger.getLogger(KundenResource.class.getName());

    @Inject
    KundenService kundenService;

    @POST
    @Operation(summary = "Erstellt einen neuen Kunden", description = "Erstellt einen neuen Kunden mit den angegebenen Daten")
    @APIResponse(responseCode = "201", description = "Kunde erstellt")
    public Response kundeAnlegen(Kunde kunde) {
        LOGGER.info("Kunde anlegen: " + kunde.getName());
        Kunde neuerKunde = kundenService.kundeAnlegen(kunde.getName());
        return Response.status(Response.Status.CREATED).entity(neuerKunde).build();
    }

    @GET
    @Operation(summary = "Gibt alle Kunden zurück", description = "Gibt eine Liste aller Kunden zurück")
    @APIResponse(responseCode = "200", description = "Liste der Kunden", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Kunde.class)))
    public Collection<Kunde> kundenAbfragen() {
        LOGGER.info("Alle Kunden abfragen");
        return kundenService.kundenAbfragen();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Gibt einen Kunden zurück", description = "Gibt die Daten eines Kunden anhand der ID zurück")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Kunde gefunden", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Kunde.class))),
            @APIResponse(responseCode = "404", description = "Kunde nicht gefunden")
    })
    public Response kundeAbfragen(@PathParam("id") Long id) {
        LOGGER.info("Kunde abfragen: " + id);
        Kunde kunde = kundenService.kundeAbfragen(id);
        if (kunde != null) {
            return Response.ok(kunde).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Löscht einen Kunden", description = "Löscht einen Kunden anhand der ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Kunde gelöscht"),
            @APIResponse(responseCode = "404", description = "Kunde nicht gefunden")
    })
    public Response kundeLoeschen(@PathParam("id") Long id) {
        LOGGER.info("Kunde löschen: " + id);
        boolean deleted = kundenService.kundeLoeschen(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/{id}/adresse")
    @Operation(summary = "Erstellt eine neue Adresse für einen Kunden", description = "Erstellt eine neue Adresse für einen Kunden anhand der ID")
    @APIResponse(responseCode = "201", description = "Adresse erstellt")
    public Response adresseAnlegen(@PathParam("id") Long id, Adresse adresse) {
        LOGGER.info("Adresse anlegen für Kunde: " + id);
        kundenService.adresseAnlegen(id, adresse);
        return Response.status(Response.Status.CREATED).build();
    }

    @PUT
    @Path("/{id}/adresse")
    @Operation(summary = "Ändert die Adresse eines Kunden", description = "Ändert die Adresse eines Kunden anhand der ID")
    @APIResponse(responseCode = "200", description = "Adresse geändert")
    public Response adresseAendern(@PathParam("id") Long id, Adresse adresse) {
        LOGGER.info("Adresse ändern für Kunde: " + id);
        kundenService.adresseAendern(id, adresse);
        return Response.ok().build();
    }

    @GET
    @Path("/{id}/adresse")
    @Operation(summary = "Gibt die Adresse eines Kunden zurück", description = "Gibt die Adresse eines Kunden anhand der ID zurück")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Adresse gefunden", content = @Content(mediaType = MediaType.APPLICATION_JSON, schema = @Schema(implementation = Adresse.class))),
            @APIResponse(responseCode = "404", description = "Adresse nicht gefunden")
    })
    public Response adresseAbfragen(@PathParam("id") Long id) {
        LOGGER.info("Adresse abfragen für Kunde: " + id);
        Adresse adresse = kundenService.adresseAbfragen(id);
        if (adresse != null) {
            return Response.ok(adresse).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}/adresse")
    @Operation(summary = "Löscht die Adresse eines Kunden", description = "Löscht die Adresse eines Kunden anhand der ID")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Adresse gelöscht"),
            @APIResponse(responseCode = "404", description = "Adresse nicht gefunden")
    })
    public Response adresseLoeschen(@PathParam("id") Long id) {
        LOGGER.info("Adresse löschen für Kunde: " + id);
        boolean deleted = kundenService.adresseLoeschen(id);
        if (deleted) {
            return Response.noContent().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}