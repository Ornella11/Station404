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

    List<Incident> findByStatutIncidentNot(StatutIncident statutIncident);

    @Query("""
    SELECT YEAR(i.dateOuverture), MONTH(i.dateOuverture), COUNT(i)
    FROM Incident i
    GROUP BY YEAR(i.dateOuverture), MONTH(i.dateOuverture)
    ORDER BY YEAR(i.dateOuverture), MONTH(i.dateOuverture)
    """)
    List<Object[]> compterParMois();

    @Query("""
    SELECT s.nom, COUNT(i)
    FROM Incident i JOIN i.equipement e JOIN e.station s
    GROUP BY s.nom
    ORDER BY COUNT(i) DESC
    """)
    List<Object[]> compterParStation();

    @Query("""
    SELECT i.dateOuverture, i.dateResolution
    FROM Incident i
    WHERE i.statutIncident = station404.projetFinal.model.StatutIncident.RESOLU
    ORDER BY i.dateResolution
    """)
    List<Object[]> trouverDatesIncidentsResolus();
}

