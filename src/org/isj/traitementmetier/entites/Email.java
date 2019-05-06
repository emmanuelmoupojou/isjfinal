package org.isj.traitementmetier.entites;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Objects;

@Entity
@XmlRootElement
@Table(name = "email")

public class Email extends Message implements Serializable {
    @Column(name = "objet")
    private String objet;

    public Email(String libelle, String description, String contenu, String destinataire, String emetteur, String objet) {
        super(libelle, description, contenu, destinataire, emetteur);
        this.objet = objet;
    }

    public Email(){}

    public String getObjet() {
        return objet;
    }

    public void setObjet(String objet) {
        this.objet = objet;
    }

    @Override
    public String getLibelle(){
        return objet +"-"+ super.getLibelle();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getObjet());
    }

    @Override
    public String toString() {
        return "Email{" +
                "objet='" + objet + '\'' +
                "} " + super.toString();
    }
}
