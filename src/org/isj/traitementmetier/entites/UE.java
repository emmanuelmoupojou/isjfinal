package org.isj.traitementmetier.entites;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name ="UE")

public class UE extends Securite implements Serializable {
    public enum Statut {
        ACTIVE, NONACTIVE
    }

    @Column(name = "code_ue")
    private String codeUE;

    @Column(name = "statut",nullable = false)
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne
    @JoinColumn(name = "module")
    private Module module;

    @ManyToOne
    @JoinColumn(name = "niveau")
    private Niveau niveau;

    @ManyToOne
    @JoinColumn(name = "specialite")
    private Specialite specialite;

    @OneToMany(mappedBy = "ue",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <Enseignement> enseignements=new ArrayList<>();

    public UE(String libelle, String description, String codeUE, Statut statut, Module module, Niveau niveau, Specialite specialite) {
        super(libelle, description);
        this.codeUE = codeUE;
        this.statut = statut;
        this.module = module;
        this.niveau = niveau;
        this.specialite = specialite;
    }

    public UE(){}

    public String getCodeUE() {
        return codeUE;
    }

    public void setCodeUE(String code_ue) {
        this.codeUE = code_ue;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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

    public List<Enseignement> getEnseignements() {
        return enseignements;
    }

    public void setEnseignements(List<Enseignement> enseignements) {
        this.enseignements = enseignements;
    }

    @Override
    public String getLibelle(){
        return codeUE + "-" + specialite.getLibelle() + "-" + module.getLibelle() + "-" + niveau.getLibelle() +"-"+statut;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCodeUE(), getStatut());
    }

    @Override
    public String toString() {
        return "UE{" +
                "code_ue='" + codeUE + '\'' +
                ", statut=" + statut +
                ", module=" + module.toString() +
                ", niveau=" + niveau.toString() +
                ", specialite=" + specialite.toString() +
                "} " + super.toString();
    }
}
