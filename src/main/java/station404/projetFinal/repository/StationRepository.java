package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import station404.projetFinal.model.Station;

public interface StationRepository extends JpaRepository<Station, Integer> {
}
