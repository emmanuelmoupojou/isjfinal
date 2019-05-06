package org.isj.messagerie.sms1;;

import org.isj.messagerie.sms1.Queue;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

public class DeserialiserFile {

    public static Queue doIt() {
        Queue f = null;
        try {
//            File fic=new File(getClass().getResourceAsStream("/Configuration_Files/DataQueue.ser").);
//            if(!fic.exists())
//                fic.createNewFile();
//            FileInputStream fichier = new FileInputStream(fic);
            
            /*
             * if(!fichier.markSupported()){ System.out.println("exited");
             * return null;
	      }
             */
            
//            if(fichier==null)
//                System.out.println("Fichier == null");
//            else
//                System.out.println("Fichier != null");
            
            File fic=new File("DataQueue.ser");  
            if(!fic.exists())
                fic.createNewFile();
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fic));
            f = (Queue) ois.readObject();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }
}
