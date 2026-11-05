package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import station404.projetFinal.exception.IncidentException;
import station404.projetFinal.exception.IncidentIntrouvableException;
import station404.projetFinal.exception.PrestataireIntrouvableException;
import station404.projetFinal.model.Incident;
import station404.projetFinal.model.Prestataire;
import station404.projetFinal.model.StatutIncident;
import station404.projetFinal.repository.IncidentRepository;
import station404.projetFinal.repository.PrestataireRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final PrestataireRepository prestataireRepository;
    private final Clock clock;

    public IncidentService(IncidentRepository incidentRepository, PrestataireRepository prestataireRepository, Clock clock) {
        this.incidentRepository = incidentRepository;
        this.prestataireRepository = prestataireRepository;
        this.clock = clock;
    }

    public List<Incident> getIncidents() {
        return incidentRepository.findAll();
    }

    public List<Incident> getIncidentNonResolu() {
        return incidentRepository.findByStatutIncidentNot(StatutIncident.RESOLU);
    }

    public Optional<Incident> getIncidentById(Integer id) {
        Optional<Incident> incident = incidentRepository.findById(id);
        return incident;
    }

        public Incident addIncident(Incident incident) {
        incident.setDateOuverture(LocalDateTime.now(clock));
        incident.setStatutIncident(StatutIncident.NON_AFFECTE);
        return incidentRepository.save(incident);
    }

    public List<Incident> findByStatutIncidentOrderByDateResolutionDesc() {
        return incidentRepository.findByStatutIncidentOrderByDateResolutionDesc(StatutIncident.RESOLU);
    }

    public void affecterPrestataire(Integer incidentId, Integer prestataireId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new IncidentIntrouvableException(incidentId));
        if (incident.isResolu()){
            throw  new IncidentException("L'incident " + incidentId + " est déjà résolu");
        }

        Prestataire prestataire = prestataireRepository.findById(prestataireId)
                .orElseThrow(() -> new PrestataireIntrouvableException(prestataireId));

        incident.setPrestataire(prestataire);
        incident.setStatutIncident(StatutIncident.AFFECTE);

        incidentRepository.save(incident);
    }

    public void resoudreIncident(Integer incidentId) {
        Incident incident = incidentRepository.findById(incidentId)
                .orElseThrow(() -> new IncidentIntrouvableException(incidentId));

        incident.setStatutIncident(StatutIncident.RESOLU);
        incident.setDateResolution(LocalDateTime.now(clock));

        incidentRepository.save(incident);
    }

    public void modifierIncident(Integer id, Incident incidentModifie) {
        Incident incidentExistant = incidentRepository.findById(id)
                .orElseThrow(() -> new IncidentIntrouvableException(id));

        incidentExistant.setEquipement(incidentModifie.getEquipement());
        incidentExistant.setDescription(incidentModifie.getDescription());

        incidentRepository.save(incidentExistant);
    }

    public List<Incident> listerIncidentsAvecDetails() {
        return incidentRepository.trouverTousAvecDetails();
    }

    public Incident chercherIncidentAvecDetails(Integer id) {
        return incidentRepository.trouverAvecDetails(id)
                .orElseThrow(() -> new IncidentIntrouvableException(id));
    }


}
