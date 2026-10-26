package station404.projetFinal.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "evaluation")
public class Evaluation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluation", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_incident", nullable = false, unique = true)
    private Incident incident;

    @Column(name = "note", nullable = false, columnDefinition = "TINYINT")
    private int note;

    @Column(name = "commentaire", length = 500)
    private String commentaire;

    @Column(name = "date_evaluation", nullable = false)
    private LocalDate dateEvaluation;

    protected Evaluation() {
    }

    public Evaluation( Incident incident, int note, String commentaire, LocalDate dateEvaluation) {
        this.incident = incident;
        this.note = note;
        this.commentaire = commentaire;
        this.dateEvaluation = dateEvaluation;
    }

    public Integer getId() {
        return id;
    }

    public int getNote() {
        return note;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public LocalDate getDateEvaluation() {
        return dateEvaluation;
    }
}
