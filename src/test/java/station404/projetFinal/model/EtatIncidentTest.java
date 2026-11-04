package station404.projetFinal.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EtatIncidentTest {
    private static final LocalDateTime OUVERTURE = LocalDateTime.of(2026, 11, 1, 10, 0);

    @Test
    void incidentNonAffecteOuvertDepuis47HeuresEstVert() {
        EtatIncident etat = EtatIncident.de(StatutIncident.NON_AFFECTE, OUVERTURE, OUVERTURE.plusHours(47));
        assertEquals(EtatIncident.VERT, etat);
    }
    @Test
    void incidentNonAffecteOuvertDepuis71HeuresEstOrange() {
        EtatIncident etat = EtatIncident.de(StatutIncident.NON_AFFECTE, OUVERTURE, OUVERTURE.plusHours(71));
        assertEquals(EtatIncident.ORANGE, etat);
    }
    @Test
    void incidentNonAffecteOuvertDepuis48HeuresEstOrange() {
        EtatIncident etat = EtatIncident.de(StatutIncident.NON_AFFECTE, OUVERTURE, OUVERTURE.plusHours(48));
        assertEquals(EtatIncident.ORANGE, etat);
    }
    @Test
    void incidentNonAffecteOuvertDepuis72HeuresEstRouge() {
        EtatIncident etat = EtatIncident.de(StatutIncident.NON_AFFECTE, OUVERTURE, OUVERTURE.plusHours(72));
        assertEquals(EtatIncident.ROUGE, etat);
    }
    @Test
    void incidentAffecteOuvertDepuis80HeuresNestJamaisRouge() {
        EtatIncident etat = EtatIncident.de(StatutIncident.AFFECTE, OUVERTURE, OUVERTURE.plusHours(80));
        assertEquals(EtatIncident.AFFECTE, etat);
    }
    @Test
    void incidentResoluEstResolu() {
        EtatIncident etat = EtatIncident.de(StatutIncident.RESOLU, OUVERTURE, OUVERTURE.plusHours(80));
        assertEquals(EtatIncident.RESOLU, etat);
    }
}
