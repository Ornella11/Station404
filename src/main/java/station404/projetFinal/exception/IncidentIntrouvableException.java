package station404.projetFinal.exception;

public class IncidentIntrouvableException extends RessourceIntrouvableException {
    public IncidentIntrouvableException(Integer id) {
        super("L'incident " + id + " n'existe pas");
    }
}
