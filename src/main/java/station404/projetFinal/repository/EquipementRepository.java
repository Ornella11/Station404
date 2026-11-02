package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import station404.projetFinal.model.Equipement;

import java.util.List;
import java.util.Optional;

public interface EquipementRepository extends JpaRepository<Equipement, Integer> {
    List<Equipement> findByStationId(Integer stationId);

    @Query("""
 SELECT e FROM Equipement e
            JOIN FETCH e.station
            WHERE e.id = :id
            """)

    Optional<Equipement> chercherAvecStation(@Param("id") Integer id);
}
