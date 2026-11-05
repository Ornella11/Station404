package station404.projetFinal.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "station404.projetFinal.controller")
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ErreursApi {

    private static final Logger log = LoggerFactory.getLogger(ErreursApi.class);

    @ExceptionHandler(RessourceIntrouvableException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErreurApi traiterRessourceIntrouvable(RessourceIntrouvableException e) {
        return construireErreur(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IncidentException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErreurApi traiterTransitionInterdite(IncidentException e) {
        return construireErreur(e, HttpStatus.CONFLICT);
    }

    private ErreurApi construireErreur(ExceptionMetier e, HttpStatus statut) {
        log.warn("Erreur métier API ({}) : {}", statut.value(), e.getMessage());
        return new ErreurApi(statut.value(), e.getMessage());
    }
}