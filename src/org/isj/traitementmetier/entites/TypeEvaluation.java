package org.isj.traitementmetier.entites;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name ="type_evaluation")


public class TypeEvaluation extends Securite implements Serializable {

    @Column(name ="pourcentage")
    private float pourcentage;

    @ManyToOne
    @JoinColumn(name="enseignement")
    private Enseignement enseignement;

    @OneToMany(mappedBy = "typeEvaluation",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <Evaluation> evaluations = new ArrayList<>();

    public TypeEvaluation(String libelle, String description, float pourcentage, Enseignement enseignement) {
        super(libelle, description);
        this.pourcentage = pourcentage;
        this.enseignement = enseignement;
    }

    public TypeEvaluation() {
    }

    public float getPourcentage() {
        return pourcentage;
    }

    public void setPourcentage(float pourcentage) {
        this.pourcentage = pourcentage;
    }

    public Enseignement getEnseignement() {
        return enseignement;
    }

    public void setEnseignement(Enseignement enseignement) {
        this.enseignement = enseignement;
    }



    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    @Override
    public String getLibelle(){
        return enseignement.getLibelle() + "-" + pourcentage;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPourcentage(), getEnseignement());
    }

    @Override
    public String toString() {
        return "TypeEvaluation{" +
                "pourcentage=" + pourcentage +
                ", enseignement=" + enseignement.toString() +
                ", evaluations=" + evaluations.toString() +
                "} " + super.toString();
    }
}

