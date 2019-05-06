package org.isj.traitementmetier.entites;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;

@Entity
@XmlRootElement
@Table(name = "role")
public class Role extends Securite implements Serializable {

    @OneToMany(mappedBy = "role",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <Droit> droits = new ArrayList<>();

    @ManyToMany(mappedBy = "roles",cascade = {CascadeType.REFRESH,CascadeType.PERSIST,CascadeType.MERGE})
    private List <Utilisateur> utilisateurs = new ArrayList<>();

    public Role(String libelle, String description) {
        super(libelle, description);
    }

    public Role() {
    }

    public List<Droit> getDroits() {
        return droits;
    }

    public void setDroits(List<Droit> droits) {
        this.droits = droits;
    }

    public List<Utilisateur> getUtilisateurs() {
        return utilisateurs;
    }

    public void setUtilisateurs(List<Utilisateur> utilisateurs) {
        this.utilisateurs = utilisateurs;
    }


    @Override
    public String toString() {
        return "Role{" +
                "droits=" + droits.toString() +
                ", utilisateurs=" + utilisateurs.toString() +
                "} " + super.toString();
    }
}
