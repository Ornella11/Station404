package station404.projetFinal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import station404.projetFinal.model.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    boolean  existsByIncidentId(Integer incidentId);

    Evaluation findByIncidentId(Integer incidentId);

    @Query("""
    SELECT p.nom, AVG(e.note), COUNT(e)
    FROM Evaluation e JOIN e.incident i JOIN i.prestataire p
    GROUP BY p.nom
    ORDER BY AVG(e.note) DESC
    """)
    List<Object[]> noteMoyenneParPrestataire();
}
