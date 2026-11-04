package station404.projetFinal.exception;

public class EquipementIntrouvableException extends RessourceIntrouvableException {
    public EquipementIntrouvableException(Integer id) {
        super("L'équipement " + id + " n'existe pas");
    }
}
