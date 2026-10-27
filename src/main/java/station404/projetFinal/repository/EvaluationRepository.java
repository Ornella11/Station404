package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import station404.projetFinal.model.Evaluation;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    boolean  existsByIncidentId(Integer incidentId);
}
