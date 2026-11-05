package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import station404.projetFinal.exception.IncidentException;
import station404.projetFinal.exception.IncidentIntrouvableException;
import station404.projetFinal.model.Evaluation;
import station404.projetFinal.model.Incident;
import station404.projetFinal.repository.EvaluationRepository;
import station404.projetFinal.repository.IncidentRepository;

import java.time.Clock;
import java.time.LocalDate;
import java.util.List;

@Service
public class EvaluationService {
    private final EvaluationRepository evaluationRepository;
    private final IncidentRepository incidentRepository;
    private final Clock clock;

    public EvaluationService(EvaluationRepository evaluationRepository, IncidentRepository incidentRepository, Clock clock) {
        this.evaluationRepository = evaluationRepository;
        this.incidentRepository = incidentRepository;
        this.clock = clock;
    }

    public List<Evaluation> getEvaluations() {
        return evaluationRepository.findAll();
    }

    public Evaluation getEvaluationByIncident(Integer incidentId) {
        return evaluationRepository.findByIncidentId(incidentId);
    }

    @Transactional
    public Evaluation evaluer(Integer incidentId, int note, String commentaire) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new IncidentIntrouvableException(incidentId));

        if (evaluationRepository.existsByIncidentId(incidentId))
            throw new IncidentException("Cet incident a déjà été évalué.");

        return evaluationRepository.save(new Evaluation(incident, note, commentaire, LocalDate.now(clock))
        );
    }

}
