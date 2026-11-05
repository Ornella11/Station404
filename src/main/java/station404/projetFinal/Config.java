package station404.projetFinal;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.time.Clock;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class Config {
    @Bean
    PasswordEncoder encodeurMotDePasse() {
        return new BCryptPasswordEncoder();
    }

    // chaîne de filtre pour les requêtes HTTP
    @Bean
    SecurityFilterChain chaineFiltresSecurite(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(autorisations -> autorisations
                        .requestMatchers("/css/**", "/images/**", "/js/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated())
                .formLogin(connexion -> connexion
                        .loginPage("/login")
                        .permitAll())
                .logout(deconnexion -> deconnexion
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .exceptionHandling(handling -> handling
                    .accessDeniedPage("/403")
        );

        return http.build();
    }

    // pour l'heure courante (pour la date d'ouverture d'un incident)
    @Bean
    Clock clock() {
        return Clock.systemDefaultZone();
    }
}
