package org.isj.traitementmetier.entites;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "anonymat")
public class Anonymat extends Securite implements Serializable {

    @Column(name = "numero_anonymat")
    private int numeroAnonymat;

    @OneToOne
    @JoinColumn(name = "note")
    private Note note;

    @ManyToOne
    @JoinColumn(name = "evaluation")
    private Evaluation evaluation;

    public Anonymat(String libelle, String description, int numeroAnonymat, Note note, Evaluation evaluation) {
        super(libelle, description);
        this.numeroAnonymat = numeroAnonymat;
        this.note = note;
        this.evaluation = evaluation;
    }

    public Anonymat(){}

    public int getNumeroAnonymat() {
        return numeroAnonymat;
    }

    public void setNumeroAnonymat(int numeroAnonymat) {
        this.numeroAnonymat = numeroAnonymat;
    }

    public Note getNote() {
        return note;
    }

    public void setNote(Note note) {
        this.note = note;
    }

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }

    @Override
    public String getLibelle(){
        return numeroAnonymat +
                "-" + note.getLibelle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNumeroAnonymat(), getNote(), getEvaluation());
    }

    @Override
    public String toString() {
        return "Anonymat{" +
                "numeroAnonymat=" + numeroAnonymat +
                ", note=" + note +
                ", evaluation=" + evaluation.toString() +
                "} " + super.toString();
    }
}
