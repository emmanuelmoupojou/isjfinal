package org.isj.traitementmetier;

/**
 * importation des classes
 */
import org.isj.traitementmetier.entites.*;
import org.isj.traitementmetier.facade.*;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * cette classe contient toutes les fonctions demandées par les autres modules
 *
 * @auteur traitement metier
 */

public class Isj {
    public static Utilisateur utilisateurCourant = new Utilisateur();


    public static void main(String[] args) {

/*
        Securite user = new Securite();

        new Isj().persist(user);
        Date date = new Date();
       /* Filiere filiere= new Filiere("Licence Professionnelle","Concepteur/Développeur d'applications pour l'économie numérique") ;
        Utilisateur utilisateur = new Utilisateur("second utilisateur","pourra jouer des roles","NDOUMOU","Andre","ndoumouandre@gmail.com",655841232,date, Personne.Sexe.MASCULIN, Personne.Statut.ACTIVE,"andre","1234");
        UtilisateurFacade uf = new UtilisateurFacade();
        Role role = new Role("Enregistreur","pourra lire et ecrire les données");
        RoleFacade rf = new RoleFacade();
        rf.create(role);
        new Isj().insert(utilisateur,role);
        uf.create(utilisateur);
        rf.create(role);
        FiliereFacade ff=new FiliereFacade();
        ff.create(filiere);*/
       /* UtilisateurFacade uf = new UtilisateurFacade();
        Utilisateur utilisateur = uf.find(new Long(1));

        //Pour affecter un utilisateur à un role
        new Isj().affecterUtilisateurRole(utilisateur,role);
        uf.merge(utilisateur);
        rf.merge(role);*/
       /* UtilisateurFacade uf = new UtilisateurFacade();
        Utilisateur utilisateur = uf.find(new Long(1));
        List<Role> roles = utilisateur.getRoles();
        for (int i = 0; i < roles.size(); i++) {
            System.out.println(roles.get(i).getLibelle());
            List<Droit> droits = roles.get(i).getDroits();
            for (int j = 0; j < droits.size(); j++) {
                System.out.println(droits.get(j).toString());
            }

        }*/

       //test renvoyer login en fonction du numero
        /*
        try {
            Utilisateur u = new Isj().renvoyerLoginTelephone(691063708);
            System.out.println(u.getNom());
        }catch (NoResultException n){
            System.out.println(n.getMessage());
        }*/

        //test renvoyer login en fonction du mail
        /*
        try {
            Utilisateur u = new Isj().renvoyerLoginEmail("ongono@gmail.com");
            System.out.println(u.getNom());
        }catch (NoResultException n){
            System.out.println(n.getMessage());
        }*/

        //test affichage des champs

       /* Utilisateur u = new Utilisateur();
        List <String> champ = new Isj().renvoyerChamp(Utilisateur.class);
        System.out.println(champ.get(0));*/

        //test authentification
        /*
        try {
            Utilisateur utilisateur = new Isj().authentification("yannick", "123456");
            System.out.println(utilisateur.getDateCreation());
        }catch (NoResultException n){
            System.out.println(n.getMessage());
        }*/

        //test isTelephone in BD
        /*
        Boolean u = new Isj().isTelephoneInBD(691063708);
        System.out.println(u);*/

        //test isEmail in BD
        /*
        Boolean u = new Isj().isEmailInBD("tapigue@gmail.com");
        System.out.println(u);*/

        //test sauvegarder email recu en bd

        //Email email = new EmailFacade().find(new Long(151));
        /*
        try {
            Candidat candidat = new Isj().retrouverCandidatEmail("anthonyfouda@gmai.com");
            Email email = new Email("test","test","Ceci est u test","isj@gmail.com","anthonyfouda@gmail.com","test");
            new EmailFacade().create(email);
            String u = new Isj().sauvegarderEmailSucces(email, candidat);
            System.out.println(u);
        }catch (NoResultException n){
            n.printStackTrace();
        }*/


        //test de l'enregistrement du candidat
        /*
        Classe classe = new ClasseFacade().find(new Long(4));
        new CandidatFacade().enregistrer("mon candidat","le candidat","Mfomen","Elvira","mfomnndafeu@gmail.com",678451263,new Date(), Personne.Sexe.FEMININ, Personne.Statut.ACTIVE,"Mfomenita","Ndafeu",654748521,697451821,"businessman","Menagere","Ouest","Ngoa",classe);*/

        String s = "test237";
        String q= s.substring(3);
        System.out.println(q);

        //test sauvegarder sms
        /*
        try {
            Candidat candidat = new Isj().retrouverCandidatSms(691063708);
            SmsFacade smsFacade = new SmsFacade();
            Sms sms = new Sms("", "", "Bon", String.valueOf(candidat.getTelephone()), "isj@gmail.com");
            smsFacade.create(sms);
            String result = new Isj().sauvegarderSmsEchec(sms, candidat);
        }catch (NoResultException n){
            n.printStackTrace();
        }*/
        //test de la bd

        /*
        EtudiantFacade etudiantFacade = new EtudiantFacade();
        List <Etudiant> etudiants = etudiantFacade.findAll();
        System.out.println(etudiants.get(0).getNom());*/

    }
    /*public void persist(Object object) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ISJPU");
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }*/

    /**
     * fonction qui affecte un role à un utilisateur
     * @param user
     * @param role
     */
    public void affecterUtilisateurRole (Utilisateur user, Role role) {
        role.getUtilisateurs().add(user);
        user.getRoles().add(role);
    }

    /**
     * fonction qui crée 27 droits en bd et l'affecte à un role
     * @param role
     */
    public void creerDroitRole (Role role){
        DroitFacade df = new DroitFacade();

        Droit d1 = new Droit("droit sur Classe", "permet d'influencer les classes", "Classe", false, false, false, false, role);
        Droit d2 = new Droit("droit sur Utilisateur", "permet d'influencer les utilisateurs", "Utilisateur", false, false, false, false,role);
        Droit d3 = new Droit("droit sur AnneeAcadémique", "permet d'influencer les années académiques", "AnneeAcademique", false, false, false, false,role);
        Droit d4 = new Droit("droit sur Anonymat", "permet d'influencer les anonymats", "Anonymat", false, false, false, false,role);
        Droit d5 = new Droit("droit sur Candidat", "permet d'influencer les candidats", "Candidat", false, false, false, false,role);
            Droit d6 = new Droit("droit sur Discipline", "permet d'influencer les disciplines", "Discipline", false, false, false, false,role);
            Droit d7 = new Droit("droit sur Email", "permet d'influencer les emails", "Email", false, false, false, false,role);
            Droit d8 = new Droit("droit sur Enseignant", "permet d'influencer les enseignants", "Enseignant", false, false, false, false,role);
            Droit d9 = new Droit("droit sur Enseignement", "permet d'influencer les enseignements", "Enseignement", false, false, false, false,role);
            Droit d10 = new Droit("droit sur EnvoiMessage", "permet d'influencer les envois des messages", "EnvoiMessage", false, false, false, false,role);
            Droit d11 = new Droit("droit sur EstInscrit", "permet d'influencer les inscriptions des candidats aux enseignements", "EstInscrit", false, false, false, false,role);
            Droit d12 = new Droit("droit sur Etudiant", "permet d'influencer les etudiants", "Etudiant", false, false, false, false,role);
            Droit d13 = new Droit("droit sur Evaluation", "permet d'influencer les évaluations", "Evaluation", false, false, false, false,role);
            Droit d14 = new Droit("droit sur Filiere", "permet d'influencer les filieres", "Filiere", false, false, false, false,role);
            Droit d15 = new Droit("droit sur HistoriqueNote", "permet d'influencer les historiques des notes", "HistoriqueNote", false, false, false, false,role);
            Droit d16 = new Droit("droit sur Module", "permet d'influencer les modules", "Module", false, false, false, false,role);
            Droit d17 = new Droit("droit sur Niveau", "permet d'influencer les niveaux", "Niveau", false, false, false, false,role);
            Droit d18 = new Droit("droit sur Note", "permet d'influencer les notes", "Note", false, false, false, false,role);
            Droit d19 = new Droit("droit sur Role", "permet d'influencer les roles", "Role", false, false, false, false,role);
            Droit d20 = new Droit("droit sur Semestre", "permet d'influencer les semestres", "Semestre", false, false, false, false,role);
            Droit d21 = new Droit("droit sur Sms", "permet d'influencer les sms", "Sms", false, false, false, false,role);
            Droit d22 = new Droit("droit sur Specialite", "permet d'influencer les specialités", "Specialite", false, false, false, false,role);
            Droit d23 = new Droit("droit sur TypeEvaluation", "permet d'influencer les évaluations", "Evaluation", false, false, false, false,role);
            Droit d24 = new Droit("droit sur UE", "permet d'influencer les UE", "UE", false, false, false, false,role);
            Droit d25 = new Droit("droit sur Droit", "permet d'influencer les droits", "Droit", false, false, false, false,role);

            df.create(d1);
            df.create(d2);
            df.create(d3);
            df.create(d4);
            df.create(d5);
            df.create(d6);
            df.create(d7);
            df.create(d8);
            df.create(d9);
            df.create(d10);
            df.create(d11);
            df.create(d12);
            df.create(d13);
            df.create(d14);
            df.create(d15);
            df.create(d16);
            df.create(d17);
            df.create(d18);
            df.create(d19);
            df.create(d20);
            df.create(d21);
            df.create(d22);
            df.create(d23);
            df.create(d24);
            df.create(d25);
        }



    /**
     * fonction qui retourne l'utilisateur par rapport au numéro de téléphone
     * @param numero le numéro de l'utilisateur
     * @return l'utilisateur possédant le téléphone numero
     * @throws NoResultException
     */

    public Utilisateur renvoyerLoginTelephone(int numero) throws NoResultException{
        String sql = "SELECT u FROM Utilisateur u WHERE u.telephone=:telephone";
        UtilisateurFacade uf=new UtilisateurFacade();
        Query query =uf.getEntityManager().createQuery(sql);
        query.setParameter("telephone",numero);
        return (Utilisateur)query.getSingleResult();
    }

    /**
     * fonction qui vérifie si l'utilisateur possédant le numéro de téléphone est en bd
     * @param numero
     * @return true si l'utilisateur possédant le numéro de telephone est en bd et false sinon
     */
    public Boolean isTelephoneInBD(int numero){
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur = renvoyerLoginTelephone(numero);
            return true;
        }catch (NoResultException n) {
            return false;
        }
    }

    /**
     * fonction qui cherche en BD l'utilisateur possédant l'email en parametre
     * @param email
     * @return l'utilisateur possédant l'email en parametre
     * @throws NoResultException
     */
    public Utilisateur renvoyerLoginEmail(String email) throws NoResultException{
        String sql = "SELECT u FROM Utilisateur u WHERE u.email=:email";
        UtilisateurFacade uf=new UtilisateurFacade();
        Query query =uf.getEntityManager().createQuery(sql);
        query.setParameter("email",email);
        return (Utilisateur)query.getSingleResult();
    }

    /**
     * fonction qui vérifie si un email est en BD
     * @param email
     * @return true si l'utilisateur possédant le numéro de telephone est en bd et false sinon
     */
    public Boolean isEmailInBD(String email){
        Utilisateur utilisateur = new Utilisateur();
        try {
            utilisateur = renvoyerLoginEmail(email);
            return true;
            }catch (NoResultException n) {
                return false;
            }
    }

    /**
     * fonction qui permet d'authentifier un utilisateur
     * @param login
     * @param password
     * @return l'utilisateur possédant ces données ou l'utilisateur null si l'utilisateur n'existe pas en base de données
     * @throws NoResultException
     */
    public Utilisateur authentification(String login, String password) throws NoResultException {
        UtilisateurFacade utilisateurFacade = new UtilisateurFacade();
        String sql = "SELECT u FROM Utilisateur u WHERE u.login=:login AND u.motDePasse=:mot_de_passe AND u.statut=:statut";
        Query query = utilisateurFacade.getEntityManager().createQuery(sql);
        query.setParameter("login",login);
        query.setParameter("mot_de_passe",password);
        query.setParameter("statut", Personne.Statut.ACTIVE);
        return (Utilisateur)query.getSingleResult();
    }

    /**
     * fonction qui permet de récupérer les champs d'une table en BD
     * @param entity
     * @return un objet de type ResultSetMetaData qui contient les données sur une table de la BD
     */
    public ResultSetMetaData renvoyerChamp(Class  entity){
        UtilisateurFacade uf = new UtilisateurFacade();
        List <String> champs = new ArrayList<>();
        String query= "SELECT * FROM " + entity.getSimpleName();
        try{
            Statement statement = uf.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            ResultSetMetaData champ = resultSet.getMetaData();
            return champ;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * fonction qui permet de sauveagarder un email qui a été envoyé
     * @param email
     * @param candidat
     * @return la chaine de caractère succes
     */
    public String sauvegarderEmailSucces(Email email,Candidat candidat){
        EnvoiMessage envoiMessage = new EnvoiMessage();
        Date dateEnvoi = new Date();
        EnvoiMessageFacade envoiMessageFacade = new EnvoiMessageFacade();
        EmailFacade emailFacade = new EmailFacade();
        envoiMessage.setLibelle("");
        envoiMessage.setDescription("");
        envoiMessage.setDateEnvoi(dateEnvoi);
        envoiMessage.setStatut(EnvoiMessage.Statut.SUCCES);
        envoiMessage.setMessage(email);
        envoiMessage.setCandidat(candidat);
        envoiMessageFacade.create(envoiMessage);
        return "succes";
    }

    /**
     * fonction qui permet de sauvegarder un email qui n'a pas été envoyé
     * @param email
     * @param candidat
     * @return la chaine de caractère echec
     */
    public String sauvegarderEmailEchec(Email email,Candidat candidat){
        EnvoiMessage envoiMessage = new EnvoiMessage();
        Date dateEnvoi = new Date();
        EnvoiMessageFacade envoiMessageFacade = new EnvoiMessageFacade();
        EmailFacade emailFacade = new EmailFacade();

        envoiMessage.setLibelle("");
        envoiMessage.setDescription("");
        envoiMessage.setDateEnvoi(dateEnvoi);
        envoiMessage.setStatut(EnvoiMessage.Statut.ECHEC);
        envoiMessage.setMessage(email);
        envoiMessage.setCandidat(candidat);

        envoiMessageFacade.create(envoiMessage);

        return "echec";
    }

    /**
     * fonction qui permet de sauvegarder un sms qui a été envoyé
     * @param sms
     * @param candidat
     * @return la chaine de caractère succès
     */
    public String sauvegarderSmsSucces(Sms sms,Candidat candidat){
        EnvoiMessage envoiMessage = new EnvoiMessage();
        Date dateEnvoi = new Date();
        EnvoiMessageFacade envoiMessageFacade = new EnvoiMessageFacade();
        SmsFacade smsFacade = new SmsFacade();

        envoiMessage.setLibelle("");
        envoiMessage.setDescription("");
        envoiMessage.setDateEnvoi(dateEnvoi);
        envoiMessage.setStatut(EnvoiMessage.Statut.SUCCES);
        envoiMessage.setMessage(sms);
        envoiMessage.setCandidat(candidat);

        envoiMessageFacade.create(envoiMessage);

        return "succes";
    }

    /**
     * fonction qui permet de sauvegarder un sms qui n'a pas été envoyé
     * @param sms
     * @param candidat
     * @return la chaine de caractère echec
     */
    public String sauvegarderSmsEchec(Sms sms,Candidat candidat){
        EnvoiMessage envoiMessage = new EnvoiMessage();
        Date dateEnvoi = new Date();
        EnvoiMessageFacade envoiMessageFacade = new EnvoiMessageFacade();
        SmsFacade smsFacade = new SmsFacade();

        envoiMessage.setLibelle("");
        envoiMessage.setDescription("");
        envoiMessage.setDateEnvoi(dateEnvoi);
        envoiMessage.setStatut(EnvoiMessage.Statut.ECHEC);
        envoiMessage.setMessage(sms);
        envoiMessage.setCandidat(candidat);

        envoiMessageFacade.create(envoiMessage);

        return "echec";
    }

    /**
     * fonction qui retourne le candidat possédant l'email passé en paramètre
     * @param email
     * @return un objet candidat null s'il n'existe en bd et l'objet candidat en question sinon
     * @throws NoResultException
     */
    public Candidat retrouverCandidatEmail(String email) throws NoResultException{
        String sql = "SELECT c FROM Candidat c WHERE c.email=:email";
        CandidatFacade cf=new CandidatFacade();
        Query query =cf.getEntityManager().createQuery(sql);
        query.setParameter("email",email);
        return (Candidat) query.getSingleResult();
    }

    /**
     * fonction qui retourne le candidat possédant le sms passé en paramètre
     * @param telephone
     * @return un objet candidat null s'il n'existe en bd et l'objet candidat sinon
     * @throws NoResultException
     */
    public Candidat retrouverCandidatSms(int telephone) throws NoResultException{
        String sql = "SELECT c FROM Candidat c WHERE c.telephone=:telephone";
        CandidatFacade cf=new CandidatFacade();
        Query query =cf.getEntityManager().createQuery(sql);
        query.setParameter("telephone",telephone);
        return (Candidat) query.getSingleResult();
    }
}