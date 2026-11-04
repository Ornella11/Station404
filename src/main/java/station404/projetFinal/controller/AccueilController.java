package station404.projetFinal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import station404.projetFinal.model.Incident;
import station404.projetFinal.service.*;

import java.util.List;

@Controller
public class AccueilController {
    private final AccueilService accueilService;

    public AccueilController(AccueilService accueilService) {
        this.accueilService = accueilService;
    }

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @GetMapping("/")
    public String afficherIncidentNonResolu(Model model) {
        model.addAttribute("incidents", accueilService.getIncidentsAvecUrgence());
        return "accueil";
    }




}
