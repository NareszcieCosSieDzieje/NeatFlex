package NeatFlexClasses.Simulation;

import java.io.*;
import java.net.URL;
import java.time.OffsetDateTime;

public class Serializer {

    public static void serializeSimulation(NeatFlexService serializedService) throws FileNotFoundException, java.io.IOException {
        URL url = Serializer.class.getResource("/simulation.txt"); //relative path from root
        PrintWriter pw = new PrintWriter(url.getPath());
        pw.close();
        try {
            FileOutputStream fileOut = new FileOutputStream(url.getPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(serializedService);
            out.close();
            fileOut.close();
        } catch (IOException i) {
            i.printStackTrace();
        }
    }

    public static NeatFlexService deserializeSimulation(){
        URL url = Serializer.class.getResource("/simulation.txt");
        try {
            FileInputStream fileIn = new FileInputStream(url.getPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            NeatFlexService deserializedService = (NeatFlexService) in.readObject();
            in.close();
            fileIn.close();
            return deserializedService;
        } catch (IOException i) {
            return null;
        } catch (ClassNotFoundException c) {
            c.printStackTrace();
            return null;
        }
    }



}
