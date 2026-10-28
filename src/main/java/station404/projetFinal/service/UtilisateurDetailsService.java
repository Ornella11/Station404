package station404.projetFinal.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import station404.projetFinal.repository.UtilisateurRepository;
import org.springframework.security.core.userdetails.UserDetailsService;


@Service
public class UtilisateurDetailsService implements UserDetailsService{
    private final UtilisateurRepository utilisateurRepository;
    public UtilisateurDetailsService(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername (String login) {
        return utilisateurRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("Aucun utilisateur avec le login : " + login));
    }

}
