package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import station404.projetFinal.model.Incident;
import station404.projetFinal.repository.IncidentRepository;

import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    public IncidentService(IncidentRepository incidentRepository) { this.incidentRepository = incidentRepository; }

    public List<Incident> getIncidents() {
        return incidentRepository.findAll();
    }

    public Optional<Incident> getIncidentById(Integer id) {
        Optional<Incident> incident = incidentRepository.findById(id);
        return incident;
    }

        public Incident addIncident(Incident incident) {
        return incidentRepository.save(incident);
    }


}
