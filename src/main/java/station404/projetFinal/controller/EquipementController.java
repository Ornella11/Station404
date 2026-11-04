package station404.projetFinal.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import station404.projetFinal.service.EquipementService;

@Controller
public class EquipementController {
    private final EquipementService equipementService;

    public EquipementController(EquipementService equipementService) {
        this.equipementService = equipementService;
    }

    @PreAuthorize("hasAnyRole('AGENT', 'ADMIN')")
    @GetMapping("/equipements/{id}")
    public String afficherEquipements(@PathVariable Integer id, Model model) {
        model.addAttribute("equipement", equipementService.chercherEquipement(id));
        return "equipement";
    }
}