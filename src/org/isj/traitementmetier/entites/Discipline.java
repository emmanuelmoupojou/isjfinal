package org.isj.traitementmetier.entites;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;


@Entity
@XmlRootElement
@Table(name = "discipline")
public class Discipline extends Securite implements Serializable {
    @ManyToOne
    @JoinColumn(name="etudiant")
    private Etudiant etudiant;

    @ManyToOne
    @JoinColumn(name = "semestre")
    private Semestre semestre;

    @Column(name="nb_heures_absences")
    private int nbHeures;

    @Column(name="nb_retards")
    private int nbRetards;

    public Discipline(String libelle, String description, Etudiant etudiant, Semestre semestre, int nbHeures, int nbRetards) {
        super(libelle, description);
        this.etudiant = etudiant;
        this.semestre = semestre;
        this.nbHeures = nbHeures;
        this.nbRetards = nbRetards;
    }

    public Discipline(){}

    public Etudiant getEtudiant() {
        return etudiant;
    }

    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }

    public Semestre getSemestre() {
        return semestre;
    }

    public void setSemestre(Semestre semestre) {
        this.semestre = semestre;
    }

    public int getNbHeures() {
        return nbHeures;
    }

    public void setNbHeures(int nbHeures) {
        this.nbHeures = nbHeures;
    }

    public int getNbRetards() {
        return nbRetards;
    }

    public void setNbRetards(int nbRetards) {
        this.nbRetards = nbRetards;
    }

    @Override
    public String getLibelle(){
        return etudiant.getLibelle()+
                "-" + semestre.getLibelle() + "-"+nbHeures+ "-" +nbRetards;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEtudiant(), getSemestre(), getNbHeures(), getNbRetards());
    }

    @Override
    public String toString() {
        return "Discipline{" +
                "etudiant=" + etudiant.toString() +
                ", semestre=" + semestre.toString() +
                ", nbHeures=" + nbHeures +
                ", nbRetards=" + nbRetards +
                "} " ;
    }
}
