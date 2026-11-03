package station404.projetFinal.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import station404.projetFinal.model.Equipement;
import station404.projetFinal.model.Incident;
import station404.projetFinal.service.EquipementService;
import station404.projetFinal.service.IncidentService;

import java.util.List;

@Controller
@RequestMapping("/incidents")
public class IncidentController {
    private final IncidentService incidentService;
    private final EquipementService equipementService;


    public IncidentController(IncidentService incidentService, EquipementService equipementService) {
        this.incidentService = incidentService;
        this.equipementService = equipementService;
    }

    @GetMapping
    public String getAllIncidents(Model model) {
        List<Incident> liste = incidentService.getIncidents();
        model.addAttribute("incidents", liste);
        return "incident/liste";
    }

    @GetMapping("/{id}")
    public String getIncidentById(@PathVariable("id") int id, Model model) {
        Incident incident = incidentService.getIncidentById(id)
                .orElseThrow(() -> new IllegalArgumentException("L'incident dont l'id est " + id + " n'existe pas"));
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
}
