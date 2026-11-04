package station404.projetFinal.exception;

public class PrestataireIntrouvableException extends RessourceIntrouvableException {
    public PrestataireIntrouvableException(Integer id) {
        super("Le prestataire " + id + " n'existe pas");
    }
}

