package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import station404.projetFinal.model.Station;

import java.util.List;

public interface StationRepository extends JpaRepository<Station, Integer> {
    List<Station> findAllByOrderByNom();
}
