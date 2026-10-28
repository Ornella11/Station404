package station404.projetFinal.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import station404.projetFinal.model.Utilisateur;

@ControllerAdvice
public class UtilisateurConnecte {

    @ModelAttribute("utilisateurConnecte")
    public Utilisateur utilisateurConnecte(@AuthenticationPrincipal Utilisateur utilisateur) {
        return utilisateur;
    }
}