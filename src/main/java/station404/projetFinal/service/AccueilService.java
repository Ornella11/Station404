package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import station404.projetFinal.model.Incident;
import station404.projetFinal.model.StatutIncident;
import station404.projetFinal.repository.IncidentRepository;

import java.time.Clock;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AccueilService {

    private final IncidentRepository incidentRepository;
    private final Clock clock;

    public AccueilService(IncidentRepository incidentRepository, Clock clock) {
        this.incidentRepository = incidentRepository;
        this.clock = clock;
    }

    public String calculerUrgence(Incident incident) {
        long heures = Duration.between(incident.getDateOuverture(), LocalDateTime.now(clock)).toHours();

        if (heures < 48) {
            return "VERT";
        } else if (heures < 72) {
            return "ORANGE";
        } else {
            return "ROUGE";
        }
    }

    public Map<Incident, String> getIncidentsAvecUrgence() {
        List<Incident> incidents = incidentRepository.findByStatutIncidentNot(StatutIncident.RESOLU);

        return incidents.stream()
                .collect(Collectors.toMap(
                        incident -> incident,
                        this::calculerUrgence,
                        (oldVal, newVal) -> oldVal,
                        LinkedHashMap::new
                ));
    }
}
