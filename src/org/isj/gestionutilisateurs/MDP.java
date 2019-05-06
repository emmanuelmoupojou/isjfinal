/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.isj.gestionutilisateurs;

import org.isj.traitementmetier.Isj;
import org.isj.traitementmetier.entites.*;
import org.isj.traitementmetier.Isj;

import java.security.MessageDigest;
import java.util.Date;

/**
 *
 * @author Dodolina
 */
public class MDP {
    private double codegenere;

    static String toHexString(byte[] array){
        StringBuilder sb = new StringBuilder(array.length *2);
        
        for(byte b : array){
            
           int value =0xFF & b   ;
            String toAppend = Integer.toHexString(value);
            sb.append(toAppend).append("-");
        }
        sb.setLength(sb.length()-1);
        return sb.toString().toUpperCase();
    }
int codeAuthentification;
  
  private static final int MIN = 1234;
   
 private static final int MAX = 2134;

    
private String getMatricule(String email) {
       
 String matricule = null;

        return matricule;
    }

public double code(){
        return MIN + (Math.random() * (MAX-MIN));
    }
public String Hachage(String a){
     String msgHash = null;
       try{
            
            MessageDigest md = MessageDigest.getInstance("sha-512");
            
            byte[] hash = md.digest(a.getBytes());
           //System.out.println("message:");
            msgHash = toHexString(hash);
            
            //System.out.println("message hash:" +msgHash);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    return msgHash;
        
    }
   
 public String message(String email, String objet, String message, Date date)
{
        System.out.println("Objet: "+objet+"\n Message: "+message+"\n\n "+date);

        return (getMatricule(email));

    }
    Utilisateur d = null;
    public Utilisateur comparerCodeAvecTel(double code, int numtel){//id c'est ce qui a été généré,code ce que l'utilisateur entre


        if(code==codegenere) {

            d = i.renvoyerLoginTelephone(numtel);//prend le numero de tel et cherche a quel utilisateur il appartient en bd

        }

       return d;
    }


    public  void modifierMotDePasse(String newmdp,Utilisateur d){

        String z = hachage(newmdp);
        d.setMotDePasse(z);
        i.mettreJour(d);//remplace le mot de passe a l'aide du login recu
    }
    public Utilisateur comparerCodeAvecMail(double code,double codegenere,String email){//code ce que l'utilisateur entre sur l'interface

        Utilisateur d = new Utilisateur();
        if(code==codegenere){

            d = i.renvoyerLoginEmail(email ); //prend l'email et cherche a quel utilisateur il appartient en bd

        }
        return d;
    }

     Isj i= new Isj();

    //connexion a l'application

 //recupération du mot de passe
 public void recuperationtel(int numTel){////par l'equipe interface une mathode qui prend le
     
    // ;num de tel entré par l'utilisateur
     
     boolean b = i.isTelephoneInBD(numTel);//par traitement metier pour voir si l'etudiant est present en bd
     
     if(b==true){
         codegenere =code();
         envoyermessage(numTel,codegenere);//fait par smslib

     }
 }
 public void recuperationmail(String mail){
     //mail recupere par l'equipe interface une methode qui prend le num de tel entré par l'utilisateur
     
     boolean e = i.isEmailInBD(mail);//par traitement metier pour voir si l'etudiant est present en bd
     
     if(e==true){
         codegenere =code();
         envoyermail(mail,codegenere);//fait par smslib

     }
 }





    private int recuperertel() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void envoyermail(String d, double id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void envoyermessage(int i,double code) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double recupererCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String recuperernewmdp() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String hachage(String m) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    private void Afficheruser() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void insererBdNewMdpd(String d, String z) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    

}


