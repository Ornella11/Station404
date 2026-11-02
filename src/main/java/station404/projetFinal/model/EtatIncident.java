package station404.projetFinal.model;

import java.time.Duration;
import java.time.LocalDateTime;

public enum EtatIncident {
        VERT, ORANGE, ROUGE, AFFECTE, RESOLU;

        private static final Duration SEUIL_ORANGE = Duration.ofHours(48);
        private static final Duration SEUIL_ROUGE  = Duration.ofHours(72);

    public static EtatIncident de(StatutIncident statut, LocalDateTime dateOuverture) {
        if (statut == StatutIncident.RESOLU)  return RESOLU;
        if (statut == StatutIncident.AFFECTE) return AFFECTE;

        long duree = Duration.between(Incident.dateOuverture, LocalDateTime.now()).toHours();
        if (duree >= 72) return ROUGE;
        if (duree >= 48) return ORANGE;
        return VERT;
    }
}
