package station404.projetFinal.model;

import java.time.Duration;
import java.time.LocalDateTime;

import static java.time.Duration.between;

public enum EtatIncident {
        VERT, ORANGE, ROUGE, AFFECTE, RESOLU;

        private static final Duration SEUIL_ORANGE = Duration.ofHours(48);
        private static final Duration SEUIL_ROUGE  = Duration.ofHours(72);

    public static EtatIncident de(StatutIncident statut, LocalDateTime dateOuverture, LocalDateTime maintenant) {
        if (statut == StatutIncident.RESOLU)  return RESOLU;
        if (statut == StatutIncident.AFFECTE) return AFFECTE;

        Duration dureeOuverture = between(dateOuverture, maintenant);
        if (dureeOuverture.compareTo(SEUIL_ROUGE) >= 0) {return ROUGE;}
        if (dureeOuverture.compareTo(SEUIL_ORANGE) >= 0) {return ORANGE;}
        return VERT;
    }
}
