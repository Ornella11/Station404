package station404.projetFinal.exception;

public class StationIntrouvableException extends ExceptionMetier {
    public StationIntrouvableException(Integer id) {
        super("La station " + id + " n'existe pas");
    }
}
