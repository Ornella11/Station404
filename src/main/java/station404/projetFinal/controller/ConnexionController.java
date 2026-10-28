package station404.projetFinal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConnexionController {

    @GetMapping("/login")
    public String afficherPageConnexion() {
        return "login";
    }
}
