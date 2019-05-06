package org.isj.traitementmetier.entites;;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "Module")

public class Module extends Securite implements Serializable {

    @OneToMany(mappedBy = "module",fetch = FetchType.LAZY,cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List<UE> ues = new ArrayList<>();

    public List<UE> getUes() {
        return ues;
    }

    public void setUes(List<UE> ues) {
        this.ues = ues;
    }


    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }

    @Override
    public String toString() {
        return "Module{" +
                "ues=" + ues.toString() +
                "} " + super.toString();
    }

    public Module(String libelle, String description) {
        super(libelle, description);
    }

    public Module(){}
}
