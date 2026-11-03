package station404.projetFinal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import station404.projetFinal.model.*;
import station404.projetFinal.service.EquipementService;
import station404.projetFinal.service.EvaluationService;
import station404.projetFinal.service.IncidentService;
import station404.projetFinal.service.PrestataireService;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    private final EquipementService equipementService;
    private final EvaluationService evaluationService;
    private final PrestataireService prestataireService;


    public IncidentController(IncidentService incidentService, EquipementService equipementService, EvaluationService evaluationService, PrestataireService prestataireService) {
        this.incidentService = incidentService;
        this.equipementService = equipementService;
        this.evaluationService = evaluationService;
        this.prestataireService = prestataireService;
    }

    @GetMapping
    public String getAllIncidents(Model model) {
        List<Incident> liste = incidentService.getIncidentNonResolu();
        model.addAttribute("incidents", liste);
        return "incident/liste";
    }

    @GetMapping("/historique")
    public String getOldIncidents(Model model) {
        List<Incident> liste = incidentService.findByStatutIncidentOrderByDateResolutionDesc();
        model.addAttribute("incidents", liste);
        return "incident/historique";
    }

    @GetMapping("/{id}")
    public String getIncidentById(@PathVariable("id") int id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'incident dont l'id est " + id + " n'existe pas"));
        model.addAttribute("evaluation", evaluationService.getEvaluationByIncident(id));
        model.addAttribute("incident", incident);
        return "incident/detail";
    }

    @GetMapping("/nouveau")
    public String newIncident(Model model) {
        List<Equipement> liste = equipementService.getEquipements();

        model.addAttribute("incident", new Incident());
        model.addAttribute("equipements", liste);
        return "incident/form";
    }

    @PostMapping
    public String declarerIncident(@ModelAttribute Incident incident) {
        incidentService.addIncident(incident);
        return "redirect:/incidents";
    }


    @PostMapping("/{id}/evaluer")
    public String evaluer(@PathVariable Integer id, @RequestParam int note, @RequestParam(required = false) String commentaire) {
        evaluationService.evaluer(id, note, commentaire);
        return "redirect:/incidents/" + id;
    }

    @GetMapping("/{id}/affecter")
    public String afficherFormulaireAffectation(@PathVariable("id") Integer id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident invalide : " + id));

        List<Prestataire> prestataires = prestataireService.getAllPrestataires();

        model.addAttribute("incident", incident);
        model.addAttribute("prestataires", prestataires);

        return "incident/affecter";
    }

    @PostMapping("/{id}/affecter")
    public String affecterPrestataire(@PathVariable("id") Integer id, @RequestParam("idPrestataire") Integer idPrestataire) {
        incidentService.affecterPrestataire(id, idPrestataire);

        return "redirect:/incidents/" + id;
    }

    @GetMapping("/{id}/resoudre")
    public String resoudreIncident(@PathVariable("id") Integer id) {
        incidentService.resoudreIncident(id);

        return "redirect:/incidents/" + id;
    }

    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable("id") Integer id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident invalide : " + id));

        List<Equipement> equipements = equipementService.getEquipements();

        model.addAttribute("incident", incident);
        model.addAttribute("equipements", equipements);

        return "incident/modifier";
    }

    @PostMapping("/{id}/modifier")
    public String modifierIncident(@PathVariable("id") Integer id, @ModelAttribute Incident incident) {
        incidentService.modifierIncident(id, incident);
        return "redirect:/incidents/" + id;
    }
}
