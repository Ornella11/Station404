package station404.projetFinal.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import station404.projetFinal.repository.EvaluationRepository;
import station404.projetFinal.repository.IncidentRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class StatistiqueService {

    private final IncidentRepository incidentRepository;
    private final EvaluationRepository evaluationRepository;

    public StatistiqueService(IncidentRepository incidentRepository,
                              EvaluationRepository evaluationRepository) {
        this.incidentRepository = incidentRepository;
        this.evaluationRepository = evaluationRepository;
    }

    public Map<String, Number> incidentsParMois() {
        Map<String, Number> serie = new LinkedHashMap<>();
        for (Object[] ligne : incidentRepository.compterParMois()) {
            serie.put(libelleMois(((Number) ligne[0]).intValue(), ((Number) ligne[1]).intValue()),
                    (Number) ligne[2]);
        }
        return serie;
    }

    public Map<String, Number> incidentsParStation() {
        Map<String, Number> serie = new LinkedHashMap<>();
        for (Object[] ligne : incidentRepository.compterParStation()) {
            serie.put((String) ligne[0], (Number) ligne[1]);
        }
        return serie;
    }

    public Map<String, Number> delaiMoyenParMois() {
        List<Object[]> lignes = incidentRepository.trouverDatesIncidentsResolus();

        Map<YearMonth, Double> moyennes = lignes.stream().collect(Collectors.groupingBy(
                ligne -> YearMonth.from((LocalDateTime) ligne[1]),
                TreeMap::new,
                Collectors.averagingDouble(ligne ->
                        Duration.between((LocalDateTime) ligne[0], (LocalDateTime) ligne[1]).toMinutes() / 60.0)));

        Map<String, Number> serie = new LinkedHashMap<>();
        moyennes.forEach((mois, moyenne) ->
                serie.put(libelleMois(mois.getYear(), mois.getMonthValue()), arrondi(moyenne)));
        return serie;
    }

    public Map<String, Number> noteMoyenneParPrestataire() {
        Map<String, Number> serie = new LinkedHashMap<>();
        for (Object[] ligne : evaluationRepository.noteMoyenneParPrestataire()) {
            serie.put((String) ligne[0], arrondi(((Number) ligne[1]).doubleValue()));
        }
        return serie;
    }

    private static String libelleMois(int annee, int mois) {
        return String.format("%02d/%d", mois, annee);
    }

    private static double arrondi(double valeur) {
        return Math.round(valeur * 10) / 10.0;
    }
}