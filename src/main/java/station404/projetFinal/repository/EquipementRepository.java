package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import station404.projetFinal.model.Equipement;

import java.util.List;

public interface EquipementRepository extends JpaRepository<Equipement, Integer> {
    List<Equipement> findByStationId(Integer stationId);
}
