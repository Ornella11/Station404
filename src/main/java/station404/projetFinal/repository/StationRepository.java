package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import station404.projetFinal.model.EtatStation;
import station404.projetFinal.model.Station;

import java.util.List;


public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findAllByOrderByNom();

    @Query("""
SELECT new station404.projetFinal.model.EtatStation(
           s.id, s.nom, s.latitude, s.longitude, COUNT(i))
    FROM Station s
    LEFT JOIN Equipement e ON e.station = s
    LEFT JOIN Incident i ON i.equipement = e AND i.statutIncident <> station404.projetFinal.model.StatutIncident.RESOLU
    GROUP BY s.id, s.nom, s.latitude, s.longitude
""")
    List<EtatStation> calculerEtatStation();
}
