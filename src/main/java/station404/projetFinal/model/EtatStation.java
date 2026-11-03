package station404.projetFinal.model;

public record EtatStation(
        Integer id, String nom, double latitude, double longitude,Long nbIncidentsActifs
) {
}
