package station404.projetFinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import station404.projetFinal.service.StatistiqueService;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/statistiques")
public class StatistiqueController {

    private final StatistiqueService statistiqueService;

    public StatistiqueController(StatistiqueService statistiqueService) {
        this.statistiqueService = statistiqueService;
    }

    @GetMapping
    public String getStatistiques(Model model) {
        ajouter(model, "incidentsParMois",    statistiqueService.incidentsParMois());
        ajouter(model, "incidentsParStation", statistiqueService.incidentsParStation());
        ajouter(model, "delaiParMois",        statistiqueService.delaiMoyenParMois());
        ajouter(model, "notesPrestataires",   statistiqueService.noteMoyenneParPrestataire());
        return "statistiques";
    }

    private void ajouter(Model model, String nom, Map<String, Number> serie) {
        model.addAttribute(nom + "Libelles", new ArrayList<>(serie.keySet()));
        model.addAttribute(nom + "Valeurs", new ArrayList<>(serie.values()));
    }
}