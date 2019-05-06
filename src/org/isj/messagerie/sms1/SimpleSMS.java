/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.isj.messagerie.sms1;;

import org.isj.messagerie.sms1.Queue;
import javax.swing.JOptionPane;
import org.isj.messagerie.sms1.Gateway;
/**
 *
 * @author cons
 */
public class SimpleSMS {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {


        System.out.println("\n\nRECHERCHE DU MODEM ......");

        Queue f = new Queue();
        CommTest commTest = new CommTest();
        String[] modemInfo = (String[]) commTest.getPort();
int modemStatut;

        if (modemInfo[0] == null) {

            System.out.println("\nMODEM NON DETECTE !\n");
            JOptionPane.showMessageDialog(null, "MODEM NON DETECTE !\nRassurez vous d'avoir respecte toutes les instructiions", "Service SMS", 0);
            modemStatut = 2;
        }
        System.out.println((new StringBuilder("DETECTE AU PORT : ")).append(modemInfo[0]).append("\n").toString());
        System.out.println("\nINITIALISATION DU MODEM .....");
        try {
           Gateway gateway = new Gateway( modemInfo[0], Integer.parseInt(modemInfo[1]), f, "fabricant_modem","modele_modem");
        } catch (Exception e) {
            new JOptionPane();
            JOptionPane.showMessageDialog(null, "Erreur survenue lors de l'initialisatiion du modem.\nRassurer vous d'avoir respecte toutes les instructions.", "Service SMS", 0);
        }
    }
}
