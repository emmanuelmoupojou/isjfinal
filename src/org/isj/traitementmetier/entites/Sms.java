package org.isj.traitementmetier.entites;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@XmlRootElement
@Table(name = "Sms")
public class Sms extends Message implements Serializable {
    @Override
    public String toString() {
        return "Sms{} " + super.toString();
    }


    public Sms(String libelle, String description, String contenu, String destinataire, String emetteur) {
        super(libelle, description, contenu, destinataire, emetteur);
    }

    public Sms() {
    }



    @Override
    public String getLibelle(){
        return super.getLibelle();
    }

}
