package station404.projetFinal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ConfigSecurite {
    // le mot de passe est transmis sous forme hachée
    @Bean
    PasswordEncoder encodeurMotDePasse() {
        return new BCryptPasswordEncoder();
    }

    // chaîne de filtre pour les requêtes HTTP
    @Bean
    SecurityFilterChain chaineFiltresSecurite(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(autorisations -> autorisations
                        .requestMatchers("/css/**", "/images/**", "/js/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(connexion -> connexion
                        .loginPage("/login")
                        .permitAll())
                .logout(deconnexion -> deconnexion
                        .logoutSuccessUrl("/login?logout")
                        .permitAll());
        return http.build();
    }
}
