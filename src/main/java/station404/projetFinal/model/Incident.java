package station404.projetFinal.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "incident")
public class Incident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_incident", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_equipement", nullable = false)
    private Equipement equipement;

    @Column(name = "description", length = 500, nullable = false)
    private String description;

    @Column(name = "date_ouverture", nullable = false)
    private LocalDateTime dateOuverture;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut", nullable = false)
    private StatutIncident statutIncident ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_prestataire")
    private Prestataire prestataire;

    @Column(name = "date_resolution")
    private LocalDateTime dateResolution;

    public Incident(Equipement equipement, String description, LocalDateTime dateOuverture) {
        this.equipement = equipement;
        this.description = description;
        this.dateOuverture = dateOuverture;
        this.statutIncident = StatutIncident.NON_AFFECTE;
    }

    protected Incident(){
    }

    public Integer getId() {
        return id;
    }
    public Equipement getEquipement() {
        return equipement;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getDateOuverture() {
        return dateOuverture;
    }

    public StatutIncident getStatutIncident() {
        return statutIncident;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public LocalDateTime getDateResolution() {
        return dateResolution;
    }
}

