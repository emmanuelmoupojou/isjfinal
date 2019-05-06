package org.isj.traitementmetier.entites;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "filiere")

public class Filiere extends Securite implements Serializable {
    
    @OneToMany(mappedBy = "filiere",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List<Specialite> specialites = new ArrayList<>();

    public Filiere(String libelle, String description) {
        super(libelle, description);
    }

    public Filiere() {
    }

    public List<Specialite> getSpecialites() {
        return specialites;
    }

    public void setSpecialites(List<Specialite> specialites) {
        this.specialites = specialites;
    }



    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Filiere{" +
                "specialite=" + specialites.toString()+
                "} " + super.toString();
    }
}
