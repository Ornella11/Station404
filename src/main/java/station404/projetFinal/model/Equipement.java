package station404.projetFinal.model;

import jakarta.persistence.*;

@Entity
@Table(name = "equipement")
public class Equipement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipement", nullable = false)
    private Integer id;

    @Column(name = "reference", length = 30, nullable = false)
    private String reference;

    @Column(name = "libelle", length = 200, nullable = false)
    private String libelle;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TypeEquipement typeEquipement;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_station", nullable = false)
    private Station station;

    @Enumerated(EnumType.STRING)
    @Column(name = "etat", nullable = false)
    private EtatEquipement etatEquipement ;

    protected Equipement() {
    }

    public Integer getId() {
        return id;
    }

    public String getLibelle() {
        return libelle;
    }

    public String getReference() {
        return reference;
    }

    public TypeEquipement getTypeEquipement() {
        return typeEquipement;
    }

    public Station getStation() {
        return station;
    }

    public EtatEquipement getEtatEquipement() {
        return etatEquipement;
    }
}
