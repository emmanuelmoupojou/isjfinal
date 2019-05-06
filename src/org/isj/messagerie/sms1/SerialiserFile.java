package org.isj.messagerie.sms1;

import org.isj.messagerie.sms1.Queue;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

// Referenced classes of package dataManeger:
//            Queue

public final class SerialiserFile
{

    public SerialiserFile()
    {
    }

    public static void doIt(Queue f)
    {
        try
        {
            File fi=new File("Configuration_Files");
            if(!fi.exists())
                fi.mkdirs();
            
            File fic=new File(".\\Configuration_Files\\DataQueue.ser");
            if(!fic.exists())
                fic.createNewFile();
            
//            FileOutputStream fichier = new FileOutputStream(new Object().getClass().getResource("/Configuration_Files/DataQueue.ser").getPath());
            FileOutputStream fichier = new FileOutputStream(fic);
            ObjectOutputStream oos = new ObjectOutputStream(fichier);
            oos.writeObject(f);
            oos.flush();
            oos.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
