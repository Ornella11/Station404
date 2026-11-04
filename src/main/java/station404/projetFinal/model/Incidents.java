package station404.projetFinal.model;

import java.time.LocalDateTime;

public record Incidents(
        Integer id,
        String description,
        StatutIncident statut,
        LocalDateTime dateOuverture,
        LocalDateTime dateResolution,
        String referenceEquipement,
        String nomStation,
        String nomPrestataire
) {

    public static Incidents depuis(Incident incident) {
        Prestataire prestataire = incident.getPrestataire();
        return new Incidents(
                incident.getId(),
                incident.getDescription(),
                incident.getStatutIncident(),
                incident.getDateOuverture(),
                incident.getDateResolution(),
                incident.getEquipement().getReference(),
                incident.getEquipement().getStation().getNom(),
                prestataire == null ? null : prestataire.getNom()
        );
    }
}