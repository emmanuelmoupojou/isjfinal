package org.isj.traitementmetier.entites;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "Niveau")

public class Niveau extends Securite implements Serializable {

    @Column(name = "numero")
    private int numero;

    @OneToMany(mappedBy = "niveau",fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <Classe> classes = new ArrayList<>();

    @OneToMany(mappedBy = "niveau",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <UE> ues = new ArrayList<>();

    public Niveau(String libelle, String description, int numero) {
        super(libelle, description);
        this.numero = numero;
    }

    public Niveau(int numero) {
        this.numero = numero;
    }

    public Niveau(){}

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }

    public List<UE> getUes() {
        return ues;
    }

    public void setUes(List<UE> ues) {
        this.ues = ues;
    }

    @Override
    public String getLibelle(){
        return ""+numero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLibelle(), numero);
    }

    @Override
    public String toString() {
        return "Niveau{" +
                "numero=" + numero +
                ", classes=" + classes.toString() +
                ", ues=" + ues.toString() +
                "} " + super.toString();
    }
}
