package org.isj.traitementmetier.entites;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "annee_academique")
public class AnneeAcademique extends Securite implements Serializable {

    @Column(name = "date_debut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateDebut;

    @Column(name = "date_cloture")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCloture;

    @OneToMany(mappedBy = "anneeAcademique",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <Semestre> semestres = new ArrayList<>();

    public AnneeAcademique(String libelle, String description, Date dateDebut, Date dateCloture) {
        super(libelle, description);
        this.dateDebut = dateDebut;
        this.dateCloture = dateCloture;
    }

    public AnneeAcademique(){}

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateCloture() {
        return dateCloture;
    }

    public void setDateCloture(Date dateCloture) {
        this.dateCloture = dateCloture;
    }

    public List<Semestre> getSemestres() {
        return semestres;
    }

    public void setSemestres(List<Semestre> semestres) {
        this.semestres = semestres;
    }

    @Override
    public String getLibelle(){
        return dateDebut +
                "-" + dateCloture;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getDateDebut(), getDateCloture(), getSemestres());
    }

    @Override
    public String toString() {
        return "AnneeAcademiqueFacade{" +
                "dateDebut=" + dateDebut +
                ", dateCloture=" + dateCloture +
                ", semestres=" + semestres.toString() +
                "} " + super.toString();
    }
}
