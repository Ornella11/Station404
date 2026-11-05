package station404.projetFinal.exception;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.slf4j.Logger;

@ControllerAdvice
public class ErreursWeb {
    private static final Logger log = LoggerFactory.getLogger(ErreursWeb.class);
    private static final String VUE_ERREUR = "error";

    @ExceptionHandler(RessourceIntrouvableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String traiterRessourceIntrouvable(RessourceIntrouvableException e, Model model) {
        return afficherErreur(e, HttpStatus.NOT_FOUND, model);
    }

    @ExceptionHandler(IncidentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String traiterTransitionInterdite(IncidentException e, Model model) {
        return afficherErreur(e, HttpStatus.CONFLICT, model);
    }

    private String afficherErreur(ExceptionMetier e, HttpStatus statut, Model model) {
        log.warn("Erreur métier ({}) : {}", statut.value(), e.getMessage());
        model.addAttribute("statut", statut.value());
        model.addAttribute("message", e.getMessage());
        return VUE_ERREUR;
    }
}
