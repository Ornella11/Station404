package station404.projetFinal.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import station404.projetFinal.model.Incidents;
import station404.projetFinal.service.IncidentService;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@Tag(name = "Incidents", description = "Consultation et résolution des incidents")
public class IncidentApiController {

    private final IncidentService incidentService;

    public IncidentApiController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @GetMapping
    @Operation(summary = "Lister tous les incidents, du plus récent au plus ancien")
    public List<Incidents> listerIncidents() {
        return incidentService.listerIncidentsAvecDetails().stream()
                .map(Incidents::depuis)
                .toList();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Consulter le détail d'un incident")
    @ApiResponse(responseCode = "200", description = "Incident trouvé")
    @ApiResponse(responseCode = "404", description = "Incident inexistant")
    public Incidents consulterIncident(@PathVariable Integer id) {
        return Incidents.depuis(incidentService.chercherIncidentAvecDetails(id));
    }

    @PatchMapping("/{id}/resolution")
    @Operation(summary = "Déclarer un incident comme résolu (F8)")
    @ApiResponse(responseCode = "200", description = "Incident résolu")
    @ApiResponse(responseCode = "404", description = "Incident inexistant")
    @ApiResponse(responseCode = "409", description = "Incident déjà résolu")
    public Incidents resoudreIncident(@PathVariable Integer id) {
        incidentService.resoudreIncident(id);
        return Incidents.depuis(incidentService.chercherIncidentAvecDetails(id));
    }
}
