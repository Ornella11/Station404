package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import station404.projetFinal.model.Incident;
import station404.projetFinal.model.StatutIncident;

import java.util.List;

public interface IncidentRepository extends JpaRepository<Incident, Integer> {
    List<Incident> findByEquipementIdOrderByDateOuvertureAsc(Integer equipementId);

    @Query("""
 SELECT i FROM Incident i
            JOIN FETCH i.equipement e
            JOIN FETCH e.station
            LEFT JOIN FETCH i.prestataire
            WHERE i.statutIncident <> station404.projetFinal.model.StatutIncident.RESOLU
            ORDER BY i.dateOuverture ASC
            """)
    List<Incident> trouverIncidentsActifs();

    List<Incident> findByStatutIncidentOrderByDateResolutionDesc(StatutIncident statutIncident);
}
