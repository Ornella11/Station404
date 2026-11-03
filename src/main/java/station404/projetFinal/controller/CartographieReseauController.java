package station404.projetFinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import station404.projetFinal.service.StationService;

@Controller
public class CartographieReseauController {
    private final StationService stationService;

    public CartographieReseauController(StationService stationService) {
        this.stationService = stationService;
    }

    @GetMapping("/cartographie")
    public String afficherCartographie(Model model){
        model.addAttribute("etatsStations", stationService.calculerEtatsStations());
        return "cartographie";
    }
}
