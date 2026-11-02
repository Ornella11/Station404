package station404.projetFinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import station404.projetFinal.service.EquipementService;
import station404.projetFinal.service.StationService;


@Controller
public class StationController {
    private final StationService stationService;
    private final EquipementService equipementService;

    public StationController(StationService stationService, EquipementService equipementService) {
        this.stationService = stationService;
        this.equipementService = equipementService;
    }

    @GetMapping("/stations")
    public String afficherStations(Model model){
        model.addAttribute("stations", stationService.listerStations());
        return "stations";
    }

    @GetMapping("/stations/{id}")
    public String afficherStation(@PathVariable Integer id, Model model){
        model.addAttribute("station",  stationService.chercherStation(id));
        model.addAttribute("equipements", equipementService.listerEquipements(id));
        return "station";
    }

}

