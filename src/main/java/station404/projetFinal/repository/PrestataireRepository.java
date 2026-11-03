package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import station404.projetFinal.model.Incident;
import station404.projetFinal.model.Prestataire;

public interface PrestataireRepository extends JpaRepository<Prestataire, Integer> {

}
