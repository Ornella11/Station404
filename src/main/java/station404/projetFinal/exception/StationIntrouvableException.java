package station404.projetFinal.exception;

public class StationIntrouvableException extends RessourceIntrouvableException {
    public StationIntrouvableException(Integer id) {
        super("La station " + id + " n'existe pas");
    }
}
