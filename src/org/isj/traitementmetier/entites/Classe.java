package org.isj.traitementmetier.entites;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@XmlRootElement
@Entity
@Table(name = "classe")
public class Classe extends Securite implements Serializable {

    @OneToMany(mappedBy = "classe", fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Candidat> candidats = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "niveau")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "specialite")
    private Specialite specialite;

    public Classe(String libelle, String description) {
        super(libelle, description);
    }

    public Classe(){}
    public List<Candidat> getCandidats() {
        return candidats;
    }

    public void setCandidats(List<Candidat> candidats) {
        this.candidats = candidats;
    }

    public Niveau getNiveau() {
        return niveau;
    }

    public void setNiveau(Niveau niveau) {
        this.niveau = niveau;
    }

    public Specialite getSpecialite() {
        return specialite;
    }

    public void setSpecialite(Specialite specialite) {
        this.specialite = specialite;
    }

    @Override
    public String getLibelle(){
        return this.niveau.getLibelle() + "-" + this.specialite.getLibelle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNiveau(), getSpecialite());
    }

    @Override
    public String toString() {
        return specialite.getLibelle()+"-"+niveau.getNumero();
    }
}
