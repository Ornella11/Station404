package station404.projetFinal.model;
import jakarta.persistence.*;

@Entity
@Table(name = "utilisateur")
public class Utilisateur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utilisateur", nullable = false)
    private Integer id;

    @Column(name = "login", length = 50, nullable = false)
    private String login;

    @Column(name = "mot_de_passe", length = 100, nullable = false)
    private String motDePasse;

    @Column(name = "prenom", length = 100, nullable = false)
    private String prenom;

    @Enumerated(EnumType.STRING)
    @Column(name = "profil", nullable = false)
    private ProfilUtilisateur profilUtilisateur;

    protected Utilisateur() {

    }

    public Integer getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getPrenom() {
        return prenom;
    }

    public ProfilUtilisateur getProfilUtilisateur() {
        return profilUtilisateur;
    }
}
