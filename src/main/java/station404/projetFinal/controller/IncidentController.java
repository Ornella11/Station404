package station404.projetFinal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import station404.projetFinal.model.*;
import station404.projetFinal.service.EquipementService;
import station404.projetFinal.service.EvaluationService;
import station404.projetFinal.service.IncidentService;
import station404.projetFinal.service.PrestataireService;

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

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @GetMapping
    public String getAllIncidents(Model model) {
        List<Incident> liste = incidentService.getIncidentNonResolu();
        model.addAttribute("incidents", liste);
        return "incident/liste";
    }

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @GetMapping("/historique")
    public String getOldIncidents(Model model) {
        List<Incident> liste = incidentService.findByStatutIncidentOrderByDateResolutionDesc();
        model.addAttribute("incidents", liste);
        return "incident/historique";
    }

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @GetMapping("/{id}")
    public String getIncidentById(@PathVariable("id") int id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'incident dont l'id est " + id + " n'existe pas"));
        model.addAttribute("evaluation", evaluationService.getEvaluationByIncident(id));
        model.addAttribute("incident", incident);
        return "incident/detail";
    }

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @GetMapping("/nouveau")
    public String newIncident(Model model) {
        List<Equipement> liste = equipementService.getEquipements();

        model.addAttribute("incident", new Incident());
        model.addAttribute("equipements", liste);
        return "incident/form";
    }

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @PostMapping
    public String declarerIncident(@ModelAttribute Incident incident) {
        incidentService.addIncident(incident);
        return "redirect:/incidents";
    }

    // Agent: Accès complet / Admin: Lecture seule pour l'évaluation (selon la matrice)
    @PreAuthorize("hasRole('AGENT')")
    @PostMapping("/{id}/evaluer")
    public String evaluer(@PathVariable Integer id, @RequestParam int note, @RequestParam(required = false) String commentaire) {
        evaluationService.evaluer(id, note, commentaire);
        return "redirect:/incidents/" + id;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}/affecter")
    public String afficherFormulaireAffectation(@PathVariable("id") Integer id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident invalide : " + id));

        List<Prestataire> prestataires = prestataireService.getAllPrestataires();

        model.addAttribute("incident", incident);
        model.addAttribute("prestataires", prestataires);

        return "incident/affecter";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/affecter")
    public String affecterPrestataire(@PathVariable("id") Integer id, @RequestParam("idPrestataire") Integer idPrestataire) {
        incidentService.affecterPrestataire(id, idPrestataire);

        return "redirect:/incidents/" + id;
    }

    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    @PostMapping("/{id}/resoudre")
    public String resoudreIncident(@PathVariable("id") Integer id) {
        incidentService.resoudreIncident(id);

        return "redirect:/incidents/" + id;
    }

    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    @GetMapping("/{id}/modifier")
    public String afficherFormulaireModification(@PathVariable("id") Integer id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("Incident invalide : " + id));

        List<Equipement> equipements = equipementService.getEquipements();

        model.addAttribute("incident", incident);
        model.addAttribute("equipements", equipements);

        return "incident/modifier";
    }

    @PreAuthorize("hasAnyRole('AGENT','ADMIN')")
    @PostMapping("/{id}/modifier")
    public String modifierIncident(@PathVariable("id") Integer id, @ModelAttribute Incident incident) {
        incidentService.modifierIncident(id, incident);
        return "redirect:/incidents/" + id;
    }
}